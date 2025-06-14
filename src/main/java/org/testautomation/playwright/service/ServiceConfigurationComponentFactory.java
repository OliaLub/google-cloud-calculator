package org.testautomation.playwright.service;

import com.microsoft.playwright.Page;
import lombok.experimental.UtilityClass;
import org.testautomation.playwright.enums.ServiceType;
import org.testautomation.playwright.page.configuration.InstancesServiceConfigurationComponent;
import org.testautomation.playwright.page.configuration.GKEServiceConfigurationComponent;
import org.testautomation.playwright.page.component.ServiceConfigurationComponent;
import org.testautomation.playwright.page.configuration.SoleTenantNodesServiceConfigurationComponent;

@UtilityClass
public class ServiceConfigurationComponentFactory {

  public static ServiceConfigurationComponent createComponent(ServiceType serviceType, Page page) {
    return switch (serviceType) {
      case INSTANCES -> new InstancesServiceConfigurationComponent(page);
      case SOLE_TENANT_NODES -> new SoleTenantNodesServiceConfigurationComponent(page);
      case GKE -> new GKEServiceConfigurationComponent(page);
      default -> throw new IllegalArgumentException("Unsupported service type: " + serviceType);
    };
  }

}
