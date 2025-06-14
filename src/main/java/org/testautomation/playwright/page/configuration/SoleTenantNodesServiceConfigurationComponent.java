package org.testautomation.playwright.page.configuration;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import java.util.List;
import org.testautomation.playwright.page.component.ServiceConfigurationComponent;

public class SoleTenantNodesServiceConfigurationComponent extends ServiceConfigurationComponent {

  private final Locator enableCPUOvercommit;

  public SoleTenantNodesServiceConfigurationComponent(Page page) {
    super(page);
    this.enableCPUOvercommit = page.getByRole(AriaRole.SWITCH, new Page.GetByRoleOptions().setName("Enable CPU Overcommit"));
  }

  @Override
  public List<Locator> getAdvancedSettingsOptions() {
    return List.of(
        enableCPUOvercommit
    );
  }

}
