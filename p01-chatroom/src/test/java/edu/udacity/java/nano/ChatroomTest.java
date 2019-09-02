package edu.udacity.java.nano;

import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;

/**
 * Guide: https://stackoverflow.com/questions/54599169/how-to-configure-selenium-webdriver-with-spring-boot-for-ui-testing
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ChatroomTest {

    @Autowired
    private Environment environment;

    private static String USERNAME = "testUser";
    private static String BASE_URL = "http://localhost:8080/";
    private static String CHAT_URL = BASE_URL + "index?username=" + USERNAME;

    private static WebDriver webDriver;

    @BeforeClass
    public static void setup() {
        String driverPath = "/usr/local/bin/chromedriver";
        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(driverPath))
                .build();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--start-minimized", "--disable-extensions", "--disable-dev-shm-usage", "--no-sandbox", "--disable-gpu");

        webDriver = new ChromeDriver(service, options);
    }

    @Test
    public void testLogin() {
        webDriver.get(BASE_URL);
        Assert.assertEquals(webDriver.getTitle(), "Chat Room Login");
    }

    @Test
    public void testJoin() {
        webDriver.get(BASE_URL);

        WebElement usernameInput = webDriver.findElement(By.id("username"));
        usernameInput.sendKeys(USERNAME);

        WebElement loginButton = webDriver.findElement(By.className("submit"));
        loginButton.click();

        String currentUrl = webDriver.getCurrentUrl();
        Assert.assertEquals(currentUrl, CHAT_URL);
    }

    @Test
    public void testChat() {
        String message = "Testing";

        webDriver.get(CHAT_URL);

        WebElement messageInput = webDriver.findElement(By.id("msg"));
        messageInput.sendKeys(message);

        WebElement sendButton = webDriver.findElement(By.id("send"));
        sendButton.click();

        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        wait.until(ExpectedConditions.numberOfElementsToBe(By.className("message-content"), 1));

        List<WebElement> messageElements = webDriver.findElements((By.className("message-content")));
        WebElement messageElement = messageElements.get(messageElements.size() - 1);
        Assert.assertEquals(USERNAME+"："+message, messageElement.getText());
    }

    @Test
    public void testLeave() {
        webDriver.get(CHAT_URL);
        webDriver.findElement(By.id("exit")).click();
        Assert.assertEquals(BASE_URL, webDriver.getCurrentUrl());
    }

    @AfterClass
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
