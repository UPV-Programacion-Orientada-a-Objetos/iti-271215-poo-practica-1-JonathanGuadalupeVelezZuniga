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
            String sentencia = bufer.readLine();


            interpreteSQL.sentenciaSQL(sentencia);
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


        //---------LEER
        /*
          ArchivosCSV archivo = new ArchivosCSV();

        archivo.leerArchivo("/home/jonathan-velez/Documentos/POLITECNICA/6 CUATRIMESTRE/POO/U1/BASE DE DATOS/prueba1.csv");

          */



    }
}
