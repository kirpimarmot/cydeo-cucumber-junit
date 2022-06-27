package com.cydeo.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class Driver {

    /**
     * Creating a privet constructor, so we are closing
     access to the object of this class from outside the class
     */
    private Driver(){}

    /**
     * We make driver private because we want to close access from outside the class.
     * We make it static because we will use it in a static method
     */
    //private static WebDriver driver; //the value is null by default

    private static InheritableThreadLocal<WebDriver> driverPool = new InheritableThreadLocal<>();

    /**
     * Create a re-usable utility method which will return instance when we call it
     */

    public static WebDriver getDriver(){

        if (driverPool.get() == null){

            /**
             * We read our browserType from configuration.properties.
             This way we can control which browser is opened from outside our code, from configuration.properties.
             */

            String browserType = ConfigurationReader.getProperty("browser");

            /**
             * Depending on the browserType that will be returned from configuration.properties file
             switch statement will determine the case and open the matching browser
             */
            switch (browserType){
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driverPool.set(new ChromeDriver());
                    driverPool.get().manage().window().maximize();
                    driverPool.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driverPool.set(new FirefoxDriver());
                    driverPool.get().manage().window().maximize();
                    driverPool.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                    break;
            }
        }

        return driverPool.get();
    }

    //driver.quit() --> nosuchsession
    //driver.close() -->
    //try to create a method named closeDriver

    /**
     * this method will make sur our driver value is always null after using quit() method
     */
    public static void closeDriver(){
        if (driverPool.get() != null){
            driverPool.get().quit(); // this line will terminate existing session.The Value will not even be null
            driverPool.remove();
        }
    }

}
