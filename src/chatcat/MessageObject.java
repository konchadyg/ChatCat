/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatcat;

import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author konch
 */
public class MessageObject implements Serializable {
    private static final String KEYSTRING = "iLoxFy5WQf/1KV3DfGRJDw==";
    String username;
    String hashpasswd;
    int portval;
    String SessionKey;
    int flag;
    String message;
    long checksumval;
    
    
    MessageObject(String uname,String mesg, int port, int flag)
    {
        this.username = uname;
        this.message = mesg;
        this.portval = port;
        this.flag = flag;
        this.checksumval=0;
    }
    MessageObject(String uname, String password,int port) throws NoSuchAlgorithmException
    {
        flag=0;
        this.username = uname;
        this.portval = port;
        this.checksumval=0;
        String passwordToHash = password;
        
        String generatedPassword = null;
        
        
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(passwordToHash.getBytes());
        
        byte[] bytes = md.digest();
        
        StringBuilder sb = new StringBuilder();
        
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        
        generatedPassword = sb.toString();
        
        this.hashpasswd = generatedPassword;
  
    }
    
      public static String DecryptMessage(String etext)
    {
        try {
            byte[] key_inter = Base64.getDecoder().decode(KEYSTRING);
            
            SecretKey sk1 = new SecretKeySpec(key_inter, 0, key_inter.length, "AES");
            
            System.out.println("object value of key is "+ sk1);
            
            Cipher cipher = Cipher.getInstance("AES");
            
            //String decrypted_text = decryption_msg(etext, secret_key1); 
            
            Base64.Decoder decrypt = Base64.getDecoder();
            
            byte[] etext_in_byte = decrypt.decode(etext);
            
            cipher.init(Cipher.DECRYPT_MODE, sk1);
            
            byte[] dtext_in_byte = cipher.doFinal(etext_in_byte);
            
            String dtext = new String(dtext_in_byte);
            
            return dtext;
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ChatCat1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(ChatCat1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(ChatCat1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(ChatCat1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(ChatCat1.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    public static String EncryptMessage(String ptext)
    {
        try {
            
            SecretKey key_new = KeyGenerator.getInstance("AES").generateKey();
            
            String ks1 = Base64.getEncoder().encodeToString(key_new.getEncoded());
            System.out.println("key in string is - " + ks1);
            
            byte[] key_inter = Base64.getDecoder().decode(KEYSTRING);
            SecretKey sk1 = new SecretKeySpec(key_inter, 0, key_inter.length, "AES");
            
            System.out.println("object value of key is "+ sk1);
            
            Cipher cipher;
            cipher = Cipher.getInstance("AES");
            
            System.out.println("Plain Text is - " + ptext);
            
            byte[] ptext_bytes = ptext.getBytes();
            
            cipher.init(Cipher.ENCRYPT_MODE, sk1);
            
            byte[] etext_bytes = cipher.doFinal(ptext_bytes);
            
            Base64.Encoder encrypt = Base64.getEncoder();
            
            String etext = encrypt.encodeToString(etext_bytes);
            
            return etext;
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ChatCat1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(ChatCat1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(ChatCat1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(ChatCat1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(ChatCat1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static long getCheckSumCRC32(String input)
    {
        byte bytes[] = input.getBytes();
        
        Checksum checksum = new CRC32();
        
        checksum.update(bytes, 0, bytes.length);
        
        long chksumval = checksum.getValue();
        
        return chksumval;
    }
    
}
