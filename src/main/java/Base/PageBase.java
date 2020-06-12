package Base;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static Base.Driver.Driver.getWebDriver;


import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PageBase extends Base{
	
	public static WebDriver driver;
	
	static {
		driver = getWebDriver();
	}

	//Explicit wait for visibility of the element
		public static boolean waitForIsDisplayed(WebElement element){
			WebDriverWait wait = new WebDriverWait(driver, 10);
			
			if (wait.until(ExpectedConditions.visibilityOf(element)) != null){
				return true;
			}
			
			return false;
		}
		
		public static boolean waitForElementClickable(WebElement element){
			
			WebDriverWait waitIsClickable = new WebDriverWait(driver, 10);
			
			if (waitIsClickable.until(ExpectedConditions.elementToBeClickable(element)) != null){
				return true;
			}
			
			return false;
			
		}
		
		/**
		 * Description: Captures full screen shot using RobotFramework 
		 * @return
		 */
		public boolean captureScreenShotUsingRobotFramework(){
			
			try {
				BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
				long currentTime = System.currentTimeMillis();
				System.out.println("Screen capture happened at: "+currentTime);
				
				ImageIO.write(image, "jpeg", new File("./ScreenShots/image_"+currentTime+".jpeg"));
				
			} catch (HeadlessException e) {
				System.out.println("Screen capture using Robot framework failed due to "+e.getMessage());
				return false;
			} catch (AWTException e) {
				System.out.println("Screen capture using Robot framework failed due to "+e.getMessage());
				return false;
			} catch (IOException e) {
				System.out.println("Screen capture using Robot framework failed due to "+e.getMessage());
				return false;
			}
			
			return true;
		}
		
		
		public static boolean takesScreenshot(String sScreenshotName){
			
			TakesScreenshot screenshot = (TakesScreenshot)driver;
			File file = screenshot.getScreenshotAs(OutputType.FILE);
			
			long currentTime = System.currentTimeMillis();
			try {
				FileUtils.copyFile(file, new File("./ScreenShot\\"+sScreenshotName+"_"+currentTime+".png"));
			} catch (IOException e) {
				System.out.println("Screen capture using TakesScreenshot class, failed due to "+e.getMessage());
				return false;
			}
			
			return true;
		}
}
