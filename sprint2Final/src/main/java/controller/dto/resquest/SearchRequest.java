package controller.dto.resquest;

import lombok.*;
import model.Mgni;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchRequest {
    private String choice;
    private String id;
}
