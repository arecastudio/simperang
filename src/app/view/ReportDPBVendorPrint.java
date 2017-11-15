package app.view;

import app.controller.DBHelper;
import app.controller.GetCurDate;
import app.controller.NotaModify;
import app.model.DataNota;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Created by rail on 11/15/17.
 */
public class ReportDPBVendorPrint extends VBox {
    private Connection conn;
    private DBHelper helper;
    private TableView table;
    private final DatePicker tglawal = new DatePicker();
    private final DatePicker tglakhir = new DatePicker();
    private TextField text_nota;
    private Label ket;
    private ToggleGroup group;
    private RadioButton radio1,radio2;
    private Button button_show,button_refresh,button_print,button_del;

    private final String SURAT_PERMINTAAN="/app/reports/surat-permintaan.jasper";
    private final String SURAT_PERMINTAAN_LAMPIRAN="/app/reports/surat-permintaan-lampiran.jasper";

    private String tmp_nomor="";

    public ReportDPBVendorPrint(){
        Inits();

        HBox hbox1=new HBox(5);
        hbox1.setAlignment(Pos.CENTER_LEFT);
        hbox1.getChildren().addAll(tglawal,new Label("s/d"),tglakhir);

        HBox hbox2=new HBox(5);
        hbox2.setAlignment(Pos.CENTER);
        hbox2.getChildren().addAll(button_show,button_print,button_refresh,button_del);

        GridPane grid=new GridPane();
        grid.setHgap(5);grid.setVgap(5);

        grid.add(radio1,0,0);
        grid.add(hbox1,1,0);

        grid.add(radio2,0,1);
        grid.add(text_nota,1,1);

        getChildren().addAll(new LabelJudul("Cetak Rekap Permintaan ke Vendor"),new Separator(Orientation.HORIZONTAL),grid,new Separator(Orientation.HORIZONTAL),hbox2,table,new HBox(ket));
    }

    private void Refresh() {
        table.getItems().clear();
        text_nota.setText("");
        tmp_nomor="";
        ket.setText("Keterangan :");
    }

    private void Inits() {
        setSpacing(5);
        setPadding(new Insets(5,5,5,5));
        setAlignment(Pos.TOP_CENTER);
        setMaxHeight(570);
        setMaxWidth(1024);
        getStyleClass().add("box-color");

        ket=new Label("Keterangan :");

        text_nota=new TextField();
        text_nota.setMaxWidth(200);

        tglawal.setEditable(false);
        tglawal.setValue(new GetCurDate().getLocalDate());
        tglawal.setOnAction(new EventHandler() {
            public void handle(Event t) {
                LocalDate date = tglawal.getValue();
                //System.err.println("Tgl Awal: " + date);
            }
        });

        tglakhir.setEditable(false);
        tglakhir.setValue(new GetCurDate().getLocalDate());
        tglakhir.setOnAction(new EventHandler() {
            public void handle(Event t) {
                LocalDate date = tglakhir.getValue();
                //System.err.println("Tgl Akhir: " + date);
            }
        });


        group=new ToggleGroup();
        radio1=new RadioButton("Tgl. Surat");
        radio1.setToggleGroup(group);
        radio1.setSelected(true);
        radio2=new RadioButton("No. Nota");
        radio2.setToggleGroup(group);

        button_del=new Button("Hapus Nota");
        button_del.setPrefWidth(150);
        button_del.setOnAction(e->{
            if (tmp_nomor!=""){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Dialog Konfirmasi");
                alert.setHeaderText("Anda akan menghapus Nota ini,");
                alert.setContentText("Yakin?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    int i=new NotaModify().HapusNota(tmp_nomor);
                    if (i>0){
                        Refresh();
                        ket.setText("Keterangan : Berhasil menghapus Nota.");
                    }else {
                        ket.setText("Keterangan : Gagal menghapus Nota");
                    }
                }
            }
        });

        button_print=new Button("Cetak");
        button_print.setPrefWidth(150);

        button_refresh=new Button("Refresh");
        button_refresh.setPrefWidth(150);
        button_refresh.setOnAction(event -> {
            Refresh();
        });

        button_show=new Button("Tampilkan");
        button_show.setPrefWidth(150);
        button_show.setOnAction(event -> {
            if (radio2.isSelected()){
                table.setItems(new NotaModify().GetTableItem(text_nota.getText().trim(),null,null));
            }else{
                table.setItems(new NotaModify().GetTableItem(null,tglawal.getValue().toString(),tglakhir.getValue().toString()));
            }
        });

        table=new TabelNota();
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (table.getSelectionModel().getSelectedItem()!=null){
                    DataNota nota=(DataNota) table.getSelectionModel().getSelectedItem();
                    tmp_nomor=nota.getNomor();
                    ket.setText("Keterangan || Data dipilih: "+ nota.getNomor());
                }
            }
        });
    }
}
