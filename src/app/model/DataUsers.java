package app.model;

public class DataUsers {
	//SELECT u.nik,u.nama,u.telp,u.email,u.posisi,d.nama,a.nama,r.nama FROM user AS u LEFT OUTER JOIN divisi AS d ON d.id=u.id_divisi LEFT OUTER JOIN user AS a ON a.nik=u.nik_atasan LEFT OUTER JOIN role AS r ON r.id=u.id_role WHERE 1;
	private String nik,nama,telp,email,divisi,hak;
	private String password, id_divisi, id_role;
	
	public DataUsers() {}
	
	public DataUsers(String nik,String nama,String telp,String email,String divisi,String hak) {
		this.nik=nik;
		this.nama=nama;
		this.telp=telp;
		this.email=email;
		this.divisi=divisi;
		this.hak=hak;
	}
	
	public DataUsers(String nik,String nama,String password,String telp,String email,String id_divisi,String id_role) {
		this.nik=nik;
		this.nama=nama;
		this.password=password;
		this.telp=telp;
		this.email=email;
		this.id_divisi=id_divisi;
		this.id_role=id_role;
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

	public String getDivisi() {
		return divisi;
	}

	public void setDivisi(String divisi) {
		this.divisi = divisi;
	}

	public String getHak() {
		return hak;
	}

	public void setHak(String hak) {
		this.hak = hak;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
	
		
}
