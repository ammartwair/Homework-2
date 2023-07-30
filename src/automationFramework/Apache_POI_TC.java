package automationFramework;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import pageObjects.*;
import utility.Constant;
import utility.ExcelUtils;
public class Apache_POI_TC {
	private static WebDriver driver = null;
	public static void main(String[] args) throws Exception{
		// This is to open the Excel file. Excel path, file name and the sheet name are parameters to this method
	ExcelUtils.setExcelFile(Constant.Path_TestData + Constant.File_TestData, "Sheet1");
	driver = new ChromeDriver();
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	driver.get(Constant.URL);
	WebElement ele = driver.findElement(By.cssSelector("a.toggle-menu"));
	Actions action = new Actions(driver);
	action.moveToElement(ele).perform();
	driver.findElement(By.xpath("/html/body/div[2]/header/nav/div[1]/div/div/div/div[3]/ul/li/ul/li[1]/a")).click();
	boolean isValid = false;
	int countp = 0;
	int countf = 0;
	int i = 1;
	while(true) {
		if(isValid) {
			WebElement elem = driver.findElement(By.cssSelector("a.toggle-menu"));
			Actions act = new Actions(driver);
			act.moveToElement(elem).perform();
			driver.findElement(By.xpath("/html/body/div[2]/header/nav/div[1]/div/div/div/div[3]/ul/li/ul/li[1]/a")).click();
		}
		String sEmail = ExcelUtils.getCellData(i, 1);
		String sPassword = ExcelUtils.getCellData(i,2);
		LogIn_Page.txtbx_UserName(driver).sendKeys(sEmail);
		LogIn_Page.txtbx_Password(driver).sendKeys(sPassword);
		LogIn_Page.btn_LogIN(driver).click();
		try {
			String text = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/h1")).getText();
			if(text.equals("My Account")){
				ExcelUtils.setCellData("Pass", i, 3);
				isValid=true;
				countp++;
				WebElement elem = driver.findElement(By.cssSelector("a.toggle-menu"));
				Actions act = new Actions(driver);
				act.moveToElement(elem).perform();
				driver.findElement(By.xpath("/html/body/div[2]/header/nav/div[1]/div/div/div/div[3]/ul/li/ul/li[3]/a")).click();
			}else{
				isValid = false;
				countf++;
				ExcelUtils.setCellData("Fail", i, 3);
				LogIn_Page.txtbx_UserName(driver).clear();
				LogIn_Page.txtbx_Password(driver).clear();
			}
		}
		catch (Exception e) {
			isValid = false;
			countf++;
			ExcelUtils.setCellData("Fail", i, 3);
			LogIn_Page.txtbx_UserName(driver).clear();
			LogIn_Page.txtbx_Password(driver).clear();
		}
		i++;
		if(ExcelUtils.getCellData(i, 1)==""){
			break;
		}
	}
	System.out.println("The count of all users is: "+(countp+countf));
	System.out.println("PASS: "+countp + ", FAIL: "+countf); 
	System.out.println("Finished checking all users successfully, check the TestData.xlsx file");
	driver.quit();
	}
}
