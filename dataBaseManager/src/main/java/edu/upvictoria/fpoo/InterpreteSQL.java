package edu.upvictoria.fpoo;


import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class InterpreteSQL {
    ArchivosCSV csv = new ArchivosCSV();

    private String rutaTrabajo;
    private String sentenciaSql;
    BufferedReader bufer = new BufferedReader(new InputStreamReader(System.in));


//--------------COMANDOS

    public void sentenciaSQL(String sentencia) {
        Matcher matcher;

        if ((matcher = Pattern.compile("^USE (.+);$", Pattern.CASE_INSENSITIVE).matcher(sentencia)).find()) {
            String rutaNueva = matcher.group(1);
            if(validar(rutaNueva)){
                use(rutaNueva);

            }else {
                System.out.println("ERROR. El diredtorio no existe");
            }


        } else if ((matcher = Pattern.compile("SHOW TABLES;$", Pattern.CASE_INSENSITIVE).matcher(sentencia)).find()) {
            showTable(sentencia);


        } else if ((matcher = Pattern.compile("^\\s*CREATE\\s+TABLE\\s+[a-zA-Z_][a-zA-Z0-9_]*\\s*\\(.*\\)\\s*;$", Pattern.CASE_INSENSITIVE).matcher(sentencia)).find()) {
            createTAble(sentencia);

        } else if ((matcher = Pattern.compile("SELECT\\s+\\*\\s+FROM (.+);$", Pattern.CASE_INSENSITIVE).matcher(sentencia)).find()) {
            String nombre = matcher.group(1);
            String columnas = matcher.group(2);
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
        if (rutaTrabajo != null){
            File directorio = new File(rutaTrabajo);
            File[] archivos = directorio.listFiles();
            csv.leerShow(archivos, rutaTrabajo);
        }else{
            System.out.println("No se ha elegido una ruta de trabajo.");
        }

    }

    public void createTAble(String tablaCreada) {

        if(rutaTrabajo !=null){


        Matcher match;
        Pattern pattern = Pattern.compile("CREATE TABLE (\\w+) \\((.+)\\);", Pattern.CASE_INSENSITIVE);
        match = pattern.matcher(tablaCreada);
        if(match.find()){
            String nombreTabla = match.group(1);
            //System.out.println("Tabla " + nombreTabla);
            String col = match.group(2);
            String[] columnas = col.split(",");
            // System.out.println("COlumnas:");
            csv.crearTabla(nombreTabla, columnas, rutaTrabajo);
        }else {
            System.out.println("Sintaxis con CREATE TABLE incorrecto");
        }
        }else {
            System.out.println("No se ha elegido la ruta de trabajo");
        }
    }

    public void select(String selectTable) {
        if (rutaTrabajo != null){

        }else{
            System.out.println("No se ha elegido la ruta de trabajo");
        }


    }

    public void dropTable(String dropTable) {
        if (rutaTrabajo != null){
        Matcher match;
        Pattern pattern = Pattern.compile("DROP TABLE (\\w+);$",Pattern.CASE_INSENSITIVE);
        match = pattern.matcher(dropTable);
        if (match.find()) {
            String nombreTabla = match.group(1) + ".csv";
            File directorio = new File(rutaTrabajo + "/" + nombreTabla);
            if (directorio.exists()) {
                try {


                System.out.println("¿Realmente deseas borrar la tabla? s = si/n = no");
                String res = bufer.readLine().trim().toLowerCase();

                if (res.equals("s")){
                if (directorio.delete()) {
                    System.out.println("Ha sido borrado la tabla " + match.group(1));
                } else {
                    System.out.println("No se pudo borrar la tabla");
                }}else if( res.equals("n")){
                    System.out.println("Se ha cancelado la operacion");
                }
                } catch (IOException e){
                    e.printStackTrace();
                }
            } else {
                System.out.println("La tabla no existe");
            }
        }}else {
            System.out.println("No se ha elegido la ruta de trabajo");
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
