
package entity;


public class HoaDon {
    private String maHD;
    private String ngayTao;
    private double giamGia;
    private double tienCT;
    private String maND;

    public HoaDon() {
    }
    

    public HoaDon(String maHD, String ngayTao, double giamGia, double tienCT, String maND) {
        this.maHD = maHD;
        this.ngayTao = ngayTao;
        this.giamGia = giamGia;
        this.tienCT = tienCT;
        this.maND = maND;
    }

    
    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public double getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(double giamGia) {
        this.giamGia = giamGia;
    }

    public double getTienCT() {
        return tienCT;
    }

    public void setTienCT(double tienCT) {
        this.tienCT = tienCT;
    }

    public String getMaND() {
        return maND;
    }

    public void setMaND(String maND) {
        this.maND = maND;
    }
    
}
