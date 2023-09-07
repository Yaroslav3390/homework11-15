package org.example;

import com.google.gson.Gson;
import dto.RandomOrderDto;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;


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

        randomOrderDtoEmpty.setCustomerName(generateRandomeName());
        randomOrderDtoEmpty.setCustomerPhone(generateRandomPhone());
        randomOrderDtoEmpty.setComment(generateRandomCommit());

        Gson gson = new Gson();


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

        assertAll(
                "Grouped Assertions of User",
                () -> assertNotNull(
                        randomOrderDto.getComment(), "1st Assert"),
                () -> assertEquals("nam",
                        randomOrderDto.getCustomerName(), "2nd Assert")
        );
    }
    public String generateRandomeName() {
        int lengthName = 10;
        boolean useLettersName = true;
        boolean useNumbersName = false;
        String generatedStringName = RandomStringUtils.random(lengthName, useLettersName, useNumbersName);
        return generatedStringName;
    }
    public String generateRandomPhone(){

        int lengthPhone = 8;
        boolean useLettersPhone = false;
        boolean useNumbersPhone = true;
        String generatedStringPhone = RandomStringUtils.random(lengthPhone, useLettersPhone, useNumbersPhone);
        return generatedStringPhone;
    }

    public String generateRandomCommit(){
        int lengthCommet = 6;
        boolean useLettersCommet = true;
        boolean useNumbersCommet = true;
        String generatedStringCommet= RandomStringUtils.random(lengthCommet, useLettersCommet, useNumbersCommet);
        return generatedStringCommet;
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
                .path("status");
        Assertions.assertEquals("OPEN", status );
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
    }

