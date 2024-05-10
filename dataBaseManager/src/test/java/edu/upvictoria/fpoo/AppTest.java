package edu.upvictoria.fpoo;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppTest extends TestCase {
    private String rutaTrabajo;

    public void testUse() {
        AppTest appTest = new AppTest();
        String ruta = "C:/mi/directorio";
        appTest.use(ruta);
        assertEquals(ruta, appTest.getRutaTrabajo());
    }

    public void use(String direccion) {
        rutaTrabajo = direccion;
        System.out.println("Usando dirección " + rutaTrabajo);
    }

    public String getRutaTrabajo() {
        return rutaTrabajo;
    }

    public void setRutaTrabajo(String rutaTrabajo) {
        this.rutaTrabajo = rutaTrabajo;
    }
    

}