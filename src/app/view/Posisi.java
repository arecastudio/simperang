package app.view;

import app.controller.DivisiModify;
import app.controller.PosisiModify;
import app.model.DataDivisi;
import app.model.DataPosisi;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
 * Created by rail on 11/2/17.
 */
public class Posisi extends VBox {
    private HBox hbox1;
    private TextField text_nama,text_ket;
    private ComboBox<DataDivisi> cbxDivisi;
    private Button button_save,button_del,button_refresh;
    private String tmpDivisi="",tmpPosisi="";
    private TableView table;

    public Posisi(){
        Inits();

        hbox1.getChildren().addAll(text_nama,cbxDivisi,text_ket,button_save,button_refresh);
        getChildren().addAll(new LabelJudul("Data Posisi / Jabatan User"),new Separator(Orientation.HORIZONTAL),hbox1,table,button_del);
    }

    private void Inits() {
        setSpacing(5);
        setPadding(new Insets(5, 5, 5, 5));
        setMaxHeight(500);
        setAlignment(Pos.TOP_CENTER);
        getStyleClass().add("box-color");

        hbox1=new HBox(5);
        hbox1.setPadding(new Insets(5,5,5,5));
        //hbox1.setStyle("-fx-background-color:#fff;-fx-background-radius:5px;");

        text_nama=new TextField();
        text_nama.setPrefWidth(300);
        text_nama.setPromptText("Nama posisi / jabatan");

        text_ket=new TextField();
        text_ket.setPrefWidth(400);
        text_ket.setPromptText("Keterangan tambahan (opsional)");

        button_save=new Button("Simpan");
        button_save.setPrefWidth(100);
        button_save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int i=0;
                if (tmpDivisi!="" && tmpDivisi!=null){
                    if (tmpPosisi!="" && tmpPosisi!=null){
                        i=new PosisiModify().UpdatePosisi(tmpPosisi,text_nama.getText().toString().trim(),text_ket.getText().toString().trim(),tmpDivisi);
                    }else {
                        i=new PosisiModify().AddPosisi(text_nama.getText().toString().trim(),text_ket.getText().toString().trim(),tmpDivisi);
                    }
                }

                if (i>0){
                    Refresh();
                }
            }
        });

        button_del=new Button("Hapus");
        button_del.setPrefWidth(100);
        button_del.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int i=0;
                if (tmpPosisi!=""){
                    //alert
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Dialog Konfirmasi");
                    alert.setHeaderText("Anda akan menghapus data ini,");
                    alert.setContentText("Yakin?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                        i=new PosisiModify().DeletePosisiById(tmpPosisi);
                    } else {
                        // ... user chose CANCEL or closed the dialog
                    }
                }

                if (i>0){
                    Refresh();
                }
            }
        });

        button_refresh=new Button("Refresh");
        button_refresh.setPrefWidth(100);
        button_refresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Refresh();
            }
        });


        //-----------------------
        cbxDivisi=new ComboBox<>(new DivisiModify().GetTableItems());
        cbxDivisi.setConverter(new StringConverter<DataDivisi>() {

            @Override
            public String toString(DataDivisi object) {
                return object.getNama();
            }

            @Override
            public DataDivisi fromString(String string) {
                // TODO Auto-generated method stub
                return cbxDivisi.getItems().stream().filter(ap ->
                        ap.getNama().equals(string)).findFirst().orElse(null);
            }
        });
        cbxDivisi.valueProperty().addListener((obs, oldval, newval) -> {
            if(newval != null) {
                //label.setText("NIK : "+newval.getNik());
                cbxDivisi.setStyle(newval.getId());
                tmpDivisi=newval.getId();
                cbxDivisi.setStyle(newval.getId());
                System.out.println("Divisi: " + newval.getNama() + ". tmp_divisi: " + tmpDivisi);
            }
        });

        table=new TabelPosisi();
        table.setItems(new PosisiModify().GetTableItems());
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(table.getSelectionModel().getSelectedItem() != null) {
                    DataPosisi dataPosisi=(DataPosisi)table.getSelectionModel().getSelectedItem();
                    DataPosisi dp=new PosisiModify().GetPosisiById(dataPosisi.getId());
                    tmpPosisi=dp.getId();
                    System.out.println("tmp_posisi: "+tmpPosisi);

                    text_nama.setText(dp.getNama());

                    Object ket=dp.getKet();
                    if (ket==null) ket="";
                    text_ket.setText(ket.toString()+"");

                    DataDivisi dvs=new PosisiModify().GetDivisiById(dp.getId_divisi());
                    if(dvs!=null) {
                        cbxDivisi.getSelectionModel().select(dvs);
                        //cbxDivisi.setStyle(dvs.getId());
                    }else {
                        cbxDivisi.getSelectionModel().select(new DataDivisi("","",""));
                        //cbxDivisi.setStyle("");
                    }
                }
            }
        });
    }

    private void Refresh(){
        table.setItems(new PosisiModify().GetTableItems());
        tmpDivisi="";
        tmpPosisi="";
        text_ket.setText("");
        text_nama.setText("");
        text_nama.requestFocus();
        cbxDivisi.getSelectionModel().select(new DataDivisi("","",""));
        cbxDivisi.getSelectionModel().clearSelection();

    }
}
