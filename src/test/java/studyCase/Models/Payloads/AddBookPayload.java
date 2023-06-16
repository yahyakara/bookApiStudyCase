package studyCase.Models.Payloads;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddBookPayload {

    @JsonProperty("title")
    private String title;
    @JsonProperty("author")
    private String author;

    @JsonProperty("id")
    private Integer id;

}
