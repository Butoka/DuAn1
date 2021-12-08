package dao;

import entity.*;
import helper.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChiTietLuongDAO extends CoffeeDevLDAO<ChiTietLuong, String> {

    String insertChiTietLuong = "INSERT INTO CHITIETLUONG VALUES (?, ?, ?, ?,?,?)";
    String updateChiTietLuong = "UPDATE CHITIETLUONG SET NgayLamViec=?, MaCa=?, MaNV=?, TongSoCa = ?, ThanhTien = ? WHERE MaLuongCT=?";
    String deleteChiTietLuong = "DELETE FROM CHITIETLUONG WHERE MaLuongCT=?";
    String selectAllChiTietLuong = "SELECT * FROM CHITIETLUONG";
    String selectByIdChiTietLuong = "SELECT * FROM CHITIETLUONG WHERE MaLuongCT=?";

    @Override
    public void insert(ChiTietLuong entity) {
        try {
            XJdbc.update(insertChiTietLuong,
                    entity.getMaLuongCT(),
                    entity.getNgayLamViec(),
                    entity.getMaCa(),
                    entity.getTongSo(),
                    entity.getThanhTien(),
                    entity.getMaNV()
            );
        } catch (SQLException ex) {

        }
    }

    @Override
    public void update(ChiTietLuong entity) {
        try {
            XJdbc.update(updateChiTietLuong,
                    entity.getNgayLamViec(),
                    entity.getMaCa(),
                    entity.getTongSo(),
                    entity.getThanhTien(),
                    entity.getMaNV(),
                    entity.getMaLuongCT()
            );
        } catch (SQLException ex) {

        }
    }

    @Override
    public void delete(String id) {
        try {
            XJdbc.update(deleteChiTietLuong, id);
        } catch (SQLException ex) {

        }
    }

    @Override
    public List<ChiTietLuong> selectAll() {
        return this.selectBySql(selectAllChiTietLuong);
    }

    @Override
    public List<ChiTietLuong> selectBySql(String sql, Object... args) {
        List<ChiTietLuong> list = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = XJdbc.query(sql, args);
            while (resultSet.next()) {
                ChiTietLuong ctl = new ChiTietLuong();
                ctl.setMaLuongCT(resultSet.getString(1));
                ctl.setNgayLamViec(resultSet.getString(2));
                ctl.setMaCa(resultSet.getInt(3));
                ctl.setTongSo(resultSet.getInt(4));
                ctl.setThanhTien(resultSet.getDouble(5));
                ctl.setMaNV(resultSet.getString(6));

                list.add(ctl);
            }
            resultSet.getStatement().getConnection().close();
        } catch (SQLException ex) {
        }
        return list;
    }

    @Override
    public ChiTietLuong selectById(String id) {
        List<ChiTietLuong> list = this.selectBySql(selectByIdChiTietLuong, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
