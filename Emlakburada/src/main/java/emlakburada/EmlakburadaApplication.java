package emlakburada;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class EmlakburadaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmlakburadaApplication.class, args);
		yaz("asd");
		yaz(1);
		yaz(1.1);
	}
	public static void yaz(Object s) {
		System.out.println(s.toString()+" :(");
	}
	public static void yaz(String s) {
		System.out.println(s);
	}
	public static void yaz(int s) {
		System.out.println(s+" ):");
	}
	
}
