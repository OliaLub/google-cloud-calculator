package org.testautomation.playwright.page;

import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BasePage {

  protected final Page page;
  protected final Logger logger = LoggerFactory.getLogger(getClass());

  protected BasePage(Page page) {
    this.page = page;
    logger.debug("Initialized page: {}", getClass().getSimpleName());
  }

  protected abstract BasePage openPage();

}
