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
import org.testautomation.playwright.enums.OperationSystem;
import org.testautomation.playwright.enums.Region;
import org.testautomation.playwright.enums.ServiceType;
import org.testautomation.playwright.model.Service;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Getter
public abstract class ServiceConfigurationComponent {

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
  private final Combobox serviceTypeCombobox;
  private final Locator serviceTypeComboboxValue;

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
    this.serviceTypeCombobox = new Combobox(page, "Service Type");
    this.serviceTypeComboboxValue = serviceTypeCombobox.getCombobox().locator("span").locator("span").last();
  }

  public ServiceConfigurationComponent selectServiceType(ServiceType serviceType) {
    serviceTypeCombobox.selectOption(serviceType.getServiceName());
    assertThat(serviceTypeComboboxValue).containsText(serviceType.getServiceName());
    return this;
  }

  public ServiceConfigurationComponent enableAdvancedSettings() {
    advancedSettingsButton.click();
    assertThat(advancedSettingsButton).isChecked();
    return this;
  }

  public ServiceConfigurationComponent setNumberOfInstances(int number) {
    setNumberOfInstancesInput.fill(String.valueOf(number));
    Assertions.assertThat(setNumberOfInstancesInput.inputValue()).isEqualTo(String.valueOf(number));
    return this;
  }

  public void increaseInstances(int times) {
    for (int i = 0; i < times; i++) {
      increaseInstancesButton.click();
    }
  }

  public String readSelectedOperationSystem() {
    return operationSystemComboboxValue.innerText();
  }

  public ServiceConfigurationComponent selectOperationSystem(OperationSystem operationSystem) {
    operationSystemCombobox.selectOption(operationSystem.getOsName());
    assertThat(operationSystemComboboxValue).containsText(operationSystem.getOsName());
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

  protected abstract List<Locator> getAdvancedSettingsOptions();

  public void verifyAdvancesSettingsOptionsAreVisible(){
    getAdvancedSettingsOptions().forEach(locator -> assertThat(locator).isVisible());
  }

  public void verifyAdvancesSettingsOptionsAreHidden(){
    getAdvancedSettingsOptions().forEach(locator -> assertThat(locator).isHidden());
  }

  public void fillInCalculationForm(Service configuration) {
    configuration.applyConfiguration(this);
    TitleComponent titleComponent = new TitleComponent(page);
    titleComponent.waitForPriceToStabilize();
  }

}
