/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.clientewebsockets;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author ricar
 */
public class Clientes {
    
    private static ExecutorService clientes = Executors.newCachedThreadPool();
    private static Clientes instance = new Clientes();

    private Clientes() {
    }
    
    public void agregarCliente(Runnable cliente) {
        clientes.execute(cliente);
    }
    
    public static Clientes getInstance() {
        return instance;
    }
}
