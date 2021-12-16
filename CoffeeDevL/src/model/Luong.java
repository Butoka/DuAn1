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
import dao.CaLamViecDAO;
import dao.ChiTietLuongDAO;
import dao.NhanVienDAO;
import entity.CaLamViec;
import entity.ChiTietLuong;
import entity.ChiTietLuong;
import entity.NhanVien;
import entity.SanPham;
import helper.MsgBox;
import helper.XDate;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.plaf.synth.SynthTableHeaderUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class Luong extends javax.swing.JFrame {

    /**
     * Creates new form Luong
     */
    public Luong() {
        initComponents();
        fillTable();
        viTri = -1;
        buttonSh();
        nameCollumn();
        fillTableNV();
        txtNgayLamViec.setText(XDate.toString(now, "dd-MM-yyyy"));
        fillTable();
        fillCombobox();
        fillComboboxNV();
        txtMaNV.setEditable(false);
    }

    ChiTietLuongDAO luongDao = new ChiTietLuongDAO();
    java.util.List<ChiTietLuong> listLuong = luongDao.selectAll();
    CaLamViecDAO caLamViecDao = new CaLamViecDAO();
    java.util.List<CaLamViec> listCa = caLamViecDao.selectAll();
    NhanVienDAO nhanVienDao = new NhanVienDAO();
    java.util.List<NhanVien> listNV = nhanVienDao.selectAll();
    int mpX, mpY;
    Date now = new Date();
    String maNV;
    int viTri;

    public void nameCollumn() {
        JTableHeader tableHeader = tblNhanVien.getTableHeader();
        tableHeader.setUI(new SynthTableHeaderUI());
        Font HeaderFont = new Font("SansSerif", Font.PLAIN, 18);
        tableHeader.setOpaque(false);
        tableHeader.setBackground(new Color(81, 145, 255));
        tableHeader.setForeground(Color.white);

        tableHeader.setFont(HeaderFont);
        tblNhanVien.setRowHeight(25);

        JTableHeader tableHeaderDS = tblDanhSach.getTableHeader();
        tableHeaderDS.setUI(new SynthTableHeaderUI());
        tableHeaderDS.setOpaque(false);
        tableHeaderDS.setBackground(new Color(81, 145, 255));
        tableHeaderDS.setForeground(Color.white);

        tableHeaderDS.setFont(HeaderFont);
        tblDanhSach.setRowHeight(25);

    }

    public void buttonSh() {
        if (viTri < 0) {
            btnSua.setEffectColor(Color.lightGray);
            btnXoa.setEffectColor(Color.lightGray);
            buttonPaint(btnXoa, Color.LIGHT_GRAY);
            buttonPaint(btnSua, Color.LIGHT_GRAY);
            btnThem.setEffectColor(Color.WHITE);
            buttonPaint(btnThem, new Color(81, 145, 255));
        } else {
            btnSua.setEffectColor(Color.white);
            btnXoa.setEffectColor(Color.white);
            buttonPaint(btnSua, new Color(81, 145, 255));
            buttonPaint(btnXoa, Color.RED);
            btnThem.setEffectColor(Color.lightGray);
            buttonPaint(btnThem, Color.LIGHT_GRAY);
        }

    }

    public void buttonPaint(JButton b, Color c) {
        Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
        b.setBackground(c);
        b.setCursor(cursor);
    }

    public void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblDanhSach.getModel();
        model.setRowCount(0);
        String tenNV = "", tenCa = "";

        double luong = 0;
        listLuong = luongDao.selectAll();
        listCa = caLamViecDao.selectAll();
        for (int i = 0; i < listLuong.size(); i++) {
            for (int j = 0; j < listNV.size(); j++) {
                if (listNV.get(j).getChucVu().equals("Phục vụ")) {
                    if (listLuong.get(i).getMaNV().equals(listNV.get(j).getMaNV())) {
                        tenNV = listNV.get(j).getTenNV();
                        break;
                    }
                }
            }
            for (int j = 0; j < listCa.size(); j++) {
                if (listLuong.get(i).getMaCa() == (listCa.get(j).getMaCa())) {
                    luong = listCa.get(j).getLuong();
                    tenCa = listCa.get(j).getTenCa();
                    break;
                }
            }
            Object[] rows = new Object[]{listLuong.get(i).getMaLuongCT(), listLuong.get(i).getMaNV(), tenNV,
                listLuong.get(i).getNgayLamViec(), tenCa, luong
            };
            model.addRow(rows);
        }
    }

    public void fillTableNV() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        String tenNV = "";
        listNV = nhanVienDao.selectAll();
        for (int i = 0; i < listNV.size(); i++) {
            if (listNV.get(i).getChucVu().equals("Phục vụ")) {

                Object[] rows = new Object[]{listNV.get(i).getMaNV(), listNV.get(i).getTenNV(), listNV.get(i).getGioiTinh2()
                };
                model.addRow(rows);
            }
        }
    }

    public void fillCombobox() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboCaLam.getModel();
        model.removeAllElements();
        listCa = caLamViecDao.selectAll();
        model.addElement("---Chọn Ca---");
        for (int i = 0; i < listCa.size(); i++) {
            model.addElement(listCa.get(i).getTenCa());
        }

    }

    public void fillComboboxNV() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboSapXep.getModel();
        model.removeAllElements();
        listCa = caLamViecDao.selectAll();
        model.addElement("Tất cả");
        for (int i = 0; i < listNV.size(); i++) {
            if (listNV.get(i).getChucVu().equals("Phục vụ")) {
                model.addElement(listNV.get(i).getTenNV());
            }
        }

    }

    ChiTietLuong readForm() {
        ChiTietLuong chiTietLuong = new ChiTietLuong();
        for (int i = 0; i < listCa.size(); i++) {
            if (listCa.get(i).getTenCa().equals((String) cboCaLam.getSelectedItem())) {
                chiTietLuong.setMaCa(listCa.get(i).getMaCa());
                break;
            }
        }
        chiTietLuong.setMaLuongCT(txtMaLuongCT.getText());
        chiTietLuong.setMaNV(txtMaNV.getText());
        chiTietLuong.setNgayLamViec(XDate.toString(XDate.toDate(txtNgayLamViec.getText(), "dd-MM-yyyy"), "yyyy-MM-dd"));
        return chiTietLuong;
    }

    public void writeForm(ChiTietLuong chiTietLuong) {
        txtMaLuongCT.setText(chiTietLuong.getMaLuongCT());
        for (int i = 0; i < listCa.size(); i++) {
            if (chiTietLuong.getMaCa() == (listCa.get(i).getMaCa())) {
                cboCaLam.setSelectedItem(listCa.get(i).getTenCa());
                break;
            }
        }

        txtNgayLamViec.setText(chiTietLuong.getNgayLamViec());
        txtMaNV.setText(chiTietLuong.getMaNV());
    }

    public void showTable() {
        viTri = tblDanhSach.getSelectedRow();
        ChiTietLuong chiTietLuong = listLuong.get(viTri);
        writeForm(chiTietLuong);
        tabLoai.setSelectedIndex(0);
        buttonSh();
        txtMaLuongCT.setEditable(false);
    }

    public void clear() {
        ChiTietLuong chiTietLuong = new ChiTietLuong();
        txtMaLuongCT.setText("");
        txtMaNV.setText("");
        cboCaLam.setSelectedIndex(0);
        txtMaLuongCT.setEditable(true);
        txtNgayLamViec.setText(XDate.toString(now, "dd-MM-yyyy"));
        viTri = -1;
        buttonSh();
    }

    public void insert() {
        ChiTietLuong chiTietLuong = this.readForm();
        luongDao.insert(chiTietLuong);
        MsgBox.alert(this, "Thêm thành công");
        fillTable();
        clear();
    }

    public void update() {
        ChiTietLuong chiTietLuong = this.readForm();
        luongDao.update(chiTietLuong);
        MsgBox.alert(this, "Sửa thành công");
        fillTable();
        viTri = -1;
        buttonSh();
        clear();
    }

    public void remove() {
        viTri = tblDanhSach.getSelectedRow();
        boolean chon = MsgBox.confirm(this, "Bạn có chắc chắn xóa bàn này");
        DefaultTableModel model = (DefaultTableModel) tblDanhSach.getModel();
        if (chon) {
            String maSanPham = tblDanhSach.getValueAt(viTri, 0).toString();
            luongDao.delete(maSanPham);
            MsgBox.alert(this, "Đã xóa thành công");
            fillTable();
            clear();
        } else {
            MsgBox.alert(this, "Chưa được xóa");
        }
        viTri = -1;
    }

    public void showTableNV() {
        viTri = tblNhanVien.getSelectedRow();
        txtMaNV.setText(tblNhanVien.getValueAt(viTri, 0).toString());
        //txtMaNV.setText(listNV.get(viTri).getMaNV());
    }

    public boolean validateChiTietLuong() {
        String maChiTietLuong = txtMaLuongCT.getText();
        String maNV = txtMaNV.getText();
        String loi = "";
        if (btnThem.getBackground() != Color.LIGHT_GRAY) {
            if (maChiTietLuong.equals("")) {
                loi += "Vui lòng nhập mã lương\n";
            } else {
                for (int i = 0; i < listLuong.size(); i++) {
                    if (maChiTietLuong.equals(listLuong.get(i).getMaLuongCT())) {
                        loi += "Mã đã tồn tại\n";
                        break;
                    }
                }
            }
        }
        if (maNV.equals("")) {
            loi += "Vui lòng chọn nhân viên\n";
        }
        if (cboCaLam.getSelectedIndex() == 0) {
            loi += "Vui lòng chọn ca làm\n";
        }

        if (loi.length() > 0) {
            MsgBox.alert(this, loi);
            return false;
        } else {
            return true;

        }
    }

    public void selectTab(int index) {
        tabLoai.setSelectedIndex(index);
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
        tabLoai = new javax.swing.JTabbedPane();
        panelChamCong = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cboCaLam = new javax.swing.JComboBox<>();
        btnThem = new newpackage.Button();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        txtNgayLamViec = new javax.swing.JTextField();
        txtMaNV = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtMaLuongCT = new javax.swing.JTextField();
        btnSua = new newpackage.Button();
        btnXoa = new newpackage.Button();
        btnReNew = new newpackage.Button();
        PanelDanhSach = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDanhSach = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        cboSapXep = new javax.swing.JComboBox<>();
        pnlTitleBar = new javax.swing.JPanel();
        lblExit = new javax.swing.JLabel();
        lblMini = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 2, 2, 2, new java.awt.Color(81, 145, 255)));

        tabLoai.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabLoai.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        panelChamCong.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Ngày làm việc :");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Ca làm :");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Nhân viên :");

        cboCaLam.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboCaLam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ca 1", "Ca 2", "Ca 3", " " }));

        btnThem.setBackground(new java.awt.Color(81, 145, 255));
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Xác nhận");
        btnThem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblNhanVien.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã nhân viên", "Họ tên", "Giới tính"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.setFocusable(false);
        tblNhanVien.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblNhanVien.setRowHeight(25);
        tblNhanVien.setRowMargin(0);
        tblNhanVien.setSelectionBackground(new java.awt.Color(0, 102, 215));
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblNhanVien);

        txtNgayLamViec.setEditable(false);
        txtNgayLamViec.setBackground(new java.awt.Color(255, 255, 255));
        txtNgayLamViec.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtNgayLamViec.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtNgayLamViec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayLamViecActionPerformed(evt);
            }
        });

        txtMaNV.setEditable(false);
        txtMaNV.setBackground(new java.awt.Color(255, 255, 255));
        txtMaNV.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtMaNV.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtMaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaNVActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Mã lương :");

        txtMaLuongCT.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtMaLuongCT.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtMaLuongCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaLuongCTActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(81, 145, 255));
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setText("Sửa");
        btnSua.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(81, 145, 255));
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa");
        btnXoa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnReNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/restart_33px.png"))); // NOI18N
        btnReNew.setEffectColor(new java.awt.Color(204, 204, 204));
        btnReNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReNewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelChamCongLayout = new javax.swing.GroupLayout(panelChamCong);
        panelChamCong.setLayout(panelChamCongLayout);
        panelChamCongLayout.setHorizontalGroup(
            panelChamCongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChamCongLayout.createSequentialGroup()
                .addGroup(panelChamCongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelChamCongLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelChamCongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelChamCongLayout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelChamCongLayout.createSequentialGroup()
                                .addComponent(txtMaLuongCT)
                                .addGap(9, 9, 9))
                            .addGroup(panelChamCongLayout.createSequentialGroup()
                                .addGroup(panelChamCongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelChamCongLayout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(cboCaLam, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelChamCongLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(panelChamCongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtNgayLamViec, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(1, 17, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelChamCongLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(panelChamCongLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnReNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        panelChamCongLayout.setVerticalGroup(
            panelChamCongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChamCongLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelChamCongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelChamCongLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMaLuongCT, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboCaLam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNgayLamViec, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelChamCongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnThem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelChamCongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        //        Date now = new Date();
        //        txtNgayLamViec.setText(sdf.format(now) + "");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date now = new Date();
        txtNgayLamViec.setText(sdf.format(now) + "");

        tabLoai.addTab("CHẤM CÔNG", panelChamCong);

        PanelDanhSach.setBackground(new java.awt.Color(255, 255, 255));

        tblDanhSach.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblDanhSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã Lương", "Mã nhân viên", "Tên nhân viên", "Ngày làm", "Tên Ca", "Lương"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDanhSach.setFocusable(false);
        tblDanhSach.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblDanhSach.setRowHeight(25);
        tblDanhSach.setSelectionBackground(new java.awt.Color(0, 102, 215));
        tblDanhSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDanhSach);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setText("Sắp xếp:");

        cboSapXep.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboSapXep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Lương cao nhất", "Lương nhỏ nhất", " " }));
        cboSapXep.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboSapXepItemStateChanged(evt);
            }
        });
        cboSapXep.addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                cboSapXepHierarchyChanged(evt);
            }
        });
        cboSapXep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSapXepActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelDanhSachLayout = new javax.swing.GroupLayout(PanelDanhSach);
        PanelDanhSach.setLayout(PanelDanhSachLayout);
        PanelDanhSachLayout.setHorizontalGroup(
            PanelDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDanhSachLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(PanelDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelDanhSachLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(cboSapXep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 856, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        PanelDanhSachLayout.setVerticalGroup(
            PanelDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDanhSachLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboSapXep))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        tabLoai.addTab("CHI TIẾT LƯƠNG", PanelDanhSach);

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
                .addComponent(lblExit))
        );
        pnlTitleBarLayout.setVerticalGroup(
            pnlTitleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblMini, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Quản Lý Lương");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTitleBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tabLoai)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(pnlTitleBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tabLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
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

    private void cboSapXepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSapXepActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboSapXepActionPerformed

    private void txtNgayLamViecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayLamViecActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayLamViecActionPerformed

    private void txtMaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaNVActionPerformed

    private void txtMaLuongCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaLuongCTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaLuongCTActionPerformed

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        // TODO add your handling code here:
        showTableNV();
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        if (btnThem.getBackground() != Color.LIGHT_GRAY) {
            if (validateChiTietLuong()) {
                insert();
                fillTable();
            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        if (btnThem.getBackground() == Color.LIGHT_GRAY) {
            if (validateChiTietLuong()) {
                update();
                txtMaLuongCT.setEditable(true);
            } else {

            }
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        if (btnXoa.getBackground() != Color.LIGHT_GRAY) {
            remove();
        } else {

        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnReNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReNewActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btnReNewActionPerformed

    private void tblDanhSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachMouseClicked
        // TODO add your handling code here:
        showTable();
    }//GEN-LAST:event_tblDanhSachMouseClicked

    private void cboSapXepHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_cboSapXepHierarchyChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_cboSapXepHierarchyChanged

    private void cboSapXepItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboSapXepItemStateChanged
        // TODO add your handling code here:
        if (cboSapXep.getSelectedIndex() == 0) {
            fillTable();
        } else {
            DefaultTableModel model = (DefaultTableModel) tblDanhSach.getModel();
            model.setRowCount(0);
            String tenNV = "", tenCa = "";
            double luong = 0;
            listLuong = luongDao.selectByKeyword((String) cboSapXep.getSelectedItem());
            listCa = caLamViecDao.selectAll();
            for (int i = 0; i < listLuong.size(); i++) {
                for (int j = 0; j < listNV.size(); j++) {
                    if (listLuong.get(i).getMaNV().equals(listNV.get(j).getMaNV())) {
                        tenNV = listNV.get(j).getTenNV();
                        break;
                    }
                }
                for (int j = 0; j < listCa.size(); j++) {
                    if (listLuong.get(i).getMaCa() == (listCa.get(j).getMaCa())) {
                        luong = listCa.get(j).getLuong();
                        tenCa = listCa.get(j).getTenCa();
                        break;
                    }
                }
                Object[] rows = new Object[]{listLuong.get(i).getMaLuongCT(), listLuong.get(i).getMaNV(), tenNV,
                    listLuong.get(i).getNgayLamViec(), tenCa, luong
                };
                model.addRow(rows);
            }
        }
    }//GEN-LAST:event_cboSapXepItemStateChanged

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
            java.util.logging.Logger.getLogger(Luong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Luong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Luong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Luong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Luong().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelDanhSach;
    private newpackage.Button btnReNew;
    private newpackage.Button btnSua;
    private newpackage.Button btnThem;
    private newpackage.Button btnXoa;
    private javax.swing.JComboBox<String> cboCaLam;
    private javax.swing.JComboBox<String> cboSapXep;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblMini;
    private javax.swing.JPanel panelChamCong;
    private javax.swing.JPanel pnlTitleBar;
    private javax.swing.JTabbedPane tabLoai;
    private javax.swing.JTable tblDanhSach;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtMaLuongCT;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtNgayLamViec;
    // End of variables declaration//GEN-END:variables
}
