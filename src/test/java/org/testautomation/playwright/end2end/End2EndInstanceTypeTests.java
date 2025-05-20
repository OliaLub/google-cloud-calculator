package org.testautomation.playwright.end2end;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testautomation.playwright.AbstractTest;
import org.testautomation.playwright.enums.CommittedUse;
import org.testautomation.playwright.enums.MachineType;
import org.testautomation.playwright.enums.OperationSystem;
import org.testautomation.playwright.enums.Region;
import org.testautomation.playwright.enums.ServiceType;
import org.testautomation.playwright.model.InstancesService.InstancesServiceBuilder;
import org.testautomation.playwright.model.Service;
import org.testautomation.playwright.service.ServiceConfigurationBuilderFactory;

public class End2EndInstanceTypeTests extends AbstractTest {

  private static InstancesServiceBuilder builder;

  @BeforeEach
  public void setUp(){
    serviceType = ServiceType.INSTANCES;
    builder = ServiceConfigurationBuilderFactory.getBuilder(serviceType);
    selectService(serviceType);
  }

  @Test
  public void verifyComputeEngineConfigurationsSet() {

    service = builder.numberOfInstances(2)
        .operatingSystem(OperationSystem.PAID_UBUNTU_PRO)
        .machineType(MachineType.M2_ULTRAMEM_416)
        .region(Region.TOKYO)
        .committedUse(CommittedUse.ONE_YEAR)
        .build();

    activeService.fillInCalculationForm(service);

    String updatedCostText = costDetails.readTotalCost();
    assertThat(updatedCostText).isEqualTo("$97,048.22");
  }

  @Test
  public void verifyTwoComputeEngineInstancesConfigurationsSet() {

    service = builder.numberOfInstances(1)
        .operatingSystem(OperationSystem.FREE)
        .machineType(MachineType.C2D_STANDARD_16)
        .region(Region.FRANKFURT)
        .committedUse(CommittedUse.ONE_YEAR)
        .build();

    activeService.fillInCalculationForm(service);


    String firstEngineServiceCost = costDetails.readActiveServiceCost();
    assertThat(firstEngineServiceCost).isEqualTo("$431.58");

    calculator.addToEstimate(serviceType);
    Service secondService = builder.numberOfInstances(1)
        .operatingSystem(OperationSystem.FREE)
        .machineType(MachineType.M2_MEGAMEM_416)
        .region(Region.LONDON)
        .committedUse(CommittedUse.THREE_YEARS)
        .build();

    activeService = calculator.getActiveService();
    activeService.fillInCalculationForm(secondService);

    String secondEngineServiceCost = costDetails.readActiveServiceCost();
    assertThat(secondEngineServiceCost).isEqualTo("$15,985.21");

    String updatedCostText = costDetails.readTotalCost();
    assertThat(updatedCostText).isEqualTo("$16,416.79");
  }

}
