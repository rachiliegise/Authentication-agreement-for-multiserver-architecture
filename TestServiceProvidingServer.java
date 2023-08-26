
package testserviceprovidingserver;

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


public class TestServiceProvidingServer {

    
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        Socket s1 = new Socket("localhost",2345);
        Scanner ks = new Scanner(System.in);
        Scanner ss = new Scanner(s1.getInputStream());
        PrintStream p = new PrintStream(s1.getOutputStream());
        
        System.out.println("Service Providing Server Registration:");
        System.out.println("********************************************");
        System.out.println("Enter the ID:");
        String SID = ks.next();
        System.out.println("Enter a number:");
        int a = ks.nextInt();
        
        p.println(SID);
        p.println(a);
        
        String BSj = ss.next();
        s1.close();
        System.out.println("BSj = "+BSj);
        System.out.println("Serveice Providing Server Registration completed successfully.");
        
        System.out.println("Authentication and Key Agreement:");
        System.out.println("***************************************");
        System.out.println("Waiting for User.....");
        
        ServerSocket s2 = new ServerSocket(3456);
        s1 = s2.accept();
        ss = new Scanner(s1.getInputStream());
        p = new PrintStream(s1.getOutputStream());
      
        BufferedReader rd = new BufferedReader(new InputStreamReader(s1.getInputStream()));
        StringBuilder sb1 = new StringBuilder();
        String line=null;
        while(!(line = rd.readLine()).equals("$")){
            sb1.append(line + "\n");
        }
        String out = sb1.toString();
    //    System.out.println("out = "+out);
        StringBuilder sb =new StringBuilder();
        String str[] = new String[6];
        int j=0;
        for(int i=0;i<6;i++){
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
        
        Socket s3 = new Socket("localhost",4567);
        Scanner ss1 = new Scanner(s3.getInputStream());
        PrintStream q = new PrintStream(s3.getOutputStream());
        
        int Ni2 = 134;
        String t = Integer.toString(Ni2);
    //    System.out.println("t = "+t);
        String Ji = BitwiseXOR(t,BSj);
                
        String t1 = Ni2 + BSj + Pij + TSi;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] t2 = digest.digest(t1.getBytes(StandardCharsets.UTF_8));
        String Ki = bytesToHex(t2);
        
        t1 = BSj + Ni2 + TSi + "00";
        digest = MessageDigest.getInstance("SHA-256");
        t2 = digest.digest(t1.getBytes(StandardCharsets.UTF_8));
        String t3 = bytesToHex(t2);
        String Li = BitwiseXOR(SID,t3);
        
        t1 = BSj + Ni2 + TSi + "11";
        digest = MessageDigest.getInstance("SHA-256");
        t2 = digest.digest(t1.getBytes(StandardCharsets.UTF_8));
        t3 = bytesToHex(t2);
        t = Integer.toString(a);
        String Mi = BitwiseXOR(t,t3);
        
        t1 = SID + a;
        digest = MessageDigest.getInstance("SHA-256");
        t2 = digest.digest(t1.getBytes(StandardCharsets.UTF_8));
        String PSID = bytesToHex(t2);
        
   /*     q.println(Fi);
        q.println(CIDi);
        q.println(Gi);
        q.println(Pij);
        q.println(PID);
        q.println(TSi);   */
        System.out.println("Ji = "+Ji);
  //      q.println(Ji);
        System.out.println("Ki = "+Ki);
  //      q.println(Ki);
        System.out.println("Li = "+Li);
  //      q.println(Li);
        System.out.println("Mi = "+Mi);
  //      q.println(Mi);
        System.out.println("PSID = "+PSID);
  //      q.println(PSID);
        
        out = Fi + "$" + CIDi + "$" + Gi + "$" + Pij + "$" + PID + "$" + TSi + "$" + Ji + "$" + Ki + "$" + Li + "$" + Mi + "$" + PSID + "$" + "\n$\n";
        q.println(out);
        
        BufferedReader rd1 = new BufferedReader(new InputStreamReader(s3.getInputStream()));
        StringBuilder sb2 = new StringBuilder();
        String line1=null;
        while(!(line1 = rd1.readLine()).equals("$")){
            sb2.append(line1 + "\n");
        }
        out = sb2.toString();
        
        s3.close();
    //    System.out.println("out = "+out);
        StringBuilder sb3 =new StringBuilder();
        String str1[] = new String[4];
        j=0;
        for(int i=0;i<4;i++){
            str1[i] = null;
            while(out.charAt(j)!='$'){
             //   System.out.print(out.charAt(j));
                sb3.append(out.charAt(j));
                j++;
            }
            j++;
            str1[i]=sb3.toString();
            sb3.delete(0, sb3.length());
        }
        
        String Pi = str1[0];
        System.out.println("Pi = "+Pi);
        String Qi = str1[1];
        System.out.println("Qi = "+Qi);
        String Ri = str1[2];
        System.out.println("Ri = "+Ri);
        String Vi = str1[3];
        System.out.println("Vi = "+Vi);
    
        t1 = SID + Ni2 + BSj;
        digest = MessageDigest.getInstance("SHA-256");
        t2 = digest.digest(t1.getBytes(StandardCharsets.UTF_8));
        t3 = bytesToHex(t2);
        String t4 = BitwiseXOR(Pi,t3);
  //      System.out.println("Pi = "+Pi);
        
        digest = MessageDigest.getInstance("SHA-256");
        t2 = digest.digest(t4.getBytes(StandardCharsets.UTF_8));
        String eQi = bytesToHex(t2);
        System.out.println("eQi = "+eQi);
        
        out = Ri + "$" + Vi + "$" + "\n$\n";
        
        p.println(out);
        
        t1 = Integer.toString(Ni2);
        String key = BitwiseXOR(t1,t4);
        System.out.println("key = "+key);
        
        s2.close();
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
