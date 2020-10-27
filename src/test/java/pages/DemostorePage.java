package pages;

import com.aventstack.extentreports.Status;
import elements.DemostorePageElements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import setup.TestRule;
import utils.Log4jUtils;
import utils.ReportUtils;
import utils.SeleniumUtils;

import java.util.*;

public class DemostorePage extends DemostorePageElements {

    public DemostorePage() {
        driver = TestRule.getDriver();
        PageFactory.initElements(TestRule.getDriver(), this);
    }

    public void accessDemostore() {
        ReportUtils.reportMessage(Status.INFO, "Accessing demostore.");
        String systemUrl;
        systemUrl = loadFromPropertiesFile("dell.properties",
                "DEMOSTORE_PAGE");

        driver.manage().deleteAllCookies();
        driver.navigate().to(systemUrl);
    }

    public void searchItem(String item) {
        ReportUtils.reportMessage(Status.INFO, "searching for " + item + ".");
        Log4jUtils.logMessage("INFO", "searching for " + item + ".");
        try {
            waitVisibleElement(SEARCHBAR, 40);
            setInputText(item, SEARCHBAR);
        } catch (Exception e) {
            ReportUtils.reportMessage(Status.FAIL, "Error on field searchbar.",
                    SeleniumUtils.getScreenshotReport());
            Log4jUtils.logMessage("ERROR", "Error on field searchbar");
            e.printStackTrace();
        }
    }

    public void findItemOnGrid(String item) {
        ReportUtils.reportMessage(Status.INFO, "finding " + item + " on grid.");
        Log4jUtils.logMessage("INFO", "finding " + item + " on grid.");
        try {
            waitVisibleElement(GRID, 20);
            for (WebElement row : allRows) {
                By productRow = By.xpath(".//*[@class='product-title']");
                String productTitle = row.findElement(productRow).getText();
                List array = Arrays.asList(productTitle.toLowerCase().replaceAll(",", "").split(" "));
                List items = Arrays.asList(item.toLowerCase().split(" "));
                if (array.get(0).equals(items.get(1))) {
                    clickField(row);
                    break;
                }
            }
        } catch (Exception e) {
            ReportUtils.reportMessage(Status.FAIL, "Error finding " + item + " on grid.",
                    SeleniumUtils.getScreenshotReport());
            Log4jUtils.logMessage("ERROR", "Error finding " + item + " on grid.");
            e.printStackTrace();
        }
    }

    public void addItemToCart() {
        ReportUtils.reportMessage(Status.INFO, "adding item to cart.");
        Log4jUtils.logMessage("INFO", "adding item to cart.");
        try {
            waitVisibleElement(ADD_TO_CART, 20);
            scrollTo(ADD_TO_CART);
            clickField(BOTTOM_PANEL);
            clickField(ADD_TO_CART);

        } catch (Exception e) {
            ReportUtils.reportMessage(Status.FAIL, "Error adding item to cart.",
                    SeleniumUtils.getScreenshotReport());
            Log4jUtils.logMessage("ERROR", "Error adding item to cart");
            e.printStackTrace();
        }
    }

    public String getPrice() {
        return PRICE.getText();
    }

    public void accessChart() {
        ReportUtils.reportMessage(Status.INFO, "entering chart screen.");
        Log4jUtils.logMessage("INFO", "entering chart screen.");
        try {
            waitElementBeClickable(CHART_BUTTON, 10);
            scrollTo(CHART_BUTTON);
            clickField(CHART_BUTTON);
            clickField(ADD_TO_CART);

        } catch (Exception e) {
            ReportUtils.reportMessage(Status.FAIL, "Error entering chart screen.",
                    SeleniumUtils.getScreenshotReport());
            Log4jUtils.logMessage("ERROR", "Error entering chart screen.");
            e.printStackTrace();
        }
    }

    public boolean checkChart(String itemExpected, String itemOnChart) {
        ReportUtils.reportMessage(Status.INFO, "item expected: " + itemExpected);
        ReportUtils.reportMessage(Status.INFO, "item shown: " + itemOnChart);
        Log4jUtils.logMessage("INFO", "item expected: " + itemExpected);
        Log4jUtils.logMessage("INFO", "item shown: " + itemOnChart);
        return Objects.equals(itemOnChart, itemExpected);
    }

    public boolean checkPrice(String priceExpected, String priceOnChart) {
        ReportUtils.reportMessage(Status.INFO, "value expected: " + priceExpected);
        ReportUtils.reportMessage(Status.INFO, "value shown: " + priceOnChart);
        Log4jUtils.logMessage("INFO", "value expected: " + priceExpected);
        Log4jUtils.logMessage("INFO", "value shown: " + priceOnChart);
        return Objects.equals(priceExpected, priceOnChart);
    }
}
