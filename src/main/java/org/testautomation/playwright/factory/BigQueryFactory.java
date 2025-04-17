package org.testautomation.playwright.factory;

import org.testautomation.playwright.calculator.BigQueryCalculator;
import org.testautomation.playwright.calculator.Calculator;

public class BigQueryFactory extends CalculatorFactory{

  protected BigQueryFactory() {}

  @Override
  public Calculator createCalculator() {
    return new BigQueryCalculator();
  }
}
