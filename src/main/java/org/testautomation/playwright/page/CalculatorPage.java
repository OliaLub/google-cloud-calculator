package org.testautomation.playwright.page;

import com.microsoft.playwright.Page;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.testautomation.playwright.elements.AdvancedSettingsPopUp;
import org.testautomation.playwright.enums.ServiceType;
import org.testautomation.playwright.utils.WaiterUtility;

@Getter
public class CalculatorPage extends BasePage{

  private static final String URL = "https://cloud.google.com/products/calculator";
  private final AdvancedSettingsPopUp advancedSettingsPopUp;
  private final TitleComponent titleComponent;
  private ServiceConfigurationComponent activeService;
  private final Map <ServiceType, ServiceConfigurationComponent> serviceConfigurationMap;
  private final CostDetailsComponent costDetails;

  public CalculatorPage(Page page) {
    super(page);
    this.titleComponent = new TitleComponent(page);
    this.serviceConfigurationMap = new HashMap<>();
    this.costDetails = new CostDetailsComponent(page);
    this.advancedSettingsPopUp = new AdvancedSettingsPopUp(page);
  }

  @Override
  public CalculatorPage openPage(){
    page.navigate(URL);
    return this;
  }

  public CalculatorPage addToEstimate(ServiceType serviceType){
    ServiceConfigurationComponent serviceConfiguration = costDetails.openAddToEstimateModal(page).selectProduct(serviceType, page);
    WaiterUtility.waitForText(page,titleComponent.getActiveService(),serviceType.getProduct().getProductName());
    serviceConfigurationMap.put(serviceType, serviceConfiguration);
    activeService = serviceConfiguration;
    return this;
  }

  public CalculatorPage verifyAdvancedSettingsPopupAppears() {
    WaiterUtility.waitUntilAppears(advancedSettingsPopUp);
    return this;
  }

  public CalculatorPage verifyAdvancedSettingsPopupDisappears() {
    WaiterUtility.waitUntilDisappears(advancedSettingsPopUp);
    return this;
  }

}
