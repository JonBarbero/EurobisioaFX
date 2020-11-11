package ehu.isad.controller.ui;

import ehu.isad.Main;
import ehu.isad.controller.db.Top3DBKud;
import ehu.isad.eurobisioa.Herrialdea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Top3Kud implements Initializable {

    @FXML
    private ImageView img1;

    @FXML
    private ImageView img2;

    @FXML
    private ImageView img3;

    @FXML
    private Label lbl1;

    @FXML
    private Label lbl2;

    @FXML
    private Label lbl3;

    @FXML
    private Button btnBotoia;

    private Main main;

    public void setMainApp(Main main) {
        this.main = main;
    }


    @FXML
    void onClick(ActionEvent event) {
        this.main.hasieraErakutsi();
    }

    public int handienaPos(List<Herrialdea> top, int lehenengoPos){

        int handiena=-1;
        int handienaPos=0;
        int i=0;

        while(i<top.size()){
            if(handiena<=top.get(i).getPuntuak()){
                handiena=top.get(i).getPuntuak();
                handienaPos=i;
            }
            i++;
        }
        return handienaPos;
    }

    public void top3Kargatu(){
        List<Herrialdea> top = Top3DBKud.getInstance().top3Lortu();

        int pos= this.handienaPos(top,0);
        img1.setImage(top.get(pos).getBandera());
        lbl1.setText(top.get(pos).getHerrialdea() + "-" + top.get(pos).getPuntuak());
        top.remove(pos);

        int pos1= this.handienaPos(top,pos);
        img2.setImage(top.get(pos1).getBandera());
        lbl2.setText(top.get(pos1).getHerrialdea() + "-" + top.get(pos1).getPuntuak());
        top.remove(pos1);

        int pos2= this.handienaPos(top,pos1);
        img3.setImage(top.get(pos2).getBandera());
        lbl3.setText(top.get(pos2).getHerrialdea() + "-" + top.get(pos2).getPuntuak());
        top.remove(pos2);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}

