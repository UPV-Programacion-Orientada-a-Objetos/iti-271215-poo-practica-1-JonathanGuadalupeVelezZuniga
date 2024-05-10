package edu.upvictoria.fpoo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class App
{
    public static void main( String[] args )
    {
        BufferedReader bufer = new BufferedReader(new InputStreamReader(System.in));
        InterpreteSQL interpreteSQL = new InterpreteSQL();



        try {

            while (true){


                System.out.print("SQL>");
                interpreteSQL.setSentencia(bufer.readLine());
                String sql = interpreteSQL.getSentencia();
                interpreteSQL.sentenciaSQL(sql);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                bufer.close();
            }catch (IOException e) {
                e.printStackTrace();

            }
        }





    }
}


