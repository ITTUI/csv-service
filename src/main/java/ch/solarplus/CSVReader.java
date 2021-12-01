package ch.solarplus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {

    public static final String DELIMETER = ",";

    public static void main(String[] args) {

        // csv file to read
        String csvFile = "C:\\Users\\ivan.tujkic\\Documents\\Summaries\\VC Code\\grades.csv";
        CSVReader.readCSV(csvFile);

    }

    public static void readCSV(String csvFile) {

        try {
            File file = new File(csvFile);
            FileReader fileReader = new FileReader(file);
            BufferedReader buffReader = new BufferedReader(fileReader);
            String line = "";
            String[] tempArr;
            
            while ((line = buffReader.readLine()) != null) {

                // splitting all data by ,
                tempArr = line.split(DELIMETER);
                for (String tempStr : tempArr) {

                    // print and replace all "" with nothing
                    System.out.print(tempStr.replaceAll("\"", ""));
                }
                System.out.println();
            }

            buffReader.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

}
