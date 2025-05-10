package org.testautomation.playwright.model;

import lombok.Builder;
import lombok.Getter;
import org.testautomation.playwright.enums.CommittedUse;
import org.testautomation.playwright.enums.MachineType;
import org.testautomation.playwright.enums.Region;
import org.testautomation.playwright.enums.ServiceType;
import org.testautomation.playwright.page.ServiceConfigurationComponent;
import org.testautomation.playwright.service.ServiceConfigurationBuilderFactory.ServiceConfigurationBuilder;

@Getter
@Builder
public class InstancesService implements Service {

  private final int numberOfInstances;
  private final String operatingSystem;
  private final MachineType machineType;
  private final String numberOfvCPUs;
  private final Region region;
  private final CommittedUse committedUse;

  @Override
  public ServiceType getServiceType() {
    return ServiceType.INSTANCES;
  }

  @Override
  public void applyConfiguration(ServiceConfigurationComponent component) {
    component.selectMachineConfiguration(machineType)
    .selectRegion(region)
        // other fields
    .selectCommittedUseOption(committedUse);
  }

  public static class InstancesServiceBuilder implements ServiceConfigurationBuilder<InstancesService> {
    @Override
    public InstancesService build() {
      return new InstancesService(numberOfInstances, operatingSystem, machineType, numberOfvCPUs, region, committedUse);
    }
  }

}
