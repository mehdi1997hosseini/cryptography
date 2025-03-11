package ir.smarttrustco.cryptography.basic.converter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public interface ConvertTextUtil {
    static String compress(String data){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (GZIPOutputStream gzip = new GZIPOutputStream(baos)) {
            gzip.write(data.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }
    static String decompress(String compressedData) {
        byte[] compressedBytes = Base64.getDecoder().decode(compressedData);
        try (GZIPInputStream gzip = new GZIPInputStream(new ByteArrayInputStream(compressedBytes))) {
            return new String(gzip.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
