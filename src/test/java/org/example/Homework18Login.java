package org.example;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import pages.LoginPage;

import static com.codeborne.selenide.Selenide.*;


// Task 18
public class Homework18Login {

    static SetupFunctions setupFunctions;

    @BeforeAll
    public static void setupProperty() {
        setupFunctions = new SetupFunctions();
    }

    @BeforeEach
    public void setUp() {

        open(setupFunctions.getBaseUrlWeb());
    }

    @AfterEach
    public void tearDown() {
        closeWebDriver();
    }

    @Test
    public void IncorrectLoginPasswordAndCheckError() {

        LoginPage loginPage = new LoginPage();

        loginPage.locateUsernameAndInsertText("login12345");
        loginPage.locatePasswordAndInsertText("password12345");
        loginPage.clickSignIn();


        $(By.xpath("//div[@data-name='authorizationError-popup']")).shouldBe(Condition.exist, Condition.visible);

    }

    @Test
    public void insertCorrectLoginPasswordAndCheckThePage() {

        LoginPage loginPage = new LoginPage();

        loginPage.locateUsernameAndInsertText(setupFunctions.getUsername());
        loginPage.locatePasswordAndInsertText(setupFunctions.getPassword());
        loginPage.clickSignIn();


        $(By.xpath("//button[@data-name='createOrder-button']")).shouldBe(Condition.exist, Condition.visible);
        $(By.xpath("//button[@data-name='openStatusPopup-button']")).shouldBe(Condition.exist, Condition.visible);
    }

    @Test
    public void enterAnIncorrectLoginPasswordCheckTheErrorAndTryToLoginSuccessfully() {

        LoginPage loginPage = new LoginPage();

        $(By.xpath("//input[@data-name='username-input']")).setValue("login12345");
        $(By.xpath("//input[@data-name='password-input']")).setValue("password12345");

        $(By.xpath("//button[@data-name='signIn-button']")).click();

        $(By.xpath("//button[@data-name='authorizationError-popup-close-button']")).shouldBe(Condition.exist, Condition.visible);

        $(By.xpath("//button[@data-name='authorizationError-popup-close-button']")).click();

        $(By.xpath("//input[@data-name='username-input']")).setValue(setupFunctions.getUsername());
        $(By.xpath("//input[@data-name='password-input']")).setValue(setupFunctions.getPassword());

        $(By.xpath("//button[@data-name='signIn-button']")).click();

        $(By.xpath("//input[@data-name='phone-input']")).shouldBe(Condition.exist, Condition.visible);

    }

    @Test

    public void errorCheckingByMinimumNumberOfCharacters() {

        LoginPage loginPage = new LoginPage();

        $(By.xpath("//input[@data-name='username-input']")).setValue("Y");
        $(By.xpath("//input[@data-name='password-input']")).setValue("K");

        $(By.xpath("//button[@data-name='signIn-button']")).shouldBe(Condition.disabled);
        $(By.xpath("//*[@data-name='password-input']/..//span[@data-name='username-input-error']")).shouldBe(Condition.exist, Condition.visible);


    }
}
