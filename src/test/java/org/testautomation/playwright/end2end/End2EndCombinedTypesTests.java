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
import org.testautomation.playwright.model.GKEService.GKEServiceBuilder;
import org.testautomation.playwright.model.InstancesService.InstancesServiceBuilder;
import org.testautomation.playwright.service.ServiceConfigurationBuilderFactory;

public class End2EndCombinedTypesTests extends AbstractTest {

  ServiceType secondServiceType = ServiceType.INSTANCES;
  private static InstancesServiceBuilder instancesBuilder;
  private static GKEServiceBuilder kubernetesBuilder;

  @BeforeEach
  public void setUp(){
    serviceType = ServiceType.GKE;
    kubernetesBuilder = ServiceConfigurationBuilderFactory.getBuilder(serviceType);
    instancesBuilder = ServiceConfigurationBuilderFactory.getBuilder(secondServiceType);
    selectService(serviceType);
  }

  @Test
  public void verifyKubernetesEngineAndComputeEngineConfigurationsSet() {

    service = kubernetesBuilder.numberOfNodes(5)
        .operatingSystem(OperationSystem.FREE_UBUNTU)
        .machineType(MachineType.M1_ULTRAMEM_80)
        .region(Region.TORONTO)
        .committedUse(CommittedUse.ONE_YEAR)
        .build();

    activeService.fillInCalculationForm(service);

    String kubernetesServiceCost = costDetails.readActiveServiceCost();
    assertThat(kubernetesServiceCost).isEqualTo("$29,983.99");

    calculator.addToEstimate(secondServiceType);

    service = instancesBuilder.numberOfInstances(1)
        .operatingSystem(OperationSystem.FREE)
        .machineType(MachineType.M1_ULTRAMEM_160)
        .region(Region.LOS_ANGELES)
        .committedUse(CommittedUse.ONE_YEAR)
        .build();

    activeService = calculator.getActiveService();
    activeService.fillInCalculationForm(service);

    String engineServiceCost = costDetails.readActiveServiceCost();
    assertThat(engineServiceCost).isEqualTo("$13,050.63");

    String updatedCostText = calculator.getCostDetails().readTotalCost();
    assertThat(updatedCostText).isEqualTo("$43,034.61");
  }

}
