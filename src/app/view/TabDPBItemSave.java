package app.view;

import java.util.Optional;

import app.GlobalUtility;
import app.Main;
import app.controller.DPBKolektifModify;
import app.controller.EmailController;
import app.controller.PosisiModify;
import app.controller.UserModify;
import app.model.DataPermintaan;
import app.model.DataPosisi;
import app.model.DataUserLogin;
import app.model.DataUsers;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class TabDPBItemSave extends Pane{
	private VBox vbox1;
	private HBox hbox1,hbox10;
	private TableView table;
	private Boolean isAdmin;
	private DataUserLogin ul;
	private Label label_stat;
	private Button button_clear,button_save;
	private TextField text_nomor,text_ket;
	private Boolean b=Boolean.FALSE;
	private CheckBox cekEmail;

	private boolean isSendMail;
	private Alert alert;

	private ComboBox<DataPosisi> cbxPosisiMgr;
	private ComboBox<DataUsers> cbxManager;
	private String tmp_id_manager="",tmp_nama_manager="",tmp_id_posisi_manager="",tmp_nama_posisi_manager="";

	private ProgressBar progressBar;
	

	public TabDPBItemSave() {
		init();

		GridPane grid=new GridPane();
		grid.setVgap(5);
		grid.setHgap(5);

		grid.add(new Label("No. Surat Permintaan"),0,0);
		grid.add(text_nomor,1,0);

		grid.add(new Label("Perihal"),0,1);
		grid.add(text_ket,1,1);

		HBox hbox0=new HBox(cbxManager,new Label("Jabatan :"),cbxPosisiMgr);
		hbox0.setAlignment(Pos.CENTER_LEFT);hbox0.setSpacing(5);
		grid.add(new Label("Penanggung jawab (SDM)"),0,2);
		grid.add(hbox0,1,2);

		
		hbox1.getChildren().addAll(button_clear,button_save,new Separator(Orientation.VERTICAL),cekEmail);

		hbox10.getChildren().addAll(label_stat);
		
		vbox1.setMinWidth(TabDPBItemSave.this.getWidth());
		vbox1.getChildren().addAll(grid,new Separator(Orientation.HORIZONTAL),hbox1,table,hbox10);
		this.getChildren().add(vbox1);
	}

	private void init() {

		ul=GlobalUtility.getUser_logged_in();
		table=new TableView();
		vbox1=new VBox(5);
		//vbox1.setPadding(new Insets(5,5,5,5));
		
		hbox1=new HBox(5);
		hbox1.setPadding(new Insets(5,5,5,5));
		hbox1.setAlignment(Pos.CENTER);

		hbox10=new HBox(5);
		progressBar=new ProgressBar(0);

		isSendMail=false;
		cekEmail=new CheckBox("Kirim ke e-mail user");
		cekEmail.setSelected(true);
		cekEmail.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				isSendMail=false;
				if (cekEmail.isSelected()) isSendMail=true;
				System.out.println("Kirim email? "+ isSendMail);
			}
		});
		if (cekEmail.isSelected()) isSendMail=true;
		
		label_stat=new Label("Keterangan :");

		isAdmin=Boolean.FALSE;
		if(ul.getId_role().equals("1"))isAdmin=Boolean.TRUE;
		
		button_clear=new Button("Refesh");
		button_clear.setPrefWidth(100);
		
		button_save=new Button("Simpan");
		button_save.setPrefWidth(100);
		
		text_nomor=new TextField();
		text_nomor.setMaxWidth(220);
		text_nomor.setPromptText("Nomor Surat Rekap Permintaan");
		
		text_ket=new TextField();
		text_ket.setMaxWidth(350);
		text_ket.setPromptText("Perihal...");

		//------------------------------------------------------------
		cbxPosisiMgr=new ComboBox<>(new PosisiModify().GetTableItems());
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
				tmp_id_posisi_manager=newval.getId();
				tmp_nama_posisi_manager=newval.getNama();
				System.out.println("posisi: " + tmp_nama_posisi_manager + ", id: " + tmp_id_posisi_manager);
			}
		});
		//------------------------------------------------------------
		cbxManager=new ComboBox<>(new UserModify().GetTableItems());
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
				tmp_nama_manager=newval.getNama();
				System.out.println("manager: " + tmp_nama_manager + ". nik: " + tmp_id_manager);
			}
		});
		//------------------------------------------------------------

		table.setPrefHeight(300);
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
        
        nomorCol.setPrefWidth(200);
        keteranganCol.setPrefWidth(450);
        TanggalCol.setPrefWidth(200);
        divisiCol.setPrefWidth(200);
        table.setItems(GlobalUtility.getDataDPB());//status 1, dari Divisi sudah di-posting ke SDM
        
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				if(table.getSelectionModel().getSelectedItem()!=null) {
					/*DataPermintaan data=(DataPermintaan)table.getSelectionModel().getSelectedItem();
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
					}*/
				}
			}
		});
        
        button_clear.setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent event) {
				GlobalUtility.dataDPB.clear();
				refresh();
			}
		});
        
        button_save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String nomor=text_nomor.getText().toString().trim();
				String ket=text_ket.getText().toString().trim();
				String nik=GlobalUtility.getUser_logged_in().getNik();
				Integer i=(Integer) GlobalUtility.dataDPB.size();
				if(nomor.length()>0 && ket.length()>0 && i>0) {
					alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Dialog Konfirmasi");
					alert.setHeaderText("Pembuatan Daftar Permintaan Kolektif");
					alert.setContentText("Nomor Surat: ["+nomor+"]\nLanjut proses Simpan?");

					hbox10.getChildren().clear();
					hbox10.getChildren().addAll(progressBar,new Separator(Orientation.VERTICAL),label_stat);
					progressBar.setProgress(-1.0);
					label_stat.setText("Keterangan: Tunggu hingga proses selesai...");
					
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK){
						int simpan=new DPBKolektifModify().Simpan(nomor, ket, nik, GlobalUtility.getDataDPB());
						/*if(simpan>0){
							GlobalUtility.dataDPB.clear();
							refresh();
							Main.borderPane.setCenter(new Label("Berhasil membuat Permintaan Kolektif untuk diteruskan ke Vendor"));
							if(GlobalUtility.getInetStat()==true) {
								new EmailController().send();//kirim email
							}
							//Main.borderPane.setCenter(new DPBKolektif());							
						}*/
						if(simpan>0) {
							GlobalUtility.dataDPB.clear();							
							//pi.setProgress(0.9);
							
							Task<Object> task=new Task<Object>() {
								@Override
								protected Object call() throws Exception {
									if (cekEmail.isSelected()){
										if(GlobalUtility.getInetStat()==true) {
											b = new EmailController().justSend(nomor);//kirim email
										}
									}
									return null;
								}};
							task.setOnSucceeded(e->{								
								new DPBKolektifModify().UpdateOnMailSend(nomor);
								//--------------------------
								Platform.runLater(()->{
									alert = new Alert(AlertType.INFORMATION);
									alert.setTitle("Dialog Informasi");
									alert.setHeaderText("Pembuatan Daftar Permintaan Kolektif");
									if(b.equals(Boolean.TRUE)) {
										alert.setContentText("Berhasil simpan data dan kirim mail notifikasi.");
										//new DPBKolektifModify().UpdateOnMailSend(nomor);
									}else {
										alert.setContentText("Berhasil simpan data.");
									}
									alert.showAndWait();
									refresh();
									Main.borderPane.setCenter(new DPBKolektif());
								});
							});

							Thread thread=new Thread(task);
							thread.setDaemon(true);
							thread.start();
						}
					}
					//klir dpb & refresh
				}
			}
		});
	}
	
	private void refresh() {
		table.setItems(GlobalUtility.dataDPB);
		text_nomor.setText("");
		text_ket.setText("");

		hbox10.getChildren().clear();
		hbox10.getChildren().addAll(label_stat);
		label_stat.setText("Keterangan:");
	}

}
