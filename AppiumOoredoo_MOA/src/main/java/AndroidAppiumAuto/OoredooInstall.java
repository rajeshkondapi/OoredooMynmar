package AndroidAppiumAuto;

import java.io.IOException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class OoredooInstall {
	
	public static AndroidDriver<MobileElement> driver;
	//driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	
	
	public static void main(String[] args) throws IOException {		
		//Device Configurations
		
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
		//cap.setCapability(MobileCapabilityType.UDID, "520074b4ee426523");
		//cap.setCapability(MobileCapabilityType.UDID, "ZX1D637KJM");
		cap.setCapability(MobileCapabilityType.UDID, "4d0082a64a9d41c7");
		
		
		//cap.setCapability(MobileCapabilityType.PLATFORM, "Android");
		cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, "6");
		
	//	cap.setCapability(MobileCapabilityType.APP, "C:\\Users\\rajesh.k\\Downloads\\apks\\MOA7_latest1212.apk");		
		cap.setCapability(MobileCapabilityType.APP, "C:\\Users\\rajesh.k\\Downloads\\apks\\myIM3_LMS_06April2020_@staging.apk");
		URL url = new URL("http://127.0.0.1:4723/wd/hub");
		
		System.setProperty("webdriver.http.factory", "apache");
		
		
		driver = new AndroidDriver<MobileElement>(url,cap);
		
		driver.quit();
	}
}
