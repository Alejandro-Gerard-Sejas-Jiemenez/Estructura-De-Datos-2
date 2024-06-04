package Arboles;

import java.util.List;
public interface IArbolBusqueda <k extends Comparable <k>,v>
{
    void insertar(k clave,v valor);
    v eliminar (k clave);
    v buscar(k clave);
    boolean contiene(k clave);
    int size();
    int altura();
    void vaciar();
    boolean esArbolVacio();
    int nivel();
    List<k>recorridoEnInOrden();
    List<k>recorridoEnPreOrden();
    List<k>recorridoEnPostOrden();
    List<k>recorridoPorNiveles();

}