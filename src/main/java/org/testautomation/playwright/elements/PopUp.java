package org.testautomation.playwright.elements;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.Getter;

@Getter
public class PopUp {

  private final Locator popUp;

  public PopUp(Page page, String popUpText) {
    this.popUp = page.getByText(popUpText);
  }

}
