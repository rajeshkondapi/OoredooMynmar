package AndroidAppiumAuto;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Array;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
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

			//String PathCheck3 = obj.getProperty(clickeve);
			String PathCheck3 = (String) obj.get(clickeve);			
			//System.out.println(PathCheck3);		
			WebDriverWait wait = new WebDriverWait(driver, 20);
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PathCheck3)));
			} catch (Exception ex) {
				throw new CustomException(1000,
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
				logger.log(LogStatus.PASS, "Selected Field/Element is "+driver.findElement(By.xpath(selectevent)).getText());
				return;
			}
		}  catch (CustomException e) {
			System.out.println("Unable to find " + selectevent + " to Select !" + e);

			logger.log(LogStatus.INFO, "Exception occured: " + e.getMessage());
			throw new CustomException(e.getCode(), e.getMessage());

		}
	}

	public String AppValidation(String elements) throws CustomException  {

		try {
			propertyelements();		
			String PathCheck2 = obj.getProperty(elements);			
			MobileElement DisplayCheck = null;			
			DisplayCheck = (MobileElement) driver.findElement(By.xpath(PathCheck2));
			if (DisplayCheck.isDisplayed()) {
				String appcheck = DisplayCheck.getText();
				return appcheck;
			} 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new CustomException(300, "The Element " + elements + " could not find from the application please try again !");				

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
		waituntillfound("buypkpopmesg");
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


		for (int i = 0; i < 6; i++) {
			swipeByElements();
			//System.out.println("Search attempts - "+i);
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
		String OldLocationAddress = AppValidation("OldLocation");
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
			logger.log(LogStatus.INFO, mesesage);
		}			
		ClickEvents("confirmbutton");
		Thread.sleep(1000);	
		
		String Updatedlocation = AppValidation("OldLocation");
		if(Updatedlocation.equalsIgnoreCase(OldLocationAddress)) {
			logger.log(LogStatus.FAIL, "Location Not Updted");
		} else  {
			logger.log(LogStatus.PASS, "Old Location Address : "+OldLocationAddress);
			logger.log(LogStatus.PASS, "New Location Address : "+Updatedlocation);
		}
		
		
		
		 // 8.1
		
		/*String LocationAddress = AppValidation("OldLocation");
		String Name = AppValidation("Setting_Name");
		ClickEvents("uploadprofilepic");
		if(!AppValidation("VIPAddressHeader").isEmpty()) {
			logger.log(LogStatus.PASS, "Redirected to Profile Update Page");
			getResult1("pass");
			String updatedlocation = AppValidation("VIPUpdateAddress");
			String namefield = AppValidation("VIPEnterName");
			if(updatedlocation.equals(LocationAddress) && Name.equals(namefield)) {
				logger.log(LogStatus.PASS, "Updated Location is = "+updatedlocation );
				logger.log(LogStatus.PASS, "Name Field are same = "+Name );
			} else {
				logger.log(LogStatus.FAIL, "Location  & Name MisMatched ");
			}
		} else {
			logger.log(LogStatus.FAIL, "Redirection to Profile Page is Failed");
		}

		ClickEvents("homebtton");*/

	}


	public String imagedimesions(String dim) {					
		String dimen = driver.findElement(By.xpath(obj.getProperty(dim))).getSize().toString();	
		if(dimen.isEmpty()) {
			logger.log(LogStatus.FAIL, "No Image/Banner/Icon is available");
		} else {
			logger.log(LogStatus.PASS, "Dimensions of Image/Banner/Icon for "+dim+" = "+dimen);
			//logger.log(LogStatus.PASS, "Number of Available Images/Icon/Banner for "+dim+" = "+dimen.length());
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
		//vipclickupdate();
		swipeByElements();
		//getResult1("pass");
		//for(int spinposneg = 0; spinposneg<2; spinposneg++ ) {
			String toastmesg = tc.ToastMessage(TakeScreenshot(),"Spin");
			System.out.println("toastmesg == > "+toastmesg);
			if(toastmesg.equals("True")) {
				ClickEvents("contentpopimages");
				Thread.sleep(3000);
			} else {
				System.out.println("No spin the wheel");
			}	
			Thread.sleep(5000);
			//System.out.println(driver.getPageSource());
			if(driver.findElements(By.xpath(obj.getProperty("buypkpopmesg"))).size()!=0) {	
				getResult1("pass");
				String lesspoints = AppValidation("buypkpopmesg");
				System.out.println(lesspoints);
				if(lesspoints.trim().equalsIgnoreCase("Sorry customer! You need sufficient amount of VIP points to play the Game!"))
				{
					logger.log(LogStatus.INFO, lesspoints);
					ClickEvents("Ok_button");
					return;
				}
			}	
			else if(driver.findElements(By.xpath(obj.getProperty("Redeemmwith50"))).size()!=0) {
				//getResult1("pass");
				String Redeemmwith50 = AppValidation("Redeemmwith50");			
				if(Redeemmwith50.trim().equalsIgnoreCase("You will be charged 50 points from the points balance to play the game.")) {
					logger.log(LogStatus.INFO, "New Spin User :"+Redeemmwith50);
					System.out.println(Redeemmwith50);
				} else {
					System.out.println(Redeemmwith50);
					logger.log(LogStatus.INFO, "Welcome Back and Play spin Now :"+Redeemmwith50);	
				}			
			}
			Thread.sleep(5000);
			ClickEvents("Redeemproceed");
			getResult1("pass");
			//ClickEvents("KnowTheSpins");
			//UiSelectorClick("Know Your Spins >");
			//Thread.sleep(2000);		
			//getResult1("pass");
			//swipeByElements();
			logger.log(LogStatus.INFO, "Before Spin Wheel");

			//System.out.println("STW Code :"+driver.getPageSource());
			//ClickEvents("playnow");
			//Thread.sleep(1000);
			//getResult1("pass");

			//logger.log(LogStatus.INFO, "During Spin Wheel");
			//String countspin = driver.findElement(By.xpath(obj.getProperty("SpinCount"))).getText();				
			String noofspins =  driver.findElement(By.xpath(obj.getProperty("SpinCount"))).getText();
			int nospi = Integer.parseInt(noofspins);
			System.out.println(nospi);
			logger.log(LogStatus.INFO, "No of Available Before Spins : "+Integer.parseInt(noofspins));
			//System.out.println(Integer.valueOf(noofspins));
			for(int spcount=0; spcount < nospi;spcount++) {	
				System.out.println("Count in for loop - "+spcount);
				ClickEvents("SpinButton");
				logger.log(LogStatus.INFO, "No of Available Spins : "+driver.findElement(By.xpath(obj.getProperty("SpinCount"))).getText());
				Thread.sleep(10000);
				getResult1("pass");
				Thread.sleep(2000);
				if(!driver.findElements(By.xpath(obj.getProperty("Redeemnow"))).isEmpty()) { 
					//ClickEvents("okspin");
					System.out.println("Redeem now is available");
				} else {

					//System.out.println("efore ok - "+spcount);
					getResult1("pass");
					logger.log(LogStatus.PASS, "Offer redeemed");
					ClickEvents("okspin");
					//System.out.println("after ok - "+spcount);
				}

				//System.out.println("out of if else ok - ");
			}
			//System.out.println("out of if else ok - and for ");
			//Thread.sleep(2000);	
			//System.out.println("After spins all :"+driver.getPageSource());
			//driver.navigate().back();
		//}
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
			ClickEvents("HOME_USER_POINTS");
			Thread.sleep(2000);
			//if(!driver.findElements(By.xpath(obj.getProperty("UpdateLater"))).isEmpty()) {
			try {
				
				if(driver.findElements(By.xpath(obj.getProperty("UpdateLater"))).size() != 0) {
					ClickEvents("UpdateLater");
				} else  {
					System.out.println("No Update later");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.getLocalizedMessage();
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
		//ScrollUp();
		List<AndroidElement> shortlist = AppSameList("shortcutTitles");
		for(int m =0; m<1; m++) {
			logger.log(LogStatus.PASS, "Clicked on "+shortlist.get(m).getText());
			shortlist.get(m).click();	// Redeem text click
		}			
		List<AndroidElement> keykey = AppSameList("VIPOffersTitile");	// Redeem offers titiels	
		keykey.remove(0);
		Thread.sleep(2000);
		//System.out.println("keykey = "+keykey.ge);
		List<AndroidElement> valuesvalues = AppSameList("ViewAll");
		System.out.println("keykey.size() = "+keykey.size());
		for(int view=0; view<keykey.size(); view++) {	
			vipoffer.put(keykey.get(view).getText(), valuesvalues.get(view).getText());		// data=viewall // voice-viewall // spec-viewall	
		}
		//System.out.println("one =  "+vipoffer);
		//System.out.println("Two = "+vipoffer.keySet().toString());
		// 8.1
				//waituntillfound("Ok_button");
				/*if(!driver.findElements(By.xpath(obj.getProperty("Ok_button"))).isEmpty()) {
					String noofferfound = AppValidation("success_or_failure_pop_up_msg");
					getResult1("pass");
					logger.log(LogStatus.PASS, noofferfound);
					ClickEvents("Ok_button");
				} */
		// To click all data/voice/speci off view all and take screenshot
		try {
		for(int entersearch=0; entersearch<3;entersearch++ ) {			
		ClickEvents("SearchTxt");
		if(entersearch==0) {
		SendEvent("EnterSearchTxt", "Test Search");			
		//
		} else if(entersearch==1) {
			SendEvent("EnterSearchTxt", "MB");	
			
		} else if (entersearch ==2) {
			SendEvent("EnterSearchTxt", "momo");
			
		}
		ClickEvents("EnterSearch");	
		Thread.sleep(2000);
		getResult1("pass");			
		int numberoftitles = driver.findElements(By.xpath(obj.getProperty("DataVoiceValidity"))).size();	
		
		if(numberoftitles!=0) {
			for(int titles = 0; titles<numberoftitles; titles++) {										
					logger.log(LogStatus.PASS, "Available offers & Points are: "+AppSameList("SearchTitles").get(titles).getText() + " Points :"+AppSameList("DataVoiceValidity").get(titles).getText());									
					}
		}		
		else {			
			System.out.println("No offer found");
			logger.log(LogStatus.PASS, "Offers found");
		}
		ClickEvents("Backarrow");
	}
		
		Thread.sleep(2000);
			for(int m=0 ; m<3; m++) {

				logger.log(LogStatus.PASS, "Offer selected is " +keykey.get(m).getText());				
				//System.out.println("Looping before = "+m);
				Thread.sleep(2000);
				valuesvalues.get(m).click();	// data's - viewall click // voice- viewall click // sploffer - viewall click	
				//System.out.println("Looping After = "+m);
				//getResult1("pass");
				Thread.sleep(1000);
				
				scrolltill("FAQ", "FAQ");

				// Spciloffers - related
				if(driver.findElements(By.xpath(obj.getProperty("Entertainment"))).size()!=0) {
					String[] ar = {"Food and Beverage","Entertainment","Electronics","Beauty","Cafe","Fashion","Fitness","Retail"};

					for(int arr=0; arr<ar.length; arr++) {
						String xpathofdiscounts = "//android.widget.TextView[@text = '"+ar[arr]+"']";
						if(!driver.findElements(By.xpath(xpathofdiscounts)).isEmpty()) {					
							driver.findElement(By.xpath(xpathofdiscounts)).click();						
							Thread.sleep(1000);
							logger.log(LogStatus.PASS, "Special Offer selected is "+ar[arr]);
							getResult1("pass");
							int numberofoffers = driver.findElements(By.xpath(obj.getProperty("Specialofferpoints"))).size();

							for(int offers=0; offers<numberofoffers; offers++) {
								if(driver.findElements(By.xpath(obj.getProperty("Specialofferpoints"))).size()!=0) {								
									logger.log(LogStatus.PASS, "Available offers & Points are: "+AppSameList("SpecialofferTitiles").get(offers).getText() + " Points :"+AppSameList("Specialofferpoints").get(offers).getText());									
								} else {
									logger.log(LogStatus.INFO, "No Offers are available for "+ar[arr]);
								}
							}							
						}
					}					
					Thread.sleep(2000);
					//getResult1("pass");
					driver.navigate().back();
					
				} else { // Data Voice - related
					System.out.println("Data voice related");
					int numberofoffers = driver.findElements(By.xpath(obj.getProperty("Specialofferpoints"))).size();
					List<AndroidElement> redeemoffers = AppSameList("SpecialofferTitiles");
					List<AndroidElement> redeemofferspoints = AppSameList("Specialofferpoints");
					for(int dataoff=0; dataoff<numberofoffers; dataoff++) {								
						if(!driver.findElements(By.xpath(obj.getProperty("Specialofferpoints"))).isEmpty()) {							
							logger.log(LogStatus.PASS, "Available offers & Points are: "+redeemoffers.get(dataoff).getText() + " Points :"+redeemofferspoints.get(dataoff).getText());									
						} else {
							logger.log(LogStatus.INFO, "No Offers are available");
						}
					}
					ClickEvents("PackDetails");
					Thread.sleep(5000);					
					getResult1("pass");
					UiSelectorClick("CONFIRM");	
					//System.out.println("debug 1 ="+redeemoffers.get(0).getText());
					/*String packdetails = redeemoffers.get(0).getText().toString();
					String PackPoints = redeemofferspoints.get(0).getText().toString().replaceAll("[^0-9]", "");
					
						System.out.println("packdetails = "+packdetails);
						System.out.println("PackPoints = "+PackPoints);	
						
						try {

							String DataOffers = "You are trying to redeem '"+packdetails+" Offer' with "+PackPoints+" VIP points.";
							if(!UiSelectorText(DataOffers).isEmpty()) {
								logger.log(LogStatus.PASS, "Selected offer is =  " +UiSelectorText(DataOffers));
								UiSelectorClick("CONFIRM");	
								
										}	
							//else if ()
						} catch (Exception e) {					
								String voiceOffers = "You are trying to redeem '"+packdetails+" Call' with "+PackPoints+" VIP points.";
								String callonnet = UiSelectorText(voiceOffers);
								logger.log(LogStatus.PASS, "Selected offer is =  " +callonnet);
								//System.out.println(callonnet);
								UiSelectorClick("CONFIRM");	
							}	*/		
						Thread.sleep(1000);
						getResult1("pass");
						UiSelectorClick("OK");					
						Thread.sleep(2000);
						getResult1("pass");
						driver.navigate().back();
						//break;
							}
					}	
				//}
			//}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.getLocalizedMessage();
		}
		ScrollUp();
		ScrollUp();	
		
		//ScrollUp();
		//ScrollUp();
		
		/*String noenoughpoints = "You don't have enough points to redeem this offer.";					
		if(driver.findElements(By.xpath(obj.getProperty("Earnpointsbutton"))).size()!=0) {
			System.out.println("8.1");
			getResult1("pass");
			logger.log(LogStatus.PASS, noenoughpoints);
			ClickEvents("Earnpointsbutton");
			if(AppValidation("EarnmorepointTitles").equalsIgnoreCase("Earn More Points")) {
				logger.log(LogStatus.PASS, "Redirected to Earn More Points");
				getResult1("pass");
				ScrollUp();																		
				shortlist.get(0).click();
			}

		} else {*/

		// 8.1

		//String DataVoiceValidity = AppValidation("DataVoiceValidity");  //data/voice First offer validity
		/*int validitycount = driver.findElements(By.xpath("DataVoiceValidity")).size();
		for(int datavoicevalid=0;datavoicevalid<validitycount;datavoicevalid++ ) {
			if(!driver.findElements(By.xpath(obj.getProperty("DataVoiceValidity"))).isEmpty()) {
				logger.log(LogStatus.PASS, "Available offers & Validity are: "+AppSameList("DataVoiceTItlename") + " Validity :"+AppSameList("DataVoiceValidity"));
			} else {
				logger.log(LogStatus.INFO, "No Offers are available");
			}
		}*/

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
		//vipclickupdate();
		Thread.sleep(2000);
		List<AndroidElement> shortlist = AppSameList("shortcutTitles");
		for(int m =2; m<3; m++) {
			logger.log(LogStatus.PASS, "Clicked on "+shortlist.get(m).getText());
			shortlist.get(m).click();	
		}
		Thread.sleep(500);
		getResult1("pass");
		//String pointshs = AppValidation("pointshistory");
		if(!driver.findElements(By.xpath(obj.getProperty("RedeemLayout"))).isEmpty()) {			
			logger.log(LogStatus.PASS, "Current Tier Icon exists and its dimensions are "+imagedimesions("CurrentLevelTierIcon") );
			logger.log(LogStatus.PASS, "Available Points are  "+AppValidation("AvailablePoints") + " " +AppValidation("AvailableText"));
			logger.log(LogStatus.PASS, "Points History Redirection Text "+AppValidation("PointHistoryRedirection"));
			ClickEvents("PointHistoryRedirection");
			Thread.sleep(4000);
			if(AppValidation("PointsHistory").equals("Points History")) {
				logger.log(LogStatus.PASS, "Redirected to History Points Page ");
				getResult1("pass");
			} else {
				logger.log(LogStatus.FAIL, "Redirection Failed");
			}			
			
			ClickEvents("ALL");
			mypointshistory();
			getResult1("pass");
			ClickEvents("REDEEM_POINTS");
			mypointshistory();
			getResult1("pass");
			ClickEvents("POINTS_EARNED");
			mypointshistory();
			getResult1("pass");
			driver.navigate().back();						
		}

		if(!driver.findElements(By.xpath(obj.getProperty("pointshistory"))).isEmpty()) {			
			logger.log(LogStatus.PASS, ""+AppValidation("PointsCount") + " " +AppValidation("PointsExpireBy")+ " " +AppValidation("PointsExpireByValue"));
			//logger.log(LogStatus.PASS, ""+AppValidation("PointsCount") );//" " +AppValidation("EarnedQuarter"));
			logger.log(LogStatus.PASS, "Redeem Points Redirection Text "+AppValidation("RedeemPointsRedirection"));
			ClickEvents("RedeemPointsRedirection");
			Thread.sleep(4000);
			if(AppValidation("RedeemPoints").equals("Redeem Points")) {
				logger.log(LogStatus.PASS, "Redirected to Redeem Points Page");
				getResult1("pass");
			} else {
				logger.log(LogStatus.FAIL, "Redirection Failed");
			}		
							
			driver.navigate().back();	
		}


		if(!driver.findElements(By.xpath(obj.getProperty("EarnPointsLayout"))).isEmpty()) {			
			logger.log(LogStatus.PASS, "Next Level Tier Icon exists and its dimensions are "+imagedimesions("NextLevelTierIcon") );
			logger.log(LogStatus.PASS, "Available Points are  "+AppValidation("NextLevelPoints") + " " +AppValidation("RequiredLevelText"));
			logger.log(LogStatus.PASS, "Next Level Redirection Text "+AppValidation("EarnPointsRedirection"));
			ClickEvents("EarnPointsRedirection");
			Thread.sleep(4000);
			if(AppValidation("MyBenefits").equals("My Benefits")) {
				logger.log(LogStatus.PASS, "Redirected to Next Level Page ");
				getResult1("pass");
			} else  {
				logger.log(LogStatus.FAIL, "Redirection Failed");
			}			
			driver.navigate().back();						
		}		
	}

	public void mypointshistory() {
		List<AndroidElement> Alltitles;
		try {
			if(!driver.findElements(By.xpath(obj.getProperty("AllTitles"))).isEmpty()){
			Alltitles = AppSameList("AllTitles");
			List<AndroidElement> AllDates = AppSameList("AllDates");
			List<AndroidElement> AllPoints = AppSameList("AllPoints");
			System.out.println(Alltitles.size());
			for(int points=0;points<Alltitles.size();points++) {
				String Titles =  Alltitles.get(points).getText();
				String Dates =  AllDates.get(points).getText();
				String Points =  AllPoints.get(points).getText();
				logger.log(LogStatus.PASS, Titles +" "+ Dates + " "+ Points);
				}
			} else {
				logger.log(LogStatus.PASS, "No Records Found");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void MyBenefits() throws Exception {
		vipclickupdate();
		scrolltill("knowmore>", "Know more >");
		List<AndroidElement> shortlist = AppSameList("shortcutTitles");
		//shortlist.get(3).click();
		imagedimesions("MyBenefitsIcon");
		getResult1("pass");
		try {
			System.out.println(driver.findElement(By.xpath("//android.widget.LinearLayout[@focusable='true']")).getText());			
			//System.out.println(driver.findElement(By.xpath(obj.getProperty("MyBenefitsIcon"))).getCssValue("color"));
			//System.out.println("colorofelement = "+colorofelement);
			String desc = AppValidation("MyBenefitsDesc");
			if(driver.findElement(By.xpath(obj.getProperty("MyBenefitSelected"))).isSelected()) {
				driver.findElement(By.xpath(obj.getProperty("MyBenefitSelected"))).getText();
			}			
			if(desc.isEmpty()) {
				logger.log(LogStatus.FAIL, "Description is not available");
			} else {
				logger.log(LogStatus.PASS, "My Benefits Description : "+desc);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

		ClickEvents("knowmore>");
		Thread.sleep(2000);		
		for(int viplevels=1; viplevels<3; viplevels++) {		
			checkexists("MyBenefitsheadertitle");
			checkexists("MyBenefitsLooking");		
			imagedimesions("MyBenefitsVipIcon");			
			imagedimesions("MyBenefitsImgIcons");		
			checkexists("MyBenefitsIconName");
			checkexists("MyBenefitsEarnPoints");
			checkexists("MySpecialBenefitHeading");
			checkexists("MySpecialBenefitsoffers");
			checkexists("MySpecialBenefitsvalues");		
			checkexists("MyBenefitsAmount");					
			checkexists("MyBenefitsVipName");			
			checkexists("MyBenefitsVipPoints");
			checkexists("MyBenefitsNextLevelPoints");
			checkexists("MyBenefitsEarnedPoints");
			checkexists("MyBenefitsLevelIndicator");
			//swipeByElements();
			if(!driver.findElements(By.xpath(obj.getProperty("MyBenefitsEarnPoints"))).isEmpty()) {
				ClickEvents("MyBenefitsEarnPoints");				
			}			
			ScrollUp();			
			Swipe(viplevels, "MyBenefitsAmount",1);
			Thread.sleep(2000);
			getResult1("pass");			
		}
		int iconscountsmy = AppSameList("MyBenefitspacksupdate").size();
	//	List<AndroidElement> myicons = AppSameList("MyBenefitspacksupdate");

		System.out.println(iconscountsmy);
		for(int myben=1; myben<=iconscountsmy; myben++) {

			driver.findElement(By.xpath("//android.widget.LinearLayout[contains(@resource-id,'com.ooredoo.selfcare:id/llBuyPacks')]["+myben+"]")).click();
			//ClickEvents("MyBenefitsImgIcons");
			//System.out.println(myicons.get(myben).getText());
			System.out.println(myben);
			Thread.sleep(2000);
			try {
				if(!driver.findElements(By.xpath(obj.getProperty("VIPDobhead"))).isEmpty()) {
					//if(myicons.get(myben).getText().equals("Update Profile")) {
					System.out.println("Update");
					//myicons.get(myben).click();
					waituntillfound("UpdatePopup");
					logger.log(LogStatus.PASS, "Redirected to Profile Update Page");
					getResult1("pass");
					//driver.navigate().back();
					//} else if (myicons.get(myben).getText().equals("Buy Packs")) {
				} else {
					//myicons.get(myben).click();
					System.out.println("bp");
					logger.log(LogStatus.PASS, "Redirected to Buy Packs Page");
					waituntillfound("bpinternet");
					getResult1("pass");				
					//ClickEvents("AddOption");
					//ClickEvents("VIPQuick");	
				}
				//myicons.get(myben).click();
				ClickEvents("homebtton");
				vipclickupdate();
				waituntillfound("VipMainIcon");
				ClickEvents("VipMainIcon");	
				waituntillfound("MyBenefitsImgIcons");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}

		}	
	}

	public void waituntillfound(String waittill) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath(obj.getProperty(waittill))));
	}


	public void checkexists(String exisitnot) {
		if(!driver.findElements(By.xpath(obj.getProperty(exisitnot))).isEmpty()) {
			int coun = driver.findElements(By.xpath(obj.getProperty(exisitnot))).size();
			//System.out.println(coun);
			for(int countof = 0; countof<coun;countof++) {
				String values = driver.findElements(By.xpath(obj.getProperty(exisitnot))).get(countof).getText();
				//logger.log(LogStatus.PASS, "Number of elements available are = "+coun);
				logger.log(LogStatus.PASS, ""+values);
				//return values;
			}


		} else {
			logger.log(LogStatus.INFO, "The Element "+exisitnot+" you are trying is not available");
			//return "The Element "+exisitnot+" you are trying is not available";
		}
		//return "";
	}

	public void ProfileUpdate() throws Exception, InterruptedException, CustomException {
		String vippointsbeforeupdate = AppValidation("HOME_USER_POINTS"); 
		
		try {
			vipclickupdate();
			Thread.sleep(1000);
			//Before profile updte check points
			
			logger.log(LogStatus.PASS, "VIP Points Before  Update ="+vippointsbeforeupdate);
			//Profile icon click
			ClickEvents("VipProfileIcon");
			if(!driver.findElements(By.xpath(obj.getProperty("UpdatePopup"))).isEmpty()) {
				logger.log(LogStatus.PASS, "Profile Yet to Update");
				getResult1("pass");
				imagedimesions("BannerDetails3");
				logger.log(LogStatus.PASS, ""+AppValidation("UpdatePopup"));	
				imagedimesions("VipPopupBenefits");
				ClickEvents("confirmbutton");			
			} else  {
				logger.log(LogStatus.PASS, "Profile Already Updated");
			}

			Thread.sleep(1000);
			getResult1("pass");			
			if(AppValidation("MyBenefitsheadertitle").equalsIgnoreCase("Profile Update"))  {
				logger.log(LogStatus.PASS, "User in Profile Page");	
				if(!AppValidation("TierLevelinProflepage").isEmpty()) {
					logger.log(LogStatus.PASS, AppValidation("TierLevelinProflepage"));
				} else {
					logger.log(LogStatus.FAIL, "Tier Level doest not exists in vip page");
				}
				
				if(AppValidation("Settings_NickName").equalsIgnoreCase("NAME"))  {
					logger.log(LogStatus.PASS, "Name Title Exists");				
				} else {
					logger.log(LogStatus.FAIL, "Name Title DoestNot Exists");
				}
				if(!AppValidation("VIPEnterName").isEmpty()) {
					logger.log(LogStatus.PASS, "Name Already Exists");
					driver.findElement(By.xpath(obj.getProperty("VIPEnterName"))).clear();
					ClickEvents("VIPUpdateButton");						
					getResult1("pass");
					if(!driver.findElements(By.xpath(obj.getProperty("subscribe_confirm_pop_up1"))).isEmpty()) {
						logger.log(LogStatus.PASS, AppValidation("subscribe_confirm_pop_up1"));
						ClickEvents("Ok_button");
					}else {
						logger.log(LogStatus.FAIL, "No Error Pop Up");
						}
					SendEvent("VIPEnterName", "IMIMOBILEIMIMOBILEIMIMOBILEIMIMOBILEIMIMOBILEIMIMOBILE");
					String givenText= "IMIMOBILEIMIMOBILEIMIMOBILEIMIMOBILEIMIMOBILEIMIMOBILEs";
					logger.log(LogStatus.PASS, "Given Text is = "+givenText+ " Length is =  "+givenText.length()+" Name Field Taken Character length :"+AppValidation("VIPEnterName").length());

					}					
				} 

			
			if(AppValidation("VIPDobhead").equalsIgnoreCase("DOB"))  {
				logger.log(LogStatus.PASS, "DOB Title Exists");
				if(driver.findElement(By.xpath(obj.getProperty("VIPDOB"))).isEnabled()) {
					logger.log(LogStatus.PASS, "DOB Enabled");
					ClickEvents("VIPDOB");
					getResult1("pass");
					ClickEvents("PreviousDOB");
					ClickEvents("PreviousDOB");
					ClickEvents("DOBSelectDate");
					String SelectedDate = AppValidation("DOBSelectedDateDay");
					ClickEvents("DOBSYear");
					ClickEvents("DOBSelectYear");
					getResult1("pass");
					String SelectedYear = AppValidation("DOBSYear");					
					logger.log(LogStatus.PASS, "DOB Selected Date : "+SelectedDate + "Selected Year : "+SelectedYear);
					ClickEvents("Report_Calender_Ok_Button");
				} else {
					logger.log(LogStatus.PASS, "DOB Disabled as user already updated ");
				}
			}
			if(AppValidation("VIPGenderHeader").equalsIgnoreCase("GENDER"))  {
				logger.log(LogStatus.PASS, "GENDER Title Exists");	
				if(driver.findElement(By.xpath(obj.getProperty("VIPGenderMale"))).getAttribute("checked").equals("true")) {
					logger.log(LogStatus.PASS, "Selected is Male");
				} else {
					logger.log(LogStatus.PASS, "Selected is Female");
				}
				//SelectEvent("VIPGenderMale");
				//SelectEvent("VIPGenderFeMale");
			}
			if(AppValidation("VIPAddressHeader").equalsIgnoreCase("ADDRESS"))  {
				logger.log(LogStatus.PASS, "ADDRESS Title Exists");	
				
				//if(AppValidation("VIPUpdateAddress").isEmpty()) {
					logger.log(LogStatus.PASS, "Address Field is Empty");
					SendEvent("VIPUpdateAddress", "Hyderabad, Telangana");					
				/*} else {
					logger.log(LogStatus.INFO, "Address Field is Not Empty");
				}*/	
				
			}
			
			ClickEvents("VIPUpdateButton");
			Thread.sleep(2000);	
			String successmessge =  AppValidation("success_or_failure_pop_up_msg");			
			if(successmessge.equals("Profile Update Completion - 50 points")) {					
				logger.log(LogStatus.PASS, "New Profile Updated ="+successmessge);
				ClickEvents("confirmbutton");
			}else {
				logger.log(LogStatus.PASS, "Profile Updated ="+successmessge);
				ClickEvents("confirmbutton");
			}	
		} catch (Exception e) {
			e.getMessage();
		}
		Thread.sleep(2000);
		waituntillfound("VIP_USER_POINTS");
		String AfterUpdatePoints = AppValidation("VIP_USER_POINTS");
		if(AfterUpdatePoints.equalsIgnoreCase(vippointsbeforeupdate)) {
			logger.log(LogStatus.PASS, "VIP Points are not Updated");
		} else {
			logger.log(LogStatus.PASS, "Updated Points are = "+(Integer.parseInt(AfterUpdatePoints) - Integer.parseInt(vippointsbeforeupdate)));
		}
	}
			
			// 8.1 App
			
			/*if(AppValidation("VIPHomeLocation").equalsIgnoreCase("Home Location >"))  {
				logger.log(LogStatus.PASS, "Home Location Title Exists");
				ClickEvents("VIPHomeLocation");
				Thread.sleep(2000);
				logger.log(LogStatus.PASS, "Navigated to Location");
				getResult1("pass");
				swipeBycoordinates(608,1010,70,3);
				ClickEvents("locationsetnew");
				getResult1("pass");
				ClickEvents("updatelocationbutton");
				Thread.sleep(1000);
				getResult1("pass");
				String mesesage = AppValidation("subscribe_confirm_pop_up1");
				if(mesesage.equalsIgnoreCase("Your new home location successfully updated")) 
				{			
					logger.log(LogStatus.INFO, "Success Message ="+mesesage);
					ClickEvents("confirmbutton");
				}				
				ClickEvents("VIPUpdateButton");
				Thread.sleep(4000);				
				String successmessge =  AppValidation("success_or_failure_pop_up_msg");
				getResult1("pass");
				if(successmessge.equals("Profile Update Completion - 50 points")) {					
					logger.log(LogStatus.PASS, "New Profile Updated ="+successmessge);
					ClickEvents("confirmbutton");
				}else {
					logger.log(LogStatus.PASS, "Profile Updated ="+successmessge);
					ClickEvents("confirmbutton");
				}		

				String AfterUpdatePoints = AppValidation("VIP_USER_POINTS");
				if(AfterUpdatePoints.equalsIgnoreCase(vippointsbeforeupdate)) {
					logger.log(LogStatus.PASS, "VIP Points are not Updated");
				} else {
					logger.log(LogStatus.PASS, "Updated Points are = "+(Integer.parseInt(AfterUpdatePoints) - Integer.parseInt(vippointsbeforeupdate)));
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
*/
	





	public void UiSelectorClick(String textclick) {
		driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""+textclick+"\")")).click();
	}

	public String UiSelectorText(String textclick) {
		return driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\""+textclick+"\")")).getText();
	}
	public void CloseRegister()
	{
		if(!driver.findElements(By.xpath(obj.getProperty("closeregister"))).isEmpty()) {
			try {
				ClickEvents("closeregister");
			} catch (IOException | InterruptedException | CustomException e) {
				// TODO Auto-generated catch block
				e.getLocalizedMessage();
			}
		} else {
			System.out.println("No Content Popup");
		}
		
	}


}
