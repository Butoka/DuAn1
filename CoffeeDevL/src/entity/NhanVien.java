package entity;

import dao.NguoiDungDAO;
import helper.XDate;
import java.util.Date;
import java.util.List;

public class NhanVien {

    private String maNV;
    private String tenNV;
    private String gioiTinh;
    private String ngaySinh;
    private String sdt;
    private String email;
    private String diaChi;
    private String chucVu;
    private String ngayVaoLam;

    @Override
    public String toString() {
        NguoiDungDAO dao = new NguoiDungDAO();
        List<NguoiDung> list = dao.selectByMaND(maNV);
        String trangThai = "";
        if (list.isEmpty()) {
            trangThai += "Chưa có tài khoản";
            return tenNV + " - " + trangThai;//

        } else {
            return tenNV;
        }

    }

    public NhanVien() {
    }

    public NhanVien(String maNV, String tenNV, String gioiTinh, String ngaySinh, String sdt, String email, String diaChi, String chucVu, String ngayVaoLam) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
        this.email = email;
        this.diaChi = diaChi;
        this.chucVu = chucVu;
        this.ngayVaoLam = ngayVaoLam;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getGioiTinh() {
        return gioiTinh;

    }

    public String getGioiTinh2() {
        if (gioiTinh.equals("1")) {
            return "Nam";
        } else {
            return "Nữ";
        }

    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public String getNgaySinh2() {
        Date ngaySinh1 = XDate.toDate(ngaySinh, "yyyy-MM-dd");
        String ngaySinh = XDate.toString(ngaySinh1, "dd-MM-yyyy");
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public String getNgayVaoLam() {

        return ngayVaoLam;
    }

    public String getNgayVaoLam2() {
        Date ngayVaoLam1 = XDate.toDate(ngayVaoLam, "yyyy-MM-dd");
        String ngayVaoLam = XDate.toString(ngayVaoLam1, "dd-MM-yyyy");
        return ngayVaoLam;
    }

    public void setNgayVaoLam(String ngayVaoLam) {
        this.ngayVaoLam = ngayVaoLam;
    }

}
