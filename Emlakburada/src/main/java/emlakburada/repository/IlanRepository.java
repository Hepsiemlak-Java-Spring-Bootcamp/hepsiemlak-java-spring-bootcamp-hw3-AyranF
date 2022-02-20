package emlakburada.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import emlakburada.model.Advert;
import emlakburada.model.RealEstate;
import emlakburada.model.User;
import emlakburada.repository.UserRepository;


@Repository
public class IlanRepository {

	private String url = "localhost";
	private String pass = "şifre";
	
	static List<Advert> ilanListesi = new ArrayList<>();
	
	@Autowired
	private static UserRepository userRepository;

	public List<Advert> fetchAllAdverts() {
		return ilanListesi;
	}

	static Advert prepareIlan(int advertNo, String baslik, int userId) {
		Advert advert = new Advert();
		//user.getPublishledAdverts().add(advert);
		advert.setUser(userRepository.findUserById(userId));
		advert.setAdvertNo(advertNo);
		advert.setBaslik(baslik);
		advert.setGayrimenkul(makeGayrimenkul());
		// kullanici.mesajKutusu.add(new Mesaj("acil dönüş")); // NPE

		// ilan.setKullanici(kullanici);

		advert.setAktifMi(true);

		advert.setResimList(makeResimList());

		return advert;
	}

	private static String[] makeResimList() {
		String[] resimList = new String[5];
		resimList[0] = "https://hecdnw01.hemlak.com/ds01/4/4/9/0/2/3/8/3/81d2e088-a551-485d-b2e9-664cc9200cdc.jpg";
		resimList[1] = "https://hecdnw01.hemlak.com/ds01/4/4/9/0/2/3/8/3/81d2e088-a551-485d-b2e9-664cc9200cdc.jpg";
		return resimList;
	}

	private static RealEstate makeGayrimenkul() {
		return new RealEstate();
	}

	public Advert saveAdvert(Advert advert) {
		ilanListesi.add(advert);
		return advert;
	}

	public Advert findAdvertByAdvertId(int advertNo) {
		for(int i = 0; i < ilanListesi.size(); i++) {
			if(ilanListesi.get(i).getAdvertNo() == advertNo)
				return ilanListesi.get(i);
		}
		return null;
	}

}