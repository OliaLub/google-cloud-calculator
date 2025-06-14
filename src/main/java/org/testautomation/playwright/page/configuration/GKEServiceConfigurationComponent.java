package org.testautomation.playwright.page.configuration;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.GetByRoleOptions;
import com.microsoft.playwright.options.AriaRole;
import java.util.List;
import org.testautomation.playwright.page.component.ServiceConfigurationComponent;

public class GKEServiceConfigurationComponent extends ServiceConfigurationComponent {

  private final Locator kubernetesEditionLabel;
  private final Locator addGPUsButton;
  private final Locator enableConfidentialGkeNodesSwitch;
  private final Locator addSustainedUseDiscountsSwitch;

  public GKEServiceConfigurationComponent(Page page) {
    super(page);
    this.kubernetesEditionLabel = page.getByText("Kubernetes Edition*").first();
    this.addGPUsButton = page.getByRole(AriaRole.SWITCH, new Page.GetByRoleOptions().setName("Add GPUs"));
    this.enableConfidentialGkeNodesSwitch = page.getByRole(AriaRole.SWITCH, new GetByRoleOptions().setName("Enable Confidential GKE Nodes"));
    this.addSustainedUseDiscountsSwitch = page.getByRole(AriaRole.SWITCH, new GetByRoleOptions().setName("Add sustained use discounts"));
  }

  @Override
  public List<Locator> getAdvancedSettingsOptions() {
    return List.of(
        kubernetesEditionLabel,
        addGPUsButton,
        enableConfidentialGkeNodesSwitch,
        addSustainedUseDiscountsSwitch);
  }

}
