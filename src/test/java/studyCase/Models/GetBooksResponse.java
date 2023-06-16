package studyCase.Models;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetBooksResponse {

	@JsonProperty("books")
	private List<Book> books;

	public List<Book> getBooks(){
		return books;
	}
}