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
import com.sun.webkit.BackForwardList;
import com.toedter.calendar.*;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import dao.NguoiDungDAO;
import dao.NhanVienDAO;
import entity.NguoiDung;
import entity.NhanVien;
import helper.Auth;
import helper.MsgBox;
import helper.XDate;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.plaf.basic.BasicPanelUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;

public class NhanVienJFrame extends javax.swing.JFrame {

    /**
     * Creates new form NhanVienJFrame
     */
    public NhanVienJFrame() {
        initComponents();
        init();
        dcNgaySinh.setMaxSelectableDate(setYear(18));
        dcNgaySinh.setMinSelectableDate(setYear(60));
        dcNgaySinh.setDate(setYear(18));
    }

    public Date setYear(int Tuoi) {
        Date now = new Date();
        Period actual = null;
        Date ngayGioiHan = null;
        LocalDate now1 = LocalDate.of(now.getYear() + 1900, now.getMonth(), now.getDate());
        int Nam = 0, Thang = 12, Ngay = 1;
        int duTuoi = 0;
        Nam = now.getYear() + 1900 - Tuoi;
        for (int i = 1; i <= Thang; i++) {
            switch (i) {
                // các tháng 1, 3, 5, 7, 8, 10 và 12 có 31 ngày.
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    Ngay = 31;
                    break;

                // các tháng 4, 6, 9 và 11 có 30 ngày
                case 4:
                case 6:
                case 9:
                case 11:
                    Ngay = 30;
                    break;

                // Riêng tháng 2 nếu là năm nhuận thì có 29 ngày, còn không thì có 28 ngày.
                case 2:

                    if ((Nam % 4 == 0 && Nam % 100 != 0) || (Nam % 400 == 0)) {
                        Ngay = 29;
                    } else {
                        Ngay = 28;
                    }
                    break;

            }
            for (int j = 1; j <= Ngay; j++) {
                actual = Period.between(LocalDate.of(Nam, i, j), now1);
                if (actual.getYears() == 18 || actual.getYears() == 60) {
                    if (actual.getMonths() == 0) {
                        if (actual.getDays() == 0) {
                            ngayGioiHan = new Date(Nam - 1900, i, j);
                            duTuoi += 1;
                            break;
                        }
                    }

                }
            }
            if (duTuoi > 0) {
                break;
            }
        }
        return ngayGioiHan;
    }

    NhanVienDAO dao = new NhanVienDAO();
    List<NhanVien> list = dao.selectAll();
    NguoiDungDAO daoND = new NguoiDungDAO();
    List<NguoiDung> listND = daoND.selectAll();
    int mpX, mpY;
    int index;

    public void getAdmin(int i) {

        if (tblNhanVien.getValueAt(i, 0).equals("admin")) {
            eventButtonEnable(btnXoa);
            btnXoa.setEffectColor(Color.gray);
        } else {
            eventButton(btnXoa, new Color(255, 0, 0));
        }

    }

    public void init() {
        eventButtonEnable(btnSua);
        btnSua.setEffectColor(Color.gray);
        eventButtonEnable(btnXoa);
        btnXoa.setEffectColor(Color.gray);
        JTextFieldDateEditor editor = (JTextFieldDateEditor) dcNgaySinh.getDateEditor();
        editor.setEditable(false);
        fillTable();
    }

    public void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        String tenNV = txtTimKiem.getText();

        list = dao.selectByKeyword2(tenNV);
//        list = dao.selectAll();
        for (NhanVien nv : list) {
            Object[] rows = new Object[]{nv.getMaNV(), nv.getTenNV(), nv.getGioiTinh2(),
                nv.getNgaySinh2(), nv.getSdt(), nv.getEmail(), nv.getDiaChi(), nv.getChucVu(), nv.getNgayVaoLam2()

            };
            model.addRow(rows);
        }
    }

    public NhanVien readForm() {

        JTextFieldDateEditor editor = (JTextFieldDateEditor) dcNgaySinh.getDateEditor();
        editor.setEditable(false);
        NhanVien nv = new NhanVien();

        nv.setMaNV(txtMaNV.getText());
        nv.setTenNV(txtTenNV.getText());
        if (cboGioiTinh.getSelectedItem().equals("Nam")) {
            nv.setGioiTinh("1");
        } else {
            nv.setGioiTinh("0");
        }
        String ngaySinh = editor.getText();
        Date ngaySinh2 = XDate.toDate(ngaySinh, "dd-MM-yyyy");
        nv.setNgaySinh(XDate.toString(ngaySinh2, "yyyy-MM-dd"));

        nv.setSdt(txtSDT.getText());
        nv.setEmail(txtEmail.getText());
        nv.setDiaChi(txtDiaChi.getText());
        nv.setChucVu(cboChucVu.getSelectedItem().toString());

        String ngayNhap = txtNgayNhap.getText();
        Date ngayNhap2 = XDate.toDate(ngayNhap, "dd-MM-yyyy");
        nv.setNgayVaoLam(XDate.toString(ngayNhap2, "yyyy-MM-dd"));

        return nv;
    }

    public void eventButtonEnable(JButton button) {
        button.setEnabled(false);
        button.setBackground(Color.GRAY);
    }

    public void eventButton(JButton button, Color color) {
        button.setEnabled(true);
        button.setBackground(color);
    }

    public void writeForm() {
        int i = tblNhanVien.getSelectedRow();
        getAdmin(i);
        eventButtonEnable(btnThem);
        btnThem.setEffectColor(Color.gray);
        eventButton(btnSua, new Color(81, 145, 255));
//        eventButtonEnable(btnXoa);
//        btnXoa.setEffectColor(Color.gray);
        txtMaNV.setEnabled(false);
        tabNhanVien.setSelectedIndex(0);
        JTextFieldDateEditor editor = (JTextFieldDateEditor) dcNgaySinh.getDateEditor();
        int index = tblNhanVien.getSelectedRow();

        txtMaNV.setText(tblNhanVien.getValueAt(index, 0).toString());
        txtTenNV.setText(tblNhanVien.getValueAt(index, 1).toString());
        cboGioiTinh.setSelectedItem(tblNhanVien.getValueAt(index, 2).toString());
        editor.setText(tblNhanVien.getValueAt(index, 3).toString());
        txtSDT.setText(tblNhanVien.getValueAt(index, 4).toString());
        txtEmail.setText(tblNhanVien.getValueAt(index, 5).toString());
        txtDiaChi.setText(tblNhanVien.getValueAt(index, 6).toString());
        cboChucVu.setSelectedItem(tblNhanVien.getValueAt(index, 7).toString());
        txtNgayNhap.setText(tblNhanVien.getValueAt(index, 8).toString());

    }

    public void clearForm() {

        txtEmail.setBackground(Color.white);
        txtSDT.setBackground(Color.white);

        eventButton(btnThem, new Color(81, 145, 255));
        eventButtonEnable(btnSua);
        btnSua.setEffectColor(Color.gray);
        eventButtonEnable(btnXoa);
        btnXoa.setEffectColor(Color.gray);
        txtMaNV.setEnabled(true);
        JTextFieldDateEditor editor = (JTextFieldDateEditor) dcNgaySinh.getDateEditor();
        int index = tblNhanVien.getSelectedRow();
        txtMaNV.setText("");
        txtTenNV.setText("");
        cboGioiTinh.setSelectedItem("");
        editor.setText("");
        txtSDT.setText("");
        txtEmail.setText("");
        txtDiaChi.setText("");
        cboChucVu.setSelectedItem("");
        cboGioiTinh.setSelectedIndex(0);
        cboChucVu.setSelectedIndex(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date now = new Date();
        txtNgayNhap.setText(sdf.format(now) + "");

    }

    public boolean checkNhanVien() {
        String maNV = txtMaNV.getText();
        String tenNV = txtTenNV.getText();
        JTextFieldDateEditor editor = (JTextFieldDateEditor) dcNgaySinh.getDateEditor();
        String ngaySinh = editor.getText();
        String sdt = txtSDT.getText();
        String email = txtEmail.getText();
        String diaChi = txtDiaChi.getText();
        String loi = "";
        if (maNV.isEmpty() || tenNV.isEmpty() || ngaySinh.isEmpty()
                || sdt.isEmpty() || email.isEmpty() || diaChi.isEmpty()) {
            loi += "Không được bỏ trống !\n";
        } else {
            String dinhdangGmail = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "gmail+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            String dinhdangFpt = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "fpt+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            if (txtEmail.getText().matches(dinhdangGmail) || txtEmail.getText().matches(dinhdangFpt)) {
                txtEmail.setBackground(Color.white);
            } else {
                loi += "Định dạng email không hợp lệ ! \n";
                txtEmail.setBackground(Color.orange);
                txtEmail.requestFocus();

            }
            String sdtDinhDang = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";
            if (sdt.matches(sdtDinhDang)) {
                txtSDT.setBackground(Color.white);
            } else {

                loi += "Định dạng số điện thoại không hợp lệ ! \n";
                txtSDT.setBackground(Color.orange);
                txtSDT.requestFocus();

            }

        }
        if (cboGioiTinh.getSelectedIndex() == 0) {
            loi += "Vui lòng chọn giới tính !\n";
        }
        if (cboChucVu.getSelectedIndex() == 0) {
            loi += "Vui lòng chọn chức vụ !\n";
        }

        if (loi.length() > 0) {
            MsgBox.alert(this, loi);
            return false;
        }
        return true;
    }

    public boolean checkTrung() {
        String maNV = txtMaNV.getText();
        List<NhanVien> nv = dao.selectByKeyword(maNV);
        String email = txtEmail.getText();
        String sdt = txtSDT.getText();
        String loi = "";
        for (int i = 0; i < nv.size(); i++) {

            if (maNV.equalsIgnoreCase(nv.get(i).getMaNV())) {

                loi += "Nhân viên đã tồn tại! \n";

            }
            if (email.equals(nv.get(i).getEmail())) {

                loi += "Email đã tồn tại! \n";

            }
            if (sdt.equals(nv.get(i).getSdt())) {

                loi += "Số điện thoại đã tồn tại! \n";

            }
        }
        if (loi.length() != 0) {
            MsgBox.alert(this, loi);
            return false;
        }
        return true;
    }

    public void insert() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date now = new Date();
        txtNgayNhap.setText(sdf.format(now) + "");

        NhanVien nv = this.readForm();
        dao.insert(nv);
        fillTable();
        clearForm();
        MsgBox.alert(this, "Thêm thành công !?");
    }

    public void delete() {
        int viTri = tblNhanVien.getSelectedRow();
        boolean chon = MsgBox.confirm(this, "Bạn có chắc chắn muốn xóa nhân viên này ?!"), xoa = true;
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        String maNV = tblNhanVien.getValueAt(viTri, 0).toString();
        if (chon) {
            for (int i = 0; i < listND.size(); i++) {
                if (listND.get(i).getMaND().equals(maNV)) {
                    MsgBox.alert(this, "Nhân viên này đã có tài khoản không được xóa");
                    xoa = false;
                    break;
                }

            }
            if (xoa) {
                dao.delete(maNV);
                clearForm();
                fillTable();
                MsgBox.alert(this, "Đã xóa thành công");
            }

        }

    }

    public void update() {
        NhanVien nv = this.readForm();
        dao.update(nv);
        fillTable();
        clearForm();
        MsgBox.alert(this, "Cập nhật thành công !");
    }

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
        pnlTablePane = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tabNhanVien = new javax.swing.JTabbedPane();
        pnlDiaChi = new javax.swing.JPanel();
        lblMaNV = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        txtTenNV = new javax.swing.JTextField();
        lblTenNv = new javax.swing.JLabel();
        lblGioiTinh = new javax.swing.JLabel();
        cboGioiTinh = new javax.swing.JComboBox<>();
        txtSDT = new javax.swing.JTextField();
        lblSDT = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        btnXoa = new newpackage.Button();
        btnSua = new newpackage.Button();
        btnThem = new newpackage.Button();
        dcNgaySinh = new com.toedter.calendar.JDateChooser();
        lblNgaySinh = new javax.swing.JLabel();
        lblDiaChi = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        lblNgayVaoLam = new javax.swing.JLabel();
        txtNgayNhap = new javax.swing.JTextField();
        cboChucVu = new javax.swing.JComboBox<>();
        lblChucVu = new javax.swing.JLabel();
        btnLamMoi = new newpackage.Button();
        pnlDanhSach = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        pnlTimKiem = new javax.swing.JPanel();
        btnTimKiem = new newpackage.Button();
        txtTimKiem = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        pnlTitleBar.setBackground(new java.awt.Color(81, 145, 255));
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

        lblExit.setBackground(new java.awt.Color(81, 145, 255));
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

        lblMaxi.setBackground(new java.awt.Color(81, 145, 255));
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

        lblMini.setBackground(new java.awt.Color(81, 145, 255));
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
                .addGap(0, 0, Short.MAX_VALUE)
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

        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 2, 2, 2, new java.awt.Color(81, 145, 255)));

        pnlTablePane.setBackground(new java.awt.Color(255, 255, 255));
        pnlTablePane.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(255, 255, 255)));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Quản Lý Nhân Viên");

        javax.swing.GroupLayout pnlTablePaneLayout = new javax.swing.GroupLayout(pnlTablePane);
        pnlTablePane.setLayout(pnlTablePaneLayout);
        pnlTablePaneLayout.setHorizontalGroup(
            pnlTablePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlTablePaneLayout.setVerticalGroup(
            pnlTablePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        tabNhanVien.setBackground(new java.awt.Color(255, 255, 255));
        tabNhanVien.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabNhanVien.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tabNhanVien.setOpaque(true);

        pnlDiaChi.setBackground(new java.awt.Color(255, 255, 255));

        lblMaNV.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMaNV.setText("MÃ NHÂN VIÊN :");

        txtMaNV.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtMaNV.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));

        txtTenNV.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtTenNV.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));

        lblTenNv.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTenNv.setText("TÊN NHÂN VIÊN :");

        lblGioiTinh.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblGioiTinh.setText("GIỚI TÍNH :");

        cboGioiTinh.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Chọn giới tính---", "Nam", "Nữ" }));
        cboGioiTinh.setOpaque(false);

        txtSDT.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtSDT.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));

        lblSDT.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblSDT.setText("SỐ ĐIỆN THOẠI :");

        txtEmail.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtEmail.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        lblEmail.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblEmail.setText("EMAIL :");

        btnXoa.setBackground(new java.awt.Color(255, 0, 0));
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa");
        btnXoa.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(81, 145, 255));
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setText("Sửa");
        btnSua.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnThem.setBackground(new java.awt.Color(81, 145, 255));
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm");
        btnThem.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        dcNgaySinh.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        dcNgaySinh.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dcNgaySinh.setMaxSelectableDate(new java.util.Date(1072807274000L));
        dcNgaySinh.setMinSelectableDate(new java.util.Date(-315640731000L));
        dcNgaySinh.setOpaque(false);

        lblNgaySinh.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblNgaySinh.setText("NGÀY SINH :");

        lblDiaChi.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblDiaChi.setText("ĐỊA CHỈ :");

        txtDiaChi.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtDiaChi.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtDiaChi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiaChiActionPerformed(evt);
            }
        });

        lblNgayVaoLam.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblNgayVaoLam.setText("NGÀY VÀO LÀM :");

        txtNgayNhap.setEditable(false);
        txtNgayNhap.setBackground(new java.awt.Color(255, 255, 255));
        txtNgayNhap.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtNgayNhap.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));

        cboChucVu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboChucVu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Chọn chức vụ---", "Quản lý ", "Thu ngân", "Bảo vệ", "Phục vụ", "Pha chế" }));
        cboChucVu.setOpaque(false);

        lblChucVu.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblChucVu.setText("Chức vụ :");

        btnLamMoi.setBackground(new java.awt.Color(0, 204, 102));
        btnLamMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoi.setText("Làm mới");
        btnLamMoi.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlDiaChiLayout = new javax.swing.GroupLayout(pnlDiaChi);
        pnlDiaChi.setLayout(pnlDiaChiLayout);
        pnlDiaChiLayout.setHorizontalGroup(
            pnlDiaChiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDiaChiLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlDiaChiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDiaChiLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(pnlDiaChiLayout.createSequentialGroup()
                        .addGroup(pnlDiaChiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblChucVu)
                            .addComponent(cboChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEmail)
                            .addComponent(lblSDT)
                            .addComponent(lblMaNV)
                            .addComponent(lblGioiTinh)
                            .addComponent(cboGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTenNv))
                        .addGap(0, 521, Short.MAX_VALUE))
                    .addGroup(pnlDiaChiLayout.createSequentialGroup()
                        .addGroup(pnlDiaChiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNgayVaoLam)
                            .addComponent(lblNgaySinh)
                            .addComponent(lblDiaChi))
                        .addContainerGap(822, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDiaChiLayout.createSequentialGroup()
                        .addGroup(pnlDiaChiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNgayNhap, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSDT, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dcNgaySinh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenNV, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDiaChi))
                        .addGap(20, 20, 20))))
        );
        pnlDiaChiLayout.setVerticalGroup(
            pnlDiaChiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDiaChiLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblMaNV)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(lblTenNv)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblGioiTinh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNgaySinh)
                .addGap(13, 13, 13)
                .addComponent(dcNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSDT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEmail)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblChucVu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNgayVaoLam)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(pnlDiaChiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnXoa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date now = new Date();
        txtNgayNhap.setText(sdf.format(now) + "");

        tabNhanVien.addTab("Cập Nhật", pnlDiaChi);

        pnlDanhSach.setBackground(new java.awt.Color(255, 255, 255));

        tblNhanVien.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã nhân viên", "Tên nhân viên", "Giới tính", "Ngày sinh", "Số điện thoại", "Email", "Địa chỉ", "Chức vụ", "Ngày vào làm"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.setRowHeight(25);
        tblNhanVien.setRowMargin(0);
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien);

        pnlTimKiem.setBackground(new java.awt.Color(255, 255, 255));
        pnlTimKiem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search_32_icon.png"))); // NOI18N
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        txtTimKiem.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        txtTimKiem.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0));
        txtTimKiem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTimKiemFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTimKiemFocusLost(evt);
            }
        });

        javax.swing.GroupLayout pnlTimKiemLayout = new javax.swing.GroupLayout(pnlTimKiem);
        pnlTimKiem.setLayout(pnlTimKiemLayout);
        pnlTimKiemLayout.setHorizontalGroup(
            pnlTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTimKiemLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(txtTimKiem)
                .addGap(0, 0, 0)
                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        pnlTimKiemLayout.setVerticalGroup(
            pnlTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTimKiemLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(pnlTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout pnlDanhSachLayout = new javax.swing.GroupLayout(pnlDanhSach);
        pnlDanhSach.setLayout(pnlDanhSachLayout);
        pnlDanhSachLayout.setHorizontalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 961, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlDanhSachLayout.setVerticalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabNhanVien.addTab("Danh Sách", pnlDanhSach);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlTablePane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tabNhanVien))
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(pnlTablePane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tabNhanVien)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTitleBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlTitleBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        lblExit.setBackground(new Color(81, 145, 255));
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
        lblMaxi.setBackground(new Color(81, 145, 255));
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
        lblMini.setBackground(new Color(81, 145, 255));
    }//GEN-LAST:event_lblMiniMouseExited

    private void pnlTitleBarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTitleBarMousePressed
        // TODO add your handling code hee:
        mpX = evt.getX();
        mpY = evt.getY();
    }//GEN-LAST:event_pnlTitleBarMousePressed

    private void pnlTitleBarMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTitleBarMouseDragged
        // TODO add your handling code here:
        if (this.getExtendedState() == JFrame.MAXIMIZED_BOTH) {

        } else {
            this.setLocation(
                    getLocation().x + evt.getX() - mpX,
                    getLocation().y + evt.getY() - mpY);
        }
    }//GEN-LAST:event_pnlTitleBarMouseDragged

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if (checkNhanVien()) {
            update();
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (checkTrung() && checkNhanVien()) {
            insert();
        }

    }//GEN-LAST:event_btnThemActionPerformed

    private void txtTimKiemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusGained
        // TODO add your handling code here:
//        if (txtTimKiem.getText().equals("Nhập để tìm kiếm ...")) {
//            txtTimKiem.setText("");
//        }
    }//GEN-LAST:event_txtTimKiemFocusGained

    private void txtTimKiemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusLost
        // TODO add your handling code here:
//        if (txtTimKiem.getText().equals("")) {
//            txtTimKiem.setText("Nhập để tìm kiếm ...");
//        }
    }//GEN-LAST:event_txtTimKiemFocusLost

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtDiaChiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiaChiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiaChiActionPerformed

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        if (evt.getClickCount() == 2) {
            writeForm();
        }


    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        fillTable();
    }//GEN-LAST:event_btnTimKiemActionPerformed

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
            java.util.logging.Logger.getLogger(NhanVienJFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NhanVienJFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NhanVienJFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NhanVienJFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NhanVienJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private newpackage.Button btnLamMoi;
    private newpackage.Button btnSua;
    private newpackage.Button btnThem;
    private newpackage.Button btnTimKiem;
    private newpackage.Button btnXoa;
    private javax.swing.JComboBox<String> cboChucVu;
    private javax.swing.JComboBox<String> cboGioiTinh;
    private com.toedter.calendar.JDateChooser dcNgaySinh;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblChucVu;
    private javax.swing.JLabel lblDiaChi;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblGioiTinh;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblMaxi;
    private javax.swing.JLabel lblMini;
    private javax.swing.JLabel lblNgaySinh;
    private javax.swing.JLabel lblNgayVaoLam;
    private javax.swing.JLabel lblSDT;
    private javax.swing.JLabel lblTenNv;
    private javax.swing.JPanel pnlDanhSach;
    private javax.swing.JPanel pnlDiaChi;
    private javax.swing.JPanel pnlTablePane;
    private javax.swing.JPanel pnlTimKiem;
    private javax.swing.JPanel pnlTitleBar;
    private javax.swing.JTabbedPane tabNhanVien;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtNgayNhap;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
