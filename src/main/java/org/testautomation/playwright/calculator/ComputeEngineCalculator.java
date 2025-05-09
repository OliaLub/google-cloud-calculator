package org.testautomation.playwright.calculator;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import java.util.regex.Pattern;
import org.testautomation.playwright.page.ComputeEngineServiceConfigurationComponent;
import org.testautomation.playwright.page.ServiceConfigurationComponent;

public class ComputeEngineCalculator implements Calculator{

  public ComputeEngineCalculator(){}

  @Override
  public void openCalculator(Page page) {
    page.locator("h2").filter(new Locator.FilterOptions().setHasText(Pattern.compile("^Compute Engine$"))).first().click();
    page.waitForCondition(() -> page.locator("[aria-label='Selected product title']").textContent().equals("Compute Engine"));
   }

  @Override
  public ServiceConfigurationComponent createServicePage(Page page) {
    return new ComputeEngineServiceConfigurationComponent(page);
  }

}
