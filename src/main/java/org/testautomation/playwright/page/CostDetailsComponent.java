package org.testautomation.playwright.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.GetByRoleOptions;
import com.microsoft.playwright.options.AriaRole;
import java.util.Arrays;
import org.assertj.core.api.Assertions;
import org.testautomation.playwright.enums.Currency;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CostDetailsComponent {

  private final Locator costLocator;
  private final Locator currencyButton;
  private final Locator currencyList;

  public CostDetailsComponent(Page page) {
    this.costLocator = page.locator("text=Estimated cost").locator("..").locator("label");
    this.currencyButton = page.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName("Open currency selector"));
    this.currencyList = page.getByRole(AriaRole.GROUP);
  }

  public String getCostText() {
    assertThat(costLocator).not().containsText("--");
    return costLocator.textContent();
  }

  public double getCostValue() {
    return Double.parseDouble(getCostText().replace("$", "").replace(",", ""));
  }

  public void selectCurrency(Currency currency) {
    currencyButton.click();
    currencyList.getByRole(AriaRole.MENUITEMRADIO, new Locator.GetByRoleOptions().setName(currency.toString())).check();
    Assertions.assertThat(currencyButton.innerText()).contains(currency.toString());
  }

  public Currency getSelectedCurrency(){
    String selectedText = currencyButton.allInnerTexts().get(0).split("\\s+")[0];
    return Arrays.stream(Currency.values())
        .filter(currency -> currency.toString().equals(selectedText))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("Unknown selected currency: " + selectedText));
  }

}
