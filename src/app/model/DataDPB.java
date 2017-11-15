package app.model;

public class DataDPB {
	private String nomor,ket,tgl,nama,total_jml,group_minta,mail_send,nik;
	private String nik_manager,nama_manager,id_posisi_manager,nama_posisi_manager;
	//tmp_id_manager="",tmp_nama_manager="",tmp_id_posisi_manager="",tmp_nama_posisi_manager=""

	public DataDPB(){}
	
	public DataDPB(String nomor,String ket,String tgl,String nama,String total_jml,String group_minta) {
		this.nomor=nomor;
		this.ket=ket;
		this.tgl=tgl;
		this.nama=nama;
		this.total_jml=total_jml;
		this.group_minta=group_minta;
	}

	public String getNomor() {
		return nomor;
	}

	public void setNomor(String nomor) {
		this.nomor = nomor;
	}

	public String getKet() {
		return ket;
	}

	public void setKet(String ket) {
		this.ket = ket;
	}

	public String getTgl() {
		return tgl;
	}

	public void setTgl(String tgl) {
		this.tgl = tgl;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getTotal_jml() {
		return total_jml;
	}

	public void setTotal_jml(String total_jml) {
		this.total_jml = total_jml;
	}

	public String getGroup_minta() {
		return group_minta;
	}

	public void setGroup_minta(String group_minta) {
		this.group_minta = group_minta;
	}

	public String getMail_send() {
		return mail_send;
	}

	public void setMail_send(String mail_send) {
		this.mail_send = mail_send;
	}

	public String getNik() {
		return nik;
	}

	public void setNik(String nik) {
		this.nik = nik;
	}

	//pejabat penanggung jawab surat permintaan ke vendor

	public String getNik_manager() {
		return nik_manager;
	}

	public void setNik_manager(String nik_manager) {
		this.nik_manager = nik_manager;
	}

	public String getNama_manager() {
		return nama_manager;
	}

	public void setNama_manager(String nama_manager) {
		this.nama_manager = nama_manager;
	}

	public String getId_posisi_manager() {
		return id_posisi_manager;
	}

	public void setId_posisi_manager(String id_posisi_manager) {
		this.id_posisi_manager = id_posisi_manager;
	}

	public String getNama_posisi_manager() {
		return nama_posisi_manager;
	}

	public void setNama_posisi_manager(String nama_posisi_manager) {
		this.nama_posisi_manager = nama_posisi_manager;
	}
}
