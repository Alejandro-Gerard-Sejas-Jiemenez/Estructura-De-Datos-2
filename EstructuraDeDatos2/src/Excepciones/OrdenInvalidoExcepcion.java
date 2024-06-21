package Excepciones;

public class OrdenInvalidoExcepcion extends Exception{

    public OrdenInvalidoExcepcion() {
        super("Orden de arbol minimo 3");
    }

    public OrdenInvalidoExcepcion(String message) {
        super(message);
    }

}
