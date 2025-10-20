package scooter.api;

import io.restassured.response.Response;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class OrderApi {

    private static final String ORDER_PATH = "/api/v1/orders";

    public Response createOrder(String firstName, String lastName, String address,
                                String metroStation, String phone, int rentTime,
                                String deliveryDate, String comment, String[] color) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("firstName", firstName);
        requestBody.put("lastName", lastName);
        requestBody.put("address", address);
        requestBody.put("metroStation", metroStation);
        requestBody.put("phone", phone);
        requestBody.put("rentTime", rentTime);
        requestBody.put("deliveryDate", deliveryDate);
        requestBody.put("comment", comment);

        if (color != null && color.length > 0) {
            requestBody.put("color", color);
        }

        return given()
                .header("Content-type", "application/json")
                .body(requestBody.toString())
                .when()
                .post(ORDER_PATH);
    }

    public Response getOrdersList() {
        return given()
                .when()
                .get(ORDER_PATH);
    }
}