package service;

import controller.dto.resquest.ClearingAccount;
import controller.dto.resquest.DepositRequest;
import controller.dto.resquest.UpdateMgniRequest;
import model.Cashi;
import model.Mgni;
import model.persistence.dao.MgniRepository;
import util.ConnectionManager;
import util.DBUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransactionService {

    private final ConnectionManager connectionManager = ConnectionManager.getInstance();
    private static final Connection connection = DBUtil.getConnection();
    private MgniRepository mgniRepository = new MgniRepository(); //declare interface?

    private CashiService cashiService = new CashiService();

    public static Connection getConnection(){
        return connection;
    }
    //search
    public Mgni findById(String id) {
        Optional<Mgni> optionalMgni = mgniRepository.findById(id);
        if (optionalMgni.isEmpty()) {
            throw new RuntimeException("Mgni with id: " + id + " was not found!");
        }

        Mgni mgni = optionalMgni.get();
        if ("1".equals(mgni.getKacType())) {
            List<Cashi> cashiList = cashiService.findAllById(id);
            mgni.getCashiList().addAll(cashiList);
        }

        return mgni;
    }

    //deposite //rollback
    public String deposit(DepositRequest request) {
        try (Connection connection = DBUtil.getDataSource().getConnection()) {
            connection.setAutoCommit(false);
            Mgni mgni = addMgni(request, connection);

            List<ClearingAccount> clearingAccountList = request.getClearingAccountList();
            List<String> distinctAccNos = clearingAccountList.stream().map(e -> e.getAccNo()).distinct()
                    .collect(Collectors.toList()); //取出不同 AccNo

            if ("1".equals(request.getKacType())) {
                if (request.getClearingAccountList() == null) {
                    throw new RuntimeException("Clearing accountList should not be null.");
                }
                for (String accNo : distinctAccNos) {
                    BigDecimal tempTotalAmt = new BigDecimal(0);
                    for (ClearingAccount clearingAccount : clearingAccountList) {
                        if (accNo.equals(clearingAccount.getAccNo())) {
                            tempTotalAmt = tempTotalAmt.add(clearingAccount.getAmt());
                        }
                    }
                    cashiService.addCashi(tempTotalAmt, accNo, mgni.getId(), mgni.getCcy(), connection);
                }
            }
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(e);
            }
        }

        return "OK"; //如果失敗
        // Unknown column 'MGNI_BICACC_N' in 'field list' 還是print ok
    }

    public Mgni addMgni(DepositRequest request, Connection connection) throws Exception {
        Mgni mgni = new Mgni();
        //kacType(2) -> 交割金 iType 必填
        if (request.getKacType().equals("2")) {
            if (request.getIType() == null || request.getIType().isBlank()) {
                throw new RuntimeException("please choose itype(1~4).");
            }
            mgni.setIType(request.getIType());
        }

        mgni.setId(idCreater());
        mgni.setTime(LocalDateTime.now());
        mgni.setType("1"); //1入金
        mgni.setCmNo(request.getCmNo());
        mgni.setKacType(request.getKacType());
        mgni.setBankNo(request.getBankNo());
        mgni.setCcy(request.getCcy());
        mgni.setPvType(request.getPvType());
        mgni.setBicaccNo(request.getBicaccNo());
        mgni.setPReason(request.getPReason());
        BigDecimal totalAmt = request.getClearingAccountList().stream().map(e -> e.getAmt()).reduce(BigDecimal.ZERO, BigDecimal::add);
        mgni.setAmt(totalAmt);
        mgni.setCtName(request.getCtName());
        mgni.setCtTel(request.getCtTel());
        mgni.setStatus("0"); //介面沒提供選項，先寫0
        mgni.setUTime(LocalDateTime.now());
        mgniRepository.save(mgni, connection);

        return mgni;
    }

    public String idCreater() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String now = "MGI" + LocalDateTime.now().format(formatter);
        return now;
    }

    public Mgni update(UpdateMgniRequest request, String id) {
        Optional<Mgni> optionalMgni = mgniRepository.update(request, id);
        return optionalMgni.get();
    }

    public String deleteById(String id) {
        Mgni mgni = findById(id);
        mgniRepository.delete(id);

        if ("1".equals(mgni.getKacType())) {
            cashiService.delete(id);
        }

        return "delete.";
    }

}
