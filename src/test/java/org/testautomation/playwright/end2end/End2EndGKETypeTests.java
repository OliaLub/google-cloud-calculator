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
import org.testautomation.playwright.service.ServiceConfigurationBuilderFactory;

public class End2EndGKETypeTests extends AbstractTest {

  private GKEServiceBuilder builder;

  @BeforeEach
  public void setUp(){
    serviceType = ServiceType.GKE;
    builder = ServiceConfigurationBuilderFactory.getBuilder(serviceType);
    selectService(serviceType);
  }

  @Test
  public void verifyKubernetesEngineConfigurationsSet() {

     service = builder.numberOfNodes(5)
        .operatingSystem(OperationSystem.FREE_CONTAINER_OPTIMIZED)
        .machineType(MachineType.C2D_STANDARD_32)
        .region(Region.FRANKFURT)
        .committedUse(CommittedUse.THREE_YEARS)
        .build();

    activeService.fillInCalculationForm(service);

    String updatedCostText = costDetails.readTotalCost();
    assertThat(updatedCostText).isEqualTo("$3,153.18");
  }

}
