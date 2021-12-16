/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.sun.java.swing.plaf.motif.MotifTabbedPaneUI;
import dao.CaLamViecDAO;
import entity.CaLamViec;
import helper.MsgBox;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.plaf.metal.MetalTabbedPaneUI;
import javax.swing.plaf.synth.SynthTableHeaderUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author admin
 */
public class CaLamViecJFrame extends javax.swing.JFrame {

    /**
     * Creates new form CaLamViec
     */
    public CaLamViecJFrame() {
        initComponents();
        nameCollumn();
        fillTable();
        viTri = -1;
        buttonSh();
    }
    CaLamViecDAO caLamViec = new CaLamViecDAO();
    List<CaLamViec> list = caLamViec.selectAll();
    int mpX, mpY;
    int viTri;

    public void nameCollumn() {
        JTableHeader tableHeader = tblCaLamViec.getTableHeader();
        tableHeader.setUI(new SynthTableHeaderUI());
        Font HeaderFont = new Font("SansSerif", Font.PLAIN, 18);
        tableHeader.setOpaque(false);
        tableHeader.setBackground(new Color(81, 145, 255));
        tableHeader.setForeground(Color.white);

        tableHeader.setFont(HeaderFont);
        tblCaLamViec.setRowHeight(25);

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

    public String cutTime(String s, JComboBox cbo) {
        s = s.substring(0, 5);
        int gio = Integer.parseInt(s.substring(0, 2));
        int phut = Integer.parseInt(s.substring(3, 5));
        String muiGio = "";
        if (gio >= 12) {
            if (gio > 12) {
                gio = gio - 12;
            }
            muiGio = " CH";
        } else {
            muiGio = " SA";
        }
        if (viTri > -1) {
            cbo.setSelectedItem(muiGio.trim());
        }
        if (gio < 10) {
            s = "0" + gio + ":";
        } else {
            s = gio + ":";
        }
        if (phut < 10) {
            s += "0" + phut + muiGio;
        } else {
            s += phut + muiGio;
        }
        return s;
    }

    public void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblCaLamViec.getModel();
        model.setRowCount(0);
        list = caLamViec.selectAll();
        for (int i = 0; i < list.size(); i++) {
            String gioBD = cutTime(list.get(i).getGioBatDau(), cboGioBD);
            String gioKT = cutTime(list.get(i).getGioKetThuc(), cboGioKT);
            Object[] rows = new Object[]{list.get(i).getMaCa(), list.get(i).getTenCa(), gioBD, gioKT, list.get(i).getLuong()
            };
            model.addRow(rows);
        }

    }

    public String setTime(String time, JTextField txt, JComboBox cbo) {
        String s = txt.getText();
        int gio = Integer.parseInt(s.substring(0, 2));
        int phut = Integer.parseInt(s.substring(3, 5));
        if (cbo.getSelectedItem().equals("CH")) {
            if (gio != 12) {
                gio = gio + 12;
            }
            time = gio + ":" + phut;
        } else {
            if (gio == 12) {
                time = "00:00";
            } else {
                time = s;
            }
        }

        return time;
    }

    CaLamViec readForm() {
        CaLamViec ca = new CaLamViec();
        String gioBD = "";
        String gioKT = "";
        gioBD = setTime(gioBD, txtGioBatDau, cboGioBD);
        gioKT = setTime(gioKT, txtGioKetThuc, cboGioKT);
        ca.setMaCa(Integer.parseInt(txtMaCa.getText()));
        ca.setTenCa(txtTenCa.getText());
        ca.setGioBatDau(gioBD);
        ca.setGioKetThuc(gioKT);
        ca.setLuong(Float.parseFloat(txtLuong.getText()));
        return ca;
    }

    public void writeForm(CaLamViec ca) {
        String gioBD = "";
        String gioKT = "";
        txtMaCa.setText(ca.getMaCa() + "");
        txtTenCa.setText(ca.getTenCa());
        if (ca.getGioBatDau() != null || ca.getGioKetThuc() != null) {
            gioBD = cutTime(ca.getGioBatDau(), cboGioBD);
            gioKT = cutTime(ca.getGioKetThuc(), cboGioKT);
            gioBD = gioBD.substring(0, 5);
            gioKT = gioKT.substring(0, 5);

        }
        txtGioBatDau.setText(gioBD);
        txtGioKetThuc.setText(gioKT);
        txtLuong.setText(ca.getLuong() + "");
    }

    public void clear() {
        CaLamViec ca = new CaLamViec();
        writeForm(ca);
        txtMaCa.setText("");
        viTri = -1;
        buttonSh();
        txtLuong.setText("");
        txtMaCa.setEditable(true);
    }

    public void insert() {
        CaLamViec ca = this.readForm();
        caLamViec.insert(ca);
        MsgBox.alert(this, "Thêm thành công");
        fillTable();
        clear();
    }

    public void update() {
        CaLamViec ca = this.readForm();
        caLamViec.update(ca);
        MsgBox.alert(this, "Sửa thành công");
        fillTable();
        viTri = -1;
        buttonSh();
        clear();
    }

    public void showTable() {
        viTri = tblCaLamViec.getSelectedRow();
        CaLamViec ca = list.get(viTri);
        writeForm(ca);
        tabCaLamViec.setSelectedIndex(0);
        buttonSh();
        txtMaCa.setEditable(false);
    }

    public void remove() {
        viTri = tblCaLamViec.getSelectedRow();
        boolean chon = MsgBox.confirm(this, "Bạn có chắc chắn xóa ca làm này");
        DefaultTableModel model = (DefaultTableModel) tblCaLamViec.getModel();
        if (chon) {
            String maCaLamViec = tblCaLamViec.getValueAt(viTri, 0).toString();
            caLamViec.delete(maCaLamViec);
            MsgBox.alert(this, "Đã xóa thành công");
            fillTable();
            clear();
        } else {
            MsgBox.alert(this, "Chưa được xóa");
        }
        viTri = -1;
    }

    public String valiTime(JTextField txt, JComboBox cbo) {
        String loi = "";
        int gio = Integer.parseInt(txt.getText().replace(":", ""));
        int gio1 = Integer.parseInt(txt.getText().substring(0, 2));
        if (cbo.getSelectedItem().equals("CH")) {
            if (gio1 < 12) {
                gio = gio + 1200;
            }
        } else {
            if (gio1 == 12) {
                gio = gio - 1200;
            }
        }
        if (gio < 600 || gio > 2300) {
            loi += " phải từ 6:00 SA đến 23:00 CH\n";
        }
        return loi;
    }

    public boolean validateCaLamViec() {
        String maCaLamViec = txtMaCa.getText();
        String tenCaLamViec = txtTenCa.getText();
        String gioBatDau = txtGioBatDau.getText();
        String gioKetThuc = txtGioKetThuc.getText();
        String luong = txtLuong.getText();

        String loi = "";
        try {
            int so = Integer.parseInt(maCaLamViec);
            if (so <= 0) {
                loi += "Mã Ca phải lớn hơn 0\n";

            }
            if (btnThem.getBackground() != Color.LIGHT_GRAY) {
                for (int i = 0; i < list.size(); i++) {
                    if (so == list.get(i).getMaCa()) {
                        loi += "Mã ca đã tồn tại\n";
                        break;
                    }
                }
            }
        } catch (NumberFormatException e) {
            loi += "Vui lòng nhập mã ca là số\n";
        }
        if (tenCaLamViec.equals("")) {
            loi += "Vui lòng nhập tên ca\n";
        }
        if (gioBatDau.equals("")) {
            loi += "Vui lòng nhập giờ bắt đầu \n";
        } else if (gioBatDau.length() < 5) {
            loi += "Vui lòng nhập đúng định dạng giờ (VD: 06:00)\n";
        } else if (valiTime(txtGioBatDau, cboGioBD).length() > 0) {
            loi += "Giờ bắt đầu";
            loi += valiTime(txtGioBatDau, cboGioBD);
        }
        if (gioKetThuc.equals("")) {
            loi += "Vui lòng nhập giờ kết thúc \n";
        } else if (gioKetThuc.length() < 5) {
            loi += "Vui lòng nhập đúng định dạng giờ (VD: 06:00)\n";
        } else if (valiTime(txtGioKetThuc, cboGioKT).length() > 0) {
            loi += "Giờ kết thúc";
            loi += valiTime(txtGioKetThuc, cboGioKT);
        }
        if (gioBatDau.equals(gioKetThuc) && cboGioBD.getSelectedItem().equals(cboGioKT.getSelectedItem())) {
            loi += "Giờ bắt đầu và kết thúc không đuọc trùng\n";
        }
        int gioBD = Integer.parseInt(txtGioBatDau.getText().replace(":", ""));
        int gioKT = Integer.parseInt(txtGioKetThuc.getText().replace(":", ""));
        if (cboGioBD.getSelectedItem().equals("CH")) {
            if (gioBD < 1200) {
                gioBD = gioBD + 1200;
            }
        }
        if (cboGioKT.getSelectedItem().equals("CH")) {
            if (gioKT < 1200) {
                gioKT = gioKT + 1200;
            }
        }
        if (gioKT < gioBD) {
            loi += "Giờ bắt đầu không được lớn giờ kết thúc\n";
        } else if (gioKT - gioBD < 100) {
            loi += "Một ca làm ít nhất là 1 tiếng\n";
        }
        for (int i = 0; i < list.size(); i++) {
            if (gioBatDau.equals(cutTime(list.get(i).getGioBatDau(), cboGioBD).substring(0, 5))) {
                int so = Integer.parseInt(maCaLamViec);
                if (btnThem.getBackground() != Color.LIGHT_GRAY) {
                    loi += "Giờ bắt đầu đã tồn tại\n";
                } else if (so != list.get(i).getMaCa()) {
                    loi += "Giờ bắt đầu đã tồn tại\n";
                }
                break;
            }
        }

        try {
            float tien = Float.parseFloat(luong);
            if (tien <= 0) {
                loi += "Lương phải lớn hơn 0\n";
            }
        } catch (NumberFormatException e) {
            loi += "Vui lòng nhập lương là số\n";
        }
        if (loi.length() > 0) {
            MsgBox.alert(this, loi);
            return false;
        } else {
            return true;

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

        jPanel1 = new javax.swing.JPanel();
        tabCaLamViec = new javax.swing.JTabbedPane();
        panelCapNhat = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtMaCa = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtTenCa = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtLuong = new javax.swing.JTextField();
        btnThem = new newpackage.Button();
        btnXoa = new newpackage.Button();
        btnSua = new newpackage.Button();
        cboGioKT = new javax.swing.JComboBox<>();
        cboGioBD = new javax.swing.JComboBox<>();
        txtGioKetThuc = new javax.swing.JTextField();
        txtGioBatDau = new javax.swing.JTextField();
        lblVND = new javax.swing.JLabel();
        btnReNew = new newpackage.Button();
        panelDanhSach = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCaLamViec = new javax.swing.JTable();
        pnlTitleBarr17 = new javax.swing.JPanel();
        lblExit = new javax.swing.JLabel();
        lblMini = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 2, 2, 2, new java.awt.Color(81, 145, 255)));

        tabCaLamViec.setBackground(new java.awt.Color(255, 255, 255));
        tabCaLamViec.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabCaLamViec.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        panelCapNhat.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Mã ca:");

        txtMaCa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtMaCa.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Tên ca:");

        txtTenCa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtTenCa.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Giờ bắt đầu: ");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Giờ kết thúc:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Lương: ");

        txtLuong.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtLuong.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLuongActionPerformed(evt);
            }
        });

        btnThem.setBackground(new java.awt.Color(81, 145, 255));
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm ");
        btnThem.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(255, 0, 0));
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa");
        btnXoa.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(81, 145, 255));
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setText("Sửa");
        btnSua.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        cboGioKT.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboGioKT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SA", "CH" }));
        cboGioKT.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        cboGioKT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboGioKTActionPerformed(evt);
            }
        });

        cboGioBD.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboGioBD.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SA", "CH" }));
        cboGioBD.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        cboGioBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboGioBDActionPerformed(evt);
            }
        });

        txtGioKetThuc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtGioKetThuc.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtGioKetThuc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtGioKetThucFocusLost(evt);
            }
        });
        txtGioKetThuc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtGioKetThucKeyPressed(evt);
            }
        });

        txtGioBatDau.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtGioBatDau.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtGioBatDau.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtGioBatDauFocusLost(evt);
            }
        });
        txtGioBatDau.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtGioBatDauKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGioBatDauKeyReleased(evt);
            }
        });

        lblVND.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblVND.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblVND.setText("VND");
        lblVND.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));

        btnReNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/restart_33px.png"))); // NOI18N
        btnReNew.setEffectColor(new java.awt.Color(204, 204, 204));
        btnReNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReNewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCapNhatLayout = new javax.swing.GroupLayout(panelCapNhat);
        panelCapNhat.setLayout(panelCapNhatLayout);
        panelCapNhatLayout.setHorizontalGroup(
            panelCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCapNhatLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panelCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCapNhatLayout.createSequentialGroup()
                        .addGroup(panelCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelCapNhatLayout.createSequentialGroup()
                        .addGroup(panelCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCapNhatLayout.createSequentialGroup()
                                .addComponent(txtGioBatDau)
                                .addGap(0, 0, 0)
                                .addComponent(cboGioBD, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCapNhatLayout.createSequentialGroup()
                                .addComponent(btnReNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 186, Short.MAX_VALUE)
                                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtMaCa)
                            .addComponent(txtTenCa)
                            .addGroup(panelCapNhatLayout.createSequentialGroup()
                                .addGroup(panelCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCapNhatLayout.createSequentialGroup()
                                .addGroup(panelCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtLuong)
                                    .addComponent(txtGioKetThuc))
                                .addGroup(panelCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cboGioKT, 0, 66, Short.MAX_VALUE)
                                    .addComponent(lblVND, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(10, 10, 10))))
        );
        panelCapNhatLayout.setVerticalGroup(
            panelCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCapNhatLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaCa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenCa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboGioBD, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGioBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(11, 11, 11)
                .addGroup(panelCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboGioKT, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGioKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblVND, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtLuong, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnReNew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(53, 53, 53))
        );

        tabCaLamViec.addTab("CẬP NHẬT", panelCapNhat);

        panelDanhSach.setBackground(new java.awt.Color(255, 255, 255));

        tblCaLamViec.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblCaLamViec.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã ca", "Tên ca", "Giờ bắt đầu", "Giờ kết thúc", "Lương"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCaLamViec.setFocusable(false);
        tblCaLamViec.setRowHeight(25);
        tblCaLamViec.setRowMargin(0);
        tblCaLamViec.setSelectionBackground(new java.awt.Color(0, 102, 255));
        tblCaLamViec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCaLamViecMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCaLamViec);

        javax.swing.GroupLayout panelDanhSachLayout = new javax.swing.GroupLayout(panelDanhSach);
        panelDanhSach.setLayout(panelDanhSachLayout);
        panelDanhSachLayout.setHorizontalGroup(
            panelDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDanhSachLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        panelDanhSachLayout.setVerticalGroup(
            panelDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDanhSachLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabCaLamViec.addTab("DANH SÁCH", panelDanhSach);

        pnlTitleBarr17.setBackground(new java.awt.Color(81, 145, 255));
        pnlTitleBarr17.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlTitleBarr17MouseDragged(evt);
            }
        });
        pnlTitleBarr17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlTitleBarr17MousePressed(evt);
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

        javax.swing.GroupLayout pnlTitleBarr17Layout = new javax.swing.GroupLayout(pnlTitleBarr17);
        pnlTitleBarr17.setLayout(pnlTitleBarr17Layout);
        pnlTitleBarr17Layout.setHorizontalGroup(
            pnlTitleBarr17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTitleBarr17Layout.createSequentialGroup()
                .addGap(0, 613, Short.MAX_VALUE)
                .addComponent(lblMini)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblExit))
        );
        pnlTitleBarr17Layout.setVerticalGroup(
            pnlTitleBarr17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblMini, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Ca làm việc");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pnlTitleBarr17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(tabCaLamViec, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnlTitleBarr17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel8)
                .addGap(0, 0, 0)
                .addComponent(tabCaLamViec, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void txtLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLuongActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        if (btnXoa.getBackground() != Color.LIGHT_GRAY) {
            remove();
        } else {

        }
    }//GEN-LAST:event_btnXoaActionPerformed

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

    private void pnlTitleBarr17MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTitleBarr17MouseDragged

        // TODO add your handling code here:
        this.setLocation(
                getLocation().x + evt.getX() - mpX,
                getLocation().y + evt.getY() - mpY);
    }//GEN-LAST:event_pnlTitleBarr17MouseDragged

    private void pnlTitleBarr17MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTitleBarr17MousePressed
        // TODO add your handling code here:
        mpX = evt.getX();
        mpY = evt.getY();
    }//GEN-LAST:event_pnlTitleBarr17MousePressed

    private void cboGioBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboGioBDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboGioBDActionPerformed

    private void cboGioKTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboGioKTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboGioKTActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        if (validateCaLamViec()) {
            insert();
            txtMaCa.setEditable(true);
        } else {

        }


    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        if (btnThem.getBackground() == Color.LIGHT_GRAY) {
            if (validateCaLamViec()) {
                update();
                txtMaCa.setEditable(true);
            } else {

            }
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void tblCaLamViecMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCaLamViecMouseClicked
        // TODO add your handling code here:
        showTable();
    }//GEN-LAST:event_tblCaLamViecMouseClicked

    private void btnReNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReNewActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btnReNewActionPerformed

    private void txtGioBatDauKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGioBatDauKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_txtGioBatDauKeyReleased

    private void txtGioBatDauKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGioBatDauKeyPressed
        // TODO add your handling code here:
        nhapGio(evt, txtGioBatDau);
    }//GEN-LAST:event_txtGioBatDauKeyPressed

    private void txtGioBatDauFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGioBatDauFocusLost
        // TODO add your handling code here:
        txtGioBatDau.setEditable(true);
    }//GEN-LAST:event_txtGioBatDauFocusLost

    private void txtGioKetThucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGioKetThucKeyPressed
        // TODO add your handling code here:
        nhapGio(evt, txtGioKetThuc);
    }//GEN-LAST:event_txtGioKetThucKeyPressed

    private void txtGioKetThucFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGioKetThucFocusLost
        // TODO add your handling code here:
        txtGioKetThuc.setEditable(true);
    }//GEN-LAST:event_txtGioKetThucFocusLost

    public void nhapGio(KeyEvent evt, JTextField txt) {
        String parten = "[0-9]";
        String nhap = String.valueOf(evt.getKeyChar());
        String text = txt.getText();
        if (evt.getKeyChar() == evt.VK_BACK_SPACE) {
            txt.setEditable(true);
        } else {
            if (!nhap.matches(parten)) {
                txt.setEditable(false);
            } else if (text.length() < 1) {
                String parten1 = "[0-1]";
                if (!nhap.matches(parten1)) {
                    txt.setText("0");
                }
            } else {
                txt.setEditable(true);
                String parten2 = "[0-5]";
                if (text.length() == 2) {
                    txt.setText(text + ":");
                    if (!nhap.matches(parten2)) {
                        txt.setText(text + ":0");
                    }
                }
                if (text.length() == 3) {
                    if (!nhap.matches(parten2)) {
                        txt.setText(text + "0");
                    }
                }
            }
            if (text.length() == 5) {
                txt.setEditable(false);
            }
            if (text.length() > 5) {
                txt.setText("");
            }
        }
    }

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
            java.util.logging.Logger.getLogger(CaLamViec.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CaLamViec.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CaLamViec.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CaLamViec.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CaLamViecJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private newpackage.Button btnReNew;
    private newpackage.Button btnSua;
    private newpackage.Button btnThem;
    private newpackage.Button btnXoa;
    private javax.swing.JComboBox<String> cboGioBD;
    private javax.swing.JComboBox<String> cboGioKT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblMini;
    private javax.swing.JLabel lblVND;
    private javax.swing.JPanel panelCapNhat;
    private javax.swing.JPanel panelDanhSach;
    private javax.swing.JPanel pnlTitleBarr17;
    private javax.swing.JTabbedPane tabCaLamViec;
    private javax.swing.JTable tblCaLamViec;
    private javax.swing.JTextField txtGioBatDau;
    private javax.swing.JTextField txtGioKetThuc;
    private javax.swing.JTextField txtLuong;
    private javax.swing.JTextField txtMaCa;
    private javax.swing.JTextField txtTenCa;
    // End of variables declaration//GEN-END:variables
}
