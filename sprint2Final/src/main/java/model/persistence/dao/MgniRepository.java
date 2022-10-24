package model.persistence.dao;

import controller.dto.resquest.UpdateMgniRequest;
import model.Mgni;
import util.ConnectionManager;
import util.DBUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class MgniRepository implements MgniDao {
    private final ConnectionManager connectionManager = ConnectionManager.getInstance();
    private final Connection connection = connectionManager.getConnection();
//MGNI_ID,MGNI_TIME,MGNI_TYPE, MGNI_CM_NO,
//    MGNI_KAC_TYPE,MGNI_BANK_NO,MGNI_CCY,MGNI_PV_TYPE
//    ,MGNI_BICACC_NO ,MGNI_I_TYPE,MGNI_P_REASON ,MGNI_AMT
//      ,MGNI_CT_NAME,MGNI_CT_TEL,MGNI_STATUS,MGNI_U_TIME

    public Mgni save(Mgni mgni,Connection connection1) {
        final String SQL = "INSERT INTO mgni(MGNI_ID,MGNI_TIME,MGNI_TYPE, MGNI_CM_NO,\n" +
                "    MGNI_KAC_TYPE,MGNI_BANK_NO,MGNI_CCY,MGNI_PV_TYPE\n" +
                "    ,MGNI_BICACC_NO ,MGNI_I_TYPE,MGNI_P_REASON ,MGNI_AMT\n" +
                "      ,MGNI_CT_NAME,MGNI_CT_TEL,MGNI_STATUS,MGNI_U_TIME) VALUE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement prepareStatement = connection1.prepareStatement(SQL)) {
            prepareStatement.setString(1,mgni.getId());
            prepareStatement.setObject(2,mgni.getTime());
            prepareStatement.setString(3,mgni.getType() );
            prepareStatement.setString(4, mgni.getCmNo());
            prepareStatement.setString(5, mgni.getKacType());
            prepareStatement.setString(6, mgni.getBankNo());
            prepareStatement.setString(7, mgni.getCcy());
            prepareStatement.setString(8, mgni.getPvType());
            prepareStatement.setString(9,mgni.getBicaccNo());
            prepareStatement.setString(10,mgni.getIType());
            prepareStatement.setString(11, mgni.getPReason());
            prepareStatement.setBigDecimal(12, mgni.getAmt());
            prepareStatement.setString(13, mgni.getCtName());
            prepareStatement.setString(14, mgni.getCtTel());
            prepareStatement.setString(15, mgni.getStatus());
            prepareStatement.setObject(16, mgni.getUTime());
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("save fail.");
        }
        return mgni;
    }

    @Override
    public Mgni save(Mgni entity) {
        return null;
    }

    @Override
    public Optional<Mgni> findById(String id) {
        final String SQL = "SELECT * FROM mgni WHERE MGNI_ID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Mgni mgni = new Mgni().builder()
                            .id(resultSet.getString(   "MGNI_ID"))
                            .time(resultSet.getObject(  "MGNI_TIME", LocalDateTime.class))
                            .type(resultSet.getString(   "MGNI_TYPE"))
                            .cmNo(resultSet.getString(   "MGNI_CM_NO"))
                            .kacType(resultSet.getString("MGNI_KAC_TYPE"))
                            .bankNo(resultSet.getString( "MGNI_BANK_NO"))
                            .ccy(resultSet.getString(    "MGNI_CCY"))
                            .pvType(resultSet.getString(  "MGNI_PV_TYPE"))
                            .bicaccNo(resultSet.getString("MGNI_BICACC_NO"))
                            .iType(resultSet.getString(   "MGNI_I_TYPE"))
                            .pReason(resultSet.getString("MGNI_P_REASON"))
                            .amt(resultSet.getBigDecimal( "MGNI_AMT"))
                            .ctName(resultSet.getString( "MGNI_CT_NAME"))
                            .ctTel(resultSet.getString( "MGNI_CT_TEL"))
                            .status(resultSet.getString( "MGNI_STATUS"))
                            .uTime(resultSet.getObject( "MGNI_U_TIME", LocalDateTime.class))
                            .build();
                    return Optional.of(mgni);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Mgni> findAll() {
        return null;
    }

    @Override //update 不用整個Mgni 要改crud
    public Optional<Mgni> update(Mgni entity, String s) {
        return Optional.empty();
    }

    public Optional<Mgni> update(UpdateMgniRequest request, String id) {
        final String SQL = "UPDATE mgni SET MGNI_CT_NAME = ?, MGNI_CT_TEL = ? WHERE MGNI_ID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, request.getCtName());
            preparedStatement.setString(2,request.getCtTel());
            preparedStatement.setString(3,id);

            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                Optional<Mgni> optionalMgni = findById(id);
                return optionalMgni;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public void delete(String id) {
        final String SQL = "DELETE FROM mgni WHERE MGNI_ID = ?";
        try (   Connection connection = DBUtil.getDataSource().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Mgni> findAllBy(Predicate<Mgni> predicate) {
        return null;
    }
}
