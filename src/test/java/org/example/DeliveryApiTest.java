package org.example;

import com.google.gson.Gson;
import dto.OrderRealDto;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class DeliveryApiTest {
    //    new
    static String token;

    @BeforeAll
    public static void setup() {

        SetupFunctions setupFunctions = new SetupFunctions();
        token = setupFunctions.getToken();

        if (token == "") {
            Assertions.fail();
        }
    }
    @Test
    public void createOrder(){
        int a = 1;
    }
}

////        //        new
////
////        RestAssured.baseURI = setupFunctions.getBaseUrl();
//
//        }

//    @Test
//    public  void getOrder(){
//        //       new
//
//        given()
//                .when()
//                .header("Content-Type", "application/json")
//                .header("Authorization", "Bearer " + token)
//                .log()
//                .all()
//                .get("/orders/4135" )
//                .then()
//                .log()
//                .all()
//                .statusCode(HttpStatus.SC_OK);}


//        @Test
//        public void createOrder() {
//
//            OrderRealDto orderRealDto = new OrderRealDto("name", "12345678", "comment");
//            Gson gson = new Gson();
//
//            given()
//                    .when()
//                    .header("Content-Type", "application/json")
//                    .header("Authorization", "Bearer " + token)
//                    .log()
//                    .all()
//                    .body(gson.toJson(orderRealDto))
//                    .post("/orders")
//                    .then()
//                    .log()
//                    .all()
//                    .statusCode(HttpStatus.SC_OK);
//        }
//
//}
