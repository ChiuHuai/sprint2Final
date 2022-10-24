package controller.dto.resquest;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateMgniRequest {
    private String ctName;
    private String ctTel;
}
