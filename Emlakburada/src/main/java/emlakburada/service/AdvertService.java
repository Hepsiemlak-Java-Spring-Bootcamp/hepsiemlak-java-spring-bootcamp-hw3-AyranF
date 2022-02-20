package emlakburada.service;

import java.util.ArrayList;
import java.util.List;

import javax.management.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import emlakburada.client.BannerClient;
import emlakburada.client.UserClient;
import emlakburada.client.request.AddressRequest;
import emlakburada.client.request.BannerRequest;
import emlakburada.dto.request.AddUserAdvertRequest;
import emlakburada.dto.request.AdvertRequest;
import emlakburada.dto.response.AdvertResponse;
import emlakburada.model.Advert;
import emlakburada.model.RealEstate;
import emlakburada.model.User;
import emlakburada.queue.ActiveMqService;
import emlakburada.queue.RabbitMqService;
import emlakburada.repository.DbConnectionRepository;
import emlakburada.repository.IlanRepository;

@Service
public class AdvertService {

	@Autowired
	private IlanRepository advertRepository;

	@Autowired
	@Qualifier(value = "jdbcConnectionRepository")
	private DbConnectionRepository dbConn;

	@Autowired
	private UserService userService;
	
	private static int advertNo = 0;
	
	@Autowired
	private BannerClient bannerClient;
	
	@Autowired
	private UserClient userClient;
	
	@Autowired
	RabbitMqService rabbitMqSerivce;
	
	@Autowired
	ActiveMqService activeMqService;
	

	public List<AdvertResponse> getAllAdverts() {
		List<AdvertResponse> advertList = new ArrayList<>();
		for (Advert advert : advertRepository.fetchAllAdverts()) {
			advertList.add(convertToAdvertResponse(advert));
		}
		return advertList;
	}

	public AdvertResponse saveAdvert(AdvertRequest request) {
		Advert savedAdvert = advertRepository.saveAdvert(convertToAdvert(request));
		User user = userService.getUserById(request.getUserId());
		EmailMessage emailMessage = new EmailMessage(user.getEmail()+" 1");
		activeMqService.sendMessage(emailMessage);
		bannerClient.saveBanner(prepareSaveBannerRequest());
		user.getPublishledAdvert().add(savedAdvert);
		return convertToAdvertResponse(savedAdvert);
	}

	private AdvertResponse convertToAdvertResponse(Advert savedAdvert) {
		AdvertResponse response = new AdvertResponse();
		response.setBaslik(savedAdvert.getBaslik());
		response.setFiyat(savedAdvert.getFiyat());
		response.setAdvertNo(savedAdvert.getAdvertNo());
		response.setUserName(savedAdvert.getUser().getName());
		return response;
	}

	private Advert convertToAdvert(AdvertRequest request) {
		Advert advert = new Advert();
		advert.setAdvertNo(advertNo);
		advert.setBaslik(request.getBaslik());
		advert.setFiyat(request.getFiyat());
		advert.setUser(userService.getUserById(request.getUserId()));
		advertNo++;
		return advert;
	}

	public AdvertResponse getAdvertByAdvertId(int advertId) {
		Advert advert = advertRepository.findAdvertByAdvertId(advertId);
		return convertToAdvertResponse(advert);
	}
	
	public Advert getAdvertToAddUser(int advertId) {
		return advertRepository.findAdvertByAdvertId(advertId);
	}
	
	private BannerRequest prepareSaveBannerRequest() {
		BannerRequest request = new BannerRequest();
		request.setAdvertNo(advertNo);
		request.setPhone("555");
		request.setTotal(1);
		request.setAddress(new AddressRequest("istanbul", "kadıköy", "acik adres"));
		return request;
	}
	private AddUserAdvertRequest prepareUserUpdate() {
		AddUserAdvertRequest request = new AddUserAdvertRequest();
		request.setAdvertId(advertNo);
		return request;
	}
}