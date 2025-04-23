package org.testautomation.playwright.calculator;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import java.util.regex.Pattern;
import org.testautomation.playwright.page.ServicePage;

public class BigQueryCalculator implements Calculator{

  public BigQueryCalculator() {}

  @Override
  public void openCalculator(Page page) {
    page.locator("h2").filter(new Locator.FilterOptions().setHasText(Pattern.compile("^BigQuery$"))).first().click();
    page.waitForCondition(() -> page.locator("[aria-label='Selected product title']").textContent().equals("BigQuery"));
  }

  @Override
  public ServicePage createServicePage(Page page) {
    return null;
  }

}
