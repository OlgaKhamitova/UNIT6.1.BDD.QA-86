package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.NotFoundException;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private final SelenideElement chooseButton = $("[data-test-id=action-deposit]");
    private final ElementsCollection cards = $$(".list__item");
    public DashboardPage(){
        SelenideElement header = $("[data-test-id=dashboard]");
        if (!header.exists()) {
            throw new NoSuchElementException("Page header not found");
        }
    }
    public ReplenishCard clickOnButton(){
        chooseButton.click();
        return new ReplenishCard();
    }

    public Map<String, Integer> getCardsBalance() {
        var firstCardTextInfo = cards.get(0).getText();
        var secondCardInfoText = cards.get(1).getText();
        Integer firstCardBalance = extractBalanceValue(firstCardTextInfo);
        Integer secondCardBalance = extractBalanceValue(secondCardInfoText);
        return Map.of("1", firstCardBalance, "2", secondCardBalance);
    }

    private int extractBalanceValue(String cardTestInfo) {
        Pattern pattern = Pattern.compile("баланс: -?\\d+");
        Matcher matcher = pattern.matcher(cardTestInfo);
        if (matcher.find()) {
            String[] cardTextArray = matcher.group().split("\\s+");
            return Integer.parseInt(cardTextArray[1]);
        }
        else {
            throw new NotFoundException("Balance not found");
        }
    }
}
