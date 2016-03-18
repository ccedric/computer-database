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
public class OrderByIt {
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
  public void testOrderByIt() throws Exception {
    driver.get(baseUrl + "/computerDB/login");
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("admin");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("admin");
    driver.findElement(By.name("submit")).click();
    driver.findElement(By.linkText("Computer name")).click();
    driver.findElement(By.linkText("Computer name")).click();
    driver.findElement(By.linkText("Introduced date")).click();
    driver.findElement(By.linkText("Introduced date")).click();
    driver.findElement(By.linkText("Discontinued date")).click();
    driver.findElement(By.linkText("Discontinued date")).click();
    driver.findElement(By.linkText("Company")).click();
    driver.findElement(By.linkText("Discontinued date")).click();
    driver.findElement(By.linkText("2")).click();
    driver.findElement(By.linkText("3")).click();
    driver.findElement(By.linkText("Discontinued date")).click();
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
