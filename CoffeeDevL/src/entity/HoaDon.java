package entity;

public class HoaDon {

    private String maHD;
    private String ngayTao;
    private String maND;
    private boolean trangThai;

    public HoaDon() {
    }

    public HoaDon(String maHD, String ngayTao, String maND, boolean trangThai) {
        this.maHD = maHD;
        this.ngayTao = ngayTao;
        this.trangThai = trangThai;
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

    public String getMaND() {
        return maND;
    }

    public void setMaND(String maND) {
        this.maND = maND;
    }

    public boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
}
