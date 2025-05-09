package org.testautomation.playwright.enums;

import lombok.Getter;

@Getter
public enum ServiceType {
  INSTANCES("Instances", Product.COMPUTE_ENGINE),
  SOLE_TENANT_NODES("Sole-Tenant Nodes", Product.COMPUTE_ENGINE),
  MACHINE_IMAGES("Machine Images", Product.COMPUTE_ENGINE),

  GKE("GKE", Product.GOOGLE_KUBERNETES_ENGINE),
  BACKUP_FOR_GKE("Backup for GKE", Product.GOOGLE_KUBERNETES_ENGINE),

  EDITIONS("Editions", Product.BIG_QUERY),
  ON_DEMAND("On-Demand", Product.BIG_QUERY),
  BIGQUERY_ML("BigQuery ML", Product.BIG_QUERY),
  BI_ENGINE("BI Engine", Product.BIG_QUERY);

  private final String serviceName;
  private final Product product;

  ServiceType(String serviceName, Product product) {
    this.serviceName = serviceName;
    this.product = product;
  }

}
