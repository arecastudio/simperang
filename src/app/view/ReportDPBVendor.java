package app.view;

import app.GlobalUtility;
import app.Main;
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
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
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
    private ComboBox<DataUsers> cbxManager;
    private CheckBox chkPrint,chkEmail;

    private final String KONTEN_SURAT="/app/surat_permintaan.txt";
    private final String SURAT_PERMINTAAN="/app/reports/surat-permintaan.jasper";

    private InputStream input;
    //khusus jasper for fast loading
    /*
    JasperDesign design;
    JasperReport report;*/
    private HBox hbox10;

    private int mailsend=0;
    private int simpan=0;

    private ProgressBar progressBar;

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

        grid.add(new Label("Pihak Pertama"),0,3);
        grid.add(cbxManager,1,3);

        grid.add(new Label("Pihak Ke Dua"),0,4);
        grid.add(cbxVendor,1,4);

        grid.add(new Label("Nota Dinas No."),0,5);
        grid.add(text_nota,1,5);

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
        cbxManager=new ComboBox<>(new UserModify().GetTableItems());
        cbxManager.setPrefWidth(200);
        cbxManager.setConverter(new StringConverter<DataUsers>() {

            @Override
            public String toString(DataUsers object) {
                return object.getNama();
            }

            @Override
            public DataUsers fromString(String string) {
                // TODO Auto-generated method stub
                return cbxManager.getItems().stream().filter(ap ->
                        ap.getNama().equals(string)).findFirst().orElse(null);
            }
        });

        cbxManager.valueProperty().addListener((obs, oldval, newval) -> {
            if(newval != null) {
                //label.setText("NIK : "+newval.getNik());
                cbxManager.setStyle(newval.getNik());
                tmpIdManager=newval.getNik();
                tmpNamaManager=newval.getNama();
                //System.out.println("manager: " + newval.getNama() + ". nik: " + tmpIdManager);
            }
        });


        button_print=new Button("Cetak");
        //button_print.setDisable(true);
        button_print.setPrefWidth(120);
        button_print.setOnAction(event -> {
            //tmpNomor="6666";
            if (textArea.getText().toString().trim().length()>0  && text_nota.getText().toString().trim().length()>0 && tmpNomor!="" && tmpNamaVendor!="" && tmpAlamatVendor!="" && tmpPemilikVendor!="" && tmpNamaManager!=""){
                //InputStream
                //input=getClass().getResourceAsStream("/app/reports/surat-permintaan.jrxml");
                input=getClass().getResourceAsStream(SURAT_PERMINTAAN);

                try {
                    //JasperDesign design= JRXmlLoader.load(input);
                    //JasperReport report= JasperCompileManager.compileReport(design);
                    JasperReport report=(JasperReport) JRLoader.loadObject(input);

                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("nomor_surat",tmpNomor);
                    params.put("nota_dinas",text_nota.getText().toString().trim());
                    params.put("konten_surat",textArea.getText().toString().trim());
                    params.put("nama_vendor",tmpNamaVendor);
                    params.put("alamat_vendor",tmpAlamatVendor);
                    params.put("pemilik_vendor",tmpPemilikVendor);
                    params.put("nama_manager",tmpNamaManager);

                    /*
                    * tmpTotal="";
                    tmpPPN="";
                    tmpGrandTotal="";
                    tmpTerbilang="";
                    * */

                    //params.put("tanggal_posting",tmpTanggal);
                    params.put("total",tmpTotal);
                    params.put("ppn",tmpPPN);
                    params.put("grand_total",tmpGrandTotal);
                    params.put("terbilang",tmpTerbilang);

                    JasperPrint jasperPrint= JasperFillManager.fillReport(report,params,conn);
                    jasperPrint.setName("Rekap Surat Permintaan");

                    JasperViewer jv=new JasperViewer(jasperPrint,false);
                    jv.setTitle("Rekap Surat Permintaan");
                    jv.setVisible(true);
                } catch (JRException e) {
                    e.printStackTrace();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Dialog Informasi");
                alert.setHeaderText("Print Preview Gagal");
                alert.setContentText("Lengkapi opsi pilihan pada form ini.");
                alert.show();
            }
        });

        button_save=new Button("Simpan");
        button_save.setPrefWidth(120);
        button_save.setOnAction((ActionEvent event) -> {
            //if (1==1){
            text_nota.setText("df");
            if (textArea.getText().toString().trim().length()>0  && text_nota.getText().toString().trim().length()>0 && tmpNomor!="" && tmpNamaVendor!="" && tmpAlamatVendor!="" && tmpPemilikVendor!="" && tmpNamaManager!="") {
                //========================================================
                Task<Void>task_cetak=new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        if (chkPrint.isSelected()) {
                            input = getClass().getResourceAsStream(SURAT_PERMINTAAN);
                            try {
                                JasperReport report = (JasperReport) JRLoader.loadObject(input);

                                Map<String, Object> params = new HashMap<String, Object>();
                                params.put("nomor_surat", tmpNomor);
                                params.put("nota_dinas", text_nota.getText().toString().trim());
                                params.put("konten_surat", textArea.getText().toString().trim());
                                params.put("nama_vendor", tmpNamaVendor);
                                params.put("alamat_vendor", tmpAlamatVendor);
                                params.put("pemilik_vendor", tmpPemilikVendor);
                                params.put("nama_manager", tmpNamaManager);

                                //params.put("tanggal_posting",tmpTanggal);
                                params.put("total", tmpTotal);
                                params.put("ppn", tmpPPN);
                                params.put("grand_total", tmpGrandTotal);
                                params.put("terbilang", tmpTerbilang);

                                JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, conn);
                                jasperPrint.setName("Rekap Surat Permintaan");

                                JasperViewer jv=new JasperViewer(jasperPrint,false);
                                jv.setTitle("Rekap Surat Permintaan");
                                jv.setVisible(true);

                            } catch (JRException e) {
                                e.printStackTrace();
                            }
                        }

                        return null;
                    }
                };
                task_cetak.setOnSucceeded(event1 -> {
                    Refresh();
                    ket.setText("Keterangan: Proses Cetak berhasil.");
                    hbox10.getChildren().clear();
                    hbox10.getChildren().addAll(ket);
                });
                //========================================================
                Task<Void>task_simpan=new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        DataNota d=new DataNota();
                        d.setId_vendor(Integer.parseInt(tmpVendor));
                        d.setNomor(text_nota.getText().toString().trim());
                        d.setNomor_dpb_kolektif(tmpNomor);
                        d.setMail_send(mailsend);

                        simpan=new NotaModify().Simpan(d);
                        return null;
                    }
                };
                task_simpan.setOnSucceeded(event1 -> {
                    ket.setText("Keterangan: Proses Simpan Data berhasil.");
                    Platform.runLater(()->{
                        if (chkPrint.isSelected()){
                            hbox10.getChildren().clear();
                            hbox10.getChildren().addAll(progressBar,new Separator(Orientation.VERTICAL),ket);
                            progressBar.setProgress(-1.0);
                            ket.setText("Keterangan: Proses Simpan Data berhasil. Lanjut ke Proses Cetak.");
                            Thread r=new Thread(task_cetak);
                            r.setDaemon(true);
                            r.start();
                        }else {
                            Refresh();
                            ket.setText("Keterangan: Proses Simpan Data berhasil.");
                            hbox10.getChildren().clear();
                            hbox10.getChildren().addAll(ket);
                        }
                    });

                    Platform.runLater(()->{
                        if (simpan>0){
                            hbox10.getChildren().clear();
                            hbox10.getChildren().addAll(ket);
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Dialog Informasi");
                            alert.setHeaderText("Proses Berhasil");
                            alert.setContentText("Data telah tersimpan.");
                            alert.show();
                            //Refresh();
                        }
                    });
                });
                //========================================================
                Task<Void>task_mail=new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        DataVendor v=new VendorModify().GetVenderoById(tmpVendor);
                        if (chkEmail.isSelected()){
                            if (GlobalUtility.getInetStat()==true){
                                mailsend=new EmailController().EmailVendor(v.getEmail().toString());
                            }
                        }
                        return null;
                    }
                };
                task_mail.setOnSucceeded(event1 -> {
                    ket.setText("Keterangan: Proses kirim e-mail berhasil. Lanjut ke Proses Simpan Data.");
                });
                //========================================================



                Thread e=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //BOF kirim email
                        if (chkEmail.isSelected()){

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Dialog Konfirmasi");
                            alert.setHeaderText("Proses kirim e-mail.");
                            alert.setContentText("Proses waktu beberapa detik. Lanjutkan?");

                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK){

                                hbox10.getChildren().clear();
                                hbox10.getChildren().addAll(progressBar,new Separator(Orientation.VERTICAL),ket);

                                progressBar.setProgress(-1.0);
                                ket.setText("Keterangan: Mulai proses kirim e-mail.");

                                Thread thread=new Thread(task_mail);
                                thread.setDaemon(true);
                                thread.start();

                            } else {
                                chkEmail.setSelected(false);
                            }
                        }
                        //EOF kirim email


                        Platform.runLater(()->{
                            ket.setText("Keterangan: Mulai proses Simpan Data.");
                            progressBar.setProgress(-1.0);
                            hbox10.getChildren().clear();
                            hbox10.getChildren().addAll(progressBar,new Separator(Orientation.VERTICAL),ket);

                            Thread t=new Thread(task_simpan);
                            t.setDaemon(true);
                            t.start();
                        });

                    }
                });
                e.setDaemon(true);
                e.start();

                /*DataVendor v=new VendorModify().GetVenderoById(tmpVendor);
                if (chkEmail.isSelected()){
                    mailsend=new EmailController().EmailVendor(v.getEmail().toString());
                }

                DataNota d=new DataNota();
                d.setId_vendor(Integer.parseInt(tmpVendor));
                d.setNomor(text_nota.getText().toString().trim());
                d.setNomor_dpb_kolektif(tmpNomor);
                d.setMail_send(mailsend);

                simpan=new NotaModify().Simpan(d);

                if (chkPrint.isSelected()) {
                    input = getClass().getResourceAsStream(SURAT_PERMINTAAN);
                    try {
                        JasperReport report = (JasperReport) JRLoader.loadObject(input);

                        Map<String, Object> params = new HashMap<String, Object>();
                        params.put("nomor_surat", tmpNomor);
                        params.put("nota_dinas", text_nota.getText().toString().trim());
                        params.put("konten_surat", textArea.getText().toString().trim());
                        params.put("nama_vendor", tmpNamaVendor);
                        params.put("alamat_vendor", tmpAlamatVendor);
                        params.put("pemilik_vendor", tmpPemilikVendor);
                        params.put("nama_manager", tmpNamaManager);

                        //params.put("tanggal_posting",tmpTanggal);
                        params.put("total", tmpTotal);
                        params.put("ppn", tmpPPN);
                        params.put("grand_total", tmpGrandTotal);
                        params.put("terbilang", tmpTerbilang);

                        JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, conn);
                        jasperPrint.setName("Rekap Surat Permintaan");

                        JasperViewer jv=new JasperViewer(jasperPrint,false);
                        jv.setTitle("Rekap Surat Permintaan");
                        jv.setVisible(true);

                        //if (jasperPrint!=null){
                        //    JasperExportManager.exportReportToPdfFile(jasperPrint,"/home/rail/Documents/cetak-permintaan-"+tmpNamaVendor+"-"+tmpNomor+".pdf");
                        //}

                    } catch (JRException e) {
                        e.printStackTrace();
                    }
                }*/
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Dialog Informasi");
                alert.setHeaderText("Print Preview Gagal");
                alert.setContentText("Lengkapi opsi pilihan pada form ini.");
                alert.show();
                progressBar.setProgress(0);
            }

            /*if (simpan>0){
                progressBar.setProgress(1);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Dialog Informasi");
                alert.setHeaderText("Proses Berhasil");
                alert.setContentText("Data telah tersimpan.");
                alert.show();
            }else {
                progressBar.setProgress(0);
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Dialog Informasi");
                alert.setHeaderText("Proses Gagal");
                alert.setContentText("Terjadi kesalahan proses simpan.");
                alert.show();
            }*/
        });


        button_del=new Button("Hapus");

    }


    private void Refresh(){
        //button_print.setDisable(true);
        textArea.setText(stringBuffer.toString());

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

        cbxManager.getSelectionModel().clearSelection();
        cbxVendor.getSelectionModel().clearSelection();

        table.getItems().clear();

        ket.setText("Keterangan :");
    }
}