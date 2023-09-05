package org.example;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class Homework18Esto {


    @Test
    public void buttonLogiSisseActiveMobiilId(){

        Configuration.browser="firefox";
        Selenide.open("https://profile.esto.ee/login/mobile-id");

        $(By.xpath("//*[@id='phone-field']/ ..//input[@class='esto-text-input-container__input']")).setValue("+37255622476");
       $(By.xpath("//*[@id='pin-field']/..//input[@class='esto-text-input-container__input']")).setValue("39007100171");

        $(By.xpath("//button[@estocypressdata='mobile-id-login-button']")).shouldBe(Condition.exist, Condition.visible);
    }


    @Test
    public void buttonLogiSisseActiveSmartId(){

        System.setProperty("selenide.browser","firefox");
        Selenide.open ("https://profile.esto.ee/login/smart-id");

        $(By.xpath("//*[@id='login-field']/..//input[@class='esto-text-input-container__input']")).setValue("39007100171");

        $(By.xpath("//button[@class='form-button esto-button esto-button__accent']")).shouldBe(Condition.exist,Condition.visible);
    }


    @Test
    public void buttonLogiSisseActiveParool(){

        System.setProperty("selenide.browser","firefox");
        Selenide.open("https://profile.esto.ee/login/password");

        $(By.xpath("//*[@id='login-field']/..//input[@class='esto-text-input-container__input']")).setValue("Yaroslav");
        $(By.xpath("//*[@id='password-field']/..//input[@class='esto-text-input-container__input']")).setValue("password");

        $(By.xpath("//button[@class='form-button esto-button esto-button__accent']")).shouldBe(Condition.exist, Condition.visible);
    }


    @Test
    public void errorForUnsuccessfulLoginSmartId (){

        System.setProperty("selenide.browser", "firefox");
        Selenide.open("https://profile.esto.ee/login/smart-id");

        $(By.xpath("//*[@id='login-field']/..//input[@class='esto-text-input-container__input']")).setValue("39007100171");

        $(By.xpath("//button[@class='form-button esto-button esto-button__accent']")).click();

        $(By.xpath("//*[@class='error-text ng-star-inserted']")).shouldBe(Condition.exist, Condition.visible);
    }


    @Test
    public void errorForUnsuccessfulLoginParool (){

        Configuration.browser="firefox";
        Selenide.open("https://profile.esto.ee/login/password");

        $(By.xpath("//*[@id='login-field']/..//input[@class='esto-text-input-container__input']")).setValue("");
        $(By.xpath("//*[@id='password-field']/..//input[@class='esto-text-input-container__input']")).setValue(" ");

        $(By.xpath("//button[@class='form-button esto-button esto-button__accent']")).click();

        $(By.xpath("//span[@class='ng-star-inserted']")).shouldBe(Condition.exist,Condition.visible);
    }

}
