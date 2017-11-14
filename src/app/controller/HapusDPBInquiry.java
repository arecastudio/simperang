package app.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.model.DataDPB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class HapusDPBInquiry {
	private Connection conn = null;	
	private ResultSet rs = null;
	private PreparedStatement pst = null;
	private DBHelper helper;

	public HapusDPBInquiry() {
		conn=helper.Konek();
	}
	
	public ObservableList<DataDPB> listTableDPB(String tglawal,String tglakhir,String nomor){
		ObservableList<DataDPB> d=FXCollections.observableArrayList();
		String sql="SELECT k.nomor,k.ket,k.tgl,u.nama,COUNT(d.nomor_dpb_kolektif) AS jMinta,GROUP_CONCAT(d.nomor_permintaan) AS dtl FROM dpb_kolektif AS k LEFT OUTER JOIN dpb_kolektif_d AS d ON d.nomor_dpb_kolektif=k.nomor LEFT OUTER JOIN user AS u ON u.nik=k.nik WHERE (DATE(k.tgl) BETWEEN ? AND ?) GROUP BY k.nomor LIMIT 1;";
		try {
			pst=conn.prepareStatement(sql);
			pst.setString(1, tglawal);
			pst.setString(2, tglakhir);
			rs=pst.executeQuery();
			while(rs.next()) {
				d.add(new DataDPB(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
			}
		} catch (SQLException e) {		
			e.printStackTrace();
		}		
		return d;
	}

	public int Hapus(String tmpNomor) {
		/*update status masing2 permintaan ke riview SDM
		 * hapus permintaan kolektif
		 * */
		int ret=0;
		String sql="";
		try {
			sql="UPDATE permintaan SET status=1 WHERE nomor IN (SELECT nomor_permintaan FROM dpb_kolektif_d WHERE nomor_dpb_kolektif=?);";
			pst=conn.prepareStatement(sql);
			pst.setString(1, tmpNomor);
			if(pst.executeUpdate()>0) { //jumlah return value / integer adalah jumlah row yang dieksekusi
				sql="DELETE FROM dpb_kolektif_d WHERE nomor_dpb_kolektif=?;";
				pst=conn.prepareStatement(sql);
				pst.setString(1, tmpNomor);
				pst.executeUpdate();
					
				sql="DELETE FROM dpb_kolektif WHERE nomor=?;";
				pst=conn.prepareStatement(sql);
				pst.setString(1, tmpNomor);
				pst.executeUpdate();
				
				ret=1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
}
