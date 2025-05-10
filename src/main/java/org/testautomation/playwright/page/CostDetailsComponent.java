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

  private final Locator currencyButton;
  private final Locator currencyList;
  private final Locator addToEstimateButton;
  private final Locator activeServiceElement;
  private final Locator costLocator;

  public CostDetailsComponent(Page page) {
    this.currencyButton = page.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName("Open currency selector"));
    this.currencyList = page.getByRole(AriaRole.GROUP);
    this.addToEstimateButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to estimate")).last();
    this.activeServiceElement = page.locator("div[aria-label^='Edit '][class*=' ']");
    this.costLocator = page.locator("text=Estimated cost").locator("..").locator("label");
  }

  public AddToEstimateModal openAddToEstimateModal(Page page){
    addToEstimateButton.click();
    return new AddToEstimateModal(page);
  }

  public String readTotalCost() {
    assertThat(costLocator).not().containsText("--");
    return costLocator.textContent();
  }

  public String readActiveServiceCost() {
    assertThat(costLocator).not().containsText("--");
    return activeServiceElement.locator("div", new Locator.LocatorOptions().setHasText(".")).first().textContent();
  }

  public double readCostValue() {
    return Double.parseDouble(readTotalCost().replace("$", "").replace(",", ""));
  }

  public void selectCurrency(Currency currency) {
    currencyButton.click();
    currencyList.getByRole(AriaRole.MENUITEMRADIO, new Locator.GetByRoleOptions().setName(currency.toString())).check();
    Assertions.assertThat(currencyButton.innerText()).contains(currency.toString());
  }

  public Currency readSelectedCurrency(){
    String selectedText = currencyButton.allInnerTexts().get(0).split("\\s+")[0];
    return Arrays.stream(Currency.values())
        .filter(currency -> currency.toString().equals(selectedText))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("Unknown selected currency: " + selectedText));
  }

}
