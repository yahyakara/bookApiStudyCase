package studyCase.config;

import io.restassured.response.Response;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.Assert.assertThat;


@SuppressWarnings("unchecked")
public class RestResponse<X> implements IRestResponse<X> {
    private X data;
    private Response response;
    private Exception e;

    public RestResponse(Class<X> x, Response response) {
        this.response = response;
        try {
            this.data = x.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Should be a default constructor in the Response POJO");
        }
    }

    public String getContent() {
        return response.getBody().asString();
    }

    public int getStatusCode() {
        return response.getStatusCode();
    }

    public boolean isSuccessful() {
        int code = response.getStatusCode();
        return code >= 200 && code < 301;
    }


    public String getStatusDescription() {
        return response.getStatusLine();
    }

    public Response getResponse() {
        return response;
    }


    public X getBody() {
        try {
            data = (X) response.getBody().as(data.getClass());
        } catch (Exception e) {
            this.e = e;
        }
        return data;
    }

    public Exception getException() {
        return e;
    }

    public void checkSchema(String schemaFilePath) {
        assertThat(response.asString(), matchesJsonSchemaInClasspath(schemaFilePath));
    }


}
