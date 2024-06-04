package Arboles;

public class OrdenInvalidoExcepcion extends Exception{
    public OrdenInvalidoExcepcion()
    {
        super("Orden del arbol debe ser al menos 3");
    }
    public OrdenInvalidoExcepcion(String message)
    {
        super (message);
    }
}