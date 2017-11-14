package app.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.GlobalUtility;
import app.model.*;
import javafx.collections.ObservableList;

public class PermintaanSimpan {
	//private DataPermintaan dp;
	private DataPermintaanDetail dpb;	

	private Connection conn = null;	
	private ResultSet rs = null;
	private PreparedStatement pst = null;
	private DBHelper helper;
	public static String nomor_permintaan;
	private Boolean isEdit;
	private DataUserLogin user_login;
	
	public PermintaanSimpan() {
		conn=helper.Konek();
		user_login=(DataUserLogin)GlobalUtility.getUser_logged_in();
	}
	
	public int SimpanAll(DataPermintaan dp, ObservableList<DataBarangDipilih> data1, Boolean isEdit) {
		int ret=0;
		String sql="";
		if (isEdit.equals(Boolean.FALSE)) {
			//mode insert
			try {
				sql = "INSERT INTO permintaan (nomor, alasan, nik, id_posisi,nik_atasan,id_posisi_atasan, nama,nama_posisi,nama_atasan,nama_posisi_atasan) VALUES(?, ?, ?, ?,?,?, ?,?,?,?);";
				pst=conn.prepareStatement(sql);		
				pst.setString(1, dp.getNomor());
				pst.setString(2, dp.getAlasan());
				pst.setString(3, dp.getNik());
				//tambahan setelah perubahan pada struktur database
				pst.setInt(4,Integer.parseInt(dp.getId_posisi()));
				pst.setString(5, dp.getNik_atasan());
				pst.setInt(6,Integer.parseInt(dp.getId_posisi_atasan()));

				//nama,nama_posisi,nama_atasan,nama_posisi_atasan
				pst.setString(7,dp.getNama());
				pst.setString(8,dp.getNama_posisi());
				pst.setString(9,dp.getNama_atasan());
				pst.setString(10,dp.getNama_posisi_atasan());
				
				if(pst.executeUpdate()>0) {
					//simpan permintaan_d
					for (DataBarangDipilih data2 : data1) {
						if( data2.getId().length()>0 && data2.getJumlah().length()>0) {				
							sql = "INSERT IGNORE INTO permintaan_d(nomor_permintaan, id_barang, jml_minta,user_nama,nama_barang,satuan_barang,harga_barang)VALUES(?, ?, ?,?,?,?,?);";
							pst=conn.prepareStatement(sql);		
							pst.setString(1, dp.getNomor());
							pst.setInt(2, Integer.parseInt(data2.getId()));
							pst.setInt(3, Integer.parseInt(data2.getJumlah()));
							pst.setString(4, user_login.getNama());
							pst.setString(5, data2.getNama());
							pst.setString(6, data2.getSatuan());
							pst.setDouble(7, Double.parseDouble(data2.getHarga()));
							ret=pst.executeUpdate();						
							pst.close();								
						}
					}
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			//mode update			
			try {
				pst=conn.prepareStatement("DELETE FROM permintaan_d WHERE nomor_permintaan=?;");
				pst.setString(1, dp.getNomor());
				pst.executeUpdate();
				
				for (DataBarangDipilih data2 : data1) {
					if( data2.getId().length()>0 && data2.getJumlah().length()>0) {				
						sql = "INSERT INTO permintaan_d(nomor_permintaan, id_barang, jml_minta,user_nama,nama_barang,satuan_barang,harga_barang)VALUES(?, ?, ?,?,?,?,?) ON DUPLICATE KEY UPDATE jml_minta=VALUES(jml_minta);";
						pst=conn.prepareStatement(sql);		
						pst.setString(1, dp.getNomor());
						pst.setInt(2, Integer.parseInt(data2.getId()));
						pst.setInt(3, Integer.parseInt(data2.getJumlah()));
						pst.setString(4, user_login.getNama());
						pst.setString(5, data2.getNama());
						pst.setString(6, data2.getSatuan());
						pst.setInt(7, Integer.parseInt(data2.getHarga()));
						ret=pst.executeUpdate();						
						pst.close();								
					}
				}
				//if(ret>0)GlobalUtility.dataBarangDipilih.removeAll();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
	
	/*public int permintaan666(DataPermintaan dp, Boolean isEdit) throws SQLException {
		int ret=0;
		this.isEdit=isEdit;
		//this.dp=dp;
		this.nomor_permintaan=dp.getNomor();
		if(isEdit.equals(Boolean.FALSE)) {
			if(dp.getNik().length()>0 && dp.getNomor().length()>0 && dp.getAlasan().length()>0) {				
							
				//String sql = "INSERT INTO permintaan (nomor, alasan, nik) VALUES(?, ?, ?)ON DUPLICATE KEY UPDATE alasan=VALUES(alasan),nik=VALUES(nik);";
				String sql = "INSERT INTO permintaan (nomor, alasan, nik) VALUES(?, ?, ?);";
				pst=conn.prepareStatement(sql);		
				pst.setString(1, dp.getNomor());
				pst.setString(2, dp.getAlasan());
				pst.setString(3, dp.getNik());
				int qry=pst.executeUpdate();
				if (qry>0) ret=1;
				pst.close();
				
			}
		}else ret=1;
		return ret;		
	}*/
	
	/*private void permintaanEdit(DataPermintaan dp) throws SQLException {
		//int ret=0;
		//this.dp=dp;
		if(dp.getNik().length()>0 && dp.getNomor().length()>0 && dp.getAlasan().length()>0) {
			this.nomor_permintaan=dp.getNomor();
			
			String sql = "DELETE FROM permintaan_d WHERE nomor_permintaan=?;";
			pst=conn.prepareStatement(sql);		
			pst.setString(1, dp.getNomor());
			int qry=pst.executeUpdate();
			
			sql="DELETE FROM permintaan WHERE nomor=?";
			pst=conn.prepareStatement(sql);		
			pst.setString(1, dp.getNomor());
			qry=pst.executeUpdate();
			
			pst.close();
			
		}
		//permintaan(dp);
	}*/
	
	/*public Boolean permintaan_d666(ObservableList<DataBarangDipilih> data1, Boolean isEdit) throws SQLException {
		Boolean ret=Boolean.FALSE;
		//this.dpb=dpb;
		this.isEdit=isEdit;
		
		if(isEdit.equals(Boolean.TRUE)) {
			pst=conn.prepareStatement("DELETE FROM permintaan_d WHERE nomor_permintaan=?;");
			pst.setString(1, this.nomor_permintaan);
			pst.executeUpdate();
		}
		
		for (DataBarangDipilih data2 : data1) {
			if( data2.getId().length()>0 && data2.getJumlah().length()>0) {				
				String sql = "INSERT INTO permintaan_d(nomor_permintaan, id_barang, jml_minta,user_nama)VALUES(?, ?, ?,?) ON DUPLICATE KEY UPDATE jml_minta=VALUES(jml_minta);";
				pst=conn.prepareStatement(sql);		
				pst.setString(1, this.nomor_permintaan);
				pst.setInt(2, Integer.parseInt(data2.getId()));
				pst.setInt(3, Integer.parseInt(data2.getJumlah()));
				pst.setString(4, user_login.getNama());
				int qry=pst.executeUpdate();
				if (qry>0) ret=(ret && Boolean.TRUE);
				pst.close();				
			}
		}
			
		return ret;
	}*/
	
	public Boolean permintaan_dEdit(ObservableList<DataBarangDipilih> data1) throws SQLException {
		Boolean ret=Boolean.FALSE;
		//this.dpb=dpb;
		
		for (DataBarangDipilih data2 : data1) {
			if( data2.getId().length()>0 && data2.getJumlah().length()>0) {				
				String sql = "INSERT INTO permintaan_d(nomor_permintaan, id_barang, jml_minta)VALUES(?, ?, ?);";
				pst=conn.prepareStatement(sql);		
				pst.setString(1, this.nomor_permintaan);
				pst.setInt(2, Integer.parseInt(data2.getId()));
				pst.setInt(3, Integer.parseInt(data2.getJumlah()));
				int qry=pst.executeUpdate();
				if (qry>0) ret=(ret && Boolean.TRUE);
				pst.close();				
			}
		}
			
		return ret;
	}
	
	public int HapusPermintaan(String nomor){
		int ret=0;
		try {
					
			pst=conn.prepareStatement("DELETE FROM permintaan_d WHERE nomor_permintaan=?;");
			pst.setString(1, nomor);
			pst.executeUpdate();
			
			pst=conn.prepareStatement("DELETE FROM permintaan WHERE nomor=?;");
			pst.setString(1, nomor);
			pst.executeUpdate();
			
			pst.close();
			
			ret=1;
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return ret;
	}

	public DataPermintaanReport getPermintaanReport(String nomor){
		DataPermintaanReport dpr=new DataPermintaanReport();
		//String sql="SELECT p.nomor,p.alasan,DATE(p.tgl),CONCAT('NIK. ',p.nik),u.nama,d.id,d.nama,CURRENT_DATE() FROM permintaan AS p INNER JOIN user AS u ON u.nik=p.nik INNER JOIN divisi AS d ON d.id=u.id_divisi WHERE p.nomor=? LIMIT 1;";
		String sql="SELECT p.nomor,p.alasan,DATE(p.tgl),CONCAT('NIK. ',p.nik) AS nik,u.nama,d.id,d.nama,CURRENT_DATE(),CONCAT('NIK.',p.nik_atasan) AS nik_atasan,a.nama,p1.nama,p2.nama FROM permintaan AS p INNER JOIN user AS u ON u.nik=p.nik INNER JOIN divisi AS d ON d.id=u.id_divisi LEFT OUTER JOIN user AS a ON a.nik=p.nik_atasan LEFT OUTER JOIN posisi AS p1 ON p1.id=p.id_posisi LEFT OUTER JOIN posisi AS p2 ON p2.id=p.id_posisi_atasan WHERE p.nomor=? LIMIT 1;";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1,nomor);
			rs=pst.executeQuery();
			if (rs.next()){
				dpr.setNomor(rs.getString(1));
				dpr.setAlasan(rs.getString(2));
				dpr.setTgl(rs.getString(3));
				dpr.setDivisi(rs.getString(7));
				dpr.setNik_user(rs.getString(4));
				dpr.setUser(rs.getString(5));
				dpr.setTgl_cetak(rs.getString(8));

				dpr.setNik_atasan(rs.getString(9));
				dpr.setAtasan(rs.getString(10));
				dpr.setJabatan(rs.getString(11));
				dpr.setJabatan_atasan(rs.getString(12));
			}
		}catch (SQLException ex){
			ex.printStackTrace();
		}
		return dpr;
	}

	public DataDPBReport getDPBReport(String nomor){
		DataDPBReport dpr=new DataDPBReport();
		String sql="SELECT k.ket,DATE(k.tgl),k.nik,u.nama,CURRENT_DATE() FROM dpb_kolektif AS k INNER JOIN user AS u ON u.nik=k.nik WHERE k.nomor=? LIMIT 1;";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1,nomor);
			rs=pst.executeQuery();
			if (rs.next()){
				dpr.setNomor(nomor);
				dpr.setKeterangan(rs.getString(1));
				dpr.setTgl(rs.getString(2));
				dpr.setNik_staf(rs.getString(3));
				dpr.setNama_staf(rs.getString(4));
				dpr.setTgl_cetak(rs.getString(5));
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
		return dpr;
	}

	public String[] GetUserOnPermintaan(String nomor){
		String[] ret=null;
		try {
			pst=conn.prepareStatement("SELECT nik,id_posisi,nik_atasan,id_posisi_atasan FROM permintaan WHERE nomor=?;");
			pst.setString(1,nomor);
			rs=pst.executeQuery();
			if (rs.next()){
				ret=new String[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)};
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}

}
