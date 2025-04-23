package org.testautomation.playwright.page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class ComputeEngineServicePage extends ServicePage{

  private final Locator ephemeralPublicIPInstancesLabel;
  private final Locator staticPublicIPInstancesLabel;

  public ComputeEngineServicePage(Page page) {
    super(page);
    this.ephemeralPublicIPInstancesLabel = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("tooltip")).locator("xpath=ancestor::*[4]/div[contains(., \"Instances using ephemeral public IP\")]");
    this.staticPublicIPInstancesLabel = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("tooltip")).locator("xpath=ancestor::*[4]/div[contains(., \"Instances using static public IP\")]");
  }

  @Override
  public void advancesSettingsOptionsAreVisible() {
    assertThat(ephemeralPublicIPInstancesLabel).isVisible();
    assertThat(staticPublicIPInstancesLabel).isVisible();
  }

  @Override
  public void advancesSettingsOptionsAreHidden() {
    assertThat(ephemeralPublicIPInstancesLabel).isHidden();
    assertThat(staticPublicIPInstancesLabel).isHidden();
  }

}
