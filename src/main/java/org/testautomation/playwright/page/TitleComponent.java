package org.testautomation.playwright.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.GetByRoleOptions;
import com.microsoft.playwright.options.AriaRole;
import java.time.Duration;
import lombok.Getter;
import org.testautomation.playwright.utils.WaiterUtility;

@Getter
public class TitleComponent {

  private final Locator activeService;
  private final Locator price;
  private final Locator costUpdatedElement;

  public TitleComponent(Page page) {
    this.activeService = page.getByRole(AriaRole.HEADING, new GetByRoleOptions().setName("Selected product title"));
    this.price = page.getByText("/ month").locator("xpath=preceding-sibling::*[1]");
    this.costUpdatedElement = page.getByText("Service cost updated");
  }

  public TitleComponent verifyCostUpdatedPopupAppears() {
    WaiterUtility.waitUntilAppears(costUpdatedElement);
    return this;
  }

  public TitleComponent verifyCostUpdatedPopupDisappears() {
    WaiterUtility.waitUntilDisappears(costUpdatedElement);
    return this;
  }

  void waitForPriceToStabilize() {
    WaiterUtility.waitForElementValueToStabilize(price, Duration.ofMillis(1600));
  }

}
