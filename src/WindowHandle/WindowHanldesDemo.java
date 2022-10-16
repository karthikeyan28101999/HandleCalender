package WindowHandle;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
public class WindowHanldesDemo {
	public static void main(String[] args) {	
		WebDriverManager.chromedriver().setup();
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get("https://www.snapdeal.com/");
		WebElement searchBox = driver.findElement(By.id("inputValEnter"));
		searchBox.sendKeys("mobile",Keys.ENTER);
		WebElement product = driver.findElement(By.xpath("(//div[@class='product-desc-rating ']//a)[3]"));
		product.click();
		Set<String> windows= driver.getWindowHandles();
		List<String> multipleWindows=new LinkedList<String>();
		multipleWindows.addAll(windows);
		String childWindow1 = multipleWindows.get(1);
		driver.switchTo().window(childWindow1);
		WebElement addToCartBtn = driver.findElement(By.id("add-cart-button-id"));
		addToCartBtn.click();
		WebElement trustPay = driver.findElement(By.xpath("(//div[@class='col-xs-6 individualTupple'])[2]"));
		trustPay.click();
		
		windows = driver.getWindowHandles();
		multipleWindows.clear();
		multipleWindows.addAll(windows);
		String child2 = multipleWindows.get(2);
		driver.switchTo().window(child2);
		WebElement text = driver.findElement(By.xpath("//h2[contains(text(),'WHAT DOES')]"));
		System.out.println(text.getText());
		driver.quit();
	}
}