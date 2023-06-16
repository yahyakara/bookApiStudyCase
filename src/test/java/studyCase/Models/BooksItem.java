package studyCase.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BooksItem{

	@JsonProperty("author")
	private String author;

	@JsonProperty("id")
	private int id;

	@JsonProperty("title")
	private String title;

}