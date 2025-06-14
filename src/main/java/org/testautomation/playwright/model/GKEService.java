package org.testautomation.playwright.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.testautomation.playwright.enums.CommittedUse;
import org.testautomation.playwright.enums.MachineType;
import org.testautomation.playwright.enums.OperationSystem;
import org.testautomation.playwright.enums.Region;
import org.testautomation.playwright.enums.ServiceType;
import org.testautomation.playwright.page.component.ServiceConfigurationComponent;
import org.testautomation.playwright.service.ServiceConfigurationBuilderFactory.ServiceConfigurationBuilder;

@Getter
@Builder
@ToString
public class GKEService implements Service {

  private final int numberOfNodes;
  private final OperationSystem operatingSystem;
  private final MachineType machineType;
  private final String numberOfvCPUs;
  private final Region region;
  private final CommittedUse committedUse;

  @Override
  public ServiceType getServiceType() {
    return ServiceType.GKE;
  }

  @Override
  public void applyConfiguration(ServiceConfigurationComponent configuration) {
    configuration.setNumberOfInstances(numberOfNodes)
        .selectOperationSystem(operatingSystem)
        .selectMachineConfiguration(machineType)
        .selectRegion(region)
        .selectCommittedUseOption(committedUse);
  }

  public static class GKEServiceBuilder implements ServiceConfigurationBuilder<GKEService> {
    @Override
    public GKEService build() {
      return new GKEService(numberOfNodes, operatingSystem, machineType, numberOfvCPUs, region, committedUse);
    }
  }

}
