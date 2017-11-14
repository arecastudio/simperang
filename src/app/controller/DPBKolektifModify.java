package app.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.model.DataPermintaan;
import app.model.DataTerbilang;
import javafx.collections.ObservableList;

public class DPBKolektifModify {
	private Connection conn = null;	
	private ResultSet rs = null;
	private PreparedStatement pst = null;
	private DBHelper helper;
	private String sql;
	
	public DPBKolektifModify() {
		conn=helper.Konek();
	}

	public DataTerbilang GetDPBTerbilang(String nomor){
		DataTerbilang dt=null;
		String sql="SELECT k.nomor,k.ket,CONCAT(DATE_FORMAT(k.tgl,'%d %M %Y'),' ') AS tanggal,k.nik,k.mail_send,SUM(p.harga_barang*p.jml_minta) AS tot,((SUM(p.harga_barang*p.jml_minta)*10)/100)AS ppn,(SUM(p.harga_barang*p.jml_minta)-((SUM(p.harga_barang*p.jml_minta)*10)/100))AS grand_total FROM dpb_kolektif AS k LEFT OUTER JOIN dpb_kolektif_d AS d ON d.nomor_dpb_kolektif=k.nomor LEFT OUTER JOIN permintaan_d AS p ON p.nomor_permintaan=d.nomor_permintaan WHERE k.nomor=? GROUP BY k.nomor;";
		try {
			pst=conn.prepareStatement(sql);
			pst.setString(1, nomor);
			rs=pst.executeQuery();
			if (rs.next()){
				dt=new DataTerbilang();
				dt.setNomor(nomor);
				dt.setTanggal(rs.getString(3));
				dt.setTotal(rs.getDouble(6));
				dt.setPpn(rs.getDouble(7));
				dt.setGrand_total(rs.getDouble(8));
				dt.setTerbilang(new Terbilang().bilangx(rs.getDouble(8))+"Rupiah.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dt;
	}
	
	public int Simpan(String nomor,String ket, String nik, ObservableList<DataPermintaan> dp) {
		//simpan dari user,ket serta data globalutil
		int ret=0;
		try {
			sql="INSERT IGNORE INTO dpb_kolektif(nomor,ket,nik)VALUES(?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, nomor);
			pst.setString(2, ket);
			pst.setString(3, nik);
			if(pst.executeUpdate()>0) {
				sql="INSERT IGNORE INTO dpb_kolektif_d(nomor_dpb_kolektif,nomor_permintaan,divisi)VALUES(?,?,?);";
				pst=conn.prepareStatement(sql);
				for (DataPermintaan d : dp) {
					pst.setString(1, nomor);
					pst.setString(2, d.getNomor());
					pst.setString(3, d.getNik());
					pst.executeUpdate();
				}
				
				sql="UPDATE permintaan SET status=? WHERE nomor=?;";
				pst=conn.prepareStatement(sql);
				for (DataPermintaan e : dp) {
					pst.setInt(1, 2);
					pst.setString(2, e.getNomor());
					pst.executeUpdate();
				}
			}
			pst.close();
			ret=1;
		} catch (SQLException e) { 
			e.printStackTrace();
		}		
		return ret;
	}

	public boolean UpdateOnMailSend(String nomor) {
		boolean b=false;
		try {
			pst=conn.prepareStatement("UPDATE dpb_kolektif SET mail_send=1 WHERE nomor=?;");
			pst.setString(1, nomor);
			pst.executeUpdate();
			b=true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}
}
