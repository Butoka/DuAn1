
package dao;

import helper.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO {
    String sql;
     private List<Object[]> getListOfArray(String sql, String[] cols, Object... args) {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.query(sql, args);
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);

                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    public List<Object[]> getSanPham(String MaSP) {
        sql = "{CALL sp_SanPham(?)}";
        String[] cols = {"TenSP", "SoLuongNhap", "SoLuongBan","NgayNhap"};
        return this.getListOfArray(sql, cols, MaSP);

    }

    public List<Object[]> getLuongNV() {
        sql = "{CALL sp_Luong}";
        String[] cols = {"TenNhanVien", "TongCaLam", "TongLuong"};
        return this.getListOfArray(sql, cols);
    }

    public List<Object[]> getDoanhThu(int Nam) {
        sql = "{CALL sp_DoanhThu(?)}";
        String[] cols = {"Thang", "SanPhamBan","TongGiaNhap","TongGiaBan","TongGiaGiam","TongTien"};
        return this.getListOfArray(sql, cols, Nam);
    }
}
