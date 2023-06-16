Feature: Adding a Book to the List
  As a user
  I want to be able to add a book to the book list
  So that I can keep track of new books

  @mocked
  Scenario: Add a book to the list
    Given Book store is mocked base on empty book list response
    Given I retrieve the book list using the get books service
    Then the book list obtained from the get books service should be empty
    Given the Add book service is mocked based on valid response
    When I add a book using the put book service with the following details title : "Test Book"  Author : "Test Author"
    Then the response status of the put book service should be 201
    Given Book store is mocked base on valid book list response
    When I retrieve the book list using the get books service
    Then the book list obtained from the get books service should include the added book with correct information

  @mocked
  Scenario Outline: User should not miss the book title or author
    Given the Add book service is mocked based on validating author field is required
    And  the Add book service is mocked based on validating title field is required
    When I add a book using the put book service with the following details title : "<title>"  Author : "<author>"
    Then User should get "<error>" message from add books service
    And the response status of the put book service should be 400

    Examples:
      | title   | author     | error                       |
      | SRE 101 |            | Field 'author' is required. |
      |         | John Smith | Field 'title' is required.  |

  @mocked
  Scenario: Add a book to the list with invalid payload
    Given the Add book service is mocked based on validating id filed read only
    When I add a book using the put book service with the following details title : "test"  Author : "test" id : 1
    Then the response status of the put book service should be 400
    Then User should get "Field id is only read only." message from add books service

  Scenario: User can't add book similar book to list
    Given add book service is mocked based on duplicate book validation
    When I add a book using the put book service with the following details title : "Test Book"  Author : "Test Author"
    Then I add a book using the put book service with the following details title : "Test Book"  Author : "Test Author"
    Then User should get "Another book with similar title and author already exists." message from add books service

    Scenario: User can get book information with get book service
      Given the Get Book service is mocked based on validating the retrieval of a book by its ID
      When I utilize the Get Book service to retrieve the book with the ID: 99
      Then I should receive the information for a book with the title: "SRE 101", author: "John Smith", and ID: "99"
      Then the response status of the Get Book service should be 200
      When I utilize the Get Book service to retrieve the book with the ID: 101
      Then the response status of the Get Book service should be 404
