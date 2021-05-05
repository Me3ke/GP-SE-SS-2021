package gpse.example.domain;

import gpse.example.util.ResponseFile;
import gpse.example.util.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin("http://localhost:8088")
public class DocumentController {

    public class FileController {

        @Autowired
        private DocumentServiceImpl storageService;

        @PostMapping("/upload")
        public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") File file) {
            String message = "";
            Document document = null;
            try {
                 document = storageService.store(file);

                message = "Uploaded the file successfully: " + document.getDocumentTitle();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + document.getDocumentTitle() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        @GetMapping("/files")
        public ResponseEntity<List<ResponseFile>> getListFiles() {
            List<ResponseFile> files = storageService.getDocuments().stream().map(document -> {
                String documentDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(Long.toString(document.getId()))
                    .toUriString();

                return new ResponseFile(
                    document.getDocumentTitle(),
                    documentDownloadUri,
                    document.getDocumentType(),
                    document.getData().length);
            }).collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.OK).body(files);
        }

        @GetMapping("/files/{id}")
        public ResponseEntity<byte[]> getFile(@PathVariable String id) {
            Document document = null;
            try {
                document = storageService.getDocument(Long.parseLong(id));
            } catch (NumberFormatException e) {
                return null;

            }

            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getDocumentTitle() + "\"")
                .body(document.getData());
        }
    }
}
