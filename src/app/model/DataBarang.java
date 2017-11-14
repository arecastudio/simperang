package app.model;

import java.sql.Blob;

public class DataBarang {
	private String id;
	private String nama;
	private String satuan;
	private String keterangan;
	private String harga;
	private DataBarangJenis jenis;
	private Object img;
	
	public DataBarang(){}
	
	/*public DataBarang(String id,String nama,String satuan,String keterangan,String harga, DataBarangJenis jenis) {
		this.id=id;
		this.nama=nama;
		this.satuan=satuan;
		this.keterangan=keterangan;
		this.harga=harga;
		this.jenis=jenis;
	}*/

	
	public String getHarga() {
		return harga;
	}

	public void setHarga(String harga) {
		this.harga = harga;
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

	public DataBarangJenis getJenis() {
		return jenis;
	}

	public void setJenis(DataBarangJenis jenis) {
		this.jenis = jenis;
	}

	public Object getImg() {
		return img;
	}

	public void setImg(Object img) {
		this.img = img;
	}
}
