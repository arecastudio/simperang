package app.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BarangSimpan {
	private Connection conn = null;	
	private ResultSet rs = null;
	private PreparedStatement pst = null;
	private DBHelper helper;
	private String nama, satuan,ket,id,harga;
	
	public BarangSimpan(String nama, String satuan, String ket,String id,String harga) {
		this.nama=nama;
		this.satuan=satuan;
		this.ket=ket;
		this.id=id;
		this.harga=harga;
		conn=helper.Konek();
	}
	
	public int ProsesSave() throws SQLException {
		int ret=0;
		if(nama.length()>0 && satuan.length()>0 && ket.length()>0 ) {
			//conn=helper.Konek();		
			String sql = "INSERT INTO barang(nama,satuan,ket,stok,harga)VALUES(?,?,?,?,?);";
			pst=conn.prepareStatement(sql);		
			pst.setString(1, nama);
			pst.setString(2, satuan.toUpperCase());
			pst.setString(3, ket);
			pst.setInt(4, 0);
			pst.setInt(5, Integer.parseInt(harga));
			/*rs=pst.executeQuery();
			int i=0;
			while (rs.next()) {
				i++;
				System.out.println("Nilai ke-"+i+": "+rs.getString("nama"));
			}*/
			int qry=pst.executeUpdate();
			if (qry>0) ret=1;		
			pst.close();
		}		
		return ret;
	}
	
	public int ProsesUpdate() throws SQLException {
		int ret=0;
		if(nama.length()>0 && satuan.length()>0 && ket.length()>0 && id.length()>0  ) {
			//conn=helper.Konek();		
			String sql = "UPDATE barang SET nama=?,satuan=?,ket=?,harga=? WHERE id=?;";
			pst=conn.prepareStatement(sql);		
			pst.setString(1, nama);
			pst.setString(2, satuan.toUpperCase());
			pst.setString(3, ket);
			pst.setInt(4, Integer.parseInt(harga));
			pst.setInt(5, Integer.parseInt(id));
			int qry=pst.executeUpdate();
			if (qry>0) ret=1;	
			pst.close();
		}		
		return ret;
	}
	
	public int ProsesDelete() throws SQLException {
		int ret=0;
		if(id.length()>0) {
			//conn=helper.Konek();		
			String sql = "DELETE FROM barang WHERE id=?;";
			pst=conn.prepareStatement(sql);	
			pst.setInt(1, Integer.parseInt(id));
			int qry=pst.executeUpdate();
			if (qry>0) ret=1;	
			pst.close();
		}		
		return ret;
	}

}
