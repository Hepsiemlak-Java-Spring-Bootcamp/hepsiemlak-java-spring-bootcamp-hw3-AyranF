package emlakburada.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import emlakburada.repository.DbConnectionRepository;
import emlakburada.repository.IlanRepository;

@Service
public class KullaniciService {

	@Autowired
	@Qualifier(value = "mongoConnectionRepository")
	private DbConnectionRepository dbConn;

	@Autowired
	private IlanRepository ilanRepository;

	public void getAllKullanici() {
		System.out.println("KullaniciService -> ilanRepository: " + ilanRepository.toString());
	}

}