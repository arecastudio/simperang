package app.model;

/**
 * Created by rail on 10/31/17.
 */
public class DataSetRole {
    private String id, nama, berkas_barang, berkas_divisi, proses_buat_permintaan, proses_edit_permintaan, proses_hapus_permintaan, proses_review_permintaan, proses_buat_dpb_kolektif, proses_hapus_dpb_kolektif,proses_cek_email, laporan_permintaan, laporan_dpb_kolektif,laporan_dpb_vendor, pengaturan_basis_data;

    public DataSetRole(){}

    public DataSetRole(String id, String nama, String berkas_barang, String berkas_divisi, String proses_buat_permintaan, String proses_edit_permintaan, String proses_hapus_permintaan, String proses_review_permintaan, String proses_buat_dpb_kolektif, String proses_hapus_dpb_kolektif, String laporan_permintaan, String laporan_dpb_kolektif, String pengaturan_basis_data,String proses_cek_email, String laporan_dpb_vendor) {
        this.id = id;
        this.nama = nama;
        this.berkas_barang = berkas_barang;
        this.berkas_divisi = berkas_divisi;
        this.proses_buat_permintaan = proses_buat_permintaan;
        this.proses_edit_permintaan = proses_edit_permintaan;
        this.proses_hapus_permintaan = proses_hapus_permintaan;
        this.proses_review_permintaan = proses_review_permintaan;
        this.proses_buat_dpb_kolektif = proses_buat_dpb_kolektif;
        this.proses_cek_email=proses_cek_email;
        this.proses_hapus_dpb_kolektif = proses_hapus_dpb_kolektif;
        this.laporan_permintaan = laporan_permintaan;
        this.laporan_dpb_kolektif = laporan_dpb_kolektif;
        this.pengaturan_basis_data = pengaturan_basis_data;
        this.laporan_dpb_vendor=laporan_dpb_vendor;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getBerkas_barang() {
        return berkas_barang;
    }

    public String getBerkas_divisi() {
        return berkas_divisi;
    }

    public String getProses_buat_permintaan() {
        return proses_buat_permintaan;
    }

    public String getProses_edit_permintaan() {
        return proses_edit_permintaan;
    }

    public String getProses_hapus_permintaan() {
        return proses_hapus_permintaan;
    }

    public String getProses_review_permintaan() {
        return proses_review_permintaan;
    }

    public String getProses_buat_dpb_kolektif() {
        return proses_buat_dpb_kolektif;
    }

    public String getProses_hapus_dpb_kolektif() {
        return proses_hapus_dpb_kolektif;
    }

    public String getLaporan_permintaan() {
        return laporan_permintaan;
    }

    public String getLaporan_dpb_kolektif() {
        return laporan_dpb_kolektif;
    }

    public String getPengaturan_basis_data() {
        return pengaturan_basis_data;
    }

    public String getProses_cek_email() {
        return proses_cek_email;
    }

    public void setProses_cek_email(String proses_cek_email) {
        this.proses_cek_email = proses_cek_email;
    }

    public String getLaporan_dpb_vendor() {
        return laporan_dpb_vendor;
    }

    public void setLaporan_dpb_vendor(String laporan_dpb_vendor) {
        this.laporan_dpb_vendor = laporan_dpb_vendor;
    }
}
