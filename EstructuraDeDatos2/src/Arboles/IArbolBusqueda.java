package Arboles;

import java.util.List;
/**
 *
 *
 * @param <K>
 * @param <V>
 */
public interface IArbolBusqueda<K extends Comparable<K>,V> {
    void insertar(K clave, V valor);
    V eliminar(K clave);
    V buscar(K clave);
    boolean contiene(K clave);
    int size();
    int altura();
    void vaciar();
    boolean esArbolVacio();
    int nivel();
    List<K> recorridoEnInOrden();
    List<K> recorridoEnPreOrden();
    List<K> recorridoEnPosOrden();
    List<K> recorridoPorNiveles();

}