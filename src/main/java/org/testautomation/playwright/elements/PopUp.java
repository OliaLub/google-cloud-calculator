package org.testautomation.playwright.elements;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public abstract class PopUp {

  protected Locator popUp;

  protected PopUp(Page page, String popUpText) {
    this.popUp = page.getByText(popUpText);
  }

  public void waitUntilAppears() {
    popUp.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));
  }

  public void waitUntilDisappears() {
    popUp.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.DETACHED));
  }

}
