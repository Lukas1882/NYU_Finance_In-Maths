package summer.common;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.io.Reader;
import java.text.DateFormat;
import java.util.*;
import java.util.List;
import java.text.SimpleDateFormat;


public class DataHandler {
    static List<PriceRecord> priceList = new ArrayList<PriceRecord>();
    public static void loadPriceData(String fileName, String sortMethod, boolean isDescending , boolean isByDate)
    {
        try {
            // read the file
            final String dir = System.getProperty("user.dir") + "/src/main/resources/files/";
            Reader in = new FileReader(dir + fileName);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(in);
            DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            // save the data into list
            for (CSVRecord record : records)
            {
                PriceRecord recordObj = new PriceRecord();
                recordObj.setDate((Date)format.parse(record.get("Date")));
                recordObj.setOpen(Double.parseDouble(record.get("Open")));
                recordObj.setHigh(Double.parseDouble(record.get("High")));
                recordObj.setLow(Double.parseDouble(record.get("Low")));
                recordObj.setClose(Double.parseDouble(record.get("Close")));
                recordObj.setAdjClose(Double.parseDouble(record.get("Adj Close")));
                recordObj.setVolume(Integer.parseInt(record.get("Volume")));
                priceList.add(recordObj);
            }
            if (sortMethod.toLowerCase().equals("quicksort"))
            QuickSort.quickSortArrayList(priceList, isDescending,isByDate);

        }
        catch (Exception ex)
        {
            String erroInfo = ex.getMessage();
        }

    }

}
