package emlakburada.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import emlakburada.client.request.BannerRequest;

@FeignClient(name="Emlakburada-Banner",url= "http://localhost:8081/")
public interface BannerClient {
	@PostMapping(value = "/banners")
	void saveBanner(BannerRequest request);
}