package app.view;

import app.controller.DBHelper;
import app.controller.GetCurDate;
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

    public ReportDPBVendorPrint(){
        Inits();

        HBox hbox1=new HBox(5);
        hbox1.setAlignment(Pos.CENTER_LEFT);
        hbox1.getChildren().addAll(tglawal,new Label("s/d"),tglakhir);

        HBox hbox2=new HBox(5);
        hbox2.setAlignment(Pos.CENTER);
        hbox2.getChildren().addAll(button_show,button_refresh,button_print,button_del);

        GridPane grid=new GridPane();
        grid.setHgap(5);grid.setVgap(5);

        grid.add(radio1,0,0);
        grid.add(hbox1,1,0);

        grid.add(radio2,0,1);
        grid.add(text_nota,1,1);

        getChildren().addAll(new LabelJudul("Cetak Rekap Permintaan ke Vendor"),new Separator(Orientation.HORIZONTAL),grid,new Separator(Orientation.HORIZONTAL),hbox2,table,new HBox(ket));
    }

    private void Refresh() {
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

        button_del=new Button("Hapus");
        button_del.setPrefWidth(150);

        button_print=new Button("Cetak");
        button_print.setPrefWidth(150);

        button_refresh=new Button("Refresh");
        button_refresh.setPrefWidth(150);

        button_show=new Button("Tampilkan");
        button_show.setPrefWidth(150);

        table=new TableView();
    }
}
