package app.view;

import app.GlobalUtility;
import app.controller.*;
import app.model.DataNota;
import app.model.DataTerbilang;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by rail on 11/15/17.
 */
public class ReportDPBVendorPrint extends VBox {
    private Connection conn;
    private DBHelper helper;

    private HBox hbox10;
    private TableView table;
    private final DatePicker tglawal = new DatePicker();
    private final DatePicker tglakhir = new DatePicker();
    private TextField text_nota;
    private Label ket;
    private ToggleGroup group;
    private RadioButton radio1,radio2;
    private Button button_show,button_refresh,button_print,button_del,button_mail;
    private ProgressBar progressBar;

    private final String SURAT_PERMINTAAN="/app/reports/surat-permintaan.jasper";
    private final String SURAT_PERMINTAAN_LAMPIRAN="/app/reports/surat-permintaan-lampiran.jasper";

    private String tmp_nomor="";
    private String tmpTanggal="",tmpTotal="",tmpPPN="",tmpGrandTotal="",tmpTerbilang="";

    public ReportDPBVendorPrint(){
        conn=helper.Konek();
        Inits();

        HBox hbox1=new HBox(5);
        hbox1.setAlignment(Pos.CENTER_LEFT);
        hbox1.getChildren().addAll(tglawal,new Label("s/d"),tglakhir);

        HBox hbox2=new HBox(5);
        hbox2.setAlignment(Pos.CENTER);
        hbox2.getChildren().addAll(button_show,button_print,button_refresh,button_mail,new Separator(Orientation.VERTICAL),button_del);

        GridPane grid=new GridPane();
        grid.setHgap(5);grid.setVgap(5);

        grid.add(radio1,0,0);
        grid.add(hbox1,1,0);

        grid.add(radio2,0,1);
        grid.add(text_nota,1,1);

        hbox10.getChildren().add(ket);

        getChildren().addAll(new LabelJudul("Cetak Rekap Permintaan ke Vendor"),new Separator(Orientation.HORIZONTAL),grid,new Separator(Orientation.HORIZONTAL),hbox2,table,hbox10);
    }

    private void Refresh() {
        table.getItems().clear();
        text_nota.setText("");
        tmp_nomor="";
        tmpTanggal="";
        tmpTotal="";
        tmpPPN="";
        tmpGrandTotal="";
        tmpTerbilang="";
        ket.setText("Keterangan :");
        progressBar.setProgress(0);
        hbox10.getChildren().clear();
        hbox10.getChildren().add(ket);
    }

    private void Inits() {
        setSpacing(5);
        setPadding(new Insets(5,5,5,5));
        setAlignment(Pos.TOP_CENTER);
        setMaxHeight(570);
        setMaxWidth(1024);
        getStyleClass().add("box-color");

        hbox10=new HBox(5);
        hbox10.setAlignment(Pos.CENTER_LEFT);

        progressBar=new ProgressBar(0);
        ket=new Label("Keterangan :");

        text_nota=new TextField();
        text_nota.setMaxWidth(200);

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


        group=new ToggleGroup();
        radio1=new RadioButton("Tgl. Surat");
        radio1.setToggleGroup(group);
        radio1.setSelected(true);
        radio2=new RadioButton("No. Nota");
        radio2.setToggleGroup(group);

        button_del=new Button("Hapus Nota");
        button_del.setPrefWidth(150);
        button_del.setOnAction(e->{
            if (tmp_nomor!=""){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Dialog Konfirmasi");
                alert.setHeaderText("Anda akan menghapus Nota ini,");
                alert.setContentText("Yakin?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    int i=new NotaModify().HapusNota(tmp_nomor);
                    if (i>0){
                        Refresh();
                        ket.setText("Keterangan : Berhasil menghapus Nota.");
                    }else {
                        ket.setText("Keterangan : Gagal menghapus Nota");
                    }
                }
            }
        });

        button_print=new Button("Cetak");
        button_print.setPrefWidth(150);
        button_print.setOnAction(event -> {
            if (tmp_nomor!=""){
                DataNota dn=new NotaModify().GetNotaByNomor(tmp_nomor);

                Task<Void> task=new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        InputStream input = getClass().getResourceAsStream(SURAT_PERMINTAAN);
                        String lampiran=getClass().getResource(SURAT_PERMINTAAN_LAMPIRAN).toString();
                        lampiran=lampiran.substring((Integer)"file:".length(),lampiran.length()-(Integer)"surat-permintaan-lampiran.jasper".length());
                        System.out.println("File lampiran: "+lampiran.toString());
                        try {
                            JasperReport report = (JasperReport) JRLoader.loadObject(input);

                            Map<String, Object> params = new HashMap<String, Object>();
                            params.put("nomor_surat", dn.getNomor_dpb_kolektif());
                            params.put("nota_dinas", dn.getNomor());
                            params.put("konten_surat", dn.getKonten_surat());
                            params.put("nama_vendor", dn.getNama_vendor());
                            params.put("alamat_vendor", dn.getAlamat_vendor());
                            params.put("pemilik_vendor", dn.getPemilik_vendor());
                            //params.put("nama_manager", tmpNamaManager);

                            //params.put("tanggal_posting",tmpTanggal);
                            params.put("total", tmpTotal);
                            params.put("ppn", tmpPPN);
                            params.put("grand_total", tmpGrandTotal);
                            params.put("terbilang", tmpTerbilang);

                            params.put("SUBREPORT_DIR",lampiran.toString());

                            JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, conn);
                            jasperPrint.setName("Rekap Surat Permintaan");

                            JasperViewer jv=new JasperViewer(jasperPrint,false);
                            jv.setTitle("Rekap Surat Permintaan");
                            jv.setVisible(true);

                        } catch (JRException e) {
                            e.printStackTrace();
                        }

                        return null;
                    }
                };
                task.setOnSucceeded(e->{
                    ket.setText("Keterangan :");
                    progressBar.setProgress(0);
                    hbox10.getChildren().clear();
                    hbox10.getChildren().add(ket);
                });

                hbox10.getChildren().clear();
                hbox10.getChildren().addAll(progressBar,new Separator(Orientation.VERTICAL),ket);
                progressBar.setProgress(-1.0);
                ket.setText("Keterangan: Tunggu hingga proses selesai...");

                Thread thread=new Thread(task);
                thread.setDaemon(true);
                thread.start();
            }
        });

        button_mail=new Button("Kirim e-mail");
        button_mail.setPrefWidth(150);
        button_mail.setOnAction(event -> {
            if (tmp_nomor!=""){
                DataNota dn=new NotaModify().GetNotaByNomor(tmp_nomor);
                Task<Integer>task=new Task<Integer>() {
                    @Override
                    protected Integer call() throws Exception {
                        Integer sent=0;
                        if (GlobalUtility.getInetStat()==true){
                            sent=new EmailController().EmailVendor(dn.getEmail_vendor(),"");
                            //System.out.println(dn.getEmail_vendor());
                        }
                        return sent;
                    }
                };
                task.setOnSucceeded(e->{

                    if (task.getValue()>0){
                        new EmailController().UbahStatusKirimNota(dn.getNomor());
                    }

                    Platform.runLater(()->{
                        ket.setText("Keterangan :");
                        progressBar.setProgress(0);
                        hbox10.getChildren().clear();
                        hbox10.getChildren().add(ket);
                        table.setItems(new NotaModify().GetTableItem(tmp_nomor,null,null));
                    });

                });

                hbox10.getChildren().clear();
                hbox10.getChildren().addAll(progressBar,new Separator(Orientation.VERTICAL),ket);
                progressBar.setProgress(-1.0);
                ket.setText("Keterangan: Tunggu hingga proses selesai...");

                Thread thread=new Thread(task);
                thread.setDaemon(true);
                thread.start();
            }
        });

        button_refresh=new Button("Refresh");
        button_refresh.setPrefWidth(150);
        button_refresh.setOnAction(event -> {
            Refresh();
        });

        button_show=new Button("Tampilkan");
        button_show.setPrefWidth(150);
        button_show.setOnAction(event -> {
            if (radio2.isSelected()){
                table.setItems(new NotaModify().GetTableItem(text_nota.getText().trim(),null,null));
            }else{
                table.setItems(new NotaModify().GetTableItem(null,tglawal.getValue().toString(),tglakhir.getValue().toString()));
            }
        });

        table=new TabelNota();
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (table.getSelectionModel().getSelectedItem()!=null){
                    DataNota nota=(DataNota) table.getSelectionModel().getSelectedItem();
                    tmp_nomor=nota.getNomor();
                    ket.setText("Keterangan || Data dipilih: "+ nota.getNomor());


                    DataTerbilang dt=new DPBKolektifModify().GetDPBTerbilang(nota.getNomor_dpb_kolektif());
                    System.out.println(dt.getTerbilang());

                    tmpTanggal=dt.getTanggal();
                    tmpTotal=dt.getTotal()+"";
                    tmpPPN=dt.getPpn()+"";
                    tmpGrandTotal=dt.getGrand_total()+"";
                    tmpTerbilang=dt.getTerbilang();
                }
            }
        });
    }
}
