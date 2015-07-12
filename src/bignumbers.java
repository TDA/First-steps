import java.math.BigInteger;

/**
 * Created by schandramouli on 7/11/15.
 */
public class bignumbers {
    public static void main(String args[]){
        BigInteger n = new BigInteger("2423153246345743114543234636584785679768962468");
        BigInteger m = BigInteger.valueOf(1341212351);
        System.out.println("Division is " + n.divide(m));
        System.out.println("Mod is " + n.mod(m));
        System.out.println("Multi is " + n.multiply(m));
    }
}
