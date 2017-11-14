package app.view;

import javafx.scene.control.Label;

public class LabelJudul extends Label {
	private String text;

	public LabelJudul(String text) {
		this.text=text;
		setText(text);
		getStyleClass().addAll("judul");
	}

	public Label Sub(){
		Label lbl=new Label(text);
		lbl.getStyleClass().addAll("sub-judul");
		return lbl;
	}

}
