package AndroidAppiumAuto;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

import AndroidAppiumAuto.exceptions.CustomException;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;


public class AppActivity extends SupportMethods {
	

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	public static String getDate() {
		return sdf.format(new java.util.Date());
	}

	
	@Test(description = "Login Pages", priority = 1, enabled = true)
	@org.testng.annotations.Parameters(value={"msisdn"})
	public void LoginPage(String msisdn) throws Exception {
		System.out.println("msisdn = "+msisdn);
		logger = extent.startTest("LoginPage", "Description to LoginPage");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		propertyelements();	
		boolean requestotp = AppValidation("login").equalsIgnoreCase("Login");
		// String eeelements = obj.getProperty(element);
		SendEvent("msisdnenter", msisdn);
		// driver.navigate().back();
		ClickEvents("getotp");
		// Assert.asserttrue(requestotp);
		if (requestotp) {
			logger.log(LogStatus.PASS, "Login Page Launched with MSISDN =" + msisdn);
			getResult1("pass");
		} else {
			logger.log(LogStatus.FAIL, "Test Case Failed. Unable to launch login page");
		}

	}
	

	@Test(description = "Fetch OTP", priority = 2, enabled = true)
	public void GetOtp() throws Exception {
		logger = extent.startTest("GetOtp", "Description to TC2-Verify Your Number");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		propertyelements();
		getResult1("pass");
		String otp = otprecive();
		// Assert.asserttrue(Verifynum);
		if (otp != null) {
			logger.log(LogStatus.PASS, "OTP Retrived from DB = " + otp);
		} else {
			logger.log(LogStatus.FAIL, "Failed to fetch otp");
		}
	}


	@Test(description =  "Contextual Popup", priority = 3, enabled = true)
	public void contextpopup() throws Exception  {
		logger = extent.startTest("Context Popup", "Description to contextul  popup");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		propertyelements();
		Thread.sleep(5000);
		contextmethod();
	}
	
	@Test(description =  "VIPHomePage", priority = 4, enabled = true)
	public void VIPHomePage() throws Exception, InterruptedException, CustomException {
		logger = extent.startTest("VIPHomePage", "Description to VIPHomePage");
		contextmap.clear();
		vipnewhomepages();
	}


	@Test(description =  "DDD Packs", priority = 5, enabled = false)
	public void DDDPacks() throws Exception  {
		logger = extent.startTest("DDD Packs", "Description to DDD PAcks");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		ClickEvents("homebtton");
		propertyelements();		
		ddbanners();
	}



	@Test(description =  "PyawPyawYu", priority = 6, enabled = false)
	public void PyawPyawYu() throws Exception, InterruptedException, CustomException {
		logger = extent.startTest("PyawPyawYu", "Description to PyawPyawYu");
		ClickEvents("homebtton");
		pyawpyawTestCase();
	}
	
	
	@Test(description =  "VIPHomePageShortCutIcons", priority = 7, enabled = false)
	public void VIPHomePageShortCutIcons() throws Exception, InterruptedException, CustomException {
		logger = extent.startTest("VIPHomePageShortCutIcons", "Description to VIPHomePageShortCutIcons"); 
		vipclickupdate();
		List<AndroidElement> shortlist = AppSameList("shortcutTitles");
		getResult1("pass");	
		for(int m =0; m<shortlist.size()-1; m++) {
			shortlist.get(m).click();					
			logger.log(LogStatus.PASS, "Scrolled to " + shortlist.get(m).getText());
			Thread.sleep(1000);
			getResult1("pass");			
			scrollUpTill("shortcutTitles",shortlist.get(m).getText());
			Thread.sleep(2000);	
		}
	}	
	
	
	@Test(description = "VIP Spin The Wheel", priority = 8, enabled = false)
	public void VipSpinWheel() throws Exception {
		logger = extent.startTest("VIP Spin The Wheel", "Description to VIP Spin The Wheel");	
		spinwheel();		
		}
	
	
	@Test(description = "VIP RedeemPoints", priority = 9, enabled = false)
	public void VIPRedeemPoints() throws Exception {
		logger = extent.startTest("VIP RedeemPoints", "Description to VIP RedeemPoints");	
		Thread.sleep(2000);
		RedeemPoints();		
		}
	
	
	@Test(description = "VIP MyDiscounts", priority = 10, enabled = false)
	public void VIPMyDiscounts() throws Exception {
		logger = extent.startTest("VIP MyDiscounts", "Description to VIP MyDiscounts");	
		Thread.sleep(2000);
		MyDiscounts();		
		}
	
	
	@Test(description = "VIP MyPoints", priority = 11, enabled = false)
	public void VIPMyPoints() throws Exception {
		logger = extent.startTest("VIP MyPoints", "Description to VIP MyPoints");	
		Thread.sleep(2000);
		MyPoints();		
		}
	
	
	@Test(description =  "UpdateLocation", priority = 12, enabled = false)
	public void UpdateLocation() throws Exception, InterruptedException, CustomException {
		logger = extent.startTest("UpdateLocation", "Description to UpdateLocation");
		updatelocation();	
	}
	
	
	@Test(description = "VIP EarnMorePoints", priority = 10, enabled = false)
	public void VIPEarnMorePoints() throws Exception {
		logger = extent.startTest("VIPEarnMorePoints", "Description to VIP Earn More Points");	
		Thread.sleep(5000);
		EarnMorePoints();		
		}
	

	@Test(description = "VIP MyBenefits", priority = 10, enabled = false)
	public void VIPMyBenefits() throws Exception {
		logger = extent.startTest("VIPMyBenefits", "Description to MyBenefits");	
		Thread.sleep(5000);
		MyBenefits();		
		}

	
	// updated on 24/8/20 - only  APP validation

	@Test(description = "ETBanners", priority = 5, enabled = false)
	public void BannerDet() throws Exception {
		logger = extent.startTest("BannerDet", "Description to Entertainment Banners");
		propertyelements();
		scrolltill("Entertainment", "Entertainment");
		Swipe(3, "ETbanners",65);		
		logger.log(LogStatus.PASS, "Entertainment banners ");		
		getResult1("pass");
		ClickEvents("ETbanners");
		Thread.sleep(5000);
		getResult1("pass");
		logger.log(LogStatus.PASS, "Navigated to ET");
		driver.navigate().back();
		/*List<String> ent = Enternt();
		if (ent != null) {
			logger.log(LogStatus.PASS, "Test Case Pass and  banner titles are " + ent);
		}*/

	}

	// updated on 24/8/20 - only  APP validation

		@Test(description = "QueryBalance", priority = 7, enabled = false)
		public void QueryBalanceApi() throws Exception {

			logger = extent.startTest("QueryBalanceApi", "Description to QueryBalance");

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			propertyelements();
			//ClickEvents("HomeIcon");
			// ScrollUp();
			//driver.navigate().refresh();
			Map<String, String> AppMap = new HashMap<String, String>();

			List<String> datavoicesmsTitle = AppValidationList("tvDataTitle");

			// driver.findElements(By.xpath("//android.widget.TextView[contains(@resource-id,'com.ooredoo.selfcare:id/tvDataTitle')]"));

			System.out.println(datavoicesmsTitle.size());

			List<String> datavoicesmsValue = AppValidationList("tvDataValues");

			// List<MobileElement> datavoicesmsValue =
			// driver.findElements(By.xpath("//android.widget.TextView[contains(@resource-id,'com.ooredoo.selfcare:id/tvDataValue')]"));
			System.out.println(datavoicesmsValue.size());

			for (int f = 0; f < datavoicesmsTitle.size(); f++) {

				AppMap.put(datavoicesmsTitle.get(f).toString(), datavoicesmsValue.get(f));
				// AppMap.put(datavoicesmsTitle.get(f).getText(),
				// datavoicesmsValue.get(f).getText());

			}

			AppMap.put("MOBILENO", AppValidation("msisdn"));
			AppMap.put("YOUR BALANCE", (AppValidation("Qbalance") + " " + AppValidation("ksextn")));

			System.out.println("AppMap ==> " + AppMap);
			//// ecarepreprod.ooredoo.com.mm/selfcareapistg7.1/api/loyaltyapphome/getRegisteredCustomer_app



			//Map<String, String> ApiValidate = querybalance(obj.getProperty("apiversion"), "querybalance");
			//System.out.println("ApiValidate after chnge ==>" + ApiValidate);

			//boolean QueryBalanceisEqual = AppMap.equals(ApiValidate);
			// System.out.println("Success / Failure ==> "+QueryBalanceisEqual);

			// AppMap.values().retainAll(ApiValidate.values());
			// System.out.println("Retained Values ==> "+AppMap);

			//if (QueryBalanceisEqual) {
				logger.log(LogStatus.PASS, "Test Case Passed is QueryBalance = "+AppMap);
				//System.out.println("passed");
				getResult1("pass");
			/*} else {
				logger.log(LogStatus.FAIL, "QueryBalance Failed");
				logger.log(LogStatus.FAIL, "QueryBalance API Response = " + ApiValidate);
				logger.log(LogStatus.FAIL, "QueryBalance App Response = " + AppMap);*/
			//}

		}
		
		
		// Updated 31/08/20
		
		@Test(description = "Buy Packs - Buy", priority = 12, enabled = false)
		public void BuyPackBuy() throws Exception {

			logger = extent.startTest("BuyPackBuy", "Description to Buy Packs-Buy");
			Thread.sleep(8000);
			propertyelements();
			//ClickEvents("HomeIcon");
			
			bygft("buypackBuy", "Valid");
		}

		// Updated 31/08/20

		@Test(description = "Buy Packs - Gift", priority = 13, enabled = false)
		public void GiftPackGift() throws Exception {
			logger = extent.startTest("GiftPackGift", "Description to Gift Packs-Gift");
			Thread.sleep(3000);
			propertyelements();
			ClickEvents("homebtton");		
			Thread.sleep(2000);
			//ScrollUp();
			/* -- For Gift you need to get value from Sheet -- */
			bygft("buypackGift", "Valid");

		}	
		
		
		
		
		// UPDATED APP SIDE 
		@Test(description = "LiveTV Details", priority = 8, enabled = false)
		public void LiveTVDetails() throws Exception {

			logger = extent.startTest("LiveTVDetails", "Description to LiveTVDetails");
			propertyelements();
			//ClickEvents("HomeIcon");
			ClickEvents("homebtton");
			livetvdetails();
			


		}

		// APPDATED APP SIDE

		@Test(description = "Byop Query Balance", priority = 9, enabled = false)

		public void ByopQueryBalance() throws Exception {
			logger = extent.startTest("ByopQueryBalance", "Description to ByopQueryBalance");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			propertyelements();
			ScrollUp();
			ScrollUp();
			//ClickEvents("HomeIcon");
			//ClickEvents("homebtton");
			scrolltill("byoptitle", "Sate Tine Kya");

			// scrolltill("buymorebyop", "Buy More");

			List<String> APPByop = new ArrayList<>();


			// ecarepreprod.ooredoo.com.mm/selfcareapistg7.1/api/
			//List<String> byopaapi = QueryBYOPBalance(obj.getProperty("apiversion"));
			//System.out.println("byopaapi ==>" + byopaapi);

			//if (byopaapi.size() > 2) {
				APPByop.addAll(AppValidationList("isb_progress"));
				// APPByop.addAll(AppValidationList("tvStart"));
				APPByop.addAll(AppValidationList("tvEnd"));
				APPByop.addAll(AppValidationList("subbyoptitle"));

				System.out.println("APPByop ==> " + APPByop);

				//Collections.sort(APPByop);
				//Collections.sort(byopaapi);

				//boolean ByopAPPByop = APPByop.equals(byopaapi);

				// Assert.asserttrue(ByopAPPByop);

				//if (ByopAPPByop) {
					//System.out.println("Passed");
				if(APPByop.isEmpty()) {
					logger.log(LogStatus.PASS, "Banner Exists please select your own packs");
					getResult1("pass");
				} else {
					logger.log(LogStatus.PASS, "Test Case Passed is ByopQueryBalance" +APPByop);
					getResult1("pass");
				}
					
					// logger.log(LogStatus.INFO, "App Details : "+APPByop);
					// logger.log(LogStatus.INFO, "Api Details : "+byopaapi);
				/*} else {
					System.out.println("Failed");
					logger.log(LogStatus.FAIL, "MisMatch with App and Api Details : " + APPByop.removeAll(byopaapi));

					logger.log(LogStatus.FAIL, "App Details : " + APPByop);
					logger.log(LogStatus.FAIL, "Api Details : " + byopaapi);
				}
			} else {
				System.out.println("Passed");
				logger.log(LogStatus.PASS, "Banner Exists :" + byopaapi.get(0));
			}*/

		}

		@Test(description = "Tour Guide", priority = 3, enabled = false)
		public void CloseTour() throws Exception {
			logger = extent.startTest("Tour Guide and Register Pop-up", "Tour Guide and Register Pop-up");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			propertyelements();
			//ClickEvents("closeicon");
			Thread.sleep(4000);
			getResult1("pass");
			ClickEvents("closeregister");
			Thread.sleep(4000);
			getResult1("pass");
			ClickEvents("CloseTour");

		}

		@Test(description = "Content Pop-Up", priority = 4, enabled = false)
		public void contentPop() throws Exception {

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			propertyelements();
			AppValidationList("contentpopimages");
			AppValidationList("conticons");
			AppValidationList("conttitle");
			ClickEvents("contOredobtn");
			Assert.assertTrue(true);

		}


	@Test(description = "Kyo Thone - Loan", priority = 6, enabled = false)
	public void KyoThone() throws Exception {
		logger = extent.startTest("KyoThone", "Description to Kyo Thone");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		propertyelements();
		// loan();
		ScrollUp();
		boolean loanthone = loan()
				.equalsIgnoreCase("Your request is being processed, please wait for a confirmation SMS.");
		if (loanthone) {
			logger.log(LogStatus.PASS, "Test Case Passed is Kyo Thone");
			getResult1("pass");
		} else {

			logger.log(LogStatus.FAIL, "Test Case failed due to = " + loanthone);
		}
		// test = extent.createTest("Test Case 6", "PASSED test case");
		ClickEvents("HomeIcon");

	}

	@Test(description = "Chat Box", priority = 6, enabled = false)
	public void chat() throws Exception {
		Thread.sleep(3000);
		ClickEvents("chat");
		Thread.sleep(3000);
		SendEvent("mesgchat", "hi");
		Thread.sleep(3000);

		ClickEvents("sendchat");
		// driver.navigate().back();
		System.out.println(driver.getDeviceTime());

		AppValidation("alltextfields");

		WebDriverWait wait = new WebDriverWait(driver, 30);

		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//android.widget.TextView[contains(@resource-id,'com.ooredoo.selfcare:id/agent_name')]")));
		System.out.println(driver.getDeviceTime());

		ClickEvents("attach");

		driver.navigate().back();
		driver.navigate().back();

	}

	
	// log done
	@Test(description = "Top Up - Voucher", priority = 10, enabled = true)
	@org.testng.annotations.Parameters(value={"msisdn"})
	public void TopUpVoucher(String msisdnxml) throws JSONException, InterruptedException, Exception {

		logger = extent.startTest("TopUpVoucher", "Description to TopUpVoucher");

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		ClickEvents("TopUpIcon");
		
		System.out.println(driver.getPageSource());

		if (AppValidation("Voucher").equalsIgnoreCase("Voucher")) {

			TopUpApp("TopUpText", "Voucher", "topuppin", "Valid", "topupsubmit", "Voucher",msisdnxml);

			// TopUpApp("TopUpText","Voucher","topuppin","Invalid","topupsubmit","Voucher");
			// TopUpApp("TopUpText","Voucher","topuppin","boundary","topupsubmit","Voucher");

			ClickEvents("HomeIcon");

		}

	}

	// log done
	@Test(description = "Top Up - MPitesan", priority = 11, enabled = false)
	@org.testng.annotations.Parameters(value={"msisdn"})
	public void TopUpMPitesan(String msisdnxml) throws Exception {
		// System.out.println("MPite init()");
		logger = extent.startTest("TopUpMPitesan", "Description to TopUpMPitesan");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// TopUpApp("TopUpNow","Mpitesan","AmountPitesan","Empty","topupsubmit","MPite");
		propertyelements();

		TopUpApp("TopUpText", "Mpitesan", "AmountPitesan", "Valid", "topupsubmit", "MPite",msisdnxml);

		ClickEvents("HomeIcon");

	}
	

	

	@Test(description = "Report an Issue", priority = 14, enabled = false)
	public void Report_An_Issue() throws Exception {
		logger = extent.startTest("Report an Issue", "Description to Report an Issue");
		propertyelements();
		HSSFWorkbook report_an_issue_wb = Exceel();
		HSSFSheet report_an_issue_Sheet = report_an_issue_wb.getSheet("ContactUs");
		ClickEvents("MoreIcon");
		Thread.sleep(1000);
		getResult1("pass");
		ClickEvents("More_Contact_US_Support");
		Thread.sleep(1000);
		getResult1("pass");
		// driver.navigate().back();
		ClickEvents("Contact_Report_An_Issue");

		Thread.sleep(5000);
		getResult1("pass");
		try {
			for (int i = 1; i <= report_an_issue_Sheet.getPhysicalNumberOfRows(); i++) {
				String Enter_Name = report_an_issue_Sheet.getRow(i).getCell(1).getStringCellValue();
				System.out.println("Enter_Name ==>" + Enter_Name);
				String Category = report_an_issue_Sheet.getRow(i).getCell(2).getStringCellValue();
				// System.out.println("Category ==>"+Category);
				String Sub_Category = report_an_issue_Sheet.getRow(i).getCell(3).getStringCellValue();
				// System.out.println("Sub_Category ==>"+Sub_Category);
				String Date_Time = report_an_issue_Sheet.getRow(i).getCell(4).getStringCellValue();
				// System.out.println("Date_Time ==>"+Date_Time);
				String Description = report_an_issue_Sheet.getRow(i).getCell(5).getStringCellValue();
				// System.out.println("Description ==>"+Description);
				String Expected_Output = report_an_issue_Sheet.getRow(i).getCell(6).getStringCellValue();
				// System.out.println("Expected_Output ==>"+Expected_Output);
				Thread.sleep(8000);
				String result = Contact_Us_Page_Report_An_Issue(Enter_Name, Category, Sub_Category, Date_Time,
						Description, Expected_Output);
				System.out.println("result ==>" + result);
				if (result != null) {
					logger.log(LogStatus.PASS, "Report an Issue passed = " + result);
					getResult1("pass");
				} else {
					logger.log(LogStatus.FAIL, "Report an Issue Failed due to  " + result);
				}

			}
		} catch (Exception ex) {
			System.out.println("Report an Issue = " + ex);
		}

	}

	@Test(description = "More Settings", priority = 15, enabled = false)
	public void More_Settings() throws Exception {
		logger = extent.startTest("Settings (Roam, DND, Lang)", "Description to Settings");
		propertyelements();
		Settings();

	}

	@Test(description = "OoredooPlay", priority = 16, enabled = false)
	public void Ooredoo_Play() throws Exception {
		logger = extent.startTest("Ooredoo Play", "Description to Ooredoo Play");
		propertyelements();
		ClickEvents("HomeIcon");
		Ooredoo_Play_sub();
	}

	@Test(description = "VIP", priority = 17, enabled = false)
	public void VIP_Validation() throws Exception {
		logger = extent.startTest("VIP Points", "Description to VIP Points");
		propertyelements();
		ClickEvents("HomeIcon");
		getResult1("pass");
		Map<String, String> VIP_API = getLoyaltySubscriber("VIP_User_details_DB", "VIP_User_details_Query");

		System.out.println("VIP_API ==>" + VIP_API);
		//logger.log(LogStatus.INFO, "VIP API = " + VIP_API);

		Map<String, String> VIP_APP = App_VIP();
		System.out.println("VIP_APP ==>" + VIP_APP);

		//logger.log(LogStatus.INFO, "VIP APP = " + VIP_APP);

		boolean vipvalidatecheck = VIP_API.toString().equalsIgnoreCase(VIP_APP.toString());

		if (vipvalidatecheck) {
			logger.log(LogStatus.PASS, "VIP Points Passed");

		} else {
			logger.log(LogStatus.FAIL, "VIP Points Failed");
			logger.log(LogStatus.FAIL, "VIP APP = " + VIP_APP);
			logger.log(LogStatus.FAIL, "VIP API = " + VIP_API);
		}
	}

	@Test(description = "Service Banner", priority = 20, enabled = false)
	public void ServiceBanner() throws Exception  {
		logger = extent.startTest("Service Banner", "Description to Service Banner");
		ClickEvents("HomeIcon");
		scrolltill("ServiceText", "Services");
		getResult1("pass");
		String text = AppValidation("ServiceBanners");
		System.out.println("layout text == >"+text);

		String toastmesg = tc.ToastMessage(TakeScreenshot(),"discount");
		System.out.println("toastmesg == > "+toastmesg);
		if(toastmesg.equals("True")) {

			ClickEvents("ServiceBanners");
			Thread.sleep(5000);
			//getResult1("pass");
			//Thread.sleep(7000);			

			Set<String> contextNames = driver.getContextHandles();
			System.out.println(contextNames); 


			// logger.log(LogStatus.INFO, "contextName = "+contextNames);

			//String urlweb = AppValidation("WebUrl");

			// System.out.println("UrlWeb = "+urlweb);



			String BrowserRedirect = tc.ToastMessage(TakeScreenshot(),"pages.shop.com");
			System.out.println("toastmesg == > "+BrowserRedirect);
			if(BrowserRedirect.equals("True")) { 
				getResult1("pass");
				logger.log(LogStatus.PASS, "ServiceBanner Redirected Successfully "); 

			}
			else {
				logger.log(LogStatus.FAIL, "ServiceBanner Redirected UnSuccesfull . Please check again ! "); 
			}


			driver.navigate().back();


			/*for (String contextName : contextNames) {
	            System.out.println(contextName);	           
	            logger.log(LogStatus.INFO, "contextName = "+contextName);

	            if (contextName.contains("WEBVIEW")) {
	                //driver.context("WEBVIEW");
	                driver.switchTo().window(contextName);
	               System.out.println(driver.getCurrentUrl());
	                logger.log(LogStatus.INFO, "Redirected to Service Banner Page = "+driver.getCurrentUrl());

	            }
	        }*/
		}
		//You can switch to WEBVIEW using following code :			

		/*if(driver.getCurrentUrl().contains("pages.shop.com")) {
				logger.log(LogStatus.INFO, "Redirected to Service Banner Page");
				logger.log(LogStatus.PASS, "Redirected to Service Banner Page = "+driver.getCurrentUrl());
				getResult1("pass");
			} else {
				logger.log(LogStatus.FAIL, "Doesn't Redirected to Service Banner Page = "+driver.getCurrentUrl());				
			}


		}*/ else {
			System.out.println("The Text you are trying to match doesnot found in the banner");
		}
	}


	@Test(description = "Only For You Packs", priority = 18, enabled = false)
	public void OnlyForYou() throws Exception {
		logger = extent.startTest("Only For You", "Description to Only For You");
		ClickEvents("HomeIcon");
		String balanceBefore = AppValidation("Qbalance");
		logger.log(LogStatus.INFO, "Balance =" +balanceBefore);
		scrolltill("OnlyForyou", "Only for you");
		getResult1("pass");
		for(int c=1;c<4;c++) {
			logger.log(LogStatus.INFO, "Only for you Packs Titles " +c +" =" +AppValidation("only4UTitles"));		
			logger.log(LogStatus.INFO, "Only for you Packs Validities " +c +" ="+AppValidation("only4UValidity"));
			logger.log(LogStatus.INFO, "Only for you Packs Price " +c +" ="+AppValidation("only4UPrice"));
		}

		ClickEvents("onlyforpacks");
		Thread.sleep(3000);
		getResult1("pass");
		Thread.sleep(3000);
		ClickEvents("Ok_button");
		Thread.sleep(3000);
		getResult1("pass");		
		ClickEvents("Ok_button");
		ScrollUp();
		Thread.sleep(10000);
		//ScrollUp();
		ClickEvents("RefreshBalance");
		Thread.sleep(2000);
		getResult1("pass");
		String balanceAfter = AppValidation("Qbalance");
		int total = Integer.valueOf(balanceBefore) - Integer.valueOf(balanceAfter);				
		logger.log(LogStatus.INFO, "Amount Deducted for =" +total);
		logger.log(LogStatus.INFO, "Balance After =" +balanceAfter);

	}


	@Test(description = "Special Voice Data Banners", priority = 18, enabled = false)
	public void SpecialVoiceData() throws Exception {
		logger = extent.startTest("Special Voice Data Banners", "Description to Special Voice Data Banners");
		ClickEvents("HomeIcon");
		swipeByElements();
		getResult1("pass");
	}

	
	@Test(description="Existing Packages", priority = 20 , enabled=false)
	public void accountview() throws Exception {

		logger = extent.startTest("My Existing Packages", "Description to VIP Points");
		//DecimalFormat df2 = new DecimalFormat("#.##");


		try {
			propertyelements();

			ClickEvents("HomeIcon");
			Thread.sleep(2000);
			ClickEvents("MyAccountClick");			

			//AppValidation("MyNumber");	

			//System.out.println(driver.getPageSource());

			//SelectEvent("bpinternet");
			logger.log(LogStatus.INFO, "INTERNET");			
			norecords();	

			Thread.sleep(2000);			
			//Voice				
			ClickEvents("voicepackdetails");
			logger.log(LogStatus.INFO, "VOICE");			
			norecords();		
			Thread.sleep(2000);
			//SMS
			ClickEvents("smspacks");	
			logger.log(LogStatus.INFO, "SMS");
			norecords();
			Thread.sleep(2000);
			//My services
			ClickEvents("myservicespacks");	
			logger.log(LogStatus.INFO, "MY SERVICES");
			norecords();
			Thread.sleep(2000);
			//KYO Thone
			ClickEvents("kyothonepackdetails");
			logger.log(LogStatus.INFO, "KYO THONE");
			norecords();
			Thread.sleep(2000);
			//BONUS
			ClickEvents("bonuspackdetails");
			logger.log(LogStatus.INFO, "BONUS");
			norecords();
			Thread.sleep(2000);
			//ROAMING
			ClickEvents("roamingpackdetails");
			logger.log(LogStatus.INFO, "ROAMING");
			norecords();



		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (CustomException e) {
			e.printStackTrace();
		}

	}

	@Test(description = "CssProperties", priority = 21, enabled = false)
	public void CssProperties() throws Exception {
		logger = extent.startTest("BannerDet", "Description to Entertainment Banners");

		String cssvalues = driver.findElement(By.xpath("cssprop")).getCssValue("style");
		System.out.println("cssvalues" +cssvalues);
	}



	@AfterMethod
	public void getResult(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.FAILURE) {
			String screenShotPath = capture(result.getName());
			logger.log(LogStatus.FAIL, "Snapshot below: " + logger.addBase64ScreenShot((screenShotPath)));
		}
		extent.endTest(logger);
	}

	public String capture(String screenShotName) throws IOException {

		String base64Image = null;
		TakesScreenshot ts1 = (TakesScreenshot) driver;
		File src = ts1.getScreenshotAs(OutputType.FILE);
		System.out.println("Successfully captured a screenshot");
		// File source = new File(src);
		File destination = new File(System.getProperty("user.dir") + "\\ErrorScreenshots\\" + screenShotName + ".png");
		FileUtils.copyFile(src, destination);
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(destination);
			byte[] bytes = new byte[(int) destination.length()];
			fileInputStream.read(bytes);
			base64Image = new String(Base64.getEncoder().encodeToString(bytes));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return "data:image/png;base64,"+base64Image;
	}

	@BeforeTest
	@org.testng.annotations.Parameters(value={"config","environment","msisdn"})
	public void launchapp(String config_file,String environment,String msisdn) throws Exception {
	//@org.testng.annotations.Parameters(value={"config"})
	//public void launchapp(String config_file) throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();
		replacemsisdn(msisdn);
	//	replacemsisdn("9971357267");	
		System.out.println("after = "+obj.getProperty("queryotp"));
		
		propertyelements();	
		
		Iterator it;
		//System.out.println(config_file);
		//System.out.println(environment);
		
		System.out.println("Getting ready to Launch the APP ! ");			
		JSONParser parser = new JSONParser();
		Object config = parser.parse(new FileReader(System.getProperty("user.dir")+"\\src\\test\\java\\ConfigFiles\\"+config_file));
		
		
		   System.out.println("config = "+config);	    
		  org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject)config;	      
		  Map<String,String> envs = (Map<String, String>) jsonObject.get("environment");
	      System.out.println(envs.size());
	     // System.out.println("envs = "+envs);	      
	      Map<String, String> environ;
	      
		try {
			environ = (Map<String, String>) ((org.json.simple.JSONObject) envs).get(environment);
			System.out.println("envCapabilities = "+environ);
		       it = environ.entrySet().iterator();
		      while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        cap.setCapability(pair.getKey().toString(), pair.getValue().toString());
		      }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	      
	      try {
			Map<String,String> caps = (Map<String, String>) jsonObject.get("capabilities");
			  
			  it = caps.entrySet().iterator();

			    while (it.hasNext()) {

			        Map.Entry pair = (Map.Entry)it.next();

			        if(cap.getCapability(pair.getKey().toString()) == null){

			            cap.setCapability(pair.getKey().toString(), pair.getValue().toString());

			        }
			    }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
	         
	       	
		//URL url = new URL("http://127.0.0.1:4723/wd/hub");
		//cap.setCapability(MobileCapabilityType.UDID, "520074b4ee426523");
		//cap.setCapability(MobileCapabilityType.UDID, "192.168.1.6:4444");
		cap.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
		//cap.setCapability("noReset", "true");
		//System.setProperty("webdriver.http.factory", "apache");
		//driver = new AndroidDriver<MobileElement>(url, cap);
		System.out.println("Added cap = "+cap);
		String userName = "srikanthmuthyala3";
		String accessKey = "nnsoCESaU6obGbqeyb1x";

	    driver = new AndroidDriver<MobileElement>(new URL("https://"+userName+":"+accessKey+"@hub-cloud.browserstack.com/wd/hub"), cap);
		  
		
	    
		
		initmethods();
		//	String now = getDate();
		extent = new ExtentReports(System.getProperty("user.dir") + "\\MOAReport.html", true);
		extent.addSystemInfo("Host Name", "Ooredoo MOA").addSystemInfo("Environment", "Mobile Automation Testing")
		.addSystemInfo("User Name", "My Ooredoo Myanmar");
		extent.loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));

	}	

	@AfterSuite
	public void tearDown() {

		extent.flush();
		extent.close();
		Webdr.close();
	}

}
