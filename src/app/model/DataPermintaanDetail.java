package app.model;

public class DataPermintaanDetail {
	private String nomor_permintaan, id_barang, jml_minta, jml_setuju, ket_tolak;
	
	public DataPermintaanDetail() {
		// TODO Auto-generated constructor stub
	}

	public String getNomor_permintaan() {
		return nomor_permintaan;
	}

	public void setNomor_permintaan(String nomor_permintaan) {
		this.nomor_permintaan = nomor_permintaan;
	}

	public String getId_barang() {
		return id_barang;
	}

	public void setId_barang(String id_barang) {
		this.id_barang = id_barang;
	}

	public String getJml_minta() {
		return jml_minta;
	}

	public void setJml_minta(String jml_minta) {
		this.jml_minta = jml_minta;
	}

	public String getJml_setuju() {
		return jml_setuju;
	}

	public void setJml_setuju(String jml_setuju) {
		this.jml_setuju = jml_setuju;
	}

	public String getKet_tolak() {
		return ket_tolak;
	}

	public void setKet_tolak(String ket_tolak) {
		this.ket_tolak = ket_tolak;
	}

	
}
