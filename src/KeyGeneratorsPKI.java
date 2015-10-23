/**
 * Created by schandramouli on 10/23/15.
 */

import java.io.ByteArrayOutputStream;
import java.security.*;

public class KeyGeneratorsPKI {


    private PrivateKey privateKey;
    private PublicKey publicKey;

    public void generateKeys()
    {
        // this generates a pub/priv keypair that can be used for PKI
        // store these as part of the object, so we dont have to worry
        // about transmitting the pub/priv keys
        try
        {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
            keyGen.initialize(1024);
            KeyPair keypair = keyGen.generateKeyPair();
            this.privateKey = keypair.getPrivate();
            this.publicKey = keypair.getPublic();
            System.out.println("Public key: " +  getString(publicKey.getEncoded()));
            System.out.println("Private key: " + getString(privateKey.getEncoded()));
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }

    public static String getString(byte[] bytes) {
        // convenience method for creating a string of the appropriate
        // format, that can then be decoded using dsa.verify
        StringBuilder sb = new StringBuilder();
        for( int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            sb.append(0x00FF & b);
            // we need these '-' to create separate tokens, than can then be decoded
            if (i + 1 < bytes.length) {
                sb.append( "-" );
            }
        }
        return sb.toString();
    }

    private static byte[] getBytes(String str) {
        // convenience method for creating a byte array rep of the string
        // cant use BIF .getBytes() cuz we need to split the string on "-"
        // to be precise, each token is 1-3 digits long, and represents a byte
        // i.e 0 - 255
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        String[] st = str.split("-");
        for (String aString : st) {
            int k = Integer.parseInt(aString);
            byteArray.write((byte) k);
        }
        return byteArray.toByteArray();
    }


    public String signRequest(String message) {
        try
        {
            Signature dsa = Signature.getInstance("SHA1withDSA");
            dsa.initSign(privateKey);
            dsa.update(message.getBytes());
            byte[] signature = dsa.sign();
            return getString(signature);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public boolean verifySignature(String message, String signature) {
        try
        {
            Signature dsa = Signature.getInstance("SHA1withDSA");
            dsa.initVerify(publicKey);
            dsa.update(message.getBytes());
            //System.out.println(getString(getBytes(signature)));
            return dsa.verify(getBytes(signature));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        // generate the keypair - pub and priv
        KeyGeneratorsPKI keys = new KeyGeneratorsPKI();
        keys.generateKeys();

        // this is the plaintext message that needs to be signed with the private key
        String message = "hello sai";
        // sign it!
        String signedRequest = keys.signRequest(message);
        System.out.println("Encoded message: " + signedRequest);

        // check if the message returned is the same? decode using pub key
        if (keys.verifySignature(message, signedRequest)) {
            // keys are fine, PKI successful yay!
            System.out.println("Keys Verified");
        } else {
            // keys weren't proper, log the user out maybe?
            System.out.println("Keys NOT Verified");
        }
    }
}
