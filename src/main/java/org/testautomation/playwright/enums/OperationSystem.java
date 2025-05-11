package org.testautomation.playwright.enums;

import lombok.Getter;

@Getter
public enum OperationSystem {
  FREE("Free: Debian, CentOS, CoreOS, Ubuntu or BYOL (Bring Your Own License)", ServiceType.INSTANCES),
  PAID_UBUNTU_PRO("Paid: Ubuntu Pro", ServiceType.INSTANCES),
  PAID_RED_HAT_LINUX_7("Paid: Red Hat Enterprise Linux 7 with Extended Life Cycle Support Add-On", ServiceType.INSTANCES),

  FREE_CONTAINER_OPTIMIZED("Free: Container-optimized", ServiceType.GKE),
  FREE_UBUNTU("Free: Ubuntu", ServiceType.GKE),
  PAID_WINDOWS_SERVER("Paid: Windows Server", ServiceType.GKE);

  private final String osName;
  private final ServiceType serviceType;

  OperationSystem(String osName, ServiceType serviceType) {
    this.osName = osName;
    this.serviceType = serviceType;
  }

}
