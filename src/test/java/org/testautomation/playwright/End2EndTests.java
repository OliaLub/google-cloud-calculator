package org.testautomation.playwright;

import static org.assertj.core.api.Assertions.assertThat;

import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Test;
import org.testautomation.playwright.enums.CommittedUse;
import org.testautomation.playwright.enums.MachineType;
import org.testautomation.playwright.enums.Region;
import org.testautomation.playwright.enums.ServiceType;
import org.testautomation.playwright.model.InstancesService;
import org.testautomation.playwright.model.InstancesService.InstancesServiceBuilder;
import org.testautomation.playwright.page.CalculatorPage;
import org.testautomation.playwright.page.ServiceConfigurationComponent;
import org.testautomation.playwright.service.ServiceConfigurationBuilderFactory;

public class End2EndTests extends AbstractTest {

  @Test
  public void verifyComputeEngineConfigurationsSet(Page page) {

    CalculatorPage calculator = new CalculatorPage(page);
    ServiceType serviceType = ServiceType.INSTANCES;

    calculator.addToEstimate(serviceType.getProduct());
    InstancesServiceBuilder builder = ServiceConfigurationBuilderFactory.getBuilder(serviceType);
    InstancesService service = builder.machineType(MachineType.M2_ULTRAMEM_416).region(Region.TOKYO).committedUse(CommittedUse.ONE_YEAR).build();

    ServiceConfigurationComponent activeService = calculator.getActiveService();

    activeService.fillInCalculationForm(service);

    String updatedCostText = calculator.getCostDetails().readTotalCost();
    assertThat(updatedCostText).isEqualTo("$47,889.13");
  }

}
