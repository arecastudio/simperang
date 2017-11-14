package app.view;

import app.GlobalUtility;
import app.controller.EmailController;
import app.controller.GetCurDate;
import app.model.DataDPB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by rail on 11/5/17.
 */
public class EmailDPB extends VBox {
    private HBox hbox1,hbox2,hbox10;
    private TableView table;
    private final DatePicker tglawal = new DatePicker();
    private final DatePicker tglakhir = new DatePicker();
    private Button button_show,button_resend,button_refresh;
    private Label label_ket;
    private String tmpNomor="";
    private boolean b=false;

    private ProgressBar progressBar;

    private void Inits() {
        setSpacing(5);
        setPadding(new Insets(5,5,5,5));
        setAlignment(Pos.TOP_CENTER);
        setMaxHeight(400);
        setMaxWidth(1024);
        getStyleClass().add("box-color");

        hbox1=new HBox(5);
        //hbox1.setPadding(new Insets(5,5,5,5));
        hbox1.setAlignment(Pos.CENTER_LEFT);

        hbox10=new HBox(5);
        progressBar=new ProgressBar(0);

        tglawal.setPrefWidth(150);
        tglawal.setValue(new GetCurDate().getLocalDate());

        tglakhir.setPrefWidth(150);
        tglakhir.setValue(new GetCurDate().getLocalDate());

        button_show=new Button("Tampilkan");
        button_show.setPrefWidth(150);
        button_show.setOnAction(event -> {
            //System.out.println("Tombol Tampilkan diklik");
            if (tglawal.getValue()!=null && tglakhir.getValue()!=null){
                String taw=(String)tglawal.getValue().toString();
                String tak=(String)tglakhir.getValue().toString();

                table.setItems(GlobalUtility.getAllDataDPBByIndikator("",taw,tak));
            }
        });

        label_ket=new Label("Keterangan :");

        hbox2=new HBox(5);
        //hbox2.setPadding(new Insets(5,5,5,5));
        hbox2.setAlignment(Pos.CENTER_LEFT);

        hbox10=new HBox(5);

        button_resend=new Button("Kirim Ulang");
        button_resend.setPrefWidth(150);
        button_resend.setOnAction(e->{

            if (tmpNomor!=""){

                Task<Void>task=new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        if(GlobalUtility.getInetStat()==true){
                            b=new EmailController().justSend(tmpNomor);
                        }
                        return null;
                    }
                };
                task.setOnSucceeded(e1->{
                    if (b==true){
                        int i=new EmailController().UbahStatusKirim(tmpNomor);

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Dialog Informasi");
                        alert.setHeaderText("Proses Berhasil");
                        alert.setContentText("Email telah terkirim.");
                        alert.showAndWait();
                        Refresh();
                    }else{
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Dialog Informasi");
                        alert.setHeaderText("Proses Gagal");
                        alert.setContentText("Terjadi kesalahan pengiriman email.");
                        alert.showAndWait();
                        //Refresh();
                    }

                    table.getItems().clear();
                });

                hbox10.getChildren().clear();
                hbox10.getChildren().addAll(progressBar,new Separator(Orientation.VERTICAL),label_ket);
                progressBar.setProgress(-1.0);
                label_ket.setText("Keterangan: Tunggu hingga proses selesai...");

                Thread thread=new Thread(task);
                thread.setDaemon(true);
                thread.start();

            }
        });

        button_refresh=new Button("Refresh");
        button_refresh.setPrefWidth(150);
        button_refresh.setOnAction(e->{
            Refresh();
        });

        table=new TabelDPBEmail();
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (table.getSelectionModel().getSelectedItem()!=null){
                    DataDPB data=(DataDPB) table.getSelectionModel().getSelectedItem();
                    tmpNomor=data.getNomor();
                    label_ket.setText("Keterangan : "+tmpNomor);
                }
            }
        });
    }

    public EmailDPB(){
        //then main Constructor
        Inits();

        hbox1.getChildren().addAll(new Label("Tanggal Posting"),tglawal,new Label("s/d"),tglakhir,new Separator(Orientation.VERTICAL),button_show,new Separator(Orientation.VERTICAL),button_refresh);

        hbox2.getChildren().addAll(button_resend);

        hbox10.getChildren().addAll(label_ket);

        getChildren().addAll(new LabelJudul("Cek Email Permintaan"),new Separator(Orientation.HORIZONTAL),hbox1,table,hbox2,hbox10);
    }

    private void Refresh(){
        table.getItems().clear();
        label_ket.setText("Keterangan: ");
        tmpNomor="";
        hbox10.getChildren().clear();
        hbox10.getChildren().addAll(label_ket);
    }
}
