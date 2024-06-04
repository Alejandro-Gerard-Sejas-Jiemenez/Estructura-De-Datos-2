package Arboles;

import java.util.*;

public class ArbolB <K extends Comparable<K>,V>
        extends ArbolMViasBusqueda<K, V>
{
    private final int nroMaxClaves;
    private final int nroMinClaves;
    private final int nroMinHijos;

    public ArbolB()
    {
        super();
        nroMaxClaves=2;
        nroMinClaves=1;
        nroMinHijos=2;
    }
    public ArbolB(int orden)throws OrdenInvalidoExcepcion
    {   super(orden);
        nroMaxClaves=super.orden-1;
        nroMinClaves=nroMaxClaves/2;
        nroMinHijos=nroMinClaves+1;
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
            this.raiz=new NodoMVias<>(this.orden+1,claveAInsertar,valorAsociado);
            return;
        }
        Stack<NodoMVias<K,V>>pilaDeAncestros=new Stack<>();
        NodoMVias<K,V> nodoAux=this.raiz;
        do {
        int posicionDeClaveInsertar=super.buscarPosicionDeClave(nodoAux,claveAInsertar);
        if (posicionDeClaveInsertar!=ArbolMViasBusqueda.POSICION_INVALIDA) {
            nodoAux.setValor(posicionDeClaveInsertar,valorAsociado);
            nodoAux=NodoMVias.nodoVacio();
        }else if (nodoAux.esHoja()) {
            //el nodo auxiliar es hoja y la clave no esta en el nodo
            super.insertarClaveValorOrdenando(nodoAux,claveAInsertar,valorAsociado);
            if (nodoAux.nroDeClavesNoVacias()>this.nroMaxClaves) {
            this.dividirNodo(nodoAux,pilaDeAncestros);
            }
            nodoAux=NodoMVias.nodoVacio();
        }else{
            //el nodo aux no es una hoja y ya sabemos que la clave no esta en este nodo
            int posicionPorDondeBajar=super.posicionDondeBajar(nodoAux,claveAInsertar);
            pilaDeAncestros.push(nodoAux);
            nodoAux=nodoAux.getHijo(posicionPorDondeBajar);
        }
        }while(!NodoMVias.NodoVacio(nodoAux));
    }
//----------------------------------------------------------------------------------------------------------------------
protected void dividirNodoSinRaiz(NodoMVias<K,V> nodoActual,NodoMVias<K, V> nodoIzq
,NodoMVias<K, V> nodoDer,Stack<NodoMVias<K,V>> pilaDeAncestros,int posicionMedio)
{
    K clavenuevoPadre = nodoActual.getClave(posicionMedio);
    for  (int i = 0; i<nodoActual.nroDeClavesNoVacias(); i++)
    {
        K claveTurno = nodoActual.getClave(i);
        V valorTurno = nodoActual.getValor(i);
        if (claveTurno.compareTo(clavenuevoPadre)<0){
            super.insertarClaveValorOrdenando(nodoIzq, claveTurno, valorTurno);
            if (!nodoActual.esHijoVacio(i))
            {
             int pos=nodoIzq.nroDeClavesNoVacias();
             nodoIzq.setHijo(pos-1,nodoActual.getHijo(i));
            }
        } else if (claveTurno.compareTo(clavenuevoPadre) > 0) {
            super.insertarClaveValorOrdenando(nodoDer, claveTurno, valorTurno);
            if (!nodoActual.esHijoVacio(i))
            {
                int pos=nodoDer.nroDeClavesNoVacias();
                nodoDer.setHijo(pos-1,nodoActual.getHijo(i));
            }
        }else {
            if (!nodoActual.esHijoVacio(i))
            {
                int pos=nodoIzq.nroDeClavesNoVacias();
                nodoIzq.setHijo(pos,nodoActual.getHijo(i));
            }
        }
    }
    if (!nodoActual.esHijoVacio(nodoActual.nroDeClavesNoVacias())){
        int pos=nodoDer.nroDeClavesNoVacias();
        nodoDer.setHijo(pos,nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()));
    }
    int j=0;
    while (nodoActual.nroDeClavesNoVacias()!=1){
        if (nodoActual.getClave(j).compareTo(clavenuevoPadre)!=0){
            super.eliminarClaveDeNodoDePosicion(nodoActual,j);
        }else {
            j++;
        }
    }
    nodoActual.setHijo(0,nodoIzq);
    nodoActual.setHijo(1,nodoDer);
}
    protected void dividirNodo(NodoMVias<K,V> nodoActual,Stack<NodoMVias<K,V>> pilaDeAncestros)
    {    //segun mi persona esta bien pero falta revisar
    NodoMVias<K, V> nodoIzq = new NodoMVias<>(this.orden+1);
    NodoMVias<K, V> nodoDer = new NodoMVias<>(this.orden+1);
    int posicionMedio = (nodoActual.nroDeClavesNoVacias() / 2) - 1;
    if (pilaDeAncestros.isEmpty()) {
        this.dividirNodoSinRaiz(nodoActual,nodoIzq,nodoDer,pilaDeAncestros,posicionMedio);
        this.raiz=nodoActual;
    }else {
        NodoMVias<K,V>nodoPadre=pilaDeAncestros.pop();
        K clavenuevoPadre = nodoActual.getClave(posicionMedio);
        V valornuevoPadre = nodoActual.getValor(posicionMedio);
        super.insertarClaveValorOrdenando(nodoPadre,clavenuevoPadre,valornuevoPadre);
        super.eliminarClaveDeNodoDePosicion(nodoActual,posicionMedio);
        int posDondeSubeClaveNuevoPadre=super.buscarPosicionDeClave(nodoPadre,clavenuevoPadre);
        int t=0;
        while (t< nodoActual.nroDeClavesNoVacias())
        {
            K claveTurno = nodoActual.getClave(t);
            V valorTurno = nodoActual.getValor(t);
            if (claveTurno.compareTo(clavenuevoPadre)<0){
                super.insertarClaveValorOrdenando(nodoIzq, claveTurno, valorTurno);
                super.eliminarClaveDeNodoDePosicion(nodoActual, t);
            } else if (claveTurno.compareTo(clavenuevoPadre) > 0) {
                super.insertarClaveValorOrdenando(nodoDer, claveTurno, valorTurno);
                super.eliminarClaveDeNodoDePosicion(nodoActual, t);
            }else {
                t++;
            }
        }

        for (int j = posDondeSubeClaveNuevoPadre+1; j<nodoPadre.nroDeClavesNoVacias()-1; j++){
            nodoPadre.setHijo(j+2,nodoPadre.getHijo(j+1));
            nodoPadre.setHijo(j+1,nodoPadre.getHijo(j));
        }
        nodoPadre.setHijo(posDondeSubeClaveNuevoPadre , nodoIzq);
        nodoPadre.setHijo(posDondeSubeClaveNuevoPadre+1, nodoDer);

        if (nodoPadre.ClavesLlenas()){
         dividirNodo(nodoPadre,pilaDeAncestros);
            this.raiz=nodoPadre;
        }else {
            this.raiz = nodoPadre;
        }
       }
    }

//----------------------------------------------------------------------------------------------------------------------
    protected NodoMVias<K,V>buscarNodoDeLaClave(K claveAEliminar,Stack<NodoMVias<K,V>> pilaAncestros){
        //supuestamente funciona
            if (!esArbolVacio()) {
                NodoMVias<K, V> nodoActual = this.raiz;
                do {
                    boolean CambioDeNodo = false;
                    for (int i = 0; i < nodoActual.nroDeClavesNoVacias() &&
                            !CambioDeNodo; i++) {
                        K ClaveActual = nodoActual.getClave(i);
                        if (claveAEliminar.compareTo(ClaveActual) == 0) {
                            return nodoActual;
                        }
                        if (claveAEliminar.compareTo(ClaveActual)<0) {
                            CambioDeNodo = true;
                            pilaAncestros.push(nodoActual);
                            nodoActual = nodoActual.getHijo(i);
                        }
                    }//necesito de este para procesar el nodo
                    if (!CambioDeNodo) {
                        pilaAncestros.push(nodoActual);
                        nodoActual = nodoActual.getHijo(nodoActual.nroDeClavesNoVacias());
                    }
                } while (!NodoMVias.NodoVacio(nodoActual));
            }
        return NodoMVias.nodoVacio();
    }





    protected NodoMVias<K, V> prestarOFusionar(NodoMVias<K, V> nodo, Stack<NodoMVias<K, V>> pilaAncestros) {
        if (nodo.esHoja()) {
            return nodo;
        } else {
            int posicion = buscarPosicionDeClave(nodo, nodo.getClave(0));
            NodoMVias<K,V> hijo = nodo.getHijo(posicion);
            if (hijo.nroDeClavesNoVacias() > this.orden / 2) {
                K clavePrestada = hijo.getClave(hijo.nroDeClavesNoVacias() - 1);
                V valorPrestado = eliminar(clavePrestada); // Llamar al método eliminar
                nodo.setClave(posicion, clavePrestada);
                nodo.setValor(posicion, valorPrestado);
                return nodo;
            } else {
                NodoMVias<K, V> hermano = obtenerHermano(nodo, pilaAncestros, posicion);
                if (hermano != null && hermano.nroDeClavesNoVacias() > this.orden / 2) {
                    K clavePrestada = hermano.getClave(0);
                    V valorPrestado = eliminar(clavePrestada); // Llamar al método eliminar
                    nodo.setClave(posicion, clavePrestada);
                    nodo.setValor(posicion, valorPrestado);
                    return nodo;
                } else {
                    fusionarHijos(nodo, pilaAncestros, posicion);
                    return nodo;
                }
            }
        }
    }
    protected NodoMVias<K, V> obtenerHermano(NodoMVias<K, V> nodo, Stack<NodoMVias<K, V>> pilaAncestros, int posicion) {
        NodoMVias<K, V> padre = pilaAncestros.pop();
        int posicionPadre = buscarPosicionDeClave(padre, nodo.getClave(posicion));
        if (posicionPadre > 0) {
            return padre.getHijo(posicionPadre - 1);
        } else {
            return padre.getHijo(posicionPadre + 1);
        }
    }
    protected void fusionarHijos(NodoMVias<K, V> nodo, Stack<NodoMVias<K, V>> pilaAncestros, int posicion) {
        NodoMVias<K, V> hijo = nodo.getHijo(posicion);
        NodoMVias<K, V> hermano = obtenerHermano(nodo, pilaAncestros, posicion);
        hijo.setClave(hijo.nroDeClavesNoVacias(), nodo.getClave(posicion));
        hijo.setValor(hijo.nroDeClavesNoVacias(), nodo.getValor(posicion));
        for (int i = 0; i < hermano.nroDeClavesNoVacias(); i++) {
            hijo.setClave(hijo.nroDeClavesNoVacias() + i + 1, hermano.getClave(i));
            hijo.setValor(hijo.nroDeClavesNoVacias() + i + 1, hermano.getValor(i));
        }
        eliminarClaveDeNodoDePosicion(nodo, posicion);
        nodo.setHijo(posicion, hijo);
        nodo.setHijo(posicion + 1, NodoMVias.nodoVacio());
    }


    protected NodoMVias<K,V>obtenerNodoPredecesor(Stack<NodoMVias<K,V>> pilaDeAncestros
            ,NodoMVias<K,V>nodoDeLaClaveAEliminar) {
        while (!nodoDeLaClaveAEliminar.esHoja()) {
            pilaDeAncestros.push(nodoDeLaClaveAEliminar);
            nodoDeLaClaveAEliminar = nodoDeLaClaveAEliminar.getHijo(nodoDeLaClaveAEliminar.nroDeClavesNoVacias());
        }
        return nodoDeLaClaveAEliminar;
    }

    @Override
    public V eliminar(K claveAEliminar) {
        if(claveAEliminar==null)
        {
            throw new UnsupportedOperationException("Dato no Valido");
        }
        Stack<NodoMVias<K,V>> pilaDeAncestros=new Stack<>();
        NodoMVias<K,V> nodoDeLaClaveAEliminar=
                this.buscarNodoDeLaClave(claveAEliminar,pilaDeAncestros);
        if (NodoMVias.NodoVacio(nodoDeLaClaveAEliminar)){
            return null;
        }
        int posicionDeClaveAEliminar=super.buscarPosicionDeClave(
                nodoDeLaClaveAEliminar,claveAEliminar);
        V valorARetornar= nodoDeLaClaveAEliminar.getValor(posicionDeClaveAEliminar);
        if (nodoDeLaClaveAEliminar.esHoja()){
            super.eliminarClaveDeNodoDePosicion(nodoDeLaClaveAEliminar
                    ,posicionDeClaveAEliminar);
            if(nodoDeLaClaveAEliminar.nroDeClavesNoVacias()<this.nroMaxClaves) {
                if (pilaDeAncestros.isEmpty()) {
                    if (nodoDeLaClaveAEliminar.nroDeClavesNoVacias() == 0) {
                        super.vaciar();
                    }
                } else {
                    prestarOFusionar(nodoDeLaClaveAEliminar, pilaDeAncestros);
                }
            }
        }else {
            pilaDeAncestros.push(nodoDeLaClaveAEliminar);
            NodoMVias<K,V> nodoDelPredecesor=this.obtenerNodoPredecesor(
                    pilaDeAncestros,nodoDeLaClaveAEliminar.getHijo(
                            posicionDeClaveAEliminar
                    ));
            K claveDePredecesor=nodoDelPredecesor.getClave(
                    nodoDelPredecesor.nroDeClavesNoVacias()-1
            );
            V valorDelPredecesor=nodoDelPredecesor.getValor(
                    nodoDelPredecesor.nroDeClavesNoVacias()-1
            );
            super.eliminarClaveDeNodoDePosicion
                    (nodoDelPredecesor,
                    nodoDelPredecesor.nroDeClavesNoVacias()-1);
            nodoDeLaClaveAEliminar.setClave(posicionDeClaveAEliminar,
                    claveDePredecesor);
            nodoDelPredecesor.setValor(posicionDeClaveAEliminar,
                    valorDelPredecesor);
            if (nodoDelPredecesor.nroDeClavesNoVacias()<this.nroMinClaves) {
             this.prestarOFusionar(nodoDelPredecesor,pilaDeAncestros);
            }
        }
        return valorARetornar;
    }


}





