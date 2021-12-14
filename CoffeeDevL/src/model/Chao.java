/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import helper.MsgBox;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicProgressBarUI;

/**
 *
 * @author admin
 */
public class Chao extends javax.swing.JFrame {
    
    Timer thoiGian;
    int x = 0, y = 0;

    public Chao() {
        initComponents();
        chay();
        prgLoad.setUI(new BasicProgressBarUI());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
    }
    
    public void chay() {
        Chao ch = this;
        thoiGian = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int giaTriHienTai = prgLoad.getValue();
                
                prgLoad.setValue(giaTriHienTai += 1);
                lblHienThi.setText(prgLoad.getValue() + "%");
                
                if (prgLoad.getValue() == 100) {
                    thoiGian.stop();
                    ch.dispose();
                    new DangNhapMain().setVisible(true);
                    
                }
            }
        });
        thoiGian.start();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        prgLoad = new javax.swing.JProgressBar();
        lblLoading = new javax.swing.JLabel();
        lblHienThi = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblExit = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        prgLoad.setBackground(new java.awt.Color(81, 145, 255));
        prgLoad.setForeground(new java.awt.Color(51, 0, 255));
        jPanel1.add(prgLoad, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, 1010, 10));

        lblLoading.setBackground(new java.awt.Color(213, 157, 83));
        lblLoading.setFont(new java.awt.Font("Tahoma", 0, 26)); // NOI18N
        lblLoading.setForeground(new java.awt.Color(102, 102, 102));
        lblLoading.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblLoading.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/loading.gif"))); // NOI18N
        lblLoading.setText("Loading ");
        jPanel1.add(lblLoading, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 440, 139, -1));

        lblHienThi.setBackground(new java.awt.Color(213, 157, 83));
        lblHienThi.setFont(new java.awt.Font("Tahoma", 0, 26)); // NOI18N
        lblHienThi.setForeground(new java.awt.Color(102, 102, 102));
        lblHienThi.setText("100%");
        jPanel1.add(lblHienThi, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 440, 72, -1));

        jLabel7.setBackground(new java.awt.Color(81, 145, 255));
        jLabel7.setFont(new java.awt.Font("VNI-Butlong", 1, 72)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Coffee DevL");
        jLabel7.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 0, 30, 2, new java.awt.Color(255, 255, 255)));
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel7.setOpaque(true);
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 780, 230));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/coffeelogo.png"))); // NOI18N
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 210, 270, 230));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Logo.png"))); // NOI18N
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, -1, 120));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/nen.png"))); // NOI18N
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 70, 300, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("PHẦN MỀM QUẢN LÝ QUÁN CÀ PHÊ ");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 430, 580, 50));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/coffeeBackground2.png"))); // NOI18N
        jLabel2.setText("jLabel2");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, 420, 1030, 70));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/coffeeBackground.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 1010, -1));

        jPanel2.setBackground(new java.awt.Color(81, 145, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        lblExit.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblExit.setForeground(new java.awt.Color(255, 255, 255));
        lblExit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/x_icon.png"))); // NOI18N
        lblExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblExitMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 978, Short.MAX_VALUE)
                .addComponent(lblExit))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblExit, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -4, 1010, 50));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lblExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMouseClicked
        MsgBox.exit(this);
    }//GEN-LAST:event_lblExitMouseClicked

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        x = evt.getX();
        y = evt.getY();
        
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        setLocation(evt.getXOnScreen() - x, evt.getYOnScreen() - y);
    }//GEN-LAST:event_formMouseDragged

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
            java.util.logging.Logger.getLogger(Chao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Chao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Chao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Chao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Chao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblHienThi;
    private javax.swing.JLabel lblLoading;
    private javax.swing.JProgressBar prgLoad;
    // End of variables declaration//GEN-END:variables
}
