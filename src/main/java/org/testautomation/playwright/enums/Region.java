package org.testautomation.playwright.enums;

import lombok.Getter;

@Getter
public enum Region {
  IOWA("Iowa (us-central1)"),
  LONDON("London (europe-west2)"),
  LOS_ANGELES("Los Angeles (us-west2)"),
  TAIWAN("Taiwan (asia-east1)"),
  TOKYO("Tokyo (asia-northeast1)"),
  FRANKFURT("Frankfurt (europe-west3)");

  private final String regionName;

  Region(String regionName) {
    this.regionName = regionName;
  }

}
