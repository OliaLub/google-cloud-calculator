package org.testautomation.playwright.enums;

import lombok.Getter;

@Getter
public enum MachineSeries {
  N1("N1", MachineFamily.GENERAL_PURPOSE),
  N2("N2", MachineFamily.GENERAL_PURPOSE),
  N4("N4", MachineFamily.GENERAL_PURPOSE),
  C2("C2", MachineFamily.COMPUTE_OPTIMIZED),
  C2D("C2D", MachineFamily.COMPUTE_OPTIMIZED),
  M1("M1", MachineFamily.MEMORY_OPTIMIZED),
  M2("M2", MachineFamily.MEMORY_OPTIMIZED),
  M3("M3", MachineFamily.MEMORY_OPTIMIZED),
  A2("A2", MachineFamily.ACCELERATOR_OPTIMIZED),
  A3("A3", MachineFamily.ACCELERATOR_OPTIMIZED),
  Z3("Z3", MachineFamily.STORAGE_OPTIMIZED);

  private final String seriesName;
  private final MachineFamily family;

  MachineSeries(String seriesName, MachineFamily family) {
    this.seriesName = seriesName;
    this.family = family;
  }

}
