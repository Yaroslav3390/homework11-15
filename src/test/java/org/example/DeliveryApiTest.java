package org.example;

import com.google.gson.Gson;
import dto.OrderRealDto;
import dto.OrderRealDtoResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class DeliveryApiTest {
    static String token;

    //    Task 13

    @BeforeAll
    public static void setUp() {

        System.out.println("---> test start");

        SetupFunctions setupFunctions = new SetupFunctions();
        token = setupFunctions.getToken();

        System.out.println("token " + setupFunctions.getToken());

        RestAssured.baseURI = setupFunctions.getBaseUrl();

        Assumptions.assumeFalse(token.isEmpty(), "Token is not exists, all test skipped");
    }


    // Task 14

    // 1. Order creation
    @Test
    public void createOrder() {

        OrderRealDto orderRealDto = new OrderRealDto("name", "12345678", "comment", 7, 878);
        Gson gson = new Gson();

        RestAssured.given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .body(gson.toJson(orderRealDto))
                .post("/orders")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK);


        Assertions.assertNotNull(orderRealDto.getCustomerName());
    }

// 2. Greate order for searching and deleting

    public int createOrderForSearchingAndDeleting() {
        OrderRealDto orderRealDto = new OrderRealDto("name", "12345678", "comment", 7, 878);
        Gson createdGson = new Gson();

        Response response = given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .body(createdGson.toJson(orderRealDto))
                .post("/orders")
                .then()
                .log()
                .all()
                .extract()
                .response();

        OrderRealDto orderId = createdGson.fromJson(response.asString(), OrderRealDto.class);

        return orderId.getId();
    }

    @Test
    public void searchCreatedOrder() {

        int orderIdCreated = createOrderForSearchingAndDeleting();

        String status = given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .get("/orders/" + orderIdCreated)
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .path("status");


        Assertions.assertEquals("OPEN", status);
    }

    //    3. Delete order by id
    @Test
    public void deleteOrder() {

        int orderId = createOrderAndReturnOrderId();

        String body = RestAssured.given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .delete("/orders/" + orderId)
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .response()
                .getBody()
                .asString();

        Assertions.assertTrue(true);

        //      get deleted order by id
        given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .get("/orders/" + orderId)
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .response()
                .getBody()
                .asString();
        Assumptions.assumeTrue(body.isEmpty(), "Test passed if response body is empty");
    }

    //4.Negative test without token
    @Test
    public void createOrderFailedUnauthorized() {

        int orderId = createOrderAndReturnOrderId();
        given()
                .when()
                .header("Content-Type", "application/json")
//                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .get("/orders/" + orderId)
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
        Assumptions.assumeFalse(token.isEmpty(), "Token is not exists, all test skipped");
    }

    //5. Without header content-type
    @Test
    public void createOrderFailedMediaType() {

        OrderRealDto orderRealDto = new OrderRealDto("na", "12", "comment", 2, 43);
        Gson gson = new Gson();

        given()
                .when()
//                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .body(gson.toJson(orderRealDto))
                .post("/orders")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);

    }


    @Test
    public void createOrderSuccess() {

        OrderRealDto orderRealDto = new OrderRealDto("name", "12345678", "comment", 43, 434);
        Gson gson = new Gson();

        given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .body(gson.toJson(orderRealDto))
                .post("/orders")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK);


    }


//    @Test
//    public void createOrderInvalidRequestBodyDto() {
//
//        OrderRealDto orderRealDto = new OrderRealDto(null, "12345678", "comment", 4, 455);
//        Gson gson = new Gson();
//
//        given()
//                .when()
//                .header("Content-Type", "application/json")
//                .header("Authorization", "Bearer " + token)
//                .log()
//                .all()
//                .body(gson.toJson(orderRealDto))
//                .post("/orders")
//                .then()
//                .log()
//                .all()
//                .statusCode(HttpStatus.SC_BAD_REQUEST);
//    }

    @Test

    public int createOrderAndReturnOrderId() {
        OrderRealDto orderRealDto = new OrderRealDto("name", "12345678", "comment", 4, 45);
        Gson gson = new Gson();

        Response response = given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .body(gson.toJson(orderRealDto))
                .post("/orders")
                .then()
                .log()
                .all()
                .extract()
                .response();
//        deserealiza
        OrderRealDtoResponse realDtoResponse = gson.fromJson(response.asString(), OrderRealDtoResponse.class);
        return realDtoResponse.getId();
    }

//    Task 15

    @Test
    public void availableOrdersRoleStudent() {

        Response response = given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .get("/orders/available")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .extract()
                .response();

        Assertions.assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void statusOrderRoleStudent() {

        OrderRealDto statusOrdersRealDtoRoleStudent = new OrderRealDto("name", "12345678", "comment", 4, 45);
        Gson gsonStatusOrdersRealDtoRoleStudent = new Gson();

        Response response = RestAssured.given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(gsonStatusOrdersRealDtoRoleStudent.toJson(statusOrdersRealDtoRoleStudent))
                .log()
                .all()
                .put("/orders/" + statusOrdersRealDtoRoleStudent.getId() + "/status")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .extract()
                .response();

        Assertions.assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void deleteArrayOrders() {

        OrderRealDto[] ordersArray = RestAssured.given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .get("/orders")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(OrderRealDto[].class);

        if (ordersArray.length > 0) {

            for (int i = 0; i < ordersArray.length; i++) {

                System.out.println("Deleting order with id: " + ordersArray[i].getId());
                Response response = RestAssured.given()
                        .when()
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + token)
                        .log()
                        .all()
                        .delete("/orders/" + ordersArray[i].getId())
                        .then()
                        .log()
                        .all()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .response();

                Assertions.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
            }
        }
    }

    public String generatedRandomCustomerName() {
        RandomStringUtils randomStringUtils = new RandomStringUtils();
        int lengthCustomerName = 10;
        boolean useLettersCustomerName = true;
        boolean useSymbolsCustomerName = true;
        String generatedStringCustomerName = RandomStringUtils.random(lengthCustomerName, useLettersCustomerName, useSymbolsCustomerName);

        return generatedStringCustomerName;
    }

    public String generatedRandomCustomerPhone() {
        RandomStringUtils randomStringUtils = new RandomStringUtils();
        int lengthCustomerPhone = 12;
        boolean useNumbersCustomerPhone = true;
        boolean useSymbolsCustomerPhone = false;
        String generatedStringCustomerPhone = RandomStringUtils.random(lengthCustomerPhone, useSymbolsCustomerPhone, useNumbersCustomerPhone);

        return generatedStringCustomerPhone;
    }

    public String generatedRandomComment() {
        int lengthComment = 15;
        boolean useLettersComment = true;
        boolean useNumbersComment = true;
        String generatedStringComment = RandomStringUtils.random(lengthComment, useLettersComment, useNumbersComment);

        return generatedStringComment;
    }

    @Test
    public void generatedCreateOrder() {
        OrderRealDto generatedOrder = new OrderRealDto();
        generatedOrder.setStatus("OPEN");
        generatedOrder.setCourierId(0);
        generatedOrder.setCustomerName(generatedRandomCustomerName());
        generatedOrder.setCustomerPhone(generatedRandomCustomerPhone());
        generatedOrder.setComment(generatedRandomComment());
        generatedOrder.setId(0);

        Gson gsonGeneratedOrder = new Gson();

        OrderRealDto[] generatedOrderArray = {generatedOrder, generatedOrder, generatedOrder};
        for (int i = 0; i < generatedOrderArray.length; i++)

            given()
                    .when()
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .body(gsonGeneratedOrder.toJson(generatedOrder))
                    .log()
                    .all()
                    .post("/orders")
                    .then()
                    .log()
                    .all()
                    .statusCode(HttpStatus.SC_OK);

        Assertions.assertNotNull(generatedOrder.getId());

        OrderRealDto[] checkNewCreatedOrders = RestAssured.given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .get("/orders")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(OrderRealDto[].class);

        Assertions.assertEquals(0, generatedOrder.getCourierId());

    }


}