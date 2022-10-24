import controller.dto.resquest.ClearingAccount;
import controller.dto.resquest.DepositRequest;
import service.TransactionService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Sprint2Application {
    public static void main(String[] args) {
        TransactionService transactionService = new TransactionService();

//        Mgni mgni = service.findById("MGI20221005091209969");
//        System.out.println(mgni);

        //交割金 kac 2
//        DepositRequest depositRequest = new DepositRequest().builder()
//                .cmNo("05")
//                .kacType("2")
//                .bankNo("333")
//                .ccy("TWD")
//                .pvType("2")
//                .bicaccNo("bank12345")
//                .ctName("HuaiChiu")
//                .ctTel("0912345678")
//                .iType("2")
//                .pReason("no reason")
//                .build();
//        String s = service.deposit(depositRequest);
//        System.out.println(s);
/////////////////////////////////////////////////////////////////////////
        ClearingAccount clearingAccount1 = new ClearingAccount();
        clearingAccount1.setAccNo("55555");
        clearingAccount1.setAmt(new BigDecimal(2000));

        ClearingAccount clearingAccount2 = new ClearingAccount();
        clearingAccount2.setAccNo("55555");
        clearingAccount2.setAmt(new BigDecimal(2000));

        List<ClearingAccount> accounts = new ArrayList<>();
        accounts.add(clearingAccount1);
        accounts.add(clearingAccount2);

        DepositRequest depositRequest2 = new DepositRequest().builder()
                .cmNo("05")
                .kacType("1")
                .bankNo("333")
                .ccy("TWD")
                .pvType("2")
                .bicaccNo("bank12345")
                .ctName("HuaiChiu")
                .ctTel("0912345678")
                .pReason("no reason")
                .clearingAccountList(accounts)
                .build();
        String s2 = transactionService.deposit(depositRequest2);
        System.out.println(s2);

        //////////////////////////////////////////
//        UpdateMgniRequest updateMgniRequest = new UpdateMgniRequest().builder()
//                .ctName("newCt").ctTel("0911111111").build();
//        Mgni mgni = service.update(updateMgniRequest, "MGI20221016155632124");
//        System.out.println(mgni);
        ///////////////////////////////////////////

//        String delete = service.deleteById("MGI20221017020945309");
//        System.out.println(delete);
    }
}
