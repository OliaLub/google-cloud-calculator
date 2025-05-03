package org.testautomation.playwright.enums;

import lombok.Getter;

@Getter
public enum MachineType {
  C2_STANDARD_4("c2-standard-4", MachineSeries.C2),
  C2_STANDARD_8("c2-standard-8", MachineSeries.C2),
  C2_STANDARD_16("c2-standard-16", MachineSeries.C2),
  M1_ULTRAMEM_40("m1-ultramem-40", MachineSeries.M1),
  M1_ULTRAMEM_80("m1-ultramem-80", MachineSeries.M1),
  M1_ULTRAMEM_160("m1-ultramem-160", MachineSeries.M1),
  M2_MEGAMEM_416("m2-megamem-416", MachineSeries.M2),
  M2_ULTRAMEM_208("m2-ultramem-208", MachineSeries.M2),
  M2_ULTRAMEM_416("m2-ultramem-416", MachineSeries.M2);

  private final String typeName;
  private final MachineSeries series;

  MachineType(String typeName, MachineSeries series) {
    this.typeName = typeName;
    this.series = series;
  }

  public MachineFamily getFamily() {
    return series.getFamily();
  }

}
