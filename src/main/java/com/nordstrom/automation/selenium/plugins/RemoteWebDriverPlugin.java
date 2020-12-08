package com.nordstrom.automation.selenium.plugins;

import java.io.IOException;
import java.nio.file.Path;
import org.openqa.grid.common.GridRole;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.google.common.collect.ObjectArrays;
import com.nordstrom.automation.selenium.DriverPlugin;
import com.nordstrom.automation.selenium.SeleniumConfig;
import com.nordstrom.automation.selenium.core.LocalSeleniumGrid;
import com.nordstrom.automation.selenium.core.LocalSeleniumGrid.LocalGridServer;
import com.nordstrom.automation.selenium.core.SeleniumGrid.GridServer;

import net.bytebuddy.implementation.Implementation;

/**
 * This class provides the base plugin implementation for drivers that extent {@code RemoteWebDriver}.
 */
public abstract class RemoteWebDriverPlugin implements DriverPlugin {
    
    private String driverName;
    private String[] driverNames;
    
    protected RemoteWebDriverPlugin(String driverName) {
        this.driverName = driverName;
        this.driverNames = new String[] { driverName };
    }
    
    /**
     * Start local Selenium Grid node for this driver.
     * 
     * @param config {@link SeleniumConfig} object
     * @param launcherClassName fully-qualified class name for Grid launcher
     * @param dependencyContexts common dependency contexts for all Grid nodes
     * @param hubServer Grid hub server with which node should register
     * @param workingPath {@link Path} of working directory for server process; {@code null} for default
     * @param outputPath {@link Path} to output log file; {@code null} to decline log-to-file
     * @return {@link LocalGridServer} object for specified node
     * @throws IOException if an I/O error occurs
     */
    @Override
    public LocalGridServer start(SeleniumConfig config, String launcherClassName, String[] dependencyContexts,
            GridServer hubServer, final Path workingPath, final Path outputPath) throws IOException {
        
        String[] combinedContexts = combineDependencyContexts(dependencyContexts, this);
        Path nodeConfigPath = null; //config.createNodeConfig(getCapabilities(config), hubServer.getUrl());
        String[] propertyNames = getPropertyNames();
        return LocalSeleniumGrid.start(launcherClassName, combinedContexts, GridRole.NODE,
                Integer.valueOf(-1), nodeConfigPath, workingPath, outputPath, propertyNames);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Implementation getWebElementCtor(WebDriver driver, Class<? extends WebElement> refClass) {
        return null;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getDriverNames() {
        return driverNames;
    }
    
    protected String getDriverName() {
        return driverName;
    }
    
    /**
     * 
     * @param driverName
     * @return
     */
    String requireDriverName(String driverName) {
        if (this.driverName.equalsIgnoreCase(driverName)) {
            return this.driverName;
        }
        throw new IllegalArgumentException(getClass().getSimpleName() + " does not support driver: " + driverName);
    }

    /**
     * Combine driver dependency contexts with the specified core Selenium Grid contexts.
     *
     * @param dependencyContexts core Selenium Grid dependency contexts
     * @param driverPlugin driver plug-in from which to acquire dependencies
     * @return combined contexts for Selenium Grid dependencies
     */
    public static String[] combineDependencyContexts(String[] dependencyContexts, DriverPlugin driverPlugin) {
        return ObjectArrays.concat(dependencyContexts, driverPlugin.getDependencyContexts(), String.class);
    }
    
}
