package edu.upvictoria.fpoo;

import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.FileReader;

public class ArchivosCSV {

    private BufferedReader lector; //leer
    private String linea; //Recibe linea de fila
    private String partes[] = null; //Almacena cada linea leida

    public void leerArchivo(String nombreArchivo){
        try{
            lector = new BufferedReader(new FileReader(nombreArchivo));
            while ((linea = lector.readLine()) != null){
                partes = linea.split(",");
                imprimirLinea();
                System.out.println();
            }
            lector.close();
            linea = null;
            partes= null;
        } catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }

    public void imprimirLinea(){
        for (int i = 0; i < partes.length; i++ ){
            System.out.println(partes[i]+" | ");
        }
    }




}
