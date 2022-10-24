package controller.dto.response;

import lombok.*;
import model.Mgni;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchResponse {
    private String message;
    private List<Mgni> mgniList;
}
