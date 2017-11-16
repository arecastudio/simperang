package app.controller;

import app.model.DataNota;
import app.model.DataNotaDPB;
import com.sun.istack.internal.Nullable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    public int Simpan(DataNotaDPB d){
        int ret=0;
        sql="INSERT IGNORE INTO nota(nomor,id_vendor,nomor_dpb_kolektif,mail_send,konten_surat)VALUES(?,?,?,?,?);";
        try {
            pst=conn.prepareStatement(sql);
            pst.setString(1,d.getNomor());
            pst.setInt(2,d.getId_vendor());
            pst.setString(3,d.getNomor_dpb_kolektif());
            pst.setInt(4,d.getMail_send());
            pst.setString(5,d.getKonten_surat());
            ret=pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public int HapusNota(String nomor){
        int ret=0;
        sql="DELETE FROM nota WHERE nomor=?;";
        try {
            pst=conn.prepareStatement(sql);
            pst.setString(1,nomor);
            ret=pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public ObservableList<DataNota> GetTableItem(@Nullable String nomor, @Nullable String tglawal, @Nullable String tglakhir){
        //nomor,nomor_dpb_kolektif,nama_vendor,tgl,mail_send
        ObservableList<DataNota> notas= FXCollections.observableArrayList();

        try {
            if (nomor!=null){
                sql="SELECT n.nomor,n.nomor_dpb_kolektif,n.id_vendor,v.nama,n.tgl,n.mail_send FROM nota AS n LEFT OUTER JOIN vendor AS v ON v.id=n.id_vendor WHERE n.nomor LIKE ? ORDER BY n.tgl ASC;";
                pst=conn.prepareStatement(sql);
                pst.setString(1,"%"+nomor+"%");
                System.out.println("search by nomor. "+ nomor);
            }else {
                sql="SELECT n.nomor,n.nomor_dpb_kolektif,n.id_vendor,v.nama,n.tgl,n.mail_send FROM nota AS n LEFT OUTER JOIN vendor AS v ON v.id=n.id_vendor WHERE (DATE(n.tgl) BETWEEN ? AND ?) ORDER BY n.tgl ASC;";
                pst=conn.prepareStatement(sql);
                pst.setString(1,tglawal);
                pst.setString(2,tglakhir);
                System.out.println("search by tanggal.");
            }
            rs=pst.executeQuery();
            while (rs.next()){
                DataNota dn=new DataNota();
                dn.setNomor(rs.getString(1));
                dn.setNomor_dpb_kolektif(rs.getString(2));
                dn.setNama_vendor(rs.getString(4));
                dn.setTgl(rs.getString(5));

                String stat_kirim;
                if (rs.getInt(6)>0){
                    stat_kirim="Terkirim";
                }else {
                    stat_kirim="Belum terkirim";
                }
                dn.setMail_send(stat_kirim);
                notas.add(dn);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return notas;
    }

    public DataNota GetNotaByNomor(String nomor){
        DataNota nota=null;
        //vendor: nama,email,pemilik
        //manager: nama,jabatan
        //sql="SELECT n.nomor,n.nomor_dpb_kolektif,n.id_vendor,v.nama,n.tgl,n.mail_send,n.konten_surat FROM nota AS n LEFT OUTER JOIN vendor AS v ON v.id=n.id_vendor WHERE n.nomor=? ORDER BY n.tgl ASC;";
        sql="SELECT n.nomor,n.nomor_dpb_kolektif,n.id_vendor,v.nama,v.pemilik,v.email,v.alamat,k.nama_atasan,k.nama_posisi_atasan,CONCAT(DATE_FORMAT(n.tgl,'%d %M %Y'),' ') AS tanggal,n.mail_send,n.konten_surat FROM nota AS n LEFT OUTER JOIN vendor AS v ON v.id=n.id_vendor LEFT OUTER JOIN dpb_kolektif AS k ON k.nomor=n.nomor_dpb_kolektif WHERE n.nomor=? ORDER BY n.tgl ASC;";
        try {
            pst=conn.prepareStatement(sql);
            pst.setString(1,nomor);
            rs=pst.executeQuery();
            if (rs.next()){
                nota=new DataNota();
                nota.setNomor(rs.getString(1));
                nota.setNomor_dpb_kolektif(rs.getString(2));
                nota.setNama_vendor(rs.getString(4));
                nota.setPemilik_vendor(rs.getString(5));
                nota.setEmail_vendor(rs.getString(6));
                nota.setAlamat_vendor(rs.getString(7));
                nota.setNama_atasan(rs.getString(8));
                nota.setNama_posisi_atasan(rs.getString(9));
                nota.setTgl(rs.getString(10));

                String stat_kirim;
                if (rs.getInt(11)>0){
                    stat_kirim="Terkirim";
                }else {
                    stat_kirim="Belum terkirim";
                }
                nota.setMail_send(stat_kirim);

                nota.setKonten_surat(rs.getString(12));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nota;
    }
}
