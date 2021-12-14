/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author admin
 */
public class Ban {

    private int MaBan;
    private String tenBan;
    private boolean TrangThai;

    @Override
    public String toString() {
        return tenBan;
    }

    public Ban() {
    }

    public Ban(int MaBan, String tenBan) {
        this.MaBan = MaBan;
        this.tenBan = tenBan;
    }

    public int getMaBan() {
        return MaBan;
    }

    public void setMaBan(int MaBan) {
        this.MaBan = MaBan;
    }

    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }

    public boolean isTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(boolean TrangThai) {
        this.TrangThai = TrangThai;
    }
    
    

}
