package emlakburada.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import emlakburada.client.BannerClient;
import emlakburada.client.request.AddressRequest;
import emlakburada.client.request.BannerRequest;
import emlakburada.dto.request.AddUserAdvertRequest;
import emlakburada.dto.request.AdvertRequest;
import emlakburada.dto.request.UserRequest;
import emlakburada.dto.response.AdvertResponse;
import emlakburada.dto.response.UserResponse;
import emlakburada.model.Advert;
import emlakburada.model.RealEstate;
import emlakburada.model.User;
import emlakburada.queue.ActiveMqService;
import emlakburada.queue.RabbitMqService;
import emlakburada.repository.DbConnectionRepository;
import emlakburada.repository.IlanRepository;
import emlakburada.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private IlanRepository ilanRepository;

	@Autowired
	@Qualifier(value = "jdbcConnectionRepository")
	private DbConnectionRepository dbConn;

	
	private static int userId = 0;
	
	
	@Autowired
	RabbitMqService rabbitMqSerivce;
	
	@Autowired
	ActiveMqService activeMqService;
	

	public List<UserResponse> getAllUsers() {
		List<UserResponse> userList = new ArrayList<>();
		for (User user : userRepository.fetchAllUsers()) {
			userList.add(convertToUserResponse(user));
		}
		return userList;
	}

	public UserResponse createUser(UserRequest request) {
		User createUser = userRepository.createUser(convertToUser(request));
		EmailMessage emailMessage = new EmailMessage("osmanatayozturk@gmail.com"+" 0");
		activeMqService.sendMessage(emailMessage);
		return convertToUserResponse(createUser);
	}

	private UserResponse convertToUserResponse(User user) {
		UserResponse response = new UserResponse();
		response.setEmail(user.getEmail());
		response.setName(user.getName());
		response.setPublishedAdvertIds(user.getPublishledAdvert().stream().map(i -> i.getAdvertNo()).toArray());
		response.setFavorites(user.getFavorites().stream().map(i -> i.getAdvertNo()).toArray());
		response.setMessageBox(user.getMessageBox());
		response.setUserId(user.getUserId());
		return response;
	}
	
	private AdvertResponse convertToAdvertResponse(Advert advert) {
		AdvertResponse response = new AdvertResponse();
		response.setBaslik(advert.getBaslik());
		response.setFiyat(advert.getFiyat());
		advert.setAdvertNo(advert.getAdvertNo());
		response.setUserName(advert.getUser().getName());
		return response;
	}

	private User convertToUser(UserRequest request) {
		User user = new User();
		user.setEmail(request.getEmail());
		user.setName(request.getName());
		user.setBio(request.getBio());
		user.setUserId(userId);
		user.setUserType(request.getUserType());
		userId++;
		return user;
	}

	public User getUserById(int userId) {
		return userRepository.findUserById(userId);
	}

	public UserResponse getResponseUserById(int userId) {
		return convertToUserResponse(getUserById(userId));
	}
	
	public ArrayList<AdvertResponse> getFavoriteAdvertById(int userId) {
		User user = getUserById(userId);
		ArrayList<AdvertResponse> favorites = new ArrayList<>();
		for(Advert a : user.getFavorites())
			favorites.add(convertToAdvertResponse(a));
		return favorites;
	}

	public UserResponse addAdvert(AddUserAdvertRequest request, int userId) {
		Advert advert = ilanRepository.findAdvertByAdvertId(request.getAdvertId());
		User user = getUserById(userId);
		if(advert.getUser() != user)
			return convertToUserResponse(userRepository.addFavoriteAdvert(user, advert));
		return null;
	}

	public ArrayList<AdvertResponse> getPublishledAdverts(int userId) {
		User user = getUserById(userId);
		ArrayList<AdvertResponse> publishedAdverts = new ArrayList<>();
		for(Advert a : user.getPublishledAdvert())
			publishedAdverts.add(convertToAdvertResponse(a));
		return publishedAdverts;
	}

}