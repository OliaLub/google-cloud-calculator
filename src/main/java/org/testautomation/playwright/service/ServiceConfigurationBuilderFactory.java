package org.testautomation.playwright.service;

import org.testautomation.playwright.enums.ServiceType;
import org.testautomation.playwright.model.BackupForGKEService;
import org.testautomation.playwright.model.GKEService;
import org.testautomation.playwright.model.InstancesService;
import org.testautomation.playwright.model.Service;
import org.testautomation.playwright.model.SoleTenantNodesService;

public class ServiceConfigurationBuilderFactory {

  @SuppressWarnings("unchecked")
  public static <T extends ServiceConfigurationBuilder<?>> T getBuilder(ServiceType type) {
    return switch (type) {
      case INSTANCES -> (T) InstancesService.builder();
      case SOLE_TENANT_NODES -> (T) SoleTenantNodesService.builder();
      case GKE -> (T) GKEService.builder();
      case BACKUP_FOR_GKE -> (T) BackupForGKEService.builder();
      default -> throw new IllegalArgumentException("Unsupported service type: " + type);
    };
  }

  public interface ServiceConfigurationBuilder<T extends Service> {
    T build();
  }

}
