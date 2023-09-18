package org.example;

import com.google.gson.Gson;
import dto.CredentialsDto;
import io.restassured.RestAssured;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SetupFunctions {

   private  String username;
    private String password;
    private String baseUrl;
    private String baseUrlWeb;
    private String dbhost;
    private String dbport;
    private String dbname;
    private String dbusername;
    private String dbpassword;


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getBaseUrlWeb() {
        return baseUrlWeb;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }



    public SetupFunctions() {

        try (InputStream  input = new FileInputStream("settings.properties")){
            Properties properties = new Properties();
            properties.load(input);

            baseUrl = properties.getProperty("baseUrl");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            baseUrlWeb = properties.getProperty("baseUrlWeb");
            dbhost = properties.getProperty("dbhost");
            dbport = properties.getProperty("dbport");
            dbname = properties.getProperty("dbname");
            dbusername = properties.getProperty("dbusername");
            dbpassword = properties.getProperty("dbpassword");


        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public String getBaseUrl (){
        return baseUrl;}

    public String  getToken(){

        CredentialsDto credentialsDto = new CredentialsDto();

        credentialsDto.setUsername(username);

        credentialsDto.setPassword(password);



        Gson gson = new Gson();
        String credentioals = gson.toJson(credentialsDto);


        String token = RestAssured.given()
                .when()
                .header("Content-Type", "application/json")
                .log()
                .all()
                .body(credentioals)
                .when()
                .post(baseUrl + "/login/student")
                .then()
                .log()
                .all()
                .and()
                .extract()
                .asString();

        return token;

    }

    public String getDbhost() {
        return dbhost;
    }

    public void setDbhost(String dbhost) {
        this.dbhost = dbhost;
    }

    public String getDbport() {
        return dbport;
    }

    public void setDbport(String dbport) {
        this.dbport = dbport;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getDbusername() {
        return dbusername;
    }

    public void setDbusername(String dbusername) {
        this.dbusername = dbusername;
    }

    public String getDbpassword() {
        return dbpassword;
    }

    public void setDbpassword(String dbpassword) {
        this.dbpassword = dbpassword;
    }
}
