package app.view;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

import app.GlobalUtility;
import app.Main;
import app.controller.CekRole;
import app.controller.DBHelper;
import app.controller.LoginInquiry;
import app.model.DataCekRole;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Login {
	private VBox vbox;
	private HBox hbox;
	private TextField username;
	private PasswordField password;
	private Button button_login,button_cancel;
	private ImageView imgView;
	private Label label,judul;
	private Separator sep1;
	ProgressBar pb;


	public Login() {
		//test script
		//System.out.println(getClass().getResource("/app/reports/cetak-permintaan.jrxml"));
		//ReadConfig();
		//test script

		vbox=new VBox(5);
		//vbox.setStyle("-fx-background-color:#fff;-fx-background-radius:5px;");

		hbox=new HBox(5);

		judul=new Label("Login User");judul.getStyleClass().addAll("judul");
		label=new Label("Masukkan NIK dan Password untuk mengakses menu utama.");

		sep1=new Separator(Orientation.HORIZONTAL);
		
		username=new TextField();
		username.setAlignment(Pos.CENTER);
		username.setPromptText("NIK...");
		username.setText("1");

		password=new PasswordField();
		password.setAlignment(Pos.CENTER);
		password.setPromptText("Password...");
		password.setText("1");
		
		button_login=new Button("Login");		
		button_login.setPrefWidth(100);
		button_cancel=new Button("Cancel");
		button_cancel.setPrefWidth(90);

		Image imaji= new Image(getClass().getResourceAsStream("/app/login_banner.png"));

		/*Image imaji2= new Image(getClass().getResourceAsStream("/app/asisten-atk-logo.png"));
		ImageView logo=new ImageView(imaji2);
		logo.setFitHeight(200);
		logo.setFitWidth(200);*/

		pb = new ProgressBar(0);
		//pb.setProgress(0d);

		imgView = new ImageView(imaji);
		imgView.setFitHeight(150);
		imgView.setFitWidth(600);
		hbox.getChildren().addAll(username,password,button_login,button_cancel);
		//vbox.getChildren().addAll(hbox,imgView,label);
		vbox.getChildren().addAll(new LabelJudul("Login User"),new Separator(Orientation.HORIZONTAL),hbox,imgView,label,sep1);
		if (imaji==null) vbox.getChildren().remove(imgView);
		hbox.setAlignment(Pos.BASELINE_CENTER);
		vbox.setAlignment(Pos.CENTER);
		
		button_login.setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent event) {
				ProsesLogin();
			}
		});
		
		button_cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Platform.exit();
				//System.out.println(pb.getProgress()+"");
				//pb.setProgress(-1.0);
			}
		});
		
		/*Main.primaryStage.setWidth(600);
		Main.primaryStage.setHeight(300);*/
		//Main.primaryStage.setResizable(false);
		Main.label_status.setText("Status: Log out.");

		username.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)){
					password.requestFocus();
				}
			}
		});

		password.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)){
					button_login.requestFocus();
				}
			}
		});

		button_login.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)){
					ProsesLogin();
				}
			}
		});
	}

	private void ProsesLogin() {
		if(username.getText().toString().trim().length()>0 && password.getText().toString().trim().length()>0) {
			try {
				int logins=new LoginInquiry(username.getText().toString().trim(), password.getText().toString().trim()).Proses();
				if(logins>0) {
					Main.menuBar.setVisible(true);
					vbox.getChildren().clear();
					vbox.setVisible(false);

					String nama_role=GlobalUtility.getUser_logged_in().getNama_role();
					String nama_divisi=GlobalUtility.getUser_logged_in().getNama_divisi();

					SetMenu();

					Main.primaryStage.setMaximized(true);
					Main.label_status.setText("Status: Log in  ||  host: "+DBHelper.getHost()+"  ||  User: "+GlobalUtility.getUser_logged_in().getNama()+"  ||  Hak Akses: "+nama_role+"  ||  Divisi: "+nama_divisi);
				}else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Warning");
					alert.setHeaderText("Terjadi kesalahan.");
					alert.setContentText("Kombinasi NIK dan Password tidak tepat.");
					alert.showAndWait();
					/*Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK){
					} else {
					}*/
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public VBox vLogin() {
		vbox.setPadding(new Insets(5, 5, 5, 5));
		vbox.setMaxHeight(270);
		vbox.setMaxWidth(500);
		vbox.getStyleClass().add("box-color");
		return vbox;
	}

	private void SetMenu(){
		if (Main.menuBar.isVisible()==true){
			DataCekRole dcr=new CekRole().GetRole(GlobalUtility.getUser_logged_in().getId_role());

			//if (dcr.getBerkas_barang()<1)Main.barang.setDisable(true);
			Main.barang.setDisable(dis(dcr.getBerkas_barang()));
			Main.divisi.setDisable(dis(dcr.getBerkas_divisi()));
			Main.posisi.setDisable(dis(dcr.getBerkas_divisi()));
			Main.vendor.setDisable(dis(dcr.getBerkas_divisi()));
			Main.buat_permintaan.setDisable(dis(dcr.getProses_buat_permintaan()));
			Main.edit_permintaan.setDisable(dis(dcr.getProses_edit_permintaan()));
			Main.hapus_permintaan.setDisable(dis(dcr.getProses_hapus_permintaan()));
			Main.review_permintaan.setDisable(dis(dcr.getProses_review_permintaan()));
			Main.buat_dpb_kolektif.setDisable(dis(dcr.getProses_buat_dpb_kolektif()));
			Main.hapus_dpb_kolektif.setDisable(dis(dcr.getProses_hapus_dpb_kolektif()));
			Main.notifikasi_email.setDisable(dis(dcr.getProses_cek_email()));
			Main.laporan_permintaan.setDisable(dis(dcr.getLaporan_permintaan()));
			Main.laporan_dpb_kolektif.setDisable(dis(dcr.getLaporan_dpb_kolektif()));
			Main.laporan_dpb_vendor.setDisable(dis(dcr.getLaporan_dpb_vendor()));
			Main.laporan_dpb_vendor2.setDisable(dis(dcr.getLaporan_dpb_vendor()));
			Main.set_user.setDisable(dis(dcr.getPengaturan_admin()));
			Main.set_hak.setDisable(dis(dcr.getPengaturan_admin()));


			//System.out.println("Hasil: "+GlobalUtility.getUser_logged_in().getId_role()+"\nAkses barang: "+dcr.getBerkas_barang());
		}
	}

	private boolean dis(int value){
		boolean ret=true;
		if (value>0)ret=false;
		return ret;
	}
}
