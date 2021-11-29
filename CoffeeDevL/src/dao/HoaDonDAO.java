package dao;

import entity.HoaDon;
import helper.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO extends CoffeeDevLDAO<HoaDon, String> {

    String insertHoaDon = "INSERT INTO HOADON VALUES (?, ?, ?, ?)";
    String updateHoaDon = "UPDATE HOADON SET NgayTao=?, TenDangNhap=?, TrangThai=? WHERE MaHD=?";
    String deleteHoaDon = "DELETE FROM HOADON WHERE MaHD=?";
    String selectAllHoaDon = "SELECT * FROM HOADON";
    String selectByIdHoaDon = "SELECT * FROM HOADON WHERE MaHD=?";

    @Override
    public void insert(HoaDon entity) {
        try {
            XJdbc.update(insertHoaDon,
                    entity.getMaHD(),
                    entity.getNgayTao(),
                    entity.getGiamGia(),
                    entity.getTienCT(),
                    entity.getMaND()
            );
        } catch (SQLException ex) {

        }
    }

    @Override
    public void update(HoaDon entity) {
        try {
            XJdbc.update(updateHoaDon,
                    entity.getNgayTao(),
                    entity.getGiamGia(),
                    entity.getTienCT(),
                    entity.getMaND(),
                    entity.getMaHD()
            );
        } catch (SQLException ex) {

        }
    }

    @Override
    public void delete(String id) {
        try {
            XJdbc.update(deleteHoaDon, id);
        } catch (SQLException ex) {

        }
    }

    @Override
    public List<HoaDon> selectAll() {
        return this.selectBySql(selectAllHoaDon);
    }

    @Override
    public List<HoaDon> selectBySql(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = XJdbc.query(sql, args);
            while (resultSet.next()) {
                HoaDon hd = new HoaDon();

                hd.setMaHD(resultSet.getString(1));
                hd.setNgayTao(resultSet.getString(2));
                hd.setGiamGia(resultSet.getDouble(3));
                hd.setTienCT(resultSet.getDouble(4));
                hd.setMaND(resultSet.getString(5));

                list.add(hd);
            }
            resultSet.getStatement().getConnection().close();
        } catch (SQLException ex) {
        }
        return list;
    }

    @Override
    public HoaDon selectById(String id) {
        List<HoaDon> list = this.selectBySql(selectByIdHoaDon, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
