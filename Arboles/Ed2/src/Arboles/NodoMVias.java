package Arboles;

import java.util.ArrayList;
import java.util.List;


public class NodoMVias <K,V>{
    private List<K> listaDeClaves;
    private List<V> listaDeValores;
    private List<NodoMVias<K,V>> listaDeHijos;

    public static NodoMVias nodoVacio()
    {
        return null;
    }
    public static NodoMVias datoVacio()
    {
        return null;
    }
    public static boolean NodoVacio(NodoMVias Nodo)
    {
        return Nodo==nodoVacio();
    }
    public NodoMVias (int orden)
    {
        listaDeClaves=new ArrayList<>();
        listaDeValores=new ArrayList<>();
        listaDeHijos =new ArrayList<>();
        for (int i=0;i<(orden-1);i++)
        {
            this.listaDeClaves.add((K) NodoMVias.datoVacio());
            this.listaDeValores.add((V) NodoMVias.datoVacio());
            this.listaDeHijos.add(NodoMVias.nodoVacio());
        }
        this.listaDeHijos.add(NodoMVias.nodoVacio());
    }
    public NodoMVias (int orden ,K primeraClave,V primerValor)
    {this(orden);
        this.listaDeClaves.set(0, primeraClave);
        this.listaDeValores.set(0,primerValor);
    }
    public K getClave(int posicion)
    {
        return listaDeClaves.get(posicion);
    }
    public V getValor(int posicion)
    {
        return listaDeValores.get(posicion);
    }
    public void setValor (int posicion, V Valor)
    {
        this.listaDeValores.set(posicion,Valor);
    }
    public void setClave (int posicion, K Clave)
    {
        this.listaDeClaves.set(posicion,Clave);
    }
    public NodoMVias<K,V> getHijo(int posicion)
    {
        return listaDeHijos.get(posicion);
    }
    public void setHijo(int posicion, NodoMVias<K,V> unNodo)
    {
        listaDeHijos.set(posicion, unNodo);
    }
    public boolean esHijoVacio(int posicion)
    {
        return this.getHijo(posicion)==NodoMVias.nodoVacio();
    }
    public boolean esDatoVacio(int posicion)
    {
        return this.getValor(posicion)==NodoMVias.datoVacio();
    }
    public boolean esHoja()
    {
        for (int i=0;i<this.listaDeHijos.size();i++)
        {
            if (!this.esHijoVacio(i))
            {
                return false;
            }
        }
        return true ;
    }

    public int nroDeClavesNoVacias()
    {
        int cantidad=0;
        for (int i=0;i<this.listaDeClaves.size();i++)
        {
            if (!this.esDatoVacio(i))
            {
                cantidad++;
            }
        }
        return cantidad;
    }
    public boolean hayClavesNoVacia()
    {
        return this.nroDeClavesNoVacias()!=0;
    }
    public boolean ClavesLlenas()
    {
        return this.nroDeClavesNoVacias()==this.listaDeClaves.size();
    }

}
