package app.view;

import app.GlobalUtility;
import app.Main;
import app.controller.DivisiModify;
import app.controller.PermintaanModify;
import app.controller.PosisiModify;
import app.controller.UserModify;
import app.model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class TabPermintaanItemSave extends AnchorPane {
	private TableView table;
	private Button button_update,button_simpan,button_clear,button_del;
	private TextField text_jumlah,text_surat,text_keterangan;
	private Label label,label_nama,label_satuan;
	private VBox vbox;
	private HBox hbox,hbox2,hbox3;
	private Boolean isEdit;
	private ComboBox<DataUsers> cbxManager;
	private ComboBox<DataPosisi> cbxPosisi;
	private ComboBox<DataPosisi> cbxPosisiMgr;
	private String tmp_id_manager="",tmp_id_posisi="",tmp_id_posisi_mgr="",tmp_nik="";
	//nama,nama_posisi,nama_atasan,nama_posisi_atasan
	private String tmp_nama="",tmp_nama_posisi="",tmp_nama_atasan="",tmp_nama_posisi_atasan="";
	private GridPane grid;
	
	public TabPermintaanItemSave(Boolean isEdit) {
		this.isEdit=isEdit;
		hbox=new HBox(5);hbox.setPadding(new Insets(5, 5, 5, 5));hbox.setAlignment(Pos.CENTER_LEFT);
		hbox2=new HBox(5);hbox2.setPadding(new Insets(5, 5, 5, 5));hbox2.setAlignment(Pos.CENTER_LEFT);
		hbox3=new HBox(5);hbox3.setPadding(new Insets(5, 5, 5, 5));hbox3.setAlignment(Pos.CENTER_LEFT);
		vbox=new VBox(5);vbox.setPadding(new Insets(5, 5, 5, 5));

		grid=new GridPane();
		grid.setHgap(10);grid.setVgap(5);

		label=new Label("Jumlah : ");
		label_nama=new Label("Barang");
		label_satuan=new Label("Satuan");

		button_update=new Button("Update");
		button_update.setPrefWidth(130);

		button_del=new Button("Hapus Item");
		button_del.setPrefWidth(130);

		button_simpan=new Button("Posting");
		button_simpan.setPrefWidth(130);

		button_clear=new Button("Reset");
		button_clear.setPrefWidth(130);

		text_jumlah=new TextField();text_jumlah.setPrefWidth(100);
		
		text_surat=new TextField();
		text_surat.setPrefWidth(250);
		text_surat.setPromptText("Nomor Surat Permintaan");
		
		text_keterangan=new TextField();
		text_keterangan.setPrefWidth(400);
		text_keterangan.setPromptText("Perihal...");


		//------------------------------------------------------------
		String id_dvs=GlobalUtility.getUser_logged_in().getId_divisi();

		cbxPosisiMgr=new ComboBox<>(new PosisiModify().GetTableItemsByDivisi(id_dvs));
		cbxPosisiMgr.setConverter(new StringConverter<DataPosisi>() {
			@Override
			public String toString(DataPosisi object) {
				// TODO Auto-generated method stub
				return object.getNama();
			}

			@Override
			public DataPosisi fromString(String string) {
				// TODO Auto-generated method stub
				return cbxPosisiMgr.getItems().stream().filter(ap ->
						ap.getNama().equals(string)).findFirst().orElse(null);
			}
		});

		cbxPosisiMgr.valueProperty().addListener((obs, oldval, newval) -> {
			if(newval != null) {
				cbxPosisiMgr.setStyle(newval.getId());
				tmp_id_posisi_mgr=newval.getId();
				//System.out.println("posisi: " + newval.getNama() + ", id: " + tmp_id_posisi_mgr);
				tmp_nama_posisi_atasan=newval.getNama();
			}
		});
		//------------------------------------------------------------
		cbxPosisi=new ComboBox<>(new PosisiModify().GetTableItemsByDivisi(id_dvs));
		cbxPosisi.setConverter(new StringConverter<DataPosisi>() {
			@Override
			public String toString(DataPosisi object) {
				// TODO Auto-generated method stub
				return object.getNama();
			}

			@Override
			public DataPosisi fromString(String string) {
				// TODO Auto-generated method stub
				return cbxPosisi.getItems().stream().filter(ap ->
						ap.getNama().equals(string)).findFirst().orElse(null);
			}
		});

		cbxPosisi.valueProperty().addListener((obs, oldval, newval) -> {
			if(newval != null) {
				cbxPosisi.setStyle(newval.getId());
				tmp_id_posisi=newval.getId();
				tmp_nama_posisi=newval.getNama();


				System.out.println("posisi: " + tmp_nama_posisi + ", id: " + tmp_id_posisi);
			}
		});
		//------------------------------------------------------------

		cbxManager=new ComboBox<>(new UserModify().GetTableItemsInDivisi(id_dvs));
		cbxManager.setConverter(new StringConverter<DataUsers>() {

			@Override
			public String toString(DataUsers object) {
				return object.getNama();
			}

			@Override
			public DataUsers fromString(String string) {
				// TODO Auto-generated method stub
				return cbxManager.getItems().stream().filter(ap ->
						ap.getNama().equals(string)).findFirst().orElse(null);
			}
		});

		cbxManager.valueProperty().addListener((obs, oldval, newval) -> {
			if(newval != null) {
				//label.setText("NIK : "+newval.getNik());
				cbxManager.setStyle(newval.getNik());
				tmp_id_manager=newval.getNik();
				//System.out.println("manager: " + newval.getNama() + ". nik: " + tmp_id_manager);
				tmp_nama_atasan=newval.getNama();
			}
		});
		
		table=new TableView();
		table.setEditable(false);
		table.setColumnResizePolicy((param) -> true );
		table.getStyleClass().addAll("table");
		 
        TableColumn idCol = new TableColumn("ID");
        TableColumn namaCol = new TableColumn("Nama");
        TableColumn jumlahCol = new TableColumn("Jumlah");
        TableColumn satuanCol = new TableColumn("Satuan");
        TableColumn keteranganCol = new TableColumn("Keterangan");
        
        table.getColumns().addAll(idCol, namaCol,jumlahCol, satuanCol,keteranganCol);
        
        idCol.setCellValueFactory(new PropertyValueFactory<DataBarangDipilih,String>("id"));
        namaCol.setCellValueFactory(new PropertyValueFactory<DataBarangDipilih,String>("nama"));
        jumlahCol.setCellValueFactory(new PropertyValueFactory<DataBarangDipilih,String>("jumlah"));
        satuanCol.setCellValueFactory(new PropertyValueFactory<DataBarangDipilih,String>("satuan"));
        keteranganCol.setCellValueFactory(new PropertyValueFactory<DataBarangDipilih,String>("keterangan"));
        
        namaCol.setPrefWidth(300);
        keteranganCol.setPrefWidth(450);
        
        if(isEdit.equals(Boolean.TRUE)) {
        	table.setItems(GlobalUtility.getBarang_dipilihEdit());        	
        }else {
        	table.setItems(GlobalUtility.getBarang_dipilih());
        }
        
        table.setPrefWidth(Main.primaryStage.getWidth()-9);
        table.setPrefHeight(300);
		//table.setPrefWidth((Double)this.getWidth());
        
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
        	@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {				
				try {
					DataBarangDipilih dbp=(DataBarangDipilih)table.getSelectionModel().getSelectedItem();				
					text_jumlah.setText(dbp.getJumlah());
					label_nama.setText(dbp.getNama()+", ");
					label_nama.setStyle(dbp.getId());
					label_satuan.setText(dbp.getSatuan());
					text_jumlah.setStyle(""+table.getSelectionModel().getSelectedIndex());	
				} catch (Exception e) {
					//System.out.println(e.toString());
				}
			}
		});
        
		button_update.setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent event) {
				if(text_jumlah.getText().toString().trim().length()>0 && Integer.parseInt(text_jumlah.getText().toString().trim())>0 && label_nama.getStyle().trim().length()>0) {
					if(isEdit.equals(Boolean.TRUE)) {
						for(DataBarangDipilih dbp:GlobalUtility.dataBarangDipilihEdit) {
							if(dbp.getId().toString()==label_nama.getStyle().toString()) {
								GlobalUtility.dataBarangDipilihEdit.set(Integer.parseInt(text_jumlah.getStyle()), new DataBarangDipilih(dbp.getId(), dbp.getNama(), text_jumlah.getText().toString().trim(), dbp.getSatuan(), dbp.getKeterangan(),dbp.getHarga()));
								System.out.println(dbp.getNama());
								table.setItems(GlobalUtility.getBarang_dipilihEdit());
							}
						}
					}else {
						for(DataBarangDipilih dbp:GlobalUtility.dataBarangDipilih) {
							if(dbp.getId().toString()==label_nama.getStyle().toString()) {
								GlobalUtility.dataBarangDipilih.set(Integer.parseInt(text_jumlah.getStyle()), new DataBarangDipilih(dbp.getId(), dbp.getNama(), text_jumlah.getText().toString().trim(), dbp.getSatuan(), dbp.getKeterangan(),dbp.getHarga()));
								System.out.println(dbp.getNama());
								table.setItems(GlobalUtility.getBarang_dipilih());
							}
						}
					}
					label.setText("Jumlah : ");
					label_nama.setText("Barang");
					label_satuan.setText("Satuan");
				}				
			}
		});

		button_del.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(text_jumlah.getText().toString().trim().length()>0 && Integer.parseInt(text_jumlah.getText().toString().trim())>0 && label_nama.getStyle().trim().length()>0) {
					if(isEdit.equals(Boolean.TRUE)) {
						for(DataBarangDipilih dbp:GlobalUtility.dataBarangDipilihEdit) {
							if(dbp.getId().toString()==label_nama.getStyle().toString()) {
								GlobalUtility.dataBarangDipilihEdit.remove(dbp);
								//System.out.println(dbp.getNama());
								refresh();
							}
						}
					}else {
						for(DataBarangDipilih dbp:GlobalUtility.dataBarangDipilih) {
							if(dbp.getId().toString()==label_nama.getStyle().toString()) {
								GlobalUtility.dataBarangDipilih.remove(dbp);
								//System.out.println(dbp.getNama());
								refresh();
							}
						}
					}
				}
			}
		});
		
		button_simpan.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {				
				ObservableList<DataBarangDipilih> diPilih=FXCollections.observableArrayList();
				if(isEdit.equals(Boolean.FALSE)) {
					diPilih=GlobalUtility.getBarang_dipilih();
				}else {
					diPilih=GlobalUtility.getBarang_dipilihEdit();
				}
				
				if(text_surat.getText().toString().trim().length()>0 && text_keterangan.getText().toString().trim().length()>0 && diPilih.size()>0) {
					DataPermintaan dp=new DataPermintaan();
					//dp.setNik(GlobalUtility.getUser_logged_in().getNik());
					dp.setNik(tmp_nik);
					dp.setAlasan(text_keterangan.getText().trim());
					dp.setNomor(text_surat.getText().trim());

					dp.setId_posisi(tmp_id_posisi);
					dp.setNik_atasan(tmp_id_manager);
					dp.setId_posisi_atasan(tmp_id_posisi_mgr);

					//nama,nama_posisi,nama_atasan,nama_posisi_atasan
					tmp_nama=GlobalUtility.getUser_logged_in().getNama();
					dp.setNama(tmp_nama);

					dp.setNama_posisi(tmp_nama_posisi);
					dp.setNama_atasan(tmp_nama_atasan);
					dp.setNama_posisi_atasan(tmp_nama_posisi_atasan);

					DataDivisi dd=new DivisiModify().GetDataDivisiByPosisi(tmp_id_posisi);
					dp.setId_divisi(dd.getId());
					dp.setNama_divisi(dd.getNama());
					
					int i=new PermintaanModify().SimpanAll(dp, diPilih, isEdit);
					if(i>0){
						GlobalUtility.dataBarangDipilih.clear();
						GlobalUtility.dataBarangDipilihEdit.clear();
						refresh();
						//Toast.makeText(Main.primaryStage, "Permintaan berhasil di-posting !", 650, 150, 150,Color.CHARTREUSE);
						//Main.borderPane.setCenter(new Label("Berhasil menyimpan permintaan."));
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Dialog Informasi");
						alert.setHeaderText("Simpan Data.");
						alert.setContentText("Proses telah selesai.");
						alert.showAndWait();
						Main.borderPane.setCenter(new EditPermintaan());
					}
					
				}else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Dialog Error");
					alert.setHeaderText("Data belum lengkap");
					alert.setContentText("Isi Nomor Surat, Keterangan \nDan Pilih Item terlebih dahulu !");
					alert.show();
				}
				
				/*try {
					int simpan=new PermintaanModify().permintaan(dp,isEdit);
					if(simpan>0) {
						//System.out.println("tasimpan");
						if(isEdit.equals(Boolean.TRUE)) {
						//Boolean b=
							new PermintaanModify().permintaan_d(GlobalUtility.getBarang_dipilihEdit(),isEdit);
						}else {
							new PermintaanModify().permintaan_d(GlobalUtility.getBarang_dipilih(),isEdit);
							GlobalUtility.dataBarangDipilih.clear();
						}
						//if (b.equals(Boolean.TRUE)) {
							//GlobalUtility.dataBarangDipilih.removeAll();
							//GlobalUtility.edit_nomor_surat="";
							//GlobalUtility.edit_keterangan="";
							//table.getItems().clear();
							refresh();
							Toast.makeText(Main.primaryStage, "Permintaan berhasil di-posting !", 650, 150, 150,Color.CHARTREUSE);
							Main.borderPane.setCenter(new Label("Berhasil menyimpan permintaan."));
						//}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}*/				
			}
			
		});
		
		button_clear.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(isEdit.equals(Boolean.TRUE)) {
					GlobalUtility.dataBarangDipilihEdit.clear();
				}else {
					GlobalUtility.dataBarangDipilih.clear();
				}
				refresh();
			}
		});


		//grid.setPadding(new Insets(5,5,5,5));
/*
		grid.add(new Label("No. Surat"),0,0);
		grid.add(text_surat,1,0);

		grid.add(new Label("Keterangan"),0,1);
		grid.add(text_keterangan,1,1);
*/


		grid.add(new Label("Dibuat Oleh"),0,0);
		grid.add(new Button(GlobalUtility.getUser_logged_in().getNama()),1,0);
		grid.add(new Label("Jabatan"),2,0);
		grid.add(cbxPosisi,3,0);

		grid.add(new Label("Mengetahui"),0,1);
		grid.add(cbxManager,1,1);
		grid.add(new Label("Jabatan"),2,1);
		grid.add(cbxPosisiMgr,3,1);
/*
		grid.add(button_simpan,0,4);
		grid.add(button_clear,0,5);*/

        
        hbox.getChildren().addAll(label_nama,label,text_jumlah,label_satuan,button_update,new Separator(Orientation.VERTICAL),button_del);
        hbox2.getChildren().addAll(text_surat,new Separator(Orientation.VERTICAL),text_keterangan,new Separator(Orientation.VERTICAL),button_simpan,button_clear);
        //hbox3.getChildren().addAll(new Label("Mengetahui"),cbxManager,new Label("Jabatan"),cbxPosisiMgr,new Separator(Orientation.VERTICAL),new Label("Dibuat Oleh"));
        vbox.getChildren().addAll(hbox2,grid,table,hbox);
        
        this.getChildren().add(vbox);

		tmp_nik=GlobalUtility.getUser_logged_in().getNik();
		tmp_nama=GlobalUtility.getUser_logged_in().getNama();
		System.out.println("Nama User: "+tmp_nama);
		if(isEdit.equals(Boolean.TRUE)) {
			text_surat.setText(GlobalUtility.edit_nomor_surat);
			text_keterangan.setText(GlobalUtility.edit_keterangan);
			text_surat.setEditable(false);
			text_keterangan.setEditable(false);

			grid.setVisible(false);
			vbox.getChildren().remove(grid);

			String[] userSurat=new PermintaanModify().GetUserOnPermintaan(GlobalUtility.edit_nomor_surat);
			tmp_nik=userSurat[0];
			tmp_id_posisi=userSurat[1];
			tmp_id_manager=userSurat[2];
			tmp_id_posisi_mgr=userSurat[3];
			//taruh juga di refresh

			/*System.out.println("-----------------");
			System.out.println(tmp_nik);
			System.out.println(tmp_id_posisi);
			System.out.println(tmp_id_manager);
			System.out.println(tmp_id_posisi_mgr);*/
		}
	}
	
	private void refresh(){
		if(isEdit.equals(Boolean.TRUE)) {
        	table.setItems(GlobalUtility.getBarang_dipilihEdit());        	
        }else {
        	table.setItems(GlobalUtility.getBarang_dipilih());
        }
		label.setText("Jumlah : ");
		label_nama.setText("Barang");
		label_satuan.setText("Satuan");
		text_jumlah.setText("");
		text_surat.setText("");
		text_keterangan.setText("");

		tmp_nama="";
		tmp_nama_posisi="";
		tmp_nama_atasan="";
		tmp_nama_posisi_atasan="";
		
		if(isEdit.equals(Boolean.TRUE)) {
			text_surat.setText(GlobalUtility.edit_nomor_surat);
			text_keterangan.setText(GlobalUtility.edit_keterangan);
			text_surat.setEditable(false);
			text_keterangan.setEditable(false);

			grid.setVisible(false);
			vbox.getChildren().remove(grid);
			String[] userSurat=new PermintaanModify().GetUserOnPermintaan(GlobalUtility.edit_nomor_surat);
			tmp_nik=userSurat[0];
			tmp_id_posisi=userSurat[1];
			tmp_id_manager=userSurat[2];
			tmp_id_posisi_mgr=userSurat[3];
		}
	}

}
