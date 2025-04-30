package org.testautomation.playwright.page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.Getter;
import org.testautomation.playwright.enums.MachineFamily;
import org.testautomation.playwright.elements.Combobox;

@Getter
public class MachineTypeComponent {

  private final Combobox machineFamilyCombobox;
  private final Combobox seriesCombobox;
  private final Combobox machineTypeCombobox;
  private final Locator machineTypeSummaryBlock;

   MachineTypeComponent(Page page) {
    this.machineFamilyCombobox = new Combobox(page, "Machine Family");
    this.seriesCombobox = new Combobox(page, "Series");
    this.machineTypeCombobox = new Combobox(page, "Machine Type");
    this.machineTypeSummaryBlock = page.getByText("Based on your selection").locator("..");
   }

  public void selectMachineFamily(MachineFamily machineFamilyName) {
    machineFamilyCombobox.selectOption(machineFamilyName.getFamilyName());
    machineFamilyCombobox.verifyOptionIsSelected(machineFamilyName.getFamilyName());
  }

  public void selectSeries(String seriesName) {
    seriesCombobox.selectOption(seriesName);
    seriesCombobox.verifyOptionIsSelected(seriesName);
    assertThat(machineTypeSummaryBlock).containsText(seriesName.toLowerCase());
  }

  public void selectMachineType(String machineTypeName) {
    machineTypeCombobox.selectOption(machineTypeName);
    machineTypeCombobox.verifyOptionIsSelected(machineTypeName);
    assertThat(machineTypeSummaryBlock).containsText(machineTypeName);
  }

  public String getMachineTypeSummaryBlockText() {
    return machineTypeSummaryBlock.innerText();
  }

}
