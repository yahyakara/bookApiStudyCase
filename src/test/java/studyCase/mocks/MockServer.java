package studyCase.mocks;


import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.common.FatalStartupException;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import org.apache.commons.lang3.RandomUtils;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class MockServer {

    private static ThreadLocal<WireMockServer> wireMockServerThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<Integer> wireMockServerPortThreadLocal = new ThreadLocal<>();

    public static void startWireMockServer() {
        int serverPort = RandomUtils.nextInt(1024, 49151);
        WireMockConfiguration options = wireMockConfig()
                .port(serverPort)
                .notifier(new ConsoleNotifier(false))
                .extensions(new ResponseTemplateTransformer(true));

        WireMockServer wireMockServer = new WireMockServer(options);

        try {
            wireMockServer.start();
        } catch (FatalStartupException e) {
            throw new RuntimeException("Failed to start WireMock server on port: " + serverPort, e);
        }

        wireMockServerThreadLocal.set(wireMockServer);
        wireMockServerPortThreadLocal.set(serverPort);
    }

    public static WireMockServer getWireMockServerThread() {

        return wireMockServerThreadLocal.get();
    }

    public static String getMockUrl() {
        Integer serverPort = wireMockServerPortThreadLocal.get();
        if (serverPort != null) {
            return "http://localhost:" + serverPort;
        }
        return null;
    }

    public static void stopWireMockServer() {
        WireMockServer wireMockServer = wireMockServerThreadLocal.get();
        if (wireMockServer != null) {
            wireMockServer.stop();
            wireMockServerThreadLocal.remove();
            wireMockServerPortThreadLocal.remove();
        }
    }

}
