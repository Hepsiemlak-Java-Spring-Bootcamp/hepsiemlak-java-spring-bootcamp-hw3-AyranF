package emlakburada.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Advert {
	private int advertNo;
	private RealEstate gayrimenkul;
	private String baslik;
	private User kullanici; // hem bireysel & kurumsal
	private String[] resimList = new String[5];
	private BigDecimal fiyat;
	private int suresi;
	private boolean oneCikarilsinMi = false;
	private boolean incelendiMi = false;
	private Date olusturulmaTarihi;
	private boolean aktifMi;

}