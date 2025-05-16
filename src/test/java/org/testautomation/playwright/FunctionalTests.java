package org.testautomation.playwright;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testautomation.playwright.enums.CommittedUse;
import org.testautomation.playwright.enums.Currency;
import org.testautomation.playwright.enums.MachineType;
import org.testautomation.playwright.enums.OperationSystem;
import org.testautomation.playwright.enums.Region;
import org.testautomation.playwright.enums.ServiceType;
import org.testautomation.playwright.page.CostDetailsComponent;
import org.testautomation.playwright.page.ServiceConfigurationComponent;
import org.testautomation.playwright.page.TitleComponent;

public class FunctionalTests extends AbstractTest {

  static Stream<Arguments> calculatorFactoryProviderDefaultCost() {
    return Stream.of(Arguments.of(ServiceType.INSTANCES, "$69.98", "$209.95"),
         Arguments.of(ServiceType.GKE, "$628.80", "$1,740.39"));
  }

  @ParameterizedTest
  @MethodSource("calculatorFactoryProviderDefaultCost")
  public void verifyComputeEnginePriceUpdatesBasedOnNumberOfInstances(ServiceType service, String expectedDefaultCost, String expectedUpdatedCost) {
    calculator.addToEstimate(service);

    ServiceConfigurationComponent activeService = calculator.getActiveService();

    TitleComponent title = calculator.getTitleComponent();

    activeService.setNumberOfInstances(1);
    title.verifyCostUpdatedPopupAppears();

    String defaultCostText = calculator.getCostDetails().readTotalCost();
    assertThat(defaultCostText).isEqualTo(expectedDefaultCost);
    title.verifyCostUpdatedPopupDisappears();

    activeService.increaseInstances(2);
    title.verifyCostUpdatedPopupAppears();
    title.verifyCostUpdatedPopupDisappears();

    String updatedCostText = calculator.getCostDetails().readTotalCost();
    assertThat(updatedCostText).isEqualTo(expectedUpdatedCost);
  }

  static Stream<Arguments> calculatorFactoryProviderMachineType() {
    return Stream.of(Arguments.of(ServiceType.INSTANCES, MachineType.M2_ULTRAMEM_208, "n4-standard-2", "vCPUs: 2, RAM: 8 GiB", "vCPUs: 208, RAM: 5888 GiB", "$69.98", "$30,742.51"),
        Arguments.of(ServiceType.GKE, MachineType.M2_ULTRAMEM_208, "n1-standard-16", "vCPUs: 16, RAM: 60 GiB", "vCPUs: 208, RAM: 5888 GiB", "$2,851.99", "$153,786.57"));
  }

  @ParameterizedTest
  @MethodSource("calculatorFactoryProviderMachineType")
  public void verifyMachineTypeAndPriceUpdatedAccordingToSelection(ServiceType service, MachineType selectedType, String defaultMachineType, String defaultMachineFeatures, String selectedMachineFeatures,
      String expectedDefaultCost, String expectedUpdatedCost) {
    calculator.addToEstimate(service);

    ServiceConfigurationComponent activeService = calculator.getActiveService();

    TitleComponent title = calculator.getTitleComponent();
    title.verifyCostUpdatedPopupAppears();

    String defaultCostText = calculator.getCostDetails().readTotalCost();
    assertThat(defaultCostText).isEqualTo(expectedDefaultCost);
    title.verifyCostUpdatedPopupAppears();

    String defaultMachineTypeSummaryBlock = activeService.readMachineTypeSummaryBlockText();
    assertThat(defaultMachineTypeSummaryBlock).contains(defaultMachineType, defaultMachineFeatures);

    activeService.selectMachineConfiguration(selectedType);
    title.verifyCostUpdatedPopupAppears();

    String updatedCostText = calculator.getCostDetails().readTotalCost();
    assertThat(updatedCostText).isEqualTo(expectedUpdatedCost);

    String updatedMachineTypeSummaryBlock = activeService.readMachineTypeSummaryBlockText();
    assertThat(updatedMachineTypeSummaryBlock).contains(selectedType.getTypeName(), selectedMachineFeatures);
  }

  static Stream<Arguments> calculatorFactoryProviderSelectedRegion() {
    return Stream.of(Arguments.of(ServiceType.INSTANCES, Region.IOWA.getRegionName(), Region.LONDON.getRegionName(), "$69.98", "$79.78"),
        Arguments.of(ServiceType.GKE, Region.IOWA.getRegionName(), Region.LONDON.getRegionName(), "$2,851.99", "$3,652.93"));
  }

  @ParameterizedTest
  @MethodSource("calculatorFactoryProviderSelectedRegion")
  public void verifyPriceUpdatedBasedOnSelectedRegion(ServiceType service, String expectedDefaultRegion, String expectedSelectedRegion, String expectedDefaultCost, String expectedUpdatedCost) {
    calculator.addToEstimate(service);
    ServiceConfigurationComponent activeService = calculator.getActiveService();

    TitleComponent title = calculator.getTitleComponent();
    title.verifyCostUpdatedPopupAppears();

    String defaultCostText = calculator.getCostDetails().readTotalCost();
    assertThat(defaultCostText).isEqualTo(expectedDefaultCost);

    String defaultRegion = activeService.readSelectedRegion();
    assertThat(defaultRegion).isEqualTo(expectedDefaultRegion);
    title.verifyCostUpdatedPopupDisappears();

    activeService.selectRegion(Region.LONDON);
    title.verifyCostUpdatedPopupAppears();

    String selectedRegion = activeService.readSelectedRegion();
    assertThat(selectedRegion).isEqualTo(expectedSelectedRegion);

    String updatedCostText = calculator.getCostDetails().readTotalCost();
    assertThat(updatedCostText).isEqualTo(expectedUpdatedCost);
  }

  static Stream<Arguments> calculatorFactoryProviderCommittedUse() {
    return Stream.of(Arguments.of(ServiceType.INSTANCES, CommittedUse.NONE, CommittedUse.THREE_YEARS, "$69.98", "$31.93"),
        Arguments.of(ServiceType.GKE, CommittedUse.NONE, CommittedUse.THREE_YEARS, "$2,851.99", "$1,326.37"));
  }

  @ParameterizedTest
  @MethodSource("calculatorFactoryProviderCommittedUse")
  public void verifyPriceUpdatedBasedOnCommittedUseDiscountOptions(ServiceType service, CommittedUse defaultTerm, CommittedUse selectedTerm, String expectedDefaultCost, String expectedUpdatedCost) {
    calculator.addToEstimate(service);
    ServiceConfigurationComponent activeService = calculator.getActiveService();

    TitleComponent title = calculator.getTitleComponent();

    title.verifyCostUpdatedPopupAppears();
    String defaultCostText = calculator.getCostDetails().readTotalCost();
    assertThat(defaultCostText).isEqualTo(expectedDefaultCost);

    String defaultCommittedUseOption = activeService.readSelectedCommittedUseOption();
    assertThat(defaultCommittedUseOption).isEqualTo(defaultTerm.getValue());
    title.verifyCostUpdatedPopupDisappears();

    activeService.selectCommittedUseOption(selectedTerm);
    title.verifyCostUpdatedPopupAppears();

    String selectedCommittedUseOption = calculator.getActiveService().readSelectedCommittedUseOption();
    assertThat(selectedCommittedUseOption).isEqualTo(selectedTerm.getValue());

    String updatedCostText = calculator.getCostDetails().readTotalCost();
    assertThat(updatedCostText).isEqualTo(expectedUpdatedCost);
  }

  static Stream<Arguments> calculatorFactoryProviderCurrency() {
    return Stream.of(Arguments.of(ServiceType.INSTANCES, Currency.EUR, "$69.98", "€61.58"),
        Arguments.of(ServiceType.GKE, Currency.EUR, "$2,851.99", "€2,509.46"));
  }

  @ParameterizedTest
  @MethodSource("calculatorFactoryProviderCurrency")
  public void verifyPriceUpdatedBasedOnSelectedCurrency(ServiceType service, Currency selectedCurrency, String expectedDefaultCost, String expectedUpdatedCost) {
    calculator.addToEstimate(service);
    CostDetailsComponent costDetails = calculator.getCostDetails();

    TitleComponent title = calculator.getTitleComponent();
    title.verifyCostUpdatedPopupAppears();
    assertThat(costDetails.readSelectedCurrency()).isEqualTo(Currency.USD);

    String defaultCostText = costDetails.readTotalCost();
    assertThat(defaultCostText).isEqualTo(expectedDefaultCost);

    costDetails.selectCurrency(selectedCurrency);
    assertThat(costDetails.readSelectedCurrency()).isEqualTo(selectedCurrency);
    title.verifyCostUpdatedPopupAppears();

    String updatedCostText = costDetails.readTotalCost();
    assertThat(updatedCostText).isEqualTo(expectedUpdatedCost);
  }

  static Stream<Arguments> calculatorFactoryProviderOperationSystem() {
    return Stream.of(
        Arguments.of(ServiceType.INSTANCES, OperationSystem.FREE, OperationSystem.PAID_UBUNTU_PRO, "$69.98", "$72.89"),
        Arguments.of(ServiceType.GKE, OperationSystem.FREE_CONTAINER_OPTIMIZED, OperationSystem.PAID_WINDOWS_SERVER, "$2,851.99", "$5,538.39")
    );
  }

  @ParameterizedTest
  @MethodSource("calculatorFactoryProviderOperationSystem")
  public void verifyServiceTypeChangedBasedOnSelectedOperationSystem (ServiceType service, OperationSystem defaultOperationSystem, OperationSystem selectedOperationSystem, String expectedDefaultCost, String expectedUpdatedCost) {
    calculator.addToEstimate(service);

    ServiceConfigurationComponent activeService = calculator.getActiveService();

    TitleComponent title = calculator.getTitleComponent();
    title.verifyCostUpdatedPopupAppears();
    
    String defaultOS = activeService.readSelectedOperationSystem();
    assertThat(defaultOS).isEqualTo(defaultOperationSystem.getOsName());

    String defaultCostText = calculator.getCostDetails().readTotalCost();
    assertThat(defaultCostText).isEqualTo(expectedDefaultCost);
    title.verifyCostUpdatedPopupDisappears();

    activeService.selectOperationSystem(selectedOperationSystem);
    title.verifyCostUpdatedPopupAppears();

    String updatedOS = activeService.readSelectedOperationSystem();
    assertThat(updatedOS).isEqualTo(selectedOperationSystem.getOsName());

    String updatedCostText = calculator.getCostDetails().readTotalCost();
    assertThat(updatedCostText).isEqualTo(expectedUpdatedCost);
  }

  static Stream<Arguments> calculatorFactoryProviderAdvancedSettings() {
    return Stream.of(
        Arguments.of(ServiceType.INSTANCES),
        Arguments.of(ServiceType.GKE)
    );
  }

    @ParameterizedTest
    @MethodSource("calculatorFactoryProviderAdvancedSettings")
    public void verifyInstancesConfigurationsAppearAfterEnablingAdvancedSettings (ServiceType service) {
      calculator.addToEstimate(service);

      ServiceConfigurationComponent activeService = calculator.getActiveService();

      TitleComponent title = calculator.getTitleComponent();
      title.verifyCostUpdatedPopupAppears();

      activeService.verifyAdvancesSettingsOptionsAreHidden();
      activeService.enableAdvancedSettings();

      calculator.verifyAdvancedSettingsPopupAppears();
      activeService.verifyAdvancesSettingsOptionsAreVisible();
      calculator.verifyAdvancedSettingsPopupDisappears();
    }

}
