package app.view;

import java.net.URL;
import java.util.ResourceBundle;

import app.GlobalUtility;
import app.Main;
import app.controller.GetDataBarangs;
import app.model.DataBarang;
import app.model.DataBarangDipilih;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class TabPermintaanItem extends Pane {
	private HBox hbox1,hbox2;
	private VBox vbox1,vroot;
	private Label label;
	private Button button_cari,button_refresh;
	private TextField text_cari;
	private TableView table;
	private GridPane grid;
	private ObservableList<DataBarang> dataBarang;
	//public static ObservableList<DataBarang> list;
	private Boolean isEdit;
	
	public TabPermintaanItem(Boolean isEdit) {
		this.isEdit=isEdit;

		//dataBarang=GlobalUtility.getBarangs();
		dataBarang=new GetDataBarangs().Ambil(null);

		//this.setPadding(new Insets(5, 5, 5, 5));
		vroot=new VBox(5);vroot.setPadding(new Insets(5, 5, 5, 5));
		hbox1=new HBox(5);hbox1.setPadding(new Insets(5, 5, 5, 5));hbox1.setAlignment(Pos.CENTER_LEFT);
		hbox2=new HBox(5);hbox2.setPadding(new Insets(5, 5, 5, 5));hbox2.setAlignment(Pos.CENTER_LEFT);
		vbox1=new VBox(5);vbox1.setPadding(new Insets(5, 5, 5, 5));
		
		grid=new GridPane();
		grid.setHgap(3);
		grid.setVgap(3);

		label=new Label("Cari Barang: ");

		button_cari=new Button("Cari");
		button_cari.setPrefWidth(100);

		text_cari=new TextField();
		text_cari.setPrefWidth(400);

		button_refresh=new Button("Refresh");
		button_refresh.setPrefWidth(100);
		button_refresh.setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent event) {
				refresh();
			}
		});
		

		
		//list=FXCollections.observableArrayList();
		
		table=new TableView();//table.setMinWidth(this.getWidth()-5);
		table.setEditable(false);
		table.setColumnResizePolicy((param) -> true );
		table.getStyleClass().addAll("table");
		 
        TableColumn idCol = new TableColumn("ID");
        TableColumn namaCol = new TableColumn("Nama");
        TableColumn satuanCol = new TableColumn("Satuan");
        TableColumn keteranganCol = new TableColumn("Keterangan");
        
        table.getColumns().addAll(idCol, namaCol, satuanCol,keteranganCol);
		
		hbox1.getChildren().addAll(label,text_cari,button_cari,button_refresh);
		
		//ScrollPane sideBarScroller = new ScrollPane(vroot);
		//sideBarScroller.setFitToWidth(true);
		
		Tampilkan(dataBarang);
		
		/*for(int i=0;i<dataBarang.size();i++) {

		}*/
		
		//hbox2.getChildren().add(table);
		//vbox1.getChildren().add(hbox2);
		
		ScrollPane sp = new ScrollPane();
		//sp.setFitToWidth(true);
		sp.setPrefWidth(Main.primaryStage.getWidth()-10);
		sp.setPrefHeight(Main.primaryStage.getHeight()-230);
		sp.setContent(grid);
		
		vroot.getChildren().addAll(hbox1,sp);
		this.getChildren().add(vroot);


		button_cari.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (text_cari.getText()!=null && (Integer)text_cari.getText().toString().trim().length()>0){
					dataBarang=new GetDataBarangs().Ambil(text_cari.getText().toString().trim());
					Tampilkan(dataBarang);
				}
			}
		});


		text_cari.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)){
					if (text_cari.getText()!=null && (Integer)text_cari.getText().toString().trim().length()>0){
						dataBarang=new GetDataBarangs().Ambil(text_cari.getText().toString().trim());
						Tampilkan(dataBarang);
					}
				}
			}
		});
	}

	private void Tampilkan(ObservableList<DataBarang> dbr) {
		int j=0,k=0;
		//int c=0;
		grid.getChildren().clear();
		for (DataBarang db : dbr) {
			///System.out.println(db.getNama()+"");
			Button bt=new Button(""+ db.getNama());
			bt.getStyleClass().addAll("button_barang");
			bt.setId(""+db.getId());
			bt.setPrefHeight(100);
			bt.setPrefWidth(300);
			if(j>3) {j=0;k++;}
			grid.add(bt, j, k);
			j++;

			bt.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					int dpt=0;
					if(isEdit.equals(Boolean.FALSE)) {
						for(DataBarangDipilih dbp:GlobalUtility.dataBarangDipilih) {
							if(dbp.getId().equals(db.getId())) dpt=1;
						}
						if(dpt==0) {
							GlobalUtility.dataBarangDipilih.add(new DataBarangDipilih(db.getId(), db.getNama(),"1", db.getSatuan(), db.getKeterangan(),db.getHarga()));
							Toast.makeText(Main.primaryStage, "Menambahkan "+db.getNama(), 550, 150, 150,Color.GREEN);
							//System.out.println(db.getHarga());
						}else {
							Toast.makeText(Main.primaryStage, "Data sudah ada !", 550, 150, 150,Color.RED);
						}
					}else {
						for(DataBarangDipilih dbp:GlobalUtility.dataBarangDipilihEdit) {
							if(dbp.getId().equals(db.getId())) dpt=1;
						}
						if(dpt==0) {
							GlobalUtility.dataBarangDipilihEdit.add(new DataBarangDipilih(db.getId(), db.getNama(),"1", db.getSatuan(), db.getKeterangan(),db.getHarga()));
							Toast.makeText(Main.primaryStage, "Menambahkan "+db.getNama(), 550, 150, 150,Color.GREEN);
							//System.out.println(db.getHarga());
						}else {
							Toast.makeText(Main.primaryStage, "Data sudah ada !", 550, 150, 150,Color.RED);
						}
					}
				}
			});
		}
	}
	
	private void refresh() {
		text_cari.setText("");
		text_cari.requestFocus();

		dataBarang=new GetDataBarangs().Ambil(null);
		Tampilkan(dataBarang);
	}

}
