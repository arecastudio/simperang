package app.view;

import java.util.Optional;

import app.GlobalUtility;
import app.controller.ReviewPermintaanModify;
import app.model.DataPermintaan;
import app.model.DataUserLogin;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ReviewPermintaan extends VBox {
	private Label label_status;
	private TableView table;
	private Boolean isAdmin;
	private DataUserLogin ul;
	private Button button_app,button_refresh,button_detail,button_unapp;
	private Separator hsep1;
	private final String deskripsi="Modul ini menampilkan Daftar Permintaan Barang dari masing-masing Divisi dengan status \nyang dapat di-approve oleh User yang berwenang. Permintaan yang sudah di-kirim ke Vendor \ndalam bentuk Kolektif tidak lagi muncul pada modul ini.";
	
	private ReviewPermintaanModify rpm;
	private DataPermintaan dp;
	private String tmpNomor;
	
	public ReviewPermintaan() {
		init();

		setSpacing(5);
		setPadding(new Insets(5, 5, 5, 5));
		setMaxHeight(500);
		setMaxWidth(1024);
		setAlignment(Pos.TOP_CENTER);
		getStyleClass().add("box-color");
		
		HBox hbox1=new HBox(5);hbox1.setSpacing(5);
		hbox1.getChildren().addAll(button_app,button_unapp,new Label("               "),button_detail,button_refresh);
		

		this.getChildren().addAll(new LabelJudul("Review Permintaan"),new Separator(Orientation.HORIZONTAL),new HBox(new Label(deskripsi)),table,new HBox(label_status),hbox1);
	}

	private void init() {
		ul=GlobalUtility.getUser_logged_in();

		label_status=new Label("Keterangan : ");
		//table=new TableView();
		button_app=new Button("Approve");button_app.setStyle("-fx-min-width:100px;");
		button_unapp=new Button("Unapprove");button_app.setStyle("-fx-min-width:100px;");
		button_unapp.setDisable(true);
		
		button_refresh=new Button("Refresh");button_refresh.setStyle("-fx-min-width:100px;");
		button_detail=new Button("Detail");button_detail.setStyle("-fx-min-width:100px;");
		button_app.setDisable(true);
		button_detail.setDisable(true);
		hsep1=new Separator(Orientation.VERTICAL);
		hsep1.setValignment(VPos.CENTER);
		//hsep1.setStyle("-fx-background-color:#000;");
		hsep1.setOpacity(1);
		hsep1.setPrefWidth(Double.MAX_VALUE);
		
		this.tmpNomor=new String("");
		
		isAdmin=Boolean.FALSE;
		if(ul.getId_role().equals("1"))isAdmin=Boolean.TRUE;

		table=new TabelPermintaan();
        table.setItems(GlobalUtility.getAllDataPermintaan(isAdmin));
        table.setPrefHeight(330);
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				if(table.getSelectionModel().getSelectedItem()!=null) {
					DataPermintaan data=(DataPermintaan)table.getSelectionModel().getSelectedItem();
					tmpNomor=data.getNomor();
					dp=new ReviewPermintaanModify().getDPbyNomor(data.getNomor());					
					String value=null;
					int i=Integer.parseInt(dp.getStatus());
					value="Keterangan || Nomor Surat: "+data.getNomor()+" || Status: "+data.getStatus();
					//++++++++++++++Mode+Verifikasi++++++++++++++
					/*status 2->teruskan ke vendor
					 *status 3->barang diterima */
					if(Integer.parseInt(dp.getStatus()) < 1) {
						value+="\nApprove Permintaan ini ke Status ["+GlobalUtility.getStatusPosting((++i)+"")+"]?";
						button_app.setDisable(false);
						button_unapp.setDisable(true);
					}else {
						button_app.setDisable(true);
						button_unapp.setDisable(false);
					}
					//++++++++++++++---------------++++++++++++++
					label_status.setText(value);
					label_status.setStyle("-fx-text-fill:#006;");
					button_detail.setDisable(false);
				}				
			}
		});
        
        button_detail.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				if(tmpNomor!=""){
					new DetailPermintaan(tmpNomor).Tampilkan();
				}
			}
		});
        
        button_refresh.setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent event) {
				refresh();				
			}
		});
        
        button_app.setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent event) {				
				if(tmpNomor!="") {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Dialog Konfirmasi");
					alert.setHeaderText("Approve Status Daftar Permintaan Barang");
					alert.setContentText("Anda akan mengubah Status untuk Permintaan ini,\nYakin?");

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK){					
						dp=new ReviewPermintaanModify().getDPbyNomor(tmpNomor);
						int i=Integer.parseInt(dp.getStatus());
						int app=new ReviewPermintaanModify().setApprove(tmpNomor,++i);
						if(app==1){
							refresh();
						}						
					} else {
					    // ... user chose CANCEL or closed the dialog
					}
				}
			}
		});
        
        button_unapp.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Dialog Konfirmasi");
				alert.setHeaderText("Unapprove Status Daftar Permintaan Barang");
				alert.setContentText("Anda akan mengubah Status untuk Permintaan ini,\nYakin?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){					
					dp=new ReviewPermintaanModify().getDPbyNomor(tmpNomor);
					int i=Integer.parseInt(dp.getStatus());
					int app=new ReviewPermintaanModify().setApprove(tmpNomor,--i);
					if(app==1){
						refresh();
					}						
				} else {
				    // ... user chose CANCEL or closed the dialog
				}
			}
		});
	}
	
	private void refresh() {
		this.label_status.setText("Keterangan :");
		this.label_status.setStyle("-fx-text-fill:#000;");
		table.setItems(GlobalUtility.getAllDataPermintaan(isAdmin));
		this.tmpNomor="";
		button_app.setDisable(true);
		button_unapp.setDisable(true);
		button_detail.setDisable(true);
	}
}
