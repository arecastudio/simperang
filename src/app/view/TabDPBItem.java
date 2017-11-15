package app.view;

import java.util.Optional;

import app.GlobalUtility;
import app.Main;
import app.model.DataPermintaan;
import app.model.DataUserLogin;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class TabDPBItem extends Pane {
	private VBox vbox1;
	private TableView table;
	private Boolean isAdmin;
	private DataUserLogin ul;
	private Label label_stat;
	
	public TabDPBItem() {
		init();

		vbox1.setMinWidth(TabDPBItem.this.getWidth());
		vbox1.getChildren().addAll(table,new HBox(label_stat));
		this.getChildren().add(vbox1);
	}

	private void init() {
		ul=GlobalUtility.getUser_logged_in();
		vbox1=new VBox(5);
		//vbox1.setPadding(new Insets(5,5,5,5));
		vbox1.setAlignment(Pos.CENTER);
		
		label_stat=new Label("Keterangan :");

		isAdmin=Boolean.FALSE;
		if(ul.getId_role().equals("1"))isAdmin=Boolean.TRUE;

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
        
        //table.getColumns().addAll(nomorCol, keteranganCol,statusCol, TanggalCol,divisiCol,cetakCol);
        table.getColumns().addAll(nomorCol, keteranganCol,statusCol, TanggalCol,divisiCol);
        
        nomorCol.setCellValueFactory(new PropertyValueFactory<DataPermintaan,String>("nomor"));
        keteranganCol.setCellValueFactory(new PropertyValueFactory<DataPermintaan,String>("alasan"));
        statusCol.setCellValueFactory(new PropertyValueFactory<DataPermintaan,String>("status"));
        TanggalCol.setCellValueFactory(new PropertyValueFactory<DataPermintaan,String>("tgl"));
        divisiCol.setCellValueFactory(new PropertyValueFactory<DataPermintaan,String>("nik"));
        
        cetakCol.setCellValueFactory(new PropertyValueFactory<DataPermintaan,Boolean>("cetak"));
        cetakCol.setCellFactory(CheckBoxTableCell.forTableColumn(cetakCol));
        cetakCol.setEditable(true);
        
        nomorCol.setPrefWidth(180);
        keteranganCol.setPrefWidth(400);
        statusCol.setPrefWidth(180);
        divisiCol.setPrefWidth(200);
        table.setItems(GlobalUtility.getDataPermintaanForKolektif(isAdmin,1));//status 1, dari Divisi sudah di-posting ke SDM
        
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				if(table.getSelectionModel().getSelectedItem()!=null) {
					DataPermintaan data=(DataPermintaan)table.getSelectionModel().getSelectedItem();
					//System.out.println("\n\n\nNomor: "+data.getNomor()+"\nKeterangan: "+data.getAlasan()+"\nStatus: "+data.getStatus()+"\nTanggal:"+data.getTgl()+"\nDivisi:"+data.getNik());

					String value="Keterangan | Nomor: "+data.getNomor()+" | Keterangan: "+data.getAlasan()+" | Status: "+data.getStatus()+" | Tanggal: "+data.getTgl()+" | Divisi: "+data.getNik();
					label_stat.setText(value);

					int ada=0;
					if(GlobalUtility.dataDPB.size()>0) {
						for (DataPermintaan dp : GlobalUtility.dataDPB) {
							if (dp.getNomor().equals(data.getNomor())) ada=1;
							//System.out.println("\n\n\nNomor: "+dp.getNomor()+"\nKeterangan: "+dp.getAlasan()+"\nTanggal:"+dp.getTgl()+"\nDivisi:"+dp.getNik());
						}
					}
					if (ada==0) {						
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Dialog Konfirmasi");
						alert.setHeaderText("Pembuatan Permintaan Barang Kolektif");
						alert.setContentText("Anda akan manambahkan DPB ini \nKe dalam Draft Kolektif, Yakin?");

						Optional<ButtonType> result = alert.showAndWait();
						if (result.get() == ButtonType.OK){					
							GlobalUtility.dataDPB.add(new DataPermintaan(data.getNomor(),data.getAlasan(),data.getTgl(),data.getNik()));					
						} else {
						    // ... user chose CANCEL or closed the dialog
						}
					}
				}
			}
		});
	}
	
	private void refresh() {
		label_stat.setText("Keterangan :");
	}

}
