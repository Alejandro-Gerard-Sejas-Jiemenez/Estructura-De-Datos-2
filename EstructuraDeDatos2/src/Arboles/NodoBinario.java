package Arboles;

/**
 *
 *
 * @param <K>
 * @param <V>
 */
public class NodoBinario<K,V> {
    private K clave;
    private V valor;
    private NodoBinario<K,V> HijoIzquierdo;
    private NodoBinario<K,V> HijoDerecho;


    public NodoBinario(){
    }

    public NodoBinario(K clave, V valor){
        this.clave=clave;
        this.valor=valor;
    }

    public K getClave(){
        return this.clave;
    }

    public V getValor(){
        return this.valor;
    }

    public NodoBinario getHijoDerecho(){
        return this.HijoDerecho;
    }

    public NodoBinario getHijoIzquierdo(){
        return this.HijoIzquierdo;
    }

    public void setClave(K Clave){
        this.clave=Clave;
    }

    public void setValor(V Valor){
        this.valor=Valor;
    }

    public void setHijoDerecho(NodoBinario<K,V> HijoDerecho){
        this.HijoDerecho=HijoDerecho;
    }

    public void setHijoIzquierdo(NodoBinario<K,V> HijoIzquierdo){
        this.HijoIzquierdo=HijoIzquierdo;
    }

    public boolean esVacioHijoDerecho(){
        return NodoBinario.esNodoVacio(this.getHijoDerecho());
    }

    public boolean esVacioHijoIzquierdo(){
        return NodoBinario.esNodoVacio(this.getHijoIzquierdo());
    }

    public boolean esHoja(){
        return this.esVacioHijoIzquierdo() && this.esVacioHijoDerecho();
    }

    public static boolean esNodoVacio(NodoBinario nodo){
        return nodo == NodoBinario.nodoVacio();
    }

    public static NodoBinario nodoVacio(){
        return null;
    }

}