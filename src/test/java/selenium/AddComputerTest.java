 package selenium;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class AddComputerTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8080";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testAddComputer() throws Exception {
    driver.get(baseUrl + "/computerDB/dashboard");
    driver.findElement(By.id("addComputer")).click();
    driver.findElement(By.id("submit")).click();
    driver.findElement(By.id("computerName")).clear();
    driver.findElement(By.id("computerName")).sendKeys("test");
    driver.findElement(By.id("introduced")).clear();
    driver.findElement(By.id("introduced")).sendKeys("gdsf");
    driver.findElement(By.id("discontinued")).clear();
    driver.findElement(By.id("discontinued")).sendKeys("dfgsdf");
    driver.findElement(By.id("introduced")).clear();
    driver.findElement(By.id("introduced")).sendKeys("1994-10-10 12:12");
    driver.findElement(By.id("discontinued")).clear();
    driver.findElement(By.id("discontinued")).sendKeys("1990-10-10 12:12");
    driver.findElement(By.id("submit")).click();
    
	assertEquals(driver.getCurrentUrl(), "http://localhost:8080/computerDB/addComputer");
	
    driver.findElement(By.id("discontinued")).clear();
    driver.findElement(By.id("discontinued")).sendKeys("1999-10-10 12:12");
    new Select(driver.findElement(By.id("companyId"))).selectByVisibleText("Thinking Machines");	
    driver.findElement(By.id("submit")).click();
    driver.findElement(By.cssSelector("button.close")).click();
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