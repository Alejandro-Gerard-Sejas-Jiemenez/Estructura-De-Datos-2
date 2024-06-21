package Arboles;

import Excepciones.OrdenInvalidoExcepcion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GraphicInterface extends javax.swing.JFrame{
    private JButton buscarButton;
    private JTextField codigoProd;
    private JTextField nombreProd;
    private JTextArea textArea1;
    private JPanel Facturador;
    private JButton eliminarButton;
    private JButton AVLButton;
    private JButton ABBButton;
    private JButton AMVButton;
    private JButton disminuirButton;
    private JButton purgeButton;
    private JButton manifestButton;
    private JTextArea treeArea;
    private IArbolBusqueda<Integer,String> arbolBusqueda;
    private IArbolBusqueda<Integer,String> arbolCompra;
    private AVL<Integer,Integer> arbolDataBase;


    private GraphicInterface()
    {
        sendArbol();
        this.textArea1.setText("");
        setContentPane(Facturador);
        setTitle("Factura");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000,1600);
        setVisible(true);
        setLocationRelativeTo(null);
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int prod= Integer.parseInt(codigoProd.getText());
                String nombre=arbolBusqueda.buscar(prod);
                if(arbolCompra.buscar(prod)==null) {
                    arbolCompra.insertar(prod, nombre);
                    arbolDataBase.insertar(prod,1);
                }else {
                    int cantidad=arbolDataBase.buscar(prod);
                    arbolDataBase.insertar(prod,cantidad+1);
                }
                nombreProd.setText(nombre);
                String deploy=listaDeCompra();
                textArea1.setText(deploy);
            }
        });
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int prod= Integer.parseInt(codigoProd.getText());
                arbolCompra.eliminar(prod);
                arbolDataBase.eliminar(prod);
                String deploy=listaDeCompra();
                textArea1.setText(deploy);
            }
        });
        AVLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arbolBusqueda=new AVL<>();
                setArbol();
                sendArbol();
                textArea1.setText("");
                treeArea.setText(arbolBusqueda.toString());
                System.out.println(arbolBusqueda.toString());
            }
        });
        ABBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arbolBusqueda=new ArbolBinarioBusqueda<>();
                setArbol();
                sendArbol();
                textArea1.setText("");
                treeArea.setText(arbolBusqueda.toString());
                System.out.println(arbolBusqueda.toString());
            }
        });
        AMVButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    arbolBusqueda=new ArbolMViasBusqueda<>(4);
                    setArbol();
                    sendArbol();
                    textArea1.setText("");
                    treeArea.setText(arbolBusqueda.toString());
                    System.out.println(arbolBusqueda.toString());
                } catch (OrdenInvalidoExcepcion ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        disminuirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int prod= Integer.parseInt(codigoProd.getText());
                int cantidad=arbolDataBase.buscar(prod);
                if(cantidad>1) {
                    arbolDataBase.insertar(prod, cantidad - 1);
                }else{
                    arbolCompra.eliminar(prod);
                    arbolDataBase.eliminar(prod);
                }
                String deploy=listaDeCompra();
                textArea1.setText(deploy);
            }
        });
        purgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int prod= Integer.parseInt(codigoProd.getText());
                arbolBusqueda.eliminar(prod);
                treeArea.setText(arbolBusqueda.toString());
            }
        });
        manifestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int prod= Integer.parseInt(codigoProd.getText());
                if(arbolBusqueda.buscar(prod)==null){
                    String ungulate=nombreProd.getText();
                    arbolBusqueda.insertar(prod,ungulate);
                    treeArea.setText(arbolBusqueda.toString());
                }
            }
        });
    }


    public String listaDeCompra(){
        List<Integer> compras=arbolCompra.recorridoEnInOrden();
        String lista="";
        for(int i=0;i<compras.size();i++){
            lista+=compras.get(i)+"("+arbolDataBase.buscar(compras.get(i))+")"+arbolCompra.buscar(compras.get(i))+"\n";
        }
        return lista;
    }



    public static GraphicInterface createGraphicInterface() {
        return new GraphicInterface();
    }

    public void setArbol(){
        arbolBusqueda.insertar(313,"Coca Cola 2Lts");
        arbolBusqueda.insertar(314,"Coca Cola 3Lts");
        arbolBusqueda.insertar(321,"Mendocina Manzana 2Lts");
        arbolBusqueda.insertar(322,"Mendocina Naranja 2Lts");
        arbolBusqueda.insertar(304,"Leche Blanca Deslactosada 1 Lts");
        arbolBusqueda.insertar(300,"Leche Blanca 1Lts");
        arbolBusqueda.insertar(301,"Leche Blanca Deslactosada 1 Lts");
        arbolBusqueda.insertar(302,"Leche Chocolatada 1 Lts");
        arbolBusqueda.insertar(303,"Leche Chocolatada Deslactosada 1 Lts");
        arbolBusqueda.insertar(305,"Yogurt Botella 2Lts");
        arbolBusqueda.insertar(306,"Yogurt Coco 1Lts");
        arbolBusqueda.insertar(307,"Yogurt Coco Botella 2Lts");
        arbolBusqueda.insertar(308,"Yogurt Frutilla 1Lts");
        arbolBusqueda.insertar(309,"Yogurt Frutilla Botella 2Lts");
        arbolBusqueda.insertar(310,"Pilfrut Manzana 1Lts");

        arbolBusqueda.insertar(318,"Pepsi 3Lts");
        arbolBusqueda.insertar(319,"Pepsi Black 2Lts");
        arbolBusqueda.insertar(320,"Pepsi Black 3Lts");
        arbolBusqueda.insertar(323,"Mendocina Mandarina 2Lts");

        arbolBusqueda.insertar(400,"Mantequilla");
        arbolBusqueda.insertar(401,"Margarina");
        arbolBusqueda.insertar(402,"Queso 1Kg");
        arbolBusqueda.insertar(311,"Pilfrut Durazno 1Lts");
        arbolBusqueda.insertar(312,"Pilfrut Piña 1Lts");

        arbolBusqueda.insertar(315,"Coca Cola Zero 2Lts");
        arbolBusqueda.insertar(316,"Coca Cola Zero 3Lts");
        arbolBusqueda.insertar(317,"Pepsi 2Lts");
        arbolBusqueda.insertar(403,"Queso 2Kg");
        arbolBusqueda.insertar(324,"Mendocina Piña 2Lts");
        arbolBusqueda.insertar(325,"Maltin");
        arbolBusqueda.insertar(326,"Malta");
        arbolBusqueda.insertar(327,"Maltita");
        arbolBusqueda.insertar(328,"Powerade Red 1Lts");

        arbolBusqueda.insertar(404,"Salchicha Viena");
    }


    public void sendArbol() {
        arbolCompra=new AVL<>();
        arbolDataBase=new AVL<>();
    }
}
