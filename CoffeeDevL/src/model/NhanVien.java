/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author admin
 */
import java.awt.*;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class NhanVien extends javax.swing.JFrame {

    /**
     * Creates new form NhanVienJFrame
     */
    public NhanVien() {
        initComponents();
    }
    int mpX, mpY;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlTitleBar = new javax.swing.JPanel();
        lblExit = new javax.swing.JLabel();
        lblMaxi = new javax.swing.JLabel();
        lblMini = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        pnlTitleBar.setBackground(new java.awt.Color(219, 106, 50));
        pnlTitleBar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlTitleBarMouseDragged(evt);
            }
        });
        pnlTitleBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlTitleBarMousePressed(evt);
            }
        });

        lblExit.setBackground(new java.awt.Color(219, 106, 50));
        lblExit.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblExit.setForeground(new java.awt.Color(255, 255, 255));
        lblExit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/x_icon.png"))); // NOI18N
        lblExit.setOpaque(true);
        lblExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblExitMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblExitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblExitMouseExited(evt);
            }
        });

        lblMaxi.setBackground(new java.awt.Color(219, 106, 50));
        lblMaxi.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMaxi.setForeground(new java.awt.Color(255, 255, 255));
        lblMaxi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMaxi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/square_icon.png"))); // NOI18N
        lblMaxi.setOpaque(true);
        lblMaxi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMaxiMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblMaxiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblMaxiMouseExited(evt);
            }
        });

        lblMini.setBackground(new java.awt.Color(219, 106, 50));
        lblMini.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMini.setForeground(new java.awt.Color(255, 255, 255));
        lblMini.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMini.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/minus_icon.png"))); // NOI18N
        lblMini.setOpaque(true);
        lblMini.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMiniMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblMiniMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblMiniMouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnlTitleBarLayout = new javax.swing.GroupLayout(pnlTitleBar);
        pnlTitleBar.setLayout(pnlTitleBarLayout);
        pnlTitleBarLayout.setHorizontalGroup(
            pnlTitleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTitleBarLayout.createSequentialGroup()
                .addGap(0, 766, Short.MAX_VALUE)
                .addComponent(lblMini)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMaxi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblExit))
        );
        pnlTitleBarLayout.setVerticalGroup(
            pnlTitleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblMaxi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblMini, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 113, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 245, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTitleBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlTitleBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lblExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMouseClicked
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_lblExitMouseClicked

    private void lblExitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMouseEntered
        // TODO add your handling code here:
        lblExit.setBackground(Color.red);
    }//GEN-LAST:event_lblExitMouseEntered

    private void lblExitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMouseExited
        // TODO add your handling code here:
        lblExit.setBackground(new Color(219, 106, 50));
    }//GEN-LAST:event_lblExitMouseExited

    private void lblMaxiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMaxiMouseClicked
        // TODO add your handling code here:
        Insets screenInsets = getToolkit().getScreenInsets(getGraphicsConfiguration());
        Rectangle screenSize = getGraphicsConfiguration().getBounds();
        Rectangle maxBounds = new Rectangle(screenInsets.left + screenSize.x,
                screenInsets.top + screenSize.y,
                screenSize.x + screenSize.width - screenInsets.right - screenInsets.left,
                screenSize.y + screenSize.height - screenInsets.bottom - screenInsets.top);
        super.setMaximizedBounds(maxBounds);
        if (this.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
            lblMaxi.setIcon(new ImageIcon("src\\icon\\square_icon.png"));
            this.setExtendedState(JFrame.NORMAL);
        } else {
            lblMaxi.setIcon(new ImageIcon("src\\icon\\restore_down_icon.png"));
            this.setExtendedState(MAXIMIZED_BOTH);
        }
    }//GEN-LAST:event_lblMaxiMouseClicked

    private void lblMaxiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMaxiMouseEntered
        // TODO add your handling code here:
        lblMaxi.setBackground(Color.lightGray);
    }//GEN-LAST:event_lblMaxiMouseEntered

    private void lblMaxiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMaxiMouseExited
        // TODO add your handling code here:
        lblMaxi.setBackground(new Color(219, 106, 50));
    }//GEN-LAST:event_lblMaxiMouseExited

    private void lblMiniMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMiniMouseClicked
        // TODO add your handling code here:
        this.setState(Frame.ICONIFIED);
    }//GEN-LAST:event_lblMiniMouseClicked

    private void lblMiniMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMiniMouseEntered
        // TODO add your handling code here:
        lblMini.setBackground(Color.lightGray);
    }//GEN-LAST:event_lblMiniMouseEntered

    private void lblMiniMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMiniMouseExited
        // TODO add your handling code here:
        lblMini.setBackground(new Color(219, 106, 50));
    }//GEN-LAST:event_lblMiniMouseExited

    private void pnlTitleBarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTitleBarMousePressed
        // TODO add your handling code hee:
        mpX = evt.getX();
        mpY = evt.getY();
    }//GEN-LAST:event_pnlTitleBarMousePressed

    private void pnlTitleBarMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTitleBarMouseDragged
        // TODO add your handling code here:
        this.setLocation(
                getLocation().x + evt.getX() - mpX,
                getLocation().y + evt.getY() - mpY);
    }//GEN-LAST:event_pnlTitleBarMouseDragged

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
            java.util.logging.Logger.getLogger(NhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NhanVien().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblMaxi;
    private javax.swing.JLabel lblMini;
    private javax.swing.JPanel pnlTitleBar;
    // End of variables declaration//GEN-END:variables
}
