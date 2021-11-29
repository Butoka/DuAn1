package dao;

import entity.*;
import helper.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO extends CoffeeDevLDAO<SanPham, String> {

    String insertSanPham = "INSERT INTO SANPHAM VALUES (?,?,?,?,?,?,?,?,?,?)";
    String updateSanPham = "UPDATE SANPHAM SET TenSP = ?,SoLuong = ?,HinhAnh = ?,SDT = ?, GiaNhap = ?, GiaBan = ?, NgayNhap = ?,MaLoai = ?, MoTa = ?,TrangThai = ? WHERE MaSP = ?";
    String deleteSanPham = "DELETE FROM SANPHAM WHERE MaSP=?";
    String selectAllSanPham = "SELECT * FROM SANPHAM";
    String selectByIdSanPham = "SELECT * FROM SANPHAM WHERE MaSP=?";

    @Override
    public void insert(SanPham entity) {
        try {
            XJdbc.update(insertSanPham,
                    entity.getMaSP(),
                    entity.getTenSP(),
                    entity.getLoaiSP(),
                    entity.getSoLuong(),
                    entity.getGiaNhap(),
                    entity.getGiaBan(),
                    entity.getNgayNhap(),
                    entity.getMoTa(),
                    entity.getHinhAnh(),
                    entity.isTrangThai()
            );
        } catch (SQLException ex) {

        }
    }

    @Override
    public void update(SanPham entity) {
        try {
            XJdbc.update(updateSanPham,
                    entity.getTenSP(),
                    entity.getLoaiSP(),
                    entity.getSoLuong(),
                    entity.getGiaNhap(),
                    entity.getGiaBan(),
                    entity.getNgayNhap(),
                    entity.getMoTa(),
                    entity.getHinhAnh(),
                    entity.isTrangThai(),
                    entity.getMaSP()
            );
        } catch (SQLException ex) {

        }
    }

    @Override
    public void delete(String id) {
        try {
            XJdbc.update(deleteSanPham, id);
        } catch (SQLException ex) {

        }
    }

    @Override
    public List<SanPham> selectAll() {
        return this.selectBySql(selectAllSanPham);
    }

    @Override
    public List<SanPham> selectBySql(String sql, Object... args) {
        List<SanPham> list = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = XJdbc.query(sql, args);
            while (resultSet.next()) {
                SanPham sp = new SanPham();

                sp.setMaSP(resultSet.getString(1));
                sp.setTenSP(resultSet.getString(2));
                sp.setLoaiSP(resultSet.getString(3));
                sp.setSoLuong(resultSet.getInt(4));
                sp.setGiaNhap(resultSet.getDouble(5));
                sp.setGiaBan(resultSet.getDouble(6));
                sp.setNgayNhap(resultSet.getString(7));
                sp.setMoTa(resultSet.getString(8));
                sp.setHinhAnh(resultSet.getString(9));
                sp.setTrangThai(resultSet.getBoolean(10));

                list.add(sp);
            }
            resultSet.getStatement().getConnection().close();
        } catch (SQLException ex) {
        }
        return list;
    }

    @Override
    public SanPham selectById(String id) {
        List<SanPham> list = this.selectBySql(selectByIdSanPham, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
