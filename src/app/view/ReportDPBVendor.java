package app.view;

import app.GlobalUtility;
import app.controller.*;
import app.model.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.*;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by android on 11/8/17.
 */
public class ReportDPBVendor extends VBox {
    private Connection conn;
    private DBHelper helper;

    private final DatePicker tglawal = new DatePicker();
    private final DatePicker tglakhir = new DatePicker();
    private TableView table;
    private Label ket;
    private TextField text_nomor,text_nota;
    private Button button_show,button_refresh,button_print,button_save,button_del;
    private String tmpNomor="",tmpIdManager="",tmpNamaManager;
    private String tmpVendor,tmpNamaVendor="",tmpAlamatVendor="",tmpPemilikVendor="";
    private String tmpTotal="",tmpPPN="",tmpGrandTotal="",tmpTerbilang="",tmpTanggal="";
    private ToggleGroup group;
    private RadioButton radio1,radio2;
    private TextArea textArea;
    private String surat_permintaan="";
    private StringBuilder stringBuffer;
    private ComboBox<DataVendor> cbxVendor;
    private CheckBox chkPrint,chkEmail;

    private final String KONTEN_SURAT="/app/surat_permintaan.txt";
    private final String SURAT_PERMINTAAN="/app/reports/surat-permintaan.jasper";
    private final String SURAT_PERMINTAAN_LAMPIRAN="/app/reports/surat-permintaan-lampiran.jasper";

    private InputStream input;
    //khusus jasper for fast loading
    /*
    JasperDesign design;
    JasperReport report;*/
    private HBox hbox10;

    private int mailsend=0;
    private int simpan=0;

    private ProgressBar progressBar;

    private Optional<ButtonType> result;

    private String EXPORT_FILE_NAME="";

    private  JasperPrint jasperPrint=null;

    public ReportDPBVendor() {
        Inits();

        GridPane grid=new GridPane();
        grid.setHgap(3);
        grid.setVgap(3);

        HBox hbox2=new HBox(5);
        hbox2.setPadding(new Insets(5,5,0,25));
        hbox2.setAlignment(Pos.BOTTOM_LEFT);

        HBox hbox3=new HBox(5);
        //hbox3.setPadding(new Insets(5,5,5,5));
        hbox3.setAlignment(Pos.CENTER_LEFT);

        HBox hbox1=new HBox(tglawal,new Label("s/d"),tglakhir);
        hbox1.setAlignment(Pos.CENTER_LEFT); hbox1.setSpacing(5);

        grid.add(radio1,0,0);
        grid.add(hbox1,1,0);

        grid.add(radio2,0,1);
        grid.add(text_nomor,1,1);



        //grid.add(new Label("Pihak Pertama"),0,2);
        grid.add(button_show,1,2);

        grid.add(new Label("Vendor"),0,3);
        grid.add(cbxVendor,1,3);

        grid.add(new Label("Nota Dinas No."),0,4);
        grid.add(text_nota,1,4);

        hbox3.getChildren().addAll(grid,hbox2);

        hbox10.getChildren().addAll(ket);

        HBox hbox4=new HBox(5);
        hbox4.setAlignment(Pos.CENTER);
        hbox4.getChildren().addAll(button_refresh,new Separator(Orientation.VERTICAL),button_save,chkPrint,chkEmail);

        getChildren().addAll(new LabelJudul("Rekap Surat Permintaan"),new Separator(Orientation.HORIZONTAL),hbox3,hbox4,table,textArea,hbox10);
    }

    private void Inits(){
        conn=helper.Konek();

        //khusus jasper for fast loading
        /*try {
            input=getClass().getResourceAsStream("/app/reports/surat-permintaan.jrxml");
            design= JRXmlLoader.load(input);
            report= JasperCompileManager.compileReport(design);
        } catch (JRException e) {
            e.printStackTrace();
        }*/

        setSpacing(5);
        setPadding(new Insets(5,5,5,5));
        setAlignment(Pos.TOP_CENTER);
        setMaxHeight(570);
        setMaxWidth(1024);
        getStyleClass().add("box-color");

        hbox10=new HBox(5);

        progressBar=new ProgressBar(0);

        ket=new Label("Keterangan :");

        text_nomor=new TextField();
        text_nomor.setMaxWidth(250);

        text_nota=new TextField();
        text_nota.setMaxWidth(250);

        chkPrint=new CheckBox("Cetak");
        chkPrint.setSelected(true);

        chkEmail=new CheckBox("Kirim e-mail ke Vendor");
        chkEmail.setSelected(true);

        stringBuffer = new StringBuilder();
        input=getClass().getResourceAsStream(KONTEN_SURAT);
        if (input!=null){
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
            try {
                while ((surat_permintaan = bufferedReader.readLine()) != null) {
                    stringBuffer.append(surat_permintaan).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        textArea=new TextArea(stringBuffer.toString());

        group=new ToggleGroup();
        radio1=new RadioButton("Tgl. Surat");
        radio1.setToggleGroup(group);
        radio1.setSelected(true);
        radio2=new RadioButton("No. Surat");
        radio2.setToggleGroup(group);

        tglawal.setEditable(false);
        tglawal.setValue(new GetCurDate().getLocalDate());
        tglawal.setOnAction(new EventHandler() {
            public void handle(Event t) {
                LocalDate date = tglawal.getValue();
                //System.err.println("Tgl Awal: " + date);
            }
        });

        tglakhir.setEditable(false);
        tglakhir.setValue(new GetCurDate().getLocalDate());
        tglakhir.setOnAction(new EventHandler() {
            public void handle(Event t) {
                LocalDate date = tglakhir.getValue();
                //System.err.println("Tgl Akhir: " + date);
            }
        });

        table=new TabelDPB();
        table.setPrefHeight(200);
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (table.getSelectionModel().getSelectedItem()!=null){
                    DataDPB data=(DataDPB) table.getSelectionModel().getSelectedItem();
                    tmpNomor=data.getNomor();
                    ket.setText("Keterangan : Surat yang akan dicetak --> No. "+tmpNomor);
                    //button_print.setDisable(false);
                    DataTerbilang dt=new DPBKolektifModify().GetDPBTerbilang(data.getNomor());
                    System.out.println(dt.getTerbilang());

                    tmpTanggal=dt.getTanggal();
                    tmpTotal=dt.getTotal()+"";
                    tmpPPN=dt.getPpn()+"";
                    tmpGrandTotal=dt.getGrand_total()+"";
                    tmpTerbilang=dt.getTerbilang();
                }
            }
        });

        button_refresh=new Button("Refresh");
        button_refresh.setPrefWidth(120);
        button_refresh.setOnAction(event -> {Refresh();});

        button_show=new Button("Tampilkan");
        button_show.setPrefWidth(120);
        button_show.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                table.getItems().clear();
                if(radio1.isSelected() && tglawal.getValue()!=null && tglakhir.getValue()!=null){
                    //cari per tanggal
                    table.setItems(GlobalUtility.getAllDataDPBByIndikator("",tglawal.getValue().toString(),tglakhir.getValue().toString()));
                }else{
                    //cari per nomor
                    if(text_nomor.getText().toString().trim().length()>0) table.setItems(GlobalUtility.getAllDataDPBByIndikator(text_nomor.getText().toString().trim(),"",""));
                }
            }
        });

        //cbxVendor=new ComboBox(new DivisiModify().GetTableItems());
        cbxVendor=new ComboBox<>(new VendorModify().GetTableItems());
        cbxVendor.setPrefWidth(200);
        cbxVendor.setConverter(new StringConverter<DataVendor>() {

            @Override
            public String toString(DataVendor object) {
                return object.getNama();
            }

            @Override
            public DataVendor fromString(String string) {
                return cbxVendor.getItems().stream().filter(ap ->
                        ap.getNama().equals(string)).findFirst().orElse(null);
            }
        });

        cbxVendor.valueProperty().addListener((obs, oldval, newval) -> {
            if(newval != null) {
                //label.setText("NIK : "+newval.getNik());
                //cbxVendor.setStyle(newval.getId());
                //System.out.println("Vendor: " + newval.getNama() + ". Id: " + newval.getId());
                tmpVendor=newval.getId();
                tmpNamaVendor=newval.getNama();
                tmpAlamatVendor=newval.getAlamat();
                tmpPemilikVendor=newval.getPemilik();
            }
        });

        //------------------------------------------------------------

        button_save=new Button("Simpan");
        button_save.setPrefWidth(120);
        button_save.setOnAction((ActionEvent event) -> {

            if (textArea.getText().toString().trim().length()>0  && text_nota.getText().toString().trim().length()>0 && tmpNomor!="" && tmpNamaVendor!="" && tmpAlamatVendor!="" && tmpPemilikVendor!="") {

                //========================================================
                //TASK SIMPAN
                Task<Void>task_simpan=new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        DataNotaDPB d=new DataNotaDPB();
                        d.setId_vendor(Integer.parseInt(tmpVendor));
                        d.setNomor(text_nota.getText().toString().trim());
                        d.setNomor_dpb_kolektif(tmpNomor);
                        d.setMail_send(mailsend);
                        d.setKonten_surat(textArea.getText().trim());

                        simpan=new NotaModify().Simpan(d);
                        return null;
                    }
                };
                task_simpan.setOnSucceeded(event1 -> {
                    hbox10.getChildren().clear();
                    hbox10.getChildren().addAll(ket);

                    if (jasperPrint!=null){
                        JasperViewer jv=new JasperViewer(jasperPrint,false);
                        jv.setTitle("Rekap Surat Permintaan");
                        jv.setVisible(true);
                    }

                    if (simpan>0){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Dialog Informasi");
                        alert.setHeaderText("Proses Berhasil");
                        alert.setContentText("Data telah tersimpan.");
                        alert.showAndWait();
                    }else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Dialog Informasi");
                        alert.setHeaderText("Proses Gagal");
                        alert.setContentText("Terjadi kesalahan proses simpan.");
                        alert.showAndWait();
                    }
                    Refresh();
                });

                //========================================================
                //TASK KIRIM EMAIL
                Task<Void>task_mail=new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        DataVendor v=new VendorModify().GetVenderoById(tmpVendor);
                        if (chkEmail.isSelected()){
                            if (GlobalUtility.getInetStat()==true){
                                mailsend=new EmailController().EmailVendor(v.getEmail().toString(),EXPORT_FILE_NAME);
                            }
                        }
                        return null;
                    }
                };
                task_mail.setOnSucceeded(event1 -> {
                    ket.setText("Keterangan: Tunggu hingga proses selesai...");
                    Platform.runLater(()->{
                        Thread r=new Thread(task_simpan);
                        r.setDaemon(true);
                        r.start();
                    });

                });
                //========================================================
                //TASK CETAK
                Task<Void>task_cetak=new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        if (chkPrint.isSelected()) {
                            input = getClass().getResourceAsStream(SURAT_PERMINTAAN);
                            String lampiran=getClass().getResource(SURAT_PERMINTAAN_LAMPIRAN).toString();
                            lampiran=lampiran.substring((Integer)"file:".length(),lampiran.length()-(Integer)"surat-permintaan-lampiran.jasper".length());
                            System.out.println("File lampiran: "+lampiran.toString());
                            try {
                                JasperReport report = (JasperReport) JRLoader.loadObject(input);

                                Map<String, Object> params = new HashMap<String, Object>();
                                params.put("nomor_surat", tmpNomor);
                                params.put("nota_dinas", text_nota.getText().toString().trim());
                                params.put("konten_surat", textArea.getText().toString().trim());
                                params.put("nama_vendor", tmpNamaVendor);
                                params.put("alamat_vendor", tmpAlamatVendor);
                                params.put("pemilik_vendor", tmpPemilikVendor);
                                //params.put("nama_manager", tmpNamaManager);

                                //params.put("tanggal_posting",tmpTanggal);
                                params.put("total", tmpTotal);
                                params.put("ppn", tmpPPN);
                                params.put("grand_total", tmpGrandTotal);
                                params.put("terbilang", tmpTerbilang);

                                params.put("SUBREPORT_DIR",lampiran.toString());

                                jasperPrint = JasperFillManager.fillReport(report, params, conn);
                                jasperPrint.setName("Rekap Surat Permintaan");

                                String path=System.getProperty("user.home");
                                if (path.substring(path.length()-1,path.length())!="/")path+="/";

                                EXPORT_FILE_NAME=path+"attach-surat-pengadaan-"+new GetCurDate().getTanggal()+".pdf";

                                JasperExportManager.exportReportToPdfFile(jasperPrint,EXPORT_FILE_NAME);

                                /*JasperViewer jv=new JasperViewer(jasperPrint,false);
                                jv.setTitle("Rekap Surat Permintaan");
                                jv.setVisible(true);*/


                            } catch (JRException e) {
                                e.printStackTrace();
                            }
                        }

                        return null;
                    }
                };
                task_cetak.setOnSucceeded(event1 -> {
                    ket.setText("Keterangan: Tunggu hingga proses selesai...");
                    Platform.runLater(()->{
                        Thread t=new Thread(task_mail);
                        t.setDaemon(true);
                        t.start();
                    });
                });
                //========================================================

                hbox10.getChildren().clear();
                hbox10.getChildren().addAll(progressBar,new Separator(Orientation.VERTICAL),ket);
                progressBar.setProgress(-1.0);
                ket.setText("Keterangan: Tunggu hingga proses selesai...");
                Thread e=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Thread thread=new Thread(task_cetak);
                        thread.setDaemon(true);
                        thread.start();
                    }
                });
                e.setDaemon(true);
                e.start();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Dialog Informasi");
                alert.setHeaderText("Proses Gagal.");
                alert.setContentText("Lengkapi opsi pilihan pada form ini.");
                alert.showAndWait();
            }
        });


        button_del=new Button("Hapus");

    }


    private void Refresh(){
        //button_print.setDisable(true);
        textArea.setText(stringBuffer.toString());
        text_nota.setText("");

        tmpNomor="";
        tmpIdManager="";
        tmpNamaManager="";
        tmpVendor="";
        tmpNamaVendor="";
        tmpAlamatVendor="";
        tmpPemilikVendor="";

        tmpTotal="";
        tmpPPN="";
        tmpGrandTotal="";
        tmpTerbilang="";
        tmpTanggal="";

        progressBar.setProgress(0);

        simpan=0;
        mailsend=0;

        hbox10.getChildren().clear();
        hbox10.getChildren().addAll(ket);

        cbxVendor.getSelectionModel().clearSelection();

        table.getItems().clear();

        ket.setText("Keterangan :");
    }
}