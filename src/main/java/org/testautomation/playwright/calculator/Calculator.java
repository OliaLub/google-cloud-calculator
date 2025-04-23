package org.testautomation.playwright.calculator;

import com.microsoft.playwright.Page;
import org.testautomation.playwright.page.ServicePage;

public interface Calculator {

  void openCalculator(Page page);
  ServicePage createServicePage(Page page);

}
