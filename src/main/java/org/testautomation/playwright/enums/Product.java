package org.testautomation.playwright.enums;

import lombok.Getter;

@Getter
public enum Product {
  COMPUTE_ENGINE("Compute Engine"),
  GOOGLE_KUBERNETES_ENGINE("Kubernetes Engine"),
  BIG_QUERY("BigQuery"),
  CLOUD_STORAGE("Cloud Storage");

  private final String productName;

  Product(String productName) {
    this.productName = productName;
  }

}
