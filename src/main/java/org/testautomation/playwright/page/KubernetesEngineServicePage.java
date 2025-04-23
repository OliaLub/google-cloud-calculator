package org.testautomation.playwright.page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class KubernetesEngineServicePage extends ServicePage{

  private final Locator kubernetesEditionLabel;
  private final Locator addGPUsButton;

  public KubernetesEngineServicePage(Page page) {
    super(page);
    this.kubernetesEditionLabel = page.getByText("Kubernetes Edition*").first();
    this.addGPUsButton = page.getByRole(AriaRole.SWITCH, new Page.GetByRoleOptions().setName("Add GPUs"));
  }

  @Override
  public void advancesSettingsOptionsAreVisible() {
    assertThat(kubernetesEditionLabel).isVisible();
    assertThat(addGPUsButton).isVisible();
  }

  @Override
  public void advancesSettingsOptionsAreHidden() {
    assertThat(kubernetesEditionLabel).isHidden();
    assertThat(addGPUsButton).isHidden();
  }

}
