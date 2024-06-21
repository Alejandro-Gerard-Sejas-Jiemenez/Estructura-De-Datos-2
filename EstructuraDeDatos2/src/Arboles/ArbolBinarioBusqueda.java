package Arboles;

import java.util.*;

public class ArbolBinarioBusqueda<K extends Comparable<K>,V>
        implements IArbolBusqueda<K,V> {
    protected NodoBinario<K, V> raiz;

    public ArbolBinarioBusqueda() {
    }

    public NodoBinario<K, V> recontruirConPostOrden(List<K> clavesInOrden,
                                                    List<V> valoresInOrden,
                                                    List<K> clavesNoInOrden) {
        if (clavesInOrden.size() > 1) {
            K tenito = clavesNoInOrden.getLast();
            int divisionInOrden = clavesInOrden.indexOf(tenito);
            List<K> leftSide = clavesInOrden.subList(0, divisionInOrden);
            List<K> rightSide = clavesInOrden.subList(divisionInOrden + 1, clavesInOrden.size());
            int divisionNoInOrden = leftSide.size();
            NodoBinario<K, V> leftSon = reconstruirConPostOrden(leftSide, valoresInOrden.subList(0, divisionInOrden), clavesNoInOrden.subList(0, divisionNoInOrden));
            NodoBinario<K, V> rightSon = reconstruirConPostOrden(rightSide, valoresInOrden.subList(divisionInOrden + 1, valoresInOrden.size()), clavesNoInOrden.subList(divisionNoInOrden, clavesNoInOrden.size() - 1));

            NodoBinario<K, V> head = new NodoBinario<>();
            head.setHijoIzquierdo(leftSon);
            head.setHijoDerecho(rightSon);
            return head;
        }
        if (clavesInOrden.size() == 1) {
            NodoBinario<K, V> head = new NodoBinario<>();
            head.setClave(clavesInOrden.getFirst());
            head.setValor(valoresInOrden.getFirst());
            return head;
        }
        return null;
    }

    public boolean esMonticulo() {
        if (this.esArbolVacio()) {
            return false;
        }
        return esMonticulo(this.raiz);
    }

    protected boolean esMonticulo(NodoBinario<K, V> nodoActual) {
        if (nodoActual.esHoja()) {
            return true;
        }
        boolean valueIzquierda = true;
        boolean valueDerecha = true;
        boolean currentValue = true;
        if (!nodoActual.esVacioHijoDerecho()) {
            currentValue = esMonticulo(nodoActual.getHijoDerecho());
            NodoBinario<K, V> hijoDerecho = nodoActual.getHijoDerecho();
            if (nodoActual.getClave().compareTo(hijoDerecho.getClave()) > 0) {
                valueDerecha = false;
            }
        }
        if (!nodoActual.esVacioHijoIzquierdo()) {
            currentValue = currentValue &&
                    esMonticulo(nodoActual.getHijoIzquierdo());
            NodoBinario<K, V> hijoIzquierdo = nodoActual.getHijoIzquierdo();
            if (nodoActual.getClave().compareTo(hijoIzquierdo.getClave()) > 0) {
                valueIzquierda = false;
            }
        }
        return currentValue && valueIzquierda && valueDerecha;
    }

    public boolean esAVL() {
        if (this.esArbolVacio()) {
            return true;
        }
        return this.esAVL(this.raiz);
    }

    protected boolean esAVL(NodoBinario<K, V> nodoAct) {
        if (!nodoAct.esHoja()) {
            boolean AVLIzquierda;
            if (!nodoAct.esVacioHijoIzquierdo()) {
                AVLIzquierda = esAVL(nodoAct.getHijoIzquierdo());
            } else {
                AVLIzquierda = true;
            }
            boolean AVLDerecha;
            if (!nodoAct.esVacioHijoDerecho()) {
                AVLDerecha = esAVL(nodoAct.getHijoDerecho());
            } else {
                AVLDerecha = true;
            }
            int alturaIzq = this.altura(nodoAct.getHijoIzquierdo());
            int alturaDer = this.altura(nodoAct.getHijoDerecho());
            return (AVLIzquierda && AVLDerecha && (alturaIzq - alturaDer < 2) && (alturaIzq - alturaDer > -2));
        }
        return true;
    }


    protected NodoBinario<K, V> reconstruirConPreOrden(List<K> clavesInOrden,
                                                       List<V> valoresInOrden, List<K> clavesNoInOrden) {
        return null;
    }

    protected NodoBinario<K, V> reconstruirConPostOrden(List<K> clavesInOrden,
                                                        List<V> valoresInOrden, List<K> clavesNoInOrden) {
        return null;
    }


    @Override
    public void insertar(K claveAInsertar, V valorAsociado) {
        if (claveAInsertar == null) {
            throw new IllegalArgumentException("Clave a insertar no puede ser"
                    + " vacia");
        }
        if (valorAsociado == null) {
            throw new IllegalArgumentException("Valor a insertar no puede ser"
                    + " vacia");
        }
        if (this.esArbolVacio()) {
            this.raiz = new NodoBinario<>(claveAInsertar, valorAsociado);
            return;
        }

        NodoBinario<K, V> nodoAnterior = NodoBinario.nodoVacio();
        NodoBinario<K, V> nodoActual = this.raiz;

        do {
            K claveDelNodoActual = nodoActual.getClave();
            int comparacion = claveAInsertar.compareTo(claveDelNodoActual);
            nodoAnterior = nodoActual;
            if (comparacion < 0) {
                nodoActual = nodoActual.getHijoIzquierdo();
            } else if (comparacion > 0) {
                nodoActual = nodoActual.getHijoDerecho();
            } else {
                nodoActual.setValor(valorAsociado);
                return;
            }
        } while (!NodoBinario.esNodoVacio(nodoActual));

        NodoBinario<K, V> nodoNuevo = new NodoBinario<>(claveAInsertar,
                valorAsociado);
        if (claveAInsertar.compareTo(nodoAnterior.getClave()) < 0) {
            nodoAnterior.setHijoIzquierdo(nodoNuevo);
        } else {
            nodoAnterior.setHijoDerecho(nodoNuevo);
        }


    }

    @Override
    public V eliminar(K claveAEliminar) {
        V valorARetornar = this.buscar(claveAEliminar);
        if (valorARetornar == null) {
            return null;
        }
        this.raiz = eliminar(this.raiz, claveAEliminar);
        return valorARetornar;
    }

    private NodoBinario<K, V> eliminar(NodoBinario<K, V> nodoActual,
                                       K claveAEliminar) {
        K claveDelNodoActual = nodoActual.getClave();
        if (claveAEliminar.compareTo(claveDelNodoActual) < 0) {
            NodoBinario<K, V> supuestoNuevoHijoIzquierdo =
                    eliminar(nodoActual.getHijoIzquierdo(), claveAEliminar);
            nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzquierdo);
            return nodoActual;
        }
        //cuando toca ir por la derecha
        //caso 1
        if (nodoActual.esHoja()) {
            return NodoBinario.nodoVacio();
        }
        //caso 2.1
        if (!nodoActual.esVacioHijoIzquierdo() &&
                nodoActual.esVacioHijoDerecho()) {
            return NodoBinario.nodoVacio();
        }
        //caso 2.2
        if (!nodoActual.esVacioHijoDerecho() &&
                nodoActual.esVacioHijoIzquierdo()) {
            return NodoBinario.nodoVacio();
        }
        //caso 3
        NodoBinario<K, V> nodoDelSucesor = this.obtenerNodoDelSucesor(
                nodoActual.getHijoDerecho());

        NodoBinario<K, V> supuestoNuevoHijoDerecho =
                eliminar(nodoActual.getHijoDerecho(),
                        nodoDelSucesor.getClave());
        nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
        nodoActual.setClave(nodoDelSucesor.getClave());
        nodoActual.setValor(nodoDelSucesor.getValor());
        return nodoActual;
    }

    protected NodoBinario<K, V> obtenerNodoDelSucesor(NodoBinario<K, V> nodo) {
        NodoBinario<K, V> nodoAnterior = nodo;
        while (!NodoBinario.esNodoVacio(nodo)) {
            nodoAnterior = nodo;
            nodo = nodo.getHijoIzquierdo();
        }
        return nodoAnterior;
    }

    @Override
    public V buscar(K clave) {
        if (clave == null) {
            throw new IllegalArgumentException("Clave a insertar no puede ser"
                    + "vacia");
        }
        if (this.esArbolVacio()) {
            return null;
        }
        NodoBinario<K, V> nodoAux = this.raiz;
        do {
            if (nodoAux.getClave().compareTo(clave) == 0) {
                return nodoAux.getValor();
            }
            if (nodoAux.getClave().compareTo(clave) > 0) {
                nodoAux = nodoAux.getHijoIzquierdo();
            } else {
                nodoAux = nodoAux.getHijoDerecho();
            }
        } while (!NodoBinario.esNodoVacio(nodoAux));
        return null;
    }

    @Override
    public boolean contiene(K clave) {
        return this.buscar(clave) != null;
    }

    @Override
    public int size() {
        int contador = 0;
        if (!this.esArbolVacio()) {
            Stack<NodoBinario<K, V>> pilaDeNodo = new Stack<>();
            pilaDeNodo.push(raiz);
            do {
                NodoBinario<K, V> NodAux = pilaDeNodo.pop();
                contador++;
                if (!NodAux.esVacioHijoDerecho()) {
                    pilaDeNodo.push(NodAux.getHijoDerecho());
                }
                if (!NodAux.esVacioHijoIzquierdo()) {
                    pilaDeNodo.push(NodAux.getHijoIzquierdo());
                }
            } while (!pilaDeNodo.isEmpty());
        }
        return contador;
    }

    @Override
    public int altura() {
        return this.altura(this.raiz);
    }

    protected int altura(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int alturaIzq = this.altura(nodoActual.getHijoIzquierdo());
        int alturaDer = this.altura(nodoActual.getHijoDerecho());

        return alturaIzq > alturaDer ? alturaIzq + 1 : alturaDer + 1;
    }


    public int alturaIterativa() {
        int altura = 0;
        if (!this.esArbolVacio()) {
            altura = 1;
            Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            do {
                NodoBinario<K, V> nodoActual = colaDeNodos.poll();
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                    altura = altura + 1;
                }
                if (!nodoActual.esVacioHijoDerecho()) {
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                    if (!nodoActual.esVacioHijoIzquierdo()) {
                        altura = altura + 1;
                    }
                }
            } while (!colaDeNodos.isEmpty());
        }
        return altura;
    }

    @Override
    public void vaciar() {
        this.raiz = NodoBinario.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoBinario.esNodoVacio(raiz);
    }

    @Override
    public int nivel() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    protected int nivel(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return -1;
        }
        int nivelIzq = this.altura(nodoActual.getHijoIzquierdo());
        int nivelDer = this.altura(nodoActual.getHijoDerecho());

        return nivelIzq > nivelDer ? nivelIzq + 1 : nivelDer + 1;
    }

    protected NodoBinario<K,V>obtenerNodoSucesor(NodoBinario<K,V>nodo)
    {
        NodoBinario<K,V> NodoAnterior=nodo;
        while(!NodoBinario.esNodoVacio(nodo))
        {
            NodoAnterior=nodo;
            nodo=nodo.getHijoIzquierdo();
        }
        return NodoAnterior;
    }


    @Override
    public List<K> recorridoEnInOrden() {
        List<K> recorrido = new ArrayList<>();
        recorridoEnInOrden(this.raiz, recorrido);
        return recorrido;
    }

    protected void recorridoEnInOrden(NodoBinario<K, V> nodoActual, List<K> recorrido) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return;
        }
        recorridoEnInOrden(nodoActual.getHijoIzquierdo(), recorrido);
        recorrido.add(nodoActual.getClave());
        recorridoEnInOrden(nodoActual.getHijoDerecho(), recorrido);
    }

    @Override
    public List<K> recorridoEnPreOrden() {
        List<K> Recorrido = new ArrayList<>();
        if (!esArbolVacio()) {
            Stack<NodoBinario<K, V>> Pila = new Stack<>();
            Pila.push(raiz);
            do {
                NodoBinario<K, V> NodoAux = Pila.pop();
                Recorrido.add(NodoAux.getClave());
                if (!NodoAux.esVacioHijoDerecho()) {
                    Pila.push(NodoAux.getHijoDerecho());
                }
                if (!NodoAux.esVacioHijoIzquierdo()) {
                    Pila.push(NodoAux.getHijoIzquierdo());
                }
            } while (!Pila.isEmpty());
        }
        return Recorrido;
    }

    @Override
    public List<K> recorridoEnPosOrden() {
        List<K> recorrido = new ArrayList<>();
        recorridoEnPosOrden(this.raiz, recorrido);
        return recorrido;
    }

    protected void recorridoEnPosOrden(NodoBinario<K, V> nodoActual, List<K> recorrido) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return;
        }
        recorridoEnPosOrden(nodoActual.getHijoIzquierdo(), recorrido);
        recorridoEnPosOrden(nodoActual.getHijoDerecho(), recorrido);
        recorrido.add(nodoActual.getClave());
    }

    @Override
    public List<K> recorridoPorNiveles() {
        List<K> recorrido = new ArrayList<>();
        if (!this.esArbolVacio()) {
            Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            do {
                NodoBinario<K, V> nodoActual = colaDeNodos.poll();
                recorrido.add(nodoActual.getClave());
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esVacioHijoDerecho()) {
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                }
            } while (!colaDeNodos.isEmpty());
        }
        return recorrido;
    }

    public String toString() {
        return crearRepresentacion(raiz, "", "", "R");
    }

    private String crearRepresentacion(NodoBinario<K, V> nodo, String representacion, String prefijo, String tipo) {
        if (nodo == null) {
            return representacion + prefijo + (tipo.equals("I") ? "├── " : (tipo.equals("D") ? "└── " : "")) + "null\n";
        }

        representacion += prefijo + (tipo.equals("R") ? "" : (tipo.equals("I") ? "├── " : "└── ")) + nodo.getClave() + " (" + tipo + ")\n";

        String nuevoPrefijo = prefijo + (tipo.equals("I") ? "│   " : "    ");
        representacion = crearRepresentacion(nodo.getHijoIzquierdo(), representacion, nuevoPrefijo, "I");
        representacion = crearRepresentacion(nodo.getHijoDerecho(), representacion, nuevoPrefijo, "D");

        return representacion;
    }
}