package com.itboy.DACNPM.Service;

import com.itboy.DACNPM.DTO.DocumentDTO;
import com.itboy.DACNPM.DTO.DocumentVersionDTO;
import com.itboy.DACNPM.Enity.Document;
import com.itboy.DACNPM.Enity.DocumentVersion;
import com.itboy.DACNPM.Enity.User;
import com.itboy.DACNPM.Repository.DocumentRepository;
import com.itboy.DACNPM.Repository.DocumentVersionRepository;
import com.itboy.DACNPM.Repository.UserRepository;
import com.itboy.DACNPM.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    public final UserRepository userRepository;
    public final DocumentVersionRepository documentVersionRepository;

    public List<Document> getALlDocument(){
        return documentRepository.findAll();
    }
    public Optional<Document> findById(Long id){
        return documentRepository.findById(id);
    }

    public Document createDocument(DocumentDTO document){
        Optional<User> existingUser = userRepository.findById(document.getCreatedBy());
        List<DocumentVersion>documentVersion= document.getVersions() != null ?
                document.getVersions().stream()
                                .map(this::toDocDetail)
                                .toList() :
                        Collections.emptyList();
        Document newdoc= Document.builder()
                .issuingAuthority(document.getIssuingAuthority())
                .symbolNumber(document.getSymbolNumber())
                .field(document.getField())
                .describeOfDoc(document.getDescribeOfDoc())
                .versions(documentVersion)
                .createdBy(existingUser.get())
                .build();
        return documentRepository.save(newdoc);
    }
    private DocumentVersion toDocDetail(DocumentVersionDTO detailDTO){
        Optional<User> existingUser = userRepository.findById(detailDTO.getCreatedBy());

        return DocumentVersion.builder()
                .versionNumber(detailDTO.getVersionNumber())
                .createdBy(existingUser.get())
                .build();
    }

    public Document addVersionDocument(Long idDoc,DocumentVersionDTO document){
        Optional<User> existingUser = userRepository.findById(document.getCreatedBy());
        Optional<Document> doc = documentRepository.findById(idDoc);
        List<DocumentVersion>documentVersion=   doc.get().getVersions();
        documentVersion.add(toDocDetail(document));
        doc.get().setVersions(documentVersion);
        return documentRepository.save(doc.get());
    }
    public DocumentVersion createDocFile(Long docvsId,DocumentVersionDTO documentVersionDTO) {

        DocumentVersion documentVersion=  documentVersionRepository.findById(docvsId).orElseThrow(()-> new RuntimeException("Doc not found"));
        documentVersion.setFilePath(documentVersionDTO.getFilePath());
        return documentVersionRepository.save(documentVersion);
    }
    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);

    }

    public List<Document> findDocbySymbolNumber(String symbolNumber) {
        return documentRepository.findBySymbolNumber(symbolNumber);
    }

    public List<Document> findDocumentsByYear(int year) {
        return documentRepository.findDocumentsByYear(year);
    }

    public List<Document> findDocumentsByField(String field) {
        return documentRepository.findByField(field);

    }

    public DocumentVersion getDocById(Long docId) {
        return documentVersionRepository.findById(docId).orElseThrow(()-> new RuntimeException("Doc not found"));
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

}
