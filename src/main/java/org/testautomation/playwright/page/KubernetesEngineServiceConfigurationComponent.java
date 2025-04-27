package org.testautomation.playwright.page;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.GetByRoleOptions;
import com.microsoft.playwright.options.AriaRole;
import java.util.List;

public class KubernetesEngineServiceConfigurationComponent extends ServiceConfigurationComponent {

  private final Locator kubernetesEditionLabel;
  private final Locator addGPUsButton;
  private final Locator enableConfidentialGkeNodesSwitch;
  private final Locator addSustainedUseDiscountsSwitch;

  public KubernetesEngineServiceConfigurationComponent(Page page) {
    super(page);
    this.kubernetesEditionLabel = page.getByText("Kubernetes Edition*").first();
    this.addGPUsButton = page.getByRole(AriaRole.SWITCH, new Page.GetByRoleOptions().setName("Add GPUs"));
    this.enableConfidentialGkeNodesSwitch = page.getByRole(AriaRole.SWITCH, new GetByRoleOptions().setName("Enable Confidential GKE Nodes"));
    this.addSustainedUseDiscountsSwitch = page.getByRole(AriaRole.SWITCH, new GetByRoleOptions().setName("Add sustained use discounts"));
  }

  @Override
  protected List<Locator> getAdvancedSettingsOptions() {
    return List.of(
        kubernetesEditionLabel,
        addGPUsButton,
        enableConfidentialGkeNodesSwitch,
        addSustainedUseDiscountsSwitch);
  }

}
