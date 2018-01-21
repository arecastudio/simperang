package app.view;

import app.model.DataNota;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by rail on 11/15/17.
 */
public class TabelNota extends TableView {
    public TabelNota(){
        setEditable(false);
        setColumnResizePolicy((param) -> true );
        getStyleClass().addAll("table");

        TableColumn nomorCol = new TableColumn("Nomor Nota");
        TableColumn nomorDPBKolektifCol = new TableColumn("Nomor SPB Rekap");
        TableColumn tanggalCol = new TableColumn("Tanggal");
        TableColumn namaVendorCol = new TableColumn("Vendor");
        TableColumn mailCol = new TableColumn("Stat. Email");

        getColumns().addAll(nomorCol, nomorDPBKolektifCol,namaVendorCol, tanggalCol,mailCol);

        nomorCol.setCellValueFactory(new PropertyValueFactory<DataNota,String>("nomor"));
        nomorDPBKolektifCol.setCellValueFactory(new PropertyValueFactory<DataNota,String>("nomor_dpb_kolektif"));
        tanggalCol.setCellValueFactory(new PropertyValueFactory<DataNota,String>("tgl"));
        namaVendorCol.setCellValueFactory(new PropertyValueFactory<DataNota,String>("nama_vendor"));
        mailCol.setCellValueFactory(new PropertyValueFactory<DataNota,Integer>("mail_send"));


        nomorCol.setPrefWidth(200);
        nomorDPBKolektifCol.setPrefWidth(250);
        mailCol.setPrefWidth(150);
        mailCol.setStyle("-fx-alignment:center;");
        namaVendorCol.setPrefWidth(200);
        tanggalCol.setPrefWidth(150);

        prefHeight(300);
    }
}
