package org.testautomation.playwright.enums;

import lombok.Getter;

@Getter
public enum CommittedUse {
  NONE("None", "none"),
  ONE_YEAR("1 year", "1-year"),
  THREE_YEARS("3 years", "3-years");

  private final String term;
  private final String value;

  CommittedUse(String term, String value) {
    this.term = term;
    this.value = value;
  }

}
