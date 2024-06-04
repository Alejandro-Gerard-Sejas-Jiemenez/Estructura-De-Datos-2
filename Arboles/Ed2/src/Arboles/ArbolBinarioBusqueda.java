package Arboles;

import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;

public class ArbolBinarioBusqueda<K extends Comparable<K>,V> implements IArbolBusqueda<K,V>
{
    protected NodoBinario<K,V>raiz;
    public ArbolBinarioBusqueda()
    {
       this.toString();
    }
    @Override
    public void insertar(K claveAInsertar, V valorAsociado) {
        if(claveAInsertar==null)
        {throw new UnsupportedOperationException("Dato no Valido");
        }
        if(valorAsociado==null)
        {
            throw new UnsupportedOperationException("Dato no Valido");
        }
        if (esArbolVacio())
        {
            this.raiz=new NodoBinario<>(claveAInsertar,valorAsociado);
            return;
        }
        NodoBinario<K,V>NodoAnterior=NodoBinario.nodoVacio();
        NodoBinario<K,V>NodoActual=this.raiz;

        do
        {
            K ClaveNodoACtual=NodoActual.getClave();
            int compacion=claveAInsertar.compareTo(ClaveNodoACtual);
            NodoAnterior=NodoActual;
            if (compacion<0)
            {NodoActual=NodoActual.getHijoizquierdo();
            }
            else if(compacion>0)
            {NodoActual=NodoActual.getHijoderecho();
            }
            else {
                NodoActual.setValor(valorAsociado);
                return;
            }

        }while (!NodoBinario.esNodoVacio(NodoActual));

        NodoBinario<K,V> Nodo=new NodoBinario<K,V>(claveAInsertar,valorAsociado);

        if (claveAInsertar.compareTo(NodoAnterior.getClave())<0)
        {
            NodoAnterior.setHijoizquierdo(Nodo);
        }else {
            NodoAnterior.setHijoderecho(Nodo);
        }

        // Insertar(raiz, claveAInsertar, valorAsociado);

    }

    @Override
    public V eliminar(K claveAEliminar) {
        V valorARetornar=this.buscar(claveAEliminar);
        if (valorARetornar==null)
        {
            return null;
        }
        this.raiz=eliminar(this.raiz,claveAEliminar);
        return valorARetornar;
    }
    private NodoBinario<K,V> eliminar(NodoBinario<K,V> nodoActual, K claveAlEliminar)
    {
        K claveDelNodoActual=nodoActual.getClave();
        if (claveAlEliminar.compareTo(claveDelNodoActual)<0)
        {//cuando toca ir por la derecha
            NodoBinario<K,V> supuestoNuevoHijoIzquierdo=eliminar(nodoActual.getHijoizquierdo(),claveAlEliminar);
            nodoActual.setHijoizquierdo(supuestoNuevoHijoIzquierdo);
            return nodoActual;
        }
        if (claveAlEliminar.compareTo(claveDelNodoActual)>0)
        {
            NodoBinario<K,V> supuestoNuevoHijoDerecho=eliminar(nodoActual.getHijoderecho(),claveAlEliminar);
            nodoActual.setHijoderecho(supuestoNuevoHijoDerecho);
            return nodoActual;
        }
        //caso 1
        if (nodoActual.esHoja())
        {return NodoBinario.nodoVacio();
        }
        //caso2.1
        if (!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho())
        {

            return nodoActual.getHijoizquierdo();
        }
        //caso2.2
        if (!nodoActual.esVacioHijoDerecho()&& nodoActual.esVacioHijoIzquierdo())
        {
            return nodoActual.getHijoderecho();
        }
        //caso 3
        NodoBinario<K,V>NodoSucesor=this.obtenerNodoSucesor(nodoActual.getHijoderecho());
        NodoBinario<K,V> supuestoNuevoDerecho=eliminar(nodoActual.getHijoderecho(),NodoSucesor.getClave());
        nodoActual.setHijoderecho(supuestoNuevoDerecho);
        nodoActual.setClave(NodoSucesor.getClave());
        nodoActual.setValor(NodoSucesor.getValor());
        return nodoActual;
    }
//-------------------------------------------
protected NodoBinario<K,V>obtenerNodoSucesor(NodoBinario<K,V>nodo)
{
    NodoBinario<K,V> NodoAnterior=nodo;
    while(!NodoBinario.esNodoVacio(nodo))
    {
        NodoAnterior=nodo;
        nodo=nodo.getHijoizquierdo();
    }
    return NodoAnterior;
}

//-------------------------------------------

    @Override
    public V buscar(K clave) {
        if (clave==null)
        {    throw new UnsupportedOperationException("No se puede buscar una Clave nula");
        }
        if (esArbolVacio())
        {
            return null;
        }
        NodoBinario<K,V> NodoAux=raiz;
        do
        {
            if (NodoAux.getClave().compareTo(clave)==0)
            {
                return NodoAux.getValor();
            }
            if (NodoAux.getClave().compareTo(clave)<0)
            {
                NodoAux=NodoAux.getHijoderecho();
            }else
            {
                NodoAux=NodoAux.getHijoizquierdo();
            }
        }while (!NodoBinario.esNodoVacio(NodoAux));
        return null;
    }

    @Override
    public boolean contiene(K clave) {
        return this.buscar(clave)!=null;
    }

    @Override
    public int size() {
        int contador=0;
        List<K>  recorrido = new  ArrayList<>();
        if (!this.esArbolVacio())
        {
            Stack<NodoBinario<K,V>> pilaDeNodo=new Stack<>();
            pilaDeNodo.push(raiz);
            do {
                NodoBinario<K,V> NodAux=pilaDeNodo.pop();
                contador++;
                if (!NodAux.esVacioHijoDerecho())
                {pilaDeNodo.push(NodAux.getHijoderecho());
                }
                if(!NodAux.esVacioHijoIzquierdo())
                {
                    pilaDeNodo.push(NodAux.getHijoizquierdo());
                }
            }while(!pilaDeNodo.isEmpty());
        }
        return contador;
    }

    @Override
    public int altura() {
        return altura(raiz);
    }
    protected int altura(NodoBinario<K,V> NodoActual)//esta es el metodo de altura de forma recursiva
    {
        if (NodoBinario.esNodoVacio(NodoActual))
        {return 0;
        }else
        {
            int alturaIzq=this.altura(NodoActual.getHijoizquierdo());
            int alturaDer=this.altura(NodoActual.getHijoderecho());
            return alturaIzq>alturaDer? alturaIzq+1:alturaDer+1;
        }
    }
//-----------------


    //------------
    @Override
    public void vaciar() {
        this.raiz=NodoBinario.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoBinario.esNodoVacio(raiz);
    }

    @Override
    public int nivel() {
        return nivel(raiz);
    }
    protected int nivel(NodoBinario<K,V> NodoActual)//esta es el metodo de nivel de forma recursiva
    {
        if (NodoBinario.esNodoVacio(NodoActual))
        {return -1;
        }else
        {
            int nivelIzq=this.nivel(NodoActual.getHijoizquierdo());
            int nivelDer=this.nivel(NodoActual.getHijoderecho());
            return nivelIzq>nivelDer? nivelIzq+1:nivelDer+1;
        }
    }

    @Override
    public List<K> recorridoEnInOrden() {
        List<K> recorrido=new ArrayList<>();
        recorridoEnInOrden(this.raiz,recorrido);
        return recorrido;
    }
    private void recorridoEnInOrden(NodoBinario<K,V> NodoActual,List<K> recorrido)
    {
        if(NodoBinario.esNodoVacio(NodoActual))
        {
            return;
        }else
        {
            recorridoEnInOrden(NodoActual.getHijoizquierdo(),recorrido);
            recorrido.add(NodoActual.getClave());
            recorridoEnInOrden(NodoActual.getHijoderecho(),recorrido);
        }
    }
    @Override
    public List<K> recorridoEnPreOrden() {
        List<K>Recorrido=new ArrayList<>();
        if (!esArbolVacio())
        {
            Stack<NodoBinario<K,V>>Pila=new Stack<>();
            Pila.push(raiz);
            do
            {
                NodoBinario<K,V>NodoAux=Pila.pop();
                Recorrido.add(NodoAux.getClave());
                if (!NodoAux.esVacioHijoDerecho())
                {
                    Pila.push(NodoAux.getHijoderecho());
                }
                if (!NodoAux.esVacioHijoIzquierdo())
                {
                    Pila.push(NodoAux.getHijoizquierdo());
                }
            }while(!Pila.isEmpty());
        }
        return Recorrido;
    }

    @Override
    public List<K> recorridoEnPostOrden() {
        List<K>  recorrido = new  ArrayList<>();
        if (!this.esArbolVacio())
        {
            Stack<NodoBinario<K,V>> pila=new Stack<>();
            this.meterAPilaParaPostOrden(pila, raiz);
            do{
                NodoBinario<K,V> NodoActual=pila.pop();//elimina y devuelve el elemento superior
                recorrido.add(NodoActual.getClave());
                if (!pila.isEmpty())
                {
                    NodoBinario<K,V> NodoDeltope=pila.peek();//devuelve el elemento superior de la pila
                    if (NodoDeltope.getHijoizquierdo()==NodoActual)
                    {
                        this.meterAPilaParaPostOrden(pila,NodoDeltope.getHijoderecho());
                    }
                }
            }while (!pila.isEmpty());
        }
        return recorrido;
    }
    private void meterAPilaParaPostOrden(Stack<NodoBinario<K,V>> pila,NodoBinario<K,V> NodoActual)
    {
        while (!NodoBinario.esNodoVacio(NodoActual))
        {
            pila.push(NodoActual);//agregar elemento al tope de la pila
            if (!NodoActual.esVacioHijoIzquierdo())
            {
                NodoActual=NodoActual.getHijoizquierdo();
            }else
            {
                NodoActual=NodoActual.getHijoderecho();
            }
        }
    }

    @Override
    public List<K> recorridoPorNiveles() {
        List<K>Recorrido=new ArrayList<>();
        if (!esArbolVacio())
        {
            Queue<NodoBinario<K,V>> Cola= new LinkedList<>();
            Cola.offer(raiz);
            do
            {
                NodoBinario<K,V> NodoAux=Cola.poll();
                Recorrido.add(NodoAux.getClave());
                if (!NodoAux.esVacioHijoIzquierdo())
                {
                    Cola.offer(NodoAux.getHijoizquierdo());
                }
                if (!NodoAux.esVacioHijoDerecho())
                {
                    Cola.offer(NodoAux.getHijoderecho());
                }
            }while (!Cola.isEmpty());
        }
        return Recorrido;
    }









    @Override
    public String toString() {
        return crearRepresentacion(raiz, "", "", false);
    }

    private String crearRepresentacion(NodoBinario<K, V> nodo, String representacion, String prefijo, boolean esIzquierdo) {
        if (nodo == null) {
            if (esIzquierdo==true){
                return representacion + prefijo + (esIzquierdo ? "├──" : "└──") +"(I) "+ "null\n";
            }
            else {
                return representacion + prefijo + (esIzquierdo ? "├──" : "└──") +"(R) " +"null\n";
            }

        }

        if (nodo==raiz){
            representacion += prefijo + (esIzquierdo ? "├──" : "└──(R) ") + nodo.getClave() + "\n";
        }
           if (esIzquierdo==true  && nodo.getClave()!=raiz.getClave()){
               representacion += prefijo + (esIzquierdo ? "├──" : "└──")+"(I) " + nodo.getClave() + "\n";

           }else if(esIzquierdo==false  && nodo.getClave()!=raiz.getClave())  {
               representacion += prefijo + (esIzquierdo ? "├──" : "└──")+"(D) " + nodo.getClave() + "\n";
           }


        String nuevoPrefijo = prefijo + (esIzquierdo ? "│   " : "    ");
        representacion = crearRepresentacion(nodo.getHijoizquierdo(), representacion, nuevoPrefijo, true);
        representacion = crearRepresentacion(nodo.getHijoderecho(), representacion, nuevoPrefijo, false);

        return representacion;
    }
    /*
    * @Override
public String toString() {
    return crearRepresentacion(raiz, "", "", "R");
}

private String crearRepresentacion(NodoBinario<K, V> nodo, String representacion, String prefijo, String tipo) {
    if (nodo == null) {
        return representacion + prefijo + (tipo.equals("I") ? "├── " : (tipo.equals("D") ? "└── " : "")) + "null\n";
    }

    representacion += prefijo + (tipo.equals("R") ? "" : (tipo.equals("I") ? "├── " : "└── ")) + nodo.getClave() + " (" + tipo + ")\n";

    String nuevoPrefijo = prefijo + (tipo.equals("I") ? "│   " : "    ");
    representacion = crearRepresentacion(nodo.getHijoizquierdo(), representacion, nuevoPrefijo, "I");
    representacion = crearRepresentacion(nodo.getHijoderecho(), representacion, nuevoPrefijo, "D");

    return representacion;
}
*/



}
