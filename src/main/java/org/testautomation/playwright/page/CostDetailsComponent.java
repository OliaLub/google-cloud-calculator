package org.testautomation.playwright.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CostDetailsComponent {

  private final Locator costLocator;

  public CostDetailsComponent(Page page) {
    this.costLocator = page.locator("text=Estimated cost").locator("..").locator("label");
  }

  public String getCostText() {
    assertThat(costLocator).containsText("$");
    return costLocator.textContent();
  }

  public double getCostValue() {
    return Double.parseDouble(getCostText().replace("$", "").replace(",", ""));
  }

}
