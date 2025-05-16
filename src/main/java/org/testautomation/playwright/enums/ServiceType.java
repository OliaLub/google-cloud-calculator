package org.testautomation.playwright.enums;

import lombok.Getter;

@Getter
public enum ServiceType {
  INSTANCES("Instances", Product.COMPUTE_ENGINE, "$69.98"),
  SOLE_TENANT_NODES("Sole-Tenant Nodes", Product.COMPUTE_ENGINE, "$4,559.87"),
  MACHINE_IMAGES("Machine Images", Product.COMPUTE_ENGINE, "$5.00"),

  GKE("GKE", Product.GOOGLE_KUBERNETES_ENGINE, "$2,851.99"),
  BACKUP_FOR_GKE("Backup for GKE", Product.GOOGLE_KUBERNETES_ENGINE, "$15.01"),

  EDITIONS("Editions", Product.BIG_QUERY, "$6,164.81"),
  ON_DEMAND("On-Demand", Product.BIG_QUERY, "$527.06"),
  BIGQUERY_ML("BigQuery ML", Product.BIG_QUERY, "$1,281.25"),
  BI_ENGINE("BI Engine", Product.BIG_QUERY, "$3,036.80");

  private final String serviceName;
  private final Product product;
  private final String defaultCost;

  ServiceType(String serviceName, Product product, String defaultCost) {
    this.serviceName = serviceName;
    this.product = product;
    this.defaultCost = defaultCost;
  }

}
