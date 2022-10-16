package seleniumproject;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class HandleCalender {
	WebDriver driver;
	static JavascriptExecutor js;
	static Logger logger;
	@Parameters("browser")
	@BeforeTest
	public void initialize(@Optional("chrome")String browserName) {
		logger=LogManager.getLogger(HandleCalender.class.getName());
		switch (browserName) {
		case "chrome": {
			WebDriverManager.chromedriver().setup();
			logger.debug("initialize the chrome browser");
			driver = new ChromeDriver();
			break;
		}
		case "firefox": {
			WebDriverManager.firefoxdriver().setup();
			logger.debug("initialize the firefox browser");
			driver = new FirefoxDriver();
			break;
		}
		case "edge": {
			WebDriverManager.edgedriver().setup();
			logger.debug("initialize the edge browser");
			driver = new EdgeDriver();
			break;
		}
		default:
			System.out.println("browser name mismatch");
			logger.error("browser name mismatch");
		}
	}

	@AfterTest
	public void quit() throws Exception {
		Thread.sleep(1000);
		driver.quit();
		logger.debug("quit the browser");
	}

	@Test
	public void handleCalender() throws Exception {
		driver.manage().window().maximize();
		driver.get("https://www.path2usa.com/travel-companions");
		logger.debug("landing in application url");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5000));
		WebElement calenderBtn = driver.findElement(By.id("form-field-travel_comp_date"));
		js = (JavascriptExecutor) driver;
		js.executeScript("document.getElementById('form-field-travel_comp_date').scrollIntoView()");
		logger.debug("click the calender button");
		Thread.sleep(300);
		calenderBtn.click();
		String actualDate = getCurentMonthAndYear(driver);
		clickDate(actualDate, "28 october 2023", driver);
		Thread.sleep(500);
		String finall=(String)(js.executeScript("return document.getElementById"
				+ "('form-field-travel_comp_date').value"));
		validate("10/28/2023", finall);
	}

	public static String getCurentMonthAndYear(WebDriver driver) {
		WebElement currentmonth = driver.findElement(By.cssSelector(".flatpickr-month .flatpickr-current-month"));
		String actualMonth = currentmonth.getText();
		logger.debug("get current month and year");
		WebElement currentYear = driver.findElement(By.cssSelector(".cur-year"));
		js = (JavascriptExecutor) driver;
		String actualYear = (String) js.executeScript("return arguments[0].value", currentYear);
		String actualDate = actualMonth + " " + actualYear;
		System.out.println(actualDate);
		return actualDate;
	}

	public static void clickDate(String actualDate, String targetDate, WebDriver driver) throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new SimpleDateFormat("dd MMM yyyy").parse(targetDate));

		int targetDay = calendar.get(Calendar.DAY_OF_MONTH);
		int targetMonth = calendar.get(Calendar.MONTH);
		int targetYear = calendar.get(Calendar.YEAR);

		calendar.setTime(new SimpleDateFormat("MMM yyyy").parse(actualDate));
		int actualMonth = calendar.get(Calendar.MONTH);
		int actualYear = calendar.get(Calendar.YEAR);

		while (targetMonth > actualMonth || targetYear > actualYear) {
			driver.findElement(By.className("flatpickr-next-month")).click();
			logger.debug("click the next month button");
			Thread.sleep(400);
			actualDate = getCurentMonthAndYear(driver);
			calendar.setTime(new SimpleDateFormat("MMM yyyy").parse(actualDate));
			actualMonth = calendar.get(Calendar.MONTH);
			actualYear = calendar.get(Calendar.YEAR);
		}

		List<WebElement> dates = driver
				.findElements(By.xpath("//span[@class='flatpickr-day disabled' or @class='flatpickr-day ']"));
		logger.debug("get all the date from the calender");
		for (WebElement date : dates) {
			int actdate = Integer.valueOf(date.getText());
			if (actdate == targetDay) {
				date.click();
				logger.info("click the given date");
				break;
			}
		}
	}
	public static void validate (String actual ,String expect)
	{
		  logger.info("check actual and expect value eithe equal or not");
	      Assert.assertEquals(actual, expect);	
	      logger.info("both are equal");
	}
}
