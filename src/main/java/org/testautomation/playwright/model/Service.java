package org.testautomation.playwright.model;

import org.testautomation.playwright.enums.ServiceType;
import org.testautomation.playwright.page.ServiceConfigurationComponent;

public interface Service {

  ServiceType getServiceType();

  void applyConfiguration(ServiceConfigurationComponent component);

}
