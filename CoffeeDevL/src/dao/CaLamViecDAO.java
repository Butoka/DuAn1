package dao;

import entity.*;
import helper.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CaLamViecDAO extends CoffeeDevLDAO<CaLamViec, String> {

    String insertCaLamViec = "INSERT INTO CALAMVIEC VALUES (?, ?, ?, ?, ?)";
    String updateCaLamViec = "UPDATE CALAMVIEC SET TenCa=?, GioBatDau=?, GioKetThuc=?, Luong = ? WHERE MaCa=?";
    String deleteCaLamViec = "DELETE FROM CALAMVIEC WHERE MaCa=?";
    String selectAllCaLamViec = "SELECT * FROM CALAMVIEC";
    String selectByIdCaLamViec = "SELECT * FROM CALAMVIEC WHERE MaCa=?";

    @Override
    public void insert(CaLamViec entity) {
        try {
            XJdbc.update(insertCaLamViec,
                    entity.getMaCa(),
                    entity.getTenCa(),
                    entity.getGioBatDau(),
                    entity.getGioKetThuc(),
                    entity.getLuong()
            );
        } catch (SQLException ex) {

        }
    }

    @Override
    public void update(CaLamViec entity) {
        try {
            XJdbc.update(updateCaLamViec,
                    entity.getTenCa(),
                    entity.getGioBatDau(),
                    entity.getGioKetThuc(),
                    entity.getLuong(),
                    entity.getMaCa()
            );
        } catch (SQLException ex) {

        }
    }

    @Override
    public void delete(String id) {
        try {
            XJdbc.update(deleteCaLamViec, id);
        } catch (SQLException ex) {

        }
    }

    @Override
    public List<CaLamViec> selectAll() {
        return this.selectBySql(selectAllCaLamViec);
    }

    @Override
    public List<CaLamViec> selectBySql(String sql, Object... args) {
        List<CaLamViec> list = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = XJdbc.query(sql, args);
            while (resultSet.next()) {
                CaLamViec clv = new CaLamViec();
                clv.setMaCa(resultSet.getInt(1));
                clv.setTenCa(resultSet.getString(2));
                clv.setGioBatDau(resultSet.getString(3));
                clv.setGioKetThuc(resultSet.getString(4));
                clv.setLuong(resultSet.getDouble(5));
                list.add(clv);
            }
            resultSet.getStatement().getConnection().close();
        } catch (SQLException ex) {
        }
        return list;
    }

    @Override
    public CaLamViec selectById(String id) {
        List<CaLamViec> list = this.selectBySql(selectByIdCaLamViec, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
