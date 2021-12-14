/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package component;

import event.EventMenu;
import event.EventMenuSelected;
import event.EventShowPopupMenu;
import model.ModelMenu;
import swing.MenuAnimation;
import swing.MenuItem;
import scrollbar.ScrollBarCustom;
import com.sun.prism.paint.Gradient;
import helper.Auth;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.ImageIcon;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

/**
 *
 * @author admin
 */
public class Menu extends javax.swing.JPanel {

    private final MigLayout layout;
    private EventMenuSelected event;
    private EventShowPopupMenu eventShow;

    public void addEventShow(EventShowPopupMenu eventShow) {
        this.eventShow = eventShow;
    }
    private boolean enableMenu = true;
    private boolean showMenu = true;

    public void addEvent(EventMenuSelected event) {
        this.event = event;
    }

    public void setEnableMenu(boolean enableMenu) {
        this.enableMenu = enableMenu;
    }

    public void setShowMenu(boolean showMenu) {
        this.showMenu = showMenu;
    }

    public boolean isShowMenu() {
        return showMenu;
    }
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Menu() {
        initComponents();
        //setOpaque(false);
        sp.getViewport().setOpaque(false);
        sp.setVerticalScrollBar(new ScrollBarCustom());
        sp.setViewportBorder(null);
        layout = new MigLayout("wrap, fillx, insets 0", "[fill]", "[]0[]");
        panel.setLayout(layout);

    }

    public void initMenuItem() {
        if (Auth.isLogin()) {
            if (Auth.getManager().equalsIgnoreCase("admin")) {
                initMenuItemAdmin();
            } else if (Auth.getManager().equalsIgnoreCase("Quản lý")) {
                initMenuItemQuanLy();
            } else {
                initMenuItemThuNgan();
            }

        } else {
            initMenuItemNoLogin();
        }

    }

    public void initMenuItemNoLogin() {
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/thongke.png")), "Tổng hợp - thống kê"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/sanpham.png")), "Sản phẩm"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/ban.png")), "Bàn"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/banhang.png")), "Bán hàng"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/hoadon.png")), "Hóa đơn"));//, "Hóa đơn","Hóa đơn CT"
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/nhanvien.png")), "Nhân viên"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/nguoidung.png")), "Người dùng"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/doimatkhau2.png")), "Đổi mật khẩu"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/dangxuat.png")), "Đăng xuất"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/thoat.png")), "Thoát"));
    }

    public void initMenuItemAdmin() {
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/thongke.png")), "Tổng hợp - thống kê", "Lương nhân viên",  "TK Sản phẩm ", "Doanh Thu"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/sanpham.png")), "Sản phẩm", "Sản phẩm", "Loại sản phẩm"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/ban.png")), "Bàn"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/banhang.png")), "Bán hàng"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/hoadon.png")), "Hóa đơn"));//, "Hóa đơn","Hóa đơn CT"
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/nhanvien.png")), "Nhân viên", "Nhân viên", "Ca làm việc", "Chấm công", "Bảng lương"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/nguoidung.png")), "Người dùng"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/doimatkhau2.png")), "Đổi mật khẩu"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/dangxuat.png")), "Đăng xuất"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/thoat.png")), "Thoát"));
    }

    public void initMenuItemQuanLy() {
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/thongke.png")), "Tổng hợp - thống kê", "Lương nhân viên", "TK Sản phẩm ", "Doanh Thu"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/sanpham.png")), "Sản phẩm", "Sản phẩm", "Loại sản phẩm"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/ban.png")), "Bàn"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/banhang.png")), "Bán hàng"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/hoadon.png")), "Hóa đơn"));//, "Hóa đơn","Hóa đơn CT"
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/nhanvien.png")), "Nhân viên", "Nhân viên", "Ca làm việc", "Chấm công", "Bảng lương"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/doimatkhau2.png")), "Đổi mật khẩu"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/dangxuat.png")), "Đăng xuất"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/thoat.png")), "Thoát"));
    }

    public void initMenuItemThuNgan() {

        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/banhang.png")), "Bán hàng"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/hoadon.png")), "Hóa đơn"));//, "Hóa đơn","Hóa đơn CT"
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/doimatkhau2.png")), "Đổi mật khẩu"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/dangxuat.png")), "Đăng xuất"));
        addMenu(new ModelMenu(new ImageIcon(getClass().getResource("/icon/thoat.png")), "Thoát"));
    }

    private void addMenu(ModelMenu menu) {
        panel.add(new MenuItem(menu, getEventMenu(), event, panel.getComponentCount()), "h 40!");

    }

    private EventMenu getEventMenu() {
        return new EventMenu() {
            @Override
            public boolean MenuPressed(Component com, boolean open) {
                if (enableMenu) {
                    if (showMenu) {
                        if (open) {
                            new MenuAnimation(layout, com).openMenu();
                        } else {
                            new MenuAnimation(layout, com).closeMenu();

                        }
                        return true;
                    } else {
                        eventShow.showPopup(com);
                    }
                }

                return false;
            }

        };
    }

    public void hidealMenu() {
        for (Component com : panel.getComponents()) {
            MenuItem item = (MenuItem) com;
            if (item.isOpen()) {
                new MenuAnimation(layout, com, 500).closeMenu();
                item.setOpen(false);
            }

        }
    }

//    @Override
//    public void paintComponent(Graphics g) {
//
//        Graphics2D g2 = (Graphics2D) g;
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        GradientPaint gra = new GradientPaint(0, 0, new Color(255, 102, 0), getWidth(), 0, new Color(255, 171, 44));
//        g2.setPaint(gra);
//        g2.fillRect(0, 0, getWidth(), getHeight());
//
//        super.paintComponent(g);
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sp = new javax.swing.JScrollPane();
        panel = new javax.swing.JPanel();
        profile1 = new component.Profile();

        setBackground(new java.awt.Color(52, 127, 255));

        sp.setBorder(null);
        sp.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panel.setOpaque(false);

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 333, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 550, Short.MAX_VALUE)
        );

        sp.setViewportView(panel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(profile1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
            .addComponent(sp, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(profile1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sp, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panel;
    private component.Profile profile1;
    private javax.swing.JScrollPane sp;
    // End of variables declaration//GEN-END:variables
}
