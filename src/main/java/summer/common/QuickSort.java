package summer.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class QuickSort {
    public static void quickSortArrayList(List<PriceRecord> list,  boolean isDescending,boolean isByDate) {
        quickSort(list, 0, list.size() - 1, isByDate);
        // If it is descending, swap the list.
        if (isDescending) {
            swapOrder(list);
        }
    }


    private static void quickSort(List<PriceRecord> list, int left, int right, boolean isByDate) {
        if (left < right) {
            int pivot = partition(list, left, right, isByDate);
            quickSort(list, left, pivot - 1, isByDate);
            quickSort(list, pivot + 1, right, isByDate);
        }
    }

    private static int partition(List<PriceRecord> list, int left, int right, boolean isByDate) {
        if (isByDate) {
            Date pivotKey = list.get(left).getDate();
            while (left < right) {
                while (left < right && list.get(right).getDate().after(pivotKey))
                    --right;
                swap(list, left, right);
                while (left < right && list.get(left).getDate().before(pivotKey))
                    ++left;
                swap(list, left, right);
            }
        } else {
            double pivotkey = list.get(left).getAdjClose();
            while (left < right) {
                while (left < right && list.get(right).getAdjClose() >= pivotkey)
                    --right;
                swap(list, left, right);
                while (left < right && list.get(left).getAdjClose() <= pivotkey)
                    ++left;
                swap(list, left, right);
            }
        }
        return left;

    }

    private static void swap(List<PriceRecord> list, int left, int right) {
        PriceRecord tempRecord;
        tempRecord = list.get(right);
        list.set(right, list.get(left));
        list.set(left, tempRecord);
    }

    private static void swapOrder(List<PriceRecord> list)
    {
        int left = 0;
        int right = list.size() - 1;
        while (left < right) {
            swap(list, left, right);
            left++;
            right--;
        }
    }
}
