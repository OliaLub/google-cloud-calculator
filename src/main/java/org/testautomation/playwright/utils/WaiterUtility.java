package org.testautomation.playwright.utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.WaitForConditionOptions;
import com.microsoft.playwright.options.WaitForSelectorState;
import java.time.Duration;
import lombok.experimental.UtilityClass;
import org.testautomation.playwright.elements.PopUp;

@UtilityClass
public class WaiterUtility {

  public static void waitUntilAppears(Locator element) {
    element.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));
  }
  public static void waitUntilAppears(PopUp element) {
    element.getPopUp().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));
  }

  public static void waitUntilDisappears(Locator element) {
    element.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.DETACHED));
  }

  public static void waitUntilDisappears(PopUp element) {
    element.getPopUp().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.DETACHED));
  }

  public static void waitForText(Page page, Locator locator, String expectedText) {
    page.waitForCondition(() -> locator.textContent().equals(expectedText));
  }

  public static void waitForTextToChange(Page page, Locator element, String initialText) {
    page.waitForCondition(() -> !element.innerText().equals(initialText), new WaitForConditionOptions().setTimeout(5000));
  }

  public static void waitForElementToBeVisible(Locator element) {
    element.waitFor();
  }

  public static void waitForElementValueToStabilize(Locator element, Duration stableDuration) {
    String previousValue = element.innerText();
    long startTime = System.currentTimeMillis();
    long stabilizationTime = stableDuration.toMillis();

    while (System.currentTimeMillis() - startTime < stabilizationTime) {
      String currentPrice = element.innerText();
      if (!currentPrice.equals(previousValue)) {
        previousValue = currentPrice;
        startTime = System.currentTimeMillis();
      }
    }
  }

}
