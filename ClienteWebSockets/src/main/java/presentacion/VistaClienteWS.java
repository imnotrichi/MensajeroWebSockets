package presentacion;

import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.itson.clientewebsockets.Clientes;
import org.itson.clientewebsockets.WSEndpoint;

@ClientEndpoint
public class VistaClienteWS extends javax.swing.JFrame implements Runnable {

    /**
     * Creates new form VistaClienteWS
     */
    public VistaClienteWS() {
        initComponents();
        
        serverURI = URI.create("ws://localhost:8080/ServidorWebSockets/echo");
        executorService.agregarCliente(this);

        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(this, serverURI);
        } catch (DeploymentException | IOException ex) {
            Logger.getLogger(WSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Ya me conecté");
        try {
            session.getBasicRemote().sendText(session.getId() + ": Ya me conecté");
        } catch (IOException ex) {
            Logger.getLogger(WSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @OnMessage
    public void onMessage(String mensaje, Session session) {
        areaTextoMensajes.append("\nEl servidor dice: " + mensaje);
    }

    public void enviarMensaje(String mensaje) {
        try {
            session.getBasicRemote().sendText(session.getId() + ": " + mensaje);
        } catch (IOException e) {
            Logger.getLogger(WSEndpoint.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    @Override
    public void run() {
        enviarMensaje(mensaje);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        campoTextoMensaje = new javax.swing.JTextField();
        botonEnviarMensaje = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaTextoMensajes = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Escribe el mensaje:");

        botonEnviarMensaje.setText("Enviar Mensaje");
        botonEnviarMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEnviarMensajeActionPerformed(evt);
            }
        });

        areaTextoMensajes.setColumns(20);
        areaTextoMensajes.setRows(5);
        jScrollPane1.setViewportView(areaTextoMensajes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botonEnviarMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoTextoMensaje))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(campoTextoMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonEnviarMensaje)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botonEnviarMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEnviarMensajeActionPerformed
        mensaje = campoTextoMensaje.getText();
    }//GEN-LAST:event_botonEnviarMensajeActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaTextoMensajes;
    private javax.swing.JButton botonEnviarMensaje;
    private javax.swing.JTextField campoTextoMensaje;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    private URI serverURI;
    private Session session;
    private String mensaje;
    private Clientes executorService = Clientes.getInstance();
}
