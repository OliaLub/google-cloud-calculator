package org.testautomation.playwright.page.component;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.Getter;
import org.testautomation.playwright.enums.MachineFamily;
import org.testautomation.playwright.elements.Combobox;
import org.testautomation.playwright.enums.MachineSeries;
import org.testautomation.playwright.enums.MachineType;
import org.testautomation.playwright.utils.WaiterUtility;

@Getter
public class MachineTypeComponent extends BaseComponent {

  private final Combobox machineFamilyCombobox;
  private final Combobox seriesCombobox;
  private final Combobox machineTypeCombobox;
  private final Locator machineTypeSummaryBlock;

  MachineTypeComponent(Page page) {
    super(page);
    this.machineFamilyCombobox = new Combobox(page, "Machine Family");
    this.seriesCombobox = new Combobox(page, "Series");
    this.machineTypeCombobox = new Combobox(page, "Machine Type");
    this.machineTypeSummaryBlock = page.getByText("Based on your selection").locator("..");
  }

  public void selectMachineConfiguration(MachineType type) {
    logger.info("Selecting machine configuration: {}", type);
    selectMachineFamily(type.getFamily());
    selectSeries(type.getSeries());
    selectMachineType(type);
  }

  public String getMachineTypeSummaryBlockText() {
    return machineTypeSummaryBlock.innerText();
  }

  private void selectMachineFamily(MachineFamily machineFamilyName) {
    String summary = getMachineTypeSummaryBlockText();
    machineFamilyCombobox.selectOption(machineFamilyName.getFamilyName());
    machineFamilyCombobox.verifyOptionIsSelected(machineFamilyName.getFamilyName());
    WaiterUtility.waitForTextToChange(page, getMachineTypeSummaryBlock(), summary);
  }

  private void selectSeries(MachineSeries seriesName) {
    seriesCombobox.selectOption(seriesName.getSeriesName());
    seriesCombobox.verifyOptionIsSelected(seriesName.getSeriesName());
    assertThat(machineTypeSummaryBlock).containsText(seriesName.getSeriesName().toLowerCase());
  }

  private void selectMachineType(MachineType machineTypeName) {
    machineTypeCombobox.selectOption(machineTypeName.getTypeName());
    machineTypeCombobox.verifyOptionIsSelected(machineTypeName.getTypeName());
    assertThat(machineTypeSummaryBlock).containsText(machineTypeName.getTypeName());
  }

}
