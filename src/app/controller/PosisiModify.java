package app.controller;

import app.model.DataDivisi;
import app.model.DataPosisi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by rail on 11/2/17.
 */
public class PosisiModify {
    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;
    private DBHelper helper;
    private String sql="";

    public PosisiModify() {
        conn=helper.Konek();
    }

    public ObservableList<DataPosisi> GetTableItems(){
        ObservableList<DataPosisi> data=FXCollections.observableArrayList();
        try {
            sql="SELECT p.id,p.nama,p.ket,p.id_divisi,d.nama FROM posisi AS p LEFT OUTER JOIN divisi AS d ON d.id=p.id_divisi WHERE 1;";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while (rs.next()){
                DataPosisi dp=new DataPosisi();
                dp.setId(rs.getString(1));
                dp.setNama(rs.getString(2));
                dp.setKet(rs.getString(3));
                dp.setId_divisi(rs.getString(4));
                dp.setNama_divisi(rs.getString(5));
                data.add(dp);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return data;
    }

    public ObservableList<DataPosisi> GetTableItemsByDivisi(String id_divisi){
        ObservableList<DataPosisi> data=FXCollections.observableArrayList();
        try {
            sql="SELECT p.id,p.nama,p.ket,p.id_divisi,d.nama FROM posisi AS p LEFT OUTER JOIN divisi AS d ON d.id=p.id_divisi WHERE p.id_divisi=?;";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,Integer.parseInt(id_divisi));
            rs=pst.executeQuery();
            while (rs.next()){
                DataPosisi dp=new DataPosisi();
                dp.setId(rs.getString(1));
                dp.setNama(rs.getString(2));
                dp.setKet(rs.getString(3));
                dp.setId_divisi(rs.getString(4));
                dp.setNama_divisi(rs.getString(5));
                data.add(dp);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return data;
    }

    public DataPosisi GetPosisiById(String id){
        DataPosisi dp=new DataPosisi();
        try {
            sql="SELECT p.id,p.nama,p.ket,p.id_divisi,d.nama FROM posisi AS p LEFT OUTER JOIN divisi AS d ON d.id=p.id_divisi WHERE p.id=?;";
            pst=conn.prepareStatement(sql);
            pst.setString(1,id);
            rs=pst.executeQuery();
            if (rs.next()){
                dp.setId(id);
                dp.setNama(rs.getString(2));
                dp.setKet(rs.getString(3));
                dp.setId_divisi(rs.getString(4));
                dp.setNama_divisi(rs.getString(5));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dp;
    }

    public DataDivisi GetDivisiById(String id){
        DataDivisi dd=null;
        try {
            sql="SELECT id,nama,ket FROM divisi WHERE id=?;";
            pst=conn.prepareStatement(sql);
            pst.setString(1,id);
            rs=pst.executeQuery();
            if (rs.next()){
                dd=new DataDivisi(rs.getString(1),rs.getString(2),rs.getString(3));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dd;
    }

    public int DeletePosisiById(String id){
        int ret=0;
        try {
            pst=conn.prepareStatement("DELETE FROM posisi WHERE id=?;");
            pst.setString(1,id);
            ret=pst.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return ret;
    }

    public int AddPosisi(String nama,String ket,String id_divisi){
        int ret=0;
        try {
            pst=conn.prepareStatement("INSERT INTO posisi(nama,ket,id_divisi)VALUES(?,?,?);");
            pst.setString(1,nama);
            pst.setString(2,ket);
            pst.setInt(3,Integer.parseInt(id_divisi));
            ret=pst.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return ret;
    }

    public int UpdatePosisi(String id,String nama,String ket,String id_divisi){
        int ret=0;
        if(ket.equals(null))ket="-";
        try {
            pst=conn.prepareStatement("UPDATE posisi SET nama=?,ket=?,id_divisi=? WHERE id=?;");
            pst.setString(1,nama);
            pst.setString(2,ket);
            pst.setInt(3,Integer.parseInt(id_divisi));
            pst.setInt(4,Integer.parseInt(id));
            ret=pst.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return ret;
    }
}
