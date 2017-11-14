package app.model;

public class DataDivisi {
	private String id, nama, ket;
	
	public DataDivisi(String id,String nama,String ket) {
		this.id=id;
		this.nama=nama;
		this.ket=ket;
	}

	public String getId() {
		return id;
	}

	public String getNama() {
		return nama;
	}

	public String getKet() {
		return ket;
	}
	
	
}
