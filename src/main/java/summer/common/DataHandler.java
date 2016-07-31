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
    // static object holds the data from prices.csv
    private static List<PriceRecord> priceList = new ArrayList<PriceRecord>();
    private static boolean isDescending, isByDate;

    public void loadPriceData(String fileName, String sortMethod, boolean isDescending, boolean isByDate) {
        DataHandler.isByDate =   isByDate;
        DataHandler.isDescending = isDescending;
        priceList = loadFile(fileName);
        if (sortMethod.toLowerCase().equals("quicksort")) {
            QuickSort.quickSortArrayList(priceList, isDescending, isByDate);
        } else {
            BubbleSort.bubbleSortArrayList(priceList, isDescending, isByDate);
        }



    }



    public void getPrices() {

    }

    public void correctPrices(String fileName){
        List<PriceRecord> correctionList = loadFile(fileName);
        for(PriceRecord correction : correctionList){
            insertPrice(correction);
        }
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        for (int i = 0; i < priceList.size(); i++) {
            System.out.println(df.format(priceList.get(i).getDate()) + "  |||  " + priceList.get(i).getAdjClose() + "\n");
        }

    }

    public void insertPrice(PriceRecord record){
        // If exist, update the record
        boolean isExist = false;
        for (int i = 0; i< priceList.size();i++)
        {
            if(record.getDate().equals(priceList.get(i).getDate()))
            {
                isExist = true;
                priceList.set(i, record);
            }
        }
        // If cannot find this record, add this record into list.
        if (!isExist)
        {
            if (isByDate){
                if (isDescending)
                {
                    for(int i = 0; i < priceList.size();i++)
                    {
                        if (record.getDate().after(priceList.get(i).getDate())){
                          priceList.add(i,record);
                            break;
                        }
                    }
                } else {
                    for(int i = 0; i < priceList.size();i++)
                    {
                        if (record.getDate().before(priceList.get(i).getDate())){
                            priceList.add(i,record);
                            break;
                        }
                    }
            }
            } else {
                if (isDescending)
                {
                    for(int i = 0; i < priceList.size();i++)
                    {
                        if (record.getAdjClose() > priceList.get(i).getAdjClose()){
                            priceList.add(i,record);
                            break;
                        }
                    }
                } else {
                    for(int i = 0; i < priceList.size();i++)
                    {
                        if (record.getAdjClose() < priceList.get(i).getAdjClose()){
                            priceList.add(i,record);
                            break;
                        }
                    }
                }
            }

        }
    }


    private  List<PriceRecord>  loadFile(String fileName) {
        List<PriceRecord> list = new ArrayList<PriceRecord>();
        // read the file
        final String dir = System.getProperty("user.dir") + "/src/main/resources/files/";
        try {
            Reader in = new FileReader(dir + fileName);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(in);
            DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            // save the data into list
            for (CSVRecord record : records) {
                PriceRecord recordObj = new PriceRecord();
                recordObj.setDate(format.parse(record.get("Date")));
                recordObj.setOpen(Double.parseDouble(record.get("Open")));
                recordObj.setHigh(Double.parseDouble(record.get("High")));
                recordObj.setLow(Double.parseDouble(record.get("Low")));
                recordObj.setClose(Double.parseDouble(record.get("Close")));
                recordObj.setAdjClose(Double.parseDouble(record.get("Adj Close")));
                recordObj.setVolume(Integer.parseInt(record.get("Volume")));
                list.add(recordObj);
            }
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return list;
    }
}
