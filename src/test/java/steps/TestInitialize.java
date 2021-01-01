package steps;

import common.CommonTests;
import common.MyLogger;
import common.Spec;
import config.Environment;
import cucumber.api.java.Before;
import io.restassured.RestAssured;
import org.apache.logging.log4j.Logger;


public class TestInitialize {

    private Logger log = MyLogger.log;
    @Before
    public void TestSetup() throws Exception {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        new Environment();
        new Spec();
        if (!CommonTests.isAppBackendUp()) // check if the service under test is up before running any tests
            throw new Exception("Skipping tests as the application backend is down.");
    }
}
