package org.testautomation.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.testautomation.playwright.enums.ServiceType;
import org.testautomation.playwright.model.Service;
import org.testautomation.playwright.page.CalculatorPage;
import org.testautomation.playwright.page.CostDetailsComponent;
import org.testautomation.playwright.page.ServiceConfigurationComponent;
import org.testautomation.playwright.page.TitleComponent;

@UsePlaywright(AbstractTest.CustomOptions.class)
public abstract class AbstractTest {
  private static final String URL = "https://cloud.google.com/products/calculator";
  protected CalculatorPage calculator;
  protected ServiceType serviceType;
  protected ServiceConfigurationComponent activeService;
  protected CostDetailsComponent costDetails;
  protected TitleComponent title;
  protected Service service;

  public static class CustomOptions implements OptionsFactory {
    @Override
    public Options getOptions() {
      return new Options()
          .setHeadless(false)
          .setBrowserName("chromium")
          .setChannel("chrome")
          .setLaunchOptions(new LaunchOptions().setSlowMo(150).setArgs(List.of("--start-maximized")))
          .setContextOptions(new Browser.NewContextOptions().setViewportSize(null));
    }
  }

  @BeforeEach
  public void setUp(Page page) {
    page.navigate(URL);
    calculator = new CalculatorPage(page);
    costDetails = calculator.getCostDetails();
    title = calculator.getTitleComponent();
  }

  protected void selectService(ServiceType serviceType) {
    calculator.addToEstimate(serviceType);
    activeService = calculator.getActiveService();
  }

}
