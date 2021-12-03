/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;


import java.awt.BorderLayout;
import javax.swing.border.EmptyBorder;
import java.awt.Component ;
import javax.swing.JOptionPane;
import model.BanJFrame;

/**
 *
 * @author admin
 */
public class MainForm extends javax.swing.JPanel {

    /**
     * Creates new form MainForm
     */
    public MainForm() {
        initComponents();
//       btnBan.setVisible(false);
//       btnSanPham.setVisible(false);
        
    }

    public void showForm(Component form) {
        //removeAll();
        add(form);
        repaint();
        revalidate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnThoat = new newpackage.ButtonRadius();
        btnDangXuat = new newpackage.ButtonRadius();
        jPanel3 = new javax.swing.JPanel();
        btnSanPham = new newpackage.ButtonRadius();
        btnBan = new newpackage.ButtonRadius();
        btnBanHang = new newpackage.ButtonRadius();
        btnHoaDon = new newpackage.ButtonRadius();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblDongHo = new javax.swing.JLabel();
        lblDongHo1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(81, 145, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 0, 3, 0, new java.awt.Color(81, 145, 255)));

        btnThoat.setBackground(new java.awt.Color(81, 145, 255));
        btnThoat.setBorder(null);
        btnThoat.setForeground(new java.awt.Color(255, 255, 255));
        btnThoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_shutdown_45px.png"))); // NOI18N
        btnThoat.setText("Thoát");
        btnThoat.setBorderColor(new java.awt.Color(81, 145, 255));
        btnThoat.setColor(new java.awt.Color(81, 145, 255));
        btnThoat.setColorClick(new java.awt.Color(121, 214, 255));
        btnThoat.setColorOver(new java.awt.Color(66, 117, 246));
        btnThoat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });

        btnDangXuat.setBackground(new java.awt.Color(81, 145, 255));
        btnDangXuat.setBorder(null);
        btnDangXuat.setForeground(new java.awt.Color(255, 255, 255));
        btnDangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Logout_45px.png"))); // NOI18N
        btnDangXuat.setText("Đăng xuất");
        btnDangXuat.setBorderColor(new java.awt.Color(81, 145, 255));
        btnDangXuat.setColor(new java.awt.Color(81, 145, 255));
        btnDangXuat.setColorClick(new java.awt.Color(121, 214, 255));
        btnDangXuat.setColorOver(new java.awt.Color(66, 117, 246));
        btnDangXuat.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 127, 26));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 5, 0, 0, new java.awt.Color(255, 255, 255)));
        jPanel3.setOpaque(false);

        btnSanPham.setBackground(new java.awt.Color(81, 145, 255));
        btnSanPham.setBorder(null);
        btnSanPham.setForeground(new java.awt.Color(255, 255, 255));
        btnSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_box_45px.png"))); // NOI18N
        btnSanPham.setText("Sản phẩm");
        btnSanPham.setBorderColor(new java.awt.Color(81, 145, 255));
        btnSanPham.setColor(new java.awt.Color(81, 145, 255));
        btnSanPham.setColorClick(new java.awt.Color(121, 214, 255));
        btnSanPham.setColorOver(new java.awt.Color(66, 117, 246));
        btnSanPham.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        btnBan.setBackground(new java.awt.Color(81, 145, 255));
        btnBan.setBorder(null);
        btnBan.setForeground(new java.awt.Color(255, 255, 255));
        btnBan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_table_45px.png"))); // NOI18N
        btnBan.setText("Bàn");
        btnBan.setBorderColor(new java.awt.Color(81, 145, 255));
        btnBan.setColor(new java.awt.Color(81, 145, 255));
        btnBan.setColorClick(new java.awt.Color(121, 214, 255));
        btnBan.setColorOver(new java.awt.Color(66, 117, 246));
        btnBan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnBan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBanMouseClicked(evt);
            }
        });

        btnBanHang.setBackground(new java.awt.Color(81, 145, 255));
        btnBanHang.setBorder(null);
        btnBanHang.setForeground(new java.awt.Color(255, 255, 255));
        btnBanHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_shopping_cart_45px.png"))); // NOI18N
        btnBanHang.setText("Bán hàng");
        btnBanHang.setBorderColor(new java.awt.Color(81, 145, 255));
        btnBanHang.setColor(new java.awt.Color(81, 145, 255));
        btnBanHang.setColorClick(new java.awt.Color(121, 214, 255));
        btnBanHang.setColorOver(new java.awt.Color(66, 117, 246));
        btnBanHang.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        btnHoaDon.setBackground(new java.awt.Color(81, 145, 255));
        btnHoaDon.setBorder(null);
        btnHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_test_passed_45px.png"))); // NOI18N
        btnHoaDon.setText("Hóa đơn");
        btnHoaDon.setBorderColor(new java.awt.Color(81, 145, 255));
        btnHoaDon.setColor(new java.awt.Color(81, 145, 255));
        btnHoaDon.setColorClick(new java.awt.Color(121, 214, 255));
        btnHoaDon.setColorOver(new java.awt.Color(66, 117, 246));
        btnHoaDon.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(btnSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnBan, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnBanHang, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnBanHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnDangXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnThoat, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
            .addComponent(btnDangXuat, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Logo2.png"))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(81, 145, 255));

        lblDongHo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblDongHo.setForeground(new java.awt.Color(255, 255, 255));
        lblDongHo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDongHo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_info_24px.png"))); // NOI18N
        lblDongHo.setText("Phần mềm quản lý quán cà phê");

        lblDongHo1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblDongHo1.setForeground(new java.awt.Color(255, 255, 255));
        lblDongHo1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDongHo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_clock_24px.png"))); // NOI18N
        lblDongHo1.setText("12:00 PM");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblDongHo, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblDongHo1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblDongHo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblDongHo1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThoatActionPerformed

    private void btnBanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBanMouseClicked
      new BanJFrame().setVisible(true);
    }//GEN-LAST:event_btnBanMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private newpackage.ButtonRadius btnBan;
    private newpackage.ButtonRadius btnBanHang;
    private newpackage.ButtonRadius btnDangXuat;
    private newpackage.ButtonRadius btnHoaDon;
    private newpackage.ButtonRadius btnSanPham;
    private newpackage.ButtonRadius btnThoat;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblDongHo;
    private javax.swing.JLabel lblDongHo1;
    // End of variables declaration//GEN-END:variables
}
