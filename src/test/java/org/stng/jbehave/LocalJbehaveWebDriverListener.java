package org.stng.jbehave;

import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import organized.chaos.LocalDriverFactory;
import organized.chaos.LocalDriverManager;
import org.apache.log4j.Logger;


public class LocalJbehaveWebDriverListener implements IInvokedMethodListener {

    static Logger log = Logger.getLogger(LocalJbehaveWebDriverListener.class);

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            log.info("BEFORE GETTING THE DRIVER NAME: " + method.getTestMethod());
            String browserName = method.getTestMethod().getXmlTest().getLocalParameters().get("browserName");
            log.info("CREATING an instance of: " + browserName + " driver!");
            WebDriver driver = LocalDriverFactory.createInstance(browserName);
            LocalDriverManager.setWebDriver(driver);
            //LocalDriverManager.getDriver().get("http://google.pl");
            log.info("After instantiating the driver: " + LocalDriverManager.getDriver().getClass().toString());
        } else {
            log.warn("beforeInvocation(): METHOD is NOT a testMethod!!!");
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            WebDriver driver = LocalDriverManager.getDriver();
            if (driver != null) {
                driver.quit();
            }
        } else {
            log.warn("afterInvocation(): METHOD is NOT a testMethod!!!");
        }
    }
}