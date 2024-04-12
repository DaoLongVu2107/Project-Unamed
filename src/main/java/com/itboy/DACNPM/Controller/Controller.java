package com.itboy.DACNPM.Controller;


import com.itboy.DACNPM.Enity.Document;
import com.itboy.DACNPM.Service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/doc")
public class Controller {
    //http://localhost:8080/api/doc
    @Autowired
    private DocumentService documentService;
    @GetMapping("/getAll")
    public List<Document> getAllProduct(){
        return documentService.getALlDocument();
    }
    @PostMapping("/create")
    public String createDocument(Document document){
        documentService.createDocument(document);
        return "Complete";
    }
}
