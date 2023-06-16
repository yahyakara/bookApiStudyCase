package studyCase.steps;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import studyCase.mocks.BooksStub;
import studyCase.mocks.MockServer;


import static studyCase.mocks.BooksStub.*;


public class Hooks {

    @Before(order = 1)
    public void putScenario(Scenario scenario) {
        TestContext.putScenario(scenario);
    }

    @Before(order = 2)
    public void startMock() {
        MockServer.startWireMockServer();

    }

    @After
    public void closeMock() throws InterruptedException {
        WireMockServer wireMockServer = MockServer.getWireMockServerThread();
        if (wireMockServer.isRunning()) {
            wireMockServer.stop();

            if (wireMockServer.isRunning()) {
                System.err.println("Failed to stop the WireMock server.");
            }
        }
    }

}