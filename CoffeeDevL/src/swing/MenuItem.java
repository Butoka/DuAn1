/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing;

import event.EventMenu;
import event.EventMenuSelected;
import model.ModelMenu;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MenuBar;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author admin
 */
public class MenuItem extends javax.swing.JPanel {

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public EventMenuSelected getEvetS() {
        return evetS;
    }

    public void setEvetS(EventMenuSelected evetS) {
        this.evetS = evetS;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private float alpha;
    private ModelMenu menu;

    public ModelMenu getMenu() {
        return menu;
    }

    public void setMenu(ModelMenu menu) {
        this.menu = menu;
    }
    private boolean open;
    private EventMenuSelected evetS;
    private int index;

    public MenuItem(ModelMenu menu, EventMenu event, EventMenuSelected eventS, int index) {
        initComponents();
        this.menu = menu;
        this.evetS = eventS;
        this.index = index;
        setOpen(false);
        setLayout(new MigLayout("wrap, fillx, insets 0", "[fill]", "[fill, 40!]0[fill, 35!]"));
        MenuButton firstItem = new MenuButton(menu.getIcon(), "    " + menu.getMenuName().toUpperCase());
        firstItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (menu.getSubMenu().length > 0) {
                    if (event.MenuPressed(MenuItem.this, !open)) {
                        open = !open;
                    }
                }
                eventS.menuSelected(index, -1);
            }
        });
        add(firstItem);
        int subMenuIndex = -1;
        for (String st : menu.getSubMenu()) {
            MenuButton item = new MenuButton(st);
            item.setIndex(++subMenuIndex);
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    eventS.menuSelected(index, item.getIndex());

                }
            });
            add(item);
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getPreferredSize().height;
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(50, 50, 50));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.fillRect(0, 2, width, 38);
        g2.setComposite(AlphaComposite.SrcOver);
        g2.fillRect(0, 40, width, height - 40);
        g2.setColor(new Color(100, 100, 100));
        g2.drawLine(30, 40, 30, height - 17);

        for (int i = 0; i < menu.getSubMenu().length; i++) {
            int y = ((i + 1) * 35 + 40) - 17;
            g2.drawLine(30, y, 38, y);
        }
        if (menu.getSubMenu().length > 0) {
            createArrowwButton(g2);
        }
        super.paintComponent(g);
    }

    private void createArrowwButton(Graphics2D g2) {
        int size = 4;
        int y = 19;
        int x = 205;
        g2.setColor(new Color(230, 230, 230));
        float ay = alpha * size;
        float ay1 = (1f - alpha) * size;
        g2.drawLine(x, (int) (y + ay), x + 4, (int) (y + ay1));
        g2.drawLine(x + 4, (int) (y + ay1), x + 8, (int) (y + ay));

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblText = new javax.swing.JLabel();

        lblText.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblText.setText("jLabel1");

        setOpaque(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblText;
    // End of variables declaration//GEN-END:variables
}
