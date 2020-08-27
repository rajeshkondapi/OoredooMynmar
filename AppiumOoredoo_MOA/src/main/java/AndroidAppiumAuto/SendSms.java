package AndroidAppiumAuto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class SendSms {

	
	DesiredCapabilities cap = new DesiredCapabilities();
	public AndroidDriver<MobileElement> driver;
	
	
	
	@Test(enabled=false)
	public void sendsms() throws IOException {
		
		
		String[] buypacksArray = new String[]{ "bpinternet","bpdata","bpcombo","bpfacebook","bp6xpack","bpvoice","bponNetPk","bpAnyNetPk","bpInternational","bpRoam","bprmthai","bprmsinga","bprmIndia","bprmIndone","bprmRussia","bprmQatar","bprmVietna" };
System.out.println("hello ==>"+buypacksArray.length);

for(int i=0 ; i<buypacksArray.length; i++) {
	System.out.println("hello ==>"+buypacksArray[i]);
}
		
		File file = new File("C:\\Users\\rajesh.k\\Desktop\\Enterp.xls");

		FileInputStream inputStream = new FileInputStream(file);

		Workbook OoredoWorkbook = new HSSFWorkbook(inputStream);

		Sheet OoredoSheet = OoredoWorkbook.getSheet("sheet1");

		int rowCount = OoredoSheet.getLastRowNum() - OoredoSheet.getFirstRowNum();

		System.out.println("rowCount ==> " + rowCount);		

		for (int i = 1; i <=rowCount; i++) {		

			String Key = OoredoSheet.getRow(i).getCell(0).toString();

			String Value = OoredoSheet.getRow(i).getCell(1).toString();

			//mpinsheet.put(Key, Value);
		
		
	driver.findElement(By.xpath("//android.widget.ImageButton[contains(@resource-id,'com.android.mms:id/floating_action_button')]")).click();
			
	driver.findElement(By.xpath("//android.widget.EditText[contains(@resource-id,'com.android.mms:id/recipients_editor_to')]")).sendKeys(Key);
	
	driver.findElement(By.xpath("//android.widget.EditText[contains(@resource-id,'com.android.mms:id/edit_text_bottom')]")).sendKeys(Value);
	
	driver.findElement(By.xpath("//android.widget.ImageButton[contains(@resource-id,'com.android.mms:id/send_button')]")).click();
	
	driver.findElement(By.xpath("//android.widget.ImageView[contains(@resource-id,'com.android.mms:id/actionbar_arrow')]")).click();
	
	}
	
	
	}
	
	
	//@BeforeTest
	public void launchapp() throws MalformedURLException {
	cap.setCapability(MobileCapabilityType.DEVICE_NAME, "uiautomatorviewer");


	//Android 9 Samsung A8+
	cap.setCapability(MobileCapabilityType.UDID, "4d0082a64a9d41c7");
	cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, "6");

	cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");				

	cap.setCapability("appPackage", "com.android.mms");		

	cap.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, "true");


	cap.setCapability("appActivity", "com.android.mms.ui.ComposeMessageMms");
	

	URL url = new URL("http://127.0.0.1:4723/wd/hub");

	System.setProperty("webdriver.http.factory", "apache");

	driver = new AndroidDriver<MobileElement>(url,cap);


}
	
	@Test
	public void testt() {

		//String s = "350MB and 20 Any-Net minutes valid 7 days for only 699ks.Press ok";
		/*String s = "Get 1GB Data for 1099ks + 5 Tiekt for 1000 Lakh draw. Press buy now to cinfirm";
		String fileName = StringUtils.substringAfter(s, "for ");
		
		System.out.println(fileName);

			int iend = fileName.indexOf("ks");
			System.out.println(iend);
			System.out.println(fileName.substring(0,iend));

			String messKs = fileName.substring(0, iend).replaceAll("[^0-9]", "");
			System.out.println(messKs);
		
		 StringTokenizer st = new StringTokenizer(s);  
	        
	      // printing next token  
	      System.out.println("Next token is : " + st.nextToken(","));*/
	      
	      
	     
	      		String mess ="You will activate 1 GB for only 1099Ks Pack valid for 30 DAYS, Charged 1099.00 ks, Press oK to Confirm";
	      		String[] arr = mess.split(" ");
	      		int i=0;
	      		String finalVal = "";
	      		for(String token : arr) {
	      			if(token.equalsIgnoreCase("ks")) {
	      				finalVal = arr[i-1];
	      				break;
	      			} 
	      			if(token.contains("Ks")) {
	      				finalVal = token;
	      				break;
	      			}
	      			i++;
	      		}
	      		/*StringTokenizer st = new StringTokenizer(mess, " ");
	      		
	      		while (st.hasMoreTokens()) {
	      			
	      			String token = st.nextToken();
	      			
	      			if(token.contains("ks")) {
	      				finalVal = token;
	      				break;
	      			}
	      		}*/
	      		
	      		System.out.println(finalVal);
	      		String messKs = finalVal.trim().replace("Ks", "");
	      		System.out.println("messKs ==> " + messKs);
	      	}
	      
	}

