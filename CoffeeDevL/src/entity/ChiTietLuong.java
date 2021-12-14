package entity;

import helper.XDate;

public class ChiTietLuong {

    private String maLuongCT;
    private String ngayLamViec;
    private int maCa;
    private String maNV;

    public ChiTietLuong() {
    }

    public ChiTietLuong(String maLuongCT, String ngayLamViec, int maCa, String maNV) {
        this.maLuongCT = maLuongCT;
        this.ngayLamViec = ngayLamViec;
        this.maCa = maCa;
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
//    public String getNgayLamViec2() {
//        
//        return XDate.toString(XDate.toDate(getNgayLamViec(), "dd-MM-yyyy"), "dd-MM-yyyy");
//    }

    public void setNgayLamViec(String ngayLamViec) {
        this.ngayLamViec = ngayLamViec;
    }

    public int getMaCa() {
        return maCa;
    }

    public void setMaCa(int maCa) {
        this.maCa = maCa;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

}
