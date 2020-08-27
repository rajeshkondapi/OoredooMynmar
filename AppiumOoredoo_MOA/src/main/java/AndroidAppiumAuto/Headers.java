package AndroidAppiumAuto;

import java.io.File;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

public class Headers {
	
	@Test(description = "Headers",priority=0)
	public void m() {
	
	ChromeOptions  options = new ChromeOptions();
	
	//options.addExtensions(new File(""));

	// launch the browser
	System.setProperty("webdriver.chrome.driver","C:\\Users\\rajesh.k\\Desktop\\OoredooCode\\79driver\\chromedriver.exe");
	
	WebDriver driver1 = new ChromeDriver();
	
	
	driver1.get("http://stackoverflow.com/");

	// set the context on the extension so the localStorage can be accessed
	
	driver1.get("chrome-extension://idgpnmonknjnojddfkpgkljpfnnfcklj/icon.png");

	// setup ModHeader with two headers (token1 and token2)
	JavascriptExecutor js = (JavascriptExecutor) driver1;  
	
	
	js.executeScript(
	    "localStorage.setItem('profiles', JSON.stringify([{                " +
	    "  title: 'Selenium', hideComment: true, appendMode: '',           " +
	    "  headers: [                                                      " +
	    "    {enabled: true, name: 'token1', value: '01234', comment: ''}, " +
	    "    {enabled: true, name: 'token2', value: '56789', comment: ''}  " +
	    "  ],                                                              " +
	    "  respHeaders: [],                                                " +
	    "  filters: []                                                     " +
	    "}]));                                                             " );

	// visit a page
	driver1.get("http://stackoverflow.com/");

}
}
