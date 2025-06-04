package org.testautomation.playwright.end2end;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junitpioneer.jupiter.RetryingTest;
import org.testautomation.playwright.AbstractTest;
import org.testautomation.playwright.enums.Region;
import org.testautomation.playwright.enums.ServiceType;
import org.testautomation.playwright.model.SoleTenantNodesService.SoleTenantNodesServiceBuilder;
import org.testautomation.playwright.service.ServiceConfigurationBuilderFactory;

public class End2EndSoleTenantNodesTypeTests extends AbstractTest {

  private SoleTenantNodesServiceBuilder builder;
  private static final String EXPECTED_COST = "$17,539.41";

  @BeforeEach
  public void setUp(){
    serviceType = ServiceType.SOLE_TENANT_NODES;
    builder = ServiceConfigurationBuilderFactory.getBuilder(serviceType);
    calculator.addToEstimate(serviceType);
    activeService = calculator.getActiveService();
  }

  @RetryingTest(maxAttempts = 3)
  @DisplayName("Configure Sole-Tenant Nodes Compute Engine")
  public void verifySoleTenantNodesConfigurationsSet() {

    service = builder.serviceType(serviceType)
        .region(Region.TOKYO)
        .numberOfNodes(3)
        .build();

    activeService.fillInCalculationForm(service);

    String updatedCostText = costDetails.readTotalCost();
    assertThat(updatedCostText).isEqualTo(EXPECTED_COST);
  }

}
