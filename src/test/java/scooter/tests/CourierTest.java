package scooter.tests;


import scooter.api.BaseApiTest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class CourierTest extends BaseApiTest {

    @Test
    @DisplayName("Успешное создание курьера")
    @Description("Проверка что курьера можно создать с валидными данными")
    public void testCreateCourierSuccess() {
        String login = getRandomLogin();
        String password = getRandomPassword();
        String firstName = getRandomFirstName();

        Response response = courierApi.createCourier(login, password, firstName);

        response.then()
                .statusCode(201)
                .body("ok", equalTo(true));

        // Удаляем созданного курьера
        String courierId = courierApi.getCourierId(login, password);
        courierApi.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void testCreateDuplicateCourier() {
        String login = getRandomLogin();
        String password = getRandomPassword();
        String firstName = getRandomFirstName();

        // Создаем первого курьера
        courierApi.createCourier(login, password, firstName);

        // Пытаемся создать второго с тем же логином
        Response response = courierApi.createCourier(login, "different_password", "different_name");

        response.then()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

        // Удаляем созданного курьера
        String courierId = courierApi.getCourierId(login, password);
        courierApi.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверка что без логина курьер не создается")
    public void testCreateCourierWithoutLogin() {
        Response response = courierApi.createCourierWithoutLogin("any_password", getRandomFirstName());

        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Проверка что без пароля курьер не создается")
    public void testCreateCourierWithoutPassword() {
        Response response = courierApi.createCourierWithoutPassword(getRandomLogin(), getRandomFirstName());

        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Успешный логин курьера")
    @Description("Проверка что курьер может авторизоваться с валидными данными")
    public void testLoginCourierSuccess() {
        String login = getRandomLogin();
        String password = getRandomPassword();
        String firstName = getRandomFirstName();

        // Создаем курьера
        courierApi.createCourier(login, password, firstName);

        // Логинимся
        Response response = courierApi.loginCourier(login, password);

        response.then()
                .statusCode(200)
                .body("id", notNullValue());

        // Удаляем курьера
        String courierId = courierApi.getCourierId(login, password);
        courierApi.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Логин с неверным паролем")
    @Description("Проверка что при неверном пароле возвращается ошибка")
    public void testLoginWithWrongPassword() {
        String login = getRandomLogin();
        String password = getRandomPassword();
        String firstName = getRandomFirstName();

        // Создаем курьера
        courierApi.createCourier(login, password, firstName);

        // Пытаемся логиниться с неверным паролем
        Response response = courierApi.loginCourier(login, "wrong_password");

        response.then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));

        // Удаляем курьера
        String courierId = courierApi.getCourierId(login, password);
        courierApi.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Логин без логина")
    @Description("Проверка что без логина авторизация невозможна")
    public void testLoginWithoutLogin() {
        Response response = courierApi.loginCourierWithoutLogin("any_password");

        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Логин без пароля")
    @Description("Проверка что без пароля авторизация невозможна")
    public void testLoginWithoutPassword() {
        Response response = courierApi.loginCourierWithoutPassword(getRandomLogin());

        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Логин несуществующего курьера")
    @Description("Проверка что авторизация под несуществующим пользователем возвращает ошибку")
    public void testLoginNonExistentCourier() {
        Response response = courierApi.loginCourier("nonexistent_login", "nonexistent_password");

        response.then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}