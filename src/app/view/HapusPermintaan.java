package app.view;

import java.util.Optional;

import app.GlobalUtility;
import app.controller.PermintaanSimpan;
import app.model.DataPermintaan;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HapusPermintaan extends VBox {
	private Boolean isAdmin;	
	private final TableView table=new TabelPermintaan();
	private Label ket;
	private HBox hbox1;
	private Button button_del;	
	private String tmpNomor="";
	private final String desc="Modul ini hanya menampilkan Daftar Permintaan yang belum di-kirim ke SDM";

	public HapusPermintaan() {
		init();
		getChildren().addAll(new LabelJudul("Hapus Permintaan"),new Label(desc),table,ket,button_del);
	}

	private void init() {
		setSpacing(5);
		setPadding(new Insets(5, 5, 5, 5));
		setMaxHeight(500);
		setAlignment(Pos.TOP_CENTER);
		getStyleClass().add("box-color");
		
		isAdmin=Boolean.FALSE;
		if(GlobalUtility.getUser_logged_in().getId_role().equals("1"))isAdmin=Boolean.TRUE;
		
		ket=new Label("Keterangan :");		
		
		//table=new TabelPermintaan();
        table.setItems(GlobalUtility.getAllDataPermintaan(isAdmin));
        
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				if(table.getSelectionModel().getSelectedItem()!=null) {
					DataPermintaan data=(DataPermintaan)table.getSelectionModel().getSelectedItem();
					tmpNomor=data.getNomor();
					String value="Keterangan || Nomor Surat: "+data.getNomor()+" || Status: "+data.getStatus();					
					ket.setText(value);
				}				
			}
		});
        
        button_del=new Button("Hapus");
		button_del.setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent event) {
				if(tmpNomor!="") {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Dialog Konfirmasi");
					alert.setHeaderText("Hapus Permintaan:"+tmpNomor);
					alert.setContentText("Anda akan menghapus Data Permintaan ini,\nYakin?");

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK){
						int hapus=new PermintaanSimpan().HapusPermintaan(tmpNomor);
						if(hapus==1){
							refresh();
						}
					} else {
					    // ... user chose CANCEL or closed the dialog
					}
				}				
			}
		});
        
	}
	
	private void refresh() {
		table.setItems(GlobalUtility.getAllDataPermintaan(isAdmin));
		tmpNomor="";
		ket.setText("Keterangan :");
	}
}
