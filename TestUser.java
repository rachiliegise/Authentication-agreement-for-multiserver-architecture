
package testuser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;


public class TestUser {

    
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        Socket s1 = new Socket("localhost",1234);
        Scanner ks = new Scanner(System.in);
        Scanner ss = new Scanner(s1.getInputStream());
        PrintStream p = new PrintStream(s1.getOutputStream());
        
        System.out.println("User Registration:");
        System.out.println("**********************");
        
        System.out.println("Enter the ID:");
        String ID = ks.next();
        System.out.println("Enter the password:");
        String pswd = ks.next();
        System.out.println("Enter a number:");
        int a = ks.nextInt();
        
        String t1 = a + pswd;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] t2 = digest.digest(t1.getBytes(StandardCharsets.UTF_8));
        String Ai = bytesToHex(t2);
        
        p.println(ID);
        p.println(Ai);
        p.println(a);
        
        String Bi = ss.next();
        s1.close();
        System.out.println("Bi = "+Bi);
        
        t1 = ID + Ai;
        digest = MessageDigest.getInstance("SHA-256");
        t2 = digest.digest(t1.getBytes(StandardCharsets.UTF_8));
        String Ci = bytesToHex(t2);
        String Di = BitwiseXOR(Bi,Ci);
        System.out.println("Ci = "+Ci);
        System.out.println("Di = "+Di);
        System.out.println("User Registration Completed successfully.");
        
   //     System.out.println("Do you want to login?(y/n):");
   //     String ch = ks.next();
     
        System.out.println("User Login:");
        System.out.println("****************");

    while(true){
        System.out.println("Enter the ID:");
        String eID = ks.next();
        System.out.println("Enter the Password:");
        String epswd = ks.next();
        
        t1 = a + epswd;
        digest = MessageDigest.getInstance("SHA-256");
        t2 = digest.digest(t1.getBytes(StandardCharsets.UTF_8));
        String eAi = bytesToHex(t2);
        
        t1 = eID + eAi;
        digest = MessageDigest.getInstance("SHA-256");
        t2 = digest.digest(t1.getBytes(StandardCharsets.UTF_8));
        String eCi = bytesToHex(t2);
        
        if(Ci.equals(eCi))
        {
            System.out.println("Authenticate User. Login Process completed successfully.");
            break;
        }
        else
            System.out.println("Unauthenticate User. Try again.");
    }     
       
        System.out.println("Authentication and Key Agreement:");
        System.out.println("**************************************");
        
        s1 = new Socket("localhost",3456);
        ss = new Scanner(s1.getInputStream());
        p = new PrintStream(s1.getOutputStream());
        
        String eBi = BitwiseXOR(Ci,Di);
        int Ni1 = 12, TSi = 5;
        String t = Integer.toString(Ni1);
        String Fi = BitwiseXOR(t,eBi);
        
        t1 = eBi + Ni1 + TSi + "00";
        digest = MessageDigest.getInstance("SHA-256");
        t2 = digest.digest(t1.getBytes(StandardCharsets.UTF_8));
        String t3 = bytesToHex(t2);
        String CIDi = BitwiseXOR(ID,t3);
        
        t1 = eBi + Ni1 + TSi + "11";
        digest = MessageDigest.getInstance("SHA-256");
        t2 = digest.digest(t1.getBytes(StandardCharsets.UTF_8));
        t3 = bytesToHex(t2);
        t = Integer.toString(a);
        String Gi = BitwiseXOR(t,t3);
        
        String SID = "sps1";
        t1 = ID + a;
        digest = MessageDigest.getInstance("SHA-256");
        t2 = digest.digest(t1.getBytes(StandardCharsets.UTF_8));
        String PID = bytesToHex(t2);
        t1 = Ni1 + SID + PID + TSi;
        digest = MessageDigest.getInstance("SHA-256");
        t2 = digest.digest(t1.getBytes(StandardCharsets.UTF_8));
        t3 = bytesToHex(t2);
        String t4 = BitwiseXOR(Bi,t3);
        digest = MessageDigest.getInstance("SHA-256");
        t2 = digest.digest(t4.getBytes(StandardCharsets.UTF_8));
        String Pij = bytesToHex(t2);
        
        System.out.println("Fi = "+Fi);
    //    p.println(Fi);
        System.out.println("CIDi = "+CIDi);
    //    p.println(CIDi);
        System.out.println("Gi = "+Gi);
    //    p.println(Gi);
        System.out.println("Pij = "+Pij);
    //    p.println(Pij);
        System.out.println("PID = "+PID);
    //    p.println(PID);
        System.out.println("TSi = "+TSi);
    //    p.println(TSi);
    
        String out = Fi + "$" + CIDi + "$" + Gi + "$" + Pij + "$" + PID + "$" + TSi + "$" + "\n$\n";
    //    System.out.println("out = "+out);
        p.println(out);
    
        BufferedReader rd1 = new BufferedReader(new InputStreamReader(s1.getInputStream()));
        StringBuilder sb2 = new StringBuilder();
        String line1=null;
        while(!(line1 = rd1.readLine()).equals("$")){
            sb2.append(line1 + "\n");
        }
        out = sb2.toString();
        
        s1.close();
    //    System.out.println("out = "+out);
        StringBuilder sb3 =new StringBuilder();
        String str1[] = new String[4];
        int j=0;
        for(int i=0;i<2;i++){
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
        
        String Ri = str1[0];
        System.out.println("Ri = "+Ri);
        String Vi = str1[1];
        System.out.println("Vi = "+Vi);
            
        t1 = ID + Ni1 + Bi;
        digest = MessageDigest.getInstance("SHA-256");
        t2 = digest.digest(t1.getBytes(StandardCharsets.UTF_8));
        t3 = bytesToHex(t2);
        t4 = BitwiseXOR(Ri,t3);
  //      System.out.println("Pi = "+Pi);
        
        digest = MessageDigest.getInstance("SHA-256");
        t2 = digest.digest(t4.getBytes(StandardCharsets.UTF_8));
        String eVi = bytesToHex(t2);
        System.out.println("eVi = "+eVi);
        
        t1 = Integer.toString(Ni1);
        String key = BitwiseXOR(t1,t4);
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
