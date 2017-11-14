package app.view;

import app.controller.VendorModify;
import app.model.DataVendor;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Optional;

/**
 * Created by rail on 11/8/17.
 */
public class Vendor extends VBox {
    private HBox hbox1,hbox2,hBox3;
    private GridPane grid;
    private TextField text_nama,text_alamat,text_email,text_telp,text_pemilik;
    private Button button_save,button_refresh,button_del;
    private TableView table;
    private Label label_status;
    private String tmpId="";

    private void Inits(){
        setSpacing(5);
        setPadding(new Insets(5,5,5,5));
        setAlignment(Pos.TOP_CENTER);
        setMaxHeight(500);
        setMaxWidth(1024);
        getStyleClass().add("box-color");

        label_status=new Label("Keterangan :");

        grid=new GridPane();
        grid.setHgap(15);
        grid.setVgap(5);
        grid.setPadding(new Insets(0,0,35,0));

        text_nama=new TextField();
        text_nama.setMaxWidth(200);

        text_alamat=new TextField();
        text_alamat.setMinWidth(400);

        text_email=new TextField();
        text_email.setMinWidth(350);

        text_telp=new TextField();
        text_telp.setMaxWidth(200);

        text_pemilik=new TextField();
        text_pemilik.setMaxWidth(200);

        button_del=new Button("Hapus");
        button_del.setPrefWidth(100);
        button_del.setOnAction(event -> {
            if (tmpId!=""){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Dialog Konfirmasi");
                alert.setHeaderText("Anda akan menghapus data ini,");
                alert.setContentText("Yakin?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
				    int i=new VendorModify().Hapus(tmpId);
                    if (i>0){
                        Refresh();
                        label_status.setText("Keterangan : Berhasil menghapus Data Vendor");
                    }else {
                        label_status.setText("Keterangan : Gagal menghapus Data Vendor");
                    }
                }
            }
        });

        button_refresh=new Button("Refresh");
        button_refresh.setPrefWidth(100);
        button_refresh.setOnAction(event -> {
            Refresh();
        });

        button_save=new Button("Simpan");
        button_save.setPrefWidth(100);
        button_save.setOnAction(event -> {
            if (tmpId!=""){
                //update
                if (text_nama.getText().toString().trim().length()>0 && text_alamat.getText().toString().trim().length()>0 && text_email.getText().toString().toString().trim().length()>0 && text_telp.getText().toString().trim().length()>0){
                    DataVendor dv=new DataVendor();
                    dv.setId(tmpId);
                    dv.setNama(text_nama.getText().toString().trim());
                    dv.setAlamat(text_alamat.getText().toString().trim());
                    dv.setEmail(text_email.getText().toString().trim());
                    dv.setTelp(text_telp.getText().toString().trim());
                    dv.setPemilik(text_pemilik.getText().toString().trim());
                    int i=new VendorModify().Ubah(dv);
                    if (i>0){
                        Refresh();
                        label_status.setText("Keterangan : Berhasil mengubah Data Vendor");
                    }else {
                        label_status.setText("Keterangan : Gagal mengubah Data Vendor");
                    }
                }
            }else{
                //insert
                if (text_nama.getText().toString().trim().length()>0 && text_alamat.getText().toString().trim().length()>0 && text_email.getText().toString().toString().trim().length()>0 && text_telp.getText().toString().trim().length()>0){
                    DataVendor dv=new DataVendor();
                    dv.setNama(text_nama.getText().toString().trim());
                    dv.setAlamat(text_alamat.getText().toString().trim());
                    dv.setEmail(text_email.getText().toString().trim());
                    dv.setTelp(text_telp.getText().toString().trim());
                    dv.setPemilik(text_pemilik.getText().toString().trim());
                    int i=new VendorModify().Tambah(dv);
                    if (i>0){
                        Refresh();
                        label_status.setText("Keterangan : Berhasil menambahkan Data Vendor");
                    }else {
                        label_status.setText("Keterangan : Gagal menambahkan Data Vendor");
                    }
                }
            }
        });

        hbox1=new HBox(10);
        //hbox1.setPadding(new Insets(30,5,5,5));
        hbox1.setAlignment(Pos.BOTTOM_CENTER);

        table=new TabelVendor();
        table.setItems(new VendorModify().GetTableItems());

        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(table.getSelectionModel().getSelectedItem() != null) {
                    DataVendor dv=(DataVendor)table.getSelectionModel().getSelectedItem();
                    tmpId=dv.getId();
                    text_nama.setText(dv.getNama());
                    text_alamat.setText(dv.getAlamat());
                    text_email.setText(dv.getEmail());
                    text_telp.setText(dv.getTelp());
                    text_pemilik.setText(dv.getPemilik());

                    label_status.setText("Keterangan :");
                }
            }
        });
    }

    public Vendor() {
        Inits();
        text_nama.requestFocus();

        grid.add(new Label("Nama"),0,0);
        grid.add(text_nama,1,0);

        grid.add(new Label("Alamat"),0,1);
        grid.add(text_alamat,1,1);

        grid.add(new Label("Pemilik"),0,2);
        grid.add(text_pemilik,1,2);

        grid.add(new Label("Telepon"),2,0);
        grid.add(text_telp,3,0);

        grid.add(new Label("Email"),2,1);
        grid.add(text_email,3,1);

        hbox1.getChildren().addAll(button_save,button_refresh,button_del);

        getChildren().addAll(new LabelJudul("Data Vendor"),new Separator(Orientation.HORIZONTAL),grid,new Separator(Orientation.HORIZONTAL),hbox1,table,new HBox(label_status));
    }

    private void Refresh(){
        text_telp.setText("");
        text_email.setText("");
        text_alamat.setText("");
        text_nama.setText("");
        text_pemilik.setText("");
        text_nama.requestFocus();
        label_status.setText("Keterangan :");
        tmpId="";

        table.setItems(new VendorModify().GetTableItems());
    }
}
