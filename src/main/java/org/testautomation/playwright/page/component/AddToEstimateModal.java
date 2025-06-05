package org.testautomation.playwright.page.component;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import java.util.regex.Pattern;
import org.testautomation.playwright.enums.ServiceType;
import org.testautomation.playwright.service.ServiceConfigurationComponentFactory;
import org.testautomation.playwright.utils.WaiterUtility;

public class AddToEstimateModal extends BaseComponent{

  private final Locator title;
  private final Locator productTitle;

  public AddToEstimateModal(Page page) {
    super(page);
    this.title = page.getByText("Add to this estimate");
    this.productTitle = page.locator("h2");
  }

  public ServiceConfigurationComponent selectProduct(ServiceType serviceType, Page page) {
    logger.info("Selecting product: {}", serviceType.getProduct().getProductName());
    WaiterUtility.waitForElementToBeVisible(title);
    productTitle.filter(new Locator.FilterOptions().setHasText(Pattern.compile(serviceType.getProduct().getProductName() + "$"))).first().click();
    return ServiceConfigurationComponentFactory.createComponent(serviceType, page);
  }

}
