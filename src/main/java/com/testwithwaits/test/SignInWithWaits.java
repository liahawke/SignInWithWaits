package com.testwithwaits.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class SignInWithWaits {

    private WebDriver driver;
    private WebDriverWait wait;
    private final String URL = "https://s1.demo.opensourcecms.com/s/44";
    private final String NEW_URL = "https://s2.demo.opensourcecms.com/orangehrm/";
    String INVALID_MESSAGE = "Invalid credentials";
    String EMPTY_USERNAME_FIELD = "Username cannot be empty";
    String EMPTY_PASSWORD_FIELD = "Password cannot be empty";

    @FindBy(xpath = "//input[@id='txtUsername']")
    private WebElement login;

    @FindBy(xpath = "//input[@id='txtPassword']")
    private WebElement password;

    @FindBy(xpath = "//input[@id='btnLogin']")
    private WebElement loginBtn;

    @FindBy(xpath = "//span[@id='spanMessage']")
    private WebElement invalidMessage;

    @FindBy(xpath = "//div[@class='preview__action--close']/a")
    private WebElement frameLink;


    /**
     * Set up method to initialize driver and WebDriverWait
     */
    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
    }

    /**
     * Login to page with waits and checking error messages
     *
     */
    @Test
    public void loginToPage(){
        driver.get(URL);
        PageFactory.initElements(driver, this);
        driver.switchTo().frame("preview-frame");
        wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOf(login));
        login.click();
        login.sendKeys("username");
        password.click();
        password.sendKeys("password");
        loginBtn.click();
        Assert.assertEquals("Error message isn't as expected", INVALID_MESSAGE, invalidMessage.getText());
         loginBtn.click();
        Assert.assertEquals("Error message isn't as expected", EMPTY_USERNAME_FIELD, invalidMessage.getText());
        login.click();
        login.sendKeys("blablabla");
        loginBtn.click();
        Assert.assertEquals("Error message isn't as expected", EMPTY_PASSWORD_FIELD, invalidMessage.getText());
        driver.switchTo().defaultContent();
        frameLink.click();
        Assert.assertFalse("Frame is displaying", driver.getCurrentUrl().equals(NEW_URL));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    /**
     * Quit WebDriver
     */
    @After
    public void tearDown() {
        driver.quit();
    }
}