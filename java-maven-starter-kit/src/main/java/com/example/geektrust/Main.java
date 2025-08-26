package com.example.geektrust;

import com.example.geektrust.service.MetroService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please provide input file path");
            return;
        }

        String inputFile = args[0];
        MetroService metroService = new MetroService();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    metroService.processCommand(line.trim());
                }
            }
        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println("Error Occurred "+e);
        }

    }
}
