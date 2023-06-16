package studyCase.Models;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetBooksResponse {

	@JsonProperty("books")
	private List<BooksItem> books;
	public List<BooksItem> getBooks(){
		return books;
	}
}