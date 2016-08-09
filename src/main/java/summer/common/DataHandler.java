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



    public List<PriceRecord> getPrices(String startDateStr, String endDateStr ) {
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        List<PriceRecord> selectedPriceList =  new ArrayList<PriceRecord>();
        try {
            Date starDate = format.parse(startDateStr);
            Date endDate = format.parse(endDateStr);
            for(int i =0; i< priceList.size();i++){
                if((!priceList.get(i).getDate().after(endDate) && (!priceList.get(i).getDate().before(starDate)))){
                    selectedPriceList.add(priceList.get(i));
                }
            }
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return selectedPriceList;
    }

    public double computeAverage(String startDateStr, String endDateStr ){

        List<PriceRecord> selectedPriceList = getPrices( startDateStr,  endDateStr );
        double totalPrice = 0;
        for(PriceRecord record : selectedPriceList){
            totalPrice += record.getAdjClose();
        }
        return totalPrice/selectedPriceList.size();
    }

    public double computeMax(String startDateStr, String endDateStr ){
        List<PriceRecord> selectedPriceList = getPrices( startDateStr,  endDateStr );
        double maxPrice = selectedPriceList.get(0).getAdjClose();
        for(PriceRecord record : selectedPriceList){
            if (maxPrice<record.getAdjClose()){
                maxPrice = record.getAdjClose();
            }
        }
        return maxPrice;
    }

    public List<Double> computeMovingAverage (String startDateStr, String endDateStr, int windowSize)
    {
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = format.parse(startDateStr);
            endDate = format.parse(endDateStr);
        } catch(Exception ex){
            System.out.print(ex.getMessage());
        }

        List<Double> aveList =  new ArrayList<Double>();
        List<PriceRecord> dateSortedPrices  =  new ArrayList<PriceRecord>(priceList);
        // Date sort this new list.
        QuickSort.quickSortArrayList(dateSortedPrices, false ,true);
        for(PriceRecord record : dateSortedPrices){
            if ((!record.getDate().before(startDate)) && (!record.getDate().after(endDate))){
               aveList.add(getWindowAverage(startDate,windowSize,dateSortedPrices));
            }
            if (record.getDate().after(endDate))
                break;
        }
        return aveList;
    }

    public double getWindowAverage(Date startDate, int windowSize, List<PriceRecord> dateSortedPrices){

        for(PriceRecord record : dateSortedPrices){
            int priceNumber = 0;
            if (record.getDate().equals(startDate)){
                double totalPrice = 0;
                for (int i = dateSortedPrices.indexOf(record);i< i+windowSize;i++, priceNumber ++){
                    if (i > dateSortedPrices.size()-1)
                        break;
                    totalPrice += dateSortedPrices.get(i).getAdjClose();
                }
                return totalPrice/priceNumber;
            }
        }
        return 0;
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
        // If exist, delete the record
        boolean isExist = false;
        for (int i = 0; i< priceList.size();i++)
        {
            if(record.getDate().equals(priceList.get(i).getDate()))
            {
                isExist = true;
                priceList.remove(i);
            }
        }
        // Add this record into list.
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


    private  List<PriceRecord>  loadFile(String fileName) {
        List<PriceRecord> list = new ArrayList<PriceRecord>();
        // read the file
        try {
            Reader in = new FileReader(fileName);
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
