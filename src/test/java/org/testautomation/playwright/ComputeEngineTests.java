package org.testautomation.playwright;

import static org.assertj.core.api.Assertions.assertThat;

import com.microsoft.playwright.Page;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testautomation.playwright.calculator.Calculator;
import org.testautomation.playwright.enums.CommittedUse;
import org.testautomation.playwright.enums.Currency;
import org.testautomation.playwright.enums.MachineType;
import org.testautomation.playwright.enums.Region;
import org.testautomation.playwright.factory.CalculatorFactory;
import org.testautomation.playwright.factory.ComputeEngineFactory;
import org.testautomation.playwright.factory.KubernetesEngineFactory;
import org.testautomation.playwright.page.CalculatorPage;

public class ComputeEngineTests extends AbstractTest{

  static Stream<Arguments> calculatorFactoryProviderDefaultCost() {
    return Stream.of(
        Arguments.of(new ComputeEngineFactory(), "$69.98", "$209.95"),
        Arguments.of(new KubernetesEngineFactory(), "$2,851.99", "$3,963.58")
       // Arguments.of(new KubernetesEngineFactory(), "$628.80", "$1,740.39")
    );
  }

  @ParameterizedTest
  @MethodSource("calculatorFactoryProviderDefaultCost")
  public void verifyComputeEnginePriceUpdatesBasedOnNumberOfInstances(CalculatorFactory factory, String expectedDefaultCost, String expectedUpdatedCost, Page page){
    Calculator calculator = factory.createCalculator();
    calculator.openCalculator(page);

    CalculatorPage calculatorPage = new CalculatorPage(page,calculator.createServicePage(page));
    //set 1 number
    calculatorPage.verifyCostUpdatedPopupAppears();

    String defaultCostText = calculatorPage.readCostText();
    double defaultCost = calculatorPage.readCostValue();
    assertThat(defaultCostText).isEqualTo(expectedDefaultCost);
    calculatorPage.verifyCostUpdatedPopupDisappears();

    calculatorPage.increaseServiceInstances(2);
    calculatorPage.verifyCostUpdatedPopupAppears();
    calculatorPage.verifyCostUpdatedPopupDisappears();

    String updatedCostText = calculatorPage.readCostText();
    double updatedCost = calculatorPage.readCostValue();
    assertThat(updatedCostText).isEqualTo(expectedUpdatedCost);
 //   Assertions.assertThat(updatedCost).isCloseTo(defaultCost * 3, Assertions.within(0.01));
  }


  static Stream<Arguments> calculatorFactoryProviderAdvancedSettings() {
    return Stream.of(
        Arguments.of(new ComputeEngineFactory(), "$69.98", "$209.95"),
        Arguments.of(new KubernetesEngineFactory(), "$2,851.99", "$3,963.58")
    );
  }

  @ParameterizedTest
  @MethodSource("calculatorFactoryProviderAdvancedSettings")
  public void verifyInstancesConfigurationsAppearAfterEnablingAdvancedSettings (CalculatorFactory factory, String expectedDefaultCost, String expectedUpdatedCost, Page page) {
    Calculator calculator = factory.createCalculator();
    calculator.openCalculator(page);

    CalculatorPage calculatorPage = new CalculatorPage(page,calculator.createServicePage(page));
    calculatorPage.verifyCostUpdatedPopupAppears();

    calculatorPage.verifyServiceAdvancesSettingsOptionsAreHidden();
    calculatorPage.enableServiceAdvancedSettings();

    calculatorPage.verifyAdvancedSettingsPopupAppears();
    calculatorPage.verifyServiceAdvancesSettingsOptionsAreVisible();
    calculatorPage.verifyAdvancedSettingsPopupDisappears();
  }

  static Stream<Arguments> calculatorFactoryProviderMachineType() {
    return Stream.of(
        Arguments.of(new ComputeEngineFactory(), MachineType.M2_ULTRAMEM_208, "n4-standard-2", "vCPUs: 2, RAM: 8 GiB", "vCPUs: 208, RAM: 5888 GiB", "$69.98", "$30,742.51"),
        Arguments.of(new KubernetesEngineFactory(), MachineType.M2_ULTRAMEM_208, "n1-standard-16", "vCPUs: 16, RAM: 60 GiB", "vCPUs: 208, RAM: 5888 GiB", "$2,851.99", "$153,786.57")
    );
  }

  @ParameterizedTest
  @MethodSource("calculatorFactoryProviderMachineType")
  public void verifyMachineTypeAndPriceUpdatedAccordingToSelection (CalculatorFactory factory, MachineType selectedType, String defaultMachineType, String defaultMachineFeatures, String selectedMachineFeatures, String expectedDefaultCost, String expectedUpdatedCost, Page page) {
    Calculator calculator = factory.createCalculator();
    calculator.openCalculator(page);

    CalculatorPage calculatorPage = new CalculatorPage(page, calculator.createServicePage(page));
    calculatorPage.verifyCostUpdatedPopupAppears();

    String defaultCostText = calculatorPage.readCostText();
    assertThat(defaultCostText).isEqualTo(expectedDefaultCost);
    calculatorPage.verifyCostUpdatedPopupAppears();

    String defaultMachineTypeSummaryBlock = calculatorPage.readMachineTypeSummaryBlockText();
    assertThat(defaultMachineTypeSummaryBlock).contains(defaultMachineType, defaultMachineFeatures);

    calculatorPage.selectMachineConfiguration(selectedType);
    calculatorPage.verifyCostUpdatedPopupAppears();

    String updatedCostText = calculatorPage.readCostText();
    assertThat(updatedCostText).isEqualTo(expectedUpdatedCost);

    String updatedMachineTypeSummaryBlock = calculatorPage.readMachineTypeSummaryBlockText();
    assertThat(updatedMachineTypeSummaryBlock).contains(selectedType.getTypeName(), selectedMachineFeatures);
  }

  static Stream<Arguments> calculatorFactoryProviderSelectedRegion() {
    return Stream.of(
        Arguments.of(new ComputeEngineFactory(), Region.IOWA.getRegionName(), Region.LONDON.getRegionName(), "$69.98", "$79.78"),
        Arguments.of(new KubernetesEngineFactory(), Region.IOWA.getRegionName(), Region.LONDON.getRegionName(), "$2,851.99", "$3,652.93")
    );
  }

  @ParameterizedTest
  @MethodSource("calculatorFactoryProviderSelectedRegion")
  public void verifyPriceUpdatedBasedOnSelectedRegion (CalculatorFactory factory, String expectedDefaultRegion, String expectedSelectedRegion, String expectedDefaultCost, String expectedUpdatedCost, Page page) {
    Calculator calculator = factory.createCalculator();
    calculator.openCalculator(page);

    CalculatorPage calculatorPage = new CalculatorPage(page, calculator.createServicePage(page));
    calculatorPage.verifyCostUpdatedPopupAppears();

    String defaultCostText = calculatorPage.readCostText();
    assertThat(defaultCostText).isEqualTo(expectedDefaultCost);

    String defaultRegion = calculatorPage.readSelectedRegion();
    assertThat(defaultRegion).isEqualTo(expectedDefaultRegion);
    calculatorPage.verifyCostUpdatedPopupDisappears();

    calculatorPage.selectRegion(Region.LONDON);
    calculatorPage.verifyCostUpdatedPopupAppears();

    String selectedRegion = calculatorPage.readSelectedRegion();
    assertThat(selectedRegion).isEqualTo(expectedSelectedRegion);

    String updatedCostText = calculatorPage.readCostText();
    assertThat(updatedCostText).isEqualTo(expectedUpdatedCost);
  }

  static Stream<Arguments> calculatorFactoryProviderCommittedUse() {
    return Stream.of(
        Arguments.of(new ComputeEngineFactory(), CommittedUse.NONE, CommittedUse.THREE_YEARS, "$69.98", "$31.93"),
        Arguments.of(new KubernetesEngineFactory(), CommittedUse.NONE, CommittedUse.THREE_YEARS, "$2,851.99", "$1,326.37")
    );
  }

  @ParameterizedTest
  @MethodSource("calculatorFactoryProviderCommittedUse")
  public void verifyPriceUpdatedBasedOnCommittedUseDiscountOptions (CalculatorFactory factory, CommittedUse defaultTerm, CommittedUse selectedTerm, String expectedDefaultCost, String expectedUpdatedCost, Page page) {
    Calculator calculator = factory.createCalculator();
    calculator.openCalculator(page);

    CalculatorPage calculatorPage = new CalculatorPage(page, calculator.createServicePage(page));
    calculatorPage.verifyCostUpdatedPopupAppears();

    String defaultCostText = calculatorPage.readCostText();
    assertThat(defaultCostText).isEqualTo(expectedDefaultCost);

    String defaultCommittedUseOption = calculatorPage.readSelectedCommittedUseOption();
    assertThat(defaultCommittedUseOption).isEqualTo(defaultTerm.getValue());
    calculatorPage.verifyCostUpdatedPopupDisappears();

    calculatorPage.selectCommittedUseOption(selectedTerm);
    calculatorPage.verifyCostUpdatedPopupAppears();

    String selectedCommittedUseOption = calculatorPage.readSelectedCommittedUseOption();
    assertThat(selectedCommittedUseOption).isEqualTo(selectedTerm.getValue());

    String updatedCostText = calculatorPage.readCostText();
    assertThat(updatedCostText).isEqualTo(expectedUpdatedCost);
  }

  static Stream<Arguments> calculatorFactoryProviderCurrency() {
    return Stream.of(
        Arguments.of(new ComputeEngineFactory(), Currency.EUR, "$69.98", "€61.58"),
        Arguments.of(new KubernetesEngineFactory(), Currency.EUR, "$2,851.99", "€2,509.46")
    );
  }

  @ParameterizedTest
  @MethodSource("calculatorFactoryProviderCurrency")
  public void verifyPriceUpdatedBasedOnSelectedCurrency (CalculatorFactory factory, Currency selectedCurrency, String expectedDefaultCost, String expectedUpdatedCost, Page page) {
    Calculator calculator = factory.createCalculator();
    calculator.openCalculator(page);

    CalculatorPage calculatorPage = new CalculatorPage(page, calculator.createServicePage(page));
    calculatorPage.verifyCostUpdatedPopupAppears();

    assertThat(calculatorPage.readSelectedCurrency()).isEqualTo(Currency.USD);

    String defaultCostText = calculatorPage.readCostText();
    assertThat(defaultCostText).isEqualTo(expectedDefaultCost);

    calculatorPage.selectCurrency(selectedCurrency);
    assertThat(calculatorPage.readSelectedCurrency()).isEqualTo(selectedCurrency);
    calculatorPage.verifyCostUpdatedPopupAppears();

    String updatedCostText = calculatorPage.readCostText();
    assertThat(updatedCostText).isEqualTo(expectedUpdatedCost);
  }

}
