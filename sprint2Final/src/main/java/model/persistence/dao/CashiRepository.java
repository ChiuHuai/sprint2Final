package model.persistence.dao;

import model.Cashi;
import model.CashiId;
import util.ConnectionManager;
import util.DBUtil;

import java.awt.print.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CashiRepository implements CashiDao{
    private final ConnectionManager connectionManager = ConnectionManager.getInstance();
    private final Connection connection = connectionManager.getConnection();

    public Cashi save(Cashi cashi, Connection connection1) {
        final String SQL = "INSERT INTO cashi(CASHI_MGNI_ID,CASHI_ACC_NO,CASHI_CCY,CASHI_AMT) VALUE(?,?,?,?)";
        try (PreparedStatement prepareStatement = connection1.prepareStatement(SQL)) {
            prepareStatement.setString(1,cashi.getId());
            prepareStatement.setString(2,cashi.getAccNo());
            prepareStatement.setString(3,cashi.getCcy());
            prepareStatement.setBigDecimal(4,cashi.getAmt());
            prepareStatement.executeUpdate();
//            int i = 1/0;
        }catch(SQLException e){
           throw new RuntimeException("fail");
        }
            return cashi;
    }

    @Override
    public Cashi save(Cashi entity) {
        return null;
    }

    @Override
    public Optional<Cashi> findById(CashiId cashiId) {
        return Optional.empty();
    }

    @Override
    public List<Cashi> findAll() {
        List<Cashi>  cashiList = new ArrayList<>();
        final String SQL = "SELECT * FROM cashi";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL);
             ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                Cashi cashi = new Cashi().builder()
                        .Id(resultSet.getString("CASHI_MGNI_ID"))
                        .accNo(resultSet.getString("CASHI_ACC_NO"))
                        .ccy(resultSet.getString("CASHI_CCY"))
                        .amt(resultSet.getBigDecimal("CASHI_AMT"))
                        .build();
                cashiList.add(cashi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cashiList;
    }

    @Override
    public Optional<Cashi> update(Cashi entity, CashiId cashiId) {
        return Optional.empty();
    }


    public void delete(CashiId cashiId) {
    }

    public void delete(String id) {
        final String SQL = "DELETE FROM cashi WHERE CASHI_MGNI_ID = ?";
        try (Connection connection = DBUtil.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Override
    public List<Cashi> findAllBy(Predicate<Cashi> predicate) {
        List<Cashi> cashiList = findAll();
        return cashiList.stream().filter(predicate).collect(Collectors.toList());
    }
}
