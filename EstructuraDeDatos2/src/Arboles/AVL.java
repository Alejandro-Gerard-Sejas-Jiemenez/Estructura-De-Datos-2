package Arboles;

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
            NodoBinario<K,V> supuestoNuevoHI=Insertar(NodoActual.getHijoIzquierdo(), ClaveInsertar, ValorAsociado);
            NodoActual.setHijoIzquierdo(supuestoNuevoHI);
            return balancear(NodoActual);
        }
        if (ClaveInsertar.compareTo(claveDelNodoActual)>0)
        {
            NodoBinario<K,V> supuestoNuevoHD=Insertar(NodoActual.getHijoDerecho(), ClaveInsertar, ValorAsociado);
            NodoActual.setHijoDerecho(supuestoNuevoHD);
            return balancear(NodoActual);
        }
        NodoActual.setValor(ValorAsociado);
        return NodoActual;
    }
    //----------------------------------------------------------------------------------------------------------------------
    protected NodoBinario<K,V>balancear(NodoBinario<K,V>NodoActual)
    {
        int alturaPorIzquierda=super.altura(NodoActual.getHijoIzquierdo());
        int alturaPorDerecha=super.altura(NodoActual.getHijoDerecho());
        int diferencia=alturaPorIzquierda-alturaPorDerecha;
        if (diferencia>RANGO_SUPERIOR)
        {
            NodoBinario<K,V>hijoizquierdoDelAct=NodoActual.getHijoIzquierdo();
            alturaPorIzquierda=altura(hijoizquierdoDelAct.getHijoIzquierdo());
            alturaPorDerecha=altura(hijoizquierdoDelAct.getHijoDerecho());
            if (alturaPorDerecha>alturaPorIzquierda)
            {
                return rotacionDobleADerecha(NodoActual);
            }
            return rotacionSimpleADerecha(NodoActual);

        }else if (diferencia<RANGO_INFERIOR)
        {
            NodoBinario<K,V>hijoderechDelAct=NodoActual.getHijoDerecho();
            alturaPorIzquierda=altura(hijoderechDelAct.getHijoIzquierdo());
            alturaPorDerecha=altura(hijoderechDelAct.getHijoDerecho());
            if (alturaPorDerecha<alturaPorIzquierda)
            {
                return rotacionDobleAIzquierda(NodoActual);
            }
            return rotacionSimpleAIzquierda(NodoActual);
        }
        return NodoActual;
    }

    private NodoBinario<K,V>rotacionSimpleADerecha(NodoBinario<K,V>NodoAct) {
        NodoBinario<K,V>nodoRetornar=NodoAct.getHijoIzquierdo();
        NodoAct.setHijoIzquierdo(nodoRetornar.getHijoDerecho());
        nodoRetornar.setHijoDerecho(NodoAct);
        return nodoRetornar;
    }

    private NodoBinario<K,V>rotacionSimpleAIzquierda(NodoBinario<K,V>NodoAct) {
        NodoBinario<K,V>nodoRetornar=NodoAct.getHijoDerecho();
        NodoAct.setHijoDerecho(nodoRetornar.getHijoIzquierdo());
        nodoRetornar.setHijoIzquierdo(NodoAct);
        return nodoRetornar;
    }

    private NodoBinario<K,V> rotacionDobleADerecha(NodoBinario<K,V> NodoActual) {
        NodoBinario<K,V> NodoQueRotIzq=rotacionSimpleAIzquierda(NodoActual.getHijoIzquierdo());
        NodoActual.setHijoIzquierdo(NodoQueRotIzq);
        return rotacionSimpleADerecha(NodoActual);
    }

    private NodoBinario<K,V> rotacionDobleAIzquierda(NodoBinario<K,V> NodoActual) {
        NodoBinario<K,V> NodoQueRotDer=rotacionSimpleADerecha(NodoActual.getHijoDerecho());
        NodoActual.setHijoDerecho(NodoQueRotDer);
        return rotacionSimpleAIzquierda(NodoActual);
    }
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
            NodoBinario<K,V> supuestoNuevoHijoIzquierdo=Eliminar(nodoActual.getHijoIzquierdo(),claveAlEliminar);
            nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzquierdo);
            return balancear(nodoActual);
        }
        if (claveAlEliminar.compareTo(claveDelNodoActual)>0)
        {//cuando toca ir por la derecha
            NodoBinario<K,V> supuestoNuevoHijoDerecho=Eliminar(nodoActual.getHijoDerecho(),claveAlEliminar);
            nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
            return balancear(nodoActual);
        }

        //caso 1
        if (nodoActual.esHoja())
        {
            return NodoBinario.nodoVacio();
        }
        //caso2.1
        if (!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho())
        {
            return balancear(nodoActual.getHijoIzquierdo());
        }
        //caso2.2
        if (!nodoActual.esVacioHijoDerecho()&& nodoActual.esVacioHijoIzquierdo())
        {
            return balancear(nodoActual.getHijoDerecho());
        }
        //caso 3
        NodoBinario<K,V>NodoSucesor=this.obtenerNodoSucesor(nodoActual.getHijoDerecho());
        NodoBinario<K,V> supuestoNuevoDerecho=Eliminar(nodoActual.getHijoDerecho(),NodoSucesor.getClave());
        nodoActual.setHijoDerecho(supuestoNuevoDerecho);
        nodoActual.setClave(NodoSucesor.getClave());
        nodoActual.setValor(NodoSucesor.getValor());
        return nodoActual;
    }
//---------------------------------------------------------------------------------------------------------------------
}