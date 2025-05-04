package org.testautomation.playwright.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Locator.GetByTextOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.testautomation.playwright.enums.CommittedUse;

public class CommittedUseComponent {

  protected final Locator committedUseRadioButtonBlock;

  public CommittedUseComponent(Page page) {
    this.committedUseRadioButtonBlock = page.getByText("Committed use discount options").locator("xpath=ancestor::span[@data-is-tooltip-wrapper=\"true\"]").locator("xpath=ancestor::div[@data-field-input-type]");
  }

  public void selectOption(CommittedUse term) {
    committedUseRadioButtonBlock.getByText(term.getTerm(), new GetByTextOptions().setExact(true)).click();
  }

  public String getSelectedOption() {
    Locator radios = committedUseRadioButtonBlock.getByRole(AriaRole.RADIO);
    int count = radios.count();

    for (int i = 0; i < count; i++) {
      Locator radio = radios.nth(i);
      if (radio.isChecked()) {
        return radio.getAttribute("value");
      }
    }
    return null;
  }

}
