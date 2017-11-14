package app.view;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


import app.GlobalUtility;
import app.controller.DBHelper;
import app.controller.DivisiModify;
import app.controller.GetCurDate;
import app.controller.PermintaanSimpan;
import app.model.DataDivisi;
import app.model.DataPermintaan;
import app.model.DataPermintaanReport;
import app.model.DataStatusPosting;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

//array("parameter1"=>$nomor,"parameter2"=>$ket,"parameter3"=>$tgl,"parameter4"=>$div,"parameter5"=>$tgl_cetak,"parameter6"=>$nik1,"parameter7"=>$nama1,"parameter8"=>$nik2,"parameter9"=>$nama2)

public class ReportPermintaan extends VBox {
	private HBox hbox1,hbox2,hbox3,hbox10;
	private VBox vbox1;
	private GridPane grid;
	private TableView table;
	private Label ket;
	private TextField text_nomor;
	private Button button_show,button_clear,button_print,button_detail;
	private final DatePicker tglawal = new DatePicker();
	private final DatePicker tglakhir = new DatePicker();
	private ComboBox<DataDivisi> cbxDivisi;
	private ComboBox<DataStatusPosting> cbxStatus;
	private String tmpDivisi,tmpStatus;
	private String tmpNomor="";
	private final String tmp_desc="Cari pemintaan berdasarkan Tanggal atau Nomor Surat yang dikeluarkan oleh Divisi terkait.";

	private ToggleGroup group;
	private RadioButton radio1,radio2;
	private Boolean isAdmin;

	private Connection conn = null;
	private ResultSet rs = null;
	private PreparedStatement pst = null;
	private DBHelper helper;
	private String sql;

	private ProgressBar progressBar;

	private final String REPORT_PERMINTAAN="/app/reports/report-permintaan.jasper";
	private final String REPORT_PERMINTAAN_DETAIL="/app/reports/report-permintaan-detail.jasper";


	//panduan jasper for javafx
	//https://www.youtube.com/watch?v=AfC6MlWrXqY
	//http://www.javaquery.com/2015/11/how-to-fill-jasper-table-using.html

	/*private JRBeanCollectionDataSource jrBean;
	private JasperPrint jasperPrint;
	private JasperExportManager jasperExportManager;
	private JasperViewer jasperViewer;*/

	public ReportPermintaan() {
		init();

		hbox1.getChildren().addAll(tglawal,new Label(" s/d "),tglakhir);
		hbox2.getChildren().addAll(button_show,button_clear,button_detail,button_print);

		grid.add(radio1,0,0);
		grid.add(hbox1,1,0);
		grid.add(radio2,0,1);
		grid.add(text_nomor,1,1);

		//hbox3.getChildren().addAll(grid,hbox2);
		hbox10.getChildren().addAll(ket);

		this.getChildren().addAll(new LabelJudul("Laporan Permintaan"),new Separator(Orientation.HORIZONTAL),new Label(tmp_desc),grid,new Separator(Orientation.HORIZONTAL),hbox2,table,hbox10);
	}

	private void init() {
		conn=helper.Konek();

		setSpacing(5);
		setPadding(new Insets(5, 5, 5, 5));
		setMaxHeight(500);
		setMaxWidth(1024);
		setAlignment(Pos.TOP_CENTER);
		getStyleClass().add("box-color");

		isAdmin=Boolean.FALSE;
		if (GlobalUtility.getUser_logged_in().getId_role().equals("1"))isAdmin=Boolean.TRUE;

		hbox10=new HBox(5);
		progressBar=new ProgressBar(0);

		ket=new Label("Keterangan :");
		text_nomor=new TextField();
		text_nomor.setMaxWidth(200);

		group=new ToggleGroup();
		radio1=new RadioButton("Tgl. Surat");
		radio1.setToggleGroup(group);
		radio1.setSelected(true);
		radio2=new RadioButton("No. Surat");
		radio2.setToggleGroup(group);
		
		button_show=new Button("Tampilkan");
		button_show.setPrefWidth(120);
		button_show.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				table.getItems().clear();
				if(radio1.isSelected() && tglawal.getValue()!=null && tglakhir.getValue()!=null){
					//cari per tanggal
					table.setItems(GlobalUtility.getAllDataPermintaanByIndikator(isAdmin,"",tglawal.getValue().toString(),tglakhir.getValue().toString()));
				}else{
					//cari per nomor
					if(text_nomor.getText().toString().trim().length()>0) table.setItems(GlobalUtility.getAllDataPermintaanByIndikator(isAdmin,text_nomor.getText().toString().trim(),"",""));
				}
			}
		});

		button_detail=new Button("Detail");
		button_detail.setPrefWidth(120);
		button_detail.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Klik button Detail.");
				if(tmpNomor!=""){
					new DetailPermintaan(tmpNomor).Tampilkan();
				}
			}
		});
		
		button_clear=new Button("Clear");
		button_clear.setPrefWidth(120);
		button_clear.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				refresh();				
			}
		});
		
		button_print=new Button("Cetak");
		button_print.setPrefWidth(120);
		button_print.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String tanggal=new GetCurDate().getTanggal().toString();
				if (tmpNomor!=""){
					Task<Void>task=new Task<Void>() {
						@Override
						protected Void call() throws Exception {
							DataPermintaanReport dpr=new PermintaanSimpan().getPermintaanReport(tmpNomor);

							InputStream input=getClass().getResourceAsStream(REPORT_PERMINTAAN);
							String lampiran=getClass().getResource(REPORT_PERMINTAAN_DETAIL).toString();
							lampiran=lampiran.substring((Integer)"file:".length(),lampiran.length()-(Integer)"report-permintaan-detail.jasper".length());
							System.out.println("File lampiran: "+lampiran.toString());

							try {
								JasperReport report=(JasperReport) JRLoader.loadObject(input);
								//Map
								Map<String, Object> params = new HashMap<String, Object>();
								params.put("nomor",tmpNomor);
								params.put("SUBREPORT_DIR",lampiran.toString());

								JasperPrint jasperPrint=JasperFillManager.fillReport(report,params,conn);
								jasperPrint.setName("Laporan");

								JasperViewer jv=new JasperViewer(jasperPrint,false);
								jv.setTitle("Laporan Permintaan");
								jv.setVisible(true);
							} catch (JRException e) {
								e.printStackTrace();
							}
							return null;
						}
					};
					task.setOnSucceeded(e->{
						/*Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("Dialog Informasi");
						alert.setHeaderText("Proses Selesai.");
						alert.setContentText("Proses cetak telah dilakukan.");
						alert.showAndWait();*/
						refresh();
					});

					hbox10.getChildren().clear();
					hbox10.getChildren().addAll(progressBar,new Separator(Orientation.VERTICAL),ket);
					progressBar.setProgress(-1.0);
					ket.setText("Keterangan: Tunggu hingga proses selesai...");

					Thread thread=new Thread(task);
					thread.setDaemon(true);
					thread.start();
				}
			}
		});
		button_print.setDisable(true);
		
		hbox1=new HBox(5);
		hbox1.setPadding(new Insets(5,5,5,5));
		hbox1.setAlignment(Pos.CENTER);
		
		hbox2=new HBox(5);
		//hbox2.setPadding(new Insets(5,5,0,25));
		hbox2.setAlignment(Pos.CENTER);
		
		hbox3=new HBox(5);
		//hbox3.setPadding(new Insets(5,5,5,5));
		hbox3.setAlignment(Pos.CENTER_LEFT);

		vbox1=new VBox(5);
		vbox1.setPadding(new Insets(5,5,5,5));
		vbox1.setAlignment(Pos.CENTER);
		
		grid=new GridPane();
		grid.setHgap(3);
		grid.setVgap(3);

		tglawal.setEditable(false);
		tglawal.setValue(new GetCurDate().getLocalDate());
		tglawal.setOnAction(new EventHandler() {
		     public void handle(Event t) {
		         LocalDate date = tglawal.getValue();
		         System.err.println("Tgl Awal: " + date);
		     }
		 });

		tglakhir.setEditable(false);
		tglakhir.setValue(new GetCurDate().getLocalDate());
		tglakhir.setOnAction(new EventHandler() {
		     public void handle(Event t) {
		         LocalDate date = tglakhir.getValue();
		         System.err.println("Tgl Akhir: " + date);
		     }
		 });
		
		cbxStatus=new ComboBox<DataStatusPosting>();
		cbxStatus.setItems(FXCollections.observableArrayList(
				new DataStatusPosting("","--Semua--"),
				new DataStatusPosting("0",GlobalUtility.getStatusPosting("0")),
				new DataStatusPosting("1",GlobalUtility.getStatusPosting("1")),
				new DataStatusPosting("2",GlobalUtility.getStatusPosting("2")),
				new DataStatusPosting("3",GlobalUtility.getStatusPosting("3"))
				));
		
		cbxStatus.setConverter(new StringConverter<DataStatusPosting>() {			
			@Override
			public String toString(DataStatusPosting object) {				
				return object.getNama();
			}			
			@Override
			public DataStatusPosting fromString(String string) {
				// TODO Auto-generated method stub
				return cbxStatus.getItems().stream().filter(ap -> 
	            ap.getNama().equals(string)).findFirst().orElse(null);
			}
		});
		
		cbxStatus.valueProperty().addListener((obs, oldval, newval) -> {
		    if(newval != null) {
		        //label.setText("NIK : "+newval.getNik());
		        //cbxStatus.setStyle(newval.getId());		        
		        //System.out.println("Status: " + newval.getNama() + ". Id: " + newval.getId());
		        tmpStatus=newval.getId();
		    }
		});
		//--------------------------------------------------
		
		cbxDivisi=new ComboBox(new DivisiModify().GetTableItems());
		//cbxDivisi.setItems(FXCollections.observableArrayList(new DataDivisi("", "Semua", "", "")));
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
		        //cbxDivisi.setStyle(newval.getId());		        
		        //System.out.println("Divisi: " + newval.getNama() + ". Id: " + cbxDivisi.getStyle().trim());
		        tmpDivisi=newval.getId();
		    }
		});

		table=new TabelPermintaan();
		table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				if (table.getSelectionModel().getSelectedItem()!=null){
					DataPermintaan data=(DataPermintaan)table.getSelectionModel().getSelectedItem();
					tmpNomor=data.getNomor();
					ket.setText("Keterangan : "+tmpNomor);
					button_print.setDisable(false);
				}
			}
		});

	}
	
	private void refresh() {
		cbxDivisi.getSelectionModel().clearSelection();
		cbxStatus.getSelectionModel().clearSelection();
		tmpDivisi="";tmpStatus="";
		table.getItems().clear();
		text_nomor.setText("");
		tglawal.setValue(null);
		tglakhir.setValue(null);
		tmpNomor="";
		ket.setText("Keterangan :");
		button_print.setDisable(true);

		tglawal.setValue(new GetCurDate().getLocalDate());
		tglakhir.setValue(new GetCurDate().getLocalDate());

		hbox10.getChildren().clear();
		hbox10.getChildren().addAll(ket);
	}

}
