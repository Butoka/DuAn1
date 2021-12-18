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
import dao.HoaDonDAO;
import dao.NguoiDungDAO;
import dao.NhanVienDAO;
import entity.HoaDon;
import entity.NguoiDung;
import entity.NhanVien;
import helper.MsgBox;
import java.awt.*;
import java.util.List;
import java.awt.event.WindowEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.plaf.synth.SynthTableHeaderUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class NguoiDungJFrame extends javax.swing.JFrame {

    int mpX, mpY;
    int viTri;

    /**
     * Creates new form NguoiDungJFrame
     */
    public NguoiDungJFrame() {
        initComponents();
        init();

    }

    public void init() {
        fillComboBoxMaND();
        nameCollum();
        fillTable();
    }
    NguoiDungDAO dao = new NguoiDungDAO();
    NhanVien nv;
    NhanVienDAO daoNV = new NhanVienDAO();
    List<NguoiDung> list = dao.selectAll();
    HoaDonDAO daoHD = new HoaDonDAO();
    List<HoaDon> listHD = daoHD.selectAll();

    public void nameCollum() {
        JTableHeader tableHeader = tblNguoiDung.getTableHeader();
        tableHeader.setUI(new SynthTableHeaderUI());
        Font HeaderFont = new Font("SansSerif", Font.PLAIN, 18);
        tableHeader.setOpaque(false);
        tableHeader.setBackground(new Color(81, 145, 255));
        tableHeader.setForeground(Color.white);

        tableHeader.setFont(HeaderFont);
        tblNguoiDung.setRowHeight(25);
    }

    public void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblNguoiDung.getModel();
        model.setRowCount(0);
        String tenND = txtTimKiem.getText();

        List<NguoiDung> list = dao.selectByKeyword(tenND);
        for (NguoiDung nd : list) {
            Object[] rows = new Object[]{nd.getMaND(), nd.getTenND(),
                nd.getPhanQuyen(), nd.getTrangThai2()};
            model.addRow(rows);
        }
    }

    public void fillComboBoxMaND() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboMaND.getModel();
        model.removeAllElements();
        List<NhanVien> list = daoNV.selectNhanVien();
        for (NhanVien nv : list) {

            model.addElement(nv);

        }
        cboMaND.setSelectedIndex(0);

    }

    public NguoiDung readForm() {
        NguoiDung nd = new NguoiDung();
        nv = (NhanVien) cboMaND.getSelectedItem();
        nd.setMaND(nv.getMaNV());
        nd.setTenND(txtTenND.getText());
        nd.setMatKhau(pswMatKhau.getText());
        nd.setPhanQuyen(cboPhanQuyen.getSelectedItem().toString());
        if (rdoHoatDong.isSelected()) {
            nd.setTrangThai("1");
        } else {
            nd.setTrangThai("0");
        }
        return nd;
    }

    public NguoiDung readFormUpdate() {
        NguoiDung nd = new NguoiDung();
        nv = (NhanVien) cboMaND.getSelectedItem();
        nd.setMaND(nv.getMaNV());
        nd.setTenND(txtTenND.getText());
        if (!pswMatKhau.getText().isEmpty()) {
            nd.setMatKhau(pswMatKhau.getText());
        } else {
            String tenND = txtTenND.getText();

            NguoiDung list = dao.selectById(tenND);

            nd.setMatKhau(list.getMatKhau());
        }
        nd.setPhanQuyen(cboPhanQuyen.getSelectedItem().toString());
        if (rdoHoatDong.isSelected()) {
            nd.setTrangThai("1");
        } else {
            nd.setTrangThai("0");
        }
        return nd;
    }

    public void writeForm() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboMaND.getModel();
        nv = (NhanVien) cboMaND.getSelectedItem();
        tabNhanVien.setSelectedIndex(0);
        viTri = tblNguoiDung.getSelectedRow();
        // txtMaND.setEnabled(false);
        txtTenND.setEnabled(false);
        String phanQuyen = tblNguoiDung.getValueAt(viTri, 2).toString();
        String maND = tblNguoiDung.getValueAt(viTri, 0).toString();

        List<NhanVien> list = daoNV.selectNhanVien();
        if (!list.isEmpty()) {
            for (NhanVien nv : list) {
                if (nv.getMaNV().equals(maND)) {
                    model.setSelectedItem(nv);
                }

            }
        }

        // txtMaND.setText(maND);
        txtTenND.setText(tblNguoiDung.getValueAt(viTri, 1).toString());
        pswMatKhau.setText("");
        pswXacNhanMK.setText("");
        cboPhanQuyen.setSelectedItem(tblNguoiDung.getValueAt(viTri, 2));
        if (tblNguoiDung.getValueAt(viTri, 3).toString().equalsIgnoreCase("Hoạt động")) {
            rdoHoatDong.setSelected(true);
        } else {
            rdoKhongHoatDong.setSelected(true);
        }
        if (phanQuyen.equalsIgnoreCase("admin")) {
            cboPhanQuyen.setEnabled(false);
            eventButtonEnable(btnXoa);
            btnXoa.setEffectColor(Color.GRAY);
            rdoKhongHoatDong.setEnabled(false);
        } else {
            cboPhanQuyen.setEnabled(true);
            eventButton(btnXoa, new Color(255, 0, 0));
            btnXoa.setEffectColor(Color.white);
            rdoKhongHoatDong.setEnabled(true);
        }

    }

    public void eventButtonEnable(JButton button) {
        button.setEnabled(false);
        button.setBackground(Color.GRAY);
    }

    public void eventButton(JButton button, Color color) {
        button.setEnabled(true);
        button.setBackground(color);
    }

    public void insert() {
        NguoiDung nd = this.readForm();
        dao.insert(nd);
        fillTable();
        MsgBox.alert(this, "Thêm thành công");
        tabNhanVien.setSelectedIndex(1);

    }

    public void delete() {
        viTri = tblNguoiDung.getSelectedRow();
        if (viTri < 0) {
            MsgBox.alert(this, "Vui lòng chọn người dùng từ danh sách!");
            return;
        }
        String phanQuyen = tblNguoiDung.getValueAt(viTri, 2).toString();

        if (phanQuyen.equalsIgnoreCase("admin")) {
            MsgBox.alert(this, "Bạn không thể xóa!");
            return;
        }
        boolean chon = MsgBox.confirm(this, "Bạn có chắc chắn xóa "), xoa = true;
        DefaultTableModel model = (DefaultTableModel) tblNguoiDung.getModel();
        String tenNguoiDung = txtTenND.getText();
        if (chon) {
            for (int i = 0; i < listHD.size(); i++) {
                if (listHD.get(i).getTenDN().equals(tenNguoiDung)) {
                    MsgBox.alert(this, "Người dùng này không thể xóa!!!");
                    xoa = false;
                    break;
                }
            }

            if (xoa) {
                dao.delete(tenNguoiDung);
                fillTable();
                MsgBox.alert(this, "Đã xóa thành công");
                txtTenND.setText("");
                txtTenND.setEnabled(true);
            }

        } else {
            MsgBox.alert(this, "Chưa được xóa");
        }
    }

    public void update() {

        NguoiDung nd = this.readFormUpdate();
        dao.update(nd);
        fillTable();
        MsgBox.alert(this, "Sửa thành công");
        tabNhanVien.setSelectedIndex(1);
        for (int i = 0; i < tblNguoiDung.getRowCount(); i++) {
            if (nd.getMaND().equals(tblNguoiDung.getValueAt(i, 0))) {
                tblNguoiDung.setRowSelectionInterval(i, i);
            }
        }
    }

    public boolean checkNguoiDung() {
        //String maND = txtMaND.getText();
        String tenND = txtTenND.getText();
        String matKhau = pswMatKhau.getText();
        String nhapLaiMK = pswXacNhanMK.getText();
        String loi = "";
        if (tenND.isEmpty() && matKhau.isEmpty() && nhapLaiMK.isEmpty()) {
            loi += "Không được bỏ trống !? \n";
        } else {
//            if (maND.isEmpty()) {
//                loi += "Vui lòng nhập mã người dùng !\n";
//
//            }
            if (tenND.isEmpty()) {
                loi += "Vui lòng nhập tên người dùng !\n";

            }
            if (!matKhau.isEmpty()) {
                if (nhapLaiMK.isEmpty()) {
                    loi += "Vui lòng nhập lại mật khẩu !\n";

                } else if (!matKhau.equals(nhapLaiMK)) {
                    loi += "Mật khẩu không trùng khớp !\n";
                    pswXacNhanMK.requestFocus();
                }

            } else {
                loi += "Vui lòng nhập mật khẩu !\n";
            }

        }
        if (cboPhanQuyen.getSelectedIndex() == 0) {
            loi += "Vui lòng chọn vai trò !\n";
        }
        if (!rdoHoatDong.isSelected() && !rdoKhongHoatDong.isSelected()) {
            loi += "Vui lòng chọn trạng thái \n";
        }
        if (loi.length() != 0) {
            MsgBox.alert(this, loi);
            return false;
        }

        return true;
    }

    public boolean checkBoTrongUpdate() {
//        viTri = tblNguoiDung.getSelectedRow();
//        if (viTri < 0) {
//            MsgBox.alert(this, "Vui lòng chọn người dùng từ danh sách!");
//            return false;
//        }
        // String maND = txtMaND.getText();
        String tenND = txtTenND.getText();
        String matKhau = pswMatKhau.getText();
        String nhapLaiMK = pswXacNhanMK.getText();
        String loi = "";
        if (tenND.isEmpty() && matKhau.isEmpty() && nhapLaiMK.isEmpty()) {
            loi += "Không được bỏ trống !? \n";
        } else {
//            if (maND.isEmpty()) {
//                loi += "Vui lòng nhập mã người dùng !\n";
//
//            }
            if (tenND.isEmpty()) {
                loi += "Vui lòng nhập tên người dùng !\n";

            }
            if (!matKhau.isEmpty()) {
                if (nhapLaiMK.isEmpty()) {
                    loi += "Vui lòng nhập lại mật khẩu !\n";

                } else if (!matKhau.equals(nhapLaiMK)) {
                    loi += "Mật khẩu không trùng khớp !\n";
                    pswXacNhanMK.requestFocus();
                }

            }

        }
        if (cboPhanQuyen.getSelectedIndex() == 0) {
            loi += "Vui lòng chọn vai trò !\n";
        }
        if (!rdoHoatDong.isSelected() && !rdoKhongHoatDong.isSelected()) {
            loi += "Vui lòng chọn trạng thái \n";
        }
        if (loi.length() != 0) {
            MsgBox.alert(this, loi);
            return false;
        }

        return true;
    }

    public boolean checkTrung() {
        list = dao.selectAll();

        //String maND = txtMaND.getText();
        //List<NhanVien> nv = daoNV.selectByKeyword(maND);
        String tenND = txtTenND.getText();
        String loi = "";
        for (int i = 0; i < list.size(); i++) {

//            if (maND.equals(list.get(i).getMaND())) {
//
//                loi += "Nhân viên này đã có tài khoản! \n";
//
//            }
            if (tenND.equals(list.get(i).getTenND()) && !tenND.isEmpty()) {

                loi += "Tên tài khoản đã tồn tại! \n";

            }
        }

//        if (nv.size() == 0 && !maND.isEmpty()) {
//
//            loi += "Mã người dùng không hợp lệ \nNhân viên này ko tồn tại! \n";
//
//        }
        if (loi.length() != 0) {
            MsgBox.alert(this, loi);
            return false;
        }
        return true;
    }

    public void clearForm() {
        nv = (NhanVien) cboMaND.getSelectedItem();
        List<NguoiDung> list = dao.selectByMaND(nv.getMaNV());
        if (list.isEmpty()) {
            //txtMaND.setText("");
            txtTenND.setText("");
            pswMatKhau.setText("");
            pswXacNhanMK.setText("");
            rdoHoatDong.setSelected(true);

            cboPhanQuyen.setSelectedIndex(0);
        } else {
            pswMatKhau.setText("");
            pswXacNhanMK.setText("");
            rdoHoatDong.setSelected(true);
            if (!nv.getMaNV().equals("admin")) {
                cboPhanQuyen.setSelectedIndex(0);
            }

            viTri = 0;
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

        btngTrangThai = new javax.swing.ButtonGroup();
        pnlTitleBarr = new javax.swing.JPanel();
        lblExit = new javax.swing.JLabel();
        lblMini = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        tabNhanVien = new javax.swing.JTabbedPane();
        pnlCapNhat = new javax.swing.JPanel();
        txtTenND = new javax.swing.JTextField();
        lblTenND = new javax.swing.JLabel();
        lblMatKhau = new javax.swing.JLabel();
        lblPhanQuyen = new javax.swing.JLabel();
        btnXoa = new newpackage.Button();
        btnSua = new newpackage.Button();
        btnThem = new newpackage.Button();
        lblNhapLaiMatKhau = new javax.swing.JLabel();
        cboPhanQuyen = new javax.swing.JComboBox<>();
        lblTrangThai = new javax.swing.JLabel();
        rdoHoatDong = new javax.swing.JRadioButton();
        rdoKhongHoatDong = new javax.swing.JRadioButton();
        pswXacNhanMK = new javax.swing.JPasswordField();
        pswMatKhau = new javax.swing.JPasswordField();
        btnLamMoi = new newpackage.Button();
        cboMaND = new javax.swing.JComboBox<>();
        lblMaND1 = new javax.swing.JLabel();
        pnlDanhSach = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNguoiDung = new javax.swing.JTable();
        pnlTimKiem = new javax.swing.JPanel();
        btnTimKiem = new newpackage.Button();
        txtTimKiem = new javax.swing.JTextField();
        pnlTablePane = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        pnlTitleBarr.setBackground(new java.awt.Color(81, 145, 255));
        pnlTitleBarr.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlTitleBarrMouseDragged(evt);
            }
        });
        pnlTitleBarr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlTitleBarrMousePressed(evt);
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

        javax.swing.GroupLayout pnlTitleBarrLayout = new javax.swing.GroupLayout(pnlTitleBarr);
        pnlTitleBarr.setLayout(pnlTitleBarrLayout);
        pnlTitleBarrLayout.setHorizontalGroup(
            pnlTitleBarrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTitleBarrLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblMini)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblExit))
        );
        pnlTitleBarrLayout.setVerticalGroup(
            pnlTitleBarrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblMini, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 2, 2, 2, new java.awt.Color(81, 145, 255)));

        tabNhanVien.setBackground(new java.awt.Color(255, 255, 255));
        tabNhanVien.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabNhanVien.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tabNhanVien.setOpaque(true);

        pnlCapNhat.setBackground(new java.awt.Color(255, 255, 255));

        txtTenND.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtTenND.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtTenND.setEnabled(false);

        lblTenND.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTenND.setText("TÊN ĐĂNG NHẬP:");

        lblMatKhau.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMatKhau.setText("MẬT KHẨU :");

        lblPhanQuyen.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblPhanQuyen.setText("PHÂN QUYỀN :");

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

        lblNhapLaiMatKhau.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblNhapLaiMatKhau.setText("NHẬP LẠI MẬT KHẨU :");

        cboPhanQuyen.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboPhanQuyen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Chọn phân quyền---", "Admin", "Quản lý", "Thu ngân" }));

        lblTrangThai.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTrangThai.setText("TRẠNG THÁI:");

        rdoHoatDong.setBackground(new java.awt.Color(255, 255, 255));
        btngTrangThai.add(rdoHoatDong);
        rdoHoatDong.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        rdoHoatDong.setText("Hoạt động");
        rdoHoatDong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoHoatDongActionPerformed(evt);
            }
        });

        rdoKhongHoatDong.setBackground(new java.awt.Color(255, 255, 255));
        btngTrangThai.add(rdoKhongHoatDong);
        rdoKhongHoatDong.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        rdoKhongHoatDong.setText("Không hoạt động");
        rdoKhongHoatDong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoKhongHoatDongActionPerformed(evt);
            }
        });

        pswXacNhanMK.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        pswXacNhanMK.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));

        pswMatKhau.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        pswMatKhau.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));

        btnLamMoi.setBackground(new java.awt.Color(0, 153, 51));
        btnLamMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoi.setText("Làm mới");
        btnLamMoi.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        cboMaND.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboMaND.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboMaND.setBorder(null);
        cboMaND.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboMaNDItemStateChanged(evt);
            }
        });

        lblMaND1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMaND1.setText("HỌ TÊN NGƯỜI DÙNG:");

        javax.swing.GroupLayout pnlCapNhatLayout = new javax.swing.GroupLayout(pnlCapNhat);
        pnlCapNhat.setLayout(pnlCapNhatLayout);
        pnlCapNhatLayout.setHorizontalGroup(
            pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCapNhatLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                        .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTrangThai)
                            .addComponent(lblNhapLaiMatKhau)
                            .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(pnlCapNhatLayout.createSequentialGroup()
                                    .addComponent(rdoHoatDong)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(rdoKhongHoatDong))
                                .addComponent(cboPhanQuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(pswXacNhanMK))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCapNhatLayout.createSequentialGroup()
                        .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pswMatKhau, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenND, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(20, 20, 20))
                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                        .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMatKhau)
                            .addComponent(lblTenND)
                            .addComponent(lblPhanQuyen)
                            .addComponent(cboMaND, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(342, 342, 342))
                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                        .addComponent(lblMaND1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCapNhatLayout.createSequentialGroup()
                .addContainerGap(310, Short.MAX_VALUE)
                .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlCapNhatLayout.setVerticalGroup(
            pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCapNhatLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMaND1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addComponent(cboMaND, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addGap(22, 22, 22)
                .addComponent(lblTenND)
                .addGap(18, 18, 18)
                .addComponent(txtTenND, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMatKhau)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pswMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNhapLaiMatKhau)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pswXacNhanMK, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblPhanQuyen)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboPhanQuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTrangThai)
                .addGap(18, 18, 18)
                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoHoatDong)
                    .addComponent(rdoKhongHoatDong))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnXoa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13))
        );

        tabNhanVien.addTab("Cập Nhật", pnlCapNhat);

        pnlDanhSach.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);

        tblNguoiDung.setBackground(new java.awt.Color(123, 189, 255));
        tblNguoiDung.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblNguoiDung.setForeground(new java.awt.Color(255, 255, 255));
        tblNguoiDung.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã người dùng", "Tên đăng nhập", "Phân quyền", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNguoiDung.setFocusable(false);
        tblNguoiDung.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblNguoiDung.setRowHeight(25);
        tblNguoiDung.setSelectionBackground(new java.awt.Color(255, 255, 255));
        tblNguoiDung.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tblNguoiDung.setShowVerticalLines(false);
        tblNguoiDung.getTableHeader().setReorderingAllowed(false);
        tblNguoiDung.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNguoiDungMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNguoiDung);

        pnlTimKiem.setBackground(new java.awt.Color(255, 255, 255));
        pnlTimKiem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search_32_icon.png"))); // NOI18N
        btnTimKiem.setEffectColor(new java.awt.Color(153, 153, 153));
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        txtTimKiem.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        txtTimKiem.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0));

        javax.swing.GroupLayout pnlTimKiemLayout = new javax.swing.GroupLayout(pnlTimKiem);
        pnlTimKiem.setLayout(pnlTimKiemLayout);
        pnlTimKiemLayout.setHorizontalGroup(
            pnlTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTimKiemLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(txtTimKiem)
                .addGap(24, 24, 24)
                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDanhSachLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 799, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlDanhSachLayout.setVerticalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDanhSachLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE))
        );

        tabNhanVien.addTab("Danh Sách", pnlDanhSach);

        pnlTablePane.setBackground(new java.awt.Color(255, 255, 255));
        pnlTablePane.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(255, 255, 255)));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Quản lý người dùng");

        javax.swing.GroupLayout pnlTablePaneLayout = new javax.swing.GroupLayout(pnlTablePane);
        pnlTablePane.setLayout(pnlTablePaneLayout);
        pnlTablePaneLayout.setHorizontalGroup(
            pnlTablePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlTablePaneLayout.setVerticalGroup(
            pnlTablePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

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
            .addComponent(pnlTitleBarr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlTitleBarr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void pnlTitleBarrMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTitleBarrMouseDragged
        // TODO add your handling code here:
        this.setLocation(
                getLocation().x + evt.getX() - mpX,
                getLocation().y + evt.getY() - mpY);
    }//GEN-LAST:event_pnlTitleBarrMouseDragged

    private void pnlTitleBarrMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTitleBarrMousePressed
        // TODO add your handling code here:
        mpX = evt.getX();
        mpY = evt.getY();
    }//GEN-LAST:event_pnlTitleBarrMousePressed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if (checkBoTrongUpdate()) {
            update();
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (checkTrung() && checkNguoiDung()) {
            insert();
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void rdoHoatDongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoHoatDongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoHoatDongActionPerformed

    private void rdoKhongHoatDongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoKhongHoatDongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoKhongHoatDongActionPerformed

    private void tblNguoiDungMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNguoiDungMouseClicked
        if (evt.getClickCount() == 2) {
            writeForm();
        }
    }//GEN-LAST:event_tblNguoiDungMouseClicked

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        fillTable();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void cboMaNDItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboMaNDItemStateChanged

        maNDEvent();


    }//GEN-LAST:event_cboMaNDItemStateChanged
    public void maNDEvent() {
        if (cboMaND.getSelectedItem() == null) {

        } else {
//            if (viTri > 0) {
//                return ;
//            }
            nv = (NhanVien) cboMaND.getSelectedItem();
            List<NguoiDung> list = dao.selectByMaND(nv.getMaNV());
            if (list.isEmpty()) {
                //txtMaND.setText(nv.getMaNV());
                txtTenND.setText("");
                cboPhanQuyen.setEnabled(true);
                cboPhanQuyen.setSelectedIndex(0);

                rdoHoatDong.setSelected(false);
                txtTenND.setEnabled(true);
                eventButtonEnable(btnXoa);
                btnXoa.setEffectColor(Color.GRAY);

                eventButtonEnable(btnSua);
                btnSua.setEffectColor(Color.GRAY);
            } else {
                eventButton(btnSua, new Color(81, 145, 255));
                txtTenND.setEnabled(false);
            }
            for (NguoiDung nd : list) {

                //txtMaND.setText(nd.getMaND());
                txtTenND.setText(nd.getTenND());
                if (nd.getPhanQuyen().equalsIgnoreCase("admin")) {
                    cboPhanQuyen.setEnabled(false);
                    cboPhanQuyen.setSelectedItem("Admin");

                } else if (nd.getPhanQuyen().equalsIgnoreCase("Quản lý")) {
                    cboPhanQuyen.setEnabled(true);
                    cboPhanQuyen.setSelectedItem("Quản lý");
                } else {
                    cboPhanQuyen.setEnabled(true);
                    cboPhanQuyen.setSelectedItem("Thu ngân");
                }
                if (nd.getTrangThai().equals("1")) {
                    rdoHoatDong.setSelected(true);
                } else if (nd.getTrangThai().equals("0")) {
                    rdoKhongHoatDong.setSelected(true);
                }
                String phanQuyen = nd.getPhanQuyen();
                if (phanQuyen.equalsIgnoreCase("admin")) {
                    eventButtonEnable(btnXoa);
                    btnXoa.setEffectColor(Color.GRAY);
                    rdoKhongHoatDong.setEnabled(false);
                } else {
                    eventButton(btnXoa, new Color(255, 0, 0));
                    btnXoa.setEffectColor(Color.white);
                    rdoKhongHoatDong.setEnabled(true);
                }
            }
        }
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookanlýdfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NguoiDungJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NguoiDungJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NguoiDungJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NguoiDungJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NguoiDungJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private newpackage.Button btnLamMoi;
    private newpackage.Button btnSua;
    private newpackage.Button btnThem;
    private newpackage.Button btnTimKiem;
    private newpackage.Button btnXoa;
    private javax.swing.ButtonGroup btngTrangThai;
    private javax.swing.JComboBox<String> cboMaND;
    private javax.swing.JComboBox<String> cboPhanQuyen;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblMaND1;
    private javax.swing.JLabel lblMatKhau;
    private javax.swing.JLabel lblMini;
    private javax.swing.JLabel lblNhapLaiMatKhau;
    private javax.swing.JLabel lblPhanQuyen;
    private javax.swing.JLabel lblTenND;
    private javax.swing.JLabel lblTrangThai;
    private javax.swing.JPanel pnlCapNhat;
    private javax.swing.JPanel pnlDanhSach;
    private javax.swing.JPanel pnlTablePane;
    private javax.swing.JPanel pnlTimKiem;
    private javax.swing.JPanel pnlTitleBarr;
    private javax.swing.JPasswordField pswMatKhau;
    private javax.swing.JPasswordField pswXacNhanMK;
    private javax.swing.JRadioButton rdoHoatDong;
    private javax.swing.JRadioButton rdoKhongHoatDong;
    private javax.swing.JTabbedPane tabNhanVien;
    private javax.swing.JTable tblNguoiDung;
    private javax.swing.JTextField txtTenND;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
