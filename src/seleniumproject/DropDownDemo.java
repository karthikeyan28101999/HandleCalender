package seleniumproject;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DropDownDemo {

	public static void main(String[] args) {
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
		
		driver.get("https://www.hyrtutorials.com/p/html-dropdown-elements-practice.html");
		
		WebElement courseButton = driver.findElement(By.id("course"));
		courseButton.click();
		
		Select slct=new Select(courseButton);
		List<WebElement> options = slct.getOptions();
		
		for (WebElement option: options) {
			 option.getText();
		}
	}

}
