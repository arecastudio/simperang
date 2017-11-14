package app.view;

import app.model.DataDPB;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TabelDPB extends TableView {

	public TabelDPB() {
		this.setEditable(false);
		this.setColumnResizePolicy((param) -> true );
		this.getStyleClass().addAll("table");
		
		//nomor,ket,tgl,nama,total_jml,group_minta
		 
        TableColumn nomorCol = new TableColumn("Nomor");
        TableColumn keteranganCol = new TableColumn("Keterangan");        
        TableColumn TanggalCol = new TableColumn("Tanggal");
        TableColumn namaCol = new TableColumn("User");
        TableColumn jmlCol = new TableColumn("Jumlah");
        TableColumn mintaCol = new TableColumn<>("Detail Permintaan");
        
        //this.getColumns().addAll(nomorCol, keteranganCol,statusCol, TanggalCol,divisiCol,cetakCol);
        this.getColumns().addAll(nomorCol, keteranganCol, TanggalCol,namaCol,jmlCol,mintaCol);
        
        nomorCol.setCellValueFactory(new PropertyValueFactory<DataDPB,String>("nomor"));
        keteranganCol.setCellValueFactory(new PropertyValueFactory<DataDPB,String>("ket"));
        TanggalCol.setCellValueFactory(new PropertyValueFactory<DataDPB,String>("tgl"));
        namaCol.setCellValueFactory(new PropertyValueFactory<DataDPB,String>("nama"));
        jmlCol.setCellValueFactory(new PropertyValueFactory<DataDPB,String>("total_jml"));
        mintaCol.setCellValueFactory(new PropertyValueFactory<DataDPB,String>("group_minta"));
                
        
        nomorCol.setPrefWidth(200);
        keteranganCol.setPrefWidth(250);
        mintaCol.setPrefWidth(400);
        jmlCol.setStyle("-fx-alignment:center;");
        prefHeight(300);
        
	}

}
