package org.testautomation.playwright.enums;

import lombok.Getter;

@Getter
public enum Currency {
  USD("United States Dollar (USD)", "$"),
  AUD("Australian Dollar (AUD)", "$"),
  EUR("Euro (EUR)","€"),
  GBP("Great Britain Pound (GBP)", "£"),
  JPY("Japanese Yen (JPY)", "¥"),
  MXN("Mexican Peso (MXN)", "$");

  private final String currencyName;
  private final String currencySymbol;

  Currency(String currencyName, String currencySymbol) {
    this.currencyName = currencyName;
    this.currencySymbol = currencySymbol;
  }

}
