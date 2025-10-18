package scooter.api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.Before;


public class BaseApiTest {

    protected static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    protected CourierApi courierApi = new CourierApi();
    protected OrderApi orderApi = new OrderApi();

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.filters(
                new RequestLoggingFilter(),
                new ResponseLoggingFilter(),
                new AllureRestAssured()
        );
    }

    protected String getRandomLogin() {
        return "courier_" + System.currentTimeMillis();
    }

    protected String getRandomPassword() {
        return "password_" + System.currentTimeMillis();
    }

    protected String getRandomFirstName() {
        return "firstName_" + System.currentTimeMillis();
    }
}