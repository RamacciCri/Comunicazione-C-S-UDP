package comunicazionecs.udp;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author RamacciCri
 */
public class ServerUDP {

    public static void main(String[] args) {
        boolean attivo = true;            //per la ripetizione del servizio
        byte[] bufferIN = new byte[256];  //buffer spedizione e ricezione
        byte[] bufferOUT = new byte[256];
        try {
            DatagramSocket serverSocket = new DatagramSocket(6789);
            System.out.println("Server avviato");
            while(attivo) {
                //definizione del datagram
                DatagramPacket receivePacket = new DatagramPacket(bufferIN, bufferIN.length);
                //attesa della ricezione del dato dal client
                serverSocket.receive(receivePacket);
                //analisi del pacchetto ricevuto
                String ricevuto = new String(receivePacket.getData());
                int numCaratteri = receivePacket.getLength();
                ricevuto = ricevuto.substring(0, numCaratteri); //elimina i caratteri in eccesso
                System.out.println("ricevuto: " + ricevuto);

                //riconoscimento dei parametri del socket del client
                InetAddress IPClient = receivePacket.getAddress();
                int portaClient = receivePacket.getPort();
                
                //preparo il dato da spedire
                String daSpedire = ricevuto.toUpperCase();
                bufferOUT = daSpedire.getBytes();
                //invio del datagramma
                DatagramPacket sendPacket = new DatagramPacket(bufferOUT, bufferOUT.length, IPClient, portaClient);
                serverSocket.send(sendPacket);
                
                //controllo termine esecuzione del server
                if(ricevuto.equals("fine")) {
                    System.out.println("Server in chiusura");
                    attivo = false;
                }
            }
            serverSocket.close();
            
        } catch (SocketException ex) {
            Logger.getLogger(ServerUDP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex){
            Logger.getLogger(ServerUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
