package app.model;

/**
 * Created by rail on 11/15/17.
 */
public class DataNota {
    private String nomor,nomor_dpb_kolektif,nama_vendor,tgl,mail_send;

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
}
