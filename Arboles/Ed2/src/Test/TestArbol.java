package Test;
import Arboles.*;

import java.util.Scanner;

public class TestArbol {
    public static void main (String Argumrnt[]) throws OrdenInvalidoExcepcion
    {
        IArbolBusqueda<Integer ,String> arbolBusqueda;

        Scanner entrada =new Scanner(System.in);
        System.out.println("Elija un Tipo de arbol(ABB,AVL,ABM,AB) :");
        String tipoArbol=entrada.next();
        tipoArbol=tipoArbol.toUpperCase();
        switch (tipoArbol) {
            case "ABB":
                arbolBusqueda=new ArbolBinarioBusqueda<>();
                break;
            case "AVL":
                arbolBusqueda=new AVL<>();
                break;
            case "ABM":
                arbolBusqueda=new ArbolMViasBusqueda<>(4);
                break;
            case "AB":
                arbolBusqueda=new ArbolB<>(4);
                break;
            default:
                System.out.println("Tipo de arbol invalido,eligiendrbolo arbolBinarioBUsqueda");
                arbolBusqueda=new ArbolBinarioBusqueda<>();
                break;
        }
/*
        arbolBusqueda.insertar(300,"Kevin");
        arbolBusqueda.insertar(500, "Claudia");
        arbolBusqueda.insertar(100, "Yoselin");
        arbolBusqueda.insertar(50, "Gerson");
        arbolBusqueda.insertar(400, "Alejandro");
        arbolBusqueda.insertar(800, "Ercik");
        arbolBusqueda.insertar(90, "Gerson");
        arbolBusqueda.insertar(91, "Alejandro");
        arbolBusqueda.insertar(70, "Ercik");
        arbolBusqueda.insertar(75,"Santiago");
        arbolBusqueda.insertar(99, "Yoselin");
*/

        arbolBusqueda.insertar(80,"Kevin");
        arbolBusqueda.insertar(120, "Claudia");
        arbolBusqueda.insertar(200, "Yoselin");
        arbolBusqueda.insertar(50, "Gerson");
        arbolBusqueda.insertar(70, "Alejandro");
        arbolBusqueda.insertar(75, "Ercik");
        arbolBusqueda.insertar(72, "Gerson");
        arbolBusqueda.insertar(98, "Alejandro");
        arbolBusqueda.insertar(110, "Ercik");
        arbolBusqueda.insertar(130, "Alejandro");
        arbolBusqueda.insertar(140, "Ercik");
        arbolBusqueda.insertar(150, "Gerson");
        arbolBusqueda.insertar(134, "Alejandro");
        arbolBusqueda.insertar(160, "Ercik");
        arbolBusqueda.insertar(170, "Ercik");
        arbolBusqueda.insertar(190, "Gerson");
        arbolBusqueda.insertar(158, "Alejandro");
        arbolBusqueda.insertar(400, "Ercik");
        arbolBusqueda.insertar(500, "Alejandro");
        arbolBusqueda.insertar(560, "Ercik");

        System.out.println("Recorrido por niveles: " + arbolBusqueda.recorridoPorNiveles());
        System.out.println("Recorrido por InOrden: " + arbolBusqueda.recorridoEnInOrden());
        System.out.println("Recorrido por PosOrden: " + arbolBusqueda.recorridoEnPostOrden());
        System.out.println("Altura del arbol: "+arbolBusqueda.altura());
        System.out.println("Nivel del arbol: "+arbolBusqueda.nivel());
        System.out.println("Size del arbol: "+arbolBusqueda.size());
        System.out.println("ELIMINAR del arbol: "+arbolBusqueda.eliminar(158));
      //   System.out.println("CONTIENE del arbol: "+arbolBusqueda.contiene(80));
      //  System.out.println("BUSCAR del arbol: "+arbolBusqueda.buscar(80));
        System.out.println("Recorrido por niveles: " + arbolBusqueda.recorridoPorNiveles());
        System.out.println("Recorrido por InOrden: " + arbolBusqueda.recorridoEnInOrden());
        System.out.println("Altura del arbol: "+arbolBusqueda.altura());
        System.out.println("Nivel del arbol: "+arbolBusqueda.nivel());
        System.out.println("Size del arbol: "+arbolBusqueda.size());
      //  System.out.println(arbolBusqueda);
    }

}
