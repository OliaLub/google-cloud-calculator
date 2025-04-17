package org.testautomation.playwright.factory;

import org.testautomation.playwright.calculator.Calculator;
import org.testautomation.playwright.calculator.ComputeEngineCalculator;

public class ComputeEngineFactory extends CalculatorFactory{

  public ComputeEngineFactory() {}

  @Override
  public Calculator createCalculator() {
    return new ComputeEngineCalculator();
  }
}
