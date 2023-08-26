
package testcontrolserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;


public class TestControlServer {

    
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        int x = 12, y = 13;
        System.out.println("Authentication and Key Agreement:");
        System.out.println("***************************************");
        System.out.println("Waiting for Service Providing Server.....");
        ServerSocket s1 = new ServerSocket(4567);
        Socket s2 = s1.accept();        
        Scanner ss = new Scanner(s2.getInputStream());
        PrintStream p = new PrintStream(s2.getOutputStream());
        
        BufferedReader rd = new BufferedReader(new InputStreamReader(s2.getInputStream()));
        StringBuilder sb1 = new StringBuilder();
        String line=null;
        while(!(line = rd.readLine()).equals("$")){
            sb1.append(line + "\n");
        }
        String out = sb1.toString();
    //    System.out.println("out = "+out);
        StringBuilder sb =new StringBuilder();
        String str[] = new String[11];
        int j=0;
        for(int i=0;i<11;i++){
            str[i] = null;
            while(out.charAt(j)!='$'){
             //   System.out.print(out.charAt(j));
                sb.append(out.charAt(j));
                j++;
            }
            j++;
            str[i]=sb.toString();
            sb.delete(0, sb.length());
        }
        
        String Fi = str[0];
        System.out.println("Fi = "+Fi);
        String CIDi = str[1];
        System.out.println("CIDi = "+CIDi);
        String Gi = str[2];
        System.out.println("Gi = "+Gi);
        String Pij = str[3];
        System.out.println("Pij = "+Pij);
        String PID = str[4];
        System.out.println("PID = "+PID);
        String asd = str[5];
        int TSi = Integer.parseInt(asd);
        System.out.println("TSi = "+TSi);
        String Ji = str[6];
        System.out.println("Ji = "+Ji);
        String Ki = str[7];
        System.out.println("Ki = "+Ki);
        String Li = str[8];
        System.out.println("Li = "+Li);
        String Mi = str[9];
        System.out.println("Mi = "+Mi);
        String PSID = str[10];
        System.out.println("PSID = "+PSID);
        
        String t1 = PSID + y;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] t2 = digest.digest(t1.getBytes(StandardCharsets.UTF_8));
        String BSj = bytesToHex(t2);
        System.out.println("BSj = "+BSj);
        
        String t = BitwiseXOR(Ji,BSj);
        int Ni2 = Integer.parseInt(t);
  //      System.out.println("t = "+t);
        System.out.println("Ni2 = "+Ni2);
        
        t1 = Ni2 + BSj + Pij + TSi;
        digest = MessageDigest.getInstance("SHA-256");
        t2 = digest.digest(t1.getBytes(StandardCharsets.UTF_8));
        String eKi = bytesToHex(t2);
        System.out.println("eKi = "+eKi);
        
        t1 = PID + x;
        digest = MessageDigest.getInstance("SHA-256");
        t2 = digest.digest(t1.getBytes(StandardCharsets.UTF_8));
        String Bi = bytesToHex(t2);
        System.out.println("Bi = "+Bi);
        
        String s = BitwiseXOR(Fi,Bi);
        int Ni1 = Integer.parseInt(s);
  //      System.out.println("s = "+s);
        System.out.println("Ni1 = "+Ni1);
        
        t1 = Bi + Ni1 + TSi + "00";
        digest = MessageDigest.getInstance("SHA-256");
        t2 = digest.digest(t1.getBytes(StandardCharsets.UTF_8));
        String t3 = bytesToHex(t2);
        String ID = BitwiseXOR(CIDi,t3);
        System.out.println("ID = "+ID);
        
        t1 = BSj + Ni2 + TSi + "00";
        digest = MessageDigest.getInstance("SHA-256");
        t2 = digest.digest(t1.getBytes(StandardCharsets.UTF_8));
        t3 = bytesToHex(t2);
        String SID = BitwiseXOR(Li,t3);
        System.out.println("SID = "+SID);
        
        t1 = Ni1 + SID + PID + TSi;
        digest = MessageDigest.getInstance("SHA-256");
        t2 = digest.digest(t1.getBytes(StandardCharsets.UTF_8));
        t3 = bytesToHex(t2);
        String t4 = BitwiseXOR(Bi,t3);
        digest = MessageDigest.getInstance("SHA-256");
        t2 = digest.digest(t4.getBytes(StandardCharsets.UTF_8));
        String ePij = bytesToHex(t2);
        System.out.println("ePij = "+ePij);
    
        t1 = Bi + Ni1 + TSi + "11";
        digest = MessageDigest.getInstance("SHA-256");
        t2 = digest.digest(t1.getBytes(StandardCharsets.UTF_8));
        t3 = bytesToHex(t2);
        String st1 = BitwiseXOR(Gi,t3);
        int b = Integer.parseInt(st1);
        System.out.println("b = "+b);
        
        t1 = BSj + Ni2 + TSi + "11";
        digest = MessageDigest.getInstance("SHA-256");
        t2 = digest.digest(t1.getBytes(StandardCharsets.UTF_8));
        t3 = bytesToHex(t2);
        String st2 = BitwiseXOR(Mi,t3);
        int d = Integer.parseInt(st2);
        System.out.println("d = "+d);
        
        t1 = ID + b;
        digest = MessageDigest.getInstance("SHA-256");
        t2 = digest.digest(t1.getBytes(StandardCharsets.UTF_8));
        String ePID = bytesToHex(t2);
        System.out.println("ePID = "+ePID);
        
        t1 = SID + d;
        digest = MessageDigest.getInstance("SHA-256");
        t2 = digest.digest(t1.getBytes(StandardCharsets.UTF_8));
        String ePSID = bytesToHex(t2);
        System.out.println("ePSID = "+ePSID);
        
        int Ni3 = 15;
        t1 = SID + Ni2 + BSj;
        digest = MessageDigest.getInstance("SHA-256");
        t2 = digest.digest(t1.getBytes(StandardCharsets.UTF_8));
        t3 = bytesToHex(t2);
        t4 = Integer.toString(Ni1);
        String t5 = Integer.toString(Ni3);
        String t6 = BitwiseXOR(t4,t5);
        String Pi = BitwiseXOR(t6,t3);
        System.out.println("Pi = "+Pi);
        
        digest = MessageDigest.getInstance("SHA-256");
        t2 = digest.digest(t6.getBytes(StandardCharsets.UTF_8));
        String Qi = bytesToHex(t2);
        System.out.println("Qi = "+Qi);
       
        t1 = ID + Ni1 + Bi;
        digest = MessageDigest.getInstance("SHA-256");
        t2 = digest.digest(t1.getBytes(StandardCharsets.UTF_8));
        t3 = bytesToHex(t2);
        t4 = Integer.toString(Ni2);
        t5 = Integer.toString(Ni3);
        t6 = BitwiseXOR(t4,t5);
        String Ri = BitwiseXOR(t6,t3);
        System.out.println("Ri = "+Ri);
        
        digest = MessageDigest.getInstance("SHA-256");
        t2 = digest.digest(t6.getBytes(StandardCharsets.UTF_8));
        String Vi = bytesToHex(t2);
        System.out.println("Vi = "+Vi);
        
        out = Pi + "$" + Qi + "$" + Ri + "$" + Vi + "$" + "\n$\n";
        p.println(out);
        
        t1 = Integer.toString(Ni1);
        t4 = Integer.toString(Ni2);
        t3 = Integer.toString(Ni3);
        t5 = BitwiseXOR(t1,t4);
        String key = BitwiseXOR(t3,t5);
        System.out.println("key = "+key);
        
        s1.close();
                       
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
    
    private static String BitwiseXOR(String Bi, String Ci){
        int i;
        byte[] a = Bi.getBytes();
        byte[] b = Ci.getBytes();
       // byte[] c;
    //    StringBuilder Di = new StringBuilder();
        for(i=0;i<Bi.length();i++){
            a[i] = (byte)(a[i]^b[i]);
        }
        String out = new String(a);
        return out;
    }
    
    private static String BitwiseXORInt(String Bi, int Ni1){
        int i;
        byte[] a = Bi.getBytes();
       // byte[] c;
    //    StringBuilder Di = new StringBuilder();
        for(i=0;i<Bi.length();i++){
            a[i] = (byte)(a[i]^Ni1);
        }
        String out = new String(a);
        return out;
    }
    
}
