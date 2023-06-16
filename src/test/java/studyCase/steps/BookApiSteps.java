package studyCase.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.testng.Assert.*;

import studyCase.Models.AddBookResponse;
import studyCase.Models.BooksItem;
import studyCase.Models.BooksRes;
import studyCase.Models.GetBooksResponse;
import studyCase.Models.Payloads.AddBookPayload;
import studyCase.clients.BookClient;
import studyCase.config.IRestResponse;
import studyCase.mocks.BooksStub;

import java.awt.print.Book;
import java.util.List;
import java.util.stream.Collectors;

public class BookApiSteps {

    BookClient bookClient;
    private TestContext context;

    IRestResponse<BooksRes> booksResponse;
    IRestResponse<AddBookResponse> addedBookResponse;
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

    @Then("add book service is mocked based on valid response")
    public void addBookMock() {
        booksStub.createBookStub();
    }

    @Given("add book service is mocked based on title validation")
    public void addBookTitleValidationMock() {
        booksStub.emptyTitleStub();
    }

    @Given("add book service is mocked based on author validation")
    public void addBookAuthorValidationMock() {
        booksStub.missingAuthorStub();
    }

    @Given("add book service is mocked based on id validation")
    public void addBookIdValidationMock() {
        booksStub.idFieldReadOnlyStub();
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
        List<BooksItem> bookList = booksResponse.getBody().getBooks();

        List<BooksItem> matchingBooks = bookList.stream()
                .filter(book -> book.getTitle().equals(addedBook.getTitle()) && book.getAuthor().equals(addedBook.getAuthor()))
                .toList();

        assertFalse(matchingBooks.isEmpty(), String.format("%s should be on the list ", addedBook.getTitle()));
    }


    @Then("User should get {string} message from add books service")
    public void userShouldGetMessageFromAddBooksService(String expectedError) {
        String actualError = addedBookResponse.getBody().getError();
        assertEquals(actualError, expectedError);
    }
}
