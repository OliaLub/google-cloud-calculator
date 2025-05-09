package org.testautomation.playwright.enums;

import lombok.Getter;

@Getter
public enum Currency {
  USD("United States Dollar (USD)"),
  AUD("Australian Dollar (AUD)"),
  EUR("Euro (EUR)"),
  GBP("Great Britain Pound (GBP)"),
  JPY("Japanese Yen (JPY)"),
  MXN("Mexican Peso (MXN)");

  private final String currencyName;

  Currency(String currencyName) {
    this.currencyName = currencyName;
  }

}
