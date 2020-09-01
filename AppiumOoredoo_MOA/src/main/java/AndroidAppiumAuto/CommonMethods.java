package AndroidAppiumAuto;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Array;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.relevantcodes.extentreports.LogStatus;
import AndroidAppiumAuto.exceptions.CustomException;
import ImageCaptureToast.ToastCapture;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class CommonMethods extends ByDeclarations {

	Map<String, String> offDb = new HashMap<String, String>();
	WebDriver Webdr;
	protected ToastCapture tc = new ToastCapture();
	List<String> apioffid = new ArrayList<String>();
	int[] x = new int[] { 31, 52 };
	

	public void initmethods() {

		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\rajesh.k\\Desktop\\OoredooCode\\chrome84driver\\chromedriver.exe");
		Webdr = new ChromeDriver();

	}

	public Map<String, String> offsetnameid() {

		offDb.clear();
		try {
			if (Webdr.findElement(By.xpath("//*[@id='dbGrid']/tbody/tr")).isDisplayed()) {
				int dbtable = Webdr.findElements(By.xpath("//*[@id='dbGrid']/tbody/tr")).size();
				for (int i = 2; i <= dbtable; i++) {

					String msgKey = Webdr.findElement(By.xpath("//*[@id='dbGrid']/tbody/tr[" + i + "]/td[1]"))
							.getText();
					String idValue = Webdr.findElement(By.xpath("//*[@id='dbGrid']/tbody/tr[" + i + "]/td[2]"))
							.getText();
					offDb.put(msgKey, idValue);
					System.out.println("offDb ==> in loop ==" + offDb);

				}

			} else {
				logger.log(LogStatus.FAIL,
						"Unable to fetch details from DB - Chrome Browser Issue. Please try again !");
				System.out.println("Unable to fetch details from DB - Chrome Browser Issue. Please try again ");
			}
		} catch (NoSuchElementException e) {
			// TODO Auto-generated catch block
			logger.log(LogStatus.FAIL, "Unable to fetch details from DB - Chrome Browser Issue. Please try again");
			e.getMessage();
		}

		System.out.println("offDb ==>" + offDb);
		return offDb;

	}

	public void ClickEvents(String clickeve) throws IOException, InterruptedException, CustomException {

		propertyelements();
		try {
			
			String PathCheck3 = obj.getProperty(clickeve);
			
			System.out.println(PathCheck3);
		
			WebDriverWait wait = new WebDriverWait(driver, 20);
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PathCheck3)));

			} catch (Exception ex) {
				throw new CustomException(2000,
						"The Element " + clickeve + " you are trying to click is failed , Please try again !");
			}
			if ((driver.findElement(By.xpath(PathCheck3)).isDisplayed())) {

				driver.findElement(By.xpath(PathCheck3)).click();
				// System.out.println("click ok == >"+PathCheck3);
			} else {

				logger.log(LogStatus.FAIL, "Unable to Click Element. Please try again later..");
			}

		} catch (CustomException e) {
			System.out.println("Unable to find " + clickeve + " to click !" + e);

			logger.log(LogStatus.FAIL, "Exception occured: " + e.getMessage());
			throw new CustomException(e.getCode(), e.getMessage());

		}

	}
	
	public void SelectEvent(String selectevent) throws CustomException, Exception {
		propertyelements();
try {
			
			String SelectCheck = obj.getProperty(selectevent);
		
			WebDriverWait wait = new WebDriverWait(driver, 30);
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SelectCheck)));

			} catch (Exception ex) {
				throw new CustomException(2000,
						"The Element " + selectevent + " you are trying to Select could not found in given period ! Please try again !");
			}
			
		if(driver.findElement(By.xpath(selectevent)).isEnabled() || driver.findElement(By.xpath(selectevent)).isSelected()) {
			
			//driver.findElement(By.xpath(selectevent)).click();
			System.out.println("Element Already Selected");
			return;
		}
		}  catch (CustomException e) {
			System.out.println("Unable to find " + selectevent + " to Select !" + e);

			logger.log(LogStatus.FAIL, "Exception occured: " + e.getMessage());
			throw new CustomException(e.getCode(), e.getMessage());

		}
	}

	public String AppValidation(String elements) throws IOException, CustomException {

		propertyelements();
		// Thread.sleep(1000);
		try {
			String PathCheck2 = obj.getProperty(elements);
			// System.out.println("PathCheck2 ==>"+PathCheck2);
			MobileElement DisplayCheck = null;
			try {
				DisplayCheck = (MobileElement) driver.findElement(By.xpath(PathCheck2));
			} catch (Exception ex) {
				throw new CustomException(500,
						"The Element " + elements + " could not find from the application please try again !");
			}

			if (DisplayCheck.isDisplayed()) {

				// System.out.println(elements+" = " + DisplayCheck.getText());

				String appcheck = DisplayCheck.getText();
				// System.out.println("appcheck ==>"+appcheck);
				
				return appcheck;

			} else {
				throw new CustomException(500, "Element could not find from the application please try again !");
			}
		} catch (CustomException e) {
			//logger.log(LogStatus.INFO, "Exception occured: " + e.getMessage());
			throw new CustomException(e.getCode(), e.getMessage());
			// logger.log(LogStatus.FAIL, "Element could not found ! To String Method = "
			// +e.toString());
			// logger.log(LogStatus.FAIL, "Element could not found ! Print Stack Trace = "
			// +e.printStackTrace());

		}
	}

	public String AppValidationInLoop(String elements) throws IOException, CustomException {

		propertyelements();
		// Thread.sleep(1000);
		try {
			String PathCheck2 = obj.getProperty(elements);

			MobileElement DisplayCheck = null;
			try {
				DisplayCheck = (MobileElement) driver.findElement(By.xpath(PathCheck2));
			} catch (Exception ex) {
				throw new CustomException(500, elements + " Please try again !");
			}

			if (DisplayCheck.isDisplayed()) {

				// System.out.println(elements+" = " + DisplayCheck.getText());

				String appcheck = DisplayCheck.getText();
				// System.out.println("appcheck ==>"+appcheck);
				return appcheck;

			} else {
				throw new CustomException(500, "Element could not find from the application please try again !");
			}
		} catch (CustomException e) {
			// logger.log(LogStatus.FAIL, "Exception occured: " + e.getMessage());
			throw new CustomException(e.getCode(), e.getMessage());
			// logger.log(LogStatus.FAIL, "Element could not found ! To String Method = "
			// +e.toString());
			// logger.log(LogStatus.FAIL, "Element could not found ! Print Stack Trace = "
			// +e.printStackTrace());

		}
	}

	public List<String> PacksDetails(String packs) throws Exception {

		String PathCheck4 = obj.getProperty(packs);
		List<MobileElement> BuypackDatas = (List<MobileElement>) driver.findElements(By.xpath(PathCheck4));
		List<String> webList = new ArrayList<String>();
		for (MobileElement BuypackData : BuypackDatas) {
			webList.add(BuypackData.getText());
		}
		return webList;
	}

	public String BuyGftSuccess(String packinbuypk, String selectPack, String buygiftoption, String BpartyNum,String BeforeksBalance)
			throws Exception {

		propertyelements();
		//ClickEvents("HomeIcon");
		//String BeforeksBalance = AppValidation("Qbalance").replaceAll("[^0-9]", "");

		//System.out.println("BeforeksBalance ==> " + BeforeksBalance);
		//Thread.sleep(2000);
		//ClickEvents("Buypackicon");
		//ClickEvents(selectPack);
		//ClickEvents(packinbuypk);
		setOffers();

		if (PacksDetails("tvtitle").get(0) != null && AppValidation(buygiftoption).trim().equalsIgnoreCase("Buy")) {

			ClickEvents(buygiftoption);

			String mess = driver.findElement(By.xpath(obj.getProperty("buypkpopmesg"))).getText();

			String[] arr = mess.split(" ");
			int i = 0;
			String finalVal = "";
			for (String token : arr) {
				if (token.equalsIgnoreCase("ks") || token.equalsIgnoreCase("ks.")) {
					finalVal = arr[i - 1];
					break;
				}
				if (token.toLowerCase().contains("ks")) {
					finalVal = token;
					break;
				}
				i++;
			}

			System.out.println("finalVal of Ks Value ==>" + finalVal);
			/*
			 * if(finalVal.isEmpty()) {
			 * 
			 * logger.log(LogStatus.PASS, "Test Case Passed is Internet Buy");
			 * 
			 * logger.log(LogStatus.FAIL, "Test Case (Internet Buy) Status is passed"); }
			 */

			String messKs = finalVal.replaceAll("[^0-9]", "");

			Thread.sleep(3000);

			if (Integer.valueOf(BeforeksBalance) > Integer.valueOf(messKs)) {

				System.out.println("Low Balance ! Please Top Up");
			}

			ClickEvents("buynowok");

			String TopUp = AppValidation("buypkpopmesg");

			ClickEvents("buynowok");

			return TopUp;

		} else if (AppValidation(buygiftoption).trim().equalsIgnoreCase("Gift")) {
			System.out.println("Entered GIft");
			ClickEvents(buygiftoption);
			SendEvent("bpartynum", mpinvalue(BpartyNum, "GiftSheet"));

			ClickEvents("giftproceed");
			// String validatio = AppValidation("buypkpopmesg");
			// System.out.println("validatio ==>"+validatio);

			ClickEvents("buynowok");
			String validatio1;

			validatio1 = AppValidation("buypkpopmesg");

			if (validatio1.toLowerCase().trim().contains("low balance")) {
				ClickEvents("buynowok");
				Thread.sleep(2000);
				if (AppValidation("TopUpText").contains("Top Up")) {
					ClickEvents("Buypackicon");
				}

			}

			else {
				ClickEvents("buynowok");
			}

			/*
			 * else if (validatio1.trim().
			 * equals("Invalid B Party number. Please enter valid B party number")) {
			 * Thread.sleep(2000); ClickEvents("buynowok"); } else if
			 * (validatio1.toLowerCase().trim().equalsIgnoreCase("Process Gift failure")) {
			 * System.out.println("Gift Process Failed"); ClickEvents("buynowok"); } else if
			 * (validatio1.toLowerCase().trim().
			 * equalsIgnoreCase("please enter mobile number")) {
			 * System.out.println("Gift Process Failed"); ClickEvents("buynowok"); } else if
			 * (validatio1.toLowerCase().trim().
			 * equalsIgnoreCase("Aparty and Bparty numbers are same")) {
			 * System.out.println("Aparty and Bparty numbers are same");
			 * ClickEvents("buynowok"); } else if (validatio1.toLowerCase().trim()
			 * .equalsIgnoreCase("Invalid recipient number. Please enter valid recepient number"
			 * )) { System.out.println("Invalid recipient number"); ClickEvents("buynowok");
			 * 
			 * } else if (validatio1.toLowerCase().trim().
			 * equalsIgnoreCase("You are not eligible to gift a pack.")) {
			 * System.out.println("You are not eligible to gift a pack.");
			 * ClickEvents("buynowok"); }
			 */

			return validatio1;

		} else {
			// driver.findElement(buypackBuy).click();
			return "No Data is available !";

		}

	}

	public void ScrollUp() throws Exception {
		Thread.sleep(3000);
		// To get dimensions of page
		try {
			Dimension dim = driver.manage().window().getSize();
			// Start from middle of the page
			Double ScrollheightStart = dim.getHeight() * 0.4;
			int scrollStart = ScrollheightStart.intValue();

			// scroll till 90% of the page
			Double ScrollheightEnd = dim.getHeight() * 0.9;
			int scrollEnd = ScrollheightEnd.intValue();
			int width = dim.getWidth();
			int x = width / 2;

			new TouchAction((AndroidDriver) driver).press(PointOption.point(x, scrollStart))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(200))).moveTo(PointOption.point(x, scrollEnd))
					.release().perform();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void setOffers() throws Exception {

		// ScrollUp();
		apioffid.clear();
		List<String> datatitle = PacksDetails("tvtitle");
		System.out.println("Set Offers titles ==> " + datatitle);
		System.out.println("datatitle.get(0) ==>" + datatitle.get(0));
		System.out.println("datatitle.get(1) ==>" + datatitle.get(1));
		String data1 = datatitle.get(0).replaceAll("[^a-zA-Z0-9]", ""); // 950 MB for 999 Ks
		// 950MBforonly999Ks
		for (String offkey : offDb.keySet()) {
			if (offkey.replaceAll("[^a-zA-Z0-9]", "").equalsIgnoreCase(data1.replaceAll("[^a-zA-Z0-9]", ""))) {
				String offidvalue = offDb.get(offkey);
				System.out.println("offidvalue ==> " + offidvalue);
				apioffid.add(offidvalue.replaceAll("[^a-zA-Z0-9]", ""));
			}
		}
	}

	
	public void dbdetails(String dropdwn, String querypk) throws Exception {
		
		
		//System.out.println("Db Detils page eneterd");
		propertyelements();

		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		Webdr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);	

		Webdr.get("http://ozonecms.ooredoo.com.mm/getdbdetails/");
		
		//System.out.println("Db Detils page after url called");

		Webdr.findElement(dbdetails).sendKeys("dbdetails");

		Webdr.findElement(pass).sendKeys("db@imi@123");

		Webdr.findElement(clickbtn).click();

		Thread.sleep(1000);

		WebElement drp = Webdr.findElement(drop);

		Select selectablename = new Select(drp);

		selectablename.selectByVisibleText(obj.getProperty(dropdwn));

		Actions act = new Actions(Webdr);

		WebElement getqeury = Webdr.findElement(textQuer);

		act.moveToElement(getqeury).click().sendKeys(obj.getProperty(querypk)).build().perform();

		WebElement showclick = Webdr.findElement(show);

		showclick.click();
		//Thread.sleep(3000);
		// Webdr.close();
	}

	public Map<String, String> readexcel(String sheetname) throws IOException {

		File file = new File("C:\\Users\\rajesh.k\\Desktop\\testooredoxls.xls");

		FileInputStream inputStream = new FileInputStream(file);

		Workbook OoredoWorkbook = new HSSFWorkbook(inputStream);

		Sheet OoredoSheet = OoredoWorkbook.getSheet(sheetname);

		int rowCount = OoredoSheet.getLastRowNum() - OoredoSheet.getFirstRowNum();

		System.out.println("rowCount ==> " + rowCount);

		Map<String, String> mpinsheet = new HashMap<String, String>();

		for (int i = 1; i < rowCount; i++) {	
			String Key = OoredoSheet.getRow(i).getCell(0).toString();
			String Value = OoredoSheet.getRow(i).getCell(1).toString();
			mpinsheet.put(Key, Value);
		}
		return mpinsheet;
	}
	

	public String mpinvalue(String TopSendPin, String sheetname) throws IOException, Exception {

		List<String> Topsend = new ArrayList<>();
		System.out.println("Sheet Name: " + sheetname);
		Map<String, String> excelreadpin = readexcel(sheetname);
		System.out.println("Sheet Data: " + excelreadpin);
		for (String key : excelreadpin.keySet()) {
			if (key.equalsIgnoreCase(TopSendPin)) {
				String TopSendPinstore = excelreadpin.get(key);
				System.out.println("TopSendPinstore ==> " + TopSendPinstore);
				Topsend.add(TopSendPinstore);
			}
		}
		return Topsend.get(0);
	}
	

	public void SendEvent(String SendEvents, String InputSendKey) throws IOException, Exception {
		
		propertyelements();		
		String SendPath = obj.getProperty(SendEvents);
			try {
			if ((driver.findElement(By.xpath(SendPath)).isDisplayed())) {

				driver.findElement(By.xpath(SendPath)).clear();
				driver.findElement(By.xpath(SendPath)).sendKeys(InputSendKey);
			} else {
				logger.log(LogStatus.FAIL, "Unable to Send Request/ Element not found ");
				System.out.println("Element not found");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.log(LogStatus.FAIL, "Unable to Send Request/ Element not found ");
			System.out.println("Element not found");
		}

	}
	

	public String otprecive() throws Exception {
		propertyelements();		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		dbdetails("Dropdown1", "queryotp");
		WebElement msgtab = Webdr.findElement(sesiontoken);
		String getmsgotp = msgtab.getText();
		String gotintotp = getmsgotp.replaceAll("[^0-9]", "");
		String getotp = gotintotp.substring(0, 6);
		Webdr.findElement(logout).click();
		System.out.println("otp is = " + getotp);
		Thread.sleep(2000);
		SendEvent("OTPfield", getotp);
		if (getotp != null) {
			driver.navigate().back();
		} else {
			ClickEvents("ResendOTP");			
		}	
		return getotp;
	}
	
	
	String Exceel_Path;
	public HSSFWorkbook Exceel() throws Exception {
		Exceel_Path = obj.getProperty("Exceel_Physical_Path");
		FileInputStream fis = new FileInputStream(Exceel_Path);
		HSSFWorkbook workbook = new HSSFWorkbook(fis);
		return workbook;
	}

	public void ExceelOutput(HSSFWorkbook workbook) throws Exception {
		Exceel_Path = obj.getProperty("Exceel_Physical_Path");
		FileOutputStream fos = new FileOutputStream(Exceel_Path);
		workbook.write(fos);
	}
	
	public String ksvalue() throws IOException, CustomException {
		String mess = AppValidation("buypkpopmesg");		
		String[] arr = mess.split(" ");
		int i = 0;
		String finalVal = "";
		for (String token : arr) {
			if (token.equalsIgnoreCase("ks") || token.equalsIgnoreCase("ks.")) {
				finalVal = arr[i - 1];
				break;
			}
			if (token.toLowerCase().contains("ks")) {
				finalVal = token;
				break;
			}
			i++;
		}
		System.out.println("finalVal of Ks Value ==>" + finalVal);
		String messKs = finalVal.replaceAll("[^0-9]", "");
		return messKs;
	}
	
	
	public void scrolltill(String ActualApp, String Expected) throws Exception {

		
		for (int i = 0; i < 5; i++) {
			swipeByElements();
			System.out.println("Search attempts - "+i);
			try {
				if (AppValidation(ActualApp).equalsIgnoreCase(Expected)) {
					System.out.println(Expected +" found in " + i + " scroll");					
					break;
				}  else {
					System.out.println("No Such Details available");
					break;
				}
			} catch (CustomException e) {
				//logger.log(LogStatus.INFO, " Could not find element in " + i + " attempt " + e.getMessage());
			}
		}

	}
	
	
	public void swipeBycoordinates(int xaxis, int yaxis, int endxaxis,int numoftimes) {
		
		for(int map=1; map<=numoftimes;map++) {
		int anchor = (int) (xaxis); // x-axis
		int startPoint = (int) (yaxis); // y-axis
		int endPoint = (int) (endxaxis); // x-axis end		
		TouchAction tou = new TouchAction((AndroidDriver) driver);		
			tou.press(PointOption.point(anchor, startPoint))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(3000)))
					.moveTo(PointOption.point(endPoint, startPoint)).release().perform(); 
		}	
	}
	
	
	public void updatelocation() throws Exception {
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		//ClickEvents("closeicon");
		Thread.sleep(2000);
		ClickEvents("homebtton");
		ClickEvents("MoreIcon");
		getResult1("pass");
		ClickEvents("SetLocation");
		Thread.sleep(2000);
		getResult1("pass");	
		ClickEvents("ChangeYourLocationbutton");
		Thread.sleep(2000);
		getResult1("pass");			
		swipeBycoordinates(608,1010,70,2);			
		ClickEvents("locationsetnew");
		getResult1("pass");
		ClickEvents("updatelocationbutton");
		Thread.sleep(1000);
		getResult1("pass");
		String mesesage = AppValidation("subscribe_confirm_pop_up1");
		System.out.println(mesesage);
		Thread.sleep(2000);
		if(mesesage.equalsIgnoreCase("Your new home location successfully updated")) 
			{			
			logger.log(LogStatus.INFO, "Success Message ="+mesesage);
			}			
		ClickEvents("confirmbutton");
		Thread.sleep(1000);			
	}
	
	
	public String imagedimesions(String dim) {					
		String dimen = driver.findElement(By.xpath(obj.getProperty(dim))).getSize().toString();	
		if(dimen.isEmpty()) {
			logger.log(LogStatus.FAIL, "No Image/Banner/Icon is available");
		} else {
			logger.log(LogStatus.PASS, "Dimensions of Image/Banner/Icon = "+dimen);
		}
		
		return dimen;		
	}	
	
	
	public void vipnewhomepages() throws Exception {
		HSSFWorkbook contextbook = Exceel();
		HSSFSheet contextsheet = contextbook.getSheet("VIPJounrey");		
		for(int context = 1; context <contextsheet.getPhysicalNumberOfRows(); context++) {
			String contextType = contextsheet.getRow(context).getCell(0).getStringCellValue();
			String ContextContent = contextsheet.getRow(context).getCell(1).getStringCellValue();
			contextmap.put(contextType, ContextContent);
			}
		Thread.sleep(2000);
		if(!driver.findElements(By.xpath(obj.getProperty("HOME_USER_POINTS"))).isEmpty()) {
			//if(!AppValidation("HOME_USER_POINTS").isEmpty()) {
			logger.log(LogStatus.PASS, "Old User");			
			String homelevel = AppValidation("HOME_USER_LEVEL");
			//System.out.println(homelevel);		
			logger.log(LogStatus.PASS, "User Level in  MOA Home Page = "+homelevel);		
			String points = AppValidation("HOME_USER_POINTS");
			//System.out.println(points);
			getResult1("pass");
			//ClickEvents("HOME_USER_POINTS");	
			vipclickupdate();
			
		} else  {
			logger.log(LogStatus.PASS, "New User Please = "+AppValidation("VIPNewUser"));
			getResult1("pass");
			ClickEvents("VIPNewUser");	
			Thread.sleep(1000);
			if(!driver.findElements(By.xpath(obj.getProperty("VipNewTitle"))).isEmpty()) {
				for(int newpage = 1; newpage<4; newpage++) {
					getResult1("pass");
					String viptitle = AppValidation("VipNewTitle");
					System.out.println("viptitle = "+viptitle);					
					imagedimesions("VipNewBanners");					
					String vipmessage = AppValidation("VipNewMessage");
					System.out.println("vipmessage = "+vipmessage);					
					if(contextmap.keySet().contains(viptitle)) {
						String desc1 = contextmap.get(viptitle);						
						if(desc1.equalsIgnoreCase(vipmessage)) {
							logger.log(LogStatus.PASS, "Journey Title for Page "+newpage+" = " +viptitle);
							logger.log(LogStatus.PASS, "Journey Description  "+newpage+" = "+vipmessage);
						} else {
							logger.log(LogStatus.FAIL, "Journey Description and Journey Title Mismatch");
						}						
					}					
				}
				ClickEvents("VipRewardsbtn");
				logger.log(LogStatus.INFO, "Categories are = "+AppValidation("VipCatTitles"));
				List<AndroidElement> cattitles = AppSameList("VipCategories");
				System.out.println(cattitles);
				logger.log(LogStatus.INFO, "Categories are = "+cattitles);
				ClickEvents("toppopcancel");				
			} else {
				System.out.println("New Journey Pages are Not Available");
				logger.log(LogStatus.FAIL, "New Jounrey Pages are Not Available");
			}			
		}		
		Thread.sleep(3000);
		getResult1("pass");
		String viplevel = AppValidation("VIP_USER_LEVEL");
		String vippoints = AppValidation("VIP_USER_POINTS");
		logger.log(LogStatus.PASS, "User Navigated to VIP Home Page");
		logger.log(LogStatus.PASS, "User Level in VIP Page = "+viplevel);
		logger.log(LogStatus.PASS, "User Points in VIp Page = "+vippoints);	
		for(int vip = 1; vip<=5;vip++)
			logger.log(LogStatus.PASS, "User Levels in VIP Page = "+AppValidation("vipLevels"+vip));
	}
	
	
	public void spinwheel() throws Exception, InterruptedException, CustomException {
		//ClickEvents("HOME_USER_LEVEL");		
		swipeByElements();
		getResult1("pass");
		String toastmesg = tc.ToastMessage(TakeScreenshot(),"Spin");
		System.out.println("toastmesg == > "+toastmesg);
		if(toastmesg.equals("True")) {
			ClickEvents("contentpopimages");
			Thread.sleep(7000);
		} else {
			System.out.println("No spin the wheel");
		}	
		getResult1("pass");
		if(!driver.findElements(By.xpath(obj.getProperty("buypkpopmesg"))).isEmpty()) {			
			String lesspoints = AppValidation("buypkpopmesg");
			if(!lesspoints.equalsIgnoreCase("Sorry customer! You need sufficient amount of VIP points to play the Game!"))
				{
					logger.log(LogStatus.INFO, lesspoints);
					ClickEvents("Ok_button");
					return;
				}
		}	
		else if(!driver.findElements(By.xpath(obj.getProperty("Redeemspin"))).isEmpty()) {
			String RedeemWelcomeback = AppValidation("Redeemspin");
			logger.log(LogStatus.INFO, RedeemWelcomeback);			
		}
		else if(!driver.findElements(By.xpath(obj.getProperty("Redeemmwith50"))).isEmpty()) {
			String Redeemmwith50 = AppValidation("Redeemmwith50");
			logger.log(LogStatus.INFO, Redeemmwith50);
		}
		Thread.sleep(5000);
		ClickEvents("Redeemproceed");
		getResult1("pass");
		ClickEvents("KnowTheSpins");
		//UiSelectorClick("Know Your Spins >");
		Thread.sleep(2000);		
		getResult1("pass");
		swipeByElements();
		logger.log(LogStatus.INFO, "Before Spin Wheel");
		ClickEvents("playnow");
		Thread.sleep(1000);
		getResult1("pass");
		ClickEvents("SpinButton");
		logger.log(LogStatus.INFO, "During Spin Wheel");
		getResult1("pass");
		Thread.sleep(10000);
		getResult1("pass");
		ClickEvents("okspin");
	}
	
public void Swipe(int numoftimes, String xycord, int endpoint) {
	
	//if(!driver.findElements(By.xpath(obj.getProperty("Entertmntbanners"))).isEmpty()) {
	MobileElement bann = driver.findElement(By.xpath(obj.getProperty(xycord)));
	
	System.out.println("Dimensions of earnmore = "+bann);
	
	int xax = bann.getCenter().getX();
	int yax = bann.getCenter().getY();
	//int yax = bann.height / 2;
	
		System.out.println("X = "+xax);
		System.out.println("Y = "+yax);
		for(int map=1; map<=numoftimes;map++) {
		int anchor = (int) (xax); // x-axis
		int startPoint = (int) (yax); // y-axis
		int endPoint = (int) (endpoint); // x-axis end
		TouchAction tou = new TouchAction((AndroidDriver) driver);		
			tou.press(PointOption.point(anchor, startPoint))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(3000)))
					.moveTo(PointOption.point(endPoint, startPoint)).release().perform(); 
		}	
	}
	
	public void EarnMorePoints() {
		try {
			//ClickEvents("AddOption");
			//ClickEvents("VIPQuick");
			vipclickupdate();
			scrolltill("EarnmorepointTitles","Earn More Points");
			Thread.sleep(5000);
			Swipe(3,"EarnmorepointTitles",65);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void vipclickupdate() throws Exception {
		try {
			ClickEvents("HOME_USER_POINTS");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread.sleep(1000);
		if(!driver.findElements(By.xpath(obj.getProperty("UpdateLater"))).isEmpty()) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ClickEvents("UpdateLater");
		}
	}
	
	public void iselected() {
		MobileElement selected = driver.findElement(By.className("android.widget.TextView"));
		if(selected.isSelected()) {
			System.out.println("Selected = "+selected.getText());
			logger.log(LogStatus.PASS, "Selected offer is =  " + selected.getText());
		}
	}
	
	public void RedeemPoints() throws Exception {
		Map<String,String> vipoffer =  new LinkedHashMap<>();
		//vipclickupdate();
		ScrollUp();
		List<AndroidElement> shortlist = AppSameList("shortcutTitles");
		for(int m =0; m<1; m++) {
			logger.log(LogStatus.PASS, "Clicked on "+shortlist.get(m).getText());
			shortlist.get(m).click();	
		}			
		List<AndroidElement> keykey = AppSameList("VIPOffersTitile");		
		keykey.remove(0);
		Thread.sleep(2000);
		//System.out.println("keykey = "+keykey.ge);
		List<AndroidElement> valuesvalues = AppSameList("ViewAll");
		System.out.println("keykey.size() = "+keykey.size());
		for(int view=0; view<keykey.size(); view++) {	
			vipoffer.put(keykey.get(view).getText(), valuesvalues.get(view).getText());			
		}
		//System.out.println("one =  "+vipoffer);
		//System.out.println("Two = "+vipoffer.keySet().toString());
		
		for(int m=0 ; m<2; m++) {
			logger.log(LogStatus.PASS, "Offer selected is " +keykey.get(m).getText());
			valuesvalues.get(m).click();				
				Thread.sleep(2000);
				getResult1("pass");
				String packdetails = AppValidation("PackDetails");
				String PackPoints = AppValidation("PackPoints").replaceAll("[^0-9]", "");				
				ClickEvents("DataVoiceSpeical");
				Thread.sleep(3000);
				getResult1("pass");
				String DataOffers;
				try {
					DataOffers = "You are trying to redeem '"+packdetails+" Offer' with "+PackPoints+" VIP points.";
					if(!UiSelectorText(DataOffers).isEmpty()) {
						logger.log(LogStatus.PASS, "Selected offer is =  " +UiSelectorText(DataOffers));
						UiSelectorClick("CONFIRM");						
								}					
				} catch (Exception e) {					
						String voiceOffers = "You are trying to redeem '"+packdetails+" Call' with "+PackPoints+" VIP points.";
						String callonnet = UiSelectorText(voiceOffers);
						logger.log(LogStatus.PASS, "Selected offer is =  " +callonnet);
						//System.out.println(callonnet);
						UiSelectorClick("CONFIRM");	
					}				
				Thread.sleep(1000);
				getResult1("pass");
				UiSelectorClick("OK");
				driver.navigate().back();
				Thread.sleep(2000);
				getResult1("pass");					
		}		
		ScrollUp();
		ScrollUp();		
	}
	
	public void MyDiscounts() throws Exception {
		
		List<AndroidElement> shortlist = AppSameList("shortcutTitles");
		for(int m =1; m<2; m++) {
			logger.log(LogStatus.PASS, "Clicked on "+shortlist.get(m).getText());
			shortlist.get(m).click();	
		}		
		ClickEvents("ViewAll");
		Thread.sleep(1500);
		if(!driver.findElements(By.xpath(obj.getProperty("VipCategories"))).isEmpty()) {
			Thread.sleep(1000);		
			System.out.println("i m in ");
			getResult1("pass");
			ClickEvents("UpdateLater");
			logger.log(LogStatus.PASS, "Category Update Later >");			
		}
			Thread.sleep(1000);
			getResult1("pass");
			swipeByElements();
			String[] ar = {"FOOD AND BEVERAGE","ENTERTAINMENT","ELECTRONICS","BEAUTY","CAFE","FASHION","FITNESS","RETAIL"};
			
			for(int arr=0; arr<ar.length; arr++) {
			String xpathofdiscounts = "//android.widget.TextView[@text = '"+ar[arr]+"']";
			if(!driver.findElements(By.xpath(xpathofdiscounts)).isEmpty()) {					
				driver.findElement(By.xpath(xpathofdiscounts)).click();
				Thread.sleep(1000);
				logger.log(LogStatus.PASS, "My discounts selected is "+ar[arr]);
				getResult1("pass");
				}
			}
			driver.navigate().back();
			ScrollUp();
			ScrollUp();
		}
	
	public void MyPoints() throws Exception {
		List<AndroidElement> shortlist = AppSameList("shortcutTitles");
		for(int m =2; m<3; m++) {
			logger.log(LogStatus.PASS, "Clicked on "+shortlist.get(m).getText());
			shortlist.get(m).click();	
		}
		Thread.sleep(500);
		getResult1("pass");
		//String pointshs = AppValidation("pointshistory");
		if(!driver.findElements(By.xpath(obj.getProperty("pointshistory"))).isEmpty()) {			
			logger.log(LogStatus.PASS, "Current Tier Icon exists and its dimensions are "+imagedimesions("CurrentLevelTierIcon") );
			logger.log(LogStatus.PASS, "Available Points are  "+AppValidation("AvailablePoints") + " " +AppValidation("AvailableText"));
			logger.log(LogStatus.PASS, "Points History Redirection Text "+AppValidation("PointHistoryRedirection"));
			ClickEvents("PointHistoryRedirection");
			logger.log(LogStatus.PASS, "Redirected to Points History Page ");
			Thread.sleep(2000);
			getResult1("pass");
			driver.navigate().back();						
		}
		
		if(!driver.findElements(By.xpath(obj.getProperty("pointshistory"))).isEmpty()) {			
			logger.log(LogStatus.PASS, ""+AppValidation("PointsCount") + " " +AppValidation("PointsExpireBy")+ " " +AppValidation("PointsExpireByValue"));
			logger.log(LogStatus.PASS, "Redeem Points Redirection Text "+AppValidation("RedeemPointsRedirection"));
			ClickEvents("RedeemPointsRedirection");
			logger.log(LogStatus.PASS, "Redirected to Redeem Points Page ");
			Thread.sleep(2000);
			getResult1("pass");
			driver.navigate().back();						
		}
		
		if(!driver.findElements(By.xpath(obj.getProperty("EarnPointsLayout"))).isEmpty()) {			
			logger.log(LogStatus.PASS, "Next Level Tier Icon exists and its dimensions are "+imagedimesions("NextLevelTierIcon") );
			logger.log(LogStatus.PASS, "Available Points are  "+AppValidation("NextLevelPoints") + " " +AppValidation("RequiredLevelText"));
			logger.log(LogStatus.PASS, "Next Level Redirection Text "+AppValidation("EarnPointsRedirection"));
			ClickEvents("EarnPointsRedirection");
			logger.log(LogStatus.PASS, "Redirected to Next Level Page ");
			Thread.sleep(2000);
			getResult1("pass");
			driver.navigate().back();						
		}
		
		
	}
	
	public void MyBenefits() throws Exception {
		vipclickupdate();
		scrolltill("knowmore>", "Know more >");
		imagedimesions("MyBenefitsIcon");
		String colorofelement = driver.findElement(By.xpath(obj.getProperty("MyBenefitsIcon"))).getCssValue("style");
		System.out.println("colorofelement = "+colorofelement);
		String desc = AppValidation("MyDiscountDesc");
		if(desc.isEmpty()) {
			logger.log(LogStatus.FAIL, "Description is not available");
		} else {
			logger.log(LogStatus.PASS, "My Benefits Description : "+desc);
		}		
		
		ClickEvents("knowmore>");
	}
	
	
	
		
	
	
	public void UiSelectorClick(String textclick) {
		driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""+textclick+"\")")).click();
		}
	
	public String UiSelectorText(String textclick) {
		return driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""+textclick+"\")")).getText();
		}


}
