package org.testautomation.playwright.page;

import com.microsoft.playwright.Page;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.testautomation.playwright.elements.AdvancedSettingsPopUp;
import org.testautomation.playwright.enums.ServiceType;
import org.testautomation.playwright.utils.WaiterUtility;

@Getter
public class CalculatorPage {

  private final Page page;
  private final AdvancedSettingsPopUp advancedSettingsPopUp;
  private final TitleComponent titleComponent;
  private ServiceConfigurationComponent activeService;
  private final Map <String, ServiceConfigurationComponent> serviceConfigurationMap;
  private final CostDetailsComponent costDetails;

  public CalculatorPage(Page page) {
    this.page = page;
    this.titleComponent = new TitleComponent(page);
    this.serviceConfigurationMap = new HashMap<>();
    this.costDetails = new CostDetailsComponent(page);
    this.advancedSettingsPopUp = new AdvancedSettingsPopUp(page);
  }

  public void addToEstimate(ServiceType serviceType){
    ServiceConfigurationComponent serviceConfiguration = costDetails.openAddToEstimateModal(page).selectProduct(serviceType, page);
    page.waitForCondition(() -> titleComponent.getActiveService().textContent().equals(serviceType.getProduct().getProductName()));
    serviceConfigurationMap.put(serviceType.getServiceName(), serviceConfiguration);
    activeService = serviceConfiguration;
  }

  public void verifyAdvancedSettingsPopupAppears() {
    WaiterUtility.waitUntilAppears(advancedSettingsPopUp);
  }

  public void verifyAdvancedSettingsPopupDisappears() {
    WaiterUtility.waitUntilDisappears(advancedSettingsPopUp);
  }

}
