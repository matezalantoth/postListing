package org.springbootapp.components.browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springbootapp.pages.LoginPage;
import org.springbootapp.components.updaters.UserUpdater;
import org.springbootapp.enums.UserStatus;
import org.springbootapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
@Component
public class FacebookBrowser {

    @Autowired
    private UserUpdater userUpdater;
    @Value("${chrome.driver.path}")
    String chromePath;
    private LinkedHashMap<String, WebDriver> drivers;

    public FacebookBrowser() {
        drivers = new LinkedHashMap<>();

    }

    public WebDriver getBrowser(User user) throws Exception {
        String email = user.getEmail();
        if (drivers.containsKey(email)) {
            return drivers.get(email);
        }
        return createAndLogin(user);

    }

    protected WebDriver createAndLogin(User user) throws Exception {

        if (user.getStatus() == UserStatus.INVALID) {
            throw new Exception("User INVALID");
        }

        String email = user.getEmail();
        String password = user.getPassword();

        String path = "";

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--log-path=" + path + chromePath);
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--no-sandbox");
        System.setProperty("webdriver.chrome.driver", path + chromePath);
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();


        try {

            LoginPage loginPage = new LoginPage(driver);
            loginPage.login(email, password);

        } catch (Exception e) {
            userUpdater.updateStatus(user, UserStatus.UNDER_REVIEW);
            throw e;
        }

        drivers.put(email, driver);

        return driver;

    }

    public void closeBrowser(User user) {
        String email = user.getEmail();
        if (!drivers.containsKey(email)) {
            return;
        }
        WebDriver driver = drivers.get(email);
        driver.close();
        drivers.remove(email);

    }

}
