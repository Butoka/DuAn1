/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import component.Header;
import net.miginfocom.swing.MigLayout;
import component.Menu;
import dao.NguoiDungDAO;
import entity.NguoiDung;
import event.EventMenuSelected;
import event.EventShowPopupMenu;
import form.*;
import helper.Auth;
import helper.MsgBox;
import swing.MenuItem;
import swing.PopupMenu;
import java.awt.Component;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.*;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

/**
 *
 * @author admin
 */
public class ManHinhChinh extends javax.swing.JFrame {

    private MigLayout layout;
    private Menu menu;
    private Header header;
    private MainForm main;
    private Animator animator;
    int mpX, mpY;

    public ManHinhChinh() {
        initComponents();
        init();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    ManHinhChinh man = this;

    public void init() {
        layout = new MigLayout("fill", "0[]0[100%, fill]0", "0[fill, top]0");
        bg.setLayout(layout);
        menu = new Menu();
        header = new Header();
        main = new MainForm();

        menu.addEvent(new EventMenuSelected() {
            @Override
            public void menuSelected(int menuIndex, int subMenuIndex) {
                // System.out.println("Menu Index: " + menuIndex + " SubMenu Index: " + subMenuIndex);
                if (Auth.isLogin()) {
                    if (Auth.getManager().equalsIgnoreCase("admin")) {
                        menuAdmin(menuIndex, subMenuIndex);
                    } else if (Auth.getManager().equalsIgnoreCase("Quản lý")) {
                        menuQuanLy(menuIndex, subMenuIndex);
                    } else {
                        menuThuNgan(menuIndex, subMenuIndex);
                    }
                } else if (!Auth.isLogin()) {
                    MsgBox.alert(man, "Bạn chưa đăng nhập");
                }
            }
        });

        menu.addEventShow(new EventShowPopupMenu() {
            @Override
            public void showPopup(Component com) {
                MenuItem item = (MenuItem) com;
                PopupMenu pop = new PopupMenu(ManHinhChinh.this, item.getIndex(), item.getEvetS(), item.getMenu().getSubMenu());
                int x = ManHinhChinh.this.getX() + 52;
                int y = ManHinhChinh.this.getY() + com.getY() + 86;
                pop.setLocation(x, y);
                pop.setVisible(true);
            }
        });
        menu.initMenuItem();

        bg.add(menu, "w 230!,spany 2");
        bg.add(header, "h 50!,wrap");
        bg.add(main, "w 100%, h 100%");

        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                double width;
                if (menu.isShowMenu()) {
                    width = 60 + (170 * (1f - fraction));
                } else {
                    width = 60 + (170 * fraction);
                }
                layout.setComponentConstraints(menu, "w " + width + "!, spany 2");
                menu.revalidate();
            }

            @Override
            public void end() {
                menu.setShowMenu(!menu.isShowMenu());
                menu.setEnableMenu(true);
            }

        };
        animator = new Animator(500, target);
        animator.setResolution(0);
        animator.setDeceleration(0.5f);
        animator.setAcceleration(0.5f);
        header.addMenuEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!animator.isRunning()) {
                    animator.start();
                }
                menu.setEnableMenu(false);
                if (menu.isShowMenu()) {
                    menu.hidealMenu();
                }
            }
        });

        main.addEvent(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int dem = main.dem;
                if (dem == 0) {
                    MsgBox.exit(man);
                } else if (dem == 1) {
                    boolean chon = MsgBox.confirm(man, "Bạn chắc chắn muốn đăng xuất !?");
                    if (chon) {
                        man.dispose();
                        Auth.clear();
                        new DangNhapMain().setVisible(true);
                    }
                } else if (dem == 11) {
                    boolean chon = MsgBox.confirm(man, "Bạn chưa đăng nhập !?\nBạn có muốn đăng nhập vào phần mềm không ?");
                    if (chon) {
                        man.dispose();
                        Auth.clear();
                        new DangNhapMain().setVisible(true);
                    }
                }
                if (dem == -1) {
                    MsgBox.alert(man, "Bạn chưa đăng nhập");
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });

//        main.showForm(new MainForm());
    }

    public void menuAdmin(int menuIndex, int subMenuIndex) {
        if (menuIndex == 0) {
            if (subMenuIndex == 0) {
                ThongKe tk = new ThongKe();
                tk.setVisible(true);
                tk.selectTab(0);

            } else if (subMenuIndex == 1) {
                ThongKe tk = new ThongKe();
                tk.setVisible(true);
                tk.selectTab(1);
            } else if (subMenuIndex == 2) {
                ThongKe tk = new ThongKe();
                tk.setVisible(true);
                tk.selectTab(2);
            }

        } else if (menuIndex == 1) {
            if (subMenuIndex == 0) {
                new SanPhamJFrame().setVisible(true);
            } else if (subMenuIndex == 1) {
                new LoaiSanPhamJFrame().setVisible(true);
            }
        } else if (menuIndex == 2) {
            new BanJFrame().setVisible(true);
        } else if (menuIndex == 3) {
            new BanHangJFrame().setVisible(true);
            //MsgBox.alert(this, "Chức năng đang cập nhật ....");
        } else if (menuIndex == 4) {
            new HoaDonJFrame().setVisible(true);
        } else if (menuIndex == 5) {
            if (subMenuIndex == 0) {
                new NhanVienJFrame().setVisible(true);
            } else if (subMenuIndex == 1) {
                new CaLamViecJFrame().setVisible(true);
            } else if (subMenuIndex == 2) {
                Luong luong = new Luong();
                luong.setVisible(true);
                luong.selectTab(0);
            } else if (subMenuIndex == 3) {
                Luong luong = new Luong();
                luong.setVisible(true);
                luong.selectTab(1);
            }

        } else if (menuIndex == 6) {
            new NguoiDungJFrame().setVisible(true);
        } else if (menuIndex == 7) {
            guiMail();

        } else if (menuIndex == 8) {
            boolean chon = MsgBox.confirm(man, "Bạn chắc chắn muốn đăng xuất !?");
            if (chon) {
                man.dispose();
                Auth.clear();
                new DangNhapMain().setVisible(true);
            }
        } else if (menuIndex == 9) {
            MsgBox.exit(man);

        }

    }

    public void menuQuanLy(int menuIndex, int subMenuIndex) {
        if (menuIndex == 0) {
            if (subMenuIndex == 0) {
                ThongKe tk = new ThongKe();
                tk.setVisible(true);
                tk.selectTab(0);

            } else if (subMenuIndex == 1) {
                ThongKe tk = new ThongKe();
                tk.setVisible(true);
                tk.selectTab(1);
            } else if (subMenuIndex == 2) {
                ThongKe tk = new ThongKe();
                tk.setVisible(true);
                tk.selectTab(2);
            }

        } else if (menuIndex == 1) {
            if (subMenuIndex == 0) {
                new SanPhamJFrame().setVisible(true);
            } else if (subMenuIndex == 1) {
                new LoaiSanPhamJFrame().setVisible(true);
            }
        } else if (menuIndex == 2) {
            new BanJFrame().setVisible(true);
        } else if (menuIndex == 3) {
            new BanHangJFrame().setVisible(true);
//           MsgBox.alert(this,"Chức năng đang cập nhật ....");
        } else if (menuIndex == 4) {
            new HoaDonJFrame().setVisible(true);
        } else if (menuIndex == 5) {
            if (subMenuIndex == 0) {
                new NhanVienJFrame().setVisible(true);
            } else if (subMenuIndex == 1) {
                new CaLamViecJFrame().setVisible(true);
            } else if (subMenuIndex == 2) {
                Luong luong = new Luong();
                luong.setVisible(true);
                luong.selectTab(0);
            } else if (subMenuIndex == 3) {
                Luong luong = new Luong();
                luong.setVisible(true);
                luong.selectTab(1);
            }

        } else if (menuIndex == 6) {
            guiMail();

        } else if (menuIndex == 7) {
            boolean chon = MsgBox.confirm(man, "Bạn chắc chắn muốn đăng xuất !?");
            if (chon) {
                man.dispose();
                Auth.clear();
                new DangNhapMain().setVisible(true);
            }
        } else if (menuIndex == 8) {
            MsgBox.exit(man);

        }

    }

    public void menuThuNgan(int menuIndex, int subMenuIndex) {
        if (menuIndex == 0) {
            new BanHangJFrame().setVisible(true);
            // MsgBox.alert(this,"Chức năng đang cập nhật ....");
        } else if (menuIndex == 1) {
            new HoaDonJFrame().setVisible(true);
        } else if (menuIndex == 2) {
            guiMail();
        } else if (menuIndex == 3) {
            boolean chon = MsgBox.confirm(man, "Bạn chắc chắn muốn đăng xuất !?");
            if (chon) {
                man.dispose();
                Auth.clear();
                new DangNhapMain().setVisible(true);
            }
        } else if (menuIndex == 4) {
            MsgBox.exit(man);

        }

    }

    public void guiMail() {
        try {
            String taiKhoan = "coffeedevl2@gmail.com";
            String matKhau = "linhvln240802";

            Properties prop = new Properties();
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "587");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(taiKhoan, matKhau);
                }
            });

            String from = taiKhoan;
            String to = MsgBox.prompt(this, "Nhập địa chỉ email của bạn !?");
            String subject = "CoffeeDevL - Mã xác nhận ";
            String maXacNhan = randomMa(6);
            String body = "Mã xác nhận của bạn là : " + maXacNhan;
//            if (to.equals("")) {
//                MsgBox.alert(this, "Không được bỏ trống !");
//
//            }

            if (to != null) {

                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.setRecipients(
                        Message.RecipientType.TO,
                        InternetAddress.parse(to)
                );
                message.setSubject(subject);
                message.setText(body);

                boolean chon1 = MsgBox.confirm(this, "Để đổi mật khẩu hệ thống sẽ \nGửi mã xác nhận đến email của bạn");
                if (chon1 == true) {
                    Transport.send(message);

                    JOptionPane.showMessageDialog(this, "Đã gửi mail thành công");

                    String chon = MsgBox.prompt(this, "Vui lòng nhập mã xác nhận");
                    if (chon != null) {
                        if (chon.equals(maXacNhan)) {
                            MsgBox.alert(this, "Đúng!");
                            new DoiMatKhau().setVisible(true);
                        } else {
                            MsgBox.alert(this, "Mã xác nhận ko đúng");

                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }

    }

    private static final String digits = "0123456789"; // 0-9
    private static final String ALPHA_NUMERIC = digits;
    private static Random generator = new Random();

    public String randomMa(int soKyTu) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < soKyTu; i++) {
            int number = randomNumber(0, ALPHA_NUMERIC.length() - 1);
            char ch = ALPHA_NUMERIC.charAt(number);
            sb.append(ch);
        }
        return sb.toString();
    }

    public static int randomNumber(int min, int max) {
        return generator.nextInt((max - min) + 1) + min;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JLayeredPane();
        pnlTitleBar = new javax.swing.JPanel();
        lblExit = new javax.swing.JLabel();
        lblMaxi = new javax.swing.JLabel();
        lblMini = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImages(null);
        setUndecorated(true);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 5, new java.awt.Color(52, 127, 255)));
        bg.setOpaque(true);

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1375, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 768, Short.MAX_VALUE)
        );

        pnlTitleBar.setBackground(new java.awt.Color(52, 127, 255));
        pnlTitleBar.setPreferredSize(new java.awt.Dimension(1380, 32));
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

        lblExit.setBackground(new java.awt.Color(52, 127, 255));
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

        lblMaxi.setBackground(new java.awt.Color(52, 127, 255));
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

        lblMini.setBackground(new java.awt.Color(52, 127, 255));
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
                .addGap(0, 1270, Short.MAX_VALUE)
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg)
            .addComponent(pnlTitleBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pnlTitleBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(bg))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lblExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMouseClicked
        MsgBox.exit(this);
    }//GEN-LAST:event_lblExitMouseClicked

    private void lblExitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMouseEntered
        // TODO add your handling code here:
        lblExit.setBackground(Color.red);
    }//GEN-LAST:event_lblExitMouseEntered

    private void lblExitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMouseExited
        // TODO add your handling code here:
        lblExit.setBackground(new Color(52, 127, 255));
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
        lblMaxi.setBackground(new Color(52, 127, 255));
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
        lblMini.setBackground(new Color(52, 127, 255));
    }//GEN-LAST:event_lblMiniMouseExited

    private void pnlTitleBarMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTitleBarMouseDragged
        // TODO add your handling code here:

        if (this.getExtendedState() == JFrame.MAXIMIZED_BOTH) {

        } else {
            this.setLocation(
                    getLocation().x + evt.getX() - mpX,
                    getLocation().y + evt.getY() - mpY);
        }
    }//GEN-LAST:event_pnlTitleBarMouseDragged

    private void pnlTitleBarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTitleBarMousePressed
        // TODO add your handling code hee:
        mpX = evt.getX();
        mpY = evt.getY();
    }//GEN-LAST:event_pnlTitleBarMousePressed

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
            java.util.logging.Logger.getLogger(ManHinhChinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManHinhChinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManHinhChinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManHinhChinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManHinhChinh().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane bg;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblMaxi;
    private javax.swing.JLabel lblMini;
    private javax.swing.JPanel pnlTitleBar;
    // End of variables declaration//GEN-END:variables
}
