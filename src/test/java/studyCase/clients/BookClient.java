package studyCase.clients;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import studyCase.Constants.Routes.BookRoute;
import studyCase.Models.AddBookResponse;
import studyCase.Models.GetBookResponse;
import studyCase.Models.GetBooksResponse;
import studyCase.Models.Payloads.AddBookPayload;
import studyCase.config.AbstractRestAssuredClient;
import studyCase.config.CustomLogFilter;
import studyCase.config.IRestResponse;
import studyCase.config.RestResponse;
import studyCase.mocks.MockServer;
import studyCase.steps.ScenarioContext;


import static io.restassured.RestAssured.given;


@SuppressWarnings("unchecked")
public class BookClient extends AbstractRestAssuredClient {
    ScenarioContext scenarioContext;

    public BookClient(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @Override
    protected RequestSpecification getDefaultSpec() {
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setConfig(getSessionConfig())
                .setConfig(getMapperConfig())
                .setBaseUri(MockServer.getMockUrl())
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build()
                .filter(new CustomLogFilter())
                .filter(new AllureRestAssured());

        return requestSpec;
    }

    public IRestResponse<GetBooksResponse> getAllBooks() {
        Response response =
                given()
                        .spec(getDefaultSpec())
                        .contentType(ContentType.JSON)
                        .when()
                        .get(BookRoute.books())
                        .then().log().all().extract().response();

        return new RestResponse<>(GetBooksResponse.class, response);
    }

    public IRestResponse<GetBookResponse> getBookResponse(int id){
        Response response =
                given()
                        .spec(getDefaultSpec())
                        .contentType(ContentType.JSON)
                        .when()
                        .pathParam("id", id)
                        .get(BookRoute.getBook())
                        .then().log().all().extract().response();

        return new RestResponse<>(GetBookResponse.class, response);
    }

    public IRestResponse<AddBookResponse> addBook(AddBookPayload addBookPayload) {
        Response response =
                given()
                        .spec(getDefaultSpec())
                        .contentType(ContentType.JSON)
                        .when()
                        .body(addBookPayload)
                        .put(BookRoute.books())
                        .then().log().all().extract().response();

        return new RestResponse<>(AddBookResponse.class, response);
    }


}
