package app.view;

import app.GlobalUtility;
import app.Main;
import app.model.DataPermintaan;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class EditPermintaan extends VBox {
	private Label judul;
	private TableView table;
	private Button button;
	private Separator hsep1;
	private final String deskripsi="Modul ini menampilkan Daftar Permintaan Barang dari masing-masing Divisi dengan Status: Review Divisi / belum diposting ke SDM.";

	public EditPermintaan() {		
		judul=new Label("Edit Permintaan");judul.getStyleClass().addAll("judul");
		button=new Button("test");
		//table=new TableView();

		hsep1=new Separator(Orientation.VERTICAL);
		hsep1.setValignment(VPos.CENTER);
		//hsep1.setStyle("-fx-background-color:#000;");
		hsep1.setOpacity(1);
		hsep1.setPrefWidth(Double.MAX_VALUE);
		
		/*table.setEditable(false);
		table.setColumnResizePolicy((param) -> true );
		table.getStyleClass().addAll("table");
		 
        TableColumn nomorCol = new TableColumn("Nomor");
        TableColumn keteranganCol = new TableColumn("Keterangan");
        TableColumn statusCol = new TableColumn("Status");
        TableColumn TanggalCol = new TableColumn("Tanggal");
        TableColumn divisiCol = new TableColumn("Divisi");
        
        table.getColumns().addAll(nomorCol, keteranganCol,statusCol, TanggalCol,divisiCol);
        
        nomorCol.setCellValueFactory(new PropertyValueFactory<DataPermintaan,String>("nomor"));
        keteranganCol.setCellValueFactory(new PropertyValueFactory<DataPermintaan,String>("alasan"));
        statusCol.setCellValueFactory(new PropertyValueFactory<DataPermintaan,String>("status"));
        TanggalCol.setCellValueFactory(new PropertyValueFactory<DataPermintaan,String>("tgl"));
        divisiCol.setCellValueFactory(new PropertyValueFactory<DataPermintaan,String>("nik"));
        
        nomorCol.setPrefWidth(200);
        keteranganCol.setPrefWidth(450);
        divisiCol.setPrefWidth(200);*/
		table=new TabelPermintaan();
        Boolean bol=Boolean.FALSE;
        if(GlobalUtility.getUser_logged_in().getId_role().equals("1"))bol=Boolean.TRUE;//jika user adalah admin
        //System.out.println(""+GlobalUtility.getUser_logged_in().getId_role());
		table.setItems(GlobalUtility.getDataPermintaan(bol,0));      
        table.setPrefWidth(Main.primaryStage.getWidth()-10);
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				DataPermintaan dp=(DataPermintaan)table.getSelectionModel().getSelectedItem();
				GlobalUtility.downloadEditPermintaan(dp.getNomor());
				new EditPermintaanEntry(dp);				
			}
		});
        
        
        button.setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent event) {
				//			
			}
		});

		setSpacing(5);
		setPadding(new Insets(5, 5, 5, 5));
		setMaxHeight(500);
		setMaxWidth(1024);
		setAlignment(Pos.TOP_CENTER);
		getStyleClass().add("box-color");

		this.getChildren().addAll(judul,hsep1,new Label(deskripsi),table);//tambahkan buton untuk test
	}

}
