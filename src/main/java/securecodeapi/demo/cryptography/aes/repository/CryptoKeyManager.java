package securecodeapi.demo.cryptography.aes.repository;

import lombok.Getter;
import org.apache.commons.codec.DecoderException;
import securecodeapi.demo.cryptography.aes.AesKeyGenerator;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import static org.apache.commons.codec.binary.Hex.*;
import static org.apache.commons.io.FileUtils.*;
import static org.apache.commons.io.FileUtils.readFileToString;

import java.io.*;
import java.util.Objects;

@Getter
public class CryptoKeyManager {

    private final SecretKey secretKey;

    private final IvParameterSpec ivParameterSpec;

    private static final File fileAesKeys = new File("aesKeys.txt");

    private static final File fileIvParameters = new File("aesIvParameters.txt");

    private static final int KEY_SIZE = 128;


    CryptoKeyManager() throws NoSuchAlgorithmException, IOException {

        if(Objects.isNull(readFileToString(fileAesKeys))){
            this.secretKey = AesKeyGenerator.generateKey(KEY_SIZE);
            saveKey();
        } else {
            this.secretKey = loadKey();
        }

        if(Objects.isNull(readFileToString(fileIvParameters))) {
            this.ivParameterSpec = AesKeyGenerator.generateIv();
            saveIv();
        } else {
            this.ivParameterSpec = loadIv();
        }
    }


    private void saveKey() throws IOException {
        char[] hex = encodeHex(secretKey.getEncoded());
        writeStringToFile(fileAesKeys, String.valueOf(hex));
    }

    private static SecretKey loadKey() throws IOException {
        String data = new String(readFileToByteArray(fileAesKeys));
        byte[] encoded;
        try {
            encoded = decodeHex(data.toCharArray());
        } catch (DecoderException e) {
            e.printStackTrace();
            return null;
        }
        return new SecretKeySpec(encoded, "AES");
    }

    private void saveIv() throws IOException {
        char[] hex = encodeHex(this.ivParameterSpec.getIV());
        writeStringToFile(fileIvParameters, String.valueOf(hex));
    }

    private static IvParameterSpec loadIv() throws IOException {
        String data = new String(readFileToByteArray(fileIvParameters));
        byte[] encoded;
        try {
            encoded = decodeHex(data.toCharArray());
        } catch (DecoderException e) {
            e.printStackTrace();
            return null;
        }
        return new IvParameterSpec(encoded);
    }

}
