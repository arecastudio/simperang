package app.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.GlobalUtility;
import app.model.DataUserLogin;

public class LoginInquiry {
	private String nik,password;
	private DBHelper helper;
	private Connection conn = null;
	private ResultSet rs = null;
	private PreparedStatement pst = null;
	private GlobalUtility util;
	
	public LoginInquiry(String nik,String password) {
		this.nik=nik;
		this.password=password;
		
		helper=new DBHelper();
		conn=helper.Konek();		
	}
	
	public int Proses() throws SQLException {
		int ret=0;
		//SELECT nik,nama,password,telp,email,id_posisi,id_posisi_atasan,id_role FROM user;
		//SELECT u.nik,u.nama,u.password,u.telp,u.email,u.id_posisi,p.nama,u.id_posisi_atasan,a.nama,u.id_role,r.nama,d.id,d.nama FROM user AS u LEFT OUTER JOIN posisi AS p ON p.id=u.id_posisi LEFT OUTER JOIN posisi AS a ON a.id=u.id_posisi_atasan LEFT OUTER JOIN role AS r ON r.id=u.id_role LEFT OUTER JOIN divisi AS d ON d.id=p.id_divisi WHERE 1;
		if(nik.length()>0 && password.length()>0) {
			String sql="SELECT u.nik, u.nama, u.password, u.telp, u.email, u.id_divisi, u.id_role,r.nama,d.nama FROM user AS u LEFT OUTER JOIN role AS r ON r.id=u.id_role LEFT OUTER JOIN divisi AS d ON d.id=u.id_divisi WHERE u.nik=? AND u.password=md5(?);";
			pst=conn.prepareStatement(sql);
			pst.setString(1, nik);
			pst.setString(2, password);
			rs=pst.executeQuery();
			if(rs.next()) {
				ret=1;
				DataUserLogin uli=new DataUserLogin(rs.getString(1),rs.getString(2),
				rs.getString(3),rs.getString(4),rs.getString(5),
				rs.getString(6),rs.getString(7),rs.getString(8),
						rs.getString(9));
				new GlobalUtility().setUser_logged_in(uli);
			}
			rs.close();
			pst.close();
		}
		return ret;
	}

}
