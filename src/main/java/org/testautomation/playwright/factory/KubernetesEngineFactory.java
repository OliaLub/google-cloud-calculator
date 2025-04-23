package org.testautomation.playwright.factory;

import org.testautomation.playwright.calculator.Calculator;
import org.testautomation.playwright.calculator.KubernetesEngineCalculator;

public class KubernetesEngineFactory extends CalculatorFactory {

  public KubernetesEngineFactory() {}

  @Override
  public Calculator createCalculator() {
    return new KubernetesEngineCalculator();
  }

}
