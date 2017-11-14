package app.model;

/**
 * Created by rail on 11/8/17.
 */
public class DataJenisBarang {
    private Jns jenis;

    public DataJenisBarang() {
    }

    public Jns getJenis() {
        return jenis;
    }
}

class Jns{
    private String barang="Barang";
    private String invantaris="Inventaris";

    public String getBarang() {
        return barang;
    }

    public String getInvantaris() {
        return invantaris;
    }
}