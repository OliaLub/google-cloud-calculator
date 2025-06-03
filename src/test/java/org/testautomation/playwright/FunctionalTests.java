package org.testautomation.playwright;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.testautomation.playwright.enums.CommittedUse;
import org.testautomation.playwright.enums.Currency;
import org.testautomation.playwright.enums.MachineType;
import org.testautomation.playwright.enums.OperationSystem;
import org.testautomation.playwright.enums.Region;
import org.testautomation.playwright.enums.ServiceType;

public class FunctionalTests extends AbstractTest {

  @ParameterizedTest(name = "TC-1. Compute Engine price updates correctly based on number of instances: {arguments}")
  @EnumSource(value = ServiceType.class, names = {"INSTANCES", "GKE"})
  public void verifyComputeEnginePriceUpdatesBasedOnNumberOfInstances(ServiceType service) {
    calculator.addToEstimate(service);
    title.verifyCostUpdatedPopupAppears();

    String defaultCostText = costDetails.readTotalCost();
    assertThat(defaultCostText).isEqualTo(service.getDefaultCost());

    calculator.getActiveService().increaseInstances(2);
    title.verifyCostUpdatedPopupAppears();

    String updatedCostText = costDetails.readTotalCost();
    assertThat(updatedCostText).isNotEqualTo(service.getDefaultCost());
  }

  @ParameterizedTest(name = "TC-2. Instances configurations appear after enabling advanced  settings: {arguments}")
  @EnumSource(value = ServiceType.class, names = {"INSTANCES", "GKE"})
  public void verifyInstancesConfigurationsAppearAfterEnablingAdvancedSettings (ServiceType service) {
    calculator.addToEstimate(service);
    calculator.getActiveService().verifyAdvancesSettingsOptionsAreHidden().enableAdvancedSettings();

    calculator.verifyAdvancedSettingsPopupAppears();
    calculator.getActiveService().verifyAdvancesSettingsOptionsAreVisible();
    calculator.verifyAdvancedSettingsPopupDisappears();
  }

  static Stream<Arguments> calculatorFactoryProviderMachineType() {
    return Stream.of(Arguments.of(ServiceType.INSTANCES, MachineType.M2_ULTRAMEM_208, "n4-standard-2", "vCPUs: 2, RAM: 8 GiB", "vCPUs: 208, RAM: 5888 GiB"),
        Arguments.of(ServiceType.GKE, MachineType.M2_ULTRAMEM_208, "n1-standard-16", "vCPUs: 16, RAM: 60 GiB", "vCPUs: 208, RAM: 5888 GiB"));
  }

  @ParameterizedTest(name = "TC-3. Machine type and price updates according to selection: {arguments}")
  @MethodSource("calculatorFactoryProviderMachineType")
  public void verifyMachineTypeAndPriceUpdatedAccordingToSelection(ServiceType service, MachineType selectedType, String defaultMachineType, String defaultMachineFeatures, String selectedMachineFeatures) {
    calculator.addToEstimate(service);
    activeService = calculator.getActiveService();
    title.verifyCostUpdatedPopupAppears();

    String defaultCostText = costDetails.readTotalCost();
    assertThat(defaultCostText).isEqualTo(service.getDefaultCost());

    String defaultMachineTypeSummaryBlock = activeService.readMachineTypeSummaryBlockText();
    assertThat(defaultMachineTypeSummaryBlock).contains(defaultMachineType, defaultMachineFeatures);

    activeService.selectMachineConfiguration(selectedType);
    title.verifyCostUpdatedPopupAppears();

    String updatedCostText = costDetails.readTotalCost();
    assertThat(updatedCostText).isNotEqualTo(service.getDefaultCost());

    String updatedMachineTypeSummaryBlock = activeService.readMachineTypeSummaryBlockText();
    assertThat(updatedMachineTypeSummaryBlock).contains(selectedType.getTypeName(), selectedMachineFeatures);
  }

  static Stream<Arguments> calculatorFactoryProviderSelectedRegion() {
    return Stream.of(Arguments.of(ServiceType.INSTANCES, Region.IOWA, Region.TOKYO),
        Arguments.of(ServiceType.GKE, Region.IOWA, Region.LONDON));
  }

  @ParameterizedTest(name = "TC-5. Price changes based on selected region: {arguments}")
  @MethodSource("calculatorFactoryProviderSelectedRegion")
  public void verifyPriceUpdatedBasedOnSelectedRegion(ServiceType service, Region expectedDefaultRegion, Region expectedSelectedRegion) {
    calculator.addToEstimate(service);
    activeService = calculator.getActiveService();
    title.verifyCostUpdatedPopupAppears();

    String defaultCostText = costDetails.readTotalCost();
    assertThat(defaultCostText).isEqualTo(service.getDefaultCost());

    String defaultRegion = activeService.readSelectedRegion();
    assertThat(defaultRegion).isEqualTo(expectedDefaultRegion.getRegionName());

    activeService.selectRegion(expectedSelectedRegion);
    title.verifyCostUpdatedPopupAppears();

    String selectedRegion = activeService.readSelectedRegion();
    assertThat(selectedRegion).isEqualTo(expectedSelectedRegion.getRegionName());

    String updatedCostText = costDetails.readTotalCost();
    assertThat(updatedCostText).isNotEqualTo(service.getDefaultCost());
  }

  static Stream<Arguments> calculatorFactoryProviderCommittedUse() {
    return Stream.of(Arguments.of(ServiceType.INSTANCES, CommittedUse.NONE, CommittedUse.THREE_YEARS),
        Arguments.of(ServiceType.GKE, CommittedUse.NONE, CommittedUse.ONE_YEAR));
  }

  @ParameterizedTest(name = "TC-6. Price decreases based on selected discount: {arguments}")
  @MethodSource("calculatorFactoryProviderCommittedUse")
  public void verifyPriceUpdatedBasedOnCommittedUseDiscountOptions(ServiceType service, CommittedUse defaultTerm, CommittedUse selectedTerm) {
    calculator.addToEstimate(service);
    activeService = calculator.getActiveService();
    title.verifyCostUpdatedPopupAppears();
    String defaultCostText = costDetails.readTotalCost();
    assertThat(defaultCostText).isEqualTo(service.getDefaultCost());

    String defaultCommittedUseOption = activeService.readSelectedCommittedUseOption();
    assertThat(defaultCommittedUseOption).isEqualTo(defaultTerm.getTerm());

    activeService.selectCommittedUseOption(selectedTerm);
    title.verifyCostUpdatedPopupAppears();

    String selectedCommittedUseOption = activeService.readSelectedCommittedUseOption();
    assertThat(selectedCommittedUseOption).isEqualTo(selectedTerm.getTerm());

    String updatedCostText = costDetails.readTotalCost();
    assertThat(updatedCostText).isNotEqualTo(service.getDefaultCost());
  }

  static Stream<Arguments> calculatorFactoryProviderCurrency() {
    return Stream.of(Arguments.of(ServiceType.INSTANCES, Currency.EUR),
        Arguments.of(ServiceType.GKE, Currency.GBP));
  }

  @ParameterizedTest(name = "TC-7. Price updates with correct currency conversion: {arguments}")
  @MethodSource("calculatorFactoryProviderCurrency")
  public void verifyPriceUpdatedBasedOnSelectedCurrency(ServiceType service, Currency selectedCurrency) {
    calculator.addToEstimate(service);
    title.verifyCostUpdatedPopupAppears();
    assertThat(costDetails.readSelectedCurrency()).isEqualTo(Currency.USD);

    String defaultCostText = costDetails.readTotalCost();
    assertThat(defaultCostText).isEqualTo(service.getDefaultCost());

    costDetails.selectCurrency(selectedCurrency);
    assertThat(costDetails.readSelectedCurrency()).isEqualTo(selectedCurrency);

    String updatedCostText = costDetails.readTotalCost();
    assertThat(updatedCostText).contains(selectedCurrency.getCurrencySymbol());
  }

  static Stream<Arguments> calculatorFactoryProviderOperationSystem() {
    return Stream.of(
        Arguments.of(ServiceType.INSTANCES, OperationSystem.FREE, OperationSystem.PAID_UBUNTU_PRO),
        Arguments.of(ServiceType.GKE, OperationSystem.FREE_CONTAINER_OPTIMIZED, OperationSystem.PAID_WINDOWS_SERVER)
    );
  }

  @ParameterizedTest(name = "TC-8. Price changes based on selected operation system: {arguments}")
  @MethodSource("calculatorFactoryProviderOperationSystem")
  public void verifyServiceTypeChangedBasedOnSelectedOperationSystem (ServiceType service, OperationSystem defaultOperationSystem, OperationSystem selectedOperationSystem) {
    calculator.addToEstimate(service);
    activeService = calculator.getActiveService();

    title.verifyCostUpdatedPopupAppears();
    
    String defaultOS = activeService.readSelectedOperationSystem();
    assertThat(defaultOS).isEqualTo(defaultOperationSystem.getOsName());

    String defaultCostText = costDetails.readTotalCost();
    assertThat(defaultCostText).isEqualTo(service.getDefaultCost());

    activeService.selectOperationSystem(selectedOperationSystem);
    title.verifyCostUpdatedPopupAppears();

    String updatedOS = activeService.readSelectedOperationSystem();
    assertThat(updatedOS).isEqualTo(selectedOperationSystem.getOsName());

    String updatedCostText = costDetails.readTotalCost();
    assertThat(updatedCostText).isNotEqualTo(service.getDefaultCost());
  }

}
