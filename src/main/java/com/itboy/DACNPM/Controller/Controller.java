package com.itboy.DACNPM.Controller;


import com.itboy.DACNPM.Enity.Document;
import com.itboy.DACNPM.Service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/doc")
public class Controller {
    //http://localhost:8080/api/doc
    @Autowired
    private DocumentService documentService;
    @Value("${file.upload-dir}") // Đường dẫn thư mục để lưu trữ tệp
    private String uploadDir;

    @GetMapping("/getAll")
    public List<Document> getAllProduct(){
        return documentService.getALlDocument();
    }
    @PostMapping("/create")
    public String createDocument(@RequestBody Document document){
        documentService.createDocument(document);
        return "Complete";

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
    @DeleteMapping("/delete/{id}")
    public String deleteOrder(@PathVariable("id") int id){
        documentService.deleteDocument(id);
        return "Deleted";
    }
}
