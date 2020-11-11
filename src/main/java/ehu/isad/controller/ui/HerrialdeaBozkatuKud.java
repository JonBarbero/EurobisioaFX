package ehu.isad.controller.ui;

import ehu.isad.Main;
import ehu.isad.controller.db.HerrialdeaBozkatuDBKud;
import ehu.isad.eurobisioa.Herrialdea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.List;

public class HerrialdeaBozkatuKud implements Initializable {

    private Main main;

    @FXML
    private Label lblLabela;

    @FXML
    private Label lblPuntuak;

    @FXML
    private Label lblWarning;

    @FXML
    private ImageView imgBandera;

    @FXML
    private Button btnBotoia;

    @FXML
    private TableView<Herrialdea> tblTaula;

    @FXML
    private TableColumn<Herrialdea, Image> tblBandera;

    @FXML
    private TableColumn<Herrialdea, String> tblHerrialdea;

    @FXML
    private TableColumn<Herrialdea, String> tblArtista;

    @FXML
    private TableColumn<Herrialdea, String> tblAbestia;

    @FXML
    private TableColumn<Herrialdea, Integer> tblPuntuak;

    private String comboHerrialdea;


    public void setMainApp(Main main) {
        this.main = main;
    }

    public void labelIdatzi(String herrialdea) {
        lblLabela.setText(herrialdea + "k horrela banatuko ditu bere puntuak:");
    }

    public void puntuakIdatzi(String herrialdea){
        lblPuntuak.setText(5-HerrialdeaBozkatuDBKud.getInstance().lortuHerrialdeBatenPuntuak(herrialdea)+" puntu geratzen dira");
    }

    public void banderaJarri(String herrialdea){
        imgBandera.setImage(this.irudiaLortu(herrialdea));
    }

    public void comboBalioaLortu(String herrialdea){
        this.comboHerrialdea=herrialdea;
    }

    private Image irudiaLortu(String herrialdea) {
        Image argazkia;
         argazkia= new Image(getClass().getResourceAsStream("/"+HerrialdeaBozkatuDBKud.getInstance().lortuHerrialdearenBandera(herrialdea)+".png"));
        return argazkia;
    }


    @FXML
    void onClick(ActionEvent event) throws SQLException {

        int luzera = tblTaula.getItems().size();
        int puntuak=0;
        for(int j=0;j<luzera;j++){
            puntuak=puntuak+tblPuntuak.getCellObservableValue(j).getValue();
        }
        if(puntuak>0 && puntuak<=5) {
            this.lblWarning.setText("");
            for (int i = 0; i < luzera; i++) {
                if (tblPuntuak.getCellObservableValue(i).getValue() > 0) {
                    if (!HerrialdeaBozkatuDBKud.getInstance().herrialdeBozkatzaileEtaBozkatuaDatuBaseanDaude(tblHerrialdea.getCellObservableValue(i).getValue(), this.comboHerrialdea)) {
                        HerrialdeaBozkatuDBKud.getInstance().datuBaseanDatuBerriakSartu(tblPuntuak.getCellObservableValue(i).getValue(), tblHerrialdea.getCellObservableValue(i).getValue(), this.comboHerrialdea);
                        main.getTop3Kud().top3Kargatu();
                    }
                    else{
                        HerrialdeaBozkatuDBKud.getInstance().datuBaseanDatuZaharrakAktualizatu(tblPuntuak.getCellObservableValue(i).getValue(),tblHerrialdea.getCellObservableValue(i).getValue(), this.comboHerrialdea);
                        main.getTop3Kud().top3Kargatu();
                    }
                }
            }
            this.main.top3Erakutsi();
        }
        else if(puntuak==0){
            this.lblWarning.setText("Ez dituzu oraindik punturik eman");
        }
        else {
            this.lblWarning.setText("Gehienez 5 puntu dira banatu ahal dituzunak");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void partaideakKargatu(){
        List<Herrialdea> kargatzekoa = HerrialdeaBozkatuDBKud.getInstance().bozkatzekoHerrialdeakKargatu();
        ObservableList<Herrialdea> partaideak = FXCollections.observableArrayList(kargatzekoa);

        //add your data to the table here.
        tblTaula.setItems(partaideak);
        tblTaula.setEditable(true);


        //make sure the property value factory should be exactly same as the e.g getStudentId from your model class

        tblBandera.setCellValueFactory(new PropertyValueFactory<>("Bandera"));
        tblHerrialdea.setCellValueFactory(new PropertyValueFactory<>("Herrialdea"));
        tblArtista.setCellValueFactory(new PropertyValueFactory<>("Artista"));
        tblAbestia.setCellValueFactory(new PropertyValueFactory<>("Abestia"));
        tblPuntuak.setCellValueFactory(new PropertyValueFactory<>("Puntuak"));
        tblPuntuak.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        this.puntuakEditatu();
        this.puntuakCommit();
        this.irudiaKargatu();
    }

    public void puntuakEditatu(){
        Callback<TableColumn<Herrialdea, Integer>, TableCell<Herrialdea, Integer>> defaultTextFieldCellFactory
                = TextFieldTableCell.forTableColumn(new IntegerStringConverter());

        tblPuntuak.setCellFactory(col -> {
            TableCell<Herrialdea, Integer> cell = defaultTextFieldCellFactory.call(col);

            cell.setOnMouseClicked(event -> {
                if (! cell.isEmpty()) {
                    if (cell.getTableView().getSelectionModel().getSelectedItem().getHerrialdea().equals(this.comboHerrialdea)) {
                        cell.setEditable(false);
                    }else {
                        cell.setEditable(true);
                    }
                }
            });

            return cell ;
        });
    }

    public void puntuakCommit(){
        tblPuntuak.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow())
                            .setPuntuak(t.getNewValue());
                });
    }

    public void irudiaKargatu(){
        //Irudia kargatzeko
        tblBandera.setCellFactory(p -> new TableCell<>() {
            public void updateItem(Image image, boolean empty) {
                if (image != null && !empty){
                    final ImageView imageview = new ImageView();
                    imageview.setFitHeight(50);
                    imageview.setFitWidth(50);
                    imageview.setImage(image);
                    setGraphic(imageview);
                    setAlignment(Pos.CENTER);
                    // tbData.refresh();
                }else{
                    setGraphic(null);
                    setText(null);
                }
            };
        });
    }
}




