package app.controller;

import app.model.DataVendor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by android on 11/8/17.
 */
public class VendorModify {
    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;
    private DBHelper helper;

    public VendorModify(){
        conn=helper.Konek();
    }

    public ObservableList<DataVendor> GetTableItems(){
        ObservableList<DataVendor> data= FXCollections.observableArrayList();
        String sql="SELECT id,nama,alamat,telp,email,pemilik FROM vendor ORDER BY id ASC;";
        try {
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while (rs.next()){
                DataVendor d=new DataVendor();
                d.setId(rs.getString(1));
                d.setNama(rs.getString(2));
                d.setAlamat(rs.getString(3));
                d.setTelp(rs.getString(4));
                d.setEmail(rs.getString(5));
                d.setPemilik(rs.getString(6));
                data.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public DataVendor GetVenderoById(String id){
        DataVendor d=null;
        String sql="SELECT id,nama,alamat,telp,email,pemilik FROM vendor WHERE id=? ORDER BY id ASC;";
        try {
            pst=conn.prepareStatement(sql);
            pst.setInt(1,Integer.parseInt(id));
            rs=pst.executeQuery();
            if (rs.next()){
                d=new DataVendor();
                d.setId(rs.getString(1));
                d.setNama(rs.getString(2));
                d.setAlamat(rs.getString(3));
                d.setTelp(rs.getString(4));
                d.setEmail(rs.getString(5));
                d.setPemilik(rs.getString(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return d;
    }

    public int Tambah(DataVendor dv){
        int ret=0;
        String sql="INSERT INTO vendor(nama,alamat,telp,email,pemilik)VALUES(?,?,?,?,?);";
        try {
            pst=conn.prepareStatement(sql);
            pst.setString(1,dv.getNama());
            pst.setString(2,dv.getAlamat());
            pst.setString(3,dv.getTelp());
            pst.setString(4,dv.getEmail());
            pst.setString(5,dv.getPemilik());
            ret=pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public int Ubah(DataVendor dv){
        int ret=0;
        String sql="UPDATE vendor SET nama=?,alamat=?,telp=?,email=?,pemilik=? WHERE id=?;";
        try {
            pst=conn.prepareStatement(sql);
            pst.setString(1,dv.getNama());
            pst.setString(2,dv.getAlamat());
            pst.setString(3,dv.getTelp());
            pst.setString(4,dv.getEmail());
            pst.setString(5,dv.getPemilik());
            pst.setInt(6,Integer.parseInt(dv.getId()));
            ret=pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public int Hapus(String id){
        int ret=0;
        String sql="DELETE FROM vendor WHERE id=?;";
        try {
            pst=conn.prepareStatement(sql);
            pst.setInt(1,Integer.parseInt(id));
            ret=pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
