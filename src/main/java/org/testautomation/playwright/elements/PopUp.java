package org.testautomation.playwright.elements;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public abstract class PopUp {

  protected Locator popUp;

  protected PopUp(Page page, String popUpText) {
    this.popUp = page.getByText(popUpText);
  }

  public Locator getPopUp() {
    return this.popUp;
  }

}
