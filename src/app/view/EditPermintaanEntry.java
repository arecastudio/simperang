package app.view;

import app.GlobalUtility;
import app.Main;
import app.model.DataPermintaan;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class EditPermintaanEntry extends VBox {
	private DataPermintaan dp;
	private BorderPane borderPane;
	private Label judul;
	private TabPane tabPane;
	private Tab tab1,tab2;
	
	public EditPermintaanEntry(DataPermintaan dp) {
		setSpacing(5);
		setPadding(new Insets(5, 5, 5, 5));
		setMaxHeight(600);
		setAlignment(Pos.TOP_CENTER);
		getStyleClass().add("box-color");


		this.dp=dp;
		borderPane=Main.borderPane;		
		tabPane=new TabPane();		
		tab1=new Tab();
		tab2=new Tab();
		judul=new Label("Edit Item Permintaan");judul.getStyleClass().addAll("judul");
		
		//GlobalUtility.downloadEditPermintaan(nomor);
		GlobalUtility.edit_nomor_surat=dp.getNomor();
		GlobalUtility.edit_keterangan=dp.getAlasan();
		
		tab1.setText("Pilih Barang");	
		tab1.setClosable(false);
		tab1.setContent(new TabPermintaanItem(Boolean.TRUE));
		
		tab2.setText("Posting Perubahan");	
		tab2.setClosable(false);
		tab2.setContent(new TabPermintaanItemSave(Boolean.TRUE));
		
		tabPane.getTabs().addAll(tab1,tab2);
		
		this.getChildren().addAll(judul,tabPane);
		borderPane.setCenter(this);
	}

}
