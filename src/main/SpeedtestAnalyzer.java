package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SpeedtestAnalyzer {
    public static void main(String[] args) throws FileNotFoundException {
        String directoryString = null;
        boolean parameters = false;
        if (args.length > 1) {
            if (args[0].equals("-d")) {
                if (!(args[1].isBlank())) {
                    directoryString = args[1];
                    parameters = true;
                }
            }
        }

        if (!parameters) {
            Scanner scnr = new Scanner(System.in);
            System.out.print("Enter a valid path: ");
            directoryString = scnr.nextLine();
        }

        assert directoryString != null;
        File directory = new File(directoryString);
        File[] folder = directory.listFiles();


        assert folder != null;
        ArrayList<Double> averageDown = new ArrayList<>();
        ArrayList<Double> averageUp = new ArrayList<>();

        for (int i = 0; i < folder.length; i++) {
            String fileName = String.valueOf(folder[i]);
            if (!fileName.contains(".txt")) {
                break;
            }
            Scanner in = new Scanner(folder[i]);
            while (in.hasNextLine()) {
                String line = in.nextLine();
                if (line.contains("Download")) {
                    double value = 0;
                    Scanner string = new Scanner(line);
                    while (string.hasNext()) {
                        if (string.hasNextDouble()) {
                            value = string.nextDouble();
                            averageDown.add(value);
                            break;
                        }
                        else {
                            string.next();
                        }
                    }
                }
                else if (line.contains("Upload")) {
                    double value = 0;
                    Scanner string = new Scanner(line);
                    while (string.hasNext()) {
                        if (string.hasNextDouble()) {
                            value = string.nextDouble();
                            averageUp.add(value);
                            break;
                        }
                        else {
                            string.next();
                        }
                    }
                }
            }
            in.close();
        }

        double averageDownSpeed = 0;
        double averageUpSpeed = 0;

        for (double elements : averageDown) {
            averageDownSpeed += elements;
        }
        averageDownSpeed /= averageDown.size();

        for (double elements : averageUp) {
            averageUpSpeed += elements;
        }
        averageUpSpeed /= averageDown.size();

        System.out.printf("Average Download Speed: %.2f Mbps\n", averageDownSpeed);
        System.out.printf("Average Upload Speed: %.2f Mbps\n", averageUpSpeed);

    }
}
