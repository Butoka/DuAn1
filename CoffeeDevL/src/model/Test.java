package model;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfDiv;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

public class Test extends javax.swing.JFrame {

    public Test() {
        initComponents();

    }
    String TruocDo;

    public void in() throws IOException {
        JFileChooser chooser = new JFileChooser();
        if (TruocDo != null) {
            chooser.setCurrentDirectory(new File(TruocDo));//Set thư mục đã chọn trc đó
        } else {
            String userDir = System.getProperty("user.home");// Lấy tên PC
            chooser = new JFileChooser(userDir + "/Downloads");

        }
        int result = chooser.showSaveDialog(null);
        File f = chooser.getSelectedFile();
        if (result == JFileChooser.APPROVE_OPTION) {
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);

            try {

                PdfWriter.getInstance(document, new FileOutputStream(f.getPath()));
                document.open();
                //Khởi tạo một table có 3 cột
//                PdfPTable table = new PdfPTable(5);
//                //Khởi tạo 3 ô header
//                Paragraph header1 = new Paragraph("Tên Sản Phẩm");
//                Paragraph header2 = new Paragraph("Đơn giá");
//                Paragraph header3 = new Paragraph("Số lượng");
//                Paragraph header4 =new Paragraph("Giảm giá");
//                Paragraph header5 =new Paragraph("Thành tiền");
//
//                //Thêm 3 ô header vào table
//                table.addCell(header1);
//                table.addCell(header2);
//                table.addCell(header3);
//                table.addCell(header4);
//                table.addCell(header5);
//
//                //Khởi tạo 3 ô data: ô số 1 là string, ô số 2 là ảnh, ô số 3 là table
//                PdfPCell data1 = new PdfPCell(new Paragraph("Cà phê đen đá"));
//                PdfPCell data2 = new PdfPCell(new Paragraph("1200 VND"));
//                PdfPCell data3 = new PdfPCell(new Paragraph("2"));
//                PdfPCell data4 = new PdfPCell(new Paragraph("2000 VND"));
//                PdfPCell data5 = new PdfPCell(new Paragraph("1000 VND"));
//                
//                //Thêm data vào bảng.
//                table.addCell(data1);
//                table.addCell(data2);
//                table.addCell(data3);
//                table.addCell(data4);
//                table.addCell(data5);

                BaseFont bfComic = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                document.add(new Paragraph("Unicode: H\u00F3a \u0111\u01A1n thanh to\u00E1n", new Font(bfComic, 12)));
                document.close();
                System.out.println("Đã tạo file pdf");

            } catch (DocumentException ex) {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public String convertToUnicode(String s) {
        int i = 0, len = s.length();
        char c;
        StringBuffer sb = new StringBuffer(len);
        try {
            while (i < len) {
                c = s.charAt(i++);
                if (c == '\\') {
                    if (i < len) {
                        c = s.charAt(i++);
                        if (c == 'u') {
                            if (Character.digit(s.charAt(i), 16) != -1
                                    && Character.digit(s.charAt(i + 1), 16) != -1
                                    && Character.digit(s.charAt(i + 2), 16) != -1
                                    && Character.digit(s.charAt(i + 3), 16) != -1) {
                                if (s.substring(i).length() >= 4) {
                                    c = (char) Integer.parseInt(s.substring(i, i + 4), 16);
                                    i += 4;
                                } else {
                                    sb.append('\\');
                                }
                            } else {
                                sb.append('\\');
                            }
                        } // add other cases here as desired...
                    }
                } // fall through: \ escapes itself, quotes any character but u
                sb.append(c);
            }
        } catch (Exception e) {
            System.out.println("Error Generate PDF :: " + e.getStackTrace().toString());
            return s;
        }
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JLayeredPane();
        button1 = new newpackage.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        bg.setOpaque(true);

        button1.setBackground(new java.awt.Color(0, 255, 0));
        button1.setText("button1");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        bg.setLayer(button1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addGap(267, 267, 267)
                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(292, Short.MAX_VALUE))
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addGap(212, 212, 212)
                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(229, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn2MouseExited


    }//GEN-LAST:event_btn2MouseExited

    private void btn2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn2MousePressed


    }//GEN-LAST:event_btn2MousePressed

    private void btn2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn2MouseReleased

    }//GEN-LAST:event_btn2MouseReleased

    private void btn2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn2MouseEntered

    }//GEN-LAST:event_btn2MouseEntered

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        try {
            in();
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_button1ActionPerformed

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
            java.util.logging.Logger.getLogger(Test.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Test.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Test.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Test.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Test().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane bg;
    private newpackage.Button button1;
    // End of variables declaration//GEN-END:variables
}
