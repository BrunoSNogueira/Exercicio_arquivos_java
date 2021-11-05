package main;
import entities.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        List<Product> list = new ArrayList<>();

        System.out.println("Enter file path:");
        String fileLocation = scan.nextLine();
        File sourceFile = new File(fileLocation);
        String folderLocation = sourceFile.getParent();

        // success used to verify if directory was successfully created
        boolean success = new File(folderLocation + "\\out").mkdir();

        String summaryFile = folderLocation + "\\out\\summary.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(folderLocation))){

            String itemCsv = br.readLine();

            while(itemCsv != null){

                String[] fields = itemCsv.split(",");
                String name = fields[0];

                // as scanner is returning a string, we need to parse that string into double to save it
                double price = Double.parseDouble(fields[1]);
                int quantity = Integer.parseInt(fields[2]);

                list.add(new Product(name, price, quantity));

                itemCsv = br.readLine();
            }

            try(BufferedWriter bw = new BufferedWriter(new FileWriter(summaryFile))){

                for (Product item : list){
                    bw.write(item.getName() + "," + String.format("%.2f", item.total()));
                    bw.newLine();
                }

                System.out.println(summaryFile + " created!");
            }
            catch(IOException e){
                System.out.println("Error writing file: " + e.getMessage());
            }
        }
        catch(IOException e){
            System.out.println("Error reading file: " + e.getMessage());
        }
        scan.close();
    }
}
