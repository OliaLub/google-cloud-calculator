package org.testautomation.playwright.page.component;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.GetByRoleOptions;
import com.microsoft.playwright.options.AriaRole;
import java.util.Arrays;
import org.assertj.core.api.Assertions;
import org.testautomation.playwright.enums.Currency;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CostDetailsComponent extends BaseComponent {

  private final Locator currencyButton;
  private final Locator currencyList;
  private final Locator addToEstimateButton;
  private final Locator activeServiceElement;
  private final Locator costLocator;

  public CostDetailsComponent(Page page) {
    super(page);
    this.currencyButton = page.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName("Open currency selector"));
    this.currencyList = page.getByRole(AriaRole.GROUP);
    this.addToEstimateButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to estimate")).last();
    this.activeServiceElement = page.locator("div[aria-label^='Edit '][class*=' ']");
    this.costLocator = page.locator("text=Estimated cost").locator("..").locator("label");
  }

  public AddToEstimateModal openAddToEstimateModal(Page page){
    logger.info("Opening Add To Estimate modal");
    addToEstimateButton.click();
    return new AddToEstimateModal(page);
  }

  public String readTotalCost() {
    logger.debug("Reading total cost");
    assertThat(costLocator).not().containsText("--");
    new TitleComponent(page).waitForPriceToStabilize();
    return costLocator.textContent();
  }

  public String readActiveServiceCost() {
    logger.debug("Reading active service cost");
    assertThat(costLocator).not().containsText("--");
    new TitleComponent(page).waitForPriceToStabilize();
    return activeServiceElement.locator("div", new Locator.LocatorOptions().setHasText(".")).first().textContent();
  }

  public double readCostValue() {
    return Double.parseDouble(readTotalCost().replace("$", "").replace(",", ""));
  }

  public CostDetailsComponent selectCurrency(Currency currency) {
    logger.info("Selecting currency");
    currencyButton.click();
    currencyList.getByRole(AriaRole.MENUITEMRADIO, new Locator.GetByRoleOptions().setName(currency.toString())).check();
    Assertions.assertThat(currencyButton.innerText()).contains(currency.toString());
    return this;
  }

  public Currency readSelectedCurrency(){
    String selectedText = currencyButton.allInnerTexts().get(0).split("\\s+")[0];
    return Arrays.stream(Currency.values())
        .filter(currency -> currency.toString().equals(selectedText))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("Unknown selected currency: " + selectedText));
  }

}
