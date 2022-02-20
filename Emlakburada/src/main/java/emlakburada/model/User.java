package emlakburada.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	private String userType;
	private String name;
	private String email;
	private String photo;
	private String bio;
	private int userId;
	private ArrayList<Advert> favorites = new ArrayList<>();
	private ArrayList<Advert> publishledAdvert = new ArrayList<>();
	private List<Message> messageBox;

}
