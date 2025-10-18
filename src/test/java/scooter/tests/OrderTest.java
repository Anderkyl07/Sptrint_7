package scooter.tests;

import scooter.api.BaseApiTest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class OrderTest extends BaseApiTest {

    private final String[] color;

    public OrderTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getColorData() {
        return new Object[][] {
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {null}
        };
    }

    @Test
    @DisplayName("Создание заказа с разными цветами")
    @Description("Проверка создания заказа с параметризованными цветами: BLACK, GREY, оба цвета, без цвета")
    public void testCreateOrderWithDifferentColors() {
        Response response = orderApi.createOrder(
                "Иван",
                "Иванов",
                "ул. Пушкина, д. 10",
                "4",
                "+79991234567",
                3,
                "2024-12-31",
                "Позвонить за час",
                color
        );

        response.then()
                .statusCode(201)
                .body("track", notNullValue());
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка что в теле ответа возвращается список заказов")
    public void testGetOrdersList() {
        Response response = orderApi.getOrdersList();

        response.then()
                .statusCode(200)
                .body("orders", notNullValue())
                .body("pageInfo", notNullValue())
                .body("availableStations", notNullValue());
    }
}