
package testcontrolserverspsr;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;


public class TestControlServerSPSR {

    
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        int y = 13;
        ServerSocket s = new ServerSocket(2345);
        Socket sock = s.accept();
        Scanner ss = new Scanner(sock.getInputStream());
        PrintStream p = new PrintStream(sock.getOutputStream());
        
        String SID = ss.next();
        System.out.println("SID = "+SID);
        int a = ss.nextInt();
        System.out.println("a = "+a);

        String t1 = SID + a;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] t2 = digest.digest(t1.getBytes(StandardCharsets.UTF_8));
        String PSID = bytesToHex(t2);
        
        t1 = PSID + y;
        digest = MessageDigest.getInstance("SHA-256");
        t2 = digest.digest(t1.getBytes(StandardCharsets.UTF_8));
        String BSj = bytesToHex(t2);
        
        p.println(BSj);
        s.close();
    }
    
    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
}
