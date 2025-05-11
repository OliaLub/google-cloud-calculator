package org.testautomation.playwright.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Locator.FilterOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import java.util.List;
import lombok.Getter;
import org.assertj.core.api.Assertions;
import org.testautomation.playwright.elements.Combobox;
import org.testautomation.playwright.enums.CommittedUse;
import org.testautomation.playwright.enums.MachineType;
import org.testautomation.playwright.enums.Region;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Getter
public class ServiceConfigurationComponent {

  private final Page page;
  private final Locator advancedSettingsButton;
  private final Locator increaseInstancesButton;
  private final Locator setNumberOfInstancesInput;
  private final Combobox operationSystemCombobox;
  private final Locator operationSystemComboboxValue;
  private final MachineTypeComponent machineTypeComponent;
  private final Combobox regionCombobox;
  private final Locator regionComboboxValue;
  private final CommittedUseComponent committedUseComponent;

  public ServiceConfigurationComponent(Page page) {
    this.page = page;
    this.advancedSettingsButton = page.getByRole(AriaRole.SWITCH, new Page.GetByRoleOptions().setName("Advanced settings"));
    this.setNumberOfInstancesInput = page.getByRole(AriaRole.SPINBUTTON).first();
    this.increaseInstancesButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Increment")).first();
    this.operationSystemCombobox = new Combobox(page,"Operating System / Software");
    this.operationSystemComboboxValue = operationSystemCombobox.getCombobox().locator("span").filter(new FilterOptions().setHasText(":")).first();
    this.machineTypeComponent = new MachineTypeComponent(page);
    this.regionCombobox = new Combobox(page,"Region");
    this.regionComboboxValue = regionCombobox.getCombobox().locator("span").filter(new FilterOptions().setHasText("(")).first();
    this.committedUseComponent = new CommittedUseComponent(page);
  }

  public void enableAdvancedSettings() {
    advancedSettingsButton.click();
    assertThat(advancedSettingsButton).isChecked();
  }

  public void setNumberOfInstances(String number) {
    setNumberOfInstancesInput.fill(number);
    Assertions.assertThat(setNumberOfInstancesInput.inputValue()).isEqualTo(number);
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

  public String readSelectedOperationSystem() {
    return operationSystemComboboxValue.innerText();
  }

  public ServiceConfigurationComponent selectOperationSystem(String operationSystem) {
    operationSystemCombobox.selectOption(operationSystem);
    assertThat(operationSystemComboboxValue).containsText(operationSystem);
    return this;
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

  public void fillInCalculationForm(Service configuration) {
    configuration.applyConfiguration(this);
    TitleComponent titleComponent = new TitleComponent(page);
    titleComponent.waitForPriceToStabilize();
  }

}
