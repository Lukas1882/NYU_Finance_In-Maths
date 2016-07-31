package summer.common;

import java.util.List;

public class BubbleSort {
    public static void bubbleSortArrayList(List<PriceRecord> list, boolean isByDate, boolean isDescending) {
        if (isByDate)
        {
            for(int i=0; i < list.size()-1; i++){
                for(int j=1; j < list.size()-i; j++){
                    if(list.get(j-1).getDate().after(list.get(j).getDate())){
                        swap(list,j-1,j);
                    }
                }
            }
        } else {
            for(int i=0; i < list.size()-1; i++){
                for(int j=1; j < list.size()-i; j++){
                    if(list.get(j-1).getAdjClose() > list.get(j).getAdjClose()){
                       swap(list,j-1,j);
                    }
                }
            }
        }

        if (isDescending) {
            swapOrder(list);
        }
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
