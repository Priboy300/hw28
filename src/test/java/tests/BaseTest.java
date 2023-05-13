package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.ProjectConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.After;
import org.junit.Before;


public class BaseTest {

    public static final ProjectConfig config = ConfigFactory.create(ProjectConfig.class);

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        Configuration.browser = "chrome";
        Configuration.driverManagerEnabled = true;
        Configuration.headless = true;
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 5000;
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        System.setProperty("chromeoptions.args", "\"--no-sandbox\",\"--disable-dev-shm-usage\",\"--remote-debugging-port=9222\"");
    }

    @After
    public void turnDown() {
        Selenide.closeWebDriver();
    }

}
