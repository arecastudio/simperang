package app.view;

import java.util.Optional;

import app.controller.GetCurDate;
import app.controller.HapusDPBInquiry;
import app.model.DataDPB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HapusDPB extends VBox {
	private TableView table;
	private Label ket;
	private String tmpNomor="",sysDate="";
	private Button button_clear,button_del,button_show;
	private HBox hbox1;
	private final DatePicker tglawal = new DatePicker();
	private final DatePicker tglakhir = new DatePicker();
	private final String topdesc="Modul ini berfungsi untuk membatalkan Permintaan Kolektif. Data Permintaan yang termuat di dalamnya akan di turunkan statusnya kembali pada Review SDM.";
	
	public HapusDPB() {		
		init();
		
		hbox1.getChildren().addAll(new Label("Tanggal :"),tglawal,new Label("s/d"),tglakhir,new Label("        "),button_show);
		
		HBox hbox=new HBox(5);
		hbox.setPadding(new Insets(5,5,5,5));
		hbox.getChildren().addAll(button_del,new Label("               "),button_clear);
		
		getChildren().addAll(new LabelJudul("Hapus DPB"),new Label(topdesc),hbox1,table,ket,hbox);
	}
	
	private void init() {
		setSpacing(5);
		setPadding(new Insets(5, 5, 5, 5));
		setMaxHeight(500);
		setAlignment(Pos.TOP_CENTER);
		getStyleClass().add("box-color");
		
		tglawal.setValue(new GetCurDate().getLocalDate());
		tglakhir.setValue(new GetCurDate().getLocalDate());
		
		tglawal.setEditable(false);
		tglakhir.setEditable(false);
		
		hbox1=new HBox(5);
		hbox1.setPadding(new Insets(5,5,5,5));
		hbox1.setAlignment(Pos.CENTER_LEFT);
		
		ket=new Label("Keterangan :");
		
		table=new TabelDPB();
		//table.setItems(new HapusDPBInquiry().listTableDPB());
		
		table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				if(table.getSelectionModel().getSelectedItem()!=null) {
					DataDPB d=(DataDPB)table.getSelectionModel().getSelectedItem();
					button_del.setDisable(false);
					tmpNomor=d.getNomor();
					ket.setText("Keterangan | Nomor Permintaan Kolektif: "+d.getNomor()+" | Dibuat Oleh User: "+d.getNama()+" | Pada Tanggal: "+d.getTgl());
				}
			}
		});
		
		button_show=new Button("Tampilkan");
		button_show.setPrefWidth(150);
		button_show.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//table.setItems(new HapusDPBInquiry().listTableDPB());
				//String t1=tglawal.getValue().toString().trim();
				//String t2=tglakhir.getValue().toString().trim();
				if(tglawal.getValue()!=null && tglakhir.getValue()!=null) {
					table.setItems(new HapusDPBInquiry().listTableDPB(tglawal.getValue().toString(),tglakhir.getValue().toString(),""));
					//System.out.println("Siap");
				}else {
					//System.out.println("Belum");
				}
			}
		});
		
		button_clear=new Button("Clear");
		button_clear.setPrefWidth(150);
		button_clear.setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent event) {
				refresh();				
			}
		});
		
		button_del=new Button("Hapus");
		button_del.setPrefWidth(150);
		button_del.setDisable(true);
		button_del.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(tmpNomor!="") {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Dialog Konfirmasi");
					alert.setHeaderText("Hapus Permintaan Kolektif:\n"+tmpNomor);
					alert.setContentText("Yakin untuk hapus data ini?");

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK){						
						int hapus=new HapusDPBInquiry().Hapus(tmpNomor);
						if(hapus>0){
							refresh();
						}						
					}
				}
			}
		});
	}
	
	private void refresh() {
		//table.setItems(new HapusDPBInquiry().listTableDPB());
		table.getItems().clear();
		ket.setText("Keterangan :");
		tmpNomor="";
		button_del.setDisable(true);
		/*tglawal.setValue(null);
		tglakhir.setValue(null);*/
	}
	
	/*private static final LocalDate getCurDates (){
        String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date , formatter);
        return localDate;
    }*/
}
