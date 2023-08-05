package org.example;

import com.google.gson.Gson;
import dto.CredentialsDto;
import org.apache.groovy.ast.tools.ImmutablePropertyUtils;
import org.apache.http.HttpStatus;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.PropertyPermission;

import static io.restassured.RestAssured.given;

public class SetupFunctions {

   private  String username;
    private String password;
    private String baseUrl;

    public String getBaseUrl(){
        return baseUrl;
    }


    public SetupFunctions() {

        try (InputStream  input = new FileInputStream("settings.properties")){
            Properties properties = new Properties();
            properties.load(input);

//            api
            baseUrl = properties.getProperty("baseUrl");
            username = properties.getProperty("username");
            password = properties.getProperty("password");


        } catch (IOException e){
            e.printStackTrace();

        }
    }

    public String  getToken(){

        CredentialsDto credentialsDto = new CredentialsDto();
//        init from property file
        credentialsDto.setPassword(password);
        credentialsDto.setUsername(username);

        Gson gson = new Gson();
        String credentioals = gson.toJson(credentialsDto);


        String token = given()
                .when()
                .header("Content-Type", "application/json")
                .body(credentioals)
                .log()
                .all()
                .post("http://51.250.6.164:8080/login/student")
                .then()
                .extract()
                .asString();

        return token;

    }
}
