package p2.pt.ipleiria.estg.dei.securesms; /**
 *
 * @author Carlos Gomes
 */
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import android.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
/**
 *
 * @author Carlos Gomes
 */
public class Encriptacao {
    private static final String ALGORITHM = "AES";
    private String key = "Bar12345Bar12345";
    private Cipher ecipher;
    private Cipher dcipher;

    public Encriptacao() {
        init();
    }



    public String encrypt(String str) throws Exception {
        // Encode the string into bytes using utf-8
        Key key = generateKey();
        ecipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] utf8 = str.getBytes("UTF8");

        // Encrypt
        byte[] enc = ecipher.doFinal(utf8);

        // Encode bytes to base64 to get a string
        return Base64.encodeToString(enc, Base64.DEFAULT);
        //return new sun.misc.BASE64Encoder().encode(enc);
    }

    public String decrypt(String str) throws Exception {
        Key key = generateKey();
        dcipher.init(Cipher.DECRYPT_MODE, key);
        // Decode base64 to get bytes

        //byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);

        byte[] dec = Base64.decode(str, Base64.DEFAULT);

        byte[] utf8 = dcipher.doFinal(dec);

        // Decode using utf-8
        return new String(utf8, "UTF8");
    }

    private Key generateKey(){
        Key aesKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
        return aesKey;
    }

    private void init(){
        try {
            ecipher = Cipher.getInstance(ALGORITHM);
            dcipher = Cipher.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Encriptacao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Encriptacao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static String getALGORITHM() {
        return ALGORITHM;
    }

    /*public String encryptWithKey(String str, String key)throws Exception {
        // Encode the string into bytes using utf-8
        Key chave = generateKey(key);
        ecipher.init(Cipher.ENCRYPT_MODE, chave);
        byte[] utf8 = str.getBytes("UTF8");

        // Encrypt
        byte[] enc = ecipher.doFinal(utf8);

        // Encode bytes to base64 to get a string
        return new sun.misc.BASE64Encoder().encode(enc);
    }

    public String decryptWithKey(String str, String key) throws Exception {
        Key chave = generateKey(key);
        dcipher.init(Cipher.DECRYPT_MODE, chave);
        // Decode base64 to get bytes
        byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);

        byte[] utf8 = dcipher.doFinal(dec);

        // Decode using utf-8
        return new String(utf8, "UTF8");
    }

    private Key generateKey(String key) {
        Key aesKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
        return aesKey;
    }*/

}
