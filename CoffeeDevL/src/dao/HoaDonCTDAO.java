package dao;

import entity.HoaDonCT;
import helper.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HoaDonCTDAO extends CoffeeDevLDAO<HoaDonCT, String> {

    String insertHoaDonCT = "INSERT INTO HOADONCT VALUES (?,?,?,?,?,?)";
    String updateHoaDonCT = "UPDATE HOADONCT SET SoLuong=?,GiamGia = ?,DonGia=? WHERE MaHDCT=?";
    String deleteHoaDonCT = "DELETE FROM HOADONCT WHERE MaHDCT =?";
    String selectAllHoaDonCT = "SELECT * FROM HOADONCT";
    String selectByIdHoaDonCT = "SELECT * FROM HOADONCT WHERE MaHDCT =?";

    @Override
    public void insert(HoaDonCT entity) {
        try {
            XJdbc.update(insertHoaDonCT,
                    entity.getMaHD(),
                    entity.getMaHDCT(),
                    entity.getMaSP(),
                    entity.getSoLuong(),
                    entity.getGiamGia(),
                    entity.getDonGia()
            );
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(HoaDonCT entity) {
        try {
            XJdbc.update(updateHoaDonCT,
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

    public List<Object[]> selectHDCT(String keyWord) {
        String sql = "{CALL sp_HoaDonChiTiet(?)}";
        String[] cols = {"MaSP", "TenSP", "GiaBan", "SoLuong", "GiamGia", "DonGia"};
        return this.selectByHDCT(sql, cols, keyWord);
    }

    @Override
    public List<HoaDonCT> selectBySql(String sql, Object... args) {
        List<HoaDonCT> list = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = XJdbc.query(sql, args);
            while (resultSet.next()) {
                HoaDonCT hdct = new HoaDonCT();

                hdct.setMaHDCT(resultSet.getString(2));
                hdct.setMaHD(resultSet.getString(1));
                hdct.setMaSP(resultSet.getString(3));
                hdct.setSoLuong(resultSet.getInt(4));
                hdct.setGiamGia(resultSet.getDouble(5));
                hdct.setDonGia(resultSet.getDouble(6));

                list.add(hdct);
            }
            resultSet.getStatement().getConnection().close();
        } catch (SQLException ex) {
        }
        return list;
    }

    public List<Object[]> selectByHDCT(String sql, String[] cols, Object... args) {
        List<Object[]> list = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = XJdbc.query(sql, args);
            while (resultSet.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = resultSet.getObject(cols[i]);

                }
                list.add(vals);

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

    public List<Object> selectMaxMaHDCT() {
        String sql = "select Max(MaHDCT) from HoaDonCT";
        List<Object> list = new ArrayList<>();

        try {
            ResultSet rs = XJdbc.query(sql);
            while (rs.next()) {
                String hdd = rs.getString(1);
                if(hdd == null)
                {
                    hdd = "HDCT000";
                }
                list.add(hdd);
            }
            rs.getStatement().getConnection().close();

        } catch (SQLException ex) {

        }
        return list;
    }

    public List<HoaDonCT> selectByKeyword(String keyword1, String keyword2) {
        String sql = "select * from HoaDonCT where MaHD = ? and MaSP = ?";
        return this.selectBySql(sql, keyword1, keyword2);

    }

}
