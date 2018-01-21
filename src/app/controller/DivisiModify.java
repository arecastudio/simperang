package app.controller;

import app.model.DataDivisi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisiModify {
	private Connection conn = null;	
	private ResultSet rs = null;
	private PreparedStatement pst = null;
	private DBHelper helper;
	private String id, nama, ket;

	public DivisiModify(){
		conn=helper.Konek();
	}
	
	public DivisiModify(String id, String nama, String ket) {
		this.id=id;
		this.nama=nama;
		this.ket=ket;
		conn=helper.Konek();
	}

	public ObservableList<DataDivisi>GetTableItems(){
		ObservableList<DataDivisi> data=FXCollections.observableArrayList();
		try {
			String sql = "SELECT id, nama, ket FROM divisi ORDER BY id ASC;";
			pst=conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()) {
				data.add(new DataDivisi(rs.getString(1), rs.getString(2), rs.getString(3)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public int Simpan() throws SQLException {
		int ret=0;
		if(nama.length()>0 && ket.length()>0 ) {
			//if(nik_manager.equals(null))nik_manager="";
			String sql = "INSERT INTO divisi(nama, ket)VALUES(?,?);";
			pst=conn.prepareStatement(sql);		
			pst.setString(1, nama);			
			pst.setString(2, ket);
			int qry=pst.executeUpdate();
			if (qry>0) ret=1;	
			pst.close();
		}
		return ret;
	}
	
	public int Ubah(String id) throws SQLException {
		int ret=0;
		if(nama.length()>0 && ket.length()>0 ) {
			//if(nik_manager.equals(null))nik_manager="";
			String sql = "UPDATE divisi SET nama=?, ket=? WHERE id=?;";
			pst=conn.prepareStatement(sql);		
			pst.setString(1, nama);			
			pst.setString(2, ket);
			pst.setInt(3,Integer.parseInt(id));
			int qry=pst.executeUpdate();
			if (qry>0) ret=1;	
			pst.close();
		}
		return ret;
	}
	
	public int Hapus() throws SQLException {
		int ret=0;
		if(id.length()>0) {	
			String sql = "DELETE FROM divisi WHERE id=?;";
			pst=conn.prepareStatement(sql);	
			pst.setInt(1, Integer.parseInt(id));
			int qry=pst.executeUpdate();
			if (qry>0) ret=1;	
			pst.close();
		}		
		return ret;
	}

	public String GetNamaByIdPosisi(String id){
		String ret="";
		String sql="SELECT d.nama FROM divisi AS d INNER JOIN posisi AS p ON p.id_divisi=d.id WHERE p.id=?;";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1,id);
			rs=pst.executeQuery();
			if (rs.next()){
				ret=rs.getString(1);
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return  ret;
	}

	public DataDivisi GetDataDivisiByNik(String nik){
		DataDivisi dd=null;
		String sql="SELECT d.id,d.nama,d.ket FROM user AS u LEFT OUTER JOIN divisi AS d ON d.id=u.id_divisi WHERE u.nik=? LIMIT 1;";
		try{
			pst=conn.prepareStatement(sql);
			pst.setString(1,nik);
			rs=pst.executeQuery();
			if (rs.next()){
				dd=new DataDivisi(rs.getString(1),rs.getString(2),rs.getString(3));
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return dd;
	}

	//tidak bisa dipakai karena tidak semua data posisi sudah dimasukkan id_divisi nya.
	public DataDivisi GetDataDivisiByPosisi(String id_posisi){
		DataDivisi dd=null;
		String sql="SELECT p.id_divisi,d.nama,d.ket FROM posisi AS p LEFT OUTER JOIN divisi AS d ON d.id=p.id_divisi WHERE p.id_divisi=? LIMIT 1;";
		try{
			pst=conn.prepareStatement(sql);
			pst.setString(1,id_posisi);
			rs=pst.executeQuery();
			if (rs.next()){
				dd=new DataDivisi(rs.getString(1),rs.getString(2),rs.getString(3));
			}
			//System.out.println("DATA DIVISI di DivisiModify.java--> "+dd.getId()+""+dd.getNama());
		}catch (SQLException e){
			e.printStackTrace();
		}
		return dd;
	}

}
