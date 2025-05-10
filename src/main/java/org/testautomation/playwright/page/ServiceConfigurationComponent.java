package org.testautomation.playwright.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Locator.FilterOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import java.util.List;
import lombok.Getter;
import org.testautomation.playwright.elements.Combobox;
import org.testautomation.playwright.enums.CommittedUse;
import org.testautomation.playwright.enums.MachineType;
import org.testautomation.playwright.enums.Region;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Getter
public class ServiceConfigurationComponent {

  protected final Locator advancedSettingsButton;
  protected final Locator increaseInstancesButton;
  protected final Combobox regionCombobox;
  protected final Locator regionComboboxValue;
  protected final MachineTypeComponent machineTypeComponent;
  protected final CommittedUseComponent committedUseComponent;

  public ServiceConfigurationComponent(Page page) {
    this.advancedSettingsButton = page.getByRole(AriaRole.SWITCH, new Page.GetByRoleOptions().setName("Advanced settings"));
    this.increaseInstancesButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Increment")).first();
    this.regionCombobox = new Combobox(page,"Region");
    this.regionComboboxValue = regionCombobox.getCombobox().locator("span").filter(new FilterOptions().setHasText("(")).first();
    this.machineTypeComponent = new MachineTypeComponent(page);
    this.committedUseComponent = new CommittedUseComponent(page);
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
  }

  public void advancesSettingsOptionsAreHidden(){
    getAdvancedSettingsOptions().forEach(locator -> assertThat(locator).isHidden());
  }

  public ServiceConfigurationComponent selectMachineConfiguration(MachineType machineType) {
    machineTypeComponent.selectMachineConfiguration(machineType);
    return this;
  }

  public String readMachineTypeSummaryBlockText() {
    return machineTypeComponent.getMachineTypeSummaryBlockText();
  }

  public String readSelectedRegion() {
    return regionComboboxValue.innerText();
  }

  public ServiceConfigurationComponent selectRegion(Region region) {
    regionCombobox.selectOption(region.getRegionName());
    assertThat(regionComboboxValue).containsText(region.getRegionName());
    return this;
  }

  public ServiceConfigurationComponent selectCommittedUseOption(CommittedUse term) {
    committedUseComponent.selectOption(term);
    return this;
  }

  public String readSelectedCommittedUseOption() {
    return committedUseComponent.getSelectedOption();
  }

}
