package edu.upvictoria.fpoo;


import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class InterpreteSQL {
    ArchivosCSV csv = new ArchivosCSV();

    private String rutaTrabajo;
    private String sentenciaSql;



//--------------COMANDOS

    public void sentenciaSQL(String sentencia) {
        Matcher matcher;

        if ((matcher = Pattern.compile("^USE (.+);$", Pattern.CASE_INSENSITIVE).matcher(sentencia)).find()) {
            String rutaNueva = matcher.group(1);
            if(validar(rutaNueva)){
                use(rutaNueva);

            }else {
                System.out.println("ERRO. El diredtorio no existe");
            }


        } else if ((matcher = Pattern.compile("SHOW TABLES;$", Pattern.CASE_INSENSITIVE).matcher(sentencia)).find()) {
            showTable(sentencia);


        } else if ((matcher = Pattern.compile("^\\s*CREATE\\s+TABLE\\s+[a-zA-Z_][a-zA-Z0-9_]*\\s*\\(.*\\)\\s*;$", Pattern.CASE_INSENSITIVE).matcher(sentencia)).find()) {
            createTAble(sentencia);

        } else if ((matcher = Pattern.compile("SELECT\\s+\\*\\s+FROM (.+);$", Pattern.CASE_INSENSITIVE).matcher(sentencia)).find()) {
            select(sentencia);
        } else if ((matcher = Pattern.compile("DROP TABLE\\s+(\\w+);$", Pattern.CASE_INSENSITIVE).matcher(sentencia)).find()) {
            dropTable(sentencia);
        } else if ((matcher = Pattern.compile("^DELETE\\s+FROM\\s+[a-zA-Z_][a-zA-Z0-9_]*\\s*(?:WHERE\\s+.+)?$", Pattern.CASE_INSENSITIVE).matcher(sentencia)).find()) {
            delete(sentencia);
        } else {
            System.out.println("Sintaxis incorrecta");
        }


    }


//------------ Funciones de comandos

    public void use(String direccion) {

        rutaTrabajo = direccion;
        System.out.println("Usando direccion " + rutaTrabajo);
    }
    public boolean validar(String ruta) {
        File directorio = new File(ruta);
            return directorio.exists() && directorio.isDirectory();

    }
    //........
    public void showTable(String mostrarTablas) {
// PONERLE UN TRY CATCH ----------------
        File directorio = new File(rutaTrabajo);
        File[] archivos = directorio.listFiles();
        csv.leerShow(archivos, rutaTrabajo);

    }

    public void createTAble(String tablaCreada) {

        Matcher match;
        Pattern pattern = Pattern.compile("CREATE TABLE (\\w+) \\((.+)\\);", Pattern.CASE_INSENSITIVE);
        match = pattern.matcher(tablaCreada);
        if(match.find()){
            String nombreTabla = match.group(2) + ".csv";
            //System.out.println("Tabla " + nombreTabla);
        String col = match.group(2);
            String[] columnas = col.split(",");
            // System.out.println("COlumnas:");
            csv.crearTabla(nombreTabla, columnas, rutaTrabajo);
        }else {
            System.out.println("Sintaxis con CREATE TABLE incorrecto");
        }

    }

    public void select(String selectTable) {


    }

    public void dropTable(String dropTable) {
        Matcher match;
        Pattern pattern = Pattern.compile("DROP TABLE (\\w+);$",Pattern.CASE_INSENSITIVE);
        match = pattern.matcher(dropTable);
        if (match.find()) {
            String nombreTabla = match.group(1) + ".csv";
            System.out.println(match.group(1));
            File directorio = new File(rutaTrabajo + "/" + nombreTabla);
            if (directorio.exists()) {
                if (directorio.delete()) {
                    System.out.println("Ha sido borrado la tabla " + dropTable);
                } else {
                    System.out.println("No se pudo borrar la tabla");
                }
            } else {
                System.out.println("La tabla no existe");
            }
        }
    }

    public void delete(String delete){
        System.out.println("La sentencia ha sido borrado");
    }







    //_________________GETTERS y setters
    public String getRutaTrabajo() {
        return rutaTrabajo;
    }

    public void setRutaTrabajo(String rutaTrabajo) {
        this.rutaTrabajo = rutaTrabajo;
    }

    public String getSentencia() {
        return sentenciaSql;
    }

    public void setSentencia(String sentencia) {
        this.sentenciaSql = sentencia;
    }






}


