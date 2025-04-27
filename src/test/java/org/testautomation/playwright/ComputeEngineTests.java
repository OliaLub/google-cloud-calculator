package org.testautomation.playwright;

import com.microsoft.playwright.Page;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testautomation.playwright.calculator.Calculator;
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
    Assertions.assertThat(defaultCostText).isEqualTo(expectedDefaultCost);
    calculatorPage.verifyCostUpdatedPopupDisappears();

    calculatorPage.increaseServiceInstances(2);
    calculatorPage.verifyCostUpdatedPopupAppears();
    calculatorPage.verifyCostUpdatedPopupDisappears();

    String updatedCostText = calculatorPage.readCostText();
    double updatedCost = calculatorPage.readCostValue();
    Assertions.assertThat(updatedCostText).isEqualTo(expectedUpdatedCost);
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
        Arguments.of(new ComputeEngineFactory(), "n4-standard-2", "vCPUs: 2, RAM: 8 GiB","$69.98", "$30,742.51"),
        Arguments.of(new KubernetesEngineFactory(), "n1-standard-16", "vCPUs: 16, RAM: 60 GiB", "$2,851.99", "$153,786.57")
    );
  }

  @ParameterizedTest
  @MethodSource("calculatorFactoryProviderMachineType")
  public void verifyMachineTypeAndPriceUpdatedAccordingToSelection (CalculatorFactory factory, String defaultMachineType, String defaultMachineFeatures, String expectedDefaultCost, String expectedUpdatedCost, Page page) {
    Calculator calculator = factory.createCalculator();
    calculator.openCalculator(page);

    CalculatorPage calculatorPage = new CalculatorPage(page, calculator.createServicePage(page));
    calculatorPage.verifyCostUpdatedPopupAppears();

    String defaultCostText = calculatorPage.readCostText();
    Assertions.assertThat(defaultCostText).isEqualTo(expectedDefaultCost);
    calculatorPage.verifyCostUpdatedPopupAppears();

    String defaultMachineTypeSummaryBlock = calculatorPage.readMachineTypeSummaryBlockText();
    Assertions.assertThat(defaultMachineTypeSummaryBlock).contains(defaultMachineType, defaultMachineFeatures);

    calculatorPage.selectMachineFamily(page, "Memory-optimized"); //ENUM
    calculatorPage.selectMachineSeries(page, "M2");
    calculatorPage.selectMachineType(page, "m2-ultramem-208");
    calculatorPage.verifyCostUpdatedPopupAppears();

    String updatedCostText = calculatorPage.readCostText();
    Assertions.assertThat(updatedCostText).isEqualTo(expectedUpdatedCost);

    String updatedMachineTypeSummaryBlock = calculatorPage.readMachineTypeSummaryBlockText();
    Assertions.assertThat(updatedMachineTypeSummaryBlock).contains("m2-ultramem-208", "vCPUs: 208, RAM: 5888 GiB");
  }

}
