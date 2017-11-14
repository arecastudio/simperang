package app.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.model.DataBarang;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BarangShow {
	private ObservableList<DataBarang> data;
	private DBHelper helper;
	private Connection conn;	
	
	public BarangShow() {
		data=FXCollections.observableArrayList();	
		conn=helper.Konek();
	}
	
	public ObservableList<DataBarang> DataBarangShow(){	
		try {
			String sql = "SELECT id,nama,satuan,ket,harga,stok FROM barang ORDER BY id ASC;";
			ResultSet rs = conn.createStatement().executeQuery(sql);
			while(rs.next()) {
				DataBarang db=new DataBarang();
				db.setId(rs.getString(1));
				db.setNama(rs.getString(2));
				db.setSatuan(rs.getString(3));
				db.setKeterangan(rs.getString(4));
				db.setHarga(rs.getString(5));
				data.add(db);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;		
	}

}
