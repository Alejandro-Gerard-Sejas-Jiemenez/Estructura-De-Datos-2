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

      /*  arbolBusqueda.insertar(850, "Gerson");
        arbolBusqueda.insertar(870, "Alejandro");
        arbolBusqueda.insertar(920, "Ercik");
        arbolBusqueda.insertar(855,"Santiago");
        arbolBusqueda.insertar(862, "Yoselin");
        arbolBusqueda.insertar(868, "Gerson");
        arbolBusqueda.insertar(890, "Alejandro");
        arbolBusqueda.insertar(950, "Ercik");
        arbolBusqueda.insertar(960,"Santiago");
*/
        System.out.println("Recorrido por niveles: " + arbolBusqueda.recorridoPorNiveles());
        System.out.println("Recorrido por InOrden: " + arbolBusqueda.recorridoEnInOrden());
        System.out.println("Recorrido por PosOrden: " + arbolBusqueda.recorridoEnPostOrden());
        System.out.println("Altura del arbol: "+arbolBusqueda.altura());
        System.out.println("Nivel del arbol: "+arbolBusqueda.nivel());
        System.out.println("Size del arbol: "+arbolBusqueda.size());
        System.out.println(arbolBusqueda);
        System.out.println("ELIMINAR del arbol: "+arbolBusqueda.eliminar(99));
      //   System.out.println("CONTIENE del arbol: "+arbolBusqueda.contiene(80));
      //  System.out.println("BUSCAR del arbol: "+arbolBusqueda.buscar(80));
        System.out.println("Recorrido por niveles: " + arbolBusqueda.recorridoPorNiveles());
        System.out.println("Recorrido por InOrden: " + arbolBusqueda.recorridoEnInOrden());
        System.out.println("Altura del arbol: "+arbolBusqueda.altura());
        System.out.println("Nivel del arbol: "+arbolBusqueda.nivel());
        System.out.println("Size del arbol: "+arbolBusqueda.size());
        System.out.println(arbolBusqueda);
    }

}
