package emlakburada.dto.response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import emlakburada.model.Advert;
import emlakburada.model.Message;
import emlakburada.model.RealEstate;
import emlakburada.model.User;
import lombok.Data;

@Data
public class UserResponse {

	private String userType;
	private String name;
	private String email;
	private String photo;
	private String bio;
	private int userId;
	private Object[]  favorites;
	private Object[] publishedAdvertIds;
	private List<Message> messageBox;
}