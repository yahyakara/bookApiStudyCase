package studyCase.config;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SessionConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

@Slf4j
public abstract class AbstractRestAssuredClient {
    static int connectionTimeout = 5000;
    static int connectionManagerTimeout = 10000;
    static int socketTimeout = 15000;

    public static final HttpClientConfig defHttpClientConfig = HttpClientConfig.httpClientConfig()
            .setParam("http.connection.timout", connectionManagerTimeout)
            .setParam("http.connection.timout", connectionTimeout)
            .setParam("http.socket.timout", socketTimeout);

    protected RestAssuredConfig getSessionConfig() {
        String SESSION_ID = "session" + RandomStringUtils.randomAlphanumeric(5);
        return RestAssuredConfig.config().httpClient(defHttpClientConfig)
                .sessionConfig(new SessionConfig().sessionIdName(SESSION_ID));
    }

    protected RequestSpecification getDefaultSpec() {
        return new RequestSpecBuilder()
                .setConfig(getSessionConfig())
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build()
                .filter(new AllureRestAssured())
                .filter(new CustomLogFilter());
    }

}
