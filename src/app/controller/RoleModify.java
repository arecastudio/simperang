package app.controller;

import app.model.DataSetRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by rail on 10/31/17.
 */
public class RoleModify {
    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;
    private DBHelper helper;

    public RoleModify(){
        conn=helper.Konek();
    }

    public int Add(String nama){
        int ret=0;
        String sql="INSERT INTO role(nama)VALUES(?);";
        try{
            pst=conn.prepareStatement(sql);
            pst.setString(1,nama);
            ret=pst.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return ret;
    }

    public int Delete(String id){
        int ret=0;
        String sql="DELETE FROM user WHERE id_role=?;";
        try{
            pst=conn.prepareStatement(sql);
            pst.setInt(1,Integer.parseInt(id));
            ret=pst.executeUpdate();

            sql="DELETE FROM role WHERE id=?;";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,Integer.parseInt(id));
            ret=pst.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return ret;
    }

    public int Update(String id,DataSetRole dsr){
        int ret=0;
        String sql="UPDATE role SET berkas_barang=?, berkas_divisi=?, proses_buat_permintaan=?, proses_edit_permintaan=?, proses_hapus_permintaan=?, proses_review_permintaan=?,proses_buat_dpb_kolektif=?, proses_hapus_dpb_kolektif=?, laporan_permintaan=?, laporan_dpb_kolektif=?, pengaturan_basis_data=?,proses_cek_email=?,laporan_dpb_vendor=?"
                +" WHERE id=?;";
        try{
            pst=conn.prepareStatement(sql);
            pst.setInt(1,Integer.parseInt(dsr.getBerkas_barang()));
            pst.setInt(2,Integer.parseInt(dsr.getBerkas_divisi()));
            pst.setInt(3,Integer.parseInt(dsr.getProses_buat_permintaan()));
            pst.setInt(4,Integer.parseInt(dsr.getProses_edit_permintaan()));
            pst.setInt(5,Integer.parseInt(dsr.getProses_review_permintaan()));
            pst.setInt(6,Integer.parseInt(dsr.getProses_hapus_permintaan()));
            pst.setInt(7,Integer.parseInt(dsr.getProses_buat_dpb_kolektif()));
            pst.setInt(8,Integer.parseInt(dsr.getProses_hapus_dpb_kolektif()));
            pst.setInt(9,Integer.parseInt(dsr.getLaporan_permintaan()));
            pst.setInt(10,Integer.parseInt(dsr.getLaporan_dpb_kolektif()));
            pst.setInt(11,Integer.parseInt(dsr.getPengaturan_basis_data()));
            pst.setInt(12,Integer.parseInt(dsr.getProses_cek_email()));
            pst.setInt(13,Integer.parseInt(dsr.getLaporan_dpb_vendor()));
            pst.setString(14,id);

            ret=pst.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public DataSetRole GetRoleById(String id){
        DataSetRole dsr=null;
        String sql="SELECT nama, berkas_barang, berkas_divisi, proses_buat_permintaan, proses_edit_permintaan, proses_hapus_permintaan, proses_review_permintaan, proses_buat_dpb_kolektif, proses_hapus_dpb_kolektif, laporan_permintaan, laporan_dpb_kolektif, pengaturan_basis_data,proses_cek_email,laporan_dpb_vendor FROM role WHERE id=?;";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1,id);
            rs=pst.executeQuery();
            if (rs.next()){
                dsr= new DataSetRole(id,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),rs.getString(13),rs.getString(14));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dsr;
    }

    public String GetNamaById(String id){
        String ret="";
        String sql="SELECT nama FROM role WHERE id=?;";
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
}
