package dao;

import entity.*;
import helper.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO extends CoffeeDevLDAO<NhanVien, String> {

    String insertNhanVien = "INSERT INTO NHANVIEN VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    String updateNhanVien = "UPDATE NHANVIEN SET TenNV = ?,GioiTinh = ?,NgaySinh = ?,SDT = ?, DiaChi = ?, ChucVu = ?, Email = ?,NgayVaoLam = ? WHERE MaND = ?";
    String deleteNhanVien = "DELETE FROM NHANVIEN WHERE MaNV=?";
    String selectAllNhanVien = "SELECT * FROM NHANVIEN";
    String selectByIdNhanVien = "SELECT * FROM NHANVIEN WHERE MaNV=?";

    @Override
    public void insert(NhanVien entity) {
        try {
            XJdbc.update(insertNhanVien,
                    entity.getMaNV(),
                    entity.getTenNV(),
                    entity.isGioiTinh(),
                    entity.getNgaySinh(),
                    entity.getSdt(),
                    entity.getDiaChi(),
                    entity.getChucVu(),
                    entity.getEmail(),
                    entity.getNgayVaoLam()
            );
        } catch (SQLException ex) {

        }
    }

    @Override
    public void update(NhanVien entity) {
        try {
            XJdbc.update(updateNhanVien,
                    entity.getTenNV(),
                    entity.isGioiTinh(),
                    entity.getNgaySinh(),
                    entity.getSdt(),
                    entity.getDiaChi(),
                    entity.getChucVu(),
                    entity.getEmail(),
                    entity.getNgayVaoLam(),
                    entity.getMaNV()
            );
        } catch (SQLException ex) {

        }
    }

    @Override
    public void delete(String id) {
        try {
            XJdbc.update(deleteNhanVien, id);
        } catch (SQLException ex) {

        }
    }

    @Override
    public List<NhanVien> selectAll() {
        return this.selectBySql(selectAllNhanVien);
    }

    @Override
    public List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = XJdbc.query(sql, args);
            while (resultSet.next()) {
                NhanVien nv = new NhanVien();

                nv.setMaNV(resultSet.getString(1));
                nv.setTenNV(resultSet.getString(2));
                nv.setGioiTinh(resultSet.getBoolean(3));
                nv.setNgaySinh(resultSet.getString(4));
                nv.setSdt(resultSet.getString(5));
                nv.setEmail(resultSet.getString(6));
                nv.setDiaChi(resultSet.getString(7));
                nv.setChucVu(resultSet.getString(8));
                nv.setNgayVaoLam(resultSet.getString(9));

                list.add(nv);
            }
            resultSet.getStatement().getConnection().close();
        } catch (SQLException ex) {
        }
        return list;
    }

    @Override
    public NhanVien selectById(String id) {
        List<NhanVien> list = this.selectBySql(selectByIdNhanVien, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
