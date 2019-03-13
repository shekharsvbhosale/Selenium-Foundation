package com.nordstrom.automation.selenium.plugins;

import java.util.Map;

import com.nordstrom.automation.selenium.DriverPlugin;
import com.nordstrom.automation.selenium.SeleniumConfig;

public class InternetExplorerPlugin implements DriverPlugin {
    
    /**
     * <a href='https://www.microsoft.com/en-au/download/confirmation.aspx?id=44069'>IE WebDriver Tools</a>
     * <p>
     * <b>org.openqa.selenium.ie.InternetExplorerDriver</b>
     * 
     * <pre>&lt;dependency&gt;
     *  &lt;groupId&gt;org.seleniumhq.selenium&lt;/groupId&gt;
     *  &lt;artifactId&gt;selenium-ie-driver&lt;/artifactId&gt;
     *  &lt;version&gt;2.53.1&lt;/version&gt;
     *&lt;/dependency&gt;</pre>
     */
    private static final String[] DEPENDENCY_CONTEXTS = {
                    "org.openqa.selenium.ie.InternetExplorerDriver",
                    "org.apache.commons.exec.Executor",
                    "org.openqa.selenium.os.Kernel32",
                    "com.sun.jna.platform.win32.Kernel32",
                    "com.sun.jna.win32.StdCallLibrary"};
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getDependencyContexts() {
        return DEPENDENCY_CONTEXTS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCapabilities(SeleniumConfig config) {
        return InternetExplorerCaps.getCapabilities();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> getPersonalities() {
        return InternetExplorerCaps.getPersonalities();
    }

}
