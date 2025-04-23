package org.testautomation.playwright.calculator;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import java.util.regex.Pattern;
import org.testautomation.playwright.page.KubernetesEngineServicePage;
import org.testautomation.playwright.page.ServicePage;

public class KubernetesEngineCalculator implements Calculator{

  public KubernetesEngineCalculator(){}

  @Override
  public void openCalculator(Page page) {
    page.locator("h2").filter(new Locator.FilterOptions().setHasText(Pattern.compile("^Google Kubernetes Engine$"))).first().click();
    page.waitForCondition(() -> page.locator("[aria-label='Selected product title']").textContent().equals("Kubernetes Engine"));
  }

  @Override
  public ServicePage createServicePage(Page page) {
    return new KubernetesEngineServicePage(page);
  }

}
