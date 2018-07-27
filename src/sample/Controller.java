package sample;

import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sample.model.UrlDownloader;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public TextArea textArea;
    public TextField textUrl;
    public Label labelStatus;
    public ListView<CpuData> listView;

    private ObservableList<CpuData> observableList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        observableList = FXCollections.observableList(new ArrayList<>());
        listView.setItems(observableList);
        listView.setCellFactory(param -> new ListCell<CpuData>() {
            @Override
            protected void updateItem(CpuData item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null)
                    setText(item.getName());
            }
        });
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                textArea.setText(newValue.getElements().html()));
    }

    public void OnActionButtonLoadPressed() {
        UrlDownloader urlDownloader = new UrlDownloader();
            /*labelStatus.setText("");
            try {
                observableList.add(urlDownloader.getCpuByUrl("https://www.techpowerup.com/cpudb/"+Integer.toString(i)));
                labelStatus.setText("Downloaded");
            } catch (IOException e) {
                labelStatus.setText("Error");
                //e.printStackTrace();
            }*/
        List<String> urls = new ArrayList<>();

        for (int i = 1; i < 11; i++) {
            urls.add("https://www.techpowerup.com/cpudb/" + Integer.toString(i));
        }
        urlDownloader.getCpuByUrl(urls)
                .subscribeOn(Schedulers.newThread())
                .observeOn(JavaFxScheduler.platform())
                .subscribe(new DisposableSubscriber<CpuData>() {
                    @Override
                    public void onNext(CpuData cpuData) {
                        observableList.add(cpuData);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        labelStatus.setText(throwable.toString());
                    }

                    @Override
                    public void onComplete() {
                        labelStatus.setText("Downloaded");
                    }
                });
    }

    public void OnActionButtonTest(ActionEvent actionEvent) {
        labelStatus.setText(Integer.toString(observableList.size()));
    }
}
