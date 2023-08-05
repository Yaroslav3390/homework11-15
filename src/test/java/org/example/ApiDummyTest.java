package org.example;

import com.google.gson.Gson;
import dto.RandomOrderDto;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;


public class ApiDummyTest {

    @Test
    public void testPositiveGetOrder() {

        given()
                .when()
                .log()
                .all()
                .get("http://51.250.6.164:8080/test-orders/5")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK);

    }

    @Test
    public void testPositivePostOrder() {
        RandomOrderDto randomOrderDto = new RandomOrderDto("name", "1234567", "hfgdhfgd");
        RandomOrderDto randomOrderDtoEmpty = new RandomOrderDto();
        int lengthName = 10;
        boolean useLettersName = true;
        boolean useNumbersName = false;
        String generatedStringName = RandomStringUtils.random(lengthName, useLettersName, useNumbersName);
        randomOrderDtoEmpty.setCustomerName(generatedStringName);

        randomOrderDtoEmpty.setCustomerPhone(generateRandomPhone());

        int lengthComment = 6;
        boolean useLettersComment = true;
        boolean useNumbersComment = true;
//        String generatedStringComment = RandomStringUtils.random(lengthComment, useLettersComment, useNumbersComment);
        randomOrderDtoEmpty.setComment(RandomStringUtils.random(lengthComment, useLettersComment, useNumbersComment));


        Gson gson = new Gson();

//        String requestBody = "{\n" +
//                "  \"status\": \"DELIVERED\",\n" +
//                "  \"courierId\": 10,\n" +
//                "  \"customerName\": \"Myname\",\n" +
//                "  \"customerPhone\": \"123456\",\n" +
//                "  \"comment\": \"cmnt\",\n" +
//                "  \"id\": 5\n" +
//                "}";



        Response response = given()
                .when()
                .header("Content-Type", "application/json")
                .body(gson.toJson(randomOrderDtoEmpty))
                .log()
                .all()
                .post("http://51.250.6.164:8080/test-orders")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        RandomOrderDto receivedOrder = gson.fromJson(response.asString(), RandomOrderDto.class);
        Assertions.assertNull(receivedOrder.getStatus());
        Assertions.assertEquals(0, receivedOrder.getCourierId());
        Assertions.assertNotNull(receivedOrder.getCustomerPhone());
        Assertions.assertEquals(randomOrderDtoEmpty.getCustomerPhone(), receivedOrder.getCustomerPhone());



    }



    @Test
    public void testPosveGetOrderAndCheckOrderStatus() {

        String status = given()
                .when()
                .log()
                .all()
                .get("http://51.250.6.164:8080/test-orders/5")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
//                .asString();
                .path("status");
        Assertions.assertEquals("OPEN", status );

//        Assertions.assertTrue(response.contains("OPEN"));
    }

    @Test
    public void testNegativeGetOrder() {

        given()
                .when()
                .log()
                .all()
                .get("http://51.250.6.164:8080/test-orders/0")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_BAD_REQUEST);

    }

    public String generateRandomPhone(){

        int lengthPhone = 8;
        boolean useLettersPhone = false;
        boolean useNumbersPhone = true;
        String generatedStringPhone = RandomStringUtils.random(lengthPhone, useLettersPhone, useNumbersPhone);
        return generatedStringPhone;

    }
}
