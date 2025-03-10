package ir.smarttrustco.cryptography.basic.converter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

public abstract class ConverterData {

    private ConverterData() {
    }

    public static Resource convertStringToResource(String fileName, String fileContent) {
        if (fileName == null && fileContent == null)
            throw new NullPointerException("File path and name can't be null");

        String formatFilePrivateKey = ".txt";

        fileName = fileName.replaceAll(formatFilePrivateKey, "");

        fileName = fileName.contentEquals(formatFilePrivateKey) ? fileName.replaceAll(formatFilePrivateKey, "") + "_" +
                System.currentTimeMillis() + formatFilePrivateKey : fileName + "_" + System.currentTimeMillis() + formatFilePrivateKey;

        Path downloadPath = Path.of(System.getProperty("user.home"), "Downloads", fileName);

        try {
            Files.writeString(downloadPath, fileContent, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return new FileSystemResource(downloadPath.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File convertMultipartFileToFile(MultipartFile file) {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close(); //IOUtils.closeQuietly(fos);
        } catch (IOException e) {
            convFile = null;
        }
        return convFile;
    }

    public static String readFileAndConvertToString(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder buffer = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            return String.valueOf(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
