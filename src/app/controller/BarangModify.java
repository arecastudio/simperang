package app.controller;

import app.model.DataBarang;

import java.io.InputStream;
import java.sql.*;

public class BarangModify {
	private Connection conn = null;	
	private ResultSet rs = null;
	private PreparedStatement pst = null;
	private DBHelper helper;
	
	public BarangModify() {
		conn=helper.Konek();
	}
	
	public int ProsesSave(DataBarang db){
		int ret=0;
		try {
			String sql = "INSERT INTO barang(nama,satuan,ket,stok,harga,img)VALUES(?,?,?,?,?,?);";
			pst=conn.prepareStatement(sql);
			pst.setString(1, db.getNama());
			pst.setString(2, db.getSatuan().toUpperCase());
			pst.setString(3, db.getKeterangan());
			pst.setInt(4, 0);
			pst.setInt(5, Integer.parseInt(db.getHarga()));

			pst.setBinaryStream(6,(InputStream)db.getImg());

			int qry=pst.executeUpdate();
			if (qry>0) ret=1;
			pst.close();
		}catch (SQLException e){
			e.printStackTrace();
		}
		return ret;
	}
	
	public int ProsesUpdate(DataBarang db) {
		int ret=0;
		String sql="";
		try{
			if (db.getImg()!=null){
				sql = "UPDATE barang SET nama=?,satuan=?,ket=?,harga=?,img=? WHERE id=?;";
				pst=conn.prepareStatement(sql);
				pst.setString(1, db.getNama());
				pst.setString(2, db.getSatuan().toUpperCase());
				pst.setString(3, db.getKeterangan());
				pst.setInt(4, Integer.parseInt(db.getHarga()));
				pst.setBinaryStream(5,(InputStream)db.getImg());
				pst.setInt(6, Integer.parseInt(db.getId()));
			}else {
				sql = "UPDATE barang SET nama=?,satuan=?,ket=?,harga=? WHERE id=?;";
				pst=conn.prepareStatement(sql);
				pst.setString(1, db.getNama());
				pst.setString(2, db.getSatuan().toUpperCase());
				pst.setString(3, db.getKeterangan());
				pst.setInt(4, Integer.parseInt(db.getHarga()));
				pst.setInt(5, Integer.parseInt(db.getId()));
			}
			int qry=pst.executeUpdate();
			if (qry>0) ret=1;
			pst.close();
		}catch (SQLException e){
			e.printStackTrace();
		}
		return ret;
	}
	
	public int ProsesDelete(String id){
		int ret=0;
		try {
			String sql = "DELETE FROM barang WHERE id=?;";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, Integer.parseInt(id));
			int qry=pst.executeUpdate();
			if (qry>0) ret=1;
			pst.close();
		}catch (SQLException e){
			e.printStackTrace();
		}
		return ret;
	}

	public byte[] GetImage(String id) {
		byte[] b=null;
		try {
			String sql = "SELECT img FROM barang WHERE id=?;";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, Integer.parseInt(id));
			rs=pst.executeQuery();
			if (rs.next()){
				b=rs.getBytes(1);
			}
			pst.close();
		}catch (SQLException e){
			e.printStackTrace();
		}
		return b;
	}

}
