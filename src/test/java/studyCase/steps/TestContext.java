package studyCase.steps;


import io.cucumber.java.Scenario;
import java.util.HashMap;

public class TestContext {

    private static final HashMap<Thread, Scenario> map = new HashMap<>();
    ScenarioContext scenarioContext;

    public TestContext() {
        scenarioContext = new ScenarioContext();
    }

    public static void putScenario(Scenario scenario) {
        map.put(Thread.currentThread(), scenario);
    }

    public static Scenario getScenario() {
        return map.get(Thread.currentThread());
    }


    public ScenarioContext getScenarioContext() {
        return scenarioContext;
    }


}