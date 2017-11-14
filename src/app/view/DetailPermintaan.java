package app.view;

import app.GlobalUtility;
import app.Main;
import app.model.DataDetailPermintaan;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DetailPermintaan extends VBox {
	private Stage stage;
	private Scene scene;
	private String nomor;
	private TableView table;
	private Button btn;
	private VBox vbox;
	private AnchorPane pane;

	public DetailPermintaan(String nomor) {
		this.nomor=nomor;
	}
	
	public void Tampilkan() {
		Label label=new Label("List Item Permintaan");
		
		table=new TableView();
		table.setEditable(false);
		table.setColumnResizePolicy((param) -> true );
		table.getStyleClass().addAll("table");

        TableColumn namaCol = new TableColumn("Nama Item");
        TableColumn jmlCol = new TableColumn("Jumlah");
        TableColumn satuanCol = new TableColumn("Satuan");
        
        table.getColumns().addAll(namaCol,jmlCol,satuanCol);
        
        namaCol.setCellValueFactory(new PropertyValueFactory<DataDetailPermintaan,String>("nama"));
        jmlCol.setCellValueFactory(new PropertyValueFactory<DataDetailPermintaan,String>("jml"));
        satuanCol.setCellValueFactory(new PropertyValueFactory<DataDetailPermintaan,String>("satuan"));
        
        namaCol.setPrefWidth(230);
        jmlCol.setStyle("-fx-alignment:center-right;");
        satuanCol.setStyle("-fx-alignment:center;");
		
		table.setItems(GlobalUtility.getDataDetailPermintaanByNomor(nomor));
		
		btn=new Button("Tutup");
		btn.setPrefWidth(200);
		btn.setPrefHeight(50);
		//btn.setStyle("-fx-background-color:red;");
		
		this.setSpacing(5);
		this.setPadding(new Insets(5,5,5,5));
		this.setAlignment(Pos.TOP_CENTER);
		
		this.getChildren().addAll(label,table,btn);
		
		/*pane=new AnchorPane();
		pane.getChildren().add(vbox);*/
		
		scene=new Scene(this,400,500);
		
		stage=new Stage();
		//stg.close();
		
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(Main.primaryStage);		
		stage.setResizable(false);
		stage.setTitle("Detail Permintaan: "+nomor);
		
		stage.setScene(scene);
		stage.show();		
		
		btn.setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent event) {
				stage.close();
			}
		});
	}

}
