package com.example.ned.intresting.dbconnection;


import java.sql.*;
import java.util.Vector;

import javax.swing.JOptionPane;


public class DbConnection {
    public static Connection db;
    private static boolean connesso;
    private static DbConnection instance;



    public static String nomebase="sql11195345";
    public static String rootu="sql11195345";
    public static String rootp="3EkEfgEeJn";
    public static String host="sql11.freemysqlhosting.net";





    public static DbConnection getInstance() {
        if(instance == null)
            instance = new DbConnection();
        if(connesso != true)
            connetti(nomebase, rootu, rootp);
        return instance;
    }

    // Apre la connessione con il Database
    public static boolean connetti(String nomeDB, String nomeUtente, String pwdUtente) {

        connesso = false;
        try {

            // Carico il driver JDBC per la connessione con il database MySQL
            Class.forName("com.mysql.jdbc.Driver");
            db = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11195345",nomeUtente,pwdUtente);

            connesso=true;


        }catch (Exception e) {
            //e.printStackTrace(); VISUALIZZA GLI ERRORI SULLA CONSOLE
            JOptionPane.showMessageDialog(null,"<html>Accesso non riuscito per il database selezionato.<br>Cambiare le impostazioni di connessione e controllare che il server sia raggiungibile. <br> <br>  Errore: "+ e.getMessage(),"Non sono riuscito a collegarmi",JOptionPane.ERROR_MESSAGE);
        }
        return connesso;

    }
    public Vector<String[]> eseguiQuery(String query) {
        Vector<String[]> v = null;
        String [] record;
        int colonne = 0;
        try {
            Statement stmt = db.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            v = new Vector<String[]>();
            ResultSetMetaData rsmd = rs.getMetaData();
            colonne = rsmd.getColumnCount();

            while(rs.next()) {
                record = new String[colonne];
                for (int i=0; i<colonne; i++) record[i] = rs.getString(i+1);
                v.add( (String[]) record.clone() );
            }
            rs.close();
            stmt.close();
        } catch (Exception e) { e.printStackTrace(); }

        return v;
    }

    public boolean eseguiAggiornamento(String query) {
        int numero = 0;
        boolean risultato = false;
        try {
            Statement stmt = db.createStatement();
            numero = stmt.executeUpdate(query);
            risultato = true;
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            risultato = false;
        }
        return risultato;
    }

    public void disconnetti() {
        try {
            db.close();
            connesso = false;
        } catch (Exception e) { e.printStackTrace(); }
    }

    public boolean isConnesso() { return connesso; }
}