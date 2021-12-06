package dao;

import entity.HoaDonCT;
import helper.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HoaDonCTDAO extends CoffeeDevLDAO<HoaDonCT, String> {

    String insertHoaDonCT = "INSERT INTO HOADONCT VALUES (?, ?, ?, ?,?,?,?,?)";
    String updateHoaDonCT = "UPDATE HOADONCT SET MaHD=?, MaSP=?, SoLuong=?,GiamGia = ?,DonGia=?,ThanhTien = ?,MaBan = ? WHERE MaHDCT=?";
    String deleteHoaDonCT = "DELETE FROM HOADONCT WHERE MaHDCT =?";
    String selectAllHoaDonCT = "SELECT * FROM HOADONCT";
    String selectByIdHoaDonCT = "SELECT * FROM HOADONCT WHERE MaHDCT =?";

    @Override
    public void insert(HoaDonCT entity) {
        try {
            XJdbc.update(insertHoaDonCT,
                    entity.getMaHDCT(),
                    entity.getMaHD(),
                    entity.getMaSP(),
                    entity.getMaBan(),
                    entity.getSoLuong(),
                    entity.getGiamGia(),
                    entity.getDonGia()
            );
        } catch (SQLException ex) {

        }
    }

    @Override
    public void update(HoaDonCT entity) {
        try {
            XJdbc.update(updateHoaDonCT,
                    entity.getMaHD(),
                    entity.getMaSP(),
                    entity.getMaBan(),
                    entity.getSoLuong(),
                    entity.getGiamGia(),
                    entity.getDonGia(),
                    entity.getMaHDCT()
            );
        } catch (SQLException ex) {

        }
    }

    @Override
    public void delete(String id) {
        try {
            XJdbc.update(deleteHoaDonCT, id);
        } catch (SQLException ex) {

        }
    }

    @Override
    public List<HoaDonCT> selectAll() {
        return this.selectBySql(selectAllHoaDonCT);
    }

    @Override
    public List<HoaDonCT> selectBySql(String sql, Object... args) {
        List<HoaDonCT> list = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = XJdbc.query(sql, args);
            while (resultSet.next()) {
                HoaDonCT hdct = new HoaDonCT();

                hdct.setMaHDCT(resultSet.getString(1));
                hdct.setMaHD(resultSet.getString(2));
                hdct.setMaSP(resultSet.getString(3));
                hdct.setMaBan(resultSet.getString(4));
                hdct.setSoLuong(resultSet.getInt(5));
                hdct.setGiamGia(resultSet.getDouble(6));
                hdct.setDonGia(resultSet.getDouble(7));

                list.add(hdct);
            }
            resultSet.getStatement().getConnection().close();
        } catch (SQLException ex) {
        }
        return list;
    }

    @Override
    public HoaDonCT selectById(String id) {
        List<HoaDonCT> list = this.selectBySql(selectByIdHoaDonCT, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
