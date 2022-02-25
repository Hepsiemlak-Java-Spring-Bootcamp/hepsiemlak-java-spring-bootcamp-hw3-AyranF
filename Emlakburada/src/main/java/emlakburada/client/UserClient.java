package emlakburada.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import emlakburada.dto.request.AddUserAdvertRequest;

@FeignClient(name="User-Client",url= "http://localhost:8080/")
public interface UserClient {
	@PatchMapping(value = "/users/{userId}")
	void addAdvertToUser(AddUserAdvertRequest request, @PathVariable("userId") int UserId);
}