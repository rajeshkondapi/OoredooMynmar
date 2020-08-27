package AndroidAppiumAuto;


import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class BrowserStack {
	
		  
		  public static String userName = "srikanthmuthyala1";
		  public static String accessKey = "3XLvRiqzwQCmyRawHZPi";

		  public static void main(String args[]) throws MalformedURLException, InterruptedException {
		    DesiredCapabilities caps = new DesiredCapabilities();

		    caps.setCapability("device", "Google Pixel 3");
		    caps.setCapability("os_version", "9.0");
		    caps.setCapability("project", "Rajesh Project");
		    caps.setCapability("build", "Rajesh Build");
		    caps.setCapability("name", "Rajesh Sample Test");
		    caps.setCapability("browserstack.resignApp" , "false");
		   // caps.setCapability("app", "bs://0cdf35577b922f00ce0680999affe990cc90e5c7");
		    caps.setCapability("app", "bs://c2ac5aa8329fd54dcfb4e90207afc6dc57600a31");

		    AndroidDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(new URL("https://"+userName+":"+accessKey+"@hub-cloud.browserstack.com/wd/hub"), caps);

		    /* Write your Custom code here */
		    driver.get("https://www.google.com");

		    driver.quit();
		  }
		}


