package app.model;

/**
 * Created by android on 10/30/17.
 */
public class DataDPBReport {
    private String nomor,keterangan,tgl,nik_staf,nama_staf,tgl_cetak,total_harga;

    public DataDPBReport() {
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    public String getNik_staf() {
        return nik_staf;
    }

    public void setNik_staf(String nik_staf) {
        this.nik_staf = nik_staf;
    }

    public String getNama_staf() {
        return nama_staf;
    }

    public void setNama_staf(String nama_staf) {
        this.nama_staf = nama_staf;
    }

    public String getTgl_cetak() {
        return tgl_cetak;
    }

    public void setTgl_cetak(String tgl_cetak) {
        this.tgl_cetak = tgl_cetak;
    }

    public String getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(String total_harga) {
        this.total_harga = total_harga;
    }
}
