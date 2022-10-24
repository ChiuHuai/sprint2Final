package model;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Cashi implements Serializable {
    private String Id;
//    @Id
//    @Column(name = "CASHI_ACC_NO", nullable = false, length = 7)
    private String accNo;
//    @Id
//    @Column(name = "CASHI_CCY", nullable = false, length = 3)
    private String ccy;
//    @Column(name = "CASHI_AMT", nullable = false)
    private BigDecimal amt;
}
