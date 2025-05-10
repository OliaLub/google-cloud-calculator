package org.testautomation.playwright.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import java.util.regex.Pattern;
import org.testautomation.playwright.enums.Product;

public class AddToEstimateModal {

  private final Locator title;
  private final Locator productTitle;

  public AddToEstimateModal(Page page) {
    this.title = page.getByText("Add to this estimate");
    this.productTitle = page.locator("h2");
  }

  public ServiceConfigurationComponent selectProduct(Product product, Page page) {
    title.waitFor();
    productTitle.filter(new Locator.FilterOptions().setHasText(Pattern.compile(product.getProductName() + "$"))).first().click();
    return new ServiceConfigurationComponent(page);
  }

}
