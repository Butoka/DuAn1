package entity;

public class HoaDonCT {

    private String maHDCT;
    private String maHD;
    private String maSP;
    private int soLuong;
    private double giamGia;
    private double donGia;

    public HoaDonCT() {
    }

    public HoaDonCT(String maHDCT, String maHD, String maSP, int soLuong, double giamGia, double donGia) {
        this.maHDCT = maHDCT;
        this.maHD = maHD;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.giamGia = giamGia;
        this.donGia = donGia;
    }

    public String getMaHDCT() {
        return maHDCT;
    }

    public void setMaHDCT(String maHDCT) {
        this.maHDCT = maHDCT;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }
    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(double giamGia) {
        this.giamGia = giamGia;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

}
