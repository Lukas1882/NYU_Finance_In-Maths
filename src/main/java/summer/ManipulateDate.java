package summer;

public class ManipulateDate {
    public static void manipulateDate() {
        // quickSortArrayList(List<PriceRecord> list, boolean isByDate, boolean isDescending)
        summer.common.DataHandler.loadPriceData("prices.csv", "QuickSort", true, false);
    }
}
