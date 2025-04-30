package org.testautomation.playwright.enums;

import lombok.Getter;

@Getter
public enum MachineFamily {
  GENERAL_PURPOSE("General purpose"),
  COMPUTE_OPTIMIZED("Compute-optimized"),
  MEMORY_OPTIMIZED("Memory-optimized"),
  ACCELERATOR_OPTIMIZED("Accelerator-optimized"),
  STORAGE_OPTIMIZED("Storage-optimized");

  private final String familyName;

  MachineFamily(String familyName) {
    this.familyName = familyName;
  }

}
