package emlakburada.util;

public class EmailContentBuilderUtil {
	
	private static String registerTemplate = "<p>Hoş geldin ${user},</p>\n" ;
	
	private EmailContentBuilderUtil() {
		
	}
	
	public static String registerBuild(String userName) {
		return registerTemplate.replaceAll("\\$\\{*user\\}", userName.split("@")[0]);
	}
	
	public static String advertBuild() {
		return "Ilan basariyla olusturuldu";
	}

}