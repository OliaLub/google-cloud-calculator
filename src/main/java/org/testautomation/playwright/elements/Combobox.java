package org.testautomation.playwright.elements;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.GetByRoleOptions;
import com.microsoft.playwright.options.AriaRole;
import lombok.Getter;

@Getter
public class Combobox {

  private final Page page;
  private final Locator combobox;

  public Combobox(Page page, String comboboxName) {
    this.page = page;
    this.combobox = page.getByRole(AriaRole.COMBOBOX, new GetByRoleOptions().setName(comboboxName));
  }

  public void selectOption(String optionName) {
    combobox.click();
    Option option = new Option(optionName);
    option.select();
    assertThat(combobox).containsText(optionName);
  }

  public void verifyOptionIsSelected(String optionName) {
    assertThat(combobox).containsText(optionName);
  }

  public class Option {

    private final Locator optionLocator;

    public Option(String name) {
      this.optionLocator = page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(name));
    }

    public void select() {
      optionLocator.waitFor();
      optionLocator.click();
    }
  }

}
