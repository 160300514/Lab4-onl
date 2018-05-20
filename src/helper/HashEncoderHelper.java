package helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashEncoderHelper
{
    public int hash(String key)
    {
        int tmp = 0;
        return key == null ? 0:(tmp = key.hashCode() ^ (tmp>>>16));
    }
    public byte[] getMD5(String key) throws NoSuchAlgorithmException
    {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(key.getBytes());
        return md5.digest();
    }
}
