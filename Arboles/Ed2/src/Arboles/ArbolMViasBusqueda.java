package Arboles;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ArbolMViasBusqueda<K extends Comparable <K>,V> implements IArbolBusqueda<K, V> {
    protected NodoMVias<K, V> raiz;
    protected int orden;
    protected static final int ORDEN_MINIMO = 3;
    protected static final int POSICION_INVALIDA = -1;

    public ArbolMViasBusqueda() {

        this.orden = ArbolMViasBusqueda.ORDEN_MINIMO;
    }

    public ArbolMViasBusqueda(int orden) throws OrdenInvalidoExcepcion {
        if (orden < ArbolMViasBusqueda.ORDEN_MINIMO) {
            throw new OrdenInvalidoExcepcion();
        }
        this.orden = orden;
    }

    @Override
    public void insertar(K claveAInsertar, V valorAsociado) {
        if (claveAInsertar == null) {
            throw new UnsupportedOperationException("Dato no Valido");
        }
        if (valorAsociado == null) {
            throw new UnsupportedOperationException("Dato no Valido");
        }
        if (esArbolVacio()) {
            this.raiz = new NodoMVias<>(this.orden, claveAInsertar, valorAsociado);
            return;
        }
        NodoMVias<K, V> nodoAux = this.raiz;
        do {
            int posicionClaveAInsertar = this.buscarPosicionDeClave(nodoAux, claveAInsertar);
            if (posicionClaveAInsertar != ArbolMViasBusqueda.POSICION_INVALIDA) {
                nodoAux.setValor(posicionClaveAInsertar, valorAsociado);
                nodoAux = NodoMVias.nodoVacio();
            } else if (nodoAux.esHoja()) {
                //el nodo auxiliar es hoja y la clave no esta en el nodo
                if (nodoAux.ClavesLlenas()) {
                    NodoMVias<K, V> nodonuevo = new NodoMVias<>(this.orden, claveAInsertar, valorAsociado);
                    int posicionPorDondeBajar = this.posicionDondeBajar(nodoAux, claveAInsertar);
                    nodoAux.setHijo(posicionPorDondeBajar, nodonuevo);
                } else {
                    this.insertarClaveValorOrdenando(nodoAux, claveAInsertar, valorAsociado);//tenfo que mejorar
                }
                nodoAux = NodoMVias.nodoVacio();
            } else {
                int posicionPorDondeBajar = this.posicionDondeBajar(nodoAux, claveAInsertar);
                if (nodoAux.esHijoVacio(posicionPorDondeBajar)) {
                    NodoMVias<K, V> nodonuevo = new NodoMVias<>(this.orden, claveAInsertar, valorAsociado);
                    nodoAux.setHijo(posicionPorDondeBajar, nodonuevo);
                    nodoAux = NodoMVias.nodoVacio();
                } else {
                    nodoAux = nodoAux.getHijo(posicionPorDondeBajar);
                }
            }
        } while (!nodoAux.NodoVacio(nodoAux));
    }

    //---------------------------------------------------------------------------------------------------------------------
    protected void insertarClaveValorOrdenando(NodoMVias<K, V> nodo, K claveAlInsertar, V valorAsociado) {
        // Si el nodo es nulo, creamos un nuevo nodo con la clave y valor proporcionados//tenemos que mojorar esto
        if (nodo.nroDeClavesNoVacias()==0){
            nodo.setClave(0,claveAlInsertar);
            nodo.setValor(0,valorAsociado);
        }else {
             if (!nodo.ClavesLlenas()) {
                boolean b = true;
                for (int i = 0; i < nodo.nroDeClavesNoVacias() && b; i++) {
                    K claveTurno = nodo.getClave(i);
                    if (claveTurno.compareTo(claveAlInsertar) > 0) {
                        int posFinal = nodo.nroDeClavesNoVacias();
                        int nro2 = nodo.nroDeClavesNoVacias();
                        for (int j = i; j < nro2; j++) {
                            nodo.setClave(posFinal, nodo.getClave(posFinal - 1));
                            nodo.setValor(posFinal, nodo.getValor(posFinal - 1));
                            posFinal--;
                        }
                        b = false;
                        nodo.setClave(i, claveAlInsertar);
                        nodo.setValor(i, valorAsociado);
                    }
                    if (claveTurno.compareTo(claveAlInsertar) < 0 && i + 1 == nodo.nroDeClavesNoVacias()) {
                        b = false;
                        nodo.setClave(i + 1, claveAlInsertar);
                        nodo.setValor(i + 1, valorAsociado);
                    }
                    if (claveTurno.compareTo(claveAlInsertar) == 0) {
                        nodo.setValor(i, valorAsociado);
                        b = false;
                    }
                }
            }
        }
    }

    protected int posicionDondeBajar(NodoMVias<K, V> nodo, K claveInsetar) {
        for (int i = 0; i < nodo.nroDeClavesNoVacias(); i++) {
            K claveNodoTurno = nodo.getClave(i);
            if (claveNodoTurno.compareTo(claveInsetar) > 0) {
                return i;
            }
            if (claveNodoTurno.compareTo(claveInsetar) < 0 ) {
                return i+1;
            }
        }
        return ArbolMViasBusqueda.POSICION_INVALIDA;
    }

    protected int buscarPosicionDeClave(NodoMVias<K, V> nodo, K claveAlBuscar)//supuestamente funciona
    {
       // int o= nodo.nroDeClavesNoVacia();
        for (int i = 0; i < nodo.nroDeClavesNoVacias(); i++) {
            K claveActual = nodo.getClave(i);
            if (claveActual.compareTo(claveAlBuscar) == 0) {
                return i;
            }
        }
        return ArbolMViasBusqueda.POSICION_INVALIDA;
    }

//---------------------------------------------------------------------------------------------------------------------


    @Override
    public V eliminar(K claveAEliminar) {
        V valorARetornar = this.buscar(claveAEliminar);
        if (valorARetornar == null) {
            return null;
        }
        this.raiz = eliminar(this.raiz, claveAEliminar);
        return valorARetornar;
    }

    private NodoMVias<K, V> eliminar(NodoMVias<K, V> nodoActual, K claveAlEliminar) {
        for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            K claveEnTurno = nodoActual.getClave(i);
            if (claveAlEliminar.compareTo(claveEnTurno) == 0) {
                if (nodoActual.esHoja()) {
                    eliminarClaveDeNodoDePosicion(nodoActual, i);
                    if (!nodoActual.hayClavesNoVacia()) {
                        return NodoMVias.nodoVacio();
                    }
                    return nodoActual;
                }///si llego aca el nodo actual no es hoja
                K claveDeRemplazo;
                if (this.hayHijosMasAdelanteDeLaPosicion(nodoActual, i)) {
                    //caso 2
                    claveDeRemplazo = this.obtenerSucesorEnInOrden(nodoActual, i);
                } else {
                    //caso 3
                    claveDeRemplazo = this.obtenerPredecesorEnInOrden(nodoActual, i);
                }
                V valorDeRemplazar = this.buscar(claveDeRemplazo);
                nodoActual = eliminar(nodoActual, claveDeRemplazo);
                nodoActual.setClave(i, claveDeRemplazo);
                nodoActual.setValor(i, valorDeRemplazar);
                return nodoActual;

            }
            if (claveAlEliminar.compareTo(claveEnTurno) < 0) {
                    NodoMVias<K, V> supuestoNuevoHijo = eliminar(nodoActual.getHijo(i), claveAlEliminar);
                    nodoActual.setHijo(i, supuestoNuevoHijo);
                    return nodoActual;
            }
        }///fin del for
        NodoMVias<K, V> supuestoHijo = eliminar(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()), claveAlEliminar);
        nodoActual.setHijo(nodoActual.nroDeClavesNoVacias(), supuestoHijo);
        return nodoActual;
    }

    //------------------------------------------------------------------------------------------------------------------
    protected K obtenerSucesorEnInOrden(NodoMVias<K,V> nodo, int i){
        K claveNodo=nodo.getClave(i);
        List<K> listaInOrden=this.recorridoEnInOrden();
        int posicion=listaInOrden.indexOf(claveNodo);
        K claveRetorno=listaInOrden.get(posicion+1);
        return claveRetorno;
    }

    protected K obtenerPredecesorEnInOrden(NodoMVias<K,V> nodo, int i){
        K claveNodo=nodo.getClave(i);
        List<K> listaInOrden=this.recorridoEnInOrden();
        int posicion=listaInOrden.indexOf(claveNodo);
        K claveRetorno=listaInOrden.get(posicion-1);
        return claveRetorno;
    }

    protected boolean hayHijosMasAdelanteDeLaPosicion(NodoMVias<K, V> nodo, int p) {
        return NodoMVias.NodoVacio(nodo.getHijo(p+1))? false : true;
    }

    protected void eliminarClaveDeNodoDePosicion(NodoMVias<K, V> nodo, int p) {///fata terminar eso
        for (int i = 0; i < nodo.nroDeClavesNoVacias(); i++) {
            if (i == p) {
                nodo.setClave(i, null);
                nodo.setValor(i,null);
                for (int j = i; j < nodo.nroDeClavesNoVacias(); j++) {
                    nodo.setClave(j, nodo.getClave(j + 1));
                    nodo.setValor(j, nodo.getValor(j + 1));
                    nodo.setClave(j+1,null);
                    nodo.setValor(j+1,null);

                }
            }
        }

    }

    //------------------------------------------------------------------------------------------------------------------
    @Override
    public V buscar(K claveABuscar) {
        if (claveABuscar == null) {
            throw new UnsupportedOperationException("No se puede buscar clave nula");
        }
        if (!esArbolVacio()) {
            NodoMVias<K, V> nodoActual = this.raiz;
            do {
                boolean CambioDeNodo = false;
                for (int i = 0; i < nodoActual.nroDeClavesNoVacias() &&
                        !CambioDeNodo; i++) {
                    K ClaveActual = nodoActual.getClave(i);
                    if (claveABuscar.compareTo(ClaveActual) == 0) {
                        return nodoActual.getValor(i);
                    }
                    if (claveABuscar.compareTo(ClaveActual) < 0) {
                        CambioDeNodo = true;
                        nodoActual = nodoActual.getHijo(i);
                    }
                }//necesito de este para procesar el nodo
                if (!CambioDeNodo) {
                    nodoActual = nodoActual.getHijo(nodoActual.nroDeClavesNoVacias());
                }
            } while (!NodoMVias.NodoVacio(nodoActual));
        }
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
        if (!esArbolVacio())
        {
            Queue<NodoMVias<K,V>> colaDeNodos=new LinkedList<>();
            colaDeNodos.offer(raiz);
            do {
                NodoMVias<K,V> nodoActual=colaDeNodos.poll();
                contador++;
                for (int i=0;i<this.orden;i++)
                {
                    if (!nodoActual.esHijoVacio(i))
                    {
                        colaDeNodos.offer(nodoActual.getHijo(i));
                    }
                }
            }while(!colaDeNodos.isEmpty());
        }
        return contador;
    }

    @Override
    public int altura() {
        return altura(raiz);
    }

    protected int altura(NodoMVias<K,V> NodoActual)//esta es el metodo de altura de forma recursiva
    {
        if (NodoMVias.NodoVacio(NodoActual)) {
            return 0;
        }
            int alturaMayor=0;
            for (int i=0;i<this.orden;i++) {
                int  alturaHijoTurno=altura(NodoActual.getHijo(i));
                if (alturaHijoTurno>alturaMayor) {
                    alturaMayor=alturaHijoTurno;
                }
            }
            return alturaMayor+1;
    }

    @Override
    public void vaciar() {
     this.raiz=NodoMVias.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoMVias.NodoVacio(raiz);
    }

    @Override
    public int nivel() {
        return nivel(this.raiz);
    }

    protected int nivel(NodoMVias<K,V> NodoActual)//esta es el metodo de nivel de forma recursiva
    {
        if (NodoMVias.NodoVacio(NodoActual))
        {return -1;
        }else
        {
            int nivelMayor=-1;
            for (int i=0;i<this.orden;i++)
            {
                int  nivelHijoTurno=nivel(NodoActual.getHijo(i));
                if (nivelHijoTurno>nivelMayor)
                {
                    nivelMayor=nivelHijoTurno;
                }
            }
            return nivelMayor+1;
        }
    }

    @Override
    public List<K> recorridoEnInOrden() {
        List<K> recorrido = new ArrayList<>();
        recorridoEnInOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnInOrden(NodoMVias<K, V> NodoActual, List<K> recorrido) {
        if (NodoMVias.NodoVacio(NodoActual)) {
            return;
        } else {
            for (int i = 0; i < NodoActual.nroDeClavesNoVacias(); i++) {
                recorridoEnInOrden(NodoActual.getHijo(i), recorrido);
                recorrido.add(NodoActual.getClave(i));
            }
            recorridoEnInOrden(NodoActual.getHijo(NodoActual.nroDeClavesNoVacias()), recorrido);
        }

    }

    @Override
    public List<K> recorridoEnPreOrden() {
        List<K> recorrido = new ArrayList<>();
        recorridoEnPreOrden(this.raiz, recorrido);
        return recorrido;
    }
    private void recorridoEnPreOrden(NodoMVias<K,V> NodoActual,List<K> recorrido)
    {
        if(NodoMVias.NodoVacio(NodoActual))
        {
            return;
        }else
        {
            for (int i = 0; i<NodoActual.nroDeClavesNoVacias(); i++)
            {
                recorrido.add(NodoActual.getClave(i));
                recorridoEnPreOrden(NodoActual.getHijo(i),recorrido);

            }
            recorridoEnPreOrden(NodoActual.getHijo(NodoActual.nroDeClavesNoVacias()),recorrido);
        }
    }



    @Override
    public List<K> recorridoEnPostOrden() {
        List<K> recorrido=new ArrayList<>();
        recorridoEnPostOrden(this.raiz,recorrido);
        return recorrido;
    }
    private void recorridoEnPostOrden(NodoMVias<K,V> NodoActual,List<K> recorrido)
    {
        if(NodoActual.NodoVacio(NodoActual))
        {
            return;
        }
        recorridoEnPostOrden(NodoActual.getHijo(0),recorrido);
        for (int i = 0; i<NodoActual.nroDeClavesNoVacias(); i++)
        {
            recorridoEnPostOrden(NodoActual.getHijo(i+1),recorrido);
            recorrido.add(NodoActual.getClave(i));
        }
    }


    @Override
    public List<K> recorridoPorNiveles() {
        {
            List<K> Recorrido = new ArrayList<>();
            if (!esArbolVacio()) {
                Queue<NodoMVias<K, V>> Cola = new LinkedList<>();
                Cola.offer(raiz);
                do {
                    NodoMVias<K, V> NodoAxtual = Cola.poll();
                    for (int i = 0; i < NodoAxtual.nroDeClavesNoVacias(); i++) {
                        Recorrido.add(NodoAxtual.getClave(i));
                        if (!NodoAxtual.esHijoVacio(i)) {
                            Cola.offer(NodoAxtual.getHijo(i));
                        }
                    }//fin del for
                    if (!NodoAxtual.esHijoVacio(NodoAxtual.nroDeClavesNoVacias())) {
                        Cola.offer(NodoAxtual.getHijo(NodoAxtual.nroDeClavesNoVacias()));
                    }
                } while (!Cola.isEmpty());
            }
            return Recorrido;
        }
    }



}

