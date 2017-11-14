package app.view;

import app.model.DataPermintaan;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

public class TabelPermintaan extends TableView {

	public TabelPermintaan() {
		this.setEditable(false);
		this.setColumnResizePolicy((param) -> true );
		this.getStyleClass().addAll("table");
		 
        TableColumn nomorCol = new TableColumn("Nomor");
        TableColumn keteranganCol = new TableColumn("Keterangan");
        TableColumn statusCol = new TableColumn("Status");
        TableColumn TanggalCol = new TableColumn("Tanggal");
        TableColumn divisiCol = new TableColumn("Divisi");
        //TableColumn cetakCol = new TableColumn("Cetak");
        TableColumn cetakCol = new TableColumn<>("Cetak");
        
        //this.getColumns().addAll(nomorCol, keteranganCol,statusCol, TanggalCol,divisiCol,cetakCol);
        this.getColumns().addAll(nomorCol, keteranganCol,statusCol, TanggalCol,divisiCol);
        
        nomorCol.setCellValueFactory(new PropertyValueFactory<DataPermintaan,String>("nomor"));
        keteranganCol.setCellValueFactory(new PropertyValueFactory<DataPermintaan,String>("alasan"));
        statusCol.setCellValueFactory(new PropertyValueFactory<DataPermintaan,String>("status"));
        TanggalCol.setCellValueFactory(new PropertyValueFactory<DataPermintaan,String>("tgl"));
        divisiCol.setCellValueFactory(new PropertyValueFactory<DataPermintaan,String>("nik"));
        
        cetakCol.setCellValueFactory(new PropertyValueFactory<DataPermintaan,Boolean>("cetak"));
        cetakCol.setCellFactory(CheckBoxTableCell.forTableColumn(cetakCol));
        cetakCol.setEditable(true);
        
        nomorCol.setPrefWidth(200);
        keteranganCol.setPrefWidth(450);
        statusCol.setPrefWidth(300);
        divisiCol.setPrefWidth(200);
        TanggalCol.setPrefWidth(200);

        prefHeight(300);
	}

}
