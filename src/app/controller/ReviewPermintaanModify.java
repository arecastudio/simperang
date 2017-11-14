package app.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.model.DataPermintaan;

public class ReviewPermintaanModify {
	private Connection conn = null;	
	private ResultSet rs = null;
	private PreparedStatement pst = null;
	private DBHelper helper;
	private String sql;
	
	private DataPermintaan dp;
	private String nomor, alasan, status, tgl, nik;
	
	public ReviewPermintaanModify() {
		conn=helper.Konek();
	}

	public DataPermintaan getDPbyNomor(String nomor) {
		dp=new DataPermintaan();		
		try {
			sql=new String("SELECT alasan,status,tgl,nik FROM permintaan WHERE nomor=?;");
			pst=conn.prepareStatement(sql);
			pst.setString(1, nomor);
			rs=pst.executeQuery();
			if(rs.next()) {
				dp.setNomor(nomor);
				dp.setAlasan(rs.getString(1));
				dp.setStatus(rs.getString(2));
				dp.setTgl(rs.getString(3));
				dp.setNik(rs.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dp;
	}
	
	public int setApprove(String nomor,Integer upperLevel) {
		int ret=0;
		try {
			sql=new String("UPDATE permintaan SET status=? WHERE nomor=?;");
			pst=conn.prepareStatement(sql);
			pst.setInt(1, upperLevel);
			pst.setString(2, nomor);
			pst.executeUpdate();
			ret=1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
}
