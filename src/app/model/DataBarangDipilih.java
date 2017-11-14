package app.model;

public class DataBarangDipilih {
	private String id;
	private String nama;
	private String jumlah;
	private String satuan;
	private String keterangan;
	private String harga;

	public DataBarangDipilih() {
		// TODO Auto-generated constructor stub
	}

	public DataBarangDipilih(String id,String nama,String jumlah,String satuan,String keterangan,String harga) {
		this.id=id;
		this.nama=nama;
		this.jumlah=jumlah;
		this.satuan=satuan;
		this.keterangan=keterangan;		
		this.harga=harga;	
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

	public String getJumlah() {
		return jumlah;
	}

	public void setJumlah(String jumlah) {
		this.jumlah = jumlah;
	}

	public String getSatuan() {
		return satuan;
	}

	public void setSatuan(String satuan) {
		this.satuan = satuan;
	}

	public String getKeterangan() {
		return keterangan;
	}

	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}

	public String getHarga() {
		return harga;
	}

	public void setHarga(String harga) {
		this.harga = harga;
	}
	
	
}
