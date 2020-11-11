package ehu.isad.controller.db;

import ehu.isad.eurobisioa.Herrialdea;
import javafx.scene.image.Image;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HerrialdeaBozkatuDBKud {

    private static final HerrialdeaBozkatuDBKud instance = new HerrialdeaBozkatuDBKud();

    public static HerrialdeaBozkatuDBKud getInstance() {
        return instance;
    }

    public void datuBaseanDatuZaharrakAktualizatu(int puntuak,String herrialdeBozkatua, String herrialdeBozkatzailea){
        String query = "update Bozkaketa set puntuak = puntuak + "+puntuak+" where (bozkatuaIzanDa='"+herrialdeBozkatua+"' and bozkatuDu='"+herrialdeBozkatzailea+"');";
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        dbKudeatzaile.execSQL(query);
    }

    public void datuBaseanDatuBerriakSartu(int puntuak,String herrialdeBozkatua,String herrialdeBozkatzailea){
        String query = "insert into Bozkaketa values ('"+herrialdeBozkatua+"','"+herrialdeBozkatzailea+"',year(now()),"+puntuak+")";
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        dbKudeatzaile.execSQL(query);
    }

    public boolean herrialdeBozkatzaileEtaBozkatuaDatuBaseanDaude(String herrialdeBozkatua,String herrialdeBozkatzailea) throws SQLException {
        String query = "select bozkatuaIzanDa from Bozkaketa where bozkatuaIzanDa='"+herrialdeBozkatua+"' and bozkatuDu='"+herrialdeBozkatzailea+"'";
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        ResultSet rs = dbKudeatzaile.execSQL(query);

        if(!rs.next()){
            return false;
        }
        else{
            return true;
        }
    }


    public String lortuHerrialdearenBandera(String herrialdea){
        String query = "select bandera from Herrialde where Herrialde.izena='"+herrialdea+"'";
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        ResultSet rs = dbKudeatzaile.execSQL(query);
        String bandera="";

        try {
            while (rs.next()) {
                bandera = rs.getString("bandera");
            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return bandera;
    }

    public int lortuHerrialdeBatenPuntuak(String izena){
        String query="select puntuak from Bozkaketa where bozkatuDu='"+izena+"'";

        DBKudeatzaile dbKudeatzaile=DBKudeatzaile.getInstantzia();
        ResultSet rs=dbKudeatzaile.execSQL(query);
        int emaitza=0;

        try {
            while (rs.next()) {
                emaitza=emaitza+rs.getInt("puntuak");
            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return emaitza;
    }

    public List<Herrialdea> bozkatzekoHerrialdeakKargatu(){

        String query = "select Ordezkaritza.*, Herrialde.bandera from Ordezkaritza inner join Herrialde where herrialdea=izena";
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        ResultSet rs = dbKudeatzaile.execSQL(query);

            List<Herrialdea> emaitza = new ArrayList<>();

            try {
                while (rs.next()) {

                    String artista = rs.getString("artista");
                    String herrialdea = rs.getString("herrialdea");
                    String abestia = rs.getString("abestia");
                    Integer puntuak = rs.getInt("puntuak");
                    String bandera = rs.getString("bandera");
                    emaitza.add(this.partaideakKargatu(artista,herrialdea,abestia,puntuak,bandera));
                }
            } catch(SQLException throwables){
                throwables.printStackTrace();
            }

            return emaitza;
        }

    private Herrialdea partaideakKargatu(String artista, String herrialdea, String abestia, Integer puntuak, String bandera) {
        Image irudia = this.irudiaLortu(bandera);
        Herrialdea partaidea = new Herrialdea(irudia,herrialdea,artista,abestia,puntuak);
        return partaidea;
    }

    private Image irudiaLortu(String bandera) {

        Image image = null;
        image = new Image(getClass().getResourceAsStream("/"+bandera+".png"));
        return image;

    }

}
