package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class MapForm {

    private static final String HOTEL_IN_LIST = "(//*[@class=\"map_left_cards map_left_cards--v2\"]//a)[%s]";
    private static final By HOTEL_NAME = By.xpath(".//*[@data-testid=\"header-title\" or @class=\"map-card__title-link\"]");
    private static final By HOTEL_STARS = By.xpath(".//*[@data-testid=\"rating-stars\" or @class=\"bui-rating bui-rating--smaller\"]/span  ");
    private static final By HOTEL_AVG_RATING = By.cssSelector("[aria-label*=\"Оценка\"]");
    private static final By HOTEL_REVIEWS = By.xpath(".//*[@class=\"d8eab2cf7f c90c0a70d3 db63693c62\" or @class=\"bui-review-score__text\" ]");
    private static final By HOTEL_PRICE = By.xpath(".//*[@class=\"fcab3ed991 bd73d13072\" or @class=\"prco-valign-middle-helper\"]");

    private static final By ATLAS_MARKER = By.cssSelector("div.svg-marker.svg-poi.atlas-marker.hotel.active.fading.bounce");
    private static final By NP_HOTEL_NAME = By.xpath("//*[@class=\"d2fee87262 pp-header__title\"]");
    private static final By NP_HOTEL_AVG_RATING = By.xpath("//*[@class=\"b5cd09854e d10a6220b4\"]");
    private static final By NP_HOTEL_STARS = By.xpath("//*[@data-testid=\"rating-stars\"]/span");
    private static final By NP_HOTEL_REVIEWS = By.xpath("//*[@class=\"d8eab2cf7f c90c0a70d3 db63693c62\"]");
    private static final By NP_HOTEL_PRICE = By.xpath("//*[@class=\"prco-valign-middle-helper\"]");

    @Step("Собираем информацию об отеле, находящегося под номером {0}, со страницы с открытой картой")
    public Map<String, String> collect_data_hotel(int num) {
        Map<String, String> dictionary = new LinkedHashMap<>();
        sleep(7000); // если использовать should... то иногда отель не находится, хотя время ожидания увеличивал
        ElementsCollection hotelData = $$(By.xpath(String.format(HOTEL_IN_LIST, num)));

        for (SelenideElement hotel : hotelData) {
            String name = hotel.$(HOTEL_NAME).getText();
            String avg = hotel.$(HOTEL_AVG_RATING).getText();
            String reviews = hotel.$(HOTEL_REVIEWS).getText();
            reviews = reviews.replaceAll("\\s", "");
            String cost = hotel.$(HOTEL_PRICE).shouldBe(exist, Duration.ofSeconds(7)).getText();
            cost = cost.replaceAll("\\s", "");
            int stars = hotel.$$(HOTEL_STARS).size();

            dictionary.put("hotel_name", name);
            dictionary.put("hotel_avg", avg);
            dictionary.put("hotel_reviews", reviews);
            dictionary.put("hotel_cost", cost);
            dictionary.put("hotel_stars", String.valueOf(stars));

        }
        $(By.xpath(String.format(HOTEL_IN_LIST, num))).shouldBe(visible, Duration.ofSeconds(10)).hover();
        $(ATLAS_MARKER).shouldBe(visible, Duration.ofSeconds(25)).click();
        return dictionary;
    }

    @Step("Собираем информацию об отеле с заглавной страницы данного отеля")
    public Map<String, String> collect_data_np_hotel() {
        Map<String, String> dictionary = new LinkedHashMap<>();
        switchTo().window(1);

        String name = $(NP_HOTEL_NAME).getText();
        String avg = $(NP_HOTEL_AVG_RATING).getText();
        String reviews = $(NP_HOTEL_REVIEWS).getText();
        reviews = reviews.replaceAll("\\s", "");
        String cost = $(NP_HOTEL_PRICE).getText();
        cost = cost.replaceAll("\\s", "");
        int stars = $$(NP_HOTEL_STARS).size();

        dictionary.put("hotel_name", name);
        dictionary.put("hotel_avg", avg);
        dictionary.put("hotel_reviews", reviews);
        dictionary.put("hotel_cost", cost);
        dictionary.put("hotel_stars", String.valueOf(stars));

        switchTo().window(1).close();
        switchTo().window(0).close();
        return dictionary;
    }


}
