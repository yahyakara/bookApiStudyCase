package studyCase.steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.testng.Assert.*;

import studyCase.Models.AddBookResponse;
import studyCase.Models.Book;
import studyCase.Models.GetBookResponse;
import studyCase.Models.GetBooksResponse;
import studyCase.Models.Payloads.AddBookPayload;
import studyCase.clients.BookClient;
import studyCase.config.IRestResponse;
import studyCase.mocks.BooksStub;

import java.util.List;

public class BookApiSteps {

    BookClient bookClient;
    private TestContext context;

    IRestResponse<GetBooksResponse> booksResponse;
    IRestResponse<AddBookResponse> addedBookResponse;
    IRestResponse<GetBookResponse> getBookResponse;
    BooksStub booksStub;

    @Before
    public void beforeTests() {
        bookClient = new BookClient(context.getScenarioContext());
        booksStub = new BooksStub();
    }

    public BookApiSteps(TestContext context) {
        this.context = context;
    }

    @Given("Book store is mocked base on empty book list response")
    public void theBookStoreIsEmpty() {
        booksStub.emptyBookStoreStub();
    }

    @Given("Book store is mocked base on valid book list response")
    public void theBookStoreIHasBooks() {
        booksStub.bookListStub();
    }

    @Given("I retrieve the book list using the get books service")
    public void iRetrieveTheBookListUsingTheGetBooksService() {
        booksResponse = bookClient.getAllBooks();
    }

    @Then("the book list obtained from the get books service should be empty")
    public void theBookListObtainedFromTheGetBooksServiceShouldBeEmpty() {
        assert booksResponse.getBody().getBooks().size() == 0;
    }

    @Then("the Add book service is mocked based on valid response")
    public void addBookMock() {
        booksStub.createBookStub();
    }

    @Given("the Add book service is mocked based on validating title field is required")
    public void addBookTitleValidationMock() {
        booksStub.emptyTitleStub();
    }

    @Given("the Add book service is mocked based on validating author field is required")
    public void addBookAuthorValidationMock() {
        booksStub.missingAuthorStub();
    }

    @Given("the Add book service is mocked based on validating id filed read only")
    public void addBookIdValidationMock() {
        booksStub.idFieldReadOnlyStub();
    }


    @Given("the Get Book service is mocked based on validating the retrieval of a book by its ID")
    public void theGetBookServiceIsMockedBasedOnValidatingTheRetrievalOfABookByItsID() {
        booksStub.stubForGetBook();
        booksStub.stubForGetBookError();
    }


    @Given("add book service is mocked based on duplicate book validation")
    public void duplicateBookMock() {
        booksStub.duplicateBookStub();
    }

    @When("I add a book using the put book service with the following details title : {string}  Author : {string}")
    public void iAddABookUsingThePutBookServiceWithTheFollowingDetailsTitleAuthor(String title, String author) {
        AddBookPayload addBookPayload = AddBookPayload.builder()
                .title(title)
                .author(author)
                .id(null)
                .build();
        addedBookResponse = bookClient.addBook(addBookPayload);
    }

    @When("I add a book using the put book service with the following details title : {string}  Author : {string} id : {int}")
    public void iAddABookUsingThePutBookServiceWithTheInvalidData(String title, String author,int id) {
        AddBookPayload addBookPayload = AddBookPayload.builder()
                .title(title)
                .author(author)
                .id(id)
                .build();
        addedBookResponse = bookClient.addBook(addBookPayload);
    }


    @Then("the response status of the put book service should be {int}")
    public void theResponseStatusOfThePutBookServiceShouldBe(int expectedStatus) {
        int actualStatus = addedBookResponse.getStatusCode();
        assertEquals(actualStatus, expectedStatus, String.format("Put book response should be %s", expectedStatus));
    }

    @Then("the book list obtained from the get books service should include the added book with correct information")
    public void theBookListObtainedFromTheGetBooksServiceShouldIncludeTheAddedBookWithCorrectInformation() {
        AddBookResponse addedBook = addedBookResponse.getBody();
        List<Book> bookList = booksResponse.getBody().getBooks();

        List<Book> matchingBooks = bookList.stream()
                .filter(book -> book.getTitle().equals(addedBook.getTitle()) && book.getAuthor().equals(addedBook.getAuthor()))
                .toList();

        assertFalse(matchingBooks.isEmpty(), String.format("%s should be on the list ", addedBook.getTitle()));
    }


    @Then("User should get {string} message from add books service")
    public void userShouldGetMessageFromAddBooksService(String expectedError) {
        String actualError = addedBookResponse.getBody().getError();
        assertEquals(actualError, expectedError);
    }


    @When("I utilize the Get Book service to retrieve the book with the ID: {int}")
    public void iUtilizeTheGetBookServiceToRetrieveTheBookWithTheID(int id) {
        getBookResponse = bookClient.getBookResponse(id);
    }

    @Then("I should receive the information for a book with the title: {string}, author: {string}, and ID: {string}")
    public void iShouldReceiveTheInformationForABookWithTheTitleAuthorAndID(String expectedTitle, String expectedAuthor, String expectedID) {
        String actualTitle = getBookResponse.getBody().getTitle();
        String actualAuthor = getBookResponse.getBody().getAuthor();
        String actualID= getBookResponse.getBody().getId();

        assertEquals(actualTitle, expectedTitle, "The actual book title does not match the expected title");
        assertEquals(actualAuthor, expectedAuthor, "The actual author does not match the expected author");
        assertEquals(actualID, expectedID, "The actual ID does not match the expected ID");

    }


    @Then("the response status of the Get Book service should be {int}")
    public void theResponseStatusOfTheGetBookServiceShouldBe(int expectedStatus) {
        int actualStatus = getBookResponse.getStatusCode();
        assertEquals(actualStatus, expectedStatus, "Get book service response should be valid");
    }
}
