package app.view;

import app.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Created by android on 11/6/17.
 */
public class SetDB extends VBox {
    private HBox hbox1,hbox2;
    private FileChooser fileChooser;
    private File file;
    private Button button_browse;
    private Label label_ket;
    private TableView table;

    public SetDB(){
        Inits();

        hbox1.getChildren().addAll(button_browse);
        hbox2.getChildren().addAll(label_ket);

        getChildren().addAll(new LabelJudul("Export/Import Basis Data"),new Separator(Orientation.HORIZONTAL),hbox1,table,hbox2);
    }

    private void Inits() {
        setSpacing(5);
        setPadding(new Insets(5,5,5,5));
        setAlignment(Pos.TOP_CENTER);
        setMaxHeight(500);
        getStyleClass().add("box-color");

        hbox1=new HBox(5);

        hbox2=new HBox(5);

        file=null;

        label_ket=new Label("Keterangan: ");

        fileChooser=new FileChooser();
        fileChooser.setTitle("Pilih File CSV Sumber");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel Comma Delimiter","*.csv"));

        button_browse=new Button("Pilih File CSV Sumber");
        button_browse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                file=fileChooser.showOpenDialog(Main.primaryStage);
                System.out.println("Dipilih: "+file);
                label_ket.setText("Keterangan: "+ file);
            }
        });

        table=new TableView();
        table.setPrefHeight(300);
    }
}
