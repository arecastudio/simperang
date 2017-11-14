package app.view;

import app.GlobalUtility;
import app.controller.RoleModify;
import app.model.DataRole;
import app.model.DataSetRole;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.util.Optional;

/**
 * Created by android on 10/31/17.
 */
public class SetRole extends VBox {
    private HBox hbox1,hbox2,hbox3;
    private VBox vbox1,vbox2,vbox3,vbox4;
    private TextField text_nama;
    private Button button_show,button_save,button_clear,button_add,button_del;
    private ComboBox<DataRole> cbxRole;
    private String tmpRole="";
    private CheckBox cek_barang,cek_divisi,cek_buat_permintaan,cek_edit_permintaan,cek_review_permintaan,cek_hapus_permintaan,
    cek_buat_dpb_kolektif,cek_hapus_dpb_kolektif,cek_notifikasi_email,cek_laporan_permintaan,cek_laporan_dpb_kolektif,cek_laporan_dpb_vendor,cek_pengaturan_db;
    private Separator sep1,sep2;
    private final String ket_hapus="Penting :\nMenghapus Hak Akses akang mengakibatakan data pengguna dengan hak level tersebut ikut terhapus.\nUntuk meng-hapus Hak User, pilih terlebih dahulu pada [Pangaturan Hak User] di Atas, \nkemudian tekan Tombol [Hapus] untk konfirmasi penghapusan.";

    public SetRole(){
        inits();
        Refresh();

        hbox1.getChildren().addAll(new Label("Pilih Hak Akses : "),cbxRole,button_clear,button_save);

        vbox1.getChildren().addAll(new Label("Menu Berkas"),cek_barang,cek_divisi,new Label(" & Posisi / Jabatan"),new Label(" & Data Vendor"));

        vbox2.getChildren().addAll(new Label("Menu Proses"),cek_buat_permintaan,cek_edit_permintaan,cek_hapus_permintaan,cek_review_permintaan,cek_buat_dpb_kolektif,cek_hapus_dpb_kolektif,cek_notifikasi_email);

        vbox3.getChildren().addAll(new Label("Menu Laporan"),cek_laporan_permintaan,cek_laporan_dpb_kolektif,cek_laporan_dpb_vendor);

        vbox4.getChildren().addAll(new Label("Menu Pengaturan"),cek_pengaturan_db);

        hbox2.getChildren().addAll(vbox1,vbox2,vbox3,vbox4);

        hbox3.getChildren().addAll(text_nama,button_add);

        this.getChildren().addAll(new LabelJudul("Pengaturan Hak User"),new Separator(Orientation.HORIZONTAL),new VBox(new LabelJudul("Tambah Hak User").Sub(),hbox3),sep1,new VBox(new LabelJudul("Ubah Hak User").Sub(),hbox1,hbox2),sep2,new VBox(new LabelJudul("Hapus Hak User").Sub(),new Label(ket_hapus),button_del),new Separator(Orientation.HORIZONTAL));
    }
    private void inits(){
        setSpacing(5);
        setPadding(new Insets(5, 5, 5, 5));
        setMaxHeight(600);
        setMaxWidth(1024);
        setAlignment(Pos.TOP_CENTER);
        getStyleClass().add("box-color");

        sep1=new Separator();
        sep1.setOrientation(Orientation.HORIZONTAL);

        sep2=new Separator();
        sep2.setOrientation(Orientation.HORIZONTAL);

        hbox1=new HBox(5);
        hbox1.setAlignment(Pos.CENTER_LEFT);
        hbox1.setPadding(new Insets(3,3,10,3));

        hbox2=new HBox(20);
        hbox2.setAlignment(Pos.TOP_LEFT);
        hbox2.setPadding(new Insets(5,5,15,5));
        hbox2.setStyle("-fx-background-color:#fff;-fx-background-radius:5px;");

        hbox3=new HBox(5);
        hbox3.setAlignment(Pos.CENTER_LEFT);
        hbox3.setPadding(new Insets(5,3,5,3));
        //hbox3.setStyle("-fx-background-color:#fff;-fx-background-radius:5px;");

        vbox1=new VBox(5);
        vbox1.setAlignment(Pos.TOP_LEFT);
        vbox2=new VBox(5);
        vbox2.setAlignment(Pos.TOP_LEFT);
        vbox3=new VBox(5);
        vbox3.setAlignment(Pos.TOP_LEFT);
        vbox4=new VBox(5);
        vbox4.setAlignment(Pos.TOP_LEFT);

        cek_barang=new CheckBox("Data Barang");
        cek_divisi=new CheckBox("Data Divisi");

        cek_buat_permintaan=new CheckBox("Buat Permintaan");
        cek_review_permintaan=new CheckBox("Review Permintaan");
        cek_hapus_permintaan=new CheckBox("Hapus Permintaan");
        cek_edit_permintaan=new CheckBox("Edit Permintaan");

        cek_buat_dpb_kolektif=new CheckBox("Buat DPB Kolektif");
        cek_hapus_dpb_kolektif=new CheckBox("Hapus DPB Kolektif");
        cek_notifikasi_email=new CheckBox("Notifikasi Email");

        cek_laporan_permintaan=new CheckBox("Laporan Permintaan");
        cek_laporan_dpb_kolektif=new CheckBox("Laporan DPB Kolektif");
        cek_laporan_dpb_vendor=new CheckBox("Rekap Permintaan ke Vendor");

        cek_pengaturan_db=new CheckBox("Basis Data");
        
        text_nama=new TextField();
        text_nama.setPrefWidth(350);
        text_nama.setPromptText("Nama Hak Akses User");
        text_nama.setText("");

        button_del=new Button("Hapus");
        button_del.setPrefWidth(150);
        button_del.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Alert alert;
                if (tmpRole!=""){
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Dialog Konfirmasi");
                    alert.setHeaderText("Hapus Hak Akses User");
                    alert.setContentText("Yakin untuk hapus data ini?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                        int i=new RoleModify().Delete(tmpRole);
                        if (i>0){
                            /*alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Dialog Informasi");
                            alert.setHeaderText("Hapus Hak Akses");
                            alert.setContentText("Proses Hapus Hak Akses berhasil.");
                            alert.show();*/
                            Refresh();
                        }
                    }

                }
            }
        });
        
        button_add=new Button("Tambahkan");
        button_add.setPrefWidth(150);
        button_add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (text_nama.getText().toString().trim().length()>0){
                    int i=new RoleModify().Add(text_nama.getText().toString().trim());
                    if (i>0){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Dialog Informasi");
                        alert.setHeaderText("Tambah Hak Akses [ "+text_nama.getText().toString().trim()+" ]");
                        alert.setContentText("Proses berhasil, anda telah menambah Hak Akses.");
                        alert.show();
                        Refresh();
                    }
                }
            }
        });

        button_clear=new Button("Clear");
        button_clear.setPrefWidth(100);
        button_clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Refresh();
            }
        });

        button_save=new Button("Simpan");
        button_save.setPrefWidth(100);
        button_save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (tmpRole!=""){
                    int i=new RoleModify().Update(tmpRole,new DataSetRole("","",Cek(cek_barang),Cek(cek_divisi),Cek(cek_buat_permintaan),Cek(cek_edit_permintaan),Cek(cek_review_permintaan),Cek(cek_hapus_permintaan),
                            Cek(cek_buat_dpb_kolektif),Cek(cek_hapus_dpb_kolektif),Cek(cek_laporan_permintaan),Cek(cek_laporan_dpb_kolektif),Cek(cek_pengaturan_db),Cek(cek_notifikasi_email),Cek(cek_laporan_dpb_vendor)));
                    if (i>0){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Dialog Informasi");
                        alert.setHeaderText("Simpan Hak Akses");
                        alert.setContentText("Proses berhasil, anda telah mengubah Hak Akses.");
                        alert.show();

                        Refresh();
                    }

                    //System.out.println("akses ke barang: ");
                }
            }
        });

        button_show=new Button("Tampilkan");
        button_show.setPrefWidth(100);
        button_show.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //
            }
        });

        //------------------------------------
        cbxRole=new ComboBox<>(GlobalUtility.getRole());
        cbxRole.setPrefWidth(200);
        cbxRole.setConverter(new StringConverter<DataRole>() {
            @Override
            public String toString(DataRole object) {
                return object.nama;
            }

            @Override
            public DataRole fromString(String string) {
                return cbxRole.getItems().stream().filter(ap ->
                        ap.getNama().equals(string)).findFirst().orElse(null);
            }
        });

        cbxRole.valueProperty().addListener((obs, oldval, newval) -> {
            if(newval != null) {
                System.out.println("Role: " + newval.getNama() + ", ID: " + newval.getId());
		        /*label.setText("NIK : "+newval.getNik());*/
                cbxRole.setStyle(newval.getId());
                tmpRole=newval.getId();
                ShowRole();
            }
        });
    }

    private void Refresh(){
        tmpRole="";

        cbxRole.getItems().clear();
        cbxRole.setItems(GlobalUtility.getRole());
        cbxRole.getSelectionModel().clearSelection();

        text_nama.setText("");

        button_save.setDisable(false);
        button_del.setDisable(false);
        UnselectCheck();
    }

    private void UnselectCheck(){
        cek_barang.setSelected(false);
        cek_divisi.setSelected(false);
        cek_buat_permintaan.setSelected(false);
        cek_edit_permintaan.setSelected(false);
        cek_review_permintaan.setSelected(false);
        cek_hapus_permintaan.setSelected(false);
        cek_buat_dpb_kolektif.setSelected(false);
        cek_hapus_dpb_kolektif.setSelected(false);
        cek_laporan_permintaan.setSelected(false);
        cek_laporan_dpb_kolektif.setSelected(false);
        cek_pengaturan_db.setSelected(false);
        cek_notifikasi_email.setSelected(false);
        cek_laporan_dpb_vendor.setSelected(false);
    }

    private String Cek(CheckBox cek){
        String ret="0";
        if(cek.isSelected())ret="1";
        return ret;
    }

    private void ShowRole(){
        if (tmpRole!=""){
            button_save.setDisable(false);
            button_del.setDisable(false);
            if (tmpRole.equals("1")){
                button_save.setDisable(true);
                button_del.setDisable(true);
            }
            UnselectCheck();
            DataSetRole dsr=new RoleModify().GetRoleById(tmpRole);
            if(dsr.getBerkas_barang().equals("1"))cek_barang.setSelected(true);
            if(dsr.getBerkas_divisi().equals("1"))cek_divisi.setSelected(true);
            if(dsr.getProses_buat_permintaan().equals("1"))cek_buat_permintaan.setSelected(true);
            if(dsr.getProses_edit_permintaan().equals("1"))cek_edit_permintaan.setSelected(true);
            if(dsr.getProses_review_permintaan().equals("1"))cek_review_permintaan.setSelected(true);
            if(dsr.getProses_hapus_permintaan().equals("1"))cek_hapus_permintaan.setSelected(true);
            if(dsr.getProses_buat_dpb_kolektif().equals("1"))cek_buat_dpb_kolektif.setSelected(true);
            if(dsr.getProses_hapus_dpb_kolektif().equals("1"))cek_hapus_dpb_kolektif.setSelected(true);
            if(dsr.getProses_cek_email().equals("1"))cek_notifikasi_email.setSelected(true);

            if(dsr.getLaporan_permintaan().equals("1"))cek_laporan_permintaan.setSelected(true);
            if(dsr.getLaporan_dpb_kolektif().equals("1"))cek_laporan_dpb_kolektif.setSelected(true);
            if(dsr.getPengaturan_basis_data().equals("1"))cek_pengaturan_db.setSelected(true);

            if (dsr.getLaporan_dpb_vendor().equals("1"))cek_laporan_dpb_vendor.setSelected(true);
        }
    }
}
