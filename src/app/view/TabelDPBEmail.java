package app.view;

import app.model.DataDPB;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by rail on 11/5/17.
 */
public class TabelDPBEmail extends TableView {

    public TabelDPBEmail(){
        setEditable(false);
        setColumnResizePolicy((param) -> true );
        getStyleClass().addAll("table");

        TableColumn nomorCol = new TableColumn("Nomor");
        TableColumn keteranganCol = new TableColumn("Keterangan");
        TableColumn tanggalCol = new TableColumn("Tanggal");
        TableColumn namaCol = new TableColumn("User");
        TableColumn jmlCol = new TableColumn("Jumlah");
        TableColumn statCol = new TableColumn("Status");
        TableColumn mintaCol = new TableColumn<>("Detail Permintaan");

        getColumns().addAll(nomorCol,keteranganCol,tanggalCol,namaCol,statCol,jmlCol,mintaCol);



        nomorCol.setCellValueFactory(new PropertyValueFactory<DataDPB,String>("nomor"));
        keteranganCol.setCellValueFactory(new PropertyValueFactory<DataDPB,String>("ket"));
        tanggalCol.setCellValueFactory(new PropertyValueFactory<DataDPB,String>("tgl"));
        namaCol.setCellValueFactory(new PropertyValueFactory<DataDPB,String>("nama"));
        jmlCol.setCellValueFactory(new PropertyValueFactory<DataDPB,String>("total_jml"));
        mintaCol.setCellValueFactory(new PropertyValueFactory<DataDPB,String>("group_minta"));

        statCol.setCellValueFactory(new PropertyValueFactory<DataDPB,String>("mail_send"));


        nomorCol.setPrefWidth(200);
        keteranganCol.setPrefWidth(250);
        mintaCol.setPrefWidth(400);
        jmlCol.setStyle("-fx-alignment:center;");
        prefHeight(300);

        tanggalCol.setPrefWidth(200);
        namaCol.setPrefWidth(200);

        statCol.setPrefWidth(200);
    }
}
