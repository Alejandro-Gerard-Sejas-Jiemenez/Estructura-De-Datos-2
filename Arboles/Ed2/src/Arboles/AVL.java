package Arboles;

import java.util.List;

public class AVL <K extends Comparable <K>,V> extends ArbolBinarioBusqueda<K,V>{
    private static final byte RANGO_SUPERIOR=1;
    private static final byte RANGO_INFERIOR=-1;

    @Override
    public void insertar(K claveAInsertar, V valorAsociado) {
        if(claveAInsertar==null)
        {throw new UnsupportedOperationException("Dato no Valido");
        }
        if(valorAsociado==null)
        {
            throw new UnsupportedOperationException("Dato no Valido");
        }
        this.raiz=Insertar(super.raiz, claveAInsertar, valorAsociado);
    }

    private NodoBinario<K,V>Insertar(NodoBinario<K,V>NodoActual, K ClaveInsertar, V ValorAsociado)
    {
        if (NodoBinario.esNodoVacio(NodoActual))
        {
            NodoBinario<K,V> nodoNuevo=new NodoBinario<>(ClaveInsertar,ValorAsociado);
            return nodoNuevo;
        }

        K claveDelNodoActual=NodoActual.getClave();
        if (ClaveInsertar.compareTo(claveDelNodoActual)<0)
        {
            NodoBinario<K,V> supuestoNuevoHI=Insertar(NodoActual.getHijoizquierdo(), ClaveInsertar, ValorAsociado);
            NodoActual.setHijoizquierdo(supuestoNuevoHI);
            return balancear(NodoActual);
        }
        if (ClaveInsertar.compareTo(claveDelNodoActual)>0)
        {
            NodoBinario<K,V> supuestoNuevoHD=Insertar(NodoActual.getHijoderecho(), ClaveInsertar, ValorAsociado);
            NodoActual.setHijoderecho(supuestoNuevoHD);
            return balancear(NodoActual);
        }
        NodoActual.setValor(ValorAsociado);
        return NodoActual;
    }
//----------------------------------------------------------------------------------------------------------------------
    private NodoBinario<K,V>balancear(NodoBinario<K,V>NodoActual)
    {
        int alturaPorIzquierda=super.altura(NodoActual.getHijoizquierdo());
        int alturaPorDerecha=super.altura(NodoActual.getHijoderecho());
        int diferencia=alturaPorIzquierda-alturaPorDerecha;
        if (diferencia>RANGO_SUPERIOR)
        {
            NodoBinario<K,V>hijoizquierdoDelAct=NodoActual.getHijoizquierdo();
            alturaPorIzquierda=altura(hijoizquierdoDelAct.getHijoizquierdo());
            alturaPorDerecha=altura(hijoizquierdoDelAct.getHijoderecho());
            if (alturaPorDerecha>alturaPorIzquierda)
            {
                return rotacionDobleADerecha(NodoActual);
            }
            return rotacionSimpleADerecha(NodoActual);

        }else if (diferencia<RANGO_INFERIOR)
        {
            NodoBinario<K,V>hijoderechDelAct=NodoActual.getHijoderecho();
            alturaPorIzquierda=altura(hijoderechDelAct.getHijoizquierdo());
            alturaPorDerecha=altura(hijoderechDelAct.getHijoderecho());
            if (alturaPorDerecha<alturaPorIzquierda)
            {
                return rotacionDobleAIzquierda(NodoActual);
            }
            return rotacionSimpleAIzquierda(NodoActual);
        }
        return NodoActual;
    }

    private NodoBinario<K,V>rotacionSimpleADerecha(NodoBinario<K,V>NodoAct) {
        NodoBinario<K,V>nodoRetornar=NodoAct.getHijoizquierdo();
        NodoAct.setHijoizquierdo(nodoRetornar.getHijoderecho());
        nodoRetornar.setHijoderecho(NodoAct);
        return nodoRetornar;
    }

    private NodoBinario<K,V>rotacionSimpleAIzquierda(NodoBinario<K,V>NodoAct) {
        NodoBinario<K,V>nodoRetornar=NodoAct.getHijoderecho();
        NodoAct.setHijoderecho(nodoRetornar.getHijoizquierdo());
        nodoRetornar.setHijoizquierdo(NodoAct);
        return nodoRetornar;
    }

    private NodoBinario<K,V> rotacionDobleADerecha(NodoBinario<K,V> NodoActual) {
        NodoBinario<K,V> NodoQueRotIzq=rotacionSimpleAIzquierda(NodoActual.getHijoizquierdo());
        NodoActual.setHijoizquierdo(NodoQueRotIzq);
        return rotacionSimpleADerecha(NodoActual);
    }

    private NodoBinario<K,V> rotacionDobleAIzquierda(NodoBinario<K,V> NodoActual) {
        NodoBinario<K,V> NodoQueRotDer=rotacionSimpleADerecha(NodoActual.getHijoderecho());
        NodoActual.setHijoderecho(NodoQueRotDer);
        return rotacionSimpleAIzquierda(NodoActual);
    }
    ///falta la rotacion simple a la Izquierda//ya esta hecha
    ///falta la rotacion doble a la derecha //ya esta hecha
    ///falta ;a rotacion doble a la izquierda//ya esta hecha
//----------------------------------------------------------------------------------------------------------------------


    @Override
    public V eliminar(K claveAEliminar) {
        V valorARetornar=this.buscar(claveAEliminar);
        if (valorARetornar==null)
        {
            return null;
        }
        this.raiz=Eliminar(this.raiz,claveAEliminar);
        return valorARetornar;
    }

    private NodoBinario<K,V> Eliminar(NodoBinario<K,V> nodoActual, K claveAlEliminar)
    {
        K claveDelNodoActual=nodoActual.getClave();
        if (claveAlEliminar.compareTo(claveDelNodoActual)<0)
        {//cuando toca ir por la izquierda
            NodoBinario<K,V> supuestoNuevoHijoIzquierdo=Eliminar(nodoActual.getHijoizquierdo(),claveAlEliminar);
            nodoActual.setHijoizquierdo(supuestoNuevoHijoIzquierdo);
            return balancear(nodoActual);
        }
        if (claveAlEliminar.compareTo(claveDelNodoActual)>0)
        {//cuando toca ir por la derecha
            NodoBinario<K,V> supuestoNuevoHijoDerecho=Eliminar(nodoActual.getHijoderecho(),claveAlEliminar);
            nodoActual.setHijoderecho(supuestoNuevoHijoDerecho);
            return balancear(nodoActual);
        }

        //caso 1
        if (nodoActual.esHoja())
        {return NodoBinario.nodoVacio();
        }
        //caso2.1
        if (!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho())
        {
            return balancear(nodoActual.getHijoizquierdo());
        }
        //caso2.2
        if (!nodoActual.esVacioHijoDerecho()&& nodoActual.esVacioHijoIzquierdo())
        {
            return balancear(nodoActual.getHijoderecho());
        }
        //caso 3
        NodoBinario<K,V>NodoSucesor=this.obtenerNodoSucesor(nodoActual.getHijoderecho());
        NodoBinario<K,V> supuestoNuevoDerecho=Eliminar(nodoActual.getHijoderecho(),NodoSucesor.getClave());
        nodoActual.setHijoderecho(supuestoNuevoDerecho);
        nodoActual.setClave(NodoSucesor.getClave());
        nodoActual.setValor(NodoSucesor.getValor());
        return nodoActual;
    }
//---------------------------------------------------------------------------------------------------------------------
}



