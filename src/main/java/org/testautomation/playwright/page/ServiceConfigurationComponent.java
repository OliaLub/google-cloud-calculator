package org.testautomation.playwright.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public abstract class ServiceConfigurationComponent {

  protected final Locator advancedSettingsButton;
  protected final Locator increaseInstancesButton;
  protected MachineTypeComponent machineTypeComponent;

  public ServiceConfigurationComponent(Page page) {
    this.advancedSettingsButton = page.getByRole(AriaRole.SWITCH, new Page.GetByRoleOptions().setName("Advanced settings"));
    this.increaseInstancesButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Increment")).first();
    this.machineTypeComponent = new MachineTypeComponent(page);
  }

  public void enableAdvancedSettings() {
    advancedSettingsButton.click();
    assertThat(advancedSettingsButton).isChecked();
  }

  public void increaseInstances(int times) {
    for (int i = 0; i < times; i++) {
      increaseInstancesButton.click();
    }
  }

  protected abstract List<Locator> getAdvancedSettingsOptions();

  public void advancesSettingsOptionsAreVisible(){
    getAdvancedSettingsOptions().forEach(locator -> assertThat(locator).isVisible());
  };

  public void advancesSettingsOptionsAreHidden(){
    getAdvancedSettingsOptions().forEach(locator -> assertThat(locator).isHidden());
  };

  public void selectMachineFamily(Page page, String machineFamilyName) {
    machineTypeComponent.selectMachineFamily(page, machineFamilyName);
  }

  public void selectMachineSeries(Page page, String seriesName) {
    machineTypeComponent.selectSeries(page, seriesName);
  }

  public void selectMachineType(Page page, String machineTypeName) {
    machineTypeComponent.selectMachineType(page, machineTypeName);
  }

  public String readMachineTypeSummaryBlockText() {
    return machineTypeComponent.getMachineTypeSummaryBlockText();
  }

}
