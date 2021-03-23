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
            payasin.setApodo("platanito");
            payasin.setCirco("campo 40");
            payasin.setNivelDeComedia(8);
            byte[] byteArray=getByteArray(payasin);
            String message = "testiri";
            channel.basicPublish("", QUEUE_NAME, null, byteArray);
            System.out.println(" [x] Sent '" + payasin + "'"+" TESTIRI");
        }
    }

    public static byte[] getByteArray(Payaso payasin) throws IOException {
        ByteArrayOutputStream out= new ByteArrayOutputStream();
        ObjectOutputStream os =new ObjectOutputStream(out);
        os.writeObject(payasin);
        return out.toByteArray();
    }
}
