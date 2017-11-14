package app.view;

import java.sql.SQLException;
import java.util.Optional;

import app.controller.DivisiModify;
import app.model.DataBarang;
import app.model.DataDivisi;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Divisi {
	private Button button_add,button_del,button_fresh;
	private VBox vbox;
	private HBox hbox;
	private TextField nama,keterangan;
	private TableView table;
	//private ComboBox<DataKaryawan> cbx_manager;
	private Label label,judul,pejabat;
	
	public VBox vDivisi() {
		vbox.setPadding(new Insets(5, 5, 5, 5));
		hbox.setAlignment(Pos.CENTER_LEFT);
		hbox.getChildren().addAll(nama,keterangan,button_add,button_fresh,button_del);
		vbox.getChildren().addAll(judul,hbox,table,label);
		return vbox;
	}

	public Divisi() {
		vbox=new VBox(5);
		//vbox.setSpacing(5);
		vbox.setPadding(new Insets(5, 5, 5, 5));
		vbox.setMaxHeight(500);
		vbox.setMaxWidth(1024);
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.getStyleClass().add("box-color");


		hbox=new HBox(3);
		
		label=new Label("Keterangan :");
		judul=new Label("Data Divisi");judul.getStyleClass().addAll("judul");
		pejabat=new Label("Manager: ");

		nama=new TextField();nama.setPromptText("Nama Divisi...");nama.setMinWidth(250);
		keterangan=new TextField();keterangan.setPromptText("Keterangan...");keterangan.setMinWidth(300);

		//cbx_manager.setPromptText("NIK Manager");
		/*cbx_manager=new ComboBox<>(new UserShow().nikManagers());
		cbx_manager.setConverter(new StringConverter<DataKaryawan>() {
			
			@Override
			public String toString(DataKaryawan object) {				
				return object.getNama();
			}
			
			@Override
			public DataKaryawan fromString(String string) {
				// TODO Auto-generated method stub
				return cbx_manager.getItems().stream().filter(ap -> 
	            ap.getNama().equals(string)).findFirst().orElse(null);
			}
		});
		
		cbx_manager.valueProperty().addListener((obs, oldval, newval) -> {
		    if(newval != null) {
		        System.out.println("Selected Manager: " + newval.getNama() + ". NIK: " + newval.getNik());
		        label.setText("NIK : "+newval.getNik());
		    	cbx_manager.setStyle(newval.getNik());
		    }
		});*/
		
		button_add=new Button();
		button_add.setMinWidth(100);
		button_add.setText("Simpan/Ubah");
		
		button_add.setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent event) {
				//System.out.println("Tombol Divisi......");
				int simpan=0;
				try {
					if(nama.getStyle().trim().length()>0) {
						//update
						simpan=new DivisiModify(nama.getStyle().toString().trim(), nama.getText().toString().trim(), keterangan.getText().toString().trim()).Ubah(nama.getStyle());
					}else {
						//insert
						simpan=new DivisiModify(null, nama.getText().toString().trim(), keterangan.getText().toString().trim()).Simpan();
					}
					if (simpan==1) {
						System.out.println("Tersimpan");
						refresh();
					}else System.out.println("Gagal");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		button_del=new Button("Hapus");
		button_del.setMinWidth(100);
		button_del.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Dialog Konfirmasi");
				alert.setHeaderText("Anda akan menghapus data ini,");
				alert.setContentText("Yakin?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
				    if(nama.getStyle().trim().length()>0) {
				    	try {
				    		int hapus=new DivisiModify(nama.getStyle().toString().trim(), nama.getText().toString().trim(), keterangan.getText().toString().trim()).Hapus();
							refresh();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}					    
				    }
				} else {
				    // ... user chose CANCEL or closed the dialog
				}
			}
		});
		
		button_fresh=new Button("Refresh");
		button_fresh.setMinWidth(50);
		button_fresh.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				refresh();
			}
		});
		
		table=new TableView();
		table.setEditable(false);
		table.setColumnResizePolicy((param) -> true );
		table.getStyleClass().addAll("table");
		
		TableColumn nikCol = new TableColumn("ID");
        TableColumn namaCol = new TableColumn("Nama Divisi");
        TableColumn keteranganCol = new TableColumn("Keterangan");
        
        table.getColumns().addAll(nikCol, namaCol, keteranganCol);
        
        nikCol.setCellValueFactory(new PropertyValueFactory<DataBarang,String>("id"));
        namaCol.setCellValueFactory(new PropertyValueFactory<DataBarang,String>("nama"));
        keteranganCol.setCellValueFactory(new PropertyValueFactory<DataBarang,String>("ket"));

        namaCol.setPrefWidth(500);
        keteranganCol.setPrefWidth(600);
        
        table.setItems(new DivisiModify().GetTableItems());
        
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				if(table.getSelectionModel().getSelectedItem() != null) {
					DataDivisi dd=(DataDivisi) table.getSelectionModel().getSelectedItem();
					nama.setStyle(dd.getId());
					nama.setText(dd.getNama());
					keterangan.setText(dd.getKet());
					//cbx_manager.getSelectionModel().select(dd.getNik_manager());
					//cbx_manager.setValue(value);
				}
			}
		});
	}
	
	private void refresh() {
		nama.setText("");
		nama.setStyle("");
		keterangan.setText("");
		//cbx_manager.getItems().clear();
		label.setText("Keterangan :");
		table.setItems(new DivisiModify().GetTableItems());
		nama.requestFocus();}

}
