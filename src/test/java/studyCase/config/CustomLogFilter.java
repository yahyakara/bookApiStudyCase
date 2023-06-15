package studyCase.config;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.filter.log.LogDetail;
import io.restassured.internal.print.RequestPrinter;
import io.restassured.internal.print.ResponsePrinter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

public class CustomLogFilter implements Filter {

    public org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CustomLogFilter.class);

    public CustomLogFilter() {

    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        Response response = ctx.next(requestSpec, responseSpec);
        printRequest(requestSpec);
        printResponse(response);

        return response;
    }

    private void printRequest(FilterableRequestSpecification requestSpec) {
        final PrintStream stream = new PrintStream(new ByteArrayOutputStream(), true);
        Set<String> blacklistedHeaders = new HashSet<>();
        blacklistedHeaders.add("Authorization");
        String request = RequestPrinter.print(
                requestSpec, requestSpec.getMethod(),
                requestSpec.getURI(), LogDetail.ALL, blacklistedHeaders, stream, true);

        LOGGER.info( request);

    }

    private void printResponse(Response response) {
        final PrintStream stream = new PrintStream(new ByteArrayOutputStream());
        Set<String> blacklistedHeaders = new HashSet<>();
        blacklistedHeaders.add("Authorization");

        String responseText = ResponsePrinter.print(response, response.getBody(), stream, LogDetail.ALL, true, blacklistedHeaders);
        LOGGER.info(responseText);
    }

}