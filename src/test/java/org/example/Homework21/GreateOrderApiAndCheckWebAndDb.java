package org.example.Homework21;
import com.codeborne.selenide.Selenide;
import com.google.gson.Gson;
import db.DbManeger;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.SetupFunctions;
import org.junit.jupiter.api.*;
import pages.OrderPage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static io.restassured.RestAssured.given;
public class GreateOrderApiAndCheckWebAndDb {

        static String token;
        static SetupFunctions setupFunctions;
        LoginPage loginPage;
        OrderPage orderPage;
        static Connection connection;


        @BeforeAll
        public static void setUpProperty(){
            setupFunctions = new SetupFunctions();
        }

        @BeforeEach
        public void setUp(){
            Selenide.open(setupFunctions.getBaseUrlWeb());
        }

        @BeforeEach
        public void setToken(){
            token = setupFunctions.getToken();
            Selenide.localStorage().setItem("jwt", token);
            Selenide.refresh();
        }

        @BeforeEach
        public void setupLoginPage(){
            loginPage = new LoginPage();
        }

        @BeforeEach
        public void setupOrderPage(){
            orderPage = new OrderPage();
        }

        @AfterEach
        public void tearDown(){
            Selenide.closeWebDriver();
        }

        public String randomCustomerName(){
            RandomStringUtils randomStringUtils = new RandomStringUtils();
            int lengthFieldCustomerName = 12;
            boolean symbols = true;
            boolean numbers = false;
            String generatedStringCustomerName = RandomStringUtils.random(lengthFieldCustomerName, symbols, numbers);

            return generatedStringCustomerName;
        }

        public String randomCustomerPhone(){
            RandomStringUtils randomStringUtils = new RandomStringUtils();
            int lengthFieldCustomerPhone = 12;
            boolean symbols = false;
            boolean numbers = true;
            String generatedStringCustomerPhone = RandomStringUtils.random(lengthFieldCustomerPhone, symbols, numbers);

            return generatedStringCustomerPhone;
        }

        public String randomCustomerComment(){
            RandomStringUtils randomStringUtils = new RandomStringUtils();
            int lengthFieldCustomerComment = 20;
            boolean symbols = true;
            boolean numbers = true;
            String generatedStringCustomerComment = RandomStringUtils.random(lengthFieldCustomerComment,symbols, numbers);

            return generatedStringCustomerComment;
        }

        public int toCreateOrder() {
            ForGreateAnOrder ForGreateOrder = new ForGreateAnOrder();
            ForGreateOrder.setStatus("OPEN");
            ForGreateOrder.setCourierId(0);
            ForGreateOrder.setCustomerName(randomCustomerName());
            ForGreateOrder.setCustomerPhone(randomCustomerPhone());
            ForGreateOrder.setComment(randomCustomerComment());
            ForGreateOrder.setId(0);

            Gson gson = new Gson();

            Response response = given()
                    .when()
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .body(gson.toJson(ForGreateOrder))
                    .log()
                    .all()
                    .post(setupFunctions.getBaseUrl() + "/orders")
                    .then()
                    .log()
                    .all()
                    .extract()
                    .response();

            ForGreateAnOrder orderId = gson.fromJson(response.asString(), ForGreateAnOrder.class);

            return orderId.getId();
        }

        @Test
        public void toCheckCreatedOrderFromApiToWeb(){

            String orderIdCreated = String.valueOf(toCreateOrder());


            orderPage.clickButtonStatusToSearchOrder();

            orderPage.fieldSearchOrder(orderIdCreated);
            orderPage.clickButtonSearchOrder();
            orderPage.checkThatOrderCreated();
        }

        public void toCreateRequestToDb(int orderId) {

            String sql = String.format("select * from orders where id = %d ;", orderId);
            System.out.println();

            try {
                System.out.println("Executing sql ... ");
                System.out.println("SQL is : " + sql);

                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery( sql );

                String statusFromDb = null;
                if (resultSet != null) {

                    while ( resultSet.next() ) {
                        System.out.println(resultSet.getString(1) + resultSet.getString(2) + resultSet.getString(3));
                        statusFromDb = resultSet.getString(3);
                    }

                    Assertions.assertEquals( "OPEN", statusFromDb);

                } else {
                    Assertions.fail("Result set is null");
                }

            } catch (SQLException e) {

                System.out.println("Error while executing sql ");
                System.out.println(e.getErrorCode());
                System.out.println(e.getSQLState());
                e.printStackTrace();

                Assertions.fail("SQLException");
            }
        }

        @Test
        public void toCheckCreatedOrderFromApiToDb (){
            DbManeger dbManager = new DbManeger();
            connection = dbManager.connectionToDb();
            toCreateRequestToDb(toCreateOrder());
            dbManager.close(connection);
        }

        private class LoginPage {
        }
    }


