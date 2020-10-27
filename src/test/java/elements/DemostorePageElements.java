package elements;

import utils.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class DemostorePageElements extends SeleniumUtils {

  public static WebDriver driver;

  @FindBy(id="search_input")
  protected WebElement SEARCHBAR;

  @FindBy(id="products_search_11")
  protected WebElement GRID;

  @FindBy(xpath=".//*[contains(@class,'grid-list')]//*[contains(@class,'ty-column3')]")
  protected List<WebElement> allRows;

  @FindBy(id="button_cart_280")
  protected WebElement ADD_TO_CART;

  @FindBy(id="bp_bottom_panel")
  protected WebElement BOTTOM_PANEL;

  @FindBy(id="sec_discounted_price_280")
  protected WebElement PRICE;

  @FindBy(css="#sw_dropdown_8 > a > i")
  protected WebElement CHART_BUTTON;

  @FindBy(xpath = "//*[@class='modal bootstrap-dialog type-primary size-normal fade in']")
  public WebElement MODAL_BACKDROP;
}
