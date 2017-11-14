package app.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.model.DataRole;
import app.model.DataUsers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserModify {
	private Connection conn = null;	
	private ResultSet rs = null;
	private PreparedStatement pst = null;
	private DBHelper helper;
	private DataUsers dus;
	
	public UserModify() {
		conn=helper.Konek();
	}
	
	public int Hapus(String nik) {
		int ret=0;
		if(nik.length()>0) {			
			try {
				String sql="DELETE FROM user WHERE nik=?;";
				pst=conn.prepareStatement(sql);
				pst.setString(1, nik);
				pst.executeUpdate();
				ret=1;
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}
		return ret;
	}
	
	public int Simpan(String nik,String nama,String password,String telp,String email,String id_divisi,String id_role) {
		int ret=0;		
			try {
				String sql="INSERT INTO user (nik, nama, password, telp, email, id_divisi, id_role)VALUES(?, ?, MD5(?), ?, ?, ?, ?);";
				pst=conn.prepareStatement(sql);
				pst.setString(1, nik);
				pst.setString(2, nama);
				pst.setString(3, password);
				pst.setString(4, telp);
				pst.setString(5, email);
				pst.setInt(6,Integer.parseInt(id_divisi));
				pst.setInt(7,Integer.parseInt(id_role));
				pst.executeUpdate();
				ret=1;
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}		
		return ret;
	}
	
	/*public int UbahNP(String nik,String nama,String telp,String email,String id_posisi,String id_posisi_atasan,String id_role) {
		int ret=0;		
			try {				
				String sql="UPDATE user SET nama=?, telp=?, email=?, id_posisi=?, id_posisi_atasan=?, id_role=? WHERE nik=?;";
				pst=conn.prepareStatement(sql);
				pst.setString(7, nik);
				pst.setString(1, nama);
				pst.setString(2, telp);
				pst.setString(3, email);
				pst.setInt(4,Integer.parseInt(id_posisi));
				pst.setInt(6,Integer.parseInt(id_posisi_atasan));
				pst.setInt(7,Integer.parseInt(id_role));
				pst.executeUpdate();
				ret=1;
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return ret;
	}*/
	
	public int Ubah(String nik,String nama,String password,String telp,String email,String id_divisi,String id_role) {
		int ret=0;	
		String sql="";
			try {
				if(password.length()>0) {
					sql="UPDATE user SET nama=?, password=MD5(?), telp=?, email=?, id_divisi=?, id_role=? WHERE nik=?;";
					pst=conn.prepareStatement(sql);
					pst.setString(7, nik);
					pst.setString(1, nama);
					pst.setString(2, password);
					pst.setString(3, telp);
					pst.setString(4, email);
					pst.setInt(5,Integer.parseInt(id_divisi));
					pst.setInt(6,Integer.parseInt(id_role));
					pst.executeUpdate();
				}else {
					sql="UPDATE user SET nama=?, telp=?, email=?, id_divisi=?, id_role=? WHERE nik=?;";
					pst=conn.prepareStatement(sql);
					pst.setString(6, nik);
					pst.setString(1, nama);
					pst.setString(2, telp);
					pst.setString(3, email);
					pst.setInt(4,Integer.parseInt(id_divisi));
					pst.setInt(5,Integer.parseInt(id_role));
					pst.executeUpdate();
				}
				ret=1;
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}		
		return ret;
	}

	//nikCol,namaCol,telpCol,emailCol,posisiCol,divisiCol,atasanCol,hakCol
	public ObservableList<DataUsers> GetTableItems(){
		ObservableList<DataUsers> data=FXCollections.observableArrayList();
		String sql="SELECT u.nik,u.nama,u.telp,u.email,d.nama,r.nama FROM user AS u LEFT OUTER JOIN divisi AS d ON d.id=u.id_divisi LEFT OUTER JOIN role AS r ON r.id=u.id_role WHERE 1;";
		try {
			pst=conn.prepareStatement(sql);
			rs=pst.executeQuery();
			while (rs.next()){
				DataUsers du=new DataUsers(rs.getString(1),rs.getString(2),rs.getString(3),
				rs.getString(4),rs.getString(5),rs.getString(6)	);
				data.add(du);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	//nikCol,namaCol,telpCol,emailCol,posisiCol,divisiCol,atasanCol,hakCol
	public ObservableList<DataUsers> GetTableItemsInDivisi(String id_divisi){
		ObservableList<DataUsers> data=FXCollections.observableArrayList();
		String sql="SELECT u.nik,u.nama,u.telp,u.email,d.nama,r.nama FROM user AS u LEFT OUTER JOIN divisi AS d ON d.id=u.id_divisi LEFT OUTER JOIN role AS r ON r.id=u.id_role WHERE u.id_divisi=?;";
		try {
			pst=conn.prepareStatement(sql);
			pst.setInt(1,Integer.parseInt(id_divisi));
			rs=pst.executeQuery();
			while (rs.next()){
				DataUsers du=new DataUsers(rs.getString(1),rs.getString(2),rs.getString(3),
						rs.getString(4),rs.getString(5),rs.getString(6)	);
				data.add(du);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public DataRole GetRoleByNik(String nik){
		DataRole dr=null;
		String sql="SELECT DISTINCT r.id,r.nama FROM role AS r INNER JOIN user AS u ON u.id_role=r.id WHERE u.nik=?;";
		try {
			pst=conn.prepareStatement(sql);
			pst.setString(1, nik);
			rs=pst.executeQuery();
			if(rs.next()) {
				dr=new DataRole(rs.getString(1), rs.getString(2));
			}
			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dr;
	}

	public DataUsers GetDataUserByNik(String nik){
		DataUsers du=null;
		//nik,String nama,String telp,String email,String posisi,String divisi,String atasan,String hak
		String sql="SELECT nik, nama, password, telp, email, id_divisi, id_role FROM user WHERE nik=?;";
		try {
			pst=conn.prepareStatement(sql);
			pst.setString(1,nik);
			rs=pst.executeQuery();
			if (rs.next()){
				du=new DataUsers(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return du;
	}

	public DataUsers GetMyManager(String nik){
		DataUsers du=null;
		//nik,String nama,String telp,String email,String posisi,String divisi,String atasan,String hak
		//String sql="SELECT u.nik, u.nama, u.telp, u.email, u.id_posisi,p.id_divisi, u.id_posisi_atasan, u.id_role FROM user AS u LEFT OUTER JOIN posisi AS p ON p.id=u.id_posisi WHERE u.id_posisi=?;";
		String sql="SELECT a.nik, a.nama, a.telp, a.email, a.id_posisi,p.id_divisi, a.id_posisi_atasan, a.id_role FROM user AS u LEFT OUTER JOIN posisi AS p ON p.id=u.id_posisi LEFT OUTER JOIN user AS a ON a.id_posisi=u.id_posisi_atasan WHERE u.nik=? LIMIT 1;";
		try {
			pst=conn.prepareStatement(sql);
			pst.setString(1,nik);
			rs=pst.executeQuery();
			if (rs.next()){
				du=new DataUsers(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return du;
	}

	public String GetIdPosisiByNik(String nik){
		String ret="";
		String sql="SELECT id_posisi FROM user WHERE nik=?;";
		if (nik!=null){
			try {
				pst=conn.prepareStatement(sql);
				pst.setString(1,nik);
				rs=pst.executeQuery();
				if (rs.next()) ret=rs.getString(1);
			}catch (SQLException e){
				e.printStackTrace();
			}
		}
		return ret;
	}
}
