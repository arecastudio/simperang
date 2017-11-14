package app.model;

public class DataKaryawan {
	private String nik,nama;
	public DataKaryawan(String nik, String nama) {
		this.nik=nik;
		this.nama=nama;
	}
	public String getNik() {
		return nik;
	}
	public String getNama() {
		return nama;
	}
}
