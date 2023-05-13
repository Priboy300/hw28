package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.HomePage;
import pages.MapForm;
import pages.SearchPage;
import org.assertj.core.api.SoftAssertions;
import test_data_class.FirstTestData;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Epic("Web testing")
@Feature("Booking site")
@Story("Пользовательский сценарий 1: Пользователь просматривает информацию об отеле на разных страницах сайта")
@Owner("Tomas T")
public class FirstTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test()
    @DisplayName("Проверка информации об отеле")
    @Description("Проверяем, что информация об отеле, собранная с разных страниц, совппадает")
    @Severity(SeverityLevel.NORMAL)
    public void checkMapsData() throws IOException {
        File file = new File("src/main/resources/test_data/first_data.json");
        FirstTestData testData = objectMapper.readValue(file, FirstTestData.class);
        SoftAssertions softAssert = new SoftAssertions();
        HomePage home_page = new HomePage();
        SearchPage search_page = new SearchPage();
        MapForm map_form = new MapForm();

        home_page.openPage()
                .acceptCookies()
                .findByCity(testData.getCityName());

        search_page.chooseDate()
                .openMap();

        Map<String, String> map = map_form.collect_data_hotel(testData.getHotel_number());
        Map<String, String> np_map = map_form.collect_data_np_hotel();
        softAssert.assertThat(map).as("Значения элементов не совпадают")
                .isEqualTo(np_map);
        softAssert.assertAll();

        System.out.println(Collections.singletonList(map));  //  Использовал для проверки
        System.out.println(Collections.singletonList(np_map));

    }

}
