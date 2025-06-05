package org.testautomation.playwright.page.component;

import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseComponent {

  protected final Page page;
  protected final Logger logger = LoggerFactory.getLogger(getClass());

  protected BaseComponent(Page page) {
    this.page = page;
    logger.debug("Initialized component: {}", getClass().getSimpleName());
  }
}
