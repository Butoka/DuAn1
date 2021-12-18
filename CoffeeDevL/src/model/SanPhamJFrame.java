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
import com.sun.javafx.application.PlatformImpl;
import dao.HoaDonCTDAO;
import dao.LoaiSanPhamDAO;
import dao.SanPhamDAO;
import entity.HoaDonCT;
import entity.LoaiSanPham;
import entity.SanPham;
import helper.MsgBox;
import helper.XDate;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import javax.swing.*;
import javax.swing.plaf.basic.BasicPanelUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.plaf.synth.SynthTableHeaderUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import net.miginfocom.swing.MigLayout;

public class SanPhamJFrame extends javax.swing.JFrame {

    /**
     * Creates new form NhanVienJFrame
     */
    public SanPhamJFrame() {
        initComponents();
        txtNgayNhap.setText(sdf.format(now) + "");
        fillTable();
        viTri = -1;
        buttonSh();
        nameCollumn();
        pnlTrangThai.setVisible(false);
        fillCombobox();
    }
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    SanPhamDAO sanPhamDao = new SanPhamDAO();
    List<SanPham> list = sanPhamDao.selectAll();
    LoaiSanPhamDAO loaiDao = new LoaiSanPhamDAO();
    List<LoaiSanPham> listLoai = loaiDao.selectAll();
    HoaDonCTDAO daoHDCT = new HoaDonCTDAO();
    List<HoaDonCT> listHDCT = daoHDCT.selectAll();
    int mpX, mpY;
    Date now = new Date();
    int viTri;
    String TruocDo, duongDanAnh;

    public void nameCollumn() {
        JTableHeader tableHeader = tblSanPham.getTableHeader();
        tableHeader.setUI(new SynthTableHeaderUI());
        Font HeaderFont = new Font("SansSerif", Font.PLAIN, 18);
        tableHeader.setOpaque(false);
        tableHeader.setBackground(new Color(81, 145, 255));
        tableHeader.setForeground(Color.white);

        tableHeader.setFont(HeaderFont);
        tblSanPham.setRowHeight(25);

    }

    public String anh() {
        JFileChooser chooser = new JFileChooser();
        if (TruocDo != null) {
            chooser.setCurrentDirectory(new File(TruocDo));//Set thư mục đã chọn trc đó
        } else {
            String userDir = System.getProperty("user.home");// Lấy tên PC
            chooser = new JFileChooser(userDir + "/Downloads");

        }
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        if (f != null) {
            InputStream is = null;
            OutputStream os = null;
            try {
                is = new FileInputStream(new File(f.getPath()));
                os = new FileOutputStream(new File("src\\Image\\" + f.getName()));
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SanPhamJFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SanPhamJFrame.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    is.close();
                    os.close();
                } catch (IOException ex) {
                    Logger.getLogger(SanPhamJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (f != null) {
            TruocDo = f.getPath();
            int cuoi = TruocDo.lastIndexOf("\\");
            TruocDo = TruocDo.substring(0, cuoi);
            ImageIcon hinhAnh = new ImageIcon(new ImageIcon("src\\Image\\" + f.getName()).getImage().getScaledInstance(lblHinhAnh.getWidth(), lblHinhAnh.getHeight(), Image.SCALE_SMOOTH));
            lblHinhAnh.setIcon(hinhAnh);
            duongDanAnh = f.getName();
            txtHinhAnh.setText(duongDanAnh);
        }
        return TruocDo;
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
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        list = sanPhamDao.selectByKeyword(txtTimKiem.getText());
        String trangThai = "";
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isTrangThai()) {
                trangThai = "Kinh Doanh";
            } else {
                trangThai = "Ngừng Kinh Doanh";
            }
            Object[] rows = new Object[]{list.get(i).getMaSP(), list.get(i).getTenSP(), list.get(i).getLoaiSP(),
                list.get(i).getSoLuong(), list.get(i).getGiaNhap(), list.get(i).getGiaBan(), list.get(i).getNgayNhap(),
                list.get(i).getMoTa(), trangThai
            };
            model.addRow(rows);
        }
    }

    public void fillCombobox() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboLoai.getModel();
        model.removeAllElements();
        listLoai = loaiDao.selectAll();
        model.addElement("---Chọn Loại---");
        for (int i = 0; i < listLoai.size(); i++) {
            model.addElement(listLoai.get(i).getTenLoai());
        }

    }

    SanPham readForm() {
        SanPham sanPham = new SanPham();
        sanPham.setMaSP(txtMaSP.getText());
        sanPham.setTenSP(txtTenSP.getText());
        for (int i = 0; i < listLoai.size(); i++) {
            if (cboLoai.getSelectedItem().equals(listLoai.get(i).getTenLoai())) {
                sanPham.setLoaiSP(listLoai.get(i).getMaLoai());
                break;
            }
        }
        sanPham.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
        sanPham.setGiaNhap(Double.parseDouble(txtGiaNhap.getText()));
        sanPham.setGiaBan(Double.parseDouble(txtGiaBan.getText()));
        sanPham.setNgayNhap(XDate.toString(XDate.toDate(txtNgayNhap.getText(), "dd-MM-yyyy"), "yyyy-MM-dd"));
        sanPham.setMoTa(txtMoTa.getText());
        sanPham.setHinhAnh(txtHinhAnh.getText());
        if (btnThem.getBackground() != Color.LIGHT_GRAY) {
            sanPham.setTrangThai(true);
        } else {
            if (cboTrangThai.getSelectedIndex() == 0) {
                sanPham.setTrangThai(true);
            } else {
                sanPham.setTrangThai(false);
            }
        }
        return sanPham;
    }

    public void writeForm(SanPham sanPham) {
        txtMaSP.setText(sanPham.getMaSP());
        txtTenSP.setText(sanPham.getTenSP());
        for (int i = 0; i < listLoai.size(); i++) {
            if (sanPham.getLoaiSP().equals(listLoai.get(i).getMaLoai())) {
                cboLoai.setSelectedItem(listLoai.get(i).getTenLoai());
                break;
            }
        }
        txtGiaNhap.setText(sanPham.getGiaNhap() + "");
        txtGiaBan.setText(sanPham.getGiaBan() + "");
        txtSoLuong.setText(sanPham.getSoLuong() + "");
        txtMoTa.setText(sanPham.getMoTa());
        txtHinhAnh.setText(sanPham.getHinhAnh());
        ImageIcon hinhAnh = new ImageIcon(new ImageIcon("src\\Image\\" + sanPham.getHinhAnh()).getImage().getScaledInstance(244, 316, Image.SCALE_SMOOTH));
        lblHinhAnh.setIcon(hinhAnh);
        txtNgayNhap.setText(sanPham.getNgayNhap());
        if (sanPham.isTrangThai()) {
            cboTrangThai.setSelectedIndex(0);
        } else {
            cboTrangThai.setSelectedIndex(1);
        }
    }

    public void clear() {
        SanPham sanPham = new SanPham();
        txtMaSP.setText("");
        txtGiaBan.setText("");
        txtTenSP.setText("");
        txtGiaNhap.setText("");
        txtSoLuong.setText("");
        cboLoai.setSelectedIndex(0);
        lblHinhAnh.setIcon(null);
        txtNgayNhap.setText(sdf.format(now) + "");
        cboTrangThai.setSelectedIndex(0);
        pnlTrangThai.setVisible(false);
        viTri = -1;
        txtMoTa.setText("");
        txtHinhAnh.setText("");
        buttonSh();
        txtMaSP.setEditable(true);
    }

    public void insert() {
        SanPham sanPham = this.readForm();
        sanPhamDao.insert(sanPham);
        MsgBox.alert(this, "Thêm thành công");
        fillTable();
        clear();
    }

    public void update() {
        SanPham sanPham = this.readForm();
        sanPhamDao.update(sanPham);
        MsgBox.alert(this, "Sửa thành công");
        fillTable();
        viTri = -1;
        buttonSh();
        clear();
    }

    public void showTable() {
        viTri = tblSanPham.getSelectedRow();
        SanPham sanPham = list.get(viTri);
        writeForm(sanPham);
        tabSanPham.setSelectedIndex(0);
        pnlTrangThai.setVisible(true);
        buttonSh();
        txtMaSP.setEditable(false);
    }

    public void remove() {
        viTri = tblSanPham.getSelectedRow();
        boolean chon = MsgBox.confirm(this, "Bạn có chắc chắn xóa bàn này"), xoa = true;
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        String maSanPham = txtMaSP.getText();
        if (chon) {
            for (int i = 0; i < listHDCT.size(); i++) {
                if (listHDCT.get(i).getMaSP().equals(maSanPham)) {
                    MsgBox.alert(this, "Sản phẩm này không được xóa!!!");
                    xoa = false;
                    break;
                }
            }
            if (xoa) {
                sanPhamDao.delete(maSanPham);
                MsgBox.alert(this, "Đã xóa thành công");
                fillTable();
                clear();
            }

        } else {
            MsgBox.alert(this, "Chưa được xóa");
        }
        viTri = -1;
    }

    public boolean validateSanPham() {
        String maSanPham = txtMaSP.getText();
        String tenSanPham = txtTenSP.getText();
        String soLuong = txtSoLuong.getText();
        String giaNhap = txtGiaNhap.getText();
        String giaBan = txtGiaBan.getText();
        String loi = "";
        if (btnThem.getBackground() != Color.LIGHT_GRAY) {
            if (maSanPham.equals("")) {
                loi += "Vui lòng nhập mã sản phẩm\n";
            } else {
                for (int i = 0; i < list.size(); i++) {
                    if (maSanPham.equals(list.get(i).getMaSP())) {
                        loi += "Mã đã tồn tại\n";
                        break;
                    }
                }
            }
        }
        if (tenSanPham.equals("")) {
            loi += "Vui lòng nhập tên sản phẩm\n";
        }
        try {
            int so = Integer.parseInt(soLuong);
            if (so <= 0) {
                loi += "Số lượng phải lớn hơn 0\n";

            } else if (so > 2147483647) {
                loi += "Số lượng quá lớn \n";
            }

        } catch (NumberFormatException e) {
            loi += "Vui lòng nhập số lượng là số\n";
        }
        try {
            Double gia = Double.parseDouble(giaNhap);
            if (gia <= 0) {
                loi += "Giá nhập phải lớn hơn 0\n";

            }

        } catch (NumberFormatException e) {
            loi += "Vui lòng nhập giá nhập là số\n";
        }
        try {
            Double gia = Double.parseDouble(giaBan);
            if (gia <= 0) {
                loi += "Giá bán phải lớn hơn 0\n";

            }

        } catch (NumberFormatException e) {
            loi += "Vui lòng nhập giá bán là số\n";
        }
        if (cboLoai.getSelectedIndex() == 0) {
            loi += "Vui lòng chọn loại\n";
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

        jSlider1 = new javax.swing.JSlider();
        pnlTitleBar = new javax.swing.JPanel();
        lblExit = new javax.swing.JLabel();
        lblMaxi = new javax.swing.JLabel();
        lblMini = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        pnlTablePane = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tabSanPham = new javax.swing.JTabbedPane();
        pnlCapNhat = new javax.swing.JPanel();
        lblMaSP = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        txtTenSP = new javax.swing.JTextField();
        lblTenSP = new javax.swing.JLabel();
        lblGiaBan = new javax.swing.JLabel();
        cboLoai = new javax.swing.JComboBox<>();
        lblLoai = new javax.swing.JLabel();
        btnXoa = new newpackage.Button();
        btnSua = new newpackage.Button();
        btnThem = new newpackage.Button();
        txtSoLuong = new javax.swing.JTextField();
        lblSoLuong = new javax.swing.JLabel();
        lblHinhAnh = new javax.swing.JLabel();
        txtGiaBan = new javax.swing.JTextField();
        lblGiaNhap = new javax.swing.JLabel();
        txtGiaNhap = new javax.swing.JTextField();
        lblNgayNhap = new javax.swing.JLabel();
        lblMoTa = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        txtNgayNhap = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnThemAnh = new javax.swing.JButton();
        txtHinhAnh = new javax.swing.JTextField();
        pnlTrangThai = new javax.swing.JPanel();
        lblTrangThai = new javax.swing.JLabel();
        cboTrangThai = new javax.swing.JComboBox<>();
        btnReNew = new newpackage.Button();
        pnlDanhSach = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
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

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Quản Lí Sản Phẩm");

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

        tabSanPham.setBackground(new java.awt.Color(255, 255, 255));
        tabSanPham.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabSanPham.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tabSanPham.setOpaque(true);
        tabSanPham.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabSanPhamStateChanged(evt);
            }
        });

        pnlCapNhat.setBackground(new java.awt.Color(255, 255, 255));

        lblMaSP.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMaSP.setText("MÃ SẢN PHẨM:");

        txtMaSP.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtMaSP.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));

        txtTenSP.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtTenSP.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));

        lblTenSP.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTenSP.setText("TÊN SẢN PHẨM :");

        lblGiaBan.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblGiaBan.setText("GIÁ BÁN :");

        cboLoai.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboLoai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Chọn Lọai---", "Nam", "Nữ", "Khác" }));
        cboLoai.setOpaque(false);
        cboLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLoaiActionPerformed(evt);
            }
        });

        lblLoai.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblLoai.setText("LOẠI :");

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

        txtSoLuong.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtSoLuong.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoLuongActionPerformed(evt);
            }
        });

        lblSoLuong.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblSoLuong.setText("SỐ LƯỢNG :");

        lblHinhAnh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtGiaBan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtGiaBan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));

        lblGiaNhap.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblGiaNhap.setText("GIÁ NHẬP :");

        txtGiaNhap.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtGiaNhap.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtGiaNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaNhapActionPerformed(evt);
            }
        });

        lblNgayNhap.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblNgayNhap.setText("NGÀY NHẬP :");

        lblMoTa.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMoTa.setText("MÔ TẢ:");

        txtMoTa.setColumns(20);
        txtMoTa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtMoTa.setLineWrap(true);
        txtMoTa.setRows(5);
        txtMoTa.setWrapStyleWord(true);
        txtMoTa.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jScrollPane2.setViewportView(txtMoTa);

        txtNgayNhap.setEditable(false);
        txtNgayNhap.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtNgayNhap.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        btnThemAnh.setBackground(new java.awt.Color(255, 255, 255));
        btnThemAnh.setText("...");
        btnThemAnh.setBorder(null);
        btnThemAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemAnhActionPerformed(evt);
            }
        });

        txtHinhAnh.setEditable(false);
        txtHinhAnh.setBackground(new java.awt.Color(255, 255, 255));
        txtHinhAnh.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtHinhAnh.setBorder(null);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(txtHinhAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(btnThemAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtHinhAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(btnThemAnh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        pnlTrangThai.setBackground(new java.awt.Color(255, 255, 255));

        lblTrangThai.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTrangThai.setText("TRẠNG THÁI:");

        cboTrangThai.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kinh Doanh", "Ngừng Khinh Doanh" }));
        cboTrangThai.setOpaque(false);
        cboTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTrangThaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlTrangThaiLayout = new javax.swing.GroupLayout(pnlTrangThai);
        pnlTrangThai.setLayout(pnlTrangThaiLayout);
        pnlTrangThaiLayout.setHorizontalGroup(
            pnlTrangThaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTrangThaiLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(pnlTrangThaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTrangThai)
                    .addGroup(pnlTrangThaiLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlTrangThaiLayout.setVerticalGroup(
            pnlTrangThaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTrangThaiLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(lblTrangThai)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

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
                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlCapNhatLayout.createSequentialGroup()
                                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                                        .addComponent(lblSoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(598, 598, 598))
                                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                                        .addComponent(lblMaSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(570, 570, 570))
                                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                                        .addComponent(lblTenSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(557, 557, 557))
                                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                                        .addComponent(lblGiaNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(603, 603, 603))
                                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                                        .addComponent(lblGiaBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(617, 617, 617))
                                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                                        .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtSoLuong, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtGiaNhap)
                                            .addComponent(txtTenSP, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtMaSP, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtGiaBan, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblHinhAnh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(txtNgayNhap)
                            .addGroup(pnlCapNhatLayout.createSequentialGroup()
                                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                                        .addComponent(lblNgayNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(688, 688, 688))
                                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                                        .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblLoai)
                                            .addComponent(cboLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addComponent(pnlTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(187, 187, 187))))
                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(pnlCapNhatLayout.createSequentialGroup()
                                .addComponent(lblMoTa)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCapNhatLayout.createSequentialGroup()
                                .addComponent(btnReNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(10, 10, 10))
        );
        pnlCapNhatLayout.setVerticalGroup(
            pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCapNhatLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                        .addComponent(lblMaSP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTenSP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSoLuong)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblGiaNhap)
                        .addGap(1, 1, 1)
                        .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNgayNhap)
                .addGap(13, 13, 13)
                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                        .addComponent(txtNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblLoai)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblMoTa)
                .addGap(9, 9, 9)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(pnlCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlCapNhatLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnReNew, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabSanPham.addTab("Cập Nhật", pnlCapNhat);

        pnlDanhSach.setBackground(new java.awt.Color(255, 255, 255));

        tblSanPham.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Sản Phẩm", "Tên Sản Phẩm", "Tên loại ", "Số lượng", "Giá nhập", "Giá Bán", "Ngày nhập", "Mô tả", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.setFocusable(false);
        tblSanPham.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblSanPham.setRowHeight(25);
        tblSanPham.setRowMargin(0);
        tblSanPham.setSelectionBackground(new java.awt.Color(0, 102, 215));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPham);

        pnlTimKiem.setBackground(new java.awt.Color(255, 255, 255));
        pnlTimKiem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        btnTimKiem.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search_32_icon.png"))); // NOI18N
        btnTimKiem.setEffectColor(new java.awt.Color(0, 0, 0));
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        txtTimKiem.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        txtTimKiem.setText("Nhập để tìm kiếm ...");
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
                .addGap(20, 20, 20)
                .addGroup(pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 977, Short.MAX_VALUE)
                    .addComponent(pnlTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        pnlDanhSachLayout.setVerticalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 694, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabSanPham.addTab("Danh Sách", pnlDanhSach);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlTablePane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tabSanPham))
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(pnlTablePane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tabSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 820, Short.MAX_VALUE)
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
        Point canh = null;

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
        // TODO add your handling code here:
        if (btnXoa.getBackground() != Color.LIGHT_GRAY) {
            remove();
        } else {

        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        if (btnThem.getBackground() == Color.LIGHT_GRAY) {
            if (validateSanPham()) {
                update();
                txtMaSP.setEditable(true);
            } else {

            }
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        if (btnThem.getBackground() != Color.LIGHT_GRAY) {
            if (validateSanPham()) {
                insert();
                fillTable();
            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void txtSoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoLuongActionPerformed

    private void cboLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLoaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboLoaiActionPerformed

    private void txtTimKiemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusGained
        // TODO add your handling code here:
        if (txtTimKiem.getText().equals("Nhập để tìm kiếm ...")) {
            txtTimKiem.setText("");
        }
    }//GEN-LAST:event_txtTimKiemFocusGained

    private void txtTimKiemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusLost
        // TODO add your handling code here:
        if (txtTimKiem.getText().equals("")) {
            txtTimKiem.setText("Nhập để tìm kiếm ...");
        }
    }//GEN-LAST:event_txtTimKiemFocusLost

    private void txtGiaNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaNhapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaNhapActionPerformed

    private void cboTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTrangThaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTrangThaiActionPerformed

    private void btnThemAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemAnhActionPerformed
        // TODO add your handling code here
        anh();
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(SanPhamJFrame.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            Logger.getLogger(SanPhamJFrame.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            Logger.getLogger(SanPhamJFrame.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (UnsupportedLookAndFeelException ex) {
//            Logger.getLogger(SanPhamJFrame.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_btnThemAnhActionPerformed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        // TODO add your handling code here:
        showTable();
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void btnReNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReNewActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btnReNewActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        fillTable();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void tabSanPhamStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabSanPhamStateChanged
        // TODO add your handling code here:
        txtTimKiem.setText("");
        fillTable();
    }//GEN-LAST:event_tabSanPhamStateChanged

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
            java.util.logging.Logger.getLogger(SanPhamJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SanPhamJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SanPhamJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SanPhamJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        /* Create and display the form */
        //set Look and Feel to Windows
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SanPhamJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private newpackage.Button btnReNew;
    private newpackage.Button btnSua;
    private newpackage.Button btnThem;
    private javax.swing.JButton btnThemAnh;
    private newpackage.Button btnTimKiem;
    private newpackage.Button btnXoa;
    private javax.swing.JComboBox<String> cboLoai;
    private javax.swing.JComboBox<String> cboTrangThai;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblGiaBan;
    private javax.swing.JLabel lblGiaNhap;
    private javax.swing.JLabel lblHinhAnh;
    private javax.swing.JLabel lblLoai;
    private javax.swing.JLabel lblMaSP;
    private javax.swing.JLabel lblMaxi;
    private javax.swing.JLabel lblMini;
    private javax.swing.JLabel lblMoTa;
    private javax.swing.JLabel lblNgayNhap;
    private javax.swing.JLabel lblSoLuong;
    private javax.swing.JLabel lblTenSP;
    private javax.swing.JLabel lblTrangThai;
    private javax.swing.JPanel pnlCapNhat;
    private javax.swing.JPanel pnlDanhSach;
    private javax.swing.JPanel pnlTablePane;
    private javax.swing.JPanel pnlTimKiem;
    private javax.swing.JPanel pnlTitleBar;
    private javax.swing.JPanel pnlTrangThai;
    private javax.swing.JTabbedPane tabSanPham;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtGiaNhap;
    private javax.swing.JTextField txtHinhAnh;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JTextField txtNgayNhap;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
