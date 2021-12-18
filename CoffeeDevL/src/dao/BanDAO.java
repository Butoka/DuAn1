package dao;

import entity.Ban;
import helper.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BanDAO extends CoffeeDevLDAO<Ban, String> {

    String insertBan = "INSERT INTO Ban VALUES (?, ?,?)";
    String updateBan = "UPDATE Ban SET TenBan=? WHERE MaBan=?";
    String updateTrangThai = "UPDATE Ban SET TrangThai=? WHERE MaBan=?";
    String deleteBan = "DELETE FROM Ban WHERE MaBan=?";
    String selectAllBan = "SELECT * FROM Ban";
    String selectByIdBan = "SELECT * FROM Ban WHERE MaBan=?";
    public static boolean loi = false;
    @Override
    public void insert(Ban entity) {
        try {
            XJdbc.update(insertBan,
                    entity.getMaBan(),
                    entity.getTenBan(),
                    entity.isTrangThai());
        } catch (SQLException ex) {

        }
    }

    @Override
    public void update(Ban entity) {
        try {
            XJdbc.update(updateBan,
                    entity.getTenBan(), entity.getMaBan()
            );
        } catch (SQLException ex) {

        }
    }
    public void updateTrangThai(Ban entity)
    {
         try {
            XJdbc.update(updateTrangThai,
                    entity.isTrangThai(),
                    entity.getMaBan()
            );
        } catch (SQLException ex) {

        }
    }
    @Override
    public void delete(String id) {
        try {
            XJdbc.update(deleteBan, id);
        } catch (SQLException ex) {
           loi = true;
        }
    }

    @Override
    public List<Ban> selectAll() {
        return this.selectBySql(selectAllBan);
    }

    @Override
    public List<Ban> selectBySql(String sql, Object... args) {
        List<Ban> list = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = XJdbc.query(sql, args);
            while (resultSet.next()) {
                Ban ban = new Ban();
                ban.setMaBan(resultSet.getInt(1));
                ban.setTenBan(resultSet.getString(2));
                ban.setTrangThai(resultSet.getBoolean(3));
                
                list.add(ban);
            }
            resultSet.getStatement().getConnection().close();
        } catch (SQLException ex) {
        }
        return list;
    }

    @Override
    public Ban selectById(String id) {
        List<Ban> list = this.selectBySql(selectByIdBan, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

}
