package com.itboy.DACNPM.Controller;


import com.itboy.DACNPM.DTO.DocumentDTO;
import com.itboy.DACNPM.DTO.DocumentVersionDTO;
import com.itboy.DACNPM.DTO.UpdateDocumentDTO;
import com.itboy.DACNPM.Enity.Document;
import com.itboy.DACNPM.Enity.DocumentVersion;
import com.itboy.DACNPM.Service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/doc")
public class Controller {
    //http://localhost:8080/api/doc
    @Autowired
    private DocumentService documentService;
//    @Value("${file.upload-dir}") // Đường dẫn thư mục để lưu trữ tệp
//    private String uploadDir;
    @PutMapping("/apply/{idDoc}")//admin
        public ResponseEntity<?> applyFile(@PathVariable("idDoc") Long idDoc,@RequestParam Boolean status){
        return ResponseEntity.ok().body(documentService.applyDocument(idDoc,status));
    }
    @PutMapping("/update/{idDoc}")
    public ResponseEntity<?> updateFile(@PathVariable("idDoc") Long idDoc,@RequestBody UpdateDocumentDTO updateDocumentDTO){
        return ResponseEntity.ok().body(documentService.updateDocument(idDoc,updateDocumentDTO));
    }
    @GetMapping("/getAll")
    public List<Document> getAllProduct(){
        return documentService.getALlDocument();
    }

    @PostMapping("/create")
    public ResponseEntity<Document> createDocument(@RequestBody DocumentDTO documentDTO) {
        Document createdDocument = documentService.createDocument(documentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDocument);
    }
    //add vê xờn mới idDoc là id doc
    @PostMapping("/addversion/{idDoc}")
    public ResponseEntity<Document> createDocument( @PathVariable("idDoc") Long idDoc,
            @RequestBody DocumentVersionDTO documentVersionDTO) {
        Document ADDDocument = documentService.addVersionDocument(idDoc,documentVersionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ADDDocument);
    }
    @GetMapping("/getBysymbolNumber")
    public List<Document>getBySymbolNumber(@RequestParam String symbolNumber){
        return   documentService.findDocbySymbolNumber(symbolNumber);

    }
    @GetMapping("/getByYear")
    public List<Document>getByYear(@RequestParam int year){
        return documentService.findDocumentsByYear(year);

    }
    @GetMapping("/getByField")
    public List<Document>getByField(@RequestParam String field){
        return documentService.findDocumentsByField(field);
    }
    @GetMapping("/getByID/{id}")
    public Document getByID(@PathVariable("id") Long id){
        return documentService.getDoccById(id);

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDoc(@PathVariable("id") Long id){
        documentService.deleteDocument(id);
        return ResponseEntity.ok().body("Deleted");
    }
    @PostMapping(value = "/uploads/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    //POST http://localhost:8088/room
    public ResponseEntity<?> uploadImages(
            @PathVariable("id") Long docId//id vêrsion
            ,
            @ModelAttribute("files") List<MultipartFile> files
    ){
        try {
            DocumentVersion existingProduct = documentService.getDocById(docId);
            files = files == null ? new ArrayList<MultipartFile>() : files;
            DocumentVersion documentVersion  = new DocumentVersion();
            for (MultipartFile file : files) {
                if(file.getSize() == 0) {
                    continue;
                }
                // Kiểm tra kích thước file và định dạng
                if(file.getSize() > 50  * 1024 * 1024) { // Kích thước > 10MB
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("UPLOAD_File_FILE_LARGE");
                }
                String contentType = file.getContentType();
                if(contentType == null || !contentType.startsWith("application/pdf")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("UPLOAD_IMAGES_FILE_MUST_BE_IMAGE");
                }
                // Lưu file và cập nhật thumbnail trong DTO
                String filename = storeFile(file); // Thay thế hàm này với code của bạn để lưu file
                //lưu vào đối tượng product trong DB
                documentVersion = documentService.createDocFile(
                        existingProduct.getId(),
                        DocumentVersionDTO.builder()
                                . filePath(filename)
                                .build()
                );

            }
            return ResponseEntity.ok().body(documentVersion);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    private String storeFile(MultipartFile file) throws IOException {
        if (!isPdfFile(file) || file.getOriginalFilename() == null) {
            throw new IOException("Invalid format");
        }
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        // Thêm UUID vào trước tên file để đảm bảo tên file là duy nhất
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        // Đường dẫn đến thư mục mà bạn muốn lưu file
        java.nio.file.Path uploadDir = Paths.get("uploads");
        // Kiểm tra và tạo thư mục nếu nó không tồn tại
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // Đường dẫn đầy đủ đến file
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        // Sao chép file vào thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }
    private boolean isPdfFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.equals("application/pdf");
    }

//    @GetMapping("/file/{link}")
//    public ResponseEntity<?> viewfile(@PathVariable String link) {
//        try {
//            java.nio.file.Path path = Paths.get("uploads/"+link);
//            UrlResource resource = new UrlResource(path.toUri());
//
//            if (resource.exists()) {
//                return ResponseEntity.ok()
//                        .contentType(MediaType.APPLICATION_PDF)
//                        .body(resource);
//            } else {
//
////                return ResponseEntity.ok()
////                        .contentType(MediaType.APPLICATION_PDF)
////                        .body(new UrlResource(Paths.get("uploads/notfound.jpeg").toUri()));
//                return ResponseEntity.notFound().build();
//            }
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
    @GetMapping("/file/{link}")
        public ResponseEntity<?> viewfile(@PathVariable String link) {
        try {
            java.nio.file.Path path = Paths.get("uploads/" + link);
            UrlResource resource = new UrlResource(path.toUri());
            if (resource.exists()) {
                String encodedLink = URLEncoder.encode(link, StandardCharsets.UTF_8.toString());
                URI redirectUri = new URI("http://localhost:8080/api/doc/view/" + encodedLink);
                return ResponseEntity.status(HttpStatus.FOUND).location(redirectUri).build();
            } else {
                return ResponseEntity.status(HttpStatus.FOUND).location(new URI("/view/notfound.jpeg")).build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/view/{link}")
    public ResponseEntity<?> viewFilePage(@PathVariable String link) {
        try {
            String decodedLink = java.net.URLDecoder.decode(link, StandardCharsets.UTF_8.toString());
            java.nio.file.Path path = Paths.get("uploads/" + decodedLink);
            UrlResource resource = new UrlResource(path.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(resource);
            } else {
                java.nio.file.Path notFoundPath = Paths.get("uploads/notfound.jpeg");
                UrlResource notFoundResource = new UrlResource(notFoundPath.toUri());
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(notFoundResource);
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
