package ir.smarttrustco.cryptography.attachmentFile;

import ir.smarttrustco.cryptography.attachmentFile.dto.UploadFile;
import ir.smarttrustco.cryptography.basic.BaseController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file/")
public class FileController extends BaseController<FileEntity,Long,FileService> {

    public FileController(FileService service) {
        super(service);
    }

}
