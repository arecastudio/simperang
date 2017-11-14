package app.view;

import app.model.DataVendor;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by android on 11/8/17.
 */
public class TabelVendor extends TableView {
    public TabelVendor(){
        setEditable(false);
        setColumnResizePolicy((param) -> true );
        getStyleClass().addAll("table");

        TableColumn idCol = new TableColumn("ID");
        TableColumn namaCol = new TableColumn("Nama Vendor");
        TableColumn alamatCol = new TableColumn("Alamat");
        TableColumn telpCol = new TableColumn("Telepon");
        TableColumn emailCol = new TableColumn("Email");
        TableColumn pemilikCol = new TableColumn("Nama Pemilik");

        getColumns().addAll(idCol,namaCol,telpCol,emailCol,alamatCol,pemilikCol);

        idCol.setCellValueFactory(new PropertyValueFactory<DataVendor,String>("id"));
        namaCol.setCellValueFactory(new PropertyValueFactory<DataVendor,String>("nama"));
        alamatCol.setCellValueFactory(new PropertyValueFactory<DataVendor,String>("alamat"));
        telpCol.setCellValueFactory(new PropertyValueFactory<DataVendor,String>("telp"));
        emailCol.setCellValueFactory(new PropertyValueFactory<DataVendor,String>("email"));
        pemilikCol.setCellValueFactory(new PropertyValueFactory<DataVendor,String>("pemilik"));

        idCol.setPrefWidth(50);
        namaCol.setPrefWidth(250);
        alamatCol.setPrefWidth(350);
        telpCol.setPrefWidth(200);
        emailCol.setPrefWidth(200);
        pemilikCol.setPrefWidth(250);
    }
}
