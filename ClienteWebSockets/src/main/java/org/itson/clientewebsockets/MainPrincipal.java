/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.itson.clientewebsockets;

import presentacion.VistaClienteWS;


/**
 *
 * @author ricar
 */
public class MainPrincipal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Clientes executorService = Clientes.getInstance();
        
        executorService.agregarCliente(new VistaClienteWS());
        
    }

}
