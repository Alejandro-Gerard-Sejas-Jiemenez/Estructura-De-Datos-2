package Arboles;

public class NodoBinario <K,V>{
    private K clave;
    private V valor;
    private NodoBinario<K,V> hijoizquierdo;
    private NodoBinario<K,V> hijoderecho;
    public NodoBinario()
    {
    }
    public NodoBinario(K clave,V valor )
    {
        this.clave=clave;
        this.valor=valor;
    }

    public void setClave(K clave) {
        this.clave = clave;
    }

    public void setValor(V valor) {
        this.valor = valor;
    }

    public void setHijoizquierdo(NodoBinario<K, V> hijoizquierdo) {
        this.hijoizquierdo = hijoizquierdo;
    }

    public void setHijoderecho(NodoBinario<K, V> hijoderecho) {
        this.hijoderecho = hijoderecho;
    }

    public K getClave() {
        return clave;
    }

    public V getValor() {
        return valor;
    }

    public NodoBinario<K, V> getHijoizquierdo() {
        return hijoizquierdo;
    }

    public NodoBinario<K, V> getHijoderecho() {
        return hijoderecho;
    }

    public boolean esVacioHijoIzquierdo()
    {return NodoBinario.esNodoVacio(this.getHijoizquierdo());
    }

    public boolean esVacioHijoDerecho()
    {return NodoBinario.esNodoVacio(this.getHijoderecho());
    }
    public boolean esHoja()
    {return this.esVacioHijoDerecho() && this.esVacioHijoIzquierdo();
    }
    public static NodoBinario nodoVacio()
    {
        return null;
    }
    public static boolean esNodoVacio(NodoBinario nodo)
    {
        return nodo==NodoBinario.nodoVacio();
    }

}
