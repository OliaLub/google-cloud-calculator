package org.testautomation.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;

@UsePlaywright(AbstractTest.CustomOptions.class)
public abstract class AbstractTest {

  public static class CustomOptions implements OptionsFactory {
    @Override
    public Options getOptions() {
      return new Options()
          .setHeadless(false)
          .setBrowserName("chromium")
          .setChannel("chrome")
          .setLaunchOptions(new LaunchOptions().setSlowMo(200).setArgs(List.of("--start-maximized")))
          .setContextOptions(new Browser.NewContextOptions().setViewportSize(null));
    }
  }

  @BeforeEach
  public void setUp(Page page) {
    page.navigate("https://cloud.google.com/products/calculator");
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to estimate")).first().click();
    page.getByText("Add to this estimate").waitFor();

  }

}
