
package edu.upvictoria.fpoo;

import javax.swing.JOptionPane;
import java.io.*;

public class ArchivosCSV {


    public void crearTabla(String nombreTabla, String[] columnas, String ruta){

        String rutaF = ruta + "/";
        try {

            File tabla = new File(rutaF, nombreTabla + ".csv");
            FileWriter write = new FileWriter(tabla);

            StringBuilder colCSV = new StringBuilder();
            for (String col : columnas){
                String[] columPartes = col.trim().split(" ");
                String columNombre = columPartes[0];
                colCSV.append(columNombre).append(",");
            }
            colCSV.deleteCharAt(colCSV.length()-1);
            write.append(colCSV.toString());
            write.append("\n");
            write.close();
            System.out.println("La tabla "+ nombreTabla + " ha sido creada con exito!");

        } catch (IOException e){
            e.printStackTrace();
        }

    }


    public void leerShow(File[] archivos, String rutaTrabajo){

        if(archivos != null){
            System.out.println("Tablas en la ruta de trabajo: " + rutaTrabajo);
            for (File archivo : archivos){
                if(archivo.isFile() && archivo.getName().endsWith(".csv") && archivo.exists()){
                    System.out.println("- " + archivo.getName().replace(".csv", " "));
                }else {
                    System.out.println("No se encontraron tablas en la ruta de trabajo: " + rutaTrabajo);
                }
            }
        }
    }






}

