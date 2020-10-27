package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import pages.DemostorePage;
import setup.TestRule;
import com.aventstack.extentreports.Status;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class SeleniumUtils {

    public static WebDriver driver;

    public SeleniumUtils() {
        driver = TestRule.getDriver();
    }

    protected static Boolean verifyElementOnScreen(WebElement element) {
        boolean elementOK = false;
        try {
            if (element.isDisplayed()) {
                elementOK = true;
            }
        } catch (Exception ignored) {
        }

        return elementOK;
    }

    protected static Boolean waitElementBeClickable(WebElement element, long seconds) {
        WebElement webElement = element;
        DemostorePage demostorePage = new DemostorePage();
        waitElementDisappear(demostorePage.MODAL_BACKDROP, 2);
        try {
            Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(seconds))
                    .pollingEvery(Duration.ofSeconds(2)).ignoring(NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class).ignoring(TimeoutException.class);
            try {
                webElement = wait.until(ExpectedConditions.elementToBeClickable(webElement));
            } catch (Exception e) {
                return false;
            }
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
        return webElement != null;
    }

    protected static Boolean waitElementDisappear(WebElement element, long seconds) {
        try {
            Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(seconds))
                    .pollingEvery(Duration.ofSeconds(2)).ignoring(NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class).ignoring(TimeoutException.class);

            ExpectedCondition elementIsDisplayed = (ExpectedCondition<Boolean>) arg0 -> {
                try {
                    return !element.isDisplayed();
                } catch (NoSuchElementException | StaleElementReferenceException e) {
                    return true;
                }
            };
            try {
                wait.until(elementIsDisplayed);
            } catch (Exception e) {
                return false;
            }
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
        return true;
    }

    protected static Boolean waitVisibleElement(WebElement element, long seconds) {
        WebElement webElement;
        DemostorePage demostorePage = new DemostorePage();
        waitElementDisappear(demostorePage.MODAL_BACKDROP, 2);
        try {
            Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(seconds))
                    .pollingEvery(Duration.ofSeconds(2)).ignoring(NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class).ignoring(TimeoutException.class);
            try {
                webElement = wait.until(ExpectedConditions.visibilityOf(element));
            } catch (Exception e) {
                return false;
            }
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
        return webElement != null;
    }

    protected void setInputText(String value, WebElement element) {

        if (waitElementBeClickable(element, 5)) {
            try {
                element.clear();
                element.sendKeys(value);
                element.sendKeys(Keys.ENTER);

            } catch (Exception e) {
                ReportUtils.reportMessage(Status.INFO,
                        String.format("Não foi possível inserir a informação %s no elemento", value));
            }
        }
    }

    protected static void selectOptionByText(String value, WebElement element) {
        if (waitElementBeClickable(element, 5)) {
            Select listboxelementsPesquisa = new Select(element);
            listboxelementsPesquisa.selectByVisibleText(value);
        }
    }

    protected static void scrollTo(WebElement element) {
        String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                + "var elementTop = arguments[0].getBoundingClientRect().top;"
                + "window.scrollBy(0, elementTop-(viewPortHeight/2));";

        ((JavascriptExecutor) driver).executeScript(scrollElementIntoMiddle, element);
    }

    public static void clickField(WebElement elemento){
        elemento.click();
    }

    public static String loadFromPropertiesFile(String propertyFileName, String propertLoad) {
        Properties prop = new Properties();
        InputStream input = null;
        String path;
        if (usingJarFile()) {
            path = "";
        } else {
            path = "src/test/resources/";
        }
        String property = "";

        try {
            input = new FileInputStream(path + propertyFileName);
            // load a properties file
            prop.load(input);

            // get the property value and print it out
            property = prop.getProperty(propertLoad);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return property;
    }

    private static Boolean usingJarFile() {
        return new SeleniumUtils().getRunningJarName() != null;
    }

    private String getRunningJarName() {
        String className = this.getClass().getName().replace('.', '/');
        String classJar =
                this.getClass().getResource("/" + className + ".class").toString();
        if (classJar.startsWith("jar:")) {
            String[] vals = classJar.split("/");
            for (String val : vals) {
                if (val.contains("!")) {
                    return val.substring(0, val.length() - 1);
                }
            }
        }
        return null;
    }

    public static String getScreenshotReport() {
        String dir;
        String image_dir = "";
        driver.getCurrentUrl();
        String namePrint = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        namePrint = namePrint.replace(".", "_");
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            Thread.sleep(1000);
            createDirectory(ReportUtils.getDirectoryReport() + "/screenshots");
            dir = ReportUtils.getDirectoryReport() + "/screenshots/" + namePrint + ".png";
            image_dir = "./screenshots/" + namePrint + ".png";
            copyFileUsingStream(scrFile, new File(dir));
        } catch (Exception e) {
            ReportUtils.reportMessage(Status.FAIL, "Error saving Screenshot - " + e);
            Log4jUtils.logMessage("ERROR", "Error saving Screenshot - " + e);
        }
        return image_dir;
    }

    private static void copyFileUsingStream(File source, File dest) throws IOException {
        try (InputStream is = new FileInputStream(source); OutputStream os = new FileOutputStream(dest)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }
    }

    static void createDirectory(String directoryBeingCreated) {
        try {
            File directory = new File(directoryBeingCreated);
            if (!directory.exists()) {
                directory.mkdirs();
            }
        } catch (Exception e) {
            ReportUtils.reportMessage(Status.FAIL, "" + e.getMessage());
            Log4jUtils.logMessage("ERROR", "" + e.getMessage());
        }
    }

}
