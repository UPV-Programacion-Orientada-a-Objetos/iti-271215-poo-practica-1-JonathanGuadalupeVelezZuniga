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
            if (validar(rutaNueva)) {
                use(rutaNueva);

            } else {
                System.out.println("ERROR. El diredtorio no existe");
            }


        } else if ((matcher = Pattern.compile("SHOW TABLES;$", Pattern.CASE_INSENSITIVE).matcher(sentencia)).find()) {
            showTable(sentencia);


        } else if ((matcher = Pattern.compile("^\\s*CREATE\\s+TABLE\\s+[a-zA-Z_][a-zA-Z0-9_]*\\s*\\(.*\\)\\s*;$", Pattern.CASE_INSENSITIVE).matcher(sentencia)).find()) {
            createTAble(sentencia);

        } else if ((matcher = Pattern.compile("^SELECT\\s+(\\*|\\w+)\\s+FROM\\s+(\\w+);$", Pattern.CASE_INSENSITIVE).matcher(sentencia)).find()) {
            String nombre = matcher.group(1);
            String columnas = matcher.group(2);
            select(nombre, columnas);
        } else if ((matcher = Pattern.compile("DROP TABLE\\s+(\\w+);$", Pattern.CASE_INSENSITIVE).matcher(sentencia)).find()) {
            dropTable(sentencia);
        } else if ((matcher = Pattern.compile("^DELETE\\s+FROM\\s+[a-zA-Z_][a-zA-Z0-9_]*\\s*(?:WHERE\\s+.+)?$", Pattern.CASE_INSENSITIVE).matcher(sentencia)).find()) {
            delete(sentencia); // Falta crear metodo
        } else if ((matcher = Pattern.compile("^INSERT INTO (\\w+) \\((.+)\\) VALUES \\((.+)\\);$", Pattern.CASE_INSENSITIVE).matcher(sentencia)).find()) {
            String nombreTabla = matcher.group(1);
            String columnas = matcher.group(2);
            String valores = matcher.group(3);
            insertInto(nombreTabla, columnas.split(","), valores.split(","));

        } else {
            System.out.println("Sintaxis incorrecta");
        }
    }
    //Falta crear UPDATE, Where conditional, and y or





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

    public void select(String colum, String nomTabla) {
        if (rutaTrabajo != null) {
            String rutaF = rutaTrabajo + "/" + nomTabla + ".csv";
            File tablaRuta = new File(rutaF);

            if (tablaRuta.isFile() && tablaRuta.exists() && tablaRuta.getName().endsWith(".csv")) {
                try (BufferedReader buffer = new BufferedReader(new FileReader(tablaRuta))) {
                    String[] columnas = buffer.readLine().split(",");
                    int columnIndex = -1;

                    if (colum.equals("*")) {
                        for (String columna : columnas) {
                            System.out.print(columna + "\t");
                        }
                        System.out.println();
                    } else {
                        String[] selectedColumns = colum.split(",");
                        for (String colSeleccionadas : selectedColumns) {
                            for (int i = 0; i < columnas.length; i++) {
                                if (columnas[i].equals(colSeleccionadas)) {
                                    columnIndex = i;
                                    System.out.print(columnas[i] + "\t");
                                    break;
                                }
                            }
                        }
                        System.out.println();
                    }

                    String valores;
                    while ((valores = buffer.readLine()) != null) {
                        String[] valor = valores.split(",");
                        for (String val : valor) {
                            System.out.print(val+ "\t");
                        }
                        System.out.println();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("La tabla no existe en la ruta de trabajo: " + rutaTrabajo);
            }
        } else {
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


                    BufferedReader bufer = new BufferedReader(new InputStreamReader(System.in));
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

    public void insertInto(String tablaNombre, String[] columna, String[] valores){
        if(rutaTrabajo != null){


        String rutaArchivo = rutaTrabajo + "/" + tablaNombre + ".csv";
        File tabla = new File(rutaArchivo);

        if(!tabla.exists()){
                System.out.println("La tabla ingresada no existe en el directorio: " + rutaArchivo);
                return;
            }




        try (FileWriter writer = new FileWriter(rutaArchivo, true)) {
            StringBuilder linea = new StringBuilder();
            for (int i = 0; i < columna.length; i++) {
                if (i > 0) {
                    linea.append(",");
                }
                linea.append(valores[i]);
            }
            writer.append(linea.toString()).append("\n");
            System.out.println("Se ha insertado una fila en la tabla " + tablaNombre);
        } catch (IOException e) {
            e.printStackTrace();
        }
        }else{
            System.out.println("No se ha ingresado una ruta de trabajo");
        }

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
