package org.itson.clientewebsockets;

import java.util.Scanner;

public class PruebaClienteWS {

    public static void main(String[] args) {
        WSEndpoint ws = new WSEndpoint();
        Scanner tec = new Scanner(System.in);
        String mensaje = "";

        while (!"bye".equals(mensaje = tec.nextLine())) {
            ws.enviarMensaje(mensaje);
            System.out.println("\n");
        }
    }

}
