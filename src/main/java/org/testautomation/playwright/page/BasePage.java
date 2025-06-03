package org.testautomation.playwright.page;

import com.microsoft.playwright.Page;

public abstract class BasePage {

  protected final Page page;

  protected BasePage(Page page) {
    this.page = page;
  }

  protected abstract BasePage openPage();

}
