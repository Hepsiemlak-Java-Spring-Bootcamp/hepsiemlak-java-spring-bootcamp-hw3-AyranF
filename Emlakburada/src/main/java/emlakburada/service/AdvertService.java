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
import emlakburada.client.request.AddressRequest;
import emlakburada.client.request.BannerRequest;
import emlakburada.dto.AdvertRequest;
import emlakburada.dto.response.AdvertResponse;
import emlakburada.model.Advert;
import emlakburada.model.RealEstate;
import emlakburada.model.User;
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
	private KullaniciService kullaniciService;
	
	private static int advertNo = 38164784;
	
	@Autowired
	private BannerClient bannerClient;
	
	@Autowired
	RabbitMqService rabbitMqSerivce;
	

	// @Autowired
//	public IlanService(IlanRepository ilanRepository) {
//		super();
//		this.ilanRepository = ilanRepository;
//	}

	public List<AdvertResponse> getAllAdverts() {
		// System.out.println("IlanService -> ilanRepository: " +
		// advertRepository.toString());
		// kullaniciService.getAllKullanici();
		List<AdvertResponse> advertList = new ArrayList<>();
		for (Advert advert : advertRepository.fetchAllAdverts()) {
			advertList.add(convertToAdvertResponse(advert));
		}
		return advertList;
	}

	public AdvertResponse saveAdvert(AdvertRequest request) {
		Advert savedAdvert = advertRepository.saveAdvert(convertToAdvert(request));
		EmailMessage emailMessage = new EmailMessage("osmanatayozturk@gmail.com");
		rabbitMqSerivce.sendMessage(emailMessage);
		bannerClient.saveBanner(prepareSaveBannerRequest());
		return convertToAdvertResponse(savedAdvert);
	}

	private AdvertResponse convertToAdvertResponse(Advert savedAdvert) {
		AdvertResponse response = new AdvertResponse();
		response.setBaslik(savedAdvert.getBaslik());
		response.setFiyat(savedAdvert.getFiyat());
		response.setAdvertNo(savedAdvert.getAdvertNo());
		return response;
	}

	private Advert convertToAdvert(AdvertRequest request) {
		Advert advert = new Advert(0, new RealEstate(), null, new User(), new String[5], null, 0, false, false, null, false);
		advertNo++;
		
		advert.setAdvertNo(advertNo);
		advert.setBaslik(request.getBaslik());
		advert.setFiyat(request.getFiyat());
		return advert;
	}

	public AdvertResponse getAdvertByAdvertId(int advertId) {
		Advert advert = advertRepository.findAdvertByAdvertId(advertId);
		return convertToAdvertResponse(advert);
	}
	private BannerRequest prepareSaveBannerRequest() {
		BannerRequest request = new BannerRequest();
		request.setAdvertNo(advertNo);
		request.setPhone("555");
		request.setTotal(1);
		request.setAddress(new AddressRequest("istanbul", "kadıköy", "acik adres"));
		return request;
	}
}