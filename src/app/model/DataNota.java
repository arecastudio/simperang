package app.model;

/**
 * Created by rail on 11/15/17.
 */
public class DataNota {
    private String nomor,nomor_dpb_kolektif,tgl,mail_send,konten_surat;
    private String nama_atasan,nama_posisi_atasan,nama_vendor,pemilik_vendor,email_vendor,alamat_vendor;


    public DataNota(){}

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getNomor_dpb_kolektif() {
        return nomor_dpb_kolektif;
    }

    public void setNomor_dpb_kolektif(String nomor_dpb_kolektif) {
        this.nomor_dpb_kolektif = nomor_dpb_kolektif;
    }

    public String getNama_vendor() {
        return nama_vendor;
    }

    public void setNama_vendor(String nama_vendor) {
        this.nama_vendor = nama_vendor;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    public String getMail_send() {
        return mail_send;
    }

    public void setMail_send(String mail_send) {
        this.mail_send = mail_send;
    }

    public String getKonten_surat() {
        return konten_surat;
    }

    public void setKonten_surat(String konten_surat) {
        this.konten_surat = konten_surat;
    }

    public String getPemilik_vendor() {
        return pemilik_vendor;
    }

    public void setPemilik_vendor(String pemilik_vendor) {
        this.pemilik_vendor = pemilik_vendor;
    }

    public String getEmail_vendor() {
        return email_vendor;
    }

    public void setEmail_vendor(String email_vendor) {
        this.email_vendor = email_vendor;
    }

    public String getNama_atasan() {
        return nama_atasan;
    }

    public void setNama_atasan(String nama_atasan) {
        this.nama_atasan = nama_atasan;
    }

    public String getNama_posisi_atasan() {
        return nama_posisi_atasan;
    }

    public void setNama_posisi_atasan(String nama_posisi_atasan) {
        this.nama_posisi_atasan = nama_posisi_atasan;
    }

    public String getAlamat_vendor() {
        return alamat_vendor;
    }

    public void setAlamat_vendor(String alamat_vendor) {
        this.alamat_vendor = alamat_vendor;
    }
}
