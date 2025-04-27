package org.testautomation.playwright.page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.GetByRoleOptions;
import com.microsoft.playwright.options.AriaRole;
import org.testautomation.playwright.utils.WaiterUtility;

public class MachineTypeComponent {

  private final Locator machineFamilyCombobox;
  private final Locator seriesCombobox;
  private final Locator machineTypeCombobox;
  private final Locator machineTypeSummaryBlock;

   MachineTypeComponent(Page page) {
    this.machineFamilyCombobox = page.getByRole(AriaRole.COMBOBOX, new GetByRoleOptions().setName("Machine Family"));
    this.seriesCombobox = page.getByRole(AriaRole.COMBOBOX, new GetByRoleOptions().setName("Series"));
    this.machineTypeCombobox = page.getByRole(AriaRole.COMBOBOX, new GetByRoleOptions().setName("Machine Type"));
    this.machineTypeSummaryBlock = page.getByText("Based on your selection").locator("..");
   }

  public void selectMachineFamily(Page page, String machineFamilyName) { // ENUM
    String summary = getMachineTypeSummaryBlockText();
    machineFamilyCombobox.click();
    page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(machineFamilyName)).click();
    assertThat(machineFamilyCombobox).containsText(machineFamilyName);
    WaiterUtility.waitForTextToChange(page, machineTypeSummaryBlock, summary);
  }

  public void selectSeries(Page page, String seriesName) {
    String summary = getMachineTypeSummaryBlockText();
    seriesCombobox.click();
    page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(seriesName)).click();
    assertThat(seriesCombobox).containsText(seriesName);
    assertThat(machineTypeSummaryBlock).containsText(seriesName.toLowerCase());
    WaiterUtility.waitForTextToChange(page, machineTypeSummaryBlock, summary);
  }

  public void selectMachineType(Page page, String machineTypeName) {
    machineTypeCombobox.click();
    page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(machineTypeName)).click();
    assertThat(machineTypeCombobox).containsText(machineTypeName);
    assertThat(machineTypeSummaryBlock).containsText(machineTypeName);
  }

  public String getMachineTypeSummaryBlockText() {
    return machineTypeSummaryBlock.innerText();
  }

}
