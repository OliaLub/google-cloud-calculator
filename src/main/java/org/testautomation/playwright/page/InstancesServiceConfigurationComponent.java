package org.testautomation.playwright.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import java.util.List;

public class InstancesServiceConfigurationComponent extends ServiceConfigurationComponent {

  private final Locator ephemeralPublicIPInstancesLabel;
  private final Locator staticPublicIPInstancesLabel;
  private final Locator enableConfidentialVmServiceSwitch;

  public InstancesServiceConfigurationComponent(Page page) {
    super(page);
    this.ephemeralPublicIPInstancesLabel = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("tooltip")).locator("xpath=ancestor::*[4]/div[contains(., \"Instances using ephemeral public IP\")]");
    this.staticPublicIPInstancesLabel = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("tooltip")).locator("xpath=ancestor::*[4]/div[contains(., \"Instances using static public IP\")]");
    this.enableConfidentialVmServiceSwitch = page.getByRole(AriaRole.SWITCH, new Page.GetByRoleOptions().setName("Enable Confidential VM service"));
  }

  protected List<Locator> getAdvancedSettingsOptions() {
    return List.of(
        ephemeralPublicIPInstancesLabel,
        staticPublicIPInstancesLabel,
        enableConfidentialVmServiceSwitch
    );
  }

}
