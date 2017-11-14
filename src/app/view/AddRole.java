package app.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;



public class AddRole extends VBox {
    private HBox hbox1;
    private TextField text_nama;
    private Button button_save;
    private TableView table;

    public AddRole(){
        Inits();

        hbox1.getChildren().addAll(new Label("Nama Hak Akses"),text_nama,button_save);
        getChildren().addAll(new LabelJudul("Tambah Hak User"),hbox1,table);
    }

    private void Inits(){
        setSpacing(5);
        setPadding(new Insets(5,5,5,5));

        hbox1=new HBox(5);
        hbox1.setAlignment(Pos.CENTER_LEFT);

        text_nama=new TextField();
        text_nama.setPrefWidth(350);

        button_save=new Button("Simpan");
        button_save.setPrefWidth(100);

        table=new TableView();

        table.setEditable(false);
        table.setColumnResizePolicy((param) -> true );
        table.getStyleClass().addAll("table");

        TableColumn nomorCol = new TableColumn("Nomor");
        TableColumn keteranganCol = new TableColumn("Keterangan");
        TableColumn statusCol = new TableColumn("Status");
        TableColumn TanggalCol = new TableColumn("Tanggal");
        TableColumn divisiCol = new TableColumn("Divisi");
        //TableColumn cetakCol = new TableColumn("Cetak");
        TableColumn cetakCol = new TableColumn<>("Cetak");

        table.getColumns().addAll(nomorCol, keteranganCol,statusCol, TanggalCol,divisiCol);
    }
}
