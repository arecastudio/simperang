package app.model;

public class DataDetailPermintaan {
	private String nama,jml,satuan;
	
	public DataDetailPermintaan(String nama, String jml,String satuan) {
		this.nama=nama;
		this.jml=jml;
		this.satuan=satuan;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getJml() {
		return jml;
	}

	public void setJml(String jml) {
		this.jml = jml;
	}

	public String getSatuan() {
		return satuan;
	}

	public void setSatuan(String satuan) {
		this.satuan = satuan;
	}
	
}
