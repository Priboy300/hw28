package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class SearchPage {
    private static final By DATE_START_BUTTON = By.xpath("//*[@data-testid=\"date-display-field-start\"]");
    private static final By DATE_END_BUTTON = By.xpath("//*[@data-testid=\"date-display-field-end\"]");

    private static final By DATE_START = By.xpath("//*[@data-date=\"2023-05-20\"]");
    private static final By DATE_END = By.xpath("//*[@data-date=\"2023-05-23\"]");
    private static final By SUBMIT_BUTTON = By.xpath("//*[@type=\"submit\"]");
    private static final By MAP_POLE = By.xpath("//*[@data-testid=\"map-trigger\" and @role = \"button\"]");
    private static final By MAP_BUTTON = By.xpath(".//*[text()=\"Показать на карте\"]");
    private static final By SORTED_BUTTON = By.xpath("//*[@data-selected-sorter=\"popularity\"]");

    @Step("Выбираем даты для поездки")
    public SearchPage chooseDate() {
        $(DATE_START_BUTTON).doubleClick();
        $(DATE_START).click();
        $(DATE_END_BUTTON).click();
        $(DATE_END).click();
        $(SUBMIT_BUTTON).click();
        return this;
    }

    @Step("Открываем карту с отелями")
    public SearchPage openMap() {
        $(MAP_POLE).find(MAP_BUTTON).click();
        $(SORTED_BUTTON).shouldBe(exist, Duration.ofSeconds(7));
        return this;
    }

}
