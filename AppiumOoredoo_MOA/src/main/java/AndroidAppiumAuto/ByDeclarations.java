package AndroidAppiumAuto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import ImageCaptureToast.ToastCapture;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;



public class ByDeclarations {	//ExtentTest test;
	ExtentReports extent;
	protected ExtentTest logger;
	Map<String,String> contextmap =  new HashMap<>();
	Map<String,Integer> balanceCheck = new HashMap<>();
	Properties obj = new Properties();
	
	ArrayList<String> dboffname = new ArrayList<String>();	
	public AndroidDriver<MobileElement> driver;

	By acceptWeb = By.id("com.ooredoo.selfcare:id/tv_accept");
	By msisdnenter =  By.id("com.ooredoo.selfcare:id/et_mobileno");
	By otpreq = By.id("com.ooredoo.selfcare:id/tv_getotp");
	By okalert = By.id("com.ooredoo.selfcare:id/tv_ok");
	By dbdetails = By.id("txtUserName");
	By pass = By.id("txtPassword");
	By clickbtn = By.id("btnOk");
	By drop = By.id("cmbDSNs");
	By textQuer = By.id("txtQuery");
	By show = By.id("cmdShow");
	By tabMsg = By.xpath("//td[contains(text(),'<#> ALERT Please donot share OTP to anyone,if shar')]");
	By sesiontoken = By.xpath("//*[@id=\"dbGrid\"]/tbody/tr[2]/td");
	By logout = By.id("btnLogout");
	By otpget = By.id("com.ooredoo.selfcare:id/ev_otp");
	By Notnull = By.id("com.ooredoo.selfcare:id/tv_proceed");
	By resnd =  By.id("com.ooredoo.selfcare:id/tv_resendotp");
	By conntineBtn = By.xpath("//android.widget.TextView[@resource-id='com.ooredoo.selfcare:id/btn_continue']");
	By cntuenonenglsh = By.xpath("//android.widget.TextView[@text='My Ooredoo App သို.ဆက္သြားရန္']");
	By topup =  By.id("com.ooredoo.selfcare:id/topup_icon");
	By buyback = By.id("com.ooredoo.selfcare:id/buyapack_icon");
	By playoredo = By.id("com.ooredoo.selfcare:id/valueadded_icon");
	By emergencyicon = By.id("com.ooredoo.selfcare:id/emergency_icon");
	By FirstFold = By.xpath("//android.widget.TextView[contains(@resource-id,'com.ooredoo.selfcare:id/foldText') and (@text='Win upto 100GB')]");
	By home= By.id("com.ooredoo.selfcare:id/balance_icon");	
	By buypackBuy = By.xpath("//android.widget.TextView[contains(@resource-id,'com.ooredoo.selfcare:id/tv_price')]");
	By interntclick = By.xpath("//android.widget.TextView[contains(@resource-id, 'com.ooredoo.selfcare:id/foldText') and (@text= 'Internet')]");	
	By internet = By.xpath("//androidx.viewpager.widget.ViewPager[contains(@resource-id, 'com.ooredoo.selfcare:id/vp_topup')]");
	By listofinternet = By.id("com.ooredoo.selfcare:id/rv_content_list");
	By servciestxt = By.xpath("//android.widget.TextView[@text= 'Services']");	
	By StartElement = MobileBy.AccessibilityId("balance_icon");
	By Endelement = MobileBy.name("Services");
	By hashtxtareainput = By.id("input");
	By hashtxtoutput = By.xpath("/html/body/div[2]/div[1]/div[4]/textarea");	
	By datapacksTitle =By.xpath("//android.widget.TextView[contains(@resource-id,'com.ooredoo.selfcare:id/tv_title')]");	
	By offpk = By.xpath("//*[@id='dbGrid']/tbody/tr[2]/td[1]");
	//By servciestxt = By.xpath("//android.widget.TextView[contains(text(),'Services')]");
	By serviceicons = By.xpath("//android.widget.ImageView[contains(@resource-id,'com.ooredoo.selfcare:id/ivBanner')]");
	By liveicon =  By.xpath("//android.widget.ImageView[contains(@resource-id,'com.ooredoo.selfcare:id/icon')]");
	By offrid = By.xpath("//*[@id='dbGrid']/tbody/tr[2]/td[1]");
	By offrname= By.xpath("//*[@id='dbGrid']/tbody/tr[2]/td[2]");



	public Properties propertyelements() throws IOException {
		//FileInputStream objfile = new FileInputStream(System.getProperty("user.dir") + "\\ElementProperties.properties");
		//obj.load(objfile);		
		return obj;
	}

	public Map<String, String> readPropertiesFileAsMap(String filename, String delimiter) throws Exception {
		Map<String, String> map = new HashMap<>();
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line;
		while ((line = reader.readLine()) != null) {
			if (line.trim().length() == 0)
				continue;
			if (line.charAt(0) == '#')
				continue;
			int delimPosition = line.indexOf(delimiter);
			String key = line.substring(0, delimPosition - 1).trim();
			String value = line.substring(delimPosition + 1).trim();
			map.put(key, value);
		}
		reader.close();
		return map;
	}

	public void replacemsisdn(String msisdnxml) throws IOException {
		propertyelements();

		String filePath = System.getProperty("user.dir") + "\\ElementProperties.properties";
		try {
			Map<String, String> map = readPropertiesFileAsMap(filePath, "=");		
			String msisdn = map.get("msisdno");
			//	System.out.println("msisdn = " + msisdn);
			for (String key : map.keySet()) {
				//System.out.println(key);
				//System.out.println(map.keySet());
				//System.out.println(map.get(key));
				//obj.setProperty(key, map.get(key).replace("(MSIS)", msisdn));
				obj.setProperty(key, map.get(key).replace("(MSIS)", msisdnxml));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getResult1(String passfail) throws IOException
	{
		if(passfail.equalsIgnoreCase("fail"))
		{
			String screenShotPath = capture1(passfail);
			logger.log(LogStatus.FAIL, "Snapshot below: " + logger.addBase64ScreenShot(screenShotPath));
		} else if(passfail.equalsIgnoreCase("pass")) {
			String screenShotPath = capture1(passfail);
			logger.log(LogStatus.INFO, "Snapshot below: " + logger.addBase64ScreenShot(screenShotPath));
		}
		extent.endTest(logger);
	}

	public  String capture1(String screenShotName1) throws IOException
	{
		String base64Image = null;
		TakesScreenshot ts2 = (TakesScreenshot) driver;
		File src2 = ts2.getScreenshotAs(OutputType.FILE);		
		System.out.println("Successfully captured a screenshot");	
		File destination2 = new File(System.getProperty("user.dir") + "\\PassFailPng\\" + screenShotName1 + ".png");
		FileUtils.copyFile(src2, destination2);
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(destination2);
			byte[] bytes = new byte[(int) destination2.length()];
			fileInputStream.read(bytes);
			base64Image = new String(Base64.getEncoder().encodeToString(bytes));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return "data:image/png;base64,"+base64Image; 
	}


	public List<AndroidElement> AppSameList(String element) throws Exception {		
		propertyelements();			
		WebDriverWait wait = new WebDriverWait(driver, 30);
		String eeelements = obj.getProperty(element);		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(eeelements)));		
		List<MobileElement> DisplayCheck = driver.findElements(By.xpath(eeelements));	
		MobileElement DisplayCh = driver.findElement(By.xpath(eeelements));	
		List<AndroidElement> listdisplay = new ArrayList<>();	
		if (DisplayCh.isDisplayed()) {	
			for (MobileElement DisplayChecks : DisplayCheck) {	
				//System.out.println("App Display = " + ((AndroidElement) DisplayChecks).getText());
				listdisplay.add((AndroidElement) DisplayChecks);	
			}	
			return listdisplay;	
		} else {
			logger.log(LogStatus.FAIL, "App Loading ... Exceeded limited time. Please try again later");
			return null;
		}	
	}


	public void swipeByElements() throws Exception {	
		try {
			Dimension dim = driver.manage().window().getSize();
			// Start from middle of the page
			Double ScrollheightStart = dim.getHeight() * 0.60;
			int scrollStart = ScrollheightStart.intValue();
			// scroll till 90% of the page
			Double ScrollheightEnd = dim.getHeight() * 0.4;
			int scrollEnd = ScrollheightEnd.intValue();
			int width = dim.getWidth();
			int x = width / 2;		
			new TouchAction((AndroidDriver) driver).press(PointOption.point(x, scrollStart))
			.waitAction(WaitOptions.waitOptions(Duration.ofMillis(200))).moveTo(PointOption.point(x, scrollEnd))
			.release().perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public File TakeScreenshot()
	{
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);			
		//System.out.println("scrFile ==>"+scrFile);
		return scrFile;
	}	

}
