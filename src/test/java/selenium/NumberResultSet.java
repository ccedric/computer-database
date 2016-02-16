package selenium;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

@SuppressWarnings("unused")
public class NumberResultSet {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://localhost:8080/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testNumberResultSet() throws Exception {
		driver.get(baseUrl + "/computerDB/dashboard?page=5&numberResults=50&search=");
		driver.findElement(By.name("numberResults")).click();
		driver.findElement(By.xpath("(//button[@name='numberResults'])[2]")).click();
		driver.findElement(By.xpath("(//button[@name='numberResults'])[3]")).click();
		driver.findElement(By.linkText("5")).click();
		driver.findElement(By.linkText("6")).click();
		driver.findElement(By.xpath("(//button[@name='numberResults'])[2]")).click();
		driver.findElement(By.cssSelector("a > span")).click();
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
