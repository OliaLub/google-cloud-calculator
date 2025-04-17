package org.testautomation.playwright.factory;

import org.testautomation.playwright.calculator.Calculator;

public abstract class CalculatorFactory {

  protected CalculatorFactory() {}

  public abstract Calculator createCalculator();
}
