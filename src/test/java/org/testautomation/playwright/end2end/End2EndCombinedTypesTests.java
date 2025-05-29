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
  private InstancesServiceBuilder instancesBuilder;
  private GKEServiceBuilder kubernetesBuilder;
  private static final String EXPECTED_GKE_COST = "$29,983.99";
  private static final String EXPECTED_ENGINE_COST = "$13,050.63";
  private static final String EXPECTED_TOTAL_COST = "$43,034.61";

  @BeforeEach
  public void setUp(){
    serviceType = ServiceType.GKE;
    kubernetesBuilder = ServiceConfigurationBuilderFactory.getBuilder(serviceType);
    instancesBuilder = ServiceConfigurationBuilderFactory.getBuilder(secondServiceType);
    calculator.addToEstimate(serviceType);
    activeService = calculator.getActiveService();
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
    assertThat(kubernetesServiceCost).isEqualTo(EXPECTED_GKE_COST);

    calculator.addToEstimate(secondServiceType);

    service = instancesBuilder.numberOfInstances(1)
        .operatingSystem(OperationSystem.FREE)
        .machineType(MachineType.M1_ULTRAMEM_160)
        .region(Region.LOS_ANGELES)
        .committedUse(CommittedUse.ONE_YEAR)
        .build();

    calculator.getActiveService().fillInCalculationForm(service);

    String engineServiceCost = costDetails.readActiveServiceCost();
    assertThat(engineServiceCost).isEqualTo(EXPECTED_ENGINE_COST);

    String updatedCostText = calculator.getCostDetails().readTotalCost();
    assertThat(updatedCostText).isEqualTo(EXPECTED_TOTAL_COST);
  }

}
