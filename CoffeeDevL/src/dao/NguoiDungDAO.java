package dao;

import entity.*;
import helper.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NguoiDungDAO extends CoffeeDevLDAO<NguoiDung, String> {

    String insertNguoiDung = "INSERT INTO NGUOIDUNG VALUES (?,?,?,?,?,?,?)";
    String updateNguoiDung = "UPDATE NGUOIDUNG SET TenND = ?,MatKhau = ?,PhanQuyen = ?,TrangThai = ? WHERE MaND = ?";
    String deleteNguoiDung = "DELETE FROM NGUOIDUNG WHERE MaND = ?";
    String selectAllNguoiDung = "SELECT * FROM NGUOIDUNG";
    String selectByIdNguoiDung = "SELECT * FROM NGUOIDUNG WHERE MaND = ?";

    @Override
    public void insert(NguoiDung entity) {
        try {
            XJdbc.update(insertNguoiDung,
                    entity.getMaND(),
                    entity.getTenND(),
                    entity.getMatKhau(),
                    entity.getPhanQuyen(),
                    entity.getTrangThai()
            );
        } catch (SQLException ex) {

        }
    }

    @Override
    public void update(NguoiDung entity) {
        try {
            XJdbc.update(updateNguoiDung,
                    entity.getTenND(),
                    entity.getMatKhau(),
                    entity.getPhanQuyen(),
                    entity.getTrangThai(),
                    entity.getMaND()
            );
        } catch (SQLException ex) {

        }
    }

    @Override
    public void delete(String id) {
        try {
            XJdbc.update(deleteNguoiDung, id);
        } catch (SQLException ex) {

        }
    }

    @Override
    public List<NguoiDung> selectAll() {
        return this.selectBySql(selectAllNguoiDung);
    }

    @Override
    public List<NguoiDung> selectBySql(String sql, Object... args) {
        List<NguoiDung> list = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = XJdbc.query(sql, args);
            while (resultSet.next()) {
                NguoiDung nguoiDung = new NguoiDung();

               nguoiDung.setMaND(resultSet.getString(1));
               nguoiDung.setTenND(resultSet.getString(2));
               nguoiDung.setMatKhau(resultSet.getString(3));
               nguoiDung.setPhanQuyen(resultSet.getString(4));
               nguoiDung.setTrangThai(resultSet.getString(5));

                list.add(nguoiDung);
            }
            resultSet.getStatement().getConnection().close();
        } catch (SQLException ex) {
        }
        return list;
    }

    @Override
    public NguoiDung selectById(String id) {
        List<NguoiDung> list = this.selectBySql(selectByIdNguoiDung, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
