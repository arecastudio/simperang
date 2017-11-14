package app.controller;

import app.model.DataNota;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by rail on 11/13/17.
 */
public class NotaModify {
    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;
    private DBHelper helper;
    private String sql="";

    public NotaModify(){
        conn=helper.Konek();
    }

    public int Simpan(DataNota d){
        int ret=0;
        sql="INSERT IGNORE INTO nota(nomor,id_vendor,nomor_dpb_kolektif,mail_send)VALUES(?,?,?,?);";
        try {
            pst=conn.prepareStatement(sql);
            pst.setString(1,d.getNomor());
            pst.setInt(2,d.getId_vendor());
            pst.setString(3,d.getNomor_dpb_kolektif());
            pst.setInt(4,d.getMail_send());
            ret=pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
