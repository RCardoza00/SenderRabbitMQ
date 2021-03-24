/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sender;

import Entidad.Payaso;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 *
 * @author esnip
 */
public class Sender {

   /*
    
    */
    //aqui se define a que cola se enviara el mensaje o entidad
  private final static String QUEUE_NAME = "entidad";
  public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //creacion de la entidad a enviar
            Payaso payasin=new Payaso();
//            Scanner tec=new Scanner(System.in);
//            System.out.println("Introduce el apodo del payaso: ");
//            String apodo=tec.next();
//            System.out.println("Introduce el circo al que pertenece el payaso: ");
//            String circo=tec.next();
//            System.out.println("Introduce el nivel de comedia del payaso: ");
//            int nivel=tec.nextInt();
            int envios=0;
            while(envios<60){
                 payasin.setApodo("Payaso del rodeo");
            payasin.setCirco("Du solei");
            payasin.setNivelDeComedia(6+envios);
            byte[] byteArray=getByteArray(payasin);
            String message = "testiri";
            channel.basicPublish("", QUEUE_NAME, null, byteArray);
            System.out.println(" [x] Envié '" + payasin + "'"+" TESTIRI");
            envios++;
            Thread.sleep(500);
            }
           
        }
    }

    public static byte[] getByteArray(Payaso payasin) throws IOException {
        ByteArrayOutputStream out= new ByteArrayOutputStream();
        ObjectOutputStream os =new ObjectOutputStream(out);
        os.writeObject(payasin);
        return out.toByteArray();
    }
}
