package org.testautomation.playwright.page;

import com.microsoft.playwright.Page;
import org.testautomation.playwright.elements.ServiceCostUpdatedPopUp;
import org.testautomation.playwright.elements.AdvancedSettingsPopUp;

public class EstimatePage {

  private final ServiceCostUpdatedPopUp costUpdatedPopUp;
  private final AdvancedSettingsPopUp advancedSettingsPopUp;
  private final ServicePage servicePage;
  private final CostDetails costDetails;

  public EstimatePage(Page page, ServicePage servicePage) {
    this.costUpdatedPopUp = new ServiceCostUpdatedPopUp(page);
    this.advancedSettingsPopUp = new AdvancedSettingsPopUp(page);
    this.servicePage = servicePage;
    this.costDetails = new CostDetails(page);
  }

  public void costUpdatedPopupAppears() {
    costUpdatedPopUp.waitUntilAppears();
  }

  public void costUpdatedPopupDisappears() {
    costUpdatedPopUp.waitUntilDisappears();
  }

  public void advancedSettingsPopupAppears() {
    advancedSettingsPopUp.waitUntilAppears();
  }

  public void advancedSettingsPopupDisappears() {
    advancedSettingsPopUp.waitUntilDisappears();
  }

  public String readCostText() {
    return costDetails.getCostText();
  }

  public double readCostValue() {
    return costDetails.getCostValue();
  }

  public void increaseServiceInstances(int times) {
    servicePage.increaseInstances(times);
  }

  public void enableServiceAdvancedSettings() {
    servicePage.enableAdvancedSettings();
  }

  public void serviceAdvancesSettingsOptionsAreVisible() {
    servicePage.advancesSettingsOptionsAreVisible();
  }

  public void serviceAdvancesSettingsOptionsAreHidden() {
    servicePage.advancesSettingsOptionsAreHidden();
  }

}
