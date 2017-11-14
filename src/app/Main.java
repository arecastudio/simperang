/*

 * Aplikasi Pengadaan Barang dan Inventaris
 * pada Perusahan Listrik Negara
 * Divisi Pengembangan
 * Ranting Jayapura, Papua
 * 
 * developer: Areca Studio
 * weblog: https://arecastudio.github.io/
 * email: ondiisrail@gmail.com
 *
 *
 * Task List
 * -buat form pengaturan surat per divisi, untuk person manager dalam nama cetakan surat
 * -buat sub report daftar permintaan barang kolektif/rekap untuk vendor
 * -buat jenis barang, atk dan inventaris
 * -hapus role otomatis mengubah user-role ke guest (OPSIONAL)
 * */

package app;

import app.controller.Terbilang;
import app.view.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application{
	public static Stage primaryStage;
    public static BorderPane borderPane;
    private Scene scene;
    public static MenuItem barang,divisi,posisi,tutup,buat_permintaan,edit_permintaan,
    review_permintaan,set_user,buat_dpb_kolektif,laporan_permintaan,laporan_dpb_vendor,laporan_dpb_vendor2,
    hapus_permintaan,hapus_dpb_kolektif,notifikasi_email,laporan_dpb_kolektif,set_hak,tambah_hak,
	set_panduan,set_db,vendor;
    public static MenuBar menuBar;
    private ImageView imgView;
    public static Label label_status;
    private HBox hbox;
    
    public Main() {
    	//String hasil=new Terbilang().bilangx(new Double(1234567890123L));
		//System.out.println(hasil);
    }

    public void start(Stage stage) {
    	this.primaryStage=stage;
        stage.setTitle("Aplikasi Pengadaan Barang - PT. PLN, UIP Papua");
        //Scene scene = new Scene(new VBox(), 400, 350);        
        borderPane=new BorderPane();
        //borderPane.set

        scene=new Scene(borderPane, 1324, 600);
        scene.getStylesheets().add("app/style.css");
        setMenu();
 
        label_status=new Label("Status :");
        label_status.getStyleClass().addAll("label_status");
        //label_status.setPadding(new Insets(3,3,3,3));
        
        hbox=new HBox(5);
        hbox.setPadding(new Insets(5,5,5,5));
        hbox.getStyleClass().add("statusbar");
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().add(label_status);
        
        //((VBox) scene.getRoot()).getChildren().addAll(menuBar);
        //((BorderPane) scene.getRoot()).setTop(menuBar);
        borderPane.setTop(menuBar);
        borderPane.setBottom(hbox);
        //scene.setFill(Color.CADETBLUE);
        
        //imgView = new ImageView(new Image(getClass().getResourceAsStream("hd-wallpaper.jpg")));
        borderPane.setCenter(new Login().vLogin());

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/app/asisten-atk-logo.png")));
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();       
        
    }
	public void setMenu() {
        menuBar = new MenuBar();
        menuBar.getStyleClass().addAll("menubar");
        // --- Menu File
        Menu menuBerkas = new Menu("Berkas"); 
        barang=new MenuItem("Data Barang");
        //if(deactiveMenu.equals("barang"))barang.setDisable(true);
        divisi=new MenuItem("Data Divisi");
        posisi=new MenuItem("Data Posisi / Jabatan");
		vendor=new MenuItem("Data Vendor");
        tutup=new MenuItem("Log out");
        menuBerkas.getItems().addAll(barang, new SeparatorMenuItem(), divisi,posisi, new SeparatorMenuItem(),vendor, new SeparatorMenuItem(), tutup);
        // --- Menu Edit
        Menu menuProses = new Menu("Proses"); 
        buat_permintaan=new MenuItem("Buat Permintaan");
        edit_permintaan=new MenuItem("Edit Permintaan");
        hapus_permintaan=new MenuItem("Hapus Permintaan");
        review_permintaan=new MenuItem("Review Permintaan");
        buat_dpb_kolektif=new MenuItem("Buat DPB Kolektif");
        hapus_dpb_kolektif=new MenuItem("Hapus DPB Kolektif");
        notifikasi_email=new MenuItem("Notifikasi Email");
        menuProses.getItems().addAll(buat_permintaan,edit_permintaan,hapus_permintaan,review_permintaan, new SeparatorMenuItem(),buat_dpb_kolektif,hapus_dpb_kolektif,new SeparatorMenuItem(),notifikasi_email);
        // --- Menu View
        Menu menuLaporan = new Menu("Laporan");
        laporan_permintaan=new MenuItem("Cetak Permintaan");
        laporan_dpb_kolektif=new MenuItem("Cetak DPB Kolektif");
		laporan_dpb_vendor=new MenuItem("Rekap Permintaan ke Vendor");
		laporan_dpb_vendor2=new MenuItem("Cetak Rekap Permintaan Vendor");
        menuLaporan.getItems().addAll(laporan_permintaan,laporan_dpb_kolektif,new SeparatorMenuItem(),laporan_dpb_vendor,laporan_dpb_vendor2);
        // --- Menu View
        Menu menuPengaturan = new Menu("Pengaturan"); 
        set_user=new MenuItem("User / Karyawan");
        set_hak=new MenuItem("Atur Hak Masuk");
        tambah_hak=new MenuItem("Tambah Hak Masuk");
        set_panduan=new MenuItem("Panduan");
        set_db=new MenuItem("Export/ Import Data");
        set_db.setDisable(true);
        menuPengaturan.getItems().addAll(set_user,set_hak, new SeparatorMenuItem(),set_db, new SeparatorMenuItem(),set_panduan);
        
        menuBar.getMenus().addAll(menuBerkas, menuProses, menuLaporan,menuPengaturan);
        clickMenu();
		menuBar.setVisible(false);
	}
	public static void main(String[] args) {
		launch(args);

		//int bilangan = 55343647;
		//System.out.print(bilangan +" = ");
		//System.out.println(new TerbilangEngine);
	}
	
	private void clickMenu() {        
        barang.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Klik: menu Barang ");
                Barang hbarang=new Barang();
                borderPane.setCenter(hbarang.vBarang());
            }
        });
		
		divisi.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Divisi hdivisi=new Divisi();
				borderPane.setCenter(hdivisi.vDivisi());
			}
		});

		posisi.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				borderPane.setCenter(new Posisi());
			}
		});

		vendor.setOnAction(event -> {
			borderPane.setCenter(new Vendor());
		});
		
		tutup.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LoginMode();				
			}
		});
		
		buat_permintaan.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				BuatPermintaan bp=new BuatPermintaan();
				borderPane.setCenter(bp.vPermintaan());
			}
		});
		
		edit_permintaan.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				borderPane.setCenter(new EditPermintaan());
				
			}
		});
		
		hapus_permintaan.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				borderPane.setCenter(new HapusPermintaan());
				
			}
		});
		
		review_permintaan.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				borderPane.setCenter(new ReviewPermintaan());				
			}
		});
		
		buat_dpb_kolektif.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				borderPane.setCenter(new DPBKolektif());				
			}
		});
		
		hapus_dpb_kolektif.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				borderPane.setCenter(new HapusDPB());				
			}
		});
		
		laporan_permintaan.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				borderPane.setCenter(new ReportPermintaan());				
			}
		});
		
		laporan_dpb_kolektif.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				borderPane.setCenter(new ReportDPB());				
			}
		});

		laporan_dpb_vendor.setOnAction(event -> {
			borderPane.setCenter(new ReportDPBVendor());
		});

		laporan_dpb_vendor2.setOnAction(event -> {
			//
		});
		
		set_user.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				borderPane.setCenter(new SetUser());				
			}
		});

		set_hak.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				borderPane.setCenter(new SetRole());
			}
		});

		tambah_hak.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				borderPane.setCenter(new AddRole());
			}
		});
		notifikasi_email.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				borderPane.setCenter(new EmailDPB());
			}
		});

		set_db.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				borderPane.setCenter(new SetDB());
			}
		});


	}
	
	private void LoginMode(){
		menuBar.setVisible(false);
		/*primaryStage.setMaximized(false);
		primaryStage.setWidth(600);
		primaryStage.setHeight(300);*/
		borderPane.setCenter(new Login().vLogin());
	}

}

/*
Bersama ini kami memesan Kepada Perusanaan Saudara Barang-barang sebagaimana terlampir.
dengan syarat-syarat sebagai berikut :
1 PPn 10% dan PPh 1,5%. Mpungut langsung oleh Pinak PLN UIP Papua,
2 Tidak diberi uang muka,
3 Barang /material sudah harus diterima PLN(Persero) UIP Papua paling lambat tanggal
Selurunnya, dan apabila sampai batas waktu tersebut ternyata Saudara belum menyerahkan
barang/material maka Saudara harus membayar ganti rugi,
4 Barang /material harus diterima dengan baik, lengkap, baru dan asli sesuai spesifikasi yang ditentukan
dalam Surat Pesanan ini,
5 Pembayaran akan dilaksanakan 100% setelah barang/material/sparepart diterima dengan baik dan
melampirkan antara lain :
a. Kwitansi taginan bermeterai
b. Faktur Pajak
c. Surat Setoran Pajak
6 Apabila syarat-syarat tersebut di atas tidak dipenuhi, barang/material/sparepart akan dikembalikan/ditolak dan segala biaya yang timbul menjadi tanggung jawab Sandara.
Demikian kami sampaikan, dengan harapan dapat diselesaikan dengan sebaik-baiknya.
 */