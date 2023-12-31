package studyCase.mocks;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.client.WireMockBuilder;
import com.github.tomakehurst.wiremock.stubbing.Scenario;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static studyCase.mocks.MockServer.getWireMockServerThread;

public class BooksStub {
    private WireMockServer wireMockServer;

    public BooksStub() {
        this.wireMockServer = getWireMockServerThread();
    }

    public void emptyBookStoreStub() {
        MockServer.getWireMockServerThread().
                stubFor(get(urlEqualTo("/api/books"))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withStatus(200)
                                .withBodyFile("emptyBooks.json")));
    }

    public void bookListStub() {
        MockServer.getWireMockServerThread().
                stubFor(get(urlEqualTo("/api/books"))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withStatus(200)
                                .withBodyFile("books.json")));
    }

    public void emptyTitleStub() {
        MockServer.getWireMockServerThread().
                stubFor(put(urlEqualTo("/api/books"))
                        .withRequestBody(matchingJsonPath("$.title", equalTo("")))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withStatus(400)
                                .withBodyFile("missingTitleError.json")));
    }


    public void missingAuthorStub() {
        MockServer.getWireMockServerThread().
                stubFor(put(urlEqualTo("/api/books"))
                        .withRequestBody(matchingJsonPath("$.author", equalTo("")))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withStatus(400)
                                .withBodyFile("missingAuthorError.json")));
    }

    public void idFieldReadOnlyStub() {
        MockServer.getWireMockServerThread().
                stubFor(put(urlEqualTo("/api/books"))
                        .withRequestBody(matching(".*\"id\":.*"))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withStatus(400)
                                .withBodyFile("readonlyIdError.json")));
    }

    public void createBookStub() {
        MockServer.getWireMockServerThread().
                stubFor(put(urlEqualTo("/api/books"))
                        .withRequestBody(equalToJson("{\"title\": \"Test Book\", \"author\": \"Test Author\"}"))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withStatus(201)
                                .withBodyFile("bookAdded.json")));
    }

    public void duplicateBookStub() {
        MockServer.getWireMockServerThread().
                stubFor(put(urlEqualTo("/api/books"))
                        .withRequestBody(equalToJson("{\"title\": \"Test Book\", \"author\": \"Test Author\"}"))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withStatus(400)
                                .withBodyFile("duplicateBookError.json")));
    }

    public void stubForGetBook() {
        //mock for id lower than 10
        wireMockServer.stubFor(get(urlPathMatching("/api/books/(100|[1-9][0-9]?)$"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBodyFile("getBook.json")));
    }

    public void stubForGetBookError() {
        wireMockServer.stubFor(get(urlPathMatching("/api/books/([1-9]\\d{2,}|[2-9]\\d{2,})$"))
                .willReturn(serverError()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(404)));
    }
}