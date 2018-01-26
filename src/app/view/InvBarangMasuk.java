package app.view;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

public class InvBarangMasuk extends VBox {
    public InvBarangMasuk(){
        Init();

        getChildren().addAll(new LabelJudul("Barang Masuk"),new Separator(Orientation.HORIZONTAL));
    }

    private void Init() {
        setSpacing(5);
        setPadding(new Insets(5, 5, 5, 5));
        setMaxHeight(500);
        setMaxWidth(1024);
        setAlignment(Pos.TOP_CENTER);
        getStyleClass().add("box-color");
    }
}
