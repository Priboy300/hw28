package tests;

import io.qameta.allure.*;
import org.assertj.core.api.SoftAssertions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.HomePage;
import pages.WishesHotelPage;
import pages.SearchPage;
import com.fasterxml.jackson.databind.ObjectMapper;
import test_data_class.SecondTestData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Epic("Web testing")
@Feature("Booking site")
@Story("Пользовательский сценарий 2: Пользователь добавляет понравившийся отельв список 'My next trip' и после просматривает данный список.")
@Owner("Tomas T")
public class SecondTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Проверка списка 'My next trip'")
    @Description("Проверяем, что нужный отель добавляется в список 'My next trip'")
    @Severity(SeverityLevel.NORMAL)
    public void checkFavoritesList() throws IOException {
        File file = new File("src/main/resources/test_data/second_data.json");
        SecondTestData testData = objectMapper.readValue(file, SecondTestData.class);
        SoftAssertions softAssert = new SoftAssertions();
        HomePage home_page = new HomePage();
        SearchPage search_page = new SearchPage();
        WishesHotelPage hotel_page = new WishesHotelPage();

        home_page.openPage()
                .acceptCookies()
                .findByCity(testData.getCityName());

        search_page.chooseDate();
        ArrayList<String> arr = hotel_page.add_wishes_hotel(testData.getHotel_number());

        softAssert.assertThat(arr.get(0)).as("Значения элементов не совпадают")
                .isEqualTo(arr.get(1));
    }

}
