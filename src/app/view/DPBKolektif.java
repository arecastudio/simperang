package app.view;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DPBKolektif extends VBox {
	private VBox vbox,vbox1,vbox2;
	private HBox hbox;
	private TabPane tabPane;
	private Tab tabPilih,tabSimpan;
	private Label judul;
	private String desc="Modul ini berfungsi untuk memilih Permintaan Barang dari tiap Divisi untuk kemudaian di muat dalam Draf Kolektif yang diteruskan ke Vendor";
	
	public DPBKolektif() {
		init();

		setSpacing(5);
		setPadding(new Insets(5, 5, 5, 5));
		setMaxHeight(600);
		setMaxWidth(1024);
		setAlignment(Pos.TOP_CENTER);
		getStyleClass().add("box-color");

		this.getChildren().addAll(judul,new Separator(Orientation.HORIZONTAL),new Label(desc),tabPane);
	}

	private void init() {

		vbox=new VBox(5);
		vbox1=new VBox(5);
		vbox2=new VBox(5);
		hbox=new HBox(5);
		tabPane=new TabPane();
		judul=new Label("Buat Daftar Permintaan Barang secara Kolektif");judul.getStyleClass().addAll("judul");
		
		tabPilih=new Tab();
		tabPilih.setText("Pilih Permintaan Barang");	
		tabPilih.setClosable(false);
		hbox.getChildren().add(new TabDPBItem());
		tabPilih.setContent(hbox);
		
		tabSimpan=new Tab();
		tabSimpan.setText("Buat Permintaan Kolektif");
		tabSimpan.setClosable(false);
		//vbox2.getChildren().add(new TabPermintaanItemSave(Boolean.FALSE));
		vbox2.getChildren().add(new TabDPBItemSave());
		tabSimpan.setContent(vbox2);
		
		tabPane.getTabs().addAll(tabPilih,tabSimpan);
	}

}
