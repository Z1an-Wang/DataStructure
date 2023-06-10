package Sort;
import java.util.*;

public class Sort {
    // Identifier for the Sort Method.
    public enum SortMethod {
        BUBBLE_SORT,
        SELECTION_SORT,
        INSERTION_SORT,
        SHELL_SORT,
        MERGE_SORT,
        QUICK_SORT,
        HEAP_SORT,
        COUNT_SORT,
        BUCKET_SORT,
        RADIX_SORT
    }

    public static void main(String[] args) {
        int[] original = { 11, 5, 7, 7, 5, 4, 3, 2, 1, 9, 6 };
        int[] result = new int[0];
        SortMethod sort = SortMethod.RADIX_SORT;

        switch (sort) {
            case BUBBLE_SORT:
                result = bubbleSort(original);
                break;
            case SELECTION_SORT:
                result = selectionSort(original);
                break;
            case INSERTION_SORT:
                result = insertionSort(original);
                break;
            case SHELL_SORT:
                result = shellSort(original);
                break;
            case MERGE_SORT:
                result = mergeSort(original);
                break;
            case QUICK_SORT:
                result = quickSort(original);
                break;
            case HEAP_SORT:
                result = heapSort(original);
                break;
            case COUNT_SORT:
                result = countSort(original);
                break;
            case BUCKET_SORT:
                result = bucketSort(original, 5);
                break;
            case RADIX_SORT:
                result = radixSort(original);
                break;
            default:
                break;
        }
        System.out.println(Arrays.toString(result));
    }


    public static int[] bubbleSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            // if true, that means the loop has not been swapped,
            // the sequence has been ordered.
            boolean flag = true;
            for (int j = 0; j < arr.length - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                    flag = false;
                }
            }
            if (flag) {
                break;
            }
        }
        return arr;
    }

    public static int[] selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int tmp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = tmp;
            }
        }
        return arr;
    }

    public static int[] insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int preIndex = i - 1;
            int current = arr[i];
            while (preIndex >= 0 && arr[preIndex] > current) {
                arr[preIndex + 1] = arr[preIndex];
                preIndex -= 1;
            }
            arr[preIndex + 1] = current;
        }
        return arr;
    }

    public static int[] shellSort(int[] arr) {
        int n = arr.length;
        int gap = n / 2;
        while (gap > 0) {
            for (int i = gap; i < n; i++) {
                int preIndex = i - gap;
                int current = arr[i];
                while (preIndex >= 0 && arr[preIndex] > current) {
                    arr[preIndex + gap] = arr[preIndex];
                    preIndex -= gap;
                }
                arr[preIndex + gap] = current;
            }
            gap /= 2;
        }
        return arr;
    }

    public static int[] mergeSort(int[] arr) {
        if (arr.length > 1) {
            merge(arr, 0, arr.length - 1);
        }
        return arr;
    }
    private static void merge(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = left + (right - left) / 2;
        merge(arr, left, mid);
        merge(arr, mid + 1, right);
        int[] result = new int[right - left + 1];
        int p1 = left, p2 = mid + 1, p = 0;
        while (p1 <= mid && p2 <= right) {
            result[p++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= mid) {
            result[p++] = arr[p1++];
        }
        while (p2 <= right) {
            result[p++] = arr[p2++];
        }

        int k = 0;
        int tempIndex = left;
        while (tempIndex <= right) {
            arr[tempIndex++] = result[k++];
        }
    }

    public static int[] quickSort(int[] arr) {
        if (arr.length > 1) {
            partition(arr, 0, arr.length - 1);
        }
        return arr;
    }
    private static void partition(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        // 取最后一个元素作为中心元素。
        int pivot = arr[right];
        int pointer = left;
        // 遍历数组中的所有元素，将比中心元素大的放在右边，比中心元素小的放在左边。
        for (int i = left; i < right; i++) {
            if (arr[i] <= pivot) {
                // 将比中心元素小的元素和指针指向的元素交换位置
                // 如果第一个元素比中心元素小，这里就是自己和自己交换位置，指针和索引都向下一位移动
                // 如果元素比中心元素大，索引向下移动，指针指向这个较大的元素，直到找到比中心元素小的元素，并交换位置，指针向下移动
                int temp = arr[i];
                arr[i] = arr[pointer];
                arr[pointer] = temp;
                pointer++;
            }
        }
        // 将中心元素和指针指向的元素交换位置
        int temp = arr[pointer];
        arr[pointer] = pivot;
        arr[right] = temp;

        partition(arr, left, pointer - 1);
        partition(arr, pointer + 1, right);
    }

    private static int heapLen = 0;
    public static int[] heapSort(int[] arr) {
        // index at the end of the heap
        heapLen = arr.length;
        // build MaxHeap
        buildMaxHeap(arr);
        for (int i = arr.length - 1; i > 0; i--) {
            // Move the top of the heap to the sorted area (tail).
            swap(arr, 0, i);
            // Reduce the unsorted area (Heap area).
            heapLen -= 1;
            // Rebuild the heap.
            sink(arr, 0);
        }
        return arr;
    }
    private static void sink(int[] arr, int index) {
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        int largest = index;

        if (right < heapLen && arr[right] > arr[largest]) {
            largest = right;
        }
        if (left < heapLen && arr[left] > arr[largest]) {
            largest = left;
        }
        if (largest != index) {
            swap(arr, largest, index);
            sink(arr, largest);
        }
    }
    private static void buildMaxHeap(int[] arr) {
        for (int i = Math.max(0, (arr.length / 2) - 1); i >= 0; i--) {
            sink(arr, i);
        }
    }
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static int[] countSort(int[] arr) {
        if (arr.length < 2) {
            return arr;
        }
        // 找出数组中的最大值和最小值
        int maxValue = arr[0];
        int minValue = arr[0];
        for (int i : arr) {
            if (i > maxValue) {
                maxValue = i;
            }
            if (i < minValue) {
                minValue = i;
            }
        }
        // 创建 bucket
        int[] bucket = new int[maxValue - minValue + 1];
        Arrays.fill(bucket, 0);
        // 遍历原始数组，统计每个数值的出现次数
        for (int i : arr) {
            bucket[i - minValue] += 1;
        }
        // 根据出现次数，对数组进行排序
        int sortedIndex = 0;
        for (int i = 0; i < bucket.length; i++) {
            while (bucket[i] > 0) {
                arr[sortedIndex++] = i + minValue;
                bucket[i]--;
            }
        }
        return arr;
    }

    // bucketSize：指一个 bucket 中可以容纳多少个不同的数值。
    public static int[] bucketSort(int[] arr, int bucketSize) {
        if (arr.length < 2 || bucketSize == 0) {
            return arr;
        }
        // 找出数组中的最大值和最小值
        int minValue = arr[0];
        int maxValue = arr[0];
        for (int i : arr) {
            if (i < minValue) {
                minValue = i;
            }
            if (i > maxValue) {
                maxValue = i;
            }
        }
        // 根据 bucketSize，计算出需要多少个桶，并创建。
        int bucketCount = (maxValue - minValue) / bucketSize + 1;
        ArrayList<Integer>[] buckets = new ArrayList[bucketCount];
        for (int i = 0; i < bucketCount; i++) {
            buckets[i] = new ArrayList<Integer>();
        }
        // 遍历原数组的数值，通过整除 bucketSize，获取对应的桶，并添加。
        for (int element : arr) {
            int index = (element - minValue) / bucketSize;
            buckets[index].add(element);
        }
        // 对于桶中，元素数量多于 1 的，进行从小到大排序。
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i].size() > 1) {
                buckets[i].sort(Comparator.naturalOrder());
            }
        }
        // 根据桶中的数据，对数组进行排序。
        int sortedIndex = 0;
        for (ArrayList<Integer> al : buckets) {
            for (int element : al) {
                arr[sortedIndex++] = element;
            }
        }
        return arr;
    }

    public static int[] radixSort(int[] arr) {
        if (arr.length < 2) {
            return arr;
        }
        // 找到数组中的最大值，和最小值。
        int maxValue = arr[0];
        for (int element : arr) {
            if (element > maxValue) {
                maxValue = element;
            }
        }
        // 根据最大值，计算所需的迭代次数。
        int N = 1;
        while (maxValue / 10 != 0) {
            maxValue = maxValue / 10;
            N += 1;
        }
        // 创建桶
        ArrayList<Integer>[] radix = new ArrayList[10];
        for (int i = 0; i < 10; i++) {
            radix[i] = new ArrayList<>();
        }
        // 基数排序
        for (int i = 0; i < N; i++) {
            for (int element : arr) {
                int index = (element / (int) Math.pow(10, i)) % 10;
                radix[index].add(element);
            }
            int sortedIndex = 0;
            for (ArrayList<Integer> ar : radix) {
                while (ar.size() != 0) {
                    arr[sortedIndex++] = ar.remove(0);
                }
            }
        }
        return arr;
    }
}
