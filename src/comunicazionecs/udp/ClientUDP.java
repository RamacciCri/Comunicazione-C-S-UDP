
package comunicazionecs.udp;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author RamacciCri
 */
public class ClientUDP {

    public static void main(String[] args) {
        int portaServer = 6789;  //porta del server
        try {
            InetAddress IPServer = InetAddress.getByName("localhost");
            
            byte[] bufferOUT = new byte[256];  //buffer di spedizione e ricezione
            byte[] bufferIN = new byte[256];
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            
            //creazione del socket
            DatagramSocket clientSocket = new DatagramSocket();
            System.out.println("Client pronto, inserisci un dato da inviare: ");
            
            //preparazione del messaggio da spedire
            String daSpedire;
            daSpedire = input.readLine();
            bufferOUT = daSpedire.getBytes();
            
            //trasmissione del dato al server
            DatagramPacket sendPacket = new DatagramPacket(bufferOUT, bufferOUT.length, IPServer, portaServer);
            clientSocket.send(sendPacket);
            
            //ricezione del dato dal server
            DatagramPacket receivePacket = new DatagramPacket(bufferIN, bufferIN.length);
            clientSocket.receive(receivePacket);
            String ricevuto = new String(receivePacket.getData());
            
            //elaborazione dei dati ricevuti
            int numCaratteri = receivePacket.getLength();
            ricevuto = ricevuto.substring(0, numCaratteri);
            System.out.println("Dal server: " + ricevuto);
            
            //termine elaborazione
            clientSocket.close();
        } 
        catch (UnknownHostException ex) {
            Logger.getLogger(ClientUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
                Logger.getLogger(ClientUDP.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
    
}
