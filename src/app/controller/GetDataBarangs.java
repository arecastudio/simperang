package app.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sun.istack.internal.Nullable;
import app.model.DataBarang;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GetDataBarangs {
	private Connection conn = null;	
	private ResultSet rs = null;
	private PreparedStatement pst = null;
	private DBHelper helper;
	
	public GetDataBarangs() {
		conn=helper.Konek();
	}
	
	public ObservableList<DataBarang> Ambil(@Nullable String nama){
		ObservableList<DataBarang> dataBarang=FXCollections.observableArrayList();
		try {
			String selip="";
			if (nama!=null) selip=" WHERE nama LIKE '%"+nama+"%' ";
			String sql = "SELECT id,nama,satuan,ket,harga FROM barang "+selip+" ORDER BY nama ASC;";
			pst=conn.prepareStatement(sql);
			//if (nama!=null) pst.setString(1,"'%"+nama+"%'");
			rs=pst.executeQuery();
			//int i=0;
			while (rs.next()) {
				//i++;
				//System.out.println("Nilai ke-"+i+": "+rs.getString("nama"));
				DataBarang db=new DataBarang();
				db.setId(rs.getString(1));
				db.setNama(rs.getString(2));
				db.setSatuan(rs.getString(3));
				db.setKeterangan(rs.getString(4));
				db.setHarga(rs.getString(5));
				dataBarang.add(db);
			}		
			pst.close();
		} catch (SQLException e) {
			// TODO: handle exception
		}
		return dataBarang;
	}

}
