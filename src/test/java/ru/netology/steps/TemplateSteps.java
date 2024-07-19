package ru.netology.steps;

import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import ru.netology.dto.AuthInfo;
import ru.netology.dto.VerificationCode;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.ReplenishCard;
import ru.netology.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TemplateSteps {
    private static LoginPage loginPage;
    private static ReplenishCard replenishCard;
    private static DashboardPage dashboardPage;
    private static VerificationPage verificationPage;
    private static int initialFirstCardBalance;

    @Пусть("пользователь открывает страницу логина {string}")
    public void openAuthPage(String url) {
        open(url);
        loginPage = new LoginPage();
    }

    @Когда("пользователь залогинен с именем {string} и паролем {string}")
    public void loginOnPage(String login, String password) {
        var authInfo = new AuthInfo(login, password);
        verificationPage = loginPage.validLogin(authInfo);
    }

    @И("пользователь вводит проверочный код {string}")
    public void enterVerificationCode(String authCode) throws InterruptedException {
        var verificationCode = new VerificationCode(authCode);
//        Thread.sleep(3000);
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @И("пользователь переводит {string} рублей с карты с номером {string} на свою 1 карту с главной страницы")
    public void transfer(String transferSum, String cardNumber) {
        var cardsBalance = dashboardPage.getCardsBalance();
        initialFirstCardBalance = cardsBalance.get("1");
        replenishCard = dashboardPage.clickOnButton();
        replenishCard.replenishCard(cardNumber, Integer.parseInt(transferSum));
    }

    @Тогда("баланс его 1 карты из списка на главной странице должен увеличиться на {string} рублей")
    public void verifyBalance(String expectedBalanceGrow) {
        var cardsBalance = dashboardPage.getCardsBalance();
        var actualFirstCardBalance = cardsBalance.get("1");
        assertEquals(initialFirstCardBalance + Integer.parseInt(expectedBalanceGrow), actualFirstCardBalance);
    }

}
