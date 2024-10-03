package com.example.erplms_similarity_detector;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class PythonService {

    public double getSimilarity(String text1, String text2) throws IOException {
        // Path to the Python script
        File scriptFile = new File("text_similarity.py");
        String scriptPath = scriptFile.getAbsolutePath();

        // Using the ProcessBuilder to run the Python script
        ProcessBuilder processBuilder = new ProcessBuilder("python", scriptPath, text1, text2);
        processBuilder.redirectErrorStream(true); // Redirect error stream to output

        Process process = processBuilder.start();
        StringBuilder output = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Assuming your Python script outputs the similarity as a single line
        return Double.parseDouble(output.toString().trim());
    }
}
