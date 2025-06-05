package org.testautomation.playwright.model;

import lombok.Builder;
import lombok.Getter;
import org.testautomation.playwright.enums.CommittedUse;
import org.testautomation.playwright.enums.MachineType;
import org.testautomation.playwright.enums.Region;
import org.testautomation.playwright.enums.ServiceType;
import org.testautomation.playwright.page.component.ServiceConfigurationComponent;
import org.testautomation.playwright.service.ServiceConfigurationBuilderFactory.ServiceConfigurationBuilder;

@Getter
@Builder
public class SoleTenantNodesService implements Service {

  private final ServiceType serviceType;
  private final Region region;
  private final int numberOfNodes;
  private final MachineType nodeType;
  private final String localSSD;
  private final CommittedUse committedUse;

  @Override
  public ServiceType getServiceType() {
    return ServiceType.SOLE_TENANT_NODES;
  }

  @Override
  public void applyConfiguration(ServiceConfigurationComponent configuration) {
    configuration.selectServiceType(serviceType)
        .selectRegion(region)
        .setNumberOfInstances(numberOfNodes);
  }

  public static class SoleTenantNodesServiceBuilder implements ServiceConfigurationBuilder<SoleTenantNodesService> {
    @Override
    public SoleTenantNodesService build() {
      return new SoleTenantNodesService(serviceType, region, numberOfNodes, nodeType, localSSD, committedUse);
    }
  }

}
