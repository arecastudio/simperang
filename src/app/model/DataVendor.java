package app.model;

/**
 * Created by android on 11/8/17.
 */
public class DataVendor {
    private String id,nama,alamat,telp,email,pemilik;

    public DataVendor() {
    }

    public DataVendor(String id, String nama, String alamat, String telp, String email,String pemilik) {
        //bagian ini digunakan untuk iterasi langsung ke tabel
        //seperti pada: DataVendor dv=(DataVendor)table.getSelectionModel().getSelectedItem();
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.telp = telp;
        this.email = email;
        this.pemilik=pemilik;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPemilik() {
        return pemilik;
    }

    public void setPemilik(String pemilik) {
        this.pemilik = pemilik;
    }
}
