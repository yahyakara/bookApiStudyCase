package studyCase.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;


public class RestConfig {


    public static RestAssuredConfig createConfig() {

        return RestAssuredConfig.config()
                .objectMapperConfig(new ObjectMapperConfig().jackson2ObjectMapperFactory(
                (type, s) -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                    return objectMapper;
                }
        ));
    }
}
