package org.testautomation.playwright.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.testautomation.playwright.enums.Region;
import org.testautomation.playwright.enums.ServiceType;
import org.testautomation.playwright.page.component.ServiceConfigurationComponent;
import org.testautomation.playwright.service.ServiceConfigurationBuilderFactory.ServiceConfigurationBuilder;

@Getter
@Builder
@ToString
public class BackupForGKEService implements Service {

  private final Region region;
  private final int numberOfBackUpPlans;
  private final String averageNumberOfPodsPerBackupPlan;
  private final String amountOfDataBackedUpPerMonth;
  private final Boolean advancedSettingsEnabled;

  @Override
  public ServiceType getServiceType() {
    return ServiceType.BACKUP_FOR_GKE;
  }

  @Override
  public void applyConfiguration(ServiceConfigurationComponent configuration) {
    configuration.selectRegion(region);
  }

  public static class BackupForGKEServiceBuilder implements ServiceConfigurationBuilder<BackupForGKEService> {
    @Override
    public BackupForGKEService build() {
      return new BackupForGKEService(region, numberOfBackUpPlans, averageNumberOfPodsPerBackupPlan, amountOfDataBackedUpPerMonth, advancedSettingsEnabled);
    }
  }

}
