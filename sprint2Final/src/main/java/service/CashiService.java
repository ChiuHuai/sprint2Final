package service;

import model.Cashi;
import model.persistence.dao.CashiRepository;
import util.DBUtil;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CashiService {
    private CashiRepository cashiRepository = new CashiRepository();

    //little account may have more than 1 Cashi with same id,
    //cause it has composite pk.
    public List<Cashi> findAllById(String id) {
        List<Cashi> cashiList = cashiRepository.findAllBy((Predicate<Cashi>) e -> e.getId().equals(id));
        return cashiList;
    }

    public void addCashi(BigDecimal amt, String accNo, String id, String ccy ,Connection connection) throws Exception {
        Cashi cashi = new Cashi();
        cashi.setAmt(amt);
        cashi.setAccNo(accNo);
        cashi.setId(id);
        cashi.setCcy(ccy);
        cashiRepository.save(cashi, connection);
    }

    public void delete(String id) {
        cashiRepository.delete(id);
    }


}
