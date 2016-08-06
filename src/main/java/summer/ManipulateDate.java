package summer;

import summer.common.DataHandler;

public class ManipulateDate {
    public static void manipulateDate() {

        DataHandler dataHandler = new DataHandler();
        // quickSortArrayList(String fileName, String sortMethod, boolean isDescending, boolean isByDate)
        dataHandler.loadPriceData("prices.csv", "QuickSort", false, false);

        dataHandler.correctPrices("corrections.csv");
    }
}
