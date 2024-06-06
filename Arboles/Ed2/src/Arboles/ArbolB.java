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
///el dividirNodo 2 no funcona correctamente
    public void dividirNodo2(NodoMVias<K, V> nodo, Stack<NodoMVias<K, V>> pilaDeAncestros) {
        int puntoMedio = this.orden / 2;
        NodoMVias<K, V> nuevoNodo = new NodoMVias<>(this.orden);

        // Mover claves y valores de la derecha al nuevo nodo
        for (int i = puntoMedio + 1; i < this.orden; i++) {
            nuevoNodo.setClave(i - (puntoMedio + 1), nodo.getClave(i));
            nuevoNodo.setValor(i - (puntoMedio + 1), nodo.getValor(i));
            nuevoNodo.setHijo(i - (puntoMedio + 1), nodo.getHijo(i));
            nodo.setClave(i, null);
            nodo.setValor(i, null);
            nodo.setHijo(i, null);
        }
        nuevoNodo.setHijo(this.orden - (puntoMedio + 1), nodo.getHijo(this.orden));
        nodo.setHijo(this.orden, NodoMVias.nodoVacio());

        K claveMedio = nodo.getClave(puntoMedio);
        V valorMedio = nodo.getValor(puntoMedio);
        nodo.setClave(puntoMedio, null);
        nodo.setValor(puntoMedio, null);

        if (pilaDeAncestros.isEmpty()) {
            // Crear una nueva raíz
            this.raiz = new NodoMVias<>(this.orden);
            this.raiz.setClave(0, claveMedio);
            this.raiz.setValor(0, valorMedio);
            this.raiz.setHijo(0, nodo);
            this.raiz.setHijo(1, nuevoNodo);
        } else {
            NodoMVias<K, V> nodoPadre = pilaDeAncestros.pop();
            int posicionInsertarEnPadre = super.buscarPosicionDeClave(nodoPadre, claveMedio);
            super.insertarClaveValorOrdenando(nodoPadre, claveMedio, valorMedio);
            nodoPadre.setHijo(posicionInsertarEnPadre + 1, nuevoNodo);

            if (nodoPadre.nroDeClavesNoVacias() > this.nroMaxClaves) {
                dividirNodo2(nodoPadre, pilaDeAncestros);
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
//--------------------------------------------------------------------------------------------
private void prestarOFusionar(NodoMVias<K, V> nodo, Stack<NodoMVias<K, V>> pilaDeAncestros) {
    NodoMVias<K, V> padre = pilaDeAncestros.pop();
    int posicionDeNodo = this.buscarPosicionDeHijoEnPadre(padre, nodo);

    // Intentar prestar de un hermano derecho
    if (posicionDeNodo < padre.nroDeClavesNoVacias() && !padre.esHijoVacio(posicionDeNodo + 1)) {
        NodoMVias<K, V> hermanoDerecho = padre.getHijo(posicionDeNodo + 1);
        if (hermanoDerecho.nroDeClavesNoVacias() > this.nroMinClaves) {
            this.prestarDeHermanoDerecho(nodo, padre, hermanoDerecho, posicionDeNodo);
            return;
        }
    }

    // Intentar prestar de un hermano izquierdo
    if (posicionDeNodo > 0 && !padre.esHijoVacio(posicionDeNodo - 1)) {
        NodoMVias<K, V> hermanoIzquierdo = padre.getHijo(posicionDeNodo - 1);
        if (hermanoIzquierdo.nroDeClavesNoVacias() > this.nroMinClaves) {
            this.prestarDeHermanoIzquierdo(nodo, padre, hermanoIzquierdo, posicionDeNodo);
            return;
        }
    }

    // Si no podemos prestar, fusionar con un hermano
    if (posicionDeNodo > 0 && !padre.esHijoVacio(posicionDeNodo - 1)) {
        NodoMVias<K, V> hermanoIzquierdo = padre.getHijo(posicionDeNodo - 1);
        this.fusionarConHermanoIzquierdo(nodo, padre, hermanoIzquierdo, posicionDeNodo);
    } else if (posicionDeNodo < padre.nroDeClavesNoVacias() && !padre.esHijoVacio(posicionDeNodo + 1)) {
        NodoMVias<K, V> hermanoDerecho = padre.getHijo(posicionDeNodo + 1);
        this.fusionarConHermanoDerecho(nodo, padre, hermanoDerecho, posicionDeNodo);
    }

    if (padre.nroDeClavesNoVacias() < this.nroMinClaves) {
        this.prestarOFusionar(padre, pilaDeAncestros);
    }
    }

    private int buscarPosicionDeHijoEnPadre(NodoMVias<K, V> padre, NodoMVias<K, V> hijo) {
        for (int i = 0; i <= padre.nroDeClavesNoVacias(); i++) {
            if (padre.getHijo(i) == hijo) {
                return i;
            }
        }
        return -1; // No se encontró el hijo
    }

    private void prestarDeHermanoIzquierdo(NodoMVias<K, V> nodo, NodoMVias<K, V> padre, NodoMVias<K, V> hermanoIzquierdo, int posicionDeNodo) {
        K claveDePadre = padre.getClave(posicionDeNodo - 1);
        V valorDePadre = padre.getValor(posicionDeNodo - 1);
        K claveDeHermano = hermanoIzquierdo.getClave(hermanoIzquierdo.nroDeClavesNoVacias() - 1);
        V valorDeHermano = hermanoIzquierdo.getValor(hermanoIzquierdo.nroDeClavesNoVacias() - 1);
        NodoMVias<K, V> hijoDeHermano = hermanoIzquierdo.getHijo(hermanoIzquierdo.nroDeClavesNoVacias());

        for (int i = nodo.nroDeClavesNoVacias() - 1; i >= 0; i--) {
            nodo.setClave(i + 1, nodo.getClave(i));
            nodo.setValor(i + 1, nodo.getValor(i));
            nodo.setHijo(i + 2, nodo.getHijo(i + 1));
        }
        nodo.setClave(0, claveDePadre);
        nodo.setValor(0, valorDePadre);
        nodo.setHijo(1, nodo.getHijo(0));
        nodo.setHijo(0, hijoDeHermano);

        padre.setClave(posicionDeNodo - 1, claveDeHermano);
        padre.setValor(posicionDeNodo - 1, valorDeHermano);
        //hermanoIzquierdo.eliminarClaveDeNodoDePosicion(hermanoIzquierdo.nroDeClavesNoVacias() - 1);
        super.eliminarClaveDeNodoDePosicion(hermanoIzquierdo,hermanoIzquierdo.nroDeClavesNoVacias()-1);
    }

    private void prestarDeHermanoDerecho(NodoMVias<K, V> nodo, NodoMVias<K, V> padre, NodoMVias<K, V> hermanoDerecho, int posicionDeNodo) {
        K claveDePadre = padre.getClave(posicionDeNodo);
        V valorDePadre = padre.getValor(posicionDeNodo);
        K claveDeHermano = hermanoDerecho.getClave(0);
        V valorDeHermano = hermanoDerecho.getValor(0);
        NodoMVias<K, V> hijoDeHermano = hermanoDerecho.getHijo(0);

        nodo.setClave(nodo.nroDeClavesNoVacias(), claveDePadre);
        nodo.setValor(nodo.nroDeClavesNoVacias(), valorDePadre);
        nodo.setHijo(nodo.nroDeClavesNoVacias() + 1, hijoDeHermano);

        padre.setClave(posicionDeNodo, claveDeHermano);
        padre.setValor(posicionDeNodo, valorDeHermano);
        //hermanoDerecho.eliminarClaveDeNodoDePosicion(0);
        super.eliminarClaveDeNodoDePosicion(hermanoDerecho,0);
    }

    private void fusionarConHermanoIzquierdo(NodoMVias<K, V> nodo, NodoMVias<K, V> padre, NodoMVias<K, V> hermanoIzquierdo, int posicionDeNodo) {
        K claveDePadre = padre.getClave(posicionDeNodo - 1);
        V valorDePadre = padre.getValor(posicionDeNodo - 1);

        hermanoIzquierdo.setClave(hermanoIzquierdo.nroDeClavesNoVacias(), claveDePadre);
        hermanoIzquierdo.setValor(hermanoIzquierdo.nroDeClavesNoVacias(), valorDePadre);

        for (int i = 0; i < nodo.nroDeClavesNoVacias(); i++) {
            hermanoIzquierdo.setClave(hermanoIzquierdo.nroDeClavesNoVacias() + 1 + i, nodo.getClave(i));
            hermanoIzquierdo.setValor(hermanoIzquierdo.nroDeClavesNoVacias() + 1 + i, nodo.getValor(i));
            hermanoIzquierdo.setHijo(hermanoIzquierdo.nroDeClavesNoVacias() + 1 + i, nodo.getHijo(i));
        }
        hermanoIzquierdo.setHijo(hermanoIzquierdo.nroDeClavesNoVacias() + 1, nodo.getHijo(nodo.nroDeClavesNoVacias()));

        //padre.eliminarClaveDeNodoDePosicion(posicionDeNodo - 1);
        super.eliminarClaveDeNodoDePosicion(padre,posicionDeNodo-1);
        padre.setHijo(posicionDeNodo, hermanoIzquierdo);
    }

    private void fusionarConHermanoDerecho(NodoMVias<K, V> nodo, NodoMVias<K, V> padre, NodoMVias<K, V> hermanoDerecho, int posicionDeNodo) {
        K claveDePadre = padre.getClave(posicionDeNodo);
        V valorDePadre = padre.getValor(posicionDeNodo);

        nodo.setClave(nodo.nroDeClavesNoVacias(), claveDePadre);
        nodo.setValor(nodo.nroDeClavesNoVacias(), valorDePadre);

        for (int i = 0; i < hermanoDerecho.nroDeClavesNoVacias(); i++) {
            nodo.setClave(nodo.nroDeClavesNoVacias() + 1 + i, hermanoDerecho.getClave(i));
            nodo.setValor(nodo.nroDeClavesNoVacias() + 1 + i, hermanoDerecho.getValor(i));
            nodo.setHijo(nodo.nroDeClavesNoVacias() + 1 + i, hermanoDerecho.getHijo(i));
        }
        nodo.setHijo(nodo.nroDeClavesNoVacias() + 1, hermanoDerecho.getHijo(hermanoDerecho.nroDeClavesNoVacias()));

        //padre.eliminarClaveDeNodoDePosicion(posicionDeNodo);
        super.eliminarClaveDeNodoDePosicion(padre,posicionDeNodo);
        padre.setHijo(posicionDeNodo, nodo);
    }


    //------------------------------------------
    private NodoMVias<K, V> obtenerNodoPredecesor(Stack<NodoMVias<K, V>> pilaDeAncestros, NodoMVias<K, V> nodoHijoIzquierdo) {
        NodoMVias<K, V> nodoActual = nodoHijoIzquierdo;
        while (!nodoActual.esHijoVacio(nodoActual.nroDeClavesNoVacias())) {
            pilaDeAncestros.push(nodoActual);
            nodoActual = nodoActual.getHijo(nodoActual.nroDeClavesNoVacias());
        }
        return nodoActual;
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





