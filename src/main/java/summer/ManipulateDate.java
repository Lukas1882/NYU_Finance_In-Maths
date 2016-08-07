package summer;

import summer.common.DataHandler;
import summer.common.PriceRecord;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;


public class ManipulateDate {
    private static String dir = System.getProperty("user.dir") + "/src/main/resources/files/";
    public static void manipulateDate() {

        // 1.Create an object of class DataHandler
        DataHandler dataHandler = new DataHandler();
        // 2. Load price data
        // quickSortArrayList(String fileName, String sortMethod, boolean isDescending, boolean isByDate)
        dataHandler.loadPriceData(dir + "prices.csv", "QuickSort", false, false);
        // 3. Correct Data
        dataHandler.correctPrices(dir + "corrections.csv");
        // 4. Select date period for appropriate price.
        List<PriceRecord> selectedDatePriceList = dataHandler.getPrices("08/15/2004","08/20/2004");
        writeAppropriatePrice(selectedDatePriceList);
        // 5. Get average price from selected date period.
        double averagePrice = dataHandler.computeAverage("08/15/2004","09/15/2004");
        writeAveragePrice(averagePrice);
        // 6. Get max price from selected date period.
        double maxPrice = dataHandler.computeMax("04/15/2004","06/15/2004");
        writeMaxPrice(maxPrice);



    }

    public static void writeAppropriatePrice( List<PriceRecord> list){
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String eol = System.getProperty("line.separator");
        StringBuilder stringBuilder = new StringBuilder("The Prices of SPY between 08/15/2004 and 08/20/2004 are: "+eol);
        for (int i=0;i< list.size();i++){
            stringBuilder.append(df.format(list.get(i).getDate()) + " : "+ list.get(i).getAdjClose() + eol);
        }
        stringBuilder.append(eol);
        File file = new File(dir + "results.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintWriter writer = new PrintWriter(file);
            writer.print(stringBuilder.toString());
            writer.close();
        } catch(Exception ex){

            System.out.print(ex.getMessage());
        }
    }

    public static void writeAveragePrice( double averagePrice) {
        String eol = System.getProperty("line.separator");
        StringBuilder stringBuilder = new StringBuilder("The Average Price of SPY between 08/15/2004 and 09/15/2004 is: "+ averagePrice + eol + eol);
        try {
            Files.write(Paths.get(dir + "results.txt"), stringBuilder.toString().getBytes(), StandardOpenOption.APPEND);

        } catch(Exception ex){
            System.out.print(ex.getMessage());
        }
    }

    public static void writeMaxPrice( double maxPrice) {
        String eol = System.getProperty("line.separator");
        StringBuilder stringBuilder = new StringBuilder("The Maximum Price of SPY between 04/15/2004 and 06/15/2004 is: "+ maxPrice + eol + eol);
        try {
            Files.write(Paths.get(dir + "results.txt"), stringBuilder.toString().getBytes(), StandardOpenOption.APPEND);

        } catch(Exception ex){
            System.out.print(ex.getMessage());
        }
    }





}
