package app.model;

public class DataDPB {
	private String nomor,ket,tgl,nama,total_jml,group_minta,mail_send;
	
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
}
