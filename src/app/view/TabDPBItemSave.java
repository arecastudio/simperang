package app.view;

import java.util.Optional;

import app.GlobalUtility;
import app.Main;
import app.controller.DPBKolektifModify;
import app.controller.EmailController;
import app.model.DataPermintaan;
import app.model.DataUserLogin;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class TabDPBItemSave extends Pane{
	private VBox vbox1;
	private HBox hbox1,hbox2;
	private TableView table;
	private Boolean isAdmin;
	private DataUserLogin ul;
	private Label label_stat,info;
	private Button button_clear,button_save;
	private TextField text_nomor,text_ket;
	private Boolean b=Boolean.FALSE;
	private CheckBox cekEmail;
	private ImageView imgView;
	private final String strKet="Keterangan : Dengan menekan tombol [Simpan], anda telah setuju untuk membuat sebuah Permintaan Kolektif \nSetiap Permintaan dari Divisi yang termuat di dalamnya akan berubah status menjadi [Diteruskan ke Vendor].";
	
	private ProgressBar pb;
	private ProgressIndicator pi;
	private boolean isSendMail;
	

	public TabDPBItemSave() {
		init();
		
		pb = new ProgressBar(0.6);
		pi = new ProgressIndicator(0.6);
		
		hbox1.getChildren().addAll(text_nomor,text_ket,button_save,new Separator(Orientation.VERTICAL),cekEmail);
		hbox2.getChildren().add(info);
		
		vbox1.setPrefWidth(Main.primaryStage.getWidth());
		vbox1.getChildren().addAll(hbox1,hbox2,table,label_stat,button_clear);
		this.getChildren().add(vbox1);				
	}

	private void init() {

		ul=GlobalUtility.getUser_logged_in();
		table=new TableView();
		vbox1=new VBox(5);
		vbox1.setPadding(new Insets(5,5,5,5));
		
		hbox1=new HBox(5);
		hbox1.setPadding(new Insets(5,5,5,5));
		hbox1.setAlignment(Pos.CENTER_LEFT);
		
		hbox2=new HBox(1);
		//hbox2.setPadding(new Insets(5,5,5,5));
		hbox2.setAlignment(Pos.CENTER_LEFT);

		imgView = new ImageView(new Image(getClass().getResourceAsStream("/app/spinner.gif")));
		imgView.setFitHeight(50);
		imgView.setFitWidth(50);

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
		
		label_stat=new Label(strKet);
		info=new Label("Proses [Simpan] memakan waktu beberapa detik terkait pengiriman notifikasi email ke User yang berkaitan.");
		info.setStyle("fx-font-size:8px;");

		isAdmin=Boolean.FALSE;
		if(ul.getId_role().equals("1"))isAdmin=Boolean.TRUE;
		
		button_clear=new Button("Clear");
		button_clear.setPrefWidth(100);
		
		button_save=new Button("Simpan");
		button_save.setPrefWidth(100);
		
		text_nomor=new TextField();
		text_nomor.setPromptText("Nomor Surat DBP Kolektif");
		
		text_ket=new TextField();
		text_ket.setPromptText("Keterangan...");
		text_ket.setPrefWidth(400);

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
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Dialog Konfirmasi");
					alert.setHeaderText("Pembuatan Daftar Permintaan Kolektif");
					alert.setContentText("Nomor Surat: ["+nomor+"]\nLanjut proses Simpan?");
					
					hbox2.getChildren().clear();
					hbox2.getChildren().add(imgView);//tambahkan progress bar
					hbox2.setAlignment(Pos.CENTER);
					
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
									if (isSendMail==true){
										b=new EmailController().justSend(nomor);//kirim email
									}
									return null;
								}};
							task.setOnSucceeded(e->{								
								new DPBKolektifModify().UpdateOnMailSend(nomor);
								//Main.borderPane.setCenter(new DPBKolektif());
							});
							
							if(GlobalUtility.getInetStat()==true){
								//hbox1.getChildren().add(pi);
								task.run();
								//Main.borderPane.setCenter(new DPBKolektif());
								//b=new EmailController().SendMail(nomor);//kirim email
							}else {
								//Main.borderPane.setCenter(new DPBKolektif());
							}
							alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Dialog Informasi");
							alert.setHeaderText("Pembuatan Daftar Permintaan Kolektif");
							if(b.equals(Boolean.TRUE)) {
								alert.setContentText("Berhasil simpan data dan kirim mail notifikasi.");
								//new DPBKolektifModify().UpdateOnMailSend(nomor);
							}else {
								alert.setContentText("Berhasil simpan data.");
							}
							alert.show();
							Main.borderPane.setCenter(new DPBKolektif());
						}
					} else {
					    hbox2.getChildren().clear();
						hbox2.setAlignment(Pos.CENTER_LEFT);
					    hbox2.getChildren().add(info);
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
	}

}