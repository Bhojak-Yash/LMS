package com.example.erplms_similarity_detector;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    @Autowired
    private PythonService pythonService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFiles(@RequestParam("file1") MultipartFile file1,
                                              @RequestParam("file2") MultipartFile file2) throws IOException {
        // Extract text from the two uploaded PDF files
        String text1 = extractTextFromPDF(file1);
        String text2 = extractTextFromPDF(file2);

        // Call the Python service to get the similarity result
        double similarity = pythonService.getSimilarity(text1, text2);

        // Return the similarity percentage as a response
        return ResponseEntity.ok("The similarity between the documents is " + similarity + "%");
    }

    private String extractTextFromPDF(MultipartFile file) throws IOException {
        // Create a temporary file to store the uploaded PDF
        File tempFile = File.createTempFile("temp", ".pdf");
        file.transferTo(tempFile); // Save the uploaded file to the temporary file

        // Load the PDF document from the temporary file
        try (PDDocument document = PDDocument.load(tempFile)) {
            // Extract text using PDFTextStripper
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            return text;
        } finally {
            tempFile.delete(); // Clean up temporary file
        }
    }
}
