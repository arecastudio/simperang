package app.view;

import java.io.*;
import java.sql.Blob;
import java.util.Optional;

import app.Main;
import app.controller.BarangShow;
import app.controller.BarangModify;
import app.model.DataBarang;
import app.model.DataBarangJenis;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class Barang {
	private Button button_add,button_del,button_fresh;
	private VBox vbox;
	private HBox hbox,hbox2;
	private TextField nama,satuan,keterangan,harga;
	private TableView table;
	private Label label,judul;

	private FileChooser fileChooser;
	private File file;

	private Button button_browse;

	private ToggleGroup group;
	private RadioButton radio1,radio2;

	private String tmpId="";

	private ImageView imgView;

	private String path;
	
	public VBox vBarang() {
		vbox.setPadding(new Insets(5, 5, 5, 5));
		vbox.setMaxHeight(500);
		vbox.setMaxWidth(1024);
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.getStyleClass().add("box-color");

		GridPane grid=new GridPane();
		grid.setHgap(10);grid.setVgap(5);
		grid.setPadding(new Insets(0,0,15,0));

		grid.add(new Label("Nama Barang"),0,0);
		grid.add(nama,1,0);

		grid.add(new Label("Harga"),0,1);
		grid.add(harga,1,1);

		grid.add(new Label("Satuan"),0,2);
		grid.add(satuan,1,2);

		grid.add(new Label("Keterangan"),0,3);
		grid.add(keterangan,1,3);

		/*grid.add(new Label("Jenis Barang"),0,4);
		grid.add(new HBox(radio1,new Separator(Orientation.VERTICAL),radio2),1,4);*/

		//hbox.getChildren().addAll(nama,harga,satuan,keterangan);
		hbox2.getChildren().addAll(button_add,button_fresh,button_del);

		VBox vbox10=new VBox(5);
		vbox10.setPadding(new Insets(0,5,0,25));
		vbox10.setAlignment(Pos.CENTER);
		vbox10.getChildren().add(imgView);

		vbox.getChildren().addAll(judul,new Separator(Orientation.HORIZONTAL),new HBox(grid,vbox10),new Separator(Orientation.HORIZONTAL),new HBox(button_browse),hbox2,table,new HBox(label));
		return vbox;
	}
	
	public Barang() {
		vbox=new VBox(5);
		//vbox.getStyleClass().addAll("vbox");
		hbox=new HBox(3);
		hbox2=new HBox(15);hbox2.setAlignment(Pos.CENTER);

		imgView=new ImageView();
		imgView.setFitWidth(150);
		imgView.setFitHeight(150);

		fileChooser=new FileChooser();
		fileChooser.setTitle("Pilih Gambar");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files","*.jpg"));

		button_browse=new Button("Pilih Gambar");
		button_browse.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				file=fileChooser.showOpenDialog(Main.primaryStage);
				label.setText("Keterangan : Gambar dipilih ["+file+"]");
				//label_ket.setText("Keterangan: "+ file);
				Image image=new Image(file.toURI().toString());
				imgView.setImage(image);
			}
		});
		
		label=new Label("Keterangan :");
		judul=new Label("Data Barang");
		judul.getStyleClass().addAll("judul");
		
		nama=new TextField();
		nama.setPromptText("Nama barang...");
		nama.setMinWidth(250);
		
		satuan=new TextField();
		satuan.setPromptText("Satuan...");
		satuan.setPrefWidth(100);
		
		keterangan=new TextField();
		keterangan.setPromptText("Keterangan...");
		keterangan.setMinWidth(350);
		
		harga=new TextField();
		harga.setPromptText("Harga barang [dlm angka]");
		harga.setPrefWidth(200);

		group=new ToggleGroup();
		radio1=new RadioButton(new DataBarangJenis().getAtk());
		radio1.setToggleGroup(group);
		radio1.setSelected(true);
		radio2=new RadioButton(new DataBarangJenis().getInvent());
		radio2.setToggleGroup(group);
		
		harga.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        try {
		        	if (newValue.matches("\\d*")) {
			            int value = Integer.parseInt(newValue);
			        } else {
			            harga.setText(oldValue);
			        }
				} catch (Exception e) {
					// TODO: handle exception
				}
		    }
		});
		
		button_add=new Button("Simpan/Ubah");
		button_add.setPrefWidth(150);
		//button_add.setText("Barang");
		button_add.setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent event) {
				int simpan=0;
				if(tmpId!="") {
                    //simpan=new BarangModify(nama.getText().toString().trim(),satuan.getText().toString().trim(),keterangan.getText().toString().trim(),nama.getStyle().toString().trim(),harga.getText().toString().trim()).ProsesUpdate();
					if (nama.getText().trim().length()>0
							&& satuan.getText().trim().length()>0
							&& keterangan.getText().trim().length()>0
							&& harga.getText().trim().length()>0){

						DataBarang db=new DataBarang();
						db.setId(tmpId);
						db.setNama(nama.getText().toString().trim());
						db.setSatuan(satuan.getText().toString().trim());
						db.setKeterangan(keterangan.getText().toString().trim());
						db.setHarga(harga.getText().toString().trim());


						if (file!=null){
							FileInputStream fis= null;
							try {
								fis = new FileInputStream(file);
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							}
							db.setImg(fis);
						}

						simpan=new BarangModify().ProsesUpdate(db);
					}
                }else {
                    //simpan=new BarangModify(nama.getText().toString().trim(),satuan.getText().toString().trim(),keterangan.getText().toString().trim(),"0",harga.getText().toString().trim()).ProsesSave();
					if (nama.getText().trim().length()>0
							&& satuan.getText().trim().length()>0
							&& keterangan.getText().trim().length()>0
							&& harga.getText().trim().length()>0
							&& file!=null){

						DataBarang db=new DataBarang();
						db.setNama(nama.getText().toString().trim());
						db.setSatuan(satuan.getText().toString().trim());
						db.setKeterangan(keterangan.getText().toString().trim());
						db.setHarga(harga.getText().toString().trim());
						FileInputStream fis= null;
						try {
							fis = new FileInputStream(file);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
						db.setImg(fis);
						simpan=new BarangModify().ProsesSave(db);
					}
                }
				if (simpan==1) {
                    System.out.println("Tersimpan");
                    refresh();
                }else System.out.println("Gagal");
			}
		});
		
		button_del=new Button("Hapus");
		button_del.setPrefWidth(150);
		button_del.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(tmpId!="") {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Dialog Konfirmasi");
					alert.setHeaderText("Anda akan menghapus data ini,");
					alert.setContentText("Yakin?");

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK){
					    if(tmpId!="") {
					    	try {
								//int hapus=new BarangModify(nama.getText().toString().trim(),satuan.getText().toString().trim(),keterangan.getText().toString().trim(),nama.getStyle().toString().trim(),harga.getText().toString().trim()).ProsesDelete();
								int hapus=new BarangModify().ProsesDelete(tmpId);
								refresh();
							} catch (Exception e) {
								e.printStackTrace();
							}					    
					    }
					} else {
					    // ... user chose CANCEL or closed the dialog
					}
				}
			}
		});
		
		button_fresh=new Button("Refresh");
		button_fresh.setPrefWidth(150);
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
		 
        TableColumn idCol = new TableColumn("ID");
        TableColumn namaCol = new TableColumn("Nama");        
        TableColumn satuanCol = new TableColumn("Satuan");
        TableColumn keteranganCol = new TableColumn("Keterangan");
        TableColumn hargaCol=new TableColumn("Harga");
        //TableColumn imgCol=new TableColumn("Img.");
        
        table.getColumns().addAll(idCol, namaCol,hargaCol, satuanCol,keteranganCol);
        
        hargaCol.setStyle("-fx-alignment:center-right;");
        hargaCol.setPrefWidth(150);
        
        idCol.setCellValueFactory(new PropertyValueFactory<DataBarang,String>("id"));
        namaCol.setCellValueFactory(new PropertyValueFactory<DataBarang,String>("nama"));
        satuanCol.setCellValueFactory(new PropertyValueFactory<DataBarang,String>("satuan"));
        keteranganCol.setCellValueFactory(new PropertyValueFactory<DataBarang,String>("keterangan"));
        hargaCol.setCellValueFactory(new PropertyValueFactory<DataBarang,String>("harga"));
		//imgCol.setCellValueFactory(new PropertyValueFactory<DataBarang,Object>("img"));
        
        table.setItems(new BarangShow().DataBarangShow());
        
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				if(table.getSelectionModel().getSelectedItem() != null) {
					DataBarang pilihBarang=(DataBarang) table.getSelectionModel().getSelectedItem();
					//nama.setStyle(pilihBarang.getId());
					tmpId=pilihBarang.getId();
					nama.setText(pilihBarang.getNama());
					satuan.setText(pilihBarang.getSatuan());
					label.setText("ID: "+pilihBarang.getId());
					keterangan.setText(pilihBarang.getKeterangan());
					harga.setText(pilihBarang.getHarga());

					//System.out.println(tmpId);

					path=System.getProperty("java.io.tmpdir").toString();
					if (path.substring(path.length()-1,path.length())!="/") path+="/";
					System.out.println("temp dir: "+path);

					imgView.setImage(null);

					byte[] b=new BarangModify().GetImage(tmpId);
					if (b!=null){
						Task<Void>task=new Task<Void>() {
							@Override
							protected Void call() throws Exception {
								FileOutputStream fos = new FileOutputStream(path+tmpId+".jpg");
								fos.write(b);
								return null;
							}
						};
						task.setOnSucceeded(e->{
							File f=new File(path+tmpId+".jpg");
							Image image=new Image(f.toURI().toString());
							imgView.setImage(image);
						});

						Thread t=new Thread(task);
						t.setDaemon(true);
						t.start();
					}

				}
			}
		});
	}
	
	private void refresh() {
		tmpId="";
		nama.setText("");
		satuan.setText("");
		keterangan.setText("");
		harga.setText("");
		label.setText("Keterangan :");
		table.setItems(new BarangShow().DataBarangShow());
		imgView.setImage(null);
		nama.requestFocus();
	}

}
