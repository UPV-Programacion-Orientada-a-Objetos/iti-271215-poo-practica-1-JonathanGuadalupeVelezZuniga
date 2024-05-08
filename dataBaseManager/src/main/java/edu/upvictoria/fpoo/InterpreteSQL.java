package edu.upvictoria.fpoo;


import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InterpreteSQL {
    private String rutaTrabajo;



//--------------COMANDOS

public void sentenciaSQL(String sql) {
    Matcher matcher;

    if((matcher = Pattern.compile("^USE (.+);$", Pattern.CASE_INSENSITIVE).matcher(sql)).find()){
        System.out.println(matcher.group(1));
        Use(rutaTrabajo);


    } else if ((matcher = Pattern.compile("^SHOW TABLES [a-zA-Z_][a-zA-Z0-9_/.-]*;$", Pattern.CASE_INSENSITIVE).matcher(sql)).find()) {
        ShowTable(sql);

    } else if ((matcher = Pattern.compile("^\\s*CREATE\\s+TABLE\\s+[a-zA-Z_][a-zA-Z0-9_]*\\s*\\(.*\\)\\s*;$", Pattern.CASE_INSENSITIVE).matcher(sql)).find()) {
        CreateTAble(sql);
    }else if(){

    }
    else {
        System.out.println("Sintaxis incorrecta");
    }


}


//------------ Funciones de comandos

    public void Use(String direccion){

        rutaTrabajo = direccion;
        System.out.println("Usando direccion " + rutaTrabajo);
    }

    public void ShowTable(String mostrarTablas){
        System.out.println("Mostrando Tablas de la direcccion: " + mostrarTablas);

    }

    public void CreateTAble(String tablaCreada){
        System.out.println("TABLA CREADA CORRECTAMENTE: ");
        System.out.println(tablaCreada);
    }











}


