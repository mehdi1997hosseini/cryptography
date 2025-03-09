package ir.smarttrustco.cryptography.basic.converter;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public abstract class ConverterData {

    private ConverterData() {
    }

    public static Resource convertStringToResource(String fileName, String fileContent) {
        if (fileName == null && fileContent == null)
            throw new NullPointerException("File path and name can't be null");

        fileName = !fileName.contentEquals(".text") ? fileName + ".text" : fileName;

        Path downloadPath = Path.of(System.getProperty("user.home"), "Downloads", fileName);

        try {
            Files.writeString(downloadPath, fileContent, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return new FileSystemResource(downloadPath.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
