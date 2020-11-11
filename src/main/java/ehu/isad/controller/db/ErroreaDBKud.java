package ehu.isad.controller.db;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ErroreaDBKud {

    private static final ErroreaDBKud instance = new ErroreaDBKud();

    public static ErroreaDBKud getInstance() {
        return instance;
    }

    public String lortuBandera(String herrialdea){
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
}
