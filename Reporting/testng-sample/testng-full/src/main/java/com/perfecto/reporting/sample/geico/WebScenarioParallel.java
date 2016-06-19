package com.perfecto.reporting.sample.geico;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.exception.ReportiumException;
import com.perfecto.reportium.model.Job;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class WebScenarioParallel {
    RemoteWebDriver driver;

    // Create Remote WebDriver based on testng.xml configuration
    protected ReportiumClient reportiumClient;

    private String platformName;
    private String platformVersion;
    private String browserName;
    private String browserVersion;
    private String screenResolution;
    private String location;
    private String deviceType;

    // Create Remote WebDriver based on testng.xml configuration
    @Parameters({"platformName", "platformVersion", "browserName", "browserVersion", "screenResolution", "location", "deviceType"})
    @BeforeClass
    public void baseBeforeClass(String platformName, String platformVersion, String browserName, String browserVersion, String screenResolution, String location, String deviceType) throws MalformedURLException {
        driver = Utils.getRemoteWebDriver(platformName, platformVersion, browserName, browserVersion, screenResolution);
        this.platformName = platformName;
        this.platformVersion = platformVersion;
        this.browserName = browserName;
        this.browserVersion = browserVersion;
        this.screenResolution = screenResolution;
        this.location = location;
        this.deviceType = deviceType;
        reportiumClient = createReportium(driver);
    }

    @AfterClass
    public void baseAfterClass() {
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeMethod
    public void beforeTest(Method method) {
        String testName = method.getDeclaringClass().getName() + "::" + method.getName();
        reportiumClient.testStart(testName, new TestContext());
    }

    @SuppressWarnings("Duplicates")
    @AfterMethod
    public void afterTest(ITestResult testResult) {
        int status = testResult.getStatus();
        switch (status) {
            case ITestResult.FAILURE:
                reportiumClient.testStop(TestResultFactory.createFailure("An error occurred", testResult.getThrowable()));
                break;
            case ITestResult.SUCCESS_PERCENTAGE_FAILURE:
            case ITestResult.SUCCESS:
                reportiumClient.testStop(TestResultFactory.createSuccess());
                break;
            case ITestResult.SKIP:
                // Ignore
                break;
            default:
                throw new ReportiumException("Unexpected status " + status);
        }
    }

    private static ReportiumClient createReportium(WebDriver driver) {
        PerfectoExecutionContext perfectoExecutionContext =
                new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
                .withJob(new Job("CI job name from environment variable", 123 /*build number from environment variable */))
                .withWebDriver(driver)
                .build();
        return new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);
    }

    @Test
    public void searchGoogle() throws MalformedURLException {

        reportiumClient.testStart("WevbScanrioParallel", new TestContext());

        reportiumClient.testStep("Navigate to Google webpage");
        driver.get("http://www.google.com");

        reportiumClient.testStep("Searching for Perfecto Mobile");
        final String searchKey = "Perfecto Mobile";
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys(searchKey);
        element.submit();
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith(searchKey.toLowerCase());
            }
        });

        System.out.println("Done: searchGoogle");

    }

    // Test Method, navigate to Geico and get insurance quote
    @Test
    public void geicoInsurance() throws MalformedURLException {

        reportiumClient.testStart("navigate to Geico and get insurance quote", new TestContext());

        reportiumClient.testStep("Navigate to Geico webpage");
        driver.get("http://www.geico.com");
        reportiumClient.testStep("Select Insurance type");
        Select type = new Select(driver.findElement(By.id("insurancetype")));

        reportiumClient.testStep("Select Motorcycle");
        type.selectByVisibleText("Motorcycle");
        driver.findElement(By.id("zip")).sendKeys("01434");
        driver.findElement(By.id("submitButton")).click();

        reportiumClient.testStep("Select Type Name & Adress");
        driver.findElement(By.id("firstName")).sendKeys("MyFirstName");
        driver.findElement(By.id("lastName")).sendKeys("MyFamilyName");
        driver.findElement(By.id("street")).sendKeys("My Address");

        driver.findElement(By.id("date-monthdob")).sendKeys("8");
        driver.findElement(By.id("date-daydob")).sendKeys("3");
        driver.findElement(By.id("date-yeardob")).sendKeys("1981");

        driver.findElement(By.xpath("//*[@class='radio'][2]")).click();
        driver.findElement(By.id("btnSubmit")).click();

        driver.findElement(By.id("hasCycle-error")).isDisplayed();

        Select hasCycle = new Select(driver.findElement(By.id("hasCycle")));
        hasCycle.selectByIndex(1);

        reportiumClient.testStep("Select Current Insurance");
        Select current = new Select(driver.findElement(By.id("currentInsurance")));
        current.selectByVisibleText("Other");
        driver.findElement(By.id("btnSubmit")).click();

        reportiumClient.testStop(TestResultFactory.createSuccess());

        System.out.println("Done: geicoInsurance");

    }


}
