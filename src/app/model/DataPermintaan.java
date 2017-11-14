package app.model;

public class DataPermintaan {
	private String nomor, alasan, status, tgl, nik;
	private String id_posisi,nik_atasan,id_posisi_atasan;
	private String nama,nama_posisi,nama_atasan,nama_posisi_atasan;
	private Boolean cetak;
	
	public DataPermintaan() {}
	
	public DataPermintaan(String nomor,String alasan,String tgl,String nik) {
		this.nomor=nomor;
		this.alasan=alasan;
		this.tgl=tgl;
		this.nik=nik;
	}

	public String getId_posisi() {
		return id_posisi;
	}

	public void setId_posisi(String id_posisi) {
		this.id_posisi = id_posisi;
	}

	public String getNik_atasan() {
		return nik_atasan;
	}

	public void setNik_atasan(String nik_atasan) {
		this.nik_atasan = nik_atasan;
	}

	public String getId_posisi_atasan() {
		return id_posisi_atasan;
	}

	public void setId_posisi_atasan(String id_posisi_atasan) {
		this.id_posisi_atasan = id_posisi_atasan;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTgl() {
		return tgl;
	}

	public void setTgl(String tgl) {
		this.tgl = tgl;
	}

	public String getNik() {
		return nik;
	}

	public void setNik(String nik) {
		this.nik = nik;
	}

	public Boolean getCetak() {
		return cetak;
	}

	public void setCetak(Boolean cetak) {
		this.cetak = cetak;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getNama_posisi() {
		return nama_posisi;
	}

	public void setNama_posisi(String nama_posisi) {
		this.nama_posisi = nama_posisi;
	}

	public String getNama_atasan() {
		return nama_atasan;
	}

	public void setNama_atasan(String nama_atasan) {
		this.nama_atasan = nama_atasan;
	}

	public String getNama_posisi_atasan() {
		return nama_posisi_atasan;
	}

	public void setNama_posisi_atasan(String nama_posisi_atasan) {
		this.nama_posisi_atasan = nama_posisi_atasan;
	}
}
