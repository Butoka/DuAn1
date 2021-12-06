package entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CaLamViec {

    private int maCa;
    private String TenCa;
    private String gioBatDau;
    private String gioKetThuc;
    private double luong;

    public CaLamViec() {
    }

    public CaLamViec(int maCa, String TenCa, String gioBatDau, String gioKetThuc, double luong) {
        this.maCa = maCa;
        this.TenCa = TenCa;
        this.gioBatDau = gioBatDau;
        this.gioKetThuc = gioKetThuc;
        this.luong = luong;
    }

    public int getMaCa() {
        return maCa;
    }

    public void setMaCa(int maCa) {
        this.maCa = maCa;
    }

    public String getTenCa() {
        return TenCa;
    }

    public void setTenCa(String TenCa) {
        this.TenCa = TenCa;
    }

    public String getGioBatDau() {
        return gioBatDau;
    }

    public void setGioBatDau(String gioBatDau) {
        this.gioBatDau = gioBatDau;
    }

    public String getGioKetThuc() {
        return gioKetThuc;
    }

    public void setGioKetThuc(String gioKetThuc) {
        this.gioKetThuc = gioKetThuc;
    }

    public double getLuong() {
        return luong;
    }

    public void setLuong(double luong) {
        this.luong = luong;
    }
}
