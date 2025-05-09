package org.testautomation.playwright.enums;

import lombok.Getter;

@Getter
public enum MachineType {
  N2_STANDARD_4("n2-standard-4", MachineSeries.N2),
  N4_STANDARD_8("n4-standard-8", MachineSeries.N4),
  C2D_STANDARD_16("c2d-standard-16", MachineSeries.C2D),
  C2D_STANDARD_32("c2d-standard-32", MachineSeries.C2D),
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
