package entity;

public class HoaDon {

    private String maHD;
    private String ngayTao;
    private String tenDN;
    private boolean trangThai;
    private boolean Huy;
    private int Ban;
    private boolean dangBan;

    @Override
    public String toString() {
        return maHD;
    } 
    public HoaDon() {
    }

    public HoaDon(String maHD, String ngayTao, String tenDN, boolean trangThai) {
        this.maHD = maHD;
        this.ngayTao = ngayTao;
        this.trangThai = trangThai;
        this.tenDN = tenDN;
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

    public String getTenDN() {
        return tenDN;
    }

    public void setTenDN(String tenDN) {
        this.tenDN = tenDN;
    }

    public boolean getTrangThai() {
        return trangThai;
    }
     public String getTrangThai2() {
         if(trangThai)
         {
             return "Đã thanh toán";
         }else
         {
             return "Chưa thanh toán";
         }
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public boolean isHuy() {
        return Huy;
    }

    public void setHuy(boolean Huy) {
        this.Huy = Huy;
    }

    public int getBan() {
        return Ban;
    }

    public void setBan(int Ban) {
        this.Ban = Ban;
    }

    public boolean isDangBan() {
        return dangBan;
    }

    public void setDangBan(boolean dangBan) {
        this.dangBan = dangBan;
    }
    
}
