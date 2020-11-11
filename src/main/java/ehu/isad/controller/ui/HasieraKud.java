package ehu.isad.controller.ui;

import ehu.isad.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class HasieraKud implements Initializable {

    private Main main;

    @FXML
    private ImageView imgIrudia;

    @FXML
    private Button btn_bozkatu;

    @FXML
    void klikEgin(ActionEvent event) {
        main.herrialdeakHautatuErakutsi();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void setMainApp(Main main) {
        this.main = main;
    }
}
