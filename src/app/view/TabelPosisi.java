package app.view;

import app.model.DataPosisi;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by rail on 11/2/17.
 */
public class TabelPosisi extends TableView {
    public TabelPosisi(){
        this.setEditable(false);
        this.setColumnResizePolicy((param) -> true );
        this.getStyleClass().addAll("table");

        TableColumn idCol = new TableColumn("ID");
        TableColumn namaCol = new TableColumn("Posisi / Jabatan");
        TableColumn ketCol = new TableColumn("Keterangan");
        TableColumn divisiCol = new TableColumn("Divisi / Bagian");

        this.getColumns().addAll(idCol,namaCol,ketCol,divisiCol);

        idCol.setCellValueFactory(new PropertyValueFactory<DataPosisi,String>("id"));
        namaCol.setCellValueFactory(new PropertyValueFactory<DataPosisi,String>("nama"));
        ketCol.setCellValueFactory(new PropertyValueFactory<DataPosisi,String>("ket"));
        divisiCol.setCellValueFactory(new PropertyValueFactory<DataPosisi,String>("nama_divisi"));

        namaCol.setPrefWidth(300);
        divisiCol.setPrefWidth(300);
        ketCol.setPrefWidth(300);
    }
}
