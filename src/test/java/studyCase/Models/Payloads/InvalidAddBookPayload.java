package studyCase.Models.Payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Builder
@Data
public class InvalidAddBookPayload {

    @JsonProperty("title")
    private String title;
    @JsonProperty("author")
    private String author;

    @JsonProperty("id")
    private Integer id;
    public Map<String, Object> toRequestPayload() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("title", title);
        payload.put("author", author);
        return payload;
    }
}
