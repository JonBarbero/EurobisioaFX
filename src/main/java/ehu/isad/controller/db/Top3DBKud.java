package ehu.isad.controller.db;

import ehu.isad.eurobisioa.Herrialdea;
import javafx.scene.image.Image;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Top3DBKud {

    private static final Top3DBKud instance = new Top3DBKud();

    public static Top3DBKud getInstance() {
        return instance;
    }

    public int puntuenBatura(String herrialdea){
        String query="select puntuak" +
                " from Bozkaketa" +
                " where bozkatuaIzanDa='"+herrialdea+"'";

        DBKudeatzaile dbKudeatzaile=DBKudeatzaile.getInstantzia();
        ResultSet rs=dbKudeatzaile.execSQL(query);
        int puntuak=0;

        try {
            while (rs.next()) {
                puntuak=puntuak+rs.getInt("puntuak");
            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return puntuak;
    }

    public List<Herrialdea> top3Lortu(){
        String query="select b.bozkatuaIzanDa,h.bandera" +
                " from Bozkaketa b, Herrialde h " +
                " where bozkatuaIzanDa=izena ";

        DBKudeatzaile dbKudeatzaile=DBKudeatzaile.getInstantzia();
        ResultSet rs=dbKudeatzaile.execSQL(query);
        Herrialdea partaidea=null;
        List<Herrialdea> emaitza=new ArrayList<>();

        try {
            while (rs.next()) {
                String bandera = rs.getString("bandera");
                String herrialdea=rs.getString("bozkatuaIzanDa");
                partaidea=new Herrialdea(this.irudiaLortu(bandera),herrialdea,null,null,this.puntuenBatura(herrialdea));
                boolean aurkitua=false;
                for(int i=0;i<emaitza.size();i++){
                    if(emaitza.get(i).getHerrialdea().equals(herrialdea)){
                        aurkitua = true;
                    }
                }
                if(!aurkitua){
                    emaitza.add(partaidea);
                }
            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return emaitza;
    }

    private Image irudiaLortu(String bandera) {

        Image image;
        image = new Image(getClass().getResourceAsStream("/"+bandera+".png"));
        return image;

    }

}
