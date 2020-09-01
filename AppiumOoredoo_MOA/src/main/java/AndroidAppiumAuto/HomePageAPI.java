package AndroidAppiumAuto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.google.gson.Gson;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.relevantcodes.extentreports.LogStatus;

public class HomePageAPI extends CommonMethods {

	List<String> apirespETL = new ArrayList<>();

	public String LogiSessionid() {

		//initmethods();

		Webdr.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		// System.out.println("loginsession");

		Webdr.get("http://ozonecms.ooredoo.com.mm/getdbdetails/");

		Webdr.findElement(dbdetails).sendKeys("dbdetails");

		Webdr.findElement(pass).sendKeys("db@imi@123");

		Webdr.findElement(clickbtn).click();

		WebElement drp = Webdr.findElement(drop);

		Select selectablename = new Select(drp);

		selectablename.selectByVisibleText("WEBPMS");

		Actions act = new Actions(Webdr);

		WebElement getsessionid = Webdr.findElement(textQuer);

		act.moveToElement(getsessionid).click().sendKeys(obj.getProperty("sessionidlogin")).build().perform();

		WebElement showclick = Webdr.findElement(show);

		showclick.click();

		WebElement sessiontab = Webdr.findElement(sesiontoken);

		String getsession = sessiontab.getText();

		Webdr.findElement(logout).click();

		// Webdr.close();


		System.out.println("Session id = = " + getsession);

		return getsession;
	}

	public StringBuilder salthashkey() {

		String sess = LogiSessionid();

		logger.log(LogStatus.INFO, "Request Headers - SessionID = "+sess);
		String session[] = { sess };
		StringBuilder oddChars = new StringBuilder();
		// char c;
		for (String word : session) {
			boolean even = true;

			for (char c : word.toCharArray()) {
				if (even) {
					System.out.print(c);
					oddChars.append(c);
				}
				even = !even;
			}

		}

		return oddChars;

	}

	/* -----------------------------------------------------------Query Balance API --------------------------------------------------*/


	public String queryblnbody() throws JSONException {

		// Webdr.get("https://emn178.github.io/online-tools/sha512.html");

		Map<String, Object> m1 = new LinkedHashMap<String, Object>();

		m1.put("lang", "1");
		m1.put("channel", "odp");
		m1.put("wallet", "");
		m1.put("refreshfrom", "frombackground");

		Gson gson = new Gson();
		String jsonvalue = gson.toJson(m1);
		System.out.println("Body Values = " + jsonvalue);

		return jsonvalue;

	}

	public String BpResponse11() throws JSONException {
		Map<String, Object> BPResp = new LinkedHashMap<String, Object>();

		BPResp.put("lang", "1");
		BPResp.put("channel", "odp");
		BPResp.put("offerid", "");
		BPResp.put("offertype", "DataPackage");
		BPResp.put("type", "buypacks");

		Gson gson = new Gson();
		String jsonvalue = gson.toJson(BPResp);
		System.out.println("Body Values = " + jsonvalue);
		return jsonvalue;

	}

	public String SessionHashKey() throws JSONException, InterruptedException, Exception {

		StringBuilder Saltkey = salthashkey();

		String m2 = queryblnbody();

		logger.log(LogStatus.INFO, "Request Body = "+m2);

		String input = m2 + "&salt=" + Saltkey;

		System.out.println("input ==>" + input);

		// getInstance() method is called with algorithm SHA-512
		MessageDigest md = MessageDigest.getInstance("SHA-512");

		// digest() method is called
		// to calculate message digest of the input string
		// returned as array of byte
		byte[] messageDigest = md.digest(input.getBytes());

		// Convert byte array into signum representation
		BigInteger no = new BigInteger(1, messageDigest);

		// Convert message digest into hex value
		String sha512 = no.toString(16);

		// Add preceding 0s to make it 32 bit
		while (sha512.length() <= 32) {
			sha512 = "0" + sha512;
		}

		System.out.println("sha512 ==>" + sha512);

		logger.log(LogStatus.INFO, "Request Headers - HashKey = "+sha512);

		return sha512;

	}

	public Map<String, String> querybalance(String Ver,String postreq) throws Exception {

		//ecarepreprod.ooredoo.com.mm/selfcareapistg7.1/api/

		//RestAssured.baseURI = "http://ecarepreprod.ooredoo.com.mm/" + Ver + "/api/Home/"+postreq;
		//http://ecareapp.ooredoo.com.mm/SelfcareAPI6.2/API/Home/querybalance
		RestAssured.baseURI = "http://"+obj.getProperty("apiuri")+".ooredoo.com.mm/" + Ver + "/api/Home/"+postreq;
		//http://ecareapp.ooredoo.com.mm/SelfcareAPI6.2/API/Home/querybalance
		logger.log(LogStatus.INFO, "URL = "+RestAssured.baseURI);
		System.out.println(RestAssured.baseURI);

		RequestSpecification request = RestAssured.given();
		// System.out.println("1");
		request.header("Content-Type", "application/json");
		// System.out.println("2");
		request.header("X-IMI-UID", "1570179495649");
		// System.out.println("3");
		request.header("X-IMI-OAUTH", LogiSessionid());
		// System.out.println("4");
		request.header("X-IMI-HASH", SessionHashKey());
		// System.out.println("5");
		request.header("X-IMI-AUTHKEY", "APPYM8205D04");
		// System.out.println("6");

		//logger.log(LogStatus.PASS, "Request Headers = "+request.log());
		//logger.log(LogStatus.PASS, "Request Headers - SessionId  = "+request.get().getHeader("X-IMI-OAUTH"));
		//logger.log(LogStatus.PASS, "Request Headers - HashKey  = "+request.get().getHeader("X-IMI-HASH"));

		request.body(queryblnbody());
		// System.out.println("7");
		Response response1 = request.post();
		// System.out.println((response1.asString()));
		String responsebody = response1.asString();

		System.out.println("responsebody ==> " + responsebody);

		logger.log(LogStatus.INFO, "Response Body = "+responsebody);

		// Voice

		String apivoiceamount = (response1.body().jsonPath().get("voice") != null
				&& (response1.body().jsonPath().get("voice.dispamount") != null
				&& !response1.body().jsonPath().get("voice.dispamount").equals("")))
				? response1.body().jsonPath().get("voice.dispamount")
						: "0";


				String apivoiceMeasure = (response1.body().jsonPath().get("voice") != null
						&& (response1.body().jsonPath().get("voice.measure") != null
						&& !response1.body().jsonPath().get("voice.measure").equals("")))
						? response1.body().jsonPath().get("voice.measure")
								: "0";
						// SMS
						String apismsamount = (response1.body().jsonPath().get("sms") != null
								&& (response1.body().jsonPath().get("sms.dispamount") != null
								&& !response1.body().jsonPath().get("sms.dispamount").equals("")))
								? response1.body().jsonPath().get("sms.dispamount")
										: "0";

								String apismsMeasure = (response1.body().jsonPath().get("sms") != null
										&& (response1.body().jsonPath().get("sms.measure") != null
										&& !response1.body().jsonPath().get("sms.measure").equals("")))
										? response1.body().jsonPath().get("sms.measure")
												: "0";

										// BONUS
										String apibonusamount = (response1.body().jsonPath().get("bonus") != null
												&& (response1.body().jsonPath().get("bonus.dispamount") != null
												&& !response1.body().jsonPath().get("bonus.dispamount").equals("")))
												? response1.body().jsonPath().get("bonus.dispamount")
														: "0";


												String apibonusMeasure = (response1.body().jsonPath().get("bonus") != null
														&& (response1.body().jsonPath().get("bonus.measure") != null
														&& !response1.body().jsonPath().get("bonus.measure").equals("")))
														? response1.body().jsonPath().get("bonus.measure")
																: "0";

														// MSISDN
														String apivalidatemobileno = response1.body().jsonPath().get("mobileno");

														// TALK TIME BALANCE
														String apivalidatebalanceamount = (response1.body().jsonPath().get("balance") != null
																&& (response1.body().jsonPath().get("balance.dispamount") != null
																&& !response1.body().jsonPath().get("balance.dispamount").equals("")))
																? response1.body().jsonPath().get("balance.dispamount")
																		: "0";

																String apivalidatebalanceMeasure = (response1.body().jsonPath().get("balance") != null
																		&& (response1.body().jsonPath().get("balance.measure") != null
																		&& !response1.body().jsonPath().get("balance.measure").equals("")))
																		? response1.body().jsonPath().get("balance.measure")
																				: "0";

																		// INTERNET
																		String validatedataamount = (response1.body().jsonPath().get("data") != null
																				&& (response1.body().jsonPath().get("data.dispamount") != null
																				&& !response1.body().jsonPath().get("data.dispamount").equals("")))
																				? response1.body().jsonPath().get("data.dispamount")
																						: "0";

																				String validatedataMeasure = (response1.body().jsonPath().get("data") != null
																						&& (response1.body().jsonPath().get("data.measure") != null
																						&& !response1.body().jsonPath().get("data.measure").equals("")))
																						? response1.body().jsonPath().get("data.measure")
																								: "0";

																						/*String devide = validatedataamount;

		float roundOff;

		roundOff = (float) Integer.parseInt(devide) / 1024;
		// Float.toString(roundOff);
		BigDecimal a = new BigDecimal(roundOff);
		BigDecimal apivalidatedataamount = a.setScale(2, BigDecimal.ROUND_HALF_EVEN);*/
																						// System.out.println("Roundoff ==>" + apivalidatedataamount);
																						// apistrings.add(apivalidatebalanceexpiretime.replaceAll("[^0-9]", ""));

																						// VALIDITY
																						//String apivalidatebalanceexpiretime = response1.body().jsonPath().get("balance.expiretime");

																						Map<String, String> amountMap = new HashMap<String, String>();
																						amountMap.put("VOICE", apivoiceamount+"  "+apivoiceMeasure);
																						amountMap.put("SMS", apismsamount+"  "+apismsMeasure);
																						amountMap.put("BONUS", apibonusamount+"  "+apibonusMeasure);
																						amountMap.put("MOBILENO", apivalidatemobileno);
																						amountMap.put("YOUR BALANCE", apivalidatebalanceamount+" "+apivalidatebalanceMeasure);
																						amountMap.put("DATA", validatedataamount +"  "+validatedataMeasure);
																						//amountMap.put("VALIDITY", apivalidatebalanceexpiretime.replaceAll("[^0-9]", ""));



																						return amountMap;
	}


	/* -----------------------------------------------------------BYOP API --------------------------------------------------*/

	public String queryBYOPbody() throws JSONException {		

		Map<String, Object> byop = new LinkedHashMap<String, Object>();
		// JSONObject m1 = new JSONObject();

		byop.put("lang", "1");
		byop.put("channel", "odp");
		byop.put("wallet", "");

		Gson gsonbyop = new Gson();
		String byopjson = gsonbyop.toJson(byop);
		System.out.println("Byop body Values = " + byopjson);
		return byopjson;

	}

	public String SessionHashKeyBYOP() throws JSONException, InterruptedException, Exception {

		StringBuilder Saltkeybyop = salthashkey();



		String Hashbyop = queryBYOPbody();

		logger.log(LogStatus.INFO, "Request Body = "+Hashbyop );

		String inputbyop = Hashbyop + "&salt=" + Saltkeybyop;

		System.out.println("inputbyop ==>" + inputbyop);

		// getInstance() method is called with algorithm SHA-512
		MessageDigest md = MessageDigest.getInstance("SHA-512");

		// digest() method is called
		// to calculate message digest of the input string
		// returned as array of byte
		byte[] messageDigest = md.digest(inputbyop.getBytes());

		// Convert byte array into signum representation
		BigInteger no = new BigInteger(1, messageDigest);

		// Convert message digest into hex value
		String sha512 = no.toString(16);

		// Add preceding 0s to make it 32 bit
		while (sha512.length() <= 32) {
			sha512 = "0" + sha512;
		}

		System.out.println("sha512 ==>" + sha512);
		logger.log(LogStatus.INFO, "Request HashKey = "+sha512);

		return sha512;

	}

	public ArrayList<String> QueryBYOPBalance(String Ver) throws Exception {

		ArrayList<String> byopapistrings = new ArrayList<String>();
		//System.out.println("QueryBYOPBalance");

		//ecarepreprod.ooredoo.com.mm/selfcareapistg7.1/api/
		//RestAssured.baseURI = "http://ecarepreprod.ooredoo.com.mm/" + Ver + "/api/Home";

		//Live Request
		RestAssured.baseURI = "http://"+obj.getProperty("apiuri")+".ooredoo.com.mm/" + Ver + "/api/Home/querybyopbalance";

		logger.log(LogStatus.INFO, "Request URL = "+RestAssured.baseURI );


		// System.out.println("1");
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		// System.out.println("2");
		request.header("X-IMI-UID", "1569928420986");
		// System.out.println("3");
		request.header("X-IMI-OAUTH", LogiSessionid());
		// System.out.println("4");
		request.header("X-IMI-HASH", SessionHashKeyBYOP());
		// System.out.println("5");
		request.header("X-IMI-AUTHKEY", "APPYM8205D04");
		// System.out.println("6");
		request.body(queryBYOPbody());
		// System.out.println("7");
		Response response1 = request.post();
		// System.out.println((response1.asString()));
		String responsebodyBYOP = response1.asString();

		System.out.println("responsebodyBYOP ==> " + responsebodyBYOP);

		logger.log(LogStatus.INFO, "Request response = "+responsebodyBYOP);

		String res = response1.body().jsonPath().get("byopbanner") != null && response1.body().jsonPath().get("byopbanner.promobanners[0]") != null
				&& (response1.body().jsonPath().get("byopbanner.promobanners[0].img") != null
				&& !response1.body().jsonPath().get("byopbanner.promobanners[0].img").equals(""))
				? response1.body().jsonPath().get("byopbanner.promobanners[0].img")
						: "0";

				System.out.println("res ==> "+res);

				/*String byopapivalidatemobileno = (response1.body().jsonPath().get("byopbanner") != null && response1.body().jsonPath().get("byopbanner.promobanners[0]") != null
				&& (response1.body().jsonPath().get("byopbanner.promobanners[0].img") != null
						&& !response1.body().jsonPath().get("byopbanner.promobanners[0].img").equals("")))
								? response1.body().jsonPath().get("byopbanner.promobanners[0].img")
								: "0";*/

				//String byopapivalidatemobileno = res;

				//System.out.println("byopapivalidatemobileno ==> "+byopapivalidatemobileno);


				if (res == null || (res!=null && res.equalsIgnoreCase("0"))) {

					//System.out.println("BYOP already in Use");
					String AllCallDesc = response1.body().jsonPath().get("voice[0].desc");
					System.out.println("APIAllCall-Desc ==> " + AllCallDesc);

					String AllCallBalance = response1.body().jsonPath().get("voice[0].dispamount") + " " +response1.body().jsonPath().get("voice[0].measure");
					System.out.println("APIAllCall-BalanceAmount ==> " + AllCallBalance);		


					String AllCallIntialAmount = response1.body().jsonPath().get("voice[0].dispinitamt")+" "+response1.body().jsonPath().get("voice[0].initmeasure");
					System.out.println("APIAllCall-IntialAmount ==> " + AllCallIntialAmount);

					//String blance = AllCallBalanceAmount + " " + "/" + " " + AllCallIntialAmount;

					String OnCallDesc = response1.body().jsonPath().get("voice[1].desc");
					System.out.println("APIOnCall-Desc ==> " + OnCallDesc);

					String OnCallBalance = response1.body().jsonPath().get("voice[1].dispamount") +" "+response1.body().jsonPath().get("voice[1].measure");
					System.out.println("APIOnCall-BalanceAmount ==> " + OnCallBalance);

					String OnCallIntialAmount = response1.body().jsonPath().get("voice[1].dispinitamt") +" "+response1.body().jsonPath().get("voice[1].initmeasure");
					System.out.println("APIOnCall-IntialAmount ==> " + OnCallIntialAmount);

					//String onCallBal = OnCallBalanceAmount + " " + "/" + " " + OnCallIntialAmount;

					String ByopDataDesc = response1.body().jsonPath().get("data.desc");
					System.out.println("APIByop-DataDesc ==> " + ByopDataDesc);

					String ByopDataBalance = response1.body().jsonPath().get("data.dispamount")+" "+response1.body().jsonPath().get("data.measure");
					// String ByopDataBalanceAmount = ByopDataBalance.replaceAll("[^0-9]", "");

					String ByopDataIntial = response1.body().jsonPath().get("data.dispinitamt")+ " "+response1.body().jsonPath().get("data.initmeasure");
					System.out.println("APIByopData-IntialAmount ==> " + ByopDataIntial);
					/*String devide1 = ByopDataIntial.replaceAll("[^0-9]", "");

			float roundOff1;

			roundOff1 = (float) Integer.parseInt(devide1) / 1024;
			// Float.toString(roundOff);
			BigDecimal a1 = new BigDecimal(roundOff1);
			BigDecimal ByopDataIntialAmount = a1.setScale(2, BigDecimal.ROUND_HALF_EVEN);
			// System.out.println(apivalidatedataamount);
					 */


					//String ByopDataBalanceAmount = ByopDataBalance + " available out of " + ByopDataIntialAmount + " GB";
					//System.out.println("ByopDataBalanceAmountToIntial ==> " + ByopDataBalanceAmount);

					byopapistrings.add(AllCallDesc);
					byopapistrings.add(AllCallBalance);
					byopapistrings.add(AllCallIntialAmount);

					byopapistrings.add(OnCallDesc);
					byopapistrings.add(OnCallBalance);
					byopapistrings.add(OnCallIntialAmount);

					byopapistrings.add(ByopDataDesc);
					byopapistrings.add(ByopDataBalance);
					byopapistrings.add(ByopDataIntial);

					//System.out.println("APIbyopapistrings ==> " + byopapistrings);
					return byopapistrings;
				} else {

					System.out.println("BYOP Banner Available");

					String byopapivalidatemobileno = (response1.body().jsonPath().get("byopbanner") != null && response1.body().jsonPath().get("byopbanner.promobanners[0]") != null
							&& (response1.body().jsonPath().get("byopbanner.promobanners[0].img") != null
							&& !response1.body().jsonPath().get("byopbanner.promobanners[0].img").equals("")))
							? response1.body().jsonPath().get("byopbanner.promobanners[0].img")
									: "0";

							byopapistrings.add(byopapivalidatemobileno);

							return byopapistrings;
				}

	}


	/* -------------------------------------- Banner API -------------------------------------------------*/

	public String BannerOneBody(int bid) throws JSONException {

		Map<String, Object> bannerone = new LinkedHashMap<String, Object>();
		// JSONObject m1 = new JSONObject();

		bannerone.put("lang", "1");
		bannerone.put("channel", "odp");
		bannerone.put("bid", bid);
		bannerone.put("width", "100");
		bannerone.put("height", "100");
		bannerone.put("imsi", "404490297985288");
		if (bid == 1) {
			bannerone.put("reqtype", "all");
		} else {
			bannerone.put("reqtype", "banners");
		}

		Gson gsonbyop = new Gson();
		String byopjson = gsonbyop.toJson(bannerone);
		System.out.println("Banner Body Values = " + byopjson);
		return byopjson;
	}

	public String SessionHashKeyBannerOne(String body) throws JSONException, InterruptedException, Exception {

		StringBuilder Saltkeybyop = salthashkey();
		// String g = BannerOneBody();

		String Hashbyop = body;

		String inputbyop = Hashbyop + "&salt=" + Saltkeybyop;

		System.out.println("inputbyop ==>" + inputbyop);

		// getInstance() method is called with algorithm SHA-512
		MessageDigest md = MessageDigest.getInstance("SHA-512");

		// digest() method is called
		// to calculate message digest of the input string
		// returned as array of byte;
		byte[] messageDigest = md.digest(inputbyop.getBytes());

		// Convert byte array into signum representation
		BigInteger no = new BigInteger(1, messageDigest);

		// Convert message digest into hex value
		String sha512 = no.toString(16);

		// Add preceding 0s to make it 32 bit
		while (sha512.length() <= 32) {
			sha512 = "0" + sha512;
		}

		System.out.println("sha512 ==>" + sha512);

		return sha512.toString();
	}

	public Map<Integer, Integer> BanersApi(String Ver) throws JSONException, InterruptedException, Exception {

		RestAssured.baseURI = "http://ecarepreprod.ooredoo.com.mm/" + Ver + "/api/home";

		Map<Integer, Integer> sizes = new HashMap<Integer, Integer>();
		for (int y = 0; y < x.length; y++) {
			RequestSpecification request = RestAssured.given();
			request.header("X-IMI-HASH", SessionHashKeyBannerOne(BannerOneBody(x[y])));

			// System.out.println("1");
			request.header("Content-Type", "application/json");
			// System.out.println("2");
			request.header("X-IMI-UID", "1571803395739");
			// System.out.println("3");
			request.header("X-IMI-OAUTH", LogiSessionid());
			// System.out.println("4");

			// System.out.println("5");
			request.header("X-IMI-AUTHKEY", "APPYM8205D04");
			// System.out.println("7");
			request.body(BannerOneBody(x[y]));

			Response responsebaneone = request.post("/getbanners");
			String responsebodybannerone = responsebaneone.asString();

			System.out.println("responsebody ==> " + responsebodybannerone);
			List<Map<String, Object>> promobanners = responsebaneone.body().jsonPath().get("promobanners");
			sizes.put(x[y], promobanners.size());
			System.out.println("PromoBanners Count ==> " + promobanners.size());

		}

		return sizes;

	}


	/* ------------------------------------------------Common API ------------------------------------------------- */

	public String ETLiveTranBody(String ETLiveTrans, String TopSendPin) throws Exception {

		try {
			Map<String, Object> ETLiveTran = new LinkedHashMap<String, Object>();

			if (ETLiveTrans == "TransFee") {

				ETLiveTran.put("lang", "1");
				ETLiveTran.put("channel", "odp");

			} else if (ETLiveTrans == "UserProfile") {

				ETLiveTran.put("lang", "1");
				ETLiveTran.put("channel", "odp");
				ETLiveTran.put("nickname", "");
				ETLiveTran.put("type", "get");

			} else if (ETLiveTrans == "TopUpMyNumber") {

				ETLiveTran.put("lang", "1");
				ETLiveTran.put("channel", "odp");
				ETLiveTran.put("pin", mpinvalue(TopSendPin,"Voucher"));
				ETLiveTran.put("reqtype", "Manual");
				ETLiveTran.put("transid", "1573035302732");


			} else if (ETLiveTrans == "MpitesenPayment") {

				ETLiveTran.put("lang", "1");
				ETLiveTran.put("channel", "ODP");
				//ETLiveTran.put("amount", "2e5e895a"); -- 1000
				ETLiveTran.put("amount", "2d5e895a");

				ETLiveTran.put("mpitesennumber", "265b8053854fdfd146f894a1");
				ETLiveTran.put("mpin", "2e56895f");
				ETLiveTran.put("offerid", "");
				ETLiveTran.put("action", "topup");
				ETLiveTran.put("topuptype", "0");
				ETLiveTran.put("othernumber", "");		    

			}
			else if (ETLiveTrans == "GiftPackOffer") {

				setOffers();
				System.out.println("apioffid ==> "+apioffid);
				ETLiveTran.put("lang", "1");
				ETLiveTran.put("channel", "odp");
				ETLiveTran.put("bmsisdn",mpinvalue(TopSendPin,"GiftSheet") );	
				ETLiveTran.put("offerid", apioffid.get(0));
				ETLiveTran.put("offer", "2");	
				ETLiveTran.put("offertype", "DataPackage");			
				ETLiveTran.put("type", "gift");

			}

			else if (ETLiveTrans == "SetOffer") {

				setOffers();
				ETLiveTran.put("lang", "1");
				ETLiveTran.put("channel", "odp");
				ETLiveTran.put("offerid", apioffid.get(0));			
				ETLiveTran.put("offertype", "DataPackage");			
				ETLiveTran.put("type", "buypacks");
			}

			else {

				ETLiveTran.put("lang", "1");
				ETLiveTran.put("channel", "odp");
				ETLiveTran.put("type", "get");
			}

			Gson gsonbyop = new Gson();
			String ETLiveTranjson = gsonbyop.toJson(ETLiveTran);
			System.out.println(ETLiveTrans + " body Values = " + ETLiveTranjson);

			logger.log(LogStatus.INFO, "Request Body = "+ETLiveTranjson);
			return ETLiveTranjson;
		} catch(Exception ex) {
			System.out.println("Exception raised: " + ex);
			return "Somethig went wrong!";
		}

	}

	public String SessionHashKeyETLiveTrans(String body) throws JSONException, InterruptedException, Exception {

		StringBuilder Saltkeybyop = salthashkey();
		// String g = BannerOneBody();

		String Hashbyop = body;

		String inputbyop = Hashbyop + "&salt=" + Saltkeybyop;

		System.out.println(" HashKeyBody and Salt ==>" + inputbyop);

		// getInstance() method is called with algorithm SHA-512
		MessageDigest md = MessageDigest.getInstance("SHA-512");

		// digest() method is called
		// to calculate message digest of the input string
		// returned as array of byte;
		byte[] messageDigest = md.digest(inputbyop.getBytes());

		// Convert byte array into signum representation
		BigInteger no = new BigInteger(1, messageDigest);

		// Convert message digest into hex value
		String sha512 = no.toString(16);

		// Add preceding 0s to make it 32 bit
		while (sha512.length() <= 32) {
			sha512 = "0" + sha512;
		}

		System.out.println("sha512 ==>" + sha512);
		logger.log(LogStatus.INFO, "HashKey = "+sha512);

		return sha512.toString();
	}

	public List<String> ETLiveTransApi(String ETLiveTr, String ProTran, String Uid, String Ver, String resppath,
			String uri, String pathext, String TopSendPin) throws JSONException, InterruptedException, Exception {

		apirespETL.clear();
		System.out.println("ETLiveTr ==>"+ETLiveTr);
		RestAssured.baseURI = "http://" + uri + ".ooredoo.com.mm/" + Ver + "/api/" + ProTran+"/"+ETLiveTr;
		System.out.println("RestAssured.baseURI ==> " + RestAssured.baseURI);
		logger.log(LogStatus.INFO, "URL = "+RestAssured.baseURI);
		RequestSpecification request = RestAssured.given();	
		if(ETLiveTr.trim().equalsIgnoreCase("MpitesenPayment")) {
			System.out.println("Entered api");
			request.header("X-IMI-OAUTH", LogiSessionid());
			request.header("X-IMI-HASH", SessionHashKeyETLiveTrans(ETLiveTranBody(ETLiveTr, TopSendPin)));
			request.header("X-IMI-App-User-Agent", "IMImobile/Ooredoov2/7.1.0/40/Ooredoo");			
			request.header("X-IMI-App-OS", "Android");
			request.header("X-IMI-App-Res", "1080x1920");
			request.header("X-IMI-AUTHKEY", "APPYM8205D04");
			request.header("X-OS", "Android");
			request.header("X-IMI-UID", Uid);			
			request.header("Content-Type", "application/json");			
			request.body(ETLiveTranBody(ETLiveTr, TopSendPin));
			Response responseETL = request.post();
			System.out.println("Request Header ==> "+request);
			String responsebody = responseETL.asString();
			System.out.println(ETLiveTr + " Response ==> " + responsebody);
			//logger.log(LogStatus.PASS, "Request Headers = "+request);
			logger.log(LogStatus.INFO, "Response = "+responsebody);
			Object w = (responseETL.body().jsonPath().get(resppath) != null
					&& !responseETL.body().jsonPath().get(resppath).equals(""))
					? responseETL.body().jsonPath().get(resppath)
							: new ArrayList<Map<String, Object>>();		
					if (w != null) {
						if (w instanceof String) {
							apirespETL.add((String) w);
						}
						if (w instanceof List) {
							for (int h = 0; h < ((List<Map<String, Object>>) w).size(); h++) {
								String vasdetails = responseETL.body().jsonPath().get(resppath + "[" + h + "]." + pathext);//
								apirespETL.add(vasdetails);
							}
						}
					}


		} else {
			request.header("Content-Type", "application/json");
			// System.out.println("2");
			request.header("X-IMI-UID", Uid);
			// System.out.println("3");
			request.header("X-IMI-OAUTH", LogiSessionid());
			// System.out.println("4");
			// System.out.println("5");
			request.header("X-IMI-AUTHKEY", "APPYM8205D04");
			request.body(ETLiveTranBody(ETLiveTr, TopSendPin));
			// System.out.println("7");
			request.header("X-IMI-HASH", SessionHashKeyETLiveTrans(ETLiveTranBody(ETLiveTr, TopSendPin)));
			Response responseETL = request.post();
			String responsebody = responseETL.asString();
			System.out.println(ETLiveTr + " Response ==> " + responsebody);
			logger.log(LogStatus.INFO, "Response = "+responsebody);
			Object w = (responseETL.body().jsonPath().get(resppath) != null
					&& !responseETL.body().jsonPath().get(resppath).equals(""))
					? responseETL.body().jsonPath().get(resppath)
							: new ArrayList<Map<String, Object>>();
					//List<String> apirespETL = new ArrayList<>();
					if (w != null) {
						if (w instanceof String) {
							//apirespETL.clear();
							apirespETL.add((String) w);
							System.out.println("String API Result ==> "+apirespETL);
						}
						if (w instanceof List) {
							for (int h = 0; h < ((List<Map<String, Object>>) w).size(); h++) {
								String vasdetails = responseETL.body().jsonPath().get(resppath + "[" + h + "]." + pathext);//
								apirespETL.add(vasdetails);
								System.out.println("List API Result ==> "+apirespETL);

							}
						}
					}


		}
		System.out.println("Final API REsult ==>"+apirespETL);
		return apirespETL;
	}

}
