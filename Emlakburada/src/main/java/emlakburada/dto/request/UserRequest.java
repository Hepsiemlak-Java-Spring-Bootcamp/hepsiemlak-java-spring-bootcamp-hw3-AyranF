package emlakburada.dto.request;

import lombok.Data;

@Data
public class UserRequest {
	
	private String name;
	private String bio;
	private String userType;
	private String email;
}
