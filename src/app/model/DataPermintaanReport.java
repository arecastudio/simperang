package app.model;

/**
 * Created by rail on 10/29/17.
 */
public class DataPermintaanReport {
    private String nomor,alasan,tgl,divisi,nik_user,user,tgl_cetak;
    private String jabatan,nik_atasan,atasan,jabatan_atasan;

    public DataPermintaanReport() {
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getAlasan() {
        return alasan;
    }

    public void setAlasan(String alasan) {
        this.alasan = alasan;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    public String getDivisi() {
        return divisi;
    }

    public void setDivisi(String divisi) {
        this.divisi = divisi;
    }

    public String getNik_user() {
        return nik_user;
    }

    public void setNik_user(String nik_user) {
        this.nik_user = nik_user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTgl_cetak() {
        return tgl_cetak;
    }

    public void setTgl_cetak(String tgl_cetak) {
        this.tgl_cetak = tgl_cetak;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getNik_atasan() {
        return nik_atasan;
    }

    public void setNik_atasan(String nik_atasan) {
        this.nik_atasan = nik_atasan;
    }

    public String getAtasan() {
        return atasan;
    }

    public void setAtasan(String atasan) {
        this.atasan = atasan;
    }

    public String getJabatan_atasan() {
        return jabatan_atasan;
    }

    public void setJabatan_atasan(String jabatan_atasan) {
        this.jabatan_atasan = jabatan_atasan;
    }
}
