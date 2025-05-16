package org.testautomation.playwright;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.testautomation.playwright.enums.CommittedUse;
import org.testautomation.playwright.enums.MachineType;
import org.testautomation.playwright.enums.OperationSystem;
import org.testautomation.playwright.enums.Region;
import org.testautomation.playwright.enums.ServiceType;
import org.testautomation.playwright.model.GKEService.GKEServiceBuilder;
import org.testautomation.playwright.model.InstancesService;
import org.testautomation.playwright.model.InstancesService.InstancesServiceBuilder;
import org.testautomation.playwright.model.Service;
import org.testautomation.playwright.model.SoleTenantNodesService;
import org.testautomation.playwright.model.SoleTenantNodesService.SoleTenantNodesServiceBuilder;
import org.testautomation.playwright.page.CostDetailsComponent;
import org.testautomation.playwright.page.ServiceConfigurationComponent;
import org.testautomation.playwright.service.ServiceConfigurationBuilderFactory;

public class End2EndTests extends AbstractTest {

  @Test
  public void verifyComputeEngineConfigurationsSet() {

    ServiceType serviceType = ServiceType.INSTANCES;

    calculator.addToEstimate(serviceType);
    InstancesServiceBuilder builder = ServiceConfigurationBuilderFactory.getBuilder(serviceType);

    InstancesService service = builder.numberOfInstances(2)
        .operatingSystem(OperationSystem.PAID_UBUNTU_PRO)
        .machineType(MachineType.M2_ULTRAMEM_416)
        .region(Region.TOKYO)
        .committedUse(CommittedUse.ONE_YEAR)
        .build();

    ServiceConfigurationComponent activeService = calculator.getActiveService();
    activeService.fillInCalculationForm(service);

    String updatedCostText = calculator.getCostDetails().readTotalCost();
    assertThat(updatedCostText).isEqualTo("$97,048.22");
  }

  @Test
  public void verifyKubernetesEngineConfigurationsSet() {

    ServiceType serviceType = ServiceType.GKE;

    calculator.addToEstimate(serviceType);
    GKEServiceBuilder builder = ServiceConfigurationBuilderFactory.getBuilder(serviceType);

    Service service = builder.numberOfNodes(5)
        .operatingSystem(OperationSystem.FREE_CONTAINER_OPTIMIZED)
        .machineType(MachineType.C2D_STANDARD_32)
        .region(Region.FRANKFURT)
        .committedUse(CommittedUse.THREE_YEARS)
        .build();

    ServiceConfigurationComponent activeService = calculator.getActiveService();
    activeService.fillInCalculationForm(service);

    String updatedCostText = calculator.getCostDetails().readTotalCost();
    assertThat(updatedCostText).isEqualTo("$3,153.18");
  }

  @Test
  public void verifyKubernetesEngineAndComputeEngineConfigurationsSet() {

    ServiceType serviceType = ServiceType.GKE;

    calculator.addToEstimate(serviceType);
    GKEServiceBuilder builder = ServiceConfigurationBuilderFactory.getBuilder(serviceType);

    Service service = builder.numberOfNodes(5)
        .operatingSystem(OperationSystem.FREE_UBUNTU)
        .machineType(MachineType.M1_ULTRAMEM_80)
        .region(Region.TORONTO)
        .committedUse(CommittedUse.ONE_YEAR)
        .build();

    ServiceConfigurationComponent activeService = calculator.getActiveService();
    activeService.fillInCalculationForm(service);

    CostDetailsComponent costDetails = calculator.getCostDetails();
    String kubernetesServiceCost = costDetails.readActiveServiceCost();
    assertThat(kubernetesServiceCost).isEqualTo("$29,983.99");

    ServiceType secondServiceType = ServiceType.INSTANCES;

    calculator.addToEstimate(secondServiceType);
    InstancesServiceBuilder secondBuilder = ServiceConfigurationBuilderFactory.getBuilder(secondServiceType);

    service = secondBuilder.numberOfInstances(1)
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

  @Test
  public void verifyTwoComputeEngineInstancesConfigurationsSet() {

    ServiceType serviceType = ServiceType.INSTANCES;

    calculator.addToEstimate(serviceType);
    InstancesServiceBuilder builder = ServiceConfigurationBuilderFactory.getBuilder(serviceType);

    Service service = builder.numberOfInstances(1)
        .operatingSystem(OperationSystem.FREE)
        .machineType(MachineType.C2D_STANDARD_16)
        .region(Region.FRANKFURT)
        .committedUse(CommittedUse.ONE_YEAR)
        .build();

    ServiceConfigurationComponent activeService = calculator.getActiveService();
    activeService.fillInCalculationForm(service);

    CostDetailsComponent costDetails = calculator.getCostDetails();
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

    String updatedCostText = calculator.getCostDetails().readTotalCost();
    assertThat(updatedCostText).isEqualTo("$16,416.79");
  }

  @Test
  public void verifySoleTenantNodesConfigurationsSet() {

    ServiceType serviceType = ServiceType.SOLE_TENANT_NODES;

    calculator.addToEstimate(serviceType);

    SoleTenantNodesServiceBuilder builder = ServiceConfigurationBuilderFactory.getBuilder(serviceType);
    SoleTenantNodesService service = builder.serviceType(serviceType)
        .region(Region.TOKYO)
        .numberOfNodes(3)
        .build();

    ServiceConfigurationComponent activeService = calculator.getActiveService();
    activeService.fillInCalculationForm(service);

    String updatedCostText = calculator.getCostDetails().readTotalCost();
    assertThat(updatedCostText).isEqualTo("$17,539.41");
  }

}
