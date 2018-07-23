package sample;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.model.UrlDownloader;

import java.io.IOException;

public class Controller {

    public TextArea textArea;
    public TextField textUrl;
    public Label labelStatus;

    public void OnActionButtonLoadPressed() {
        UrlDownloader urlDownloader = new UrlDownloader();
        labelStatus.setText("");
        try {
            textArea.setText(urlDownloader.getByUrl(textUrl.getText()));
            labelStatus.setText("Downloaded");
        } catch (IOException e) {
            labelStatus.setText("Error");
            //e.printStackTrace();
        }
    }
}
