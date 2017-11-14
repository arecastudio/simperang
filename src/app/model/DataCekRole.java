package app.model;

/**
 * Created by android on 11/3/17.
 */
public class DataCekRole {
    private String nama;
    private int id, berkas_barang, berkas_divisi, proses_buat_permintaan, proses_edit_permintaan, proses_hapus_permintaan, proses_review_permintaan, proses_buat_dpb_kolektif, proses_hapus_dpb_kolektif,proses_cek_email, laporan_permintaan, laporan_dpb_kolektif,laporan_dpb_vendor, pengaturan_basis_data, pengaturan_admin;

    public DataCekRole(){}

    public DataCekRole(int id, String nama, int berkas_barang, int berkas_divisi, int proses_buat_permintaan, int proses_edit_permintaan, int proses_hapus_permintaan, int proses_review_permintaan, int proses_buat_dpb_kolektif, int proses_hapus_dpb_kolektif, int laporan_permintaan, int laporan_dpb_kolektif, int pengaturan_basis_data, int pengaturan_admin,int proses_cek_email,int laporan_dpb_vendor) {
        this.id = id;
        this.nama = nama;
        this.berkas_barang = berkas_barang;
        this.berkas_divisi = berkas_divisi;
        this.proses_buat_permintaan = proses_buat_permintaan;
        this.proses_edit_permintaan = proses_edit_permintaan;
        this.proses_hapus_permintaan = proses_hapus_permintaan;
        this.proses_review_permintaan = proses_review_permintaan;
        this.proses_buat_dpb_kolektif = proses_buat_dpb_kolektif;
        this.proses_hapus_dpb_kolektif = proses_hapus_dpb_kolektif;
        this.proses_cek_email=proses_cek_email;
        this.laporan_permintaan = laporan_permintaan;
        this.laporan_dpb_kolektif = laporan_dpb_kolektif;
        this.pengaturan_basis_data = pengaturan_basis_data;
        this.pengaturan_admin=pengaturan_admin;
        this.laporan_dpb_vendor=laporan_dpb_vendor;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public int getBerkas_barang() {
        return berkas_barang;
    }

    public int getBerkas_divisi() {
        return berkas_divisi;
    }

    public int getProses_buat_permintaan() {
        return proses_buat_permintaan;
    }

    public int getProses_edit_permintaan() {
        return proses_edit_permintaan;
    }

    public int getProses_hapus_permintaan() {
        return proses_hapus_permintaan;
    }

    public int getProses_review_permintaan() {
        return proses_review_permintaan;
    }

    public int getProses_buat_dpb_kolektif() {
        return proses_buat_dpb_kolektif;
    }

    public int getProses_hapus_dpb_kolektif() {
        return proses_hapus_dpb_kolektif;
    }

    public int getProses_cek_email() {
        return proses_cek_email;
    }

    public void setProses_cek_email(int proses_cek_email) {
        this.proses_cek_email = proses_cek_email;
    }

    public int getLaporan_permintaan() {
        return laporan_permintaan;
    }

    public int getLaporan_dpb_kolektif() {
        return laporan_dpb_kolektif;
    }

    public int getPengaturan_basis_data() {
        return pengaturan_basis_data;
    }

    public int getPengaturan_admin() {
        return pengaturan_admin;
    }

    public int getLaporan_dpb_vendor() {
        return laporan_dpb_vendor;
    }

    public void setLaporan_dpb_vendor(int laporan_dpb_vendor) {
        this.laporan_dpb_vendor = laporan_dpb_vendor;
    }
}
