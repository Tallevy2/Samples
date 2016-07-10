import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.html5.*;
import org.openqa.selenium.logging.*;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.Cookie.Builder;
import java.net.MalformedURLException;
import com.perfectomobile.selenium.util.EclipseConnector;

public class WebMapsTest {

    public static void main(String[] args) throws MalformedURLException, IOException {
        System.out.println("Run started");

        String browserName = "mobileOS";
        DesiredCapabilities capabilities = new DesiredCapabilities(browserName, "", Platform.ANY);
        String host = "yourLab.perfectomobile.com";
        String user = "yourUser";
        String pswd = "yourPassword";
        capabilities.setCapability("user", user);
        capabilities.setCapability("password", pswd);

        //TODO: Change your device ID
        capabilities.setCapability("platformName", "Windows");
        capabilities.setCapability("platformVersion", "7");
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("browserVersion", "48");
        capabilities.setCapability("resolution", "1366x768");
        capabilities.setCapability("location", "US East");

        setExecutionIdCapability(capabilities, host);

        capabilities.setCapability("scriptName", "WebMapsTest");

        RemoteWebDriver driver = new RemoteWebDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        Map<String, Object> params = new HashMap<>(); 
        driver.manage().window().maximize();

        try {
        	// Open site and verify main page display via direction icon + click (select)
            driver.get("https://www.google.com/maps");
            params.put("content", "PRIVATE:temp media/maps - directions icon.png");
            params.put("timeout", "30");
            Object result = driver.executeScript("mobile:image:select", params);
            
        	// Verify directions pane display            
            params.clear();
            params.put("content", "PRIVATE:temp media/maps - directions page indicator.png");
            params.put("timeout", "30");
            result = driver.executeScript("mobile:image:find", params);
            
            String source = "5 India St, Boston, MA 02109, USA";
            String destination = "5 Austin St, Charlestown, MA 0212";
            //Use objects to set origin and destination
            driver.findElement(By.xpath("//*[@id='sb_ifc51']/input"))
        		.sendKeys(source);
            driver.findElement(By.xpath("//*[@id='sb_ifc52']/input"))
        		.sendKeys(destination);
            
            //Click on find driving directions icon
            params.clear();
            params.put("content", "PRIVATE:temp media/maps - driving directions icon.png");
            params.put("timeout", "10");
            result = driver.executeScript("mobile:image:select", params);

            //Verify pane contains suggested routes from source to destination
            params.clear();
            params.put("content", "Send directions to your phone");
            params.put("timeout", "30");
            result = driver.executeScript("mobile:text:find", params);

            //Select first route
            WebElement firstRoute = 
            		driver.findElement(By.xpath("//div[@class='widget-pane-section-listbox' and @role='listbox']/div[1]"));
            String navigationTime = 
            		firstRoute.findElement(By.xpath("//div[@class='widget-pane-section-directions-trip-duration delay-light'][1]/span[1]"))
            		.getText();
            System.out.println("Driving from " + source + " to " + destination + " takes " + navigationTime + ".");
            String distance = 
            		firstRoute.findElement(By.xpath("//div[ancestor::div[contains(@class,'trip-distance')]]"))
            		.getText();
            System.out.println("This drive is " + distance + "long.");
            String routeTitle = "via " + 
            		firstRoute.findElement(By.xpath("//h1[contains(@class,'trip-title')]/span"))
            		.getText();
            System.out.println("The route goes " + routeTitle);            

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Retrieve the URL of the Wind Tunnel Report, can be saved to your execution summary and used to download the report at a later point
                String reportURL = (String)(driver.getCapabilities().getCapability(WindTunnelUtils.WIND_TUNNEL_REPORT_URL_CAPABILITY));

                driver.close();
                params.clear();
                driver.executeScript("mobile:execution:close", params);

                // In case you want to download the report or the report attachments, do it here.
                PerfectoLabUtils.downloadReport(driver, "pdf", "C:\\test\\google maps - report");
                // PerfectoLabUtils.downloadAttachment(driver, "video", "C:\\test\\report\\video", "flv");
                // PerfectoLabUtils.downloadAttachment(driver, "image", "C:\\test\\report\\images", "jpg");

            } catch (Exception e) {
                e.printStackTrace();
            }

            driver.quit();
        }

        System.out.println("Run ended");
    }

	private static void switchToContext(RemoteWebDriver driver, String context) {
        RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
        Map<String,String> params = new HashMap<String,String>();
        params.put("name", context);
        executeMethod.execute(DriverCommand.SWITCH_TO_CONTEXT, params);
    }

    private static String getCurrentContextHandle(RemoteWebDriver driver) {
        RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
        String context =  (String) executeMethod.execute(DriverCommand.GET_CURRENT_CONTEXT_HANDLE, null);
        return context;
    }

    private static List<String> getContextHandles(RemoteWebDriver driver) {
        RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
        List<String> contexts =  (List<String>) executeMethod.execute(DriverCommand.GET_CONTEXT_HANDLES, null);
        return contexts;
    }

    private static void setExecutionIdCapability(DesiredCapabilities capabilities, String host) throws IOException {
        EclipseConnector connector = new EclipseConnector();
        String eclipseHost = connector.getHost();
        if ((eclipseHost == null) || (eclipseHost.equalsIgnoreCase(host))) {
            String executionId = connector.getExecutionId();
            capabilities.setCapability(EclipseConnector.ECLIPSE_EXECUTION_ID, executionId);
        }
    }
}
