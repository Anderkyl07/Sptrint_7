package scooter.api;

import io.restassured.response.Response;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class CourierApi {

    private static final String COURIER_PATH = "/api/v1/courier";
    private static final String LOGIN_PATH = "/api/v1/courier/login";

    // Основной метод создания курьера
    public Response createCourier(String login, String password, String firstName) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("login", login);
        requestBody.put("password", password);
        if (firstName != null) {
            requestBody.put("firstName", firstName);
        }

        return given()
                .header("Content-type", "application/json")
                .body(requestBody.toString())
                .when()
                .post(COURIER_PATH);
    }

    // Метод для теста без логина
    public Response createCourierWithoutLogin(String password, String firstName) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("login", "");  // Пустой логин
        requestBody.put("password", password);
        if (firstName != null) {
            requestBody.put("firstName", firstName);
        }

        return given()
                .header("Content-type", "application/json")
                .body(requestBody.toString())
                .when()
                .post(COURIER_PATH);
    }

    // Метод для теста без пароля
    public Response createCourierWithoutPassword(String login, String firstName) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("login", login);
        requestBody.put("password", "");  // Пустой пароль
        if (firstName != null) {
            requestBody.put("firstName", firstName);
        }

        return given()
                .header("Content-type", "application/json")
                .body(requestBody.toString())
                .when()
                .post(COURIER_PATH);
    }

    // Основной метод логина
    public Response loginCourier(String login, String password) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("login", login);
        requestBody.put("password", password);

        return given()
                .header("Content-type", "application/json")
                .body(requestBody.toString())
                .when()
                .post(LOGIN_PATH);
    }

    // Метод для теста логина без логина
    public Response loginCourierWithoutLogin(String password) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("login", "");  // Добавляем пустой логин
        requestBody.put("password", password);

        return given()
                .header("Content-type", "application/json")
                .body(requestBody.toString())
                .when()
                .post(LOGIN_PATH);
    }

    // Метод для теста логина без пароля
    public Response loginCourierWithoutPassword(String login) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("login", login);
        requestBody.put("password", "");  // Добавляем пустой пароль

        return given()
                .header("Content-type", "application/json")
                .body(requestBody.toString())
                .when()
                .post(LOGIN_PATH);
    }

    public Response deleteCourier(String courierId) {
        return given()
                .when()
                .delete(COURIER_PATH + "/" + courierId);
    }

    public String getCourierId(String login, String password) {
        Response response = loginCourier(login, password);
        return response.then().extract().path("id").toString();
    }
}
