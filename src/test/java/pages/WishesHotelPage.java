package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.*;

public class WishesHotelPage {

    private static final By HOTELS_LIST = By.xpath("//*[@class=\"d4924c9e74\"]");
    private static final String WISHES_HOTEL_BUTTON = "(.//*[@data-testid=\"wishlist-button\"])[%s]";
    private static final String WISHES_HOTEL_NAME = "(//*[@data-testid=\"title\"])[%s]";
    private static final By LINK_TO_WISHES_PAGE = By.xpath(".//*[contains(span,\"My next trip\")]");
    private static final By WISHES_PAGE_HOTEL_NAME = By.xpath("//*[@class=\"js-listview-book js-listview-hotel-title\"]");

    @Step("Добавляем отель под номером {0} в список 'My next trip'")
    public ArrayList<String> add_wishes_hotel(int num) {
        ArrayList<String> arr = new ArrayList<>();
        String hotel_name_base_page = $(By.xpath(String.format(WISHES_HOTEL_NAME, num))).getText();
        arr.add(hotel_name_base_page);
        ElementsCollection hotel_list = $$(HOTELS_LIST);

        for (SelenideElement hl : hotel_list) {
            hl.$(By.xpath(String.format(WISHES_HOTEL_BUTTON, num))).click();
        }
        $(LINK_TO_WISHES_PAGE).click();
        switchTo().window(1);
        String hotel_name_wishes_page = $(WISHES_PAGE_HOTEL_NAME).getText();
        arr.add(hotel_name_wishes_page);

        return arr;
    }


}
