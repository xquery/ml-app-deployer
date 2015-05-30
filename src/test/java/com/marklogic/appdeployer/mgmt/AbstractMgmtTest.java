package com.marklogic.appdeployer.mgmt;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marklogic.appdeployer.AppConfig;

/**
 * Base class for tests that run against the new management API in ML8. Main purpose is to provide convenience methods
 * for quickly creating and deleting a sample application. 
 */
public abstract class AbstractMgmtTest extends Assert {

    public final static String SAMPLE_APP_NAME = "sample-app";

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected ManageConfig manageConfig;
    protected ManageClient manageClient;

    protected ConfigDir configDir;
    protected ConfigManager configMgr;

    protected AppConfig appConfig;

    @Before
    public void initialize() {
        manageClient = new ManageClient(new ManageConfig());
        configDir = new ConfigDir(new File("src/test/resources/sample-app/src/main/ml-config"));
        configMgr = new ConfigManager(manageClient);
        initializeAppConfig();
    }

    protected void createSampleApp() {
        configMgr.createRestApi(configDir, appConfig);
    }

    protected void initializeAppConfig() {
        appConfig = new AppConfig();
        appConfig.setName("sample-app");
        appConfig.setRestPort(8540);
    }

    protected void deleteSampleApp() {
        configMgr.deleteRestApiAndWaitForRestart(appConfig, true, true);
    }
}