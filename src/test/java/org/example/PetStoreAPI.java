package org.example;

import com.google.gson.Gson;
import dto.RandomOrderDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class PetStoreAPI {
    @Test
    public void testPositivePostOrderHome11() {
        RandomOrderDto randomOrderDtoEmpty = new RandomOrderDto();

        randomOrderDtoEmpty.setUsername(generateRandomeUserName());
        randomOrderDtoEmpty.setFirstname(generateRandomeFirstName());
        randomOrderDtoEmpty.setLastname(generateRandomeLastname());
        randomOrderDtoEmpty.setEmail(generateRandomeEmail());
        randomOrderDtoEmpty.setPassword(generateRandomePassword());
        randomOrderDtoEmpty.setPhone(generateRandomPhone());


        Gson gson = new Gson();

        given()
                .when()
                .header("Content-Type", "application/json")
                .body(gson.toJson(randomOrderDtoEmpty))
                .log()
                .all()
                .post("http://51.250.6.164:8080/test-orders")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK);
    }
    public String generateRandomeUserName() {
        int lengthUserName = 10;
        boolean useLettersUserName = true;
        boolean useNumbersUserName = false;
        String generatedStringUserName = RandomStringUtils.random(lengthUserName, useLettersUserName, useNumbersUserName);
        return generatedStringUserName;}
    public String generateRandomeFirstName() {
        int lengthFirstName = 10;
        boolean useLettersFirstName = true;
        boolean useNumbersFirstName = false;
        String generatedStringFirstName = RandomStringUtils.random(lengthFirstName, useLettersFirstName, useNumbersFirstName);
        return generatedStringFirstName;}

    public String generateRandomeLastname() {
        int lengthLastname = 10;
        boolean useLettersLastname = true;
        boolean useNumbersLastname = false;
        String generatedStringLastname = RandomStringUtils.random(lengthLastname, useLettersLastname, useNumbersLastname);
        return generatedStringLastname;
    }
    public String generateRandomeEmail() {
        int lengthEmail = 10;
        boolean useLettersEmail = true;
        boolean useNumbersEmail = true;
        String generatedStringEmail = RandomStringUtils.random(lengthEmail, useLettersEmail, useNumbersEmail);
        return generatedStringEmail;}
    public String generateRandomePassword() {
        int lengthPassword = 12;
        boolean useLettersPassword = true;
        boolean useNumbersPassword = true;
        String generatedStringPassword = RandomStringUtils.random(lengthPassword, useLettersPassword, useNumbersPassword);
        return generatedStringPassword;}

    public String generateRandomPhone(){

        int lengthPhone = 9;
        boolean useLettersPhone = false;
        boolean useNumbersPhone = true;
        String generatedStringPhone = RandomStringUtils.random(lengthPhone, useLettersPhone, useNumbersPhone);
        return generatedStringPhone;
    }
}
