/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dao.NguoiDungDAO;
import dao.NhanVienDAO;
import entity.*;
import helper.Auth;
import helper.MsgBox;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import main.ManHinhChinh;

/**
 *
 * @author admin
 */
public class PanelDN extends javax.swing.JPanel {

    public PanelDN() {
        initComponents();
        login.setVisible(true);
        Pass.setVisible(false);
        //lblBatLoi.setVisible(false);
    }
    NguoiDungDAO dao = new NguoiDungDAO();
    static String username, hoTen, ChucVu;
    static int cuaso = 0;
    private ActionListener event;

    public void addEvent(ActionListener event) {
        this.event = event;
    }

    public boolean checkDangNhap() {
        String tenDangNhap = txtUserName.getText().trim();
        String matKhau = pswPass.getText();
        NguoiDung nd = dao.selectById(tenDangNhap);
        if (nd != null) {    //nếu manv đúng

            String matKhau2 = nd.getMatKhau();

            if (!matKhau.equals(matKhau2)) {  //nếu mật khẩu đúng
                cuaso = 2;
            } else {

                if (nd.getTrangThai().equals("1")) {
                    Auth.user = nd;
                    cuaso = 1;
                } else {
                    cuaso = 4;
                }
                // System.out.println(Auth.getManager());

            }
        } else {
            cuaso = 3;

        }
        if (tenDangNhap.isEmpty() && matKhau.isEmpty()) {
            lblCheckUser.setText("Vui lòng nhập tên đăng nhập *");
            lblCheckPass.setText("Vui lòng nhập mật khẩu *");
            return false;
        }
        if (tenDangNhap.isEmpty()) {
            lblCheckUser.setText("Vui lòng nhập tên đăng nhập *");
            return false;
        } else if (!tenDangNhap.isEmpty()) {
            if (cuaso == 3) {
                lblCheckUser.setText("Tài khoản không tồn tại! *");
                lblCheckPass.setText("");
                return false;
            }
             if (cuaso == 4) {
                lblCheckUser.setText("Tài khoản này đã ngừng hoạt động! *");
                lblCheckPass.setText("");
                return false;
            }
            lblCheckUser.setText("");

        }
        if (matKhau.isEmpty()) {
            lblCheckPass.setText("Vui lòng nhập mật khẩu *");
            return false;
        } else if (!matKhau.isEmpty()) {
            if (cuaso == 2) {
                lblCheckPass.setText("Mật khẩu sai ! *");
                return false;

            }
            lblCheckPass.setText("");

        }
        return true;

    }

    public boolean checkQuenMatKhau() {
        cuaso = 0;
        String tenDangNhap = txtTenDangNhap.getText();
        String email = txtEmail.getText();
        NguoiDung nd = dao.selectById(tenDangNhap);
        String dinhdangGmail = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "gmail+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        String dinhdangFpt = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "fpt+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if (tenDangNhap.isEmpty() && email.isEmpty()) {
            lblCheckTenDangNhap.setText("Vui lòng nhập tên đăng nhập ! *");
            lblCheckEmail.setText("Vui lòng nhập Email của bạn ! *");
            return false;
        }
        if (tenDangNhap.isEmpty()) {
            lblCheckTenDangNhap.setText("Vui lòng nhập tên đăng nhập ! *");

            return false;
        } else if (!tenDangNhap.isEmpty()) {
            if (nd == null) {
                lblCheckTenDangNhap.setText("Tài khoản không tồn tại!! *");
                lblCheckEmail.setText("");
                return false;
            }
            if (!nd.getTrangThai().equals("1")) 
            {
                lblCheckTenDangNhap.setText("Tài khoản này đã ngừng hoạt động!*");
                lblCheckEmail.setText("");
                return false;
            }
            lblCheckTenDangNhap.setText("");

        }
        if (email.isEmpty()) {
            lblCheckEmail.setText("Vui lòng nhập Email của bạn ! *");
            return false;
        } else if (!email.isEmpty()) {

            if (email.matches(dinhdangGmail) || email.matches(dinhdangFpt)) {
                lblCheckEmail.setText("");
            } else {
                lblCheckEmail.setText("Định dạng email không hợp lệ ! *");
                return false;
            }

        }

        return true;
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
            DangNhapMain dn = new DangNhapMain();
            String from = taiKhoan;
            String tenND = txtTenDangNhap.getText();
            String to = txtEmail.getText();
            String subject = "CoffeeDevL - Mã xác nhận ";
            String maXacNhan = randomMa(6);
            String body = "Mã xác nhận của bạn là : " + maXacNhan;

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(to)
            );
            message.setSubject(subject);
            message.setText(body);

            boolean chon1 = MsgBox.confirm(dn, "Gửi mã xác nhận vào email của bạn");
            if (chon1 == true) {
                Transport.send(message);

                MsgBox.alert(dn, "Đã gửi mail thành công");

                String chon = MsgBox.prompt(dn, "Vui lòng nhập mã xác nhận");
                if (chon != null) {
                    if (chon.equals(maXacNhan)) {
                        MsgBox.alert(dn, "Đúng!");
                        NguoiDung nd = dao.selectById(txtTenDangNhap.getText());

                        if (nd != null) {
                            subject = "CoffeeDevL - Mật khẩu của tài khoản " + tenND;
                            message.setSubject(subject);
                            message.setText("Mật khẩu của bạn là: " + nd.getMatKhau());
                            Transport.send(message);

                        }
                    } else {
                        MsgBox.alert(dn, "Mã xác nhận ko đúng");

                    }
                }
            } else {
                cuaso = 0;
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

    public void resignShow(boolean show) {
        if (show) {
            Pass.setVisible(false);
            login.setVisible(true);
        } else {
            login.setVisible(false);
            Pass.setVisible(true);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        login = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtUserName = new TextAndPassFiled.MyTextField();
        pswPass = new TextAndPassFiled.MyPasswordField();
        btnLogin = new newpackage.Button();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblCheckUser = new javax.swing.JLabel();
        lblCheckPass = new javax.swing.JLabel();
        Pass = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtTenDangNhap = new TextAndPassFiled.MyTextField();
        btnQuenPass = new newpackage.Button();
        txtEmail = new TextAndPassFiled.MyTextField();
        lblCheckTenDangNhap = new javax.swing.JLabel();
        lblCheckEmail = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 51, 0));
        setLayout(new java.awt.CardLayout());

        login.setBackground(new java.awt.Color(255, 255, 255));
        login.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(242, 242, 242));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ĐĂNG NHẬP");
        login.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 520, 77));

        txtUserName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtUserName.setColor(new java.awt.Color(255, 255, 255));
        txtUserName.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtUserName.setFont(new java.awt.Font("sansserif", 0, 20)); // NOI18N
        txtUserName.setHint("Tên đăng nhập *");
        txtUserName.setSelectionColor(new java.awt.Color(0, 51, 204));
        login.add(txtUserName, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 220, 323, 35));

        pswPass.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        pswPass.setColor(new java.awt.Color(255, 255, 255));
        pswPass.setFont(new java.awt.Font("sansserif", 0, 20)); // NOI18N
        pswPass.setHint("Mật khẩu *");
        pswPass.setSelectionColor(new java.awt.Color(0, 51, 204));
        login.add(pswPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 330, 323, 34));

        btnLogin.setBackground(new java.awt.Color(51, 102, 255));
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("Đăng nhập");
        btnLogin.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnLogin.setMaximumSize(new java.awt.Dimension(100, 3));
        btnLogin.setMinimumSize(new java.awt.Dimension(100, 3));
        btnLogin.setPreferredSize(new java.awt.Dimension(100, 3));
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        login.add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 450, 362, 60));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_password_32px.png"))); // NOI18N
        login.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 330, 41, 34));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_user_32px_1.png"))); // NOI18N
        login.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 220, 41, 35));

        lblCheckUser.setBackground(new java.awt.Color(255, 0, 0));
        lblCheckUser.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblCheckUser.setForeground(new java.awt.Color(252, 83, 117));
        login.add(lblCheckUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 260, 323, 27));

        lblCheckPass.setBackground(new java.awt.Color(255, 0, 0));
        lblCheckPass.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblCheckPass.setForeground(new java.awt.Color(252, 83, 117));
        login.add(lblCheckPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 380, 323, 25));

        add(login, "card3");

        Pass.setBackground(new java.awt.Color(255, 255, 255));
        Pass.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 204, 204));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("QUÊN MẬT KHẨU ?");
        Pass.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 511, 70));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_envelope_32px.png"))); // NOI18N
        Pass.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 320, 35, 43));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_user_32px_1.png"))); // NOI18N
        Pass.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 210, 35, 43));

        txtTenDangNhap.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtTenDangNhap.setColor(new java.awt.Color(255, 255, 255));
        txtTenDangNhap.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtTenDangNhap.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        txtTenDangNhap.setHint("Tên đăng nhập *");
        txtTenDangNhap.setSelectionColor(new java.awt.Color(83, 255, 150));
        Pass.add(txtTenDangNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 210, 331, 43));

        btnQuenPass.setBackground(new java.awt.Color(0, 153, 153));
        btnQuenPass.setForeground(new java.awt.Color(255, 255, 255));
        btnQuenPass.setText("Lấy lại mật khẩu");
        btnQuenPass.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnQuenPass.setMaximumSize(new java.awt.Dimension(100, 3));
        btnQuenPass.setMinimumSize(new java.awt.Dimension(100, 3));
        btnQuenPass.setPreferredSize(new java.awt.Dimension(100, 3));
        btnQuenPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuenPassActionPerformed(evt);
            }
        });
        Pass.add(btnQuenPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 460, 362, 60));

        txtEmail.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtEmail.setColor(new java.awt.Color(255, 255, 255));
        txtEmail.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtEmail.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        txtEmail.setHint("Email *");
        txtEmail.setSelectionColor(new java.awt.Color(83, 255, 150));
        Pass.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 320, 331, 43));

        lblCheckTenDangNhap.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblCheckTenDangNhap.setForeground(new java.awt.Color(252, 83, 117));
        Pass.add(lblCheckTenDangNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 270, 331, 28));

        lblCheckEmail.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblCheckEmail.setForeground(new java.awt.Color(252, 83, 117));
        Pass.add(lblCheckEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 370, 331, 35));

        add(Pass, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        if (checkDangNhap()) {

            event.actionPerformed(evt);
        }


    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnQuenPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuenPassActionPerformed
        if (checkQuenMatKhau()) {
            guiMail();

        }
        event.actionPerformed(evt);

//        NguoiDung nd = dao.selectById(txtTenDangNhap.getText());
//        NhanVienDAO daoNV = new NhanVienDAO();
//        List<NhanVien> nv = daoNV.selectByKeyword(nd.getMaND());
//        for (int i = 0;i < nv.size();i++) {
//            System.out.println(nv.get(i).getEmail());
//        }
//        event.actionPerformed(evt);
    }//GEN-LAST:event_btnQuenPassActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Pass;
    private newpackage.Button btnLogin;
    private newpackage.Button btnQuenPass;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lblCheckEmail;
    private javax.swing.JLabel lblCheckPass;
    private javax.swing.JLabel lblCheckTenDangNhap;
    private javax.swing.JLabel lblCheckUser;
    private javax.swing.JPanel login;
    private TextAndPassFiled.MyPasswordField pswPass;
    private TextAndPassFiled.MyTextField txtEmail;
    private TextAndPassFiled.MyTextField txtTenDangNhap;
    private TextAndPassFiled.MyTextField txtUserName;
    // End of variables declaration//GEN-END:variables
}
