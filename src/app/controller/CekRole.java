package app.controller;

import app.model.DataCekRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by android on 11/3/17.
 */
public class CekRole {
    private DBHelper helper;
    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;

    public CekRole(){
        conn=helper.Konek();
    }

    //untuk menu, pakai metode setdisable, jangan setvisible
    public DataCekRole GetRole(String id_role){
        DataCekRole dcr=null;
        String sql="SELECT id, nama, berkas_barang, berkas_divisi, proses_buat_permintaan, proses_edit_permintaan, proses_hapus_permintaan, proses_review_permintaan, proses_buat_dpb_kolektif, proses_hapus_dpb_kolektif, laporan_permintaan, laporan_dpb_kolektif, pengaturan_basis_data, pengaturan_admin,proses_cek_email,laporan_dpb_vendor FROM role WHERE id=?";
        try {
            pst=conn.prepareStatement(sql);
            pst.setInt(1,Integer.parseInt(id_role));
            rs=pst.executeQuery();
            if (rs.next()){
                dcr=new DataCekRole(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getInt(7),rs.getInt(8),rs.getInt(9),rs.getInt(10),rs.getInt(11),rs.getInt(12),rs.getInt(13),rs.getInt(14),rs.getInt(15),rs.getInt(16));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dcr;
    }

}
