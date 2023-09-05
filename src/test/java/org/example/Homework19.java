package org.example;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;
import pages.LoginPage;
import pages.OrderPage;

import static com.codeborne.selenide.Selenide.open;


public class Homework19 {


        static SetupFunctions setupFunctions;
        LoginPage loginPage;
        OrderPage orderPage;

        @BeforeAll
        public static void setUpProperty(){
            setupFunctions = new SetupFunctions();
        }

        @BeforeEach
        public void setUp(){

            open(setupFunctions.getBaseUrlWeb());
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

        @Test
        public void createOrder(){

            loginPage.locateUsernameAndInsertText(setupFunctions.getUsername());
            loginPage.locatePasswordAndInsertText(setupFunctions.getPassword());

            orderPage.clickButtonSignInAndGoToOrder();

            orderPage.usernameInsertText("Yaroslav90");
            orderPage.phoneInsertNumbers("+37255622476");
            orderPage.commentInsertNumbers("Order");

            orderPage.buttonCreateOrder();

            orderPage.buttonOkWithCreatedOrderVisible();
        }

        @Test
        public void searchOrderWithOrderNumber(){

            loginPage.locateUsernameAndInsertText(setupFunctions.getUsername());
            loginPage.locatePasswordAndInsertText(setupFunctions.getPassword());

            orderPage.clickButtonSignInAndGoToOrder();

            orderPage.usernameInsertText("Yaroslav90");
            orderPage.phoneInsertNumbers("+37255622476");
            orderPage.commentInsertNumbers("Order");

            orderPage.buttonCreateOrder();

            orderPage.buttonOkWithCreatedOrderVisible();
            orderPage.clickButtonOkWithCreatedOrderVisible();

            orderPage.clickButtonStatusToSearchOrder();

            orderPage.fieldSearchOrder("4656");
            orderPage.clickButtonSearchOrder();
            orderPage.checkThatOrderCreated();
        }

}
