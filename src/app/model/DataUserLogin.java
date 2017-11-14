package app.model;

//SELECT u.nik, u.nama, u.password, u.telp, u.email, u.posisi, u.id_divisi, u.nik_atasan, u.id_role,r.nama,d.nama FROM user AS u LEFT OUTER JOIN role AS r ON r.id=u.id_role LEFT OUTER JOIN divisi AS d ON d.id=u.id_divisi WHERE nik='222';
public class DataUserLogin {
	private String nik, nama, password, telp, email, id_divisi, id_role,nama_role,nama_divisi;
	//private Boolean cetak;

	public DataUserLogin() {
	}
	public DataUserLogin(String nik,String nama,String password,String telp,String email,String id_divisi,String id_role,String nama_role,String nama_divisi) {
		this.nik=nik;
		this.nama=nama;
		this.password=password;
		this.telp=telp;
		this.email=email;
		this.id_divisi=id_divisi;
		this.id_role=id_role;
		this.nama_role=nama_role;
		this.nama_divisi=nama_divisi;
	}
	public String getNik() {
		return nik;
	}
	public void setNik(String nik) {
		this.nik = nik;
	}
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTelp() {
		return telp;
	}
	public void setTelp(String telp) {
		this.telp = telp;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getId_divisi() {
		return id_divisi;
	}
	public void setId_divisi(String id_divisi) {
		this.id_divisi = id_divisi;
	}
	public String getId_role() {
		return id_role;
	}
	public void setId_role(String id_role) {
		this.id_role = id_role;
	}
	public String getNama_role() {
		return nama_role;
	}
	public void setNama_role(String nama_role) {
		this.nama_role = nama_role;
	}
	public String getNama_divisi() {
		return nama_divisi;
	}
	public void setNama_divisi(String nama_divisi) {
		this.nama_divisi = nama_divisi;
	}

	//public Boolean getCetak() {return cetak;}
}