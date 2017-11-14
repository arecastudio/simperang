package app.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BuatPermintaan {
	private VBox vbox,vbox1,vbox2;
	private HBox hbox;
	private TabPane tabPane;
	private Tab tabPilih,tabSimpan;
	private Label judul;
	
	public BuatPermintaan() {
		vbox=new VBox(5);
		vbox.setPadding(new Insets(5, 5, 5, 5));
		vbox.setMaxHeight(600);
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.getStyleClass().add("box-color");


		vbox1=new VBox(5);
		vbox2=new VBox(5);
		hbox=new HBox(5);
		tabPane=new TabPane();
		judul=new Label("Buat Permintaan");judul.getStyleClass().addAll("judul");
		
		tabPilih=new Tab();
		tabPilih.setText("Pilih Barang");	
		tabPilih.setClosable(false);
		hbox.getChildren().add(new TabPermintaanItem(Boolean.FALSE));
		tabPilih.setContent(hbox);
		
		tabSimpan=new Tab();
		tabSimpan.setText("Posting Permintaan");
		tabSimpan.setClosable(false);
		vbox2.getChildren().add(new TabPermintaanItemSave(Boolean.FALSE));
		tabSimpan.setContent(vbox2);
		
		tabPane.getTabs().addAll(tabPilih,tabSimpan);
		
		vbox.getChildren().addAll(judul,tabPane);
		
	}
	
	public VBox vPermintaan() {		
		return vbox;
	}

}
