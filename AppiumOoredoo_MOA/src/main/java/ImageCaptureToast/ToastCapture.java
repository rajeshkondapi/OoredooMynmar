package ImageCaptureToast;

import java.io.File;
import AndroidAppiumAuto.ByDeclarations;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.LoadLibs;

public class ToastCapture extends ByDeclarations{
	
	
	
	//public AppiumDriver<MobileElement> Appdriver;
 	
 	
 	public String ToastMessage (File ScreenShot, String Expected_Value)
 	{
 		
 		try 
 		{
 			ITesseract image = new Tesseract();
 			
 			File tessDataFolder = LoadLibs.extractTessResources("tessdata");
 			image.setDatapath(tessDataFolder.getAbsolutePath());
 			String str = image.doOCR(ScreenShot);
 			//System.out.println("str==>"+str);
 		
 			if(str.contains(Expected_Value)) 
 			{				
 				return "True";
 			}
 			else
 			{

 				return "Fail";
 			}
 		}
 		catch (Exception e)
 		{

 			return e.getMessage();
 		}
 		
 	}
 	
 
		
	
	
	
	
	
	/* static String scrShotDir = "ScreenshotReport";
	  File scrFile;
	  static File scrShotDirPath = new java.io.File("./"+ scrShotDir+ "//");
	  String destFile;
	  //static AndroidDriver<MobileElement> appdriver;
	  static AndroidDriver driver = null;
	  
	  
	  
	 public String readToastMessage(DesiredCapabilities cap) throws TesseractException {
	  String imgName = takeScreenShot();
	  String result = null;
	  File imageFile = new File(scrShotDirPath, imgName);
	  System.out.println("Image name is :" + imageFile.toString());
	  ITesseract instance = new Tesseract(); 
	  
	  

	  File tessDataFolder = LoadLibs.extractTessResources("tessdata"); // Extracts
	                
	  instance.setDatapath(tessDataFolder.getAbsolutePath()); // sets tessData
	                // path

	  result = instance.doOCR(imageFile);
	  System.out.println(result);
	  return result;
	 }

	 *//**
	  * Takes screenshot of active screen
	  * 
	  * @return ImageFileName
	  *//*
	 public String takeScreenShot() {
		 TakesScreenshot ts2 = (TakesScreenshot) driver;
		 File src1 = ts2.getScreenshotAs(OutputType.FILE);
		 
	 // File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE); 
	  
	  SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
	  new File(scrShotDir).mkdirs(); // Create folder under project with name
	          // "screenshots" if doesn't exist
	  destFile = dateFormat.format(new Date()) + ".png"; // Set file name
	               // using current
	               // date time.
	  try {
	   FileUtils.copyFile(src1, new File(scrShotDir + "/" + destFile)); // Copy
	                    // paste
	                    // file
	                    // at
	                    // destination
	                    // folder
	                    // location
	  } catch (IOException e) {
	   System.out.println("Image not transfered to screenshot folder");
	   e.printStackTrace();
	  }
	  return destFile;
	 }
	 
	 
	 
	 
	 public class ToastMessagesCompare 
	 {
	 	//public WebDriver Webdriver;
	 	
	 }*/
	 
	 
	 
	 
	
	
	
	

}
