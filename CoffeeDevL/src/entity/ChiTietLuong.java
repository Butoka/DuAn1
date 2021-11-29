
package entity;


public class ChiTietLuong {
    private String maLuongCT;
    private String ngayLamViec;
    private int maCa;
    private String tongSo;
    private double thanhTien;
    private String maNV;

    public ChiTietLuong() {
    }

    
    public ChiTietLuong(String maLuongCT, String ngayLamViec, int maCa, String tongSo, double thanhTien, String maNV) {
        this.maLuongCT = maLuongCT;
        this.ngayLamViec = ngayLamViec;
        this.maCa = maCa;
        this.tongSo = tongSo;
        this.thanhTien = thanhTien;
        this.maNV = maNV;
    }
    

    public String getMaLuongCT() {
        return maLuongCT;
    }

    public void setMaLuongCT(String maLuongCT) {
        this.maLuongCT = maLuongCT;
    }

    public String getNgayLamViec() {
        return ngayLamViec;
    }

    public void setNgayLamViec(String ngayLamViec) {
        this.ngayLamViec = ngayLamViec;
    }

    public int getMaCa() {
        return maCa;
    }

    public void setMaCa(int maCa) {
        this.maCa = maCa;
    }

    public String getTongSo() {
        return tongSo;
    }

    public void setTongSo(String tongSo) {
        this.tongSo = tongSo;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }
    
    
}
