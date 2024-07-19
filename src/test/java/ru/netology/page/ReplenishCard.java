package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import lombok.NoArgsConstructor;

import static com.codeborne.selenide.Selenide.$;

@NoArgsConstructor
public class ReplenishCard {
    private final SelenideElement amountField = $("[data-test-id=amount] input");
    private final SelenideElement sourceCardField = $("[data-test-id=from] input");
    private final SelenideElement replenishButton = $("[data-test-id=action-transfer]");

    public DashboardPage replenishCard(String sourceCardNumber, int sum) {
        amountField.setValue(String.valueOf(sum));
        sourceCardField.setValue(sourceCardNumber);
        replenishButton.click();
        return new DashboardPage();
    }
}
