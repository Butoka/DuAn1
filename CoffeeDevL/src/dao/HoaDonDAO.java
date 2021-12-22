package dao;

import entity.HoaDon;
import helper.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO extends CoffeeDevLDAO<HoaDon, String> {

    String insertHoaDon = "INSERT INTO HOADON VALUES (?,?,?,?,?,?,?)";
    String updateHoaDon = "UPDATE HOADON SET NgayTao=?, TenDN=?, TrangThai=?,Huy = ?,Ban = ?,DangBan = ? WHERE MaHD=?";
    String deleteHoaDon = "DELETE FROM HOADON WHERE MaHD=?";
    String selectAllHoaDon = "SELECT * FROM HOADON";
    String selectByIdHoaDon = "SELECT * FROM HOADON WHERE MaHD=?";
    String selectByIDMaBan = "SELECT * FROM HOADON WHERE Ban=? and DangBan = 'true'";
     public static boolean loi2 = false;
    @Override
    public void insert(HoaDon entity) {
        try {
            XJdbc.update(insertHoaDon,
                    entity.getMaHD(),
                    entity.getNgayTao(),
                    entity.getTenDN(),
                    entity.getTrangThai(),
                    entity.isHuy(),
                    entity.getBan(),
                    entity.isDangBan()
            );
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void update(HoaDon entity) {
        try {
            XJdbc.update(updateHoaDon,
                    entity.getNgayTao(),
                    entity.getTenDN(),
                    entity.getTrangThai(),
                    entity.isHuy(),
                    entity.getBan(),
                    entity.isDangBan(),
                    entity.getMaHD()
            );
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        try {
            XJdbc.update(deleteHoaDon, id);
        } catch (SQLException ex) {
            loi2 = true;
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
                hd.setTenDN(resultSet.getString(3));
                hd.setTrangThai(resultSet.getBoolean(4));
                hd.setHuy(resultSet.getBoolean(5));
                hd.setBan(resultSet.getInt(6));
                hd.setDangBan(resultSet.getBoolean(7));

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

    public List<HoaDon> selectByKeyword(int keyword) {
        String sql = selectByIDMaBan;
        return this.selectBySql(sql, keyword);

    }
     public List<HoaDon> selectHD(String keyword) {
        String sql = "SELECT * FROM HOADON WHERE TENDN = ?";
        return this.selectBySql(sql, keyword);

    }

    public List<Integer> selectYears() {
        String sql = "SELECT DISTINCT year(NgayTao) FROM HOADON ";
        List<Integer> list = new ArrayList<>();

        try {
            ResultSet rs = XJdbc.query(sql);
            while (rs.next()) {
                list.add(rs.getInt(1));
            }
            rs.getStatement().getConnection().close();

        } catch (SQLException ex) {

        }
        return list;
    }

    public List<Object> selectMaxMaHD() {
        String sql = "select Max(MaHD) from HoaDon";
        List<Object> list = new ArrayList<>();

        try {
            ResultSet rs = XJdbc.query(sql);
            while (rs.next()) {
                String row = rs.getString(1);
                if(row == null)
                {
                    row = "HD000";
                }
                list.add(row);
            }
            rs.getStatement().getConnection().close();

        } catch (SQLException ex) {

        }

        return list;

    }
}
