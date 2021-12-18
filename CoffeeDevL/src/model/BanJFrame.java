/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dao.BanDAO;
import dao.HoaDonDAO;
import entity.Ban;
import entity.HoaDon;
import helper.MsgBox;
import java.awt.Color;
import java.awt.*;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.plaf.synth.SynthTableHeaderUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author admin
 */
public class BanJFrame extends javax.swing.JFrame {

    /**
     * Creates new form QuanLyBanJFrame
     */
    public BanJFrame() {
        initComponents();
        fillTable();
        viTri = -1;
        buttonSh();
        nameCollumn();
    }
    BanDAO banDao = new BanDAO();
    List<Ban> list = banDao.selectAll();
    HoaDonDAO daoHD = new HoaDonDAO();
    List<HoaDon> listHD = daoHD.selectAll();
    int mpX, mpY;
    int viTri;

    public void nameCollumn() {
        JTableHeader tableHeader = tblBan.getTableHeader();
        tableHeader.setUI(new SynthTableHeaderUI());
        Font HeaderFont = new Font("SansSerif", Font.PLAIN, 18);
        tableHeader.setOpaque(false);
        tableHeader.setBackground(new Color(81, 145, 255));
        tableHeader.setForeground(Color.white);

        tableHeader.setFont(HeaderFont);
        tblBan.setRowHeight(25);

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
        DefaultTableModel model = (DefaultTableModel) tblBan.getModel();
        model.setRowCount(0);
        list = banDao.selectAll();
        for (int i = 0; i < list.size(); i++) {
            Object[] rows = new Object[]{list.get(i).getMaBan(), list.get(i).getTenBan()
            };
            model.addRow(rows);
        }

    }

    Ban readForm() {
        Ban ban = new Ban();
        ban.setMaBan(Integer.parseInt(txtMaBan.getText()));
        ban.setTenBan(txtTenBan.getText());
        ban.setTrangThai(false);
        return ban;
    }

    Ban readFormUpdate() {
        Ban ban = new Ban();
        ban.setMaBan(Integer.parseInt(txtMaBan.getText()));
        ban.setTenBan(txtTenBan.getText());

        return ban;
    }

    public void writeForm(Ban ban) {
        txtMaBan.setText(ban.getMaBan() + "");
        txtTenBan.setText(ban.getTenBan());
    }

    public void clear() {
        Ban ban = new Ban();
        writeForm(ban);
        txtMaBan.setText("");
        viTri = -1;
        buttonSh();
        txtMaBan.setEditable(true);
    }

    public void insert() {
        Ban ban = this.readForm();
        banDao.insert(ban);
        MsgBox.alert(this, "Thêm thành công");
        fillTable();
        clear();
    }

    public void update() {
        Ban ban = this.readFormUpdate();
        banDao.update(ban);
        MsgBox.alert(this, "Sửa thành công");
        fillTable();
        viTri = -1;
        buttonSh();
        clear();
    }

    public void showTable() {
        viTri = tblBan.getSelectedRow();
        Ban ban = list.get(viTri);
        writeForm(ban);
        tabBan.setSelectedIndex(0);
        buttonSh();
        txtMaBan.setEditable(false);
    }

    public void remove() {
        viTri = tblBan.getSelectedRow();
        boolean chon = MsgBox.confirm(this, "Bạn có chắc chắn xóa bàn này"), xoa = true;

        DefaultTableModel model = (DefaultTableModel) tblBan.getModel();
        if (chon) {
            String maBan = tblBan.getValueAt(viTri, 0).toString();
            boolean loi = new BanDAO().loi;
            for (int i = 0; i < listHD.size(); i++) {
                if (listHD.get(i).getBan() == Integer.parseInt(maBan)) {
                    MsgBox.alert(this, "Bạn không thể xóa bàn này!!\n Bàn này đã có hóa đơn!");
                    xoa = false;
                    break;
                }
            }
            if (xoa) {
                banDao.delete(maBan);
                fillTable();
                clear();
                MsgBox.alert(this, "Đã xóa thành công");
            }
        } else {
            MsgBox.alert(this, "Chưa được xóa");
        }

        viTri = -1;
    }

    public boolean validateBan() {
        String maBan = txtMaBan.getText();
        String tenBan = txtTenBan.getText();

        String loi = "";
        try {
            int so = Integer.parseInt(maBan);
            if (so <= 0) {
                loi += "Mã bàn phải lớn hơn 0\n";

            }
            if (btnThem.getBackground() != Color.LIGHT_GRAY) {
                for (int i = 0; i < list.size(); i++) {
                    if (so == list.get(i).getMaBan()) {
                        loi += "Mã đã tồn tại\n";
                        break;
                    }
                }
            }
        } catch (NumberFormatException e) {
            loi += "Vui lòng nhập mã bàn là số\n";
        }
        if (tenBan.equals("")) {
            loi += "Vui lòng nhập tên bàn\n";
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

        pnlTitleBarr = new javax.swing.JPanel();
        lblExit = new javax.swing.JLabel();
        lblMini = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        tabBan = new javax.swing.JTabbedPane();
        pnlCapNhat = new javax.swing.JPanel();
        lblMaBan = new javax.swing.JLabel();
        txtMaBan = new javax.swing.JTextField();
        txtTenBan = new javax.swing.JTextField();
        lblTenBan = new javax.swing.JLabel();
        btnXoa = new newpackage.Button();
        btnSua = new newpackage.Button();
        btnThem = new newpackage.Button();
        btnReNew = new newpackage.Button();
        pnlDanhSach = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBan = new javax.swing.JTable();
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

        tabBan.setBackground(new java.awt.Color(255, 255, 255));
        tabBan.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabBan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tabBan.setOpaque(true);

        pnlCapNhat.setBackground(new java.awt.Color(255, 255, 255));

        lblMaBan.setBackground(new java.awt.Color(255, 255, 255));
        lblMaBan.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMaBan.setText("MÃ BÀN :");

        txtMaBan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtMaBan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));

        txtTenBan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtTenBan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));

        lblTenBan.setBackground(new java.awt.Color(255, 255, 255));
        lblTenBan.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTenBan.setText("TÊN BÀN :");

        btnXoa.setBackground(new java.awt.Color(255, 0, 0));
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa");
        btnXoa.setFocusCycleRoot(true);
        btnXoa.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(81, 145, 255));
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setText("Sửa");
        btnSua.setFocusCycleRoot(true);
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

        btnReNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/restart_33px.png"))); // NOI18N
        btnReNew.setEffectColor(new java.awt.Color(204, 204, 204));
        btnReNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReNewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCapNhatLayout = new javax.swing.GroupLayout(pnlCapNhat);
        pnlCapNhat.setLayout(pnlCapNhatLayout);
        pnlCapNhatLayout.setHorizontalGroup(
            pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCapNhatLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTenBan)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCapNhatLayout.createSequentialGroup()
                        .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtMaBan, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenBan, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlCapNhatLayout.createSequentialGroup()
                                .addComponent(lblMaBan)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(pnlCapNhatLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 5, Short.MAX_VALUE)
                                .addComponent(btnReNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)
                                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(20, 20, 20))))
        );
        pnlCapNhatLayout.setVerticalGroup(
            pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCapNhatLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblMaBan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblTenBan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTenBan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnThem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnReNew, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );

        tabBan.addTab("Cập Nhật", pnlCapNhat);

        pnlDanhSach.setBackground(new java.awt.Color(255, 255, 255));

        tblBan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblBan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã bàn", "Tên bàn"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBan.setFocusable(false);
        tblBan.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblBan.setRowHeight(25);
        tblBan.setSelectionBackground(new java.awt.Color(0, 102, 255));
        tblBan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblBan);

        javax.swing.GroupLayout pnlDanhSachLayout = new javax.swing.GroupLayout(pnlDanhSach);
        pnlDanhSach.setLayout(pnlDanhSachLayout);
        pnlDanhSachLayout.setHorizontalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlDanhSachLayout.setVerticalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabBan.addTab("Danh Sách", pnlDanhSach);

        pnlTablePane.setBackground(new java.awt.Color(255, 255, 255));
        pnlTablePane.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(255, 255, 255)));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Quản Lý Bàn");

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
                    .addComponent(tabBan))
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(pnlTablePane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tabBan)
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

    private void pnlTitleBarrMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTitleBarrMousePressed
        // TODO add your handling code here:
        mpX = evt.getX();
        mpY = evt.getY();
    }//GEN-LAST:event_pnlTitleBarrMousePressed

    private void pnlTitleBarrMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTitleBarrMouseDragged
        // TODO add your handling code here:
        this.setLocation(
                getLocation().x + evt.getX() - mpX,
                getLocation().y + evt.getY() - mpY);
    }//GEN-LAST:event_pnlTitleBarrMouseDragged

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        if (btnThem.getBackground() != Color.LIGHT_GRAY) {
            if (validateBan()) {
                insert();
                fillTable();
            }
        }

    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        if (btnThem.getBackground() == Color.LIGHT_GRAY) {
            if (validateBan()) {
                update();
                txtMaBan.setEditable(true);
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

    private void tblBanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBanMouseClicked
        // TODO add your handling code here:
        showTable();

    }//GEN-LAST:event_tblBanMouseClicked

    private void btnReNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReNewActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btnReNewActionPerformed

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
            java.util.logging.Logger.getLogger(Ban.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ban.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ban.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ban.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BanJFrame().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private newpackage.Button btnReNew;
    private newpackage.Button btnSua;
    private newpackage.Button btnThem;
    private newpackage.Button btnXoa;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblMaBan;
    private javax.swing.JLabel lblMini;
    private javax.swing.JLabel lblTenBan;
    private javax.swing.JPanel pnlCapNhat;
    private javax.swing.JPanel pnlDanhSach;
    private javax.swing.JPanel pnlTablePane;
    private javax.swing.JPanel pnlTitleBarr;
    private javax.swing.JTabbedPane tabBan;
    private javax.swing.JTable tblBan;
    private javax.swing.JTextField txtMaBan;
    private javax.swing.JTextField txtTenBan;
    // End of variables declaration//GEN-END:variables
}
