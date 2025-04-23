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
import org.testautomation.playwright.page.EstimatePage;

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

    EstimatePage estimatePage = new EstimatePage(page,calculator.createServicePage(page));
    //set 1 number
    estimatePage.costUpdatedPopupAppears();

    String defaultCostText = estimatePage.readCostText();
    double defaultCost = estimatePage.readCostValue();
    Assertions.assertThat(defaultCostText).isEqualTo(expectedDefaultCost);
    estimatePage.costUpdatedPopupDisappears();

    estimatePage.increaseServiceInstances(2);
    estimatePage.costUpdatedPopupAppears();
    estimatePage.costUpdatedPopupDisappears();

    String updatedCostText = estimatePage.readCostText();
    double updatedCost = estimatePage.readCostValue();
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

    EstimatePage estimatePage = new EstimatePage(page,calculator.createServicePage(page));
    estimatePage.costUpdatedPopupAppears();

    estimatePage.serviceAdvancesSettingsOptionsAreHidden();
    estimatePage.enableServiceAdvancedSettings();

    estimatePage.advancedSettingsPopupAppears();
    estimatePage.serviceAdvancesSettingsOptionsAreVisible();
    estimatePage.advancedSettingsPopupDisappears();
  }

}
