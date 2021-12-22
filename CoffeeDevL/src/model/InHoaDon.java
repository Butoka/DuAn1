/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.itextpdf.awt.AsianFontMapper;
import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.awt.DefaultFontMapper.BaseFontParameters;
import com.itextpdf.awt.FontMapper;
import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import dao.HoaDonCTDAO;
import dao.HoaDonDAO;
import helper.MsgBox;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.plaf.synth.SynthTableHeaderUI;
import javax.swing.plaf.synth.SynthTableUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import scrollbar.ScrollBarCustom;

/**
 *
 * @author admin
 */
public class InHoaDon extends javax.swing.JFrame {

    /**
     * Creates new form InHoaDon
     */
    public InHoaDon() {
        initComponents();
        nameCollum();
    }
    HoaDonDAO daoHD = new HoaDonDAO();
    HoaDonCTDAO daoHDCT = new HoaDonCTDAO();
    String maHD;
    String ngayBan;
    public static int bat = 0;

    public void InHoaDon(String maHD, String ngayBan, String thuNgan, String ban, String tongTien, String tongTienChu) {
        this.maHD = maHD;
        this.ngayBan = ngayBan;
        fillTableDanhSachSP(maHD);
        fillData(ngayBan, thuNgan, ban, tongTien, tongTienChu);

    }

    public void nameCollum() {
        panelBanHang2.getViewport().setOpaque(false);
        panelBanHang2.setVerticalScrollBar(new ScrollBarCustom());
        panelBanHang2.setViewportBorder(null);
        tblSanPham2.setRowSelectionAllowed(false);

        JTableHeader tableHeader = tblSanPham2.getTableHeader();
        tableHeader.setUI(new SynthTableHeaderUI());
        Font HeaderFont = new Font("SansSerif", Font.PLAIN, 18);
        tableHeader.setOpaque(false);
        tableHeader.setBackground(Color.gray);
        tableHeader.setForeground(Color.white);

        tableHeader.setFont(HeaderFont);
        tblSanPham2.setRowHeight(25);
    }

    public void inHD() {
        JFileChooser chooser = new JFileChooser("C:\\Users\\admin\\Desktop");

        File file = new File(maHD + "_" + ngayBan + ".pdf");
        chooser.setSelectedFile(file);
        int i = chooser.showSaveDialog(this);
        File f = chooser.getSelectedFile();
        com.itextpdf.text.Rectangle r = new com.itextpdf.text.Rectangle(0, 0, panelHD.getWidth(), panelHD.getHeight());
        Document document = new Document(r);
        if (i == JFileChooser.APPROVE_OPTION) {
            try {
                PdfWriter writer;
                writer = PdfWriter.getInstance(document, new FileOutputStream(f));
                document.open();
                PdfContentByte cb = writer.getDirectContent();

                PdfTemplate tp = cb.createTemplate(panelHD.getWidth(), panelHD.getHeight());
                Graphics2D g2d = new PdfGraphics2D(tp, panelHD.getWidth(), panelHD.getHeight(), new AsianFontMapper("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H));

                panelHD.addNotify();
                panelHD.validate();

                panelHD.paint(g2d);
                g2d.dispose();

//            cb.setFontAndSize(bfComic, 12);
                cb.addTemplate(tp, 0, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }

            document.close();
            MsgBox.alert(this, "Đã in hóa đơn thành công!");

        }
    }

    public void fillTableDanhSachSP(String maHD) {
        lblMaHD.setText("Mã HD: " + maHD);

        DefaultTableModel model = (DefaultTableModel) tblSanPham2.getModel();
        model.setRowCount(0);
        List<Object[]> list = daoHDCT.selectHDCT(maHD);
        int stt = 1;
        if (list != null) {

            for (Object[] rowData : list) {

                String donGia = getNum(rowData[2].toString());
                String giamGia = getNum(rowData[4].toString());
                String thanhTien = getNum(rowData[5].toString());
                Object[] data = new Object[]{stt++,
                    rowData[1], donGia, rowData[3], giamGia, thanhTien
                };
                model.addRow(data);
            }
        }

    }

    public void fillData(String ngayBan, String thuNgan, String ban, String tongTien, String tongTienChu) {
        lblNgayBan.setText("Ngày bán: " + ngayBan);
        lblThuNgan.setText("Thu ngân: " + thuNgan);
        lblBan.setText("Bàn: " + ban);
        lblTongTien.setText(tongTien);
        lblTongTienChu.setText(tongTienChu);
    }

    public String getNum(String num) {

        return num.substring(0, num.indexOf(".")) + " VND";
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelHD = new javax.swing.JPanel();
        lblHd = new javax.swing.JLabel();
        panelBanHang2 = new javax.swing.JScrollPane();
        tblSanPham2 = new javax.swing.JTable();
        lblBan = new javax.swing.JLabel();
        lblNgayBan = new javax.swing.JLabel();
        lblNgayBan1 = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();
        lblMaHD = new javax.swing.JLabel();
        lblThuNgan = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblNgayBan2 = new javax.swing.JLabel();
        lblTongTienChu = new javax.swing.JLabel();
        btnInHD = new newpackage.Button();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 3, 2, new java.awt.Color(102, 102, 102)));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Xem trước khi in");
        jLabel1.setOpaque(true);

        panelHD.setBackground(new java.awt.Color(255, 255, 255));

        lblHd.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        lblHd.setForeground(new java.awt.Color(102, 102, 102));
        lblHd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHd.setText("HÓA ĐƠN BÁN HÀNG");

        panelBanHang2.setBackground(new java.awt.Color(255, 255, 255));
        panelBanHang2.setBorder(null);

        tblSanPham2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        tblSanPham2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblSanPham2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "Co ca cola", "10 000", "10", "1000", "9000"},
                {"2", "Cà phê đen đá", "6000", "2", "1000", "11000"},
                {"3", "Trà đào", "12000", "1", "0", "12000"}
            },
            new String [] {
                "STT", "Tên sản phẩm", "Đơn giá", "Số lượng", "Giảm giá", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham2.setFocusable(false);
        tblSanPham2.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblSanPham2.setRowHeight(30);
        tblSanPham2.setSelectionBackground(new java.awt.Color(255, 255, 255));
        tblSanPham2.setSelectionForeground(new java.awt.Color(0, 0, 0));
        panelBanHang2.setViewportView(tblSanPham2);
        if (tblSanPham2.getColumnModel().getColumnCount() > 0) {
            tblSanPham2.getColumnModel().getColumn(0).setResizable(false);
            tblSanPham2.getColumnModel().getColumn(0).setPreferredWidth(45);
            tblSanPham2.getColumnModel().getColumn(1).setResizable(false);
            tblSanPham2.getColumnModel().getColumn(2).setResizable(false);
            tblSanPham2.getColumnModel().getColumn(3).setResizable(false);
            tblSanPham2.getColumnModel().getColumn(3).setPreferredWidth(80);
            tblSanPham2.getColumnModel().getColumn(4).setResizable(false);
            tblSanPham2.getColumnModel().getColumn(5).setResizable(false);
        }

        lblBan.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblBan.setText("Bàn:");

        lblNgayBan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblNgayBan.setText("Ngày bán :");

        lblNgayBan1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblNgayBan1.setForeground(new java.awt.Color(255, 0, 0));
        lblNgayBan1.setText("Tổng cộng:");

        lblTongTien.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTongTien.setForeground(new java.awt.Color(255, 0, 0));
        lblTongTien.setText("20000 VND");

        lblMaHD.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMaHD.setText("Mã HD:");

        lblThuNgan.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblThuNgan.setText("Thu ngân:");

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Xin hẹn gặp lại !");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Chân thành cảm ơn quý khách");

        lblNgayBan2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblNgayBan2.setForeground(new java.awt.Color(255, 0, 0));
        lblNgayBan2.setText("Bằng chữ:");

        lblTongTienChu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTongTienChu.setForeground(new java.awt.Color(255, 0, 0));
        lblTongTienChu.setText("Hai mươi nghìn VND");

        javax.swing.GroupLayout panelHDLayout = new javax.swing.GroupLayout(panelHD);
        panelHD.setLayout(panelHDLayout);
        panelHDLayout.setHorizontalGroup(
            panelHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelHDLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(280, 280, 280))
            .addGroup(panelHDLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(panelHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelHDLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(panelHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNgayBan1)
                            .addComponent(lblNgayBan2))
                        .addGap(18, 18, 18)
                        .addGroup(panelHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTongTienChu))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelHDLayout.createSequentialGroup()
                        .addGroup(panelHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelHDLayout.createSequentialGroup()
                                .addComponent(lblMaHD)
                                .addGap(146, 146, 146)
                                .addComponent(lblBan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblThuNgan))
                            .addGroup(panelHDLayout.createSequentialGroup()
                                .addGap(151, 151, 151)
                                .addComponent(lblHd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(151, 151, 151))
                    .addGroup(panelHDLayout.createSequentialGroup()
                        .addGroup(panelHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelBanHang2, javax.swing.GroupLayout.PREFERRED_SIZE, 798, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNgayBan))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        panelHDLayout.setVerticalGroup(
            panelHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHDLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(lblNgayBan)
                .addGap(35, 35, 35)
                .addComponent(lblHd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(38, 38, 38)
                .addGroup(panelHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBan)
                    .addComponent(lblMaHD)
                    .addComponent(lblThuNgan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelBanHang2, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNgayBan1)
                    .addComponent(lblTongTien))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNgayBan2)
                    .addComponent(lblTongTienChu))
                .addGap(26, 26, 26)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(26, 26, 26))
        );

        btnInHD.setBackground(new java.awt.Color(102, 102, 102));
        btnInHD.setForeground(new java.awt.Color(255, 255, 255));
        btnInHD.setText("In hóa đơn");
        btnInHD.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnInHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInHDActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("X");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnInHD, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnInHD, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnInHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInHDActionPerformed
        inHD();
        bat = 1;
    }//GEN-LAST:event_btnInHDActionPerformed

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        this.dispose();
    }//GEN-LAST:event_jLabel4MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InHoaDon().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private newpackage.Button btnInHD;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblBan;
    private javax.swing.JLabel lblHd;
    private javax.swing.JLabel lblMaHD;
    private javax.swing.JLabel lblNgayBan;
    private javax.swing.JLabel lblNgayBan1;
    private javax.swing.JLabel lblNgayBan2;
    private javax.swing.JLabel lblThuNgan;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JLabel lblTongTienChu;
    private javax.swing.JScrollPane panelBanHang2;
    private javax.swing.JPanel panelHD;
    private javax.swing.JTable tblSanPham2;
    // End of variables declaration//GEN-END:variables
}
