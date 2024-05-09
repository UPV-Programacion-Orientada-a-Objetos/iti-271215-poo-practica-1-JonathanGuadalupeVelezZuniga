package edu.upvictoria.fpoo;

import javax.swing.JOptionPane;
import java.io.*;

public class ArchivosCSV {


    public void crearTabla(String nombreTabla, String[] columnas, String ruta){

        try {

            File tablaW = new File(ruta, nombreTabla+".csv");
            StringBuilder constCSV = new StringBuilder();

            for (String col : columnas){
                String[] colPartF = col.trim().split(" ");
                String colName = colPartF[0];

                constCSV.append(colName).append(",");
            }

            constCSV. deleteCharAt(constCSV.length()-1);
            FileWriter write = new FileWriter(nombreTabla);


        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public void leerShow(File[] archivos, String rutaTrabajo){

        if(archivos != null){
            System.out.println("Tablas en la ruta de trabajo: " + rutaTrabajo);
            for (File archivo : archivos){
                if(archivo.isFile() && archivo.getName().endsWith(".csv")){
                    System.out.println("- " + archivo.getName().replace(".csv", " "));
                }else {
                    System.out.println("No se encontraron tablas en la ruta de trabajo: " + rutaTrabajo);
                }
            }
        }
    }






}
