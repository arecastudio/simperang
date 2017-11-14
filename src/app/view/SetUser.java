package app.view;

import java.util.Optional;

import app.GlobalUtility;
import app.Main;
import app.controller.DivisiModify;
import app.controller.UserModify;
import app.model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

public class SetUser extends VBox {
	private HBox hbox1;//,hbox2;
	private VBox vbox1;
	private TableView table;
	private GridPane grid,grid2,grid3;
	private TextField text_nik,text_nama,text_telp,text_email,text_posisi,text_divisi,text_atasan;
	private PasswordField text_password,text_password2;
	private Label judul;
	private Button button_simpan,button_refresh,button_del;
	private ComboBox<DataRole> cbxRole;
	private ComboBox<DataDivisi> cbxDivisi;
	
	private String tmp_id_divisi,tmpManager,tmp_id_role;
	
	public SetUser() {
		init();
		refresh();

		grid.add(new Label("NIK"), 0, 0);grid.add(text_nik, 1, 0);
		grid.add(new Label("Nama"), 0, 1);grid.add(text_nama, 1, 1);
		grid.add(new Label("Password"), 0, 2);grid.add(text_password, 1, 2);		
		grid.add(new Label("Konfirmasi"), 0, 3);grid.add(text_password2, 1, 3);
		
		grid2.add(new Label("Telepon"), 0, 0);grid2.add(text_telp, 1, 0);
		grid2.add(new Label("Email"), 0, 1);grid2.add(text_email, 1, 1);
		grid2.add(new Label("Divisi"), 0, 3);grid2.add(cbxDivisi, 1, 3);
		grid2.add(new Label("Hak Masuk"), 0, 4);grid2.add(cbxRole, 1, 4);
		//grid2.add(new Label("Posisi / Jab."), 0, 2);grid2.add(cbxPosisi, 1, 2);
		//grid2.add(new Label("Atasan"), 0, 3);grid2.add(cbxManager, 1, 3);

		//grid3.add(new Label("Divisi"), 0, 0);grid3.add(text_divisi, 1, 0);
		//grid3.add(new Label("Atasan"), 0, 1);grid3.add(text_atasan, 1, 1);

		//grid3.add(new Label("Atasan"), 0, 1);grid3.add(cbxManager, 1, 1);

		
		vbox1.getChildren().addAll(button_simpan,button_refresh,new Label("\n\n"),button_del);
		hbox1.getChildren().addAll(grid,new Separator(Orientation.VERTICAL),grid2,new Separator(Orientation.VERTICAL),vbox1);
		this.getChildren().addAll(judul,new Separator(Orientation.HORIZONTAL),hbox1,table);
		this.setPadding(new Insets(5,5,5,5));
		this.setSpacing(3);
	}

	private void init() {
		setSpacing(5);
		setPadding(new Insets(5, 5, 5, 5));
		setMaxHeight(500);
		setMaxWidth(1024);
		setAlignment(Pos.TOP_CENTER);
		getStyleClass().add("box-color");

		grid=new GridPane();
		grid.setHgap(3);
		grid.setVgap(3);
		//grid.setStyle("-fx-background-color:#ddd;-fx-background-radius:5px;");
		grid.setPadding(new Insets(10,10,10,10));

		grid2=new GridPane();
		grid2.setHgap(3);
		grid2.setVgap(3);
		//grid2.setStyle("-fx-background-color:#ddd;-fx-background-radius:5px;");
		grid2.setPadding(new Insets(10,10,10,10));

		grid3=new GridPane();
		grid3.setHgap(3);
		grid3.setVgap(3);
		//grid3.setStyle("-fx-background-color:#ddd;-fx-background-radius:5px;");
		grid3.setPadding(new Insets(10,10,10,10));
		
		hbox1=new HBox(5);
		hbox1.getStyleClass().add("box-color");

		//hbox1.setStyle("-fx-background-color:#fff;-fx-background-radius:5px;");
		//this.hbox2=new HBox(5);
		this.vbox1=new VBox(5);
		
		tmp_id_divisi=new String("");
		tmpManager=new String("");
		tmp_id_role=new String("");
		
		table=new TableView();
		text_nik=new TextField();
		this.text_nama=new TextField();
		this.text_password=new PasswordField();
		this.text_password2=new PasswordField();
		this.text_telp=new TextField();
		this.text_email=new TextField();
		this.text_posisi=new TextField();		
		this.text_divisi=new TextField();
		this.text_atasan=new TextField();

		judul=new Label("User Management");
		judul.getStyleClass().addAll("judul");

		//------------------------------------
		/*cbxPosisi=new ComboBox<>(new PosisiModify().GetTableItems());
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
				System.out.println("posisi: " + newval.getNama() + ", id: " + tmp_id_posisi);
			}
		});*/
		//------------------------------------
		cbxRole=new ComboBox<>(GlobalUtility.getRole());
		cbxRole.setPrefWidth(200);
		cbxRole.setConverter(new StringConverter<DataRole>() {
			@Override
			public String toString(DataRole object) {
				// TODO Auto-generated method stub
				return object.nama;
			}

			@Override
			public DataRole fromString(String string) {
				// TODO Auto-generated method stub
				return cbxRole.getItems().stream().filter(ap ->
	            ap.getNama().equals(string)).findFirst().orElse(null);
			}
		});

		cbxRole.valueProperty().addListener((obs, oldval, newval) -> {
		    if(newval != null) {
		        /*label.setText("NIK : "+newval.getNik());*/
		    	cbxRole.setStyle(newval.getId());
		    	tmp_id_role=newval.getId();
				System.out.println("role: " + newval.getNama() + ", id: " + tmp_id_role);
		    }
		});
		//-----------------------
		/*cbxManager=new ComboBox<>(new UserModify().GetTableItems());
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
		    	tmpManager=newval.getNik();
		    	tmp_id_posisi_atasan=new UserModify().GetIdPosisiByNik(tmpManager);
				System.out.println("manager: " + newval.getNama() + ". nik: " + newval.getNik()+", id_posisi: "+tmp_id_posisi_atasan);
		    }
		});*/
		//-----------------------
		cbxDivisi=new ComboBox<>(new DivisiModify().GetTableItems());
		cbxDivisi.setConverter(new StringConverter<DataDivisi>() {
			
			@Override
			public String toString(DataDivisi object) {				
				return object.getNama();
			}
			
			@Override
			public DataDivisi fromString(String string) {
				// TODO Auto-generated method stub
				return cbxDivisi.getItems().stream().filter(ap -> 
	            ap.getNama().equals(string)).findFirst().orElse(null);
			}
		});
		cbxDivisi.valueProperty().addListener((obs, oldval, newval) -> {
		    if(newval != null) {
		        //label.setText("NIK : "+newval.getNik());
		        cbxDivisi.setStyle(newval.getId());		        
		        System.out.println("Divisi: " + newval.getNama() + ". Id: " + cbxDivisi.getStyle().trim());
		        tmp_id_divisi=newval.getId();
		        cbxDivisi.setStyle(newval.getId());
		    }
		});
		//-----------------------
		button_simpan=new Button("Simpan");button_simpan.setStyle("-fx-min-width:100px");
		button_refresh=new Button("Refresh");button_refresh.setStyle("-fx-min-width:100px");
		button_del=new Button("Hapus");button_del.setStyle("-fx-min-width:100px");
		
		button_simpan.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(text_nik.isDisabled()) {
					//update mode / mode ubah data
					if(isValid()) {
						int ubah1= new UserModify().Ubah(								
										text_nik.getText().toString().trim(), 
										text_nama.getText().toString().trim(), 
										text_password.getText().toString().trim(), 
										text_telp.getText().toString().trim(), 
										text_email.getText().toString().trim(), 
										tmp_id_divisi,
										tmp_id_role
								);	
						if(ubah1>0) {
							System.out.println("terupdate111.....");
							refresh();
						}
					}else {
						Toast.makeText(Main.primaryStage, "Data belum lengkap !", 550, 150, 150,Color.RED);
					}
					//System.out.println("dis");
				}else {
					//System.out.println("en");//data baru +add
					if(isValid() && text_password.getText().toString().trim().length()>0  && text_password2.getText().toString().trim().equals(text_password.getText().toString().trim())) {
						int simpan= new UserModify().Simpan(
										text_nik.getText().toString().trim(), 
										text_nama.getText().toString().trim(), 
										text_password.getText().toString().trim(), 
										text_telp.getText().toString().trim(), 
										text_email.getText().toString().trim(),
										tmp_id_divisi,
										tmp_id_role
										);	
						if(simpan>0) {
							System.out.println("tersimpan.....");
							refresh();
						}
					}else {
						Toast.makeText(Main.primaryStage, "Data belum lengkap !", 550, 150, 150,Color.RED);
					}
				}		
			}
		});
		
		button_del.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Dialog Konfirmasi");
				alert.setHeaderText("Anda akan menghapus data ini,");
				alert.setContentText("Yakin?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
					if(text_nik.isDisable() && text_nik.getText().toString().trim().length()>0) {
						if(new UserModify().Hapus(text_nik.getText().toString().trim())==1) {
							System.out.println("terhapus.....");
							refresh();
						}					
					}
				} else {
				    // ... user chose CANCEL or closed the dialog
				}
							
			}
		});
		
		button_refresh.setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent event) {
				refresh();				
			}
		});
		
		table.setEditable(false);
		table.setColumnResizePolicy((param) -> true );
		table.getStyleClass().addAll("table");
		 
        TableColumn nikCol = new TableColumn("NIK");
        TableColumn namaCol = new TableColumn("Nama");
        TableColumn telpCol = new TableColumn("Telepon");
        TableColumn emailCol = new TableColumn("Email");
        TableColumn divisiCol = new TableColumn("Divisi");
        TableColumn hakCol = new TableColumn("Hak Masuk");
        
        table.getColumns().addAll(nikCol,namaCol,telpCol,emailCol,divisiCol,hakCol);
        //SELECT u.nik,u.nama,u.telp,u.email,u.posisi,d.nama,a.nama,r.nama FROM user AS u LEFT OUTER JOIN divisi AS d ON d.id=u.id_divisi LEFT OUTER JOIN user AS a ON a.nik=u.nik_atasan LEFT OUTER JOIN role AS r ON r.id=u.id_role WHERE 1;
        
        nikCol.setCellValueFactory(new PropertyValueFactory<DataUsers,String>("nik"));
        namaCol.setCellValueFactory(new PropertyValueFactory<DataUsers,String>("nama"));
        telpCol.setCellValueFactory(new PropertyValueFactory<DataUsers,String>("telp"));
        emailCol.setCellValueFactory(new PropertyValueFactory<DataUsers,String>("email"));
        divisiCol.setCellValueFactory(new PropertyValueFactory<DataUsers,String>("divisi"));
        hakCol.setCellValueFactory(new PropertyValueFactory<DataUsers,String>("hak"));

        divisiCol.setPrefWidth(250);

        //table.setItems(GlobalUtility.getUsers());
		table.setItems(new UserModify().GetTableItems());
        
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				if(table.getSelectionModel().getSelectedItem() != null) {
					DataUsers dataUsers=(DataUsers) table.getSelectionModel().getSelectedItem();
					DataUsers du=new UserModify().GetDataUserByNik(dataUsers.getNik());
					text_nik.setDisable(Boolean.TRUE);
					text_nik.setText(du.getNik());
					text_nama.setText(du.getNama());
					text_telp.setText(du.getTelp());
					text_email.setText(du.getEmail());
					//text_posisi.setText(du.getPosisi());
					//text_divisi.setText(du.getDivisi());
					//text_atasan.setText(du.getAtasan());
					
					//Pada ComboBox tidak bisa muncul karena Table cuma cover nama, tidak dengan id
					/*DataKaryawan mgr=new UserShow().getMyManager(du.getNik());
					if(mgr!=null) {
						cbxManager.getSelectionModel().select(mgr);
						cbxManager.setStyle(mgr.getNik());
						//System.out.println("nik_atasan: "+mgr.getNik()+", nama_atasan: "+mgr.getNama());
					}else {
						cbxManager.getSelectionModel().select(new DataKaryawan("",""));
						cbxManager.setStyle("");
					}

					DataUsers mgr=new UserModify().GetMyManager(du.getNik());
					if (mgr!=null){
						cbxManager.getSelectionModel().select(mgr);
					}else{
						cbxManager.getSelectionModel().select(null);
					}*/
					
					DataRole role=new UserModify().GetRoleByNik(du.getNik());
					if(role!=null) {
						cbxRole.getSelectionModel().select(role);
						cbxRole.setStyle(role.getId());
					}else {
						cbxRole.getSelectionModel().select(new DataRole("",""));
						cbxRole.setStyle("");
					}

					/*DataPosisi pss=new PosisiModify().GetPosisiById(du.getPosisi());
					if(pss!=null) {
						cbxPosisi.getSelectionModel().select(pss);
						cbxPosisi.setStyle(pss.getId());
						System.out.println("id_posisi: "+pss.getId()+", nama_posisi: "+pss.getNama());
					}else {
						cbxPosisi.getSelectionModel().select(new DataPosisi());
						cbxPosisi.setStyle("");
					}*/

					DataDivisi dvs=new DivisiModify().GetDataDivisiByNik(du.getNik());
					if(dvs!=null) {
						cbxDivisi.getSelectionModel().select(dvs);
						cbxDivisi.setStyle(dvs.getId());
					}else {
						cbxDivisi.getSelectionModel().select(new DataDivisi("","",""));
						cbxDivisi.setStyle("");
					}
					

					//System.out.println(cbxManager.getValue()+" Ini valuenya CBXManager");
				}
			}
		});
	}
	
	private void refresh(){
		String value="";

		this.cbxRole.setStyle(value);
		//this.cbxDivisi.setStyle(value);
		this.text_nik.setStyle(value);
		
		text_nik.setDisable(Boolean.FALSE);
		this.text_nik.setText(value);
		this.text_nama.setText(value);
		this.text_telp.setText(value);
		this.text_email.setText(value);
		//this.text_posisi.setText(value);
		//this.text_divisi.setText(value);
		this.text_password.setText(value);
		this.text_password2.setText(value);

		this.cbxRole.getSelectionModel().clearSelection();
		this.cbxDivisi.getSelectionModel().clearSelection();

		//cbxPosisi.getSelectionModel().select(new DataPosisi());

		cbxRole.getSelectionModel().select(new DataRole("",""));
		cbxDivisi.getSelectionModel().select(new DataDivisi("","",""));

		this.cbxRole.setStyle(value);
		//this.cbxDivisi.setStyle(value);

		table.setItems(new UserModify().GetTableItems());
		
		tmp_id_divisi=value;
		tmpManager=value;
		tmp_id_role=value;
		
		text_nik.requestFocus();
	}
	
	private boolean isValid() {
		boolean b=false;
		try {
			if( (Integer)text_nama.getText().toString().trim().length()>0
					&& (Integer)text_telp.getText().toString().trim().length()>0
					&& (Integer)text_email.getText().toString().trim().length()>0
					&& (Integer)tmp_id_role.length()>0
					) {b=true;}
		} catch (Exception e) {
			System.out.println("-----------------------------------------------------");
			e.printStackTrace();			
		}
		return b;
	}

}
