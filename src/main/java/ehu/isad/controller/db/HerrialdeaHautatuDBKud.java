package ehu.isad.controller.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HerrialdeaHautatuDBKud {

    private static final HerrialdeaHautatuDBKud instance = new HerrialdeaHautatuDBKud();

    public static HerrialdeaHautatuDBKud getInstance() {
        return instance;
    }

    public List<String> lortuHerrialdeak() {

        String query = "select izena from Herrialde";
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        ResultSet rs = dbKudeatzaile.execSQL(query);

        List<String> emaitza = new ArrayList<>();
        try {
            while (rs.next()) {

                String izena = rs.getString("izena");
                emaitza.add(izena);
            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }

        return emaitza;
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
}
