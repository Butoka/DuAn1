
package entity;


public class NguoiDung {
    private String maND;
    private String tenND;
    private String matKhau;
    private String phanQuyen;
    private String trangThai;

    public NguoiDung() {
    }
    

    public NguoiDung(String maND, String tenND, String matKhau, String phanQuyen, String trangThai) {
        this.maND = maND;
        this.tenND = tenND;
        this.matKhau = matKhau;
        this.phanQuyen = phanQuyen;
        this.trangThai = trangThai;
    }
    

    public String getMaND() {
        return maND;
    }

    public void setMaND(String maND) {
        this.maND = maND;
    }

    public String getTenND() {
        return tenND;
    }

    public void setTenND(String tenND) {
        this.tenND = tenND;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getPhanQuyen() {
        return phanQuyen;
    }

    public void setPhanQuyen(String phanQuyen) {
        this.phanQuyen = phanQuyen;
    }

    public String getTrangThai() {
        return trangThai;
    }
    public String getTrangThai2() {
        String trangThai2;
        if(trangThai.equals("1"))
        {
            trangThai2 = "Hoạt động";
        }else 
        {
            trangThai2 = "Không hoạt động";
        }
        return trangThai2;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
}
