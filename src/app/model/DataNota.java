package app.model;

/**
 * Created by rail on 11/13/17.
 */
public class DataNota {
    private String nomor,nomor_dpb_kolektif,tgl;
    private int mail_send,id_vendor;

    public DataNota() {
    }

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

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    public int getMail_send() {
        return mail_send;
    }

    public void setMail_send(int mail_send) {
        this.mail_send = mail_send;
    }

    public int getId_vendor() {
        return id_vendor;
    }

    public void setId_vendor(int id_vendor) {
        this.id_vendor = id_vendor;
    }
}
