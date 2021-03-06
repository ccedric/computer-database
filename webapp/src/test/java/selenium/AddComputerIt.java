package selenium;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class AddComputerIt {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  /**
   * Set up before the test.
   */
  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8081/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testAddComputer() throws Exception {
    driver.get(baseUrl + "/computerDB/login");
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("admin");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("admin");
    driver.findElement(By.name("submit")).click();
    driver.findElement(By.id("addComputer")).click();
    driver.findElement(By.id("name")).clear();
    driver.findElement(By.id("name")).sendKeys("test");
    driver.findElement(By.id("introduced")).clear();
    driver.findElement(By.id("introduced")).sendKeys("1990-10-10");
    driver.findElement(By.id("discontinued")).clear();
    driver.findElement(By.id("discontinued")).sendKeys("ghnnb");
    driver.findElement(By.id("discontinued")).clear();
    driver.findElement(By.id("discontinued")).sendKeys("1985-10-10");
    driver.findElement(By.id("submit")).click();
    driver.findElement(By.id("discontinued")).clear();
    driver.findElement(By.id("discontinued")).sendKeys("1995-10-10");
    new Select(driver.findElement(By.id("companyId"))).selectByVisibleText("Nokia");
    driver.findElement(By.id("name")).clear();
    driver.findElement(By.id("name")).sendKeys("");
    driver.findElement(By.id("submit")).click();
    driver.findElement(By.id("name")).clear();
    driver.findElement(By.id("name")).sendKeys("test");
    driver.findElement(By.id("submit")).click();
    // assertTrue(isElementPresent(By.id("computerCreated")));
    // driver.findElement(By.xpath("(//button[@name='number-results'])[3]")).click();
  }

  /**
   * Tear down after the test.
   */
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
