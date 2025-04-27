package org.testautomation.playwright.calculator;

import com.microsoft.playwright.Page;
import org.testautomation.playwright.page.ServiceConfigurationComponent;

public interface Calculator {

  void openCalculator(Page page);
  ServiceConfigurationComponent createServicePage(Page page);

}
