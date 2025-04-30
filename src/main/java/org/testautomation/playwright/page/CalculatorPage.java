package org.testautomation.playwright.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.testautomation.playwright.enums.MachineFamily;
import org.testautomation.playwright.elements.AdvancedSettingsPopUp;
import org.testautomation.playwright.enums.Region;
import org.testautomation.playwright.utils.WaiterUtility;

public class CalculatorPage {

  private final Page page;
  private final AdvancedSettingsPopUp advancedSettingsPopUp;
  private final ServiceConfigurationComponent servicePage;
  private final CostDetailsComponent costDetails;
  private final Locator costUpdatedElement;

  public CalculatorPage(Page page, ServiceConfigurationComponent servicePage) {
    this.page = page;
    this.servicePage = servicePage;
    this.costDetails = new CostDetailsComponent(page);
    this.costUpdatedElement = page.getByText("Service cost updated");
    this.advancedSettingsPopUp = new AdvancedSettingsPopUp(page);
  }

  public void verifyCostUpdatedPopupAppears() {
    WaiterUtility.waitUntilAppears(costUpdatedElement);
  }

  public void verifyCostUpdatedPopupDisappears() {
    WaiterUtility.waitUntilDisappears(costUpdatedElement);
  }

  public void verifyAdvancedSettingsPopupAppears() {
    WaiterUtility.waitUntilAppears(advancedSettingsPopUp);
  }

  public void verifyAdvancedSettingsPopupDisappears() {
    WaiterUtility.waitUntilDisappears(advancedSettingsPopUp);
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

  public void verifyServiceAdvancesSettingsOptionsAreVisible() {
    servicePage.advancesSettingsOptionsAreVisible();
  }

  public void verifyServiceAdvancesSettingsOptionsAreHidden() {
    servicePage.advancesSettingsOptionsAreHidden();
  }

  public void selectMachineFamily(MachineFamily machineFamilyName) {
    String summary = readMachineTypeSummaryBlockText();
    servicePage.selectMachineFamily(machineFamilyName);
    WaiterUtility.waitForTextToChange(page, servicePage.getMachineTypeComponent().getMachineTypeSummaryBlock(), summary);
  }

  public void selectMachineSeries(String seriesName) {
    servicePage.selectMachineSeries(seriesName);
  }

  public void selectMachineType(String machineTypeName) {
    servicePage.selectMachineType(machineTypeName);
  }

  public String readMachineTypeSummaryBlockText() {
    return servicePage.readMachineTypeSummaryBlockText();
  }

  public String readSelectedRegion() {
    return servicePage.readSelectedRegion();
  }

  public void selectRegion(Region region) {
    servicePage.selectRegion(region);
  }

}
