package org.testautomation.playwright.end2end;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testautomation.playwright.AbstractTest;
import org.testautomation.playwright.enums.Region;
import org.testautomation.playwright.enums.ServiceType;
import org.testautomation.playwright.model.SoleTenantNodesService.SoleTenantNodesServiceBuilder;
import org.testautomation.playwright.service.ServiceConfigurationBuilderFactory;

public class End2EndSoleTenantNodesTypeTests extends AbstractTest {

  private static SoleTenantNodesServiceBuilder builder;

  @BeforeEach
  public void setUp(){
    serviceType = ServiceType.SOLE_TENANT_NODES;
    builder = ServiceConfigurationBuilderFactory.getBuilder(serviceType);
    selectService(serviceType);
  }

  @Test
  public void verifySoleTenantNodesConfigurationsSet() {

    service = builder.serviceType(serviceType)
        .region(Region.TOKYO)
        .numberOfNodes(3)
        .build();

    activeService.fillInCalculationForm(service);

    String updatedCostText = costDetails.readTotalCost();
    assertThat(updatedCostText).isEqualTo("$17,539.41");
  }

}
