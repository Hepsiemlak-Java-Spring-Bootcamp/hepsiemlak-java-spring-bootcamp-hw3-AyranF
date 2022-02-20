package emlakburada.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import emlakburada.dto.request.AddUserAdvertRequest;
import emlakburada.dto.response.AdvertResponse;
import emlakburada.dto.response.UserResponse;
import emlakburada.model.Advert;
import emlakburada.model.User;

@Repository
public class UserRepository {
	
	ArrayList<User> userList = new ArrayList<>();
	
	
	public ArrayList<User> fetchAllUsers() {
		return userList;
	}
	
	public User createUser(User user) {
		userList.add(user);
		System.out.println(user.toString());
		return user;
	}

	public User findUserById(int userId) {
		for(User u : userList) {
			if(u.getUserId() == userId)
				return u;
		}
		return null;
	}

	public User addNewAdvert(Advert Advert, int userId) {
		User user = findUserById(userId);
		user.getPublishledAdvert().add(Advert);
		return user;
	}

	public User addFavoriteAdvert(User user, Advert advert) {
		user.getFavorites().add(advert);
		return user;
	}
}