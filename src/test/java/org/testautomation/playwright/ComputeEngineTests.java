package org.testautomation.playwright;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Locator.ClickOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testautomation.playwright.calculator.Calculator;
import org.testautomation.playwright.factory.CalculatorFactory;
import org.testautomation.playwright.factory.ComputeEngineFactory;

public class ComputeEngineTests extends AbstractTest{

  @Test
  public void verifyComputeEnginePriceUpdatesBasedOnNumberOfInstances(Page page){
    CalculatorFactory factory = new ComputeEngineFactory();
    Calculator calculator = factory.createCalculator();
    calculator.openCalculator(page);

    assertThat(page.locator("[aria-label='Selected product title']")).hasText("Compute Engine");

    Locator costLocator =  page.locator("text=Estimated cost").locator("..").locator("label");
    Locator popup = page.getByText("Service cost updated");

    popup.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));
    assertThat(costLocator).containsText("$");
    String defaultCostText = costLocator.textContent();
    double defaultCost = Double.parseDouble(defaultCostText.replace("$", ""));

    Assertions.assertThat(defaultCostText).isEqualTo("$69.98");
    popup.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.DETACHED));

    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Increment")).first().click(new ClickOptions().setClickCount(2));

    popup.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));
    popup.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.DETACHED));

    String updatedCostText = costLocator.textContent();
    double updatedCost = Double.parseDouble(updatedCostText.replace("$", ""));
    Assertions.assertThat(updatedCostText).isEqualTo("$209.95");
    Assertions.assertThat(updatedCost).isCloseTo(defaultCost * 3, Assertions.within(0.01));
  }

}
