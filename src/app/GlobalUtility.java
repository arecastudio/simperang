package app;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.controller.DBHelper;
import app.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GlobalUtility {
	private static DataUserLogin user_logged_in;
	private static DataBarang barangs;
	public static DataBarang barang_dipilih;
	public static ObservableList<DataBarang> dataBarang;
	public static ObservableList<DataBarangDipilih> dataBarangDipilih;
	public static ObservableList<DataBarangDipilih> dataBarangDipilihEdit;
	public static ObservableList<DataPermintaan> dataPermintaan;
	public static ObservableList<DataPermintaan> dataDPB;
	
	public static String edit_nomor_surat="",edit_keterangan="";
	
	private static Connection conn = null;	
	private static ResultSet rs = null;
	private static PreparedStatement pst = null;
	private DBHelper helper;
	
	public GlobalUtility() throws SQLException {
		//this.user_logged_in=user_logged_in;
		dataBarang=FXCollections.observableArrayList();
		dataBarangDipilih=FXCollections.observableArrayList();
		dataBarangDipilihEdit=FXCollections.observableArrayList();
		dataPermintaan=FXCollections.observableArrayList();
		dataDPB=FXCollections.observableArrayList();
		conn=helper.Konek();
		setBarangs();
		//setDataPermintaan();
	}
	

	public static ObservableList<DataPermintaan> getDataDPB() {
		return dataDPB;
	}	
	public static void setDataDPB(ObservableList<DataPermintaan> dataDPB) {
		GlobalUtility.dataDPB = dataDPB;
	}



	public static DataUserLogin getUser_logged_in() {
		return user_logged_in;
	}

	/*public static ObservableList<DataBarang> getBarangs666() {
		try {
			setBarangs();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dataBarang;
	}*/
	
	/*public void setBarangs(DataBarang barangs) {
		GlobalUtility.barangs = barangs;
	}*/

	public static ObservableList<DataBarangDipilih> getBarang_dipilih() {
		return dataBarangDipilih;
	}

	public static ObservableList<DataBarangDipilih> getBarang_dipilihEdit() {
		return dataBarangDipilihEdit;
	}

	public static void setBarang_dipilih(ObservableList<DataBarangDipilih>  barang_dipilih) {
		GlobalUtility.dataBarangDipilih = barang_dipilih;
	}

	public static void setUser_logged_in(DataUserLogin user_logged_in) {
		GlobalUtility.user_logged_in = user_logged_in;
	}
	
	private static void setBarangs() throws SQLException {
		String sql = "SELECT id,nama,satuan,ket,harga FROM barang ORDER BY nama ASC;";
		pst=conn.prepareStatement(sql);
		rs=pst.executeQuery();
		//int i=0;
		dataBarang.clear();
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
	}
	
	/*private ObservableList<DataBarangDipilih> getBarangReview() throws SQLException{
		ObservableList<DataBarangDipilih> data=FXCollections.observableArrayList();
		String sql = "SELECT id,nama,jumlah,satuan,ket FROM barang ORDER BY nama ASC;";
		pst=conn.prepareStatement(sql);
		rs=pst.executeQuery();
		//int i=0;
		while (rs.next()) {
			//i++;
			//System.out.println("Nilai ke-"+i+": "+rs.getString("nama"));
			DataBarangDipilih db=new DataBarangDipilih();
			db.setId(rs.getString(1));
			db.setNama(rs.getString(2));
			db.setSatuan(rs.getString(3));
			db.setKeterangan(rs.getString(4));
			data.add(db);
		}		
		pst.close();
		return data;
	}*/
	
	public static ObservableList<DataDetailPermintaan> getDataDetailPermintaanByNomor(String nomor){
		ObservableList<DataDetailPermintaan> ddp=FXCollections.observableArrayList();		
		try {
			String sql="SELECT b.nama,p.jml_minta,b.satuan FROM permintaan_d AS p LEFT OUTER JOIN barang AS b ON b.id=p.id_barang WHERE p.nomor_permintaan=? ORDER BY b.nama ASC;";
			pst=conn.prepareStatement(sql);
			pst.setString(1, nomor);
			rs=pst.executeQuery();
			while(rs.next()) {
				ddp.add(new DataDetailPermintaan(rs.getString(1), rs.getString(2), rs.getString(3)));				
			}
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ddp;
	}
	
	public static ObservableList<DataPermintaan> getDataPermintaanForKolektif(Boolean isAdmin,Integer status) {
		ObservableList<DataPermintaan> data=FXCollections.observableArrayList();
		String sql=null;
		if(isAdmin.equals(Boolean.TRUE)) {
			sql="SELECT p.nomor, p.alasan, p.status, p.tgl,d.nama, p.nik FROM permintaan AS p LEFT OUTER JOIN user AS u ON u.nik=p.nik INNER JOIN divisi AS d ON d.id=u.id_divisi WHERE p.status="+status+" AND (p.nomor NOT IN (SELECT DISTINCT nomor_permintaan FROM dpb_kolektif_d)) ORDER BY p.tgl ASC;";
		}else {
			sql="SELECT p.nomor, p.alasan, p.status, p.tgl,d.nama, p.nik FROM permintaan AS p LEFT OUTER JOIN user AS u ON u.nik=p.nik INNER JOIN divisi AS d ON d.id=u.id_divisi WHERE p.status="+status+" AND d.id=? AND (p.nomor NOT IN (SELECT DISTINCT nomor_permintaan FROM dpb_kolektif_d)) ORDER BY p.tgl ASC;";
		}
		//String sql="SELECT p.nomor, p.alasan, p.status, p.tgl,(SELECT DISTINCT d.nama FROM divisi AS d INNER JOIN user AS u ON u.id_divisi=d.id WHERE u.nik=p.nik), p.nik FROM permintaan AS p WHERE p.status=0;";
		//String sql="SELECT p.nomor, p.alasan, p.status, p.tgl,d.nama, p.nik FROM permintaan AS p LEFT OUTER JOIN user AS u ON u.nik=p.nik INNER JOIN divisi AS d ON d.id=u.id_divisi WHERE p.status=0 AND d.id=?;";
		try {
			pst=conn.prepareStatement(sql);
			if(isAdmin.equals(Boolean.FALSE)) pst.setString(1, user_logged_in.getId_divisi());
			rs=pst.executeQuery();
			while(rs.next()) {
				DataPermintaan dp=new DataPermintaan();
				dp.setNomor(rs.getString(1));
				dp.setAlasan(rs.getString(2));
				dp.setStatus(getStatusPosting(rs.getString(3)));
				dp.setTgl(rs.getString(4));
				dp.setNik(rs.getString(5));
				data.add(dp);
			}
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	
	
	public static ObservableList<DataPermintaan> getDataPermintaan(Boolean isAdmin,Integer status) {
		ObservableList<DataPermintaan> data=FXCollections.observableArrayList();
		String sql=null;
		if(isAdmin.equals(Boolean.TRUE)) {
			sql="SELECT p.nomor, p.alasan, p.status, p.tgl,d.nama, p.nik FROM permintaan AS p LEFT OUTER JOIN user AS u ON u.nik=p.nik INNER JOIN divisi AS d ON d.id=u.id_divisi WHERE p.status="+status+";";
		}else {
			sql="SELECT p.nomor, p.alasan, p.status, p.tgl,d.nama, p.nik FROM permintaan AS p LEFT OUTER JOIN user AS u ON u.nik=p.nik INNER JOIN divisi AS d ON d.id=u.id_divisi WHERE p.status="+status+" AND d.id=?;";
		}
		//String sql="SELECT p.nomor, p.alasan, p.status, p.tgl,(SELECT DISTINCT d.nama FROM divisi AS d INNER JOIN user AS u ON u.id_divisi=d.id WHERE u.nik=p.nik), p.nik FROM permintaan AS p WHERE p.status=0;";
		//String sql="SELECT p.nomor, p.alasan, p.status, p.tgl,d.nama, p.nik FROM permintaan AS p LEFT OUTER JOIN user AS u ON u.nik=p.nik INNER JOIN divisi AS d ON d.id=u.id_divisi WHERE p.status=0 AND d.id=?;";
		try {
			pst=conn.prepareStatement(sql);
			if(isAdmin.equals(Boolean.FALSE)) pst.setString(1, user_logged_in.getId_divisi());
			rs=pst.executeQuery();
			while(rs.next()) {
				DataPermintaan dp=new DataPermintaan();
				dp.setNomor(rs.getString(1));
				dp.setAlasan(rs.getString(2));
				dp.setStatus(getStatusPosting(rs.getString(3)));
				dp.setTgl(rs.getString(4));
				dp.setNik(rs.getString(5));
				data.add(dp);
			}
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	//khusus class review permintaan
	public static ObservableList<DataPermintaan> getAllDataPermintaan(Boolean isAdmin) {
		ObservableList<DataPermintaan> data=FXCollections.observableArrayList();
		String sql=null;
		if(isAdmin.equals(Boolean.TRUE)) {
			sql="SELECT p.nomor, p.alasan, p.status, p.tgl,d.nama, p.nik FROM permintaan AS p LEFT OUTER JOIN user AS u ON u.nik=p.nik INNER JOIN divisi AS d ON d.id=u.id_divisi WHERE p.status<2 ORDER BY p.tgl ASC;";
		}else {
			sql="SELECT p.nomor, p.alasan, p.status, p.tgl,d.nama, p.nik FROM permintaan AS p LEFT OUTER JOIN user AS u ON u.nik=p.nik INNER JOIN divisi AS d ON d.id=u.id_divisi WHERE  p.status<2 AND d.id=? ORDER BY p.tgl ASC;";
		}
		//String sql="SELECT p.nomor, p.alasan, p.status, p.tgl,(SELECT DISTINCT d.nama FROM divisi AS d INNER JOIN user AS u ON u.id_divisi=d.id WHERE u.nik=p.nik), p.nik FROM permintaan AS p WHERE p.status=0;";
		//String sql="SELECT p.nomor, p.alasan, p.status, p.tgl,d.nama, p.nik FROM permintaan AS p LEFT OUTER JOIN user AS u ON u.nik=p.nik INNER JOIN divisi AS d ON d.id=u.id_divisi WHERE p.status=0 AND d.id=?;";
		try {
			pst=conn.prepareStatement(sql);
			if(isAdmin.equals(Boolean.FALSE)) pst.setString(1, user_logged_in.getId_divisi());
			rs=pst.executeQuery();
			while(rs.next()) {
				DataPermintaan dp=new DataPermintaan();
				dp.setNomor(rs.getString(1));
				dp.setAlasan(rs.getString(2));
				dp.setStatus(getStatusPosting(rs.getString(3)));
				dp.setTgl(rs.getString(4));
				dp.setNik(rs.getString(5));
				dp.setCetak(Boolean.TRUE);
				data.add(dp);
			}
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	//khusus laporan permintaan
	public static ObservableList<DataPermintaan> getAllDataPermintaanByIndikator(Boolean isAdmin,String nomor,String tglawal,String tglakhir) {
		ObservableList<DataPermintaan> data=FXCollections.observableArrayList();
		String selip=null;
		String sql=null;

		if (nomor!=""){
			selip=" AND (p.nomor='"+nomor+"') ";
		}else{
			selip=" AND (DATE(p.tgl) BETWEEN '"+tglawal+"' AND '"+tglakhir+"') ";
		}

		if(isAdmin.equals(Boolean.TRUE)) {
			sql="SELECT p.nomor, p.alasan, p.status, p.tgl,d.nama, p.nik FROM permintaan AS p LEFT OUTER JOIN user AS u ON u.nik=p.nik INNER JOIN divisi AS d ON d.id=u.id_divisi WHERE p.status>=0 "+selip+" ORDER BY p.tgl ASC;";
		}else {
			sql="SELECT p.nomor, p.alasan, p.status, p.tgl,d.nama, p.nik FROM permintaan AS p LEFT OUTER JOIN user AS u ON u.nik=p.nik INNER JOIN divisi AS d ON d.id=u.id_divisi WHERE  p.status>=0 "+selip+" AND d.id=? ORDER BY p.tgl ASC;";
		}
		try {
			pst=conn.prepareStatement(sql);
			if(isAdmin.equals(Boolean.FALSE)) pst.setString(1, user_logged_in.getId_divisi());
			rs=pst.executeQuery();
			while(rs.next()) {
				DataPermintaan dp=new DataPermintaan();
				dp.setNomor(rs.getString(1));
				dp.setAlasan(rs.getString(2));
				dp.setStatus(getStatusPosting(rs.getString(3)));
				dp.setTgl(rs.getString(4));
				dp.setNik(rs.getString(5));
				dp.setCetak(Boolean.TRUE);
				data.add(dp);
			}
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	//khusus laporan permintaan kolektif
	public static ObservableList<DataDPB> getAllDataDPBByIndikator(String nomor, String tglawal, String tglakhir) {
		ObservableList<DataDPB> data=FXCollections.observableArrayList();
		String selip=null;
		String sql=null;

		if (nomor!=""){
			selip=" (k.nomor='"+nomor+"') ";
		}else{
			selip=" (DATE(k.tgl) BETWEEN '"+tglawal+"' AND '"+tglakhir+"') ";
		}

		sql="SELECT k.nomor,k.ket,k.tgl,u.nama,COUNT(d.nomor_dpb_kolektif) AS jMinta,GROUP_CONCAT(d.nomor_permintaan) AS dtl,k.mail_send FROM dpb_kolektif AS k LEFT OUTER JOIN dpb_kolektif_d AS d ON d.nomor_dpb_kolektif=k.nomor LEFT OUTER JOIN user AS u ON u.nik=k.nik WHERE "+selip+" GROUP BY k.nomor ;";
		//System.out.println("=====================\n"+sql);
		try {
			pst=conn.prepareStatement(sql);
			rs=pst.executeQuery();
			while(rs.next()) {
				DataDPB dp=new DataDPB(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
				/*dp.setNomor();
				dp.setKet(rs.getString(2));
				dp.setTgl(rs.getString(3));
				dp.setNama(rs.getString(4));
				dp.setTotal_jml(rs.getString(5));
				dp.setTotal_jml(rs.getString(6));*/
				dp.setMail_send(GetStatus(rs.getInt(7)));
				data.add(dp);
			}
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	private static String GetStatus(int status){
		String ret="Belum Dikirim";
		if (status>0) ret="Sudah Dikirim";
		return  ret;
	}

	
	public static void downloadEditPermintaan(String nomor) {
		try {
			String sql="SELECT d.id_barang,b.nama,d.jml_minta,b.satuan,b.ket,b.harga FROM permintaan AS p INNER JOIN permintaan_d AS d ON d.nomor_permintaan=p.nomor INNER JOIN barang AS b ON b.id=d.id_barang WHERE p.status=0 AND p.nomor=?;";
			pst=conn.prepareStatement(sql);
			pst.setString(1, nomor);
			rs=pst.executeQuery();
			dataBarangDipilihEdit.clear();
			while(rs.next()) {
				DataBarangDipilih dbd=new DataBarangDipilih();
				dbd.setId(rs.getString(1));
				dbd.setNama(rs.getString(2));
				dbd.setJumlah(rs.getString(3));
				dbd.setSatuan(rs.getString(4));
				dbd.setKeterangan(rs.getString(5));
				dbd.setHarga(rs.getString(6));			
				dataBarangDipilihEdit.add(dbd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//dataBarangDipilihEdit.p
	}
	
	public static String getStatusPosting(String key) {
		String ret=null;
		switch (key) {
		case "0":
			ret="Review Divisi";
			break;
		case "1":
			ret="Review SDM";
			break;
		case "2":
			ret="Kirim ke Vendor";
			break;
		default:
			ret="Sudah diterima";
			break;
		}
		return ret;
	}
	
	public static ObservableList<DataRole> getRole(){
		ObservableList<DataRole> datarole=FXCollections.observableArrayList();
		String sql="SELECT id, nama FROM role ORDER BY id ASC;";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				datarole.add(new DataRole(rs.getString(1),rs.getString(2)));
				//System.out.println(rs.getString(2));
			}
			pst.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return datarole;
	}
	
	/*public static ObservableList<DataUsers> getUsers(){
		ObservableList<DataUsers> data=FXCollections.observableArrayList();
		try {
			String sql="SELECT u.nik,u.nama,u.telp,u.email,u.id_posisi,d.nama,a.nama,r.nama FROM user AS u LEFT OUTER JOIN divisi AS d ON d.id=u.id_divisi LEFT OUTER JOIN user AS a ON a.nik=u.nik_atasan LEFT OUTER JOIN role AS r ON r.id=u.id_role WHERE 1;";
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				data.add(new DataUsers(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
			}
			pst.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}*/
	
	public static boolean getInetStat() {
		boolean b=false;
		try{
            InetAddress address = InetAddress.getByName("www.java2s.com");
            b = address.isReachable(3000);
            //System.out.println("Is host reachable? " + reachable);
        } catch (Exception e){
            //e.printStackTrace();
			System.out.println("Tidak ada koneksi Internet.");
        }
		return b;
	}

}
