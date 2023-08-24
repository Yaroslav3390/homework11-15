package org.example;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Validate;

import java.util.Calendar;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for simple App.
 */
public class AppTest {

    private  Calculator calculator;


    @BeforeAll
    public static void beforeAllTest(){

        System.out.println("This is code before all tests in this class");
    }

    @BeforeEach
    public void beforeEchTest(){
        calculator = new Calculator();

        System.out.println("This is creation calculator object befor each tests in this class");
    }

    @AfterAll
    public static void afterAllTest(){
        System.out.println(" code after all tests ");
    }
    @AfterEach
    public void afterEach(){
        System.out.println("after each test in the class");
    }

    @ParameterizedTest
    @ValueSource(ints ={2, -2, 0})
    public void squareRootParamTest(int param){
        int result = calculator.squareRoot(param);
        assertTrue(result >= 0);

    }
    @ParameterizedTest
    @CsvSource({"1, parametr1","-2, parametr2", "3, parametr3"})
    public  void myTest(int param1, String param2){
        System.out.println("pr1 " + param1);
        System.out.println("pr2 " + param2);
        Assertions.assertTrue( param1 > 0);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/testdata.csv")
    public  void myTestCsvFile(String greting, int param1) {
        System.out.println("pr1 " + param1);
        System.out.println("pr2 " + greting);
        Assertions.assertTrue(param1 < 345);
    }





    @Test
    public void checkSum(){
//        Calculator calculator = new Calculator();
        assertEquals( 3 , calculator.sum(1, 2));
    }
    @Test
    public void checkDif(){
//        Calculator calculator = new Calculator();
        assertEquals( 2 , calculator.dif(3, 1) );

    }


    @Test
    @DisplayName("Custom test name containing spaces")
    public void checkTwoValuesNotEquals(){
        assertNotEquals( 3 , 4);
    }

    @Test
    @Disabled
    public void shouldAnswerWithTrue()
    {



        assertTrue(2 == 2 );
    }

    @Test
    public void shouldAnswerWithFalse()
    {
        assertFalse( 4 < 2 );
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 4, 5})

    public void parametrsTestGetPositive(int id) {

        given()
                .when()
                .log()
                .all()
                .get("http://51.250.6.164:8080/test-orders/{id}", id)
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK);
    }
    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5})

    public void parametrsTestGetNegative(int id) {


        given()
                .when()
                .log()
                .all()
                .get("http://51.250.6.164:8080/test-orders/{id}", id)
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }


    @Test

    public void TestPostPositive() {
        String requestBody = "{\n" +
                "  \"status\": \"OPEN\",\n" +
                "  \"courierId\": 0,\n" +
                "  \"customerName\": \"string\",\n" +
                "  \"customerPhone\": \"string\",\n" +
                "  \"comment\": \"string\",\n" +
                "  \"id\": -1340938102087703600\n" +
                "}";


        given()
                .when()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .log()
                .all()
                .post("http://51.250.6.164:8080/test-orders")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test

    public void TestPostNegative() {
        String requestBody = "{\n" +
                "  \"status\": \"OPEN\",\n" +
                "  \"courierId\": 0,\n" +
                "  \"customerName\": \"string\",\n" +
                "  \"customerPhone\": \"string\",\n" +
                "  \"comment\": \"string\",\n" +
                "  \"id\": -1340938102087703600\n" +
                "}";


        given()
                .when()
                .header("Content-Type", "application/xml")
                .body(requestBody)
                .log()
                .all()
                .post("http://51.250.6.164:8080/test-orders")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
    }

    @Test

    public void testGetPositiveOrderStatus() {

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
        Assertions.assertEquals("OPEN", status);

    }
}
