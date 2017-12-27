package aoc.fifteen;

import javax.xml.bind.DatatypeConverter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Day4 {

    private static final String INPUT = "yzbqklnj";

    public static void main(final String[] args) throws NoSuchAlgorithmException {
        findIndex("00000");
        findIndex("000000");
    }

    private static void findIndex(final String prefix) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        for (int i = 0;; i++) {
            byte[] md5hash = digest.digest((INPUT + i).getBytes());
            String myHash = DatatypeConverter.printHexBinary(md5hash).toUpperCase();
            if (myHash.startsWith(prefix)) {
                System.out.println(myHash + ": " + i);
                break;
            }
        }
    }

}
