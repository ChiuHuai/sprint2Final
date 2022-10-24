package controller.dto.resquest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClearingAccount {
//    @NotBlank(message = "accNo should not be blank")
//    @Length(max = 7, message = "accNo less than 7 characters")
    private String accNo;//存入結算賬號
//    @NotNull(message = "amt should not be null")
//    @DecimalMin(value = "0", inclusive = false, message = "amt must be greater than 0")
//    @Digits(integer = 20, fraction = 4, message = "digits of price is not correct")
    private BigDecimal amt;//存入結算金額
}

