package PageClasses.Utilities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigDetails {
	
	
	public static String getProperty(String sKey){
		String sProperyValue = "";
		
		Properties prop = new Properties();
		
		try {
			prop.load(new FileReader("./src/main/resources/config.properties"));
			
			sProperyValue = prop.getProperty(sKey);
			
			System.out.println("Property returned: "+ sProperyValue);
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
		
		return sProperyValue;
		
	}

}
