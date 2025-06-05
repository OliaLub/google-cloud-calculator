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
import org.testautomation.playwright.page.component.CostDetailsComponent;
import org.testautomation.playwright.page.component.ServiceConfigurationComponent;
import org.testautomation.playwright.page.component.TitleComponent;

@UsePlaywright(AbstractTest.CustomOptions.class)
public abstract class AbstractTest {
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
    calculator = new CalculatorPage(page);
    costDetails = calculator.getCostDetails();
    title = calculator.getTitleComponent();
    calculator.openPage();
  }

}
