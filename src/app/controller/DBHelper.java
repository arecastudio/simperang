package app.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBHelper {
	private Connection conn;;
	private static String host="",db="",user="",pass="";
	
	public DBHelper() {
		conn=null;
	}
	
	public static Connection Konek() {
	    ReadConfig();


        try {
            Class.forName("com.mysql.jdbc.Driver");
            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/db_atk_fx","rail","");
            Connection conn = DriverManager.getConnection("jdbc:mysql://"+host+"/"+db,user,pass);
            //System.out.println("Database has been Connected!");
            return conn;
        } catch(Exception e){
            System.out.println("Error: "+e.toString());
            return null;
        }
    }

    private static boolean ReadConfig(){
        boolean ret=false;
        Properties prop=new Properties();
        InputStream input=null;
        String filename="/app/config.properties";
        try {
            //input = getClass().getResourceAsStream(filename);
            input=DBHelper.class.getResourceAsStream(filename);
            prop.load(input);
            /*System.out.println(prop.getProperty("database"));
            System.out.println(prop.getProperty("dbuser"));
            System.out.println(prop.getProperty("dbpassword"));*/

            host=prop.getProperty("host");
            db=prop.getProperty("db");
            user=prop.getProperty("user");
            pass=prop.getProperty("pass");
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            if(input!=null){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  ret;
    }

    public static String getHost() {
        return host;
    }
}
