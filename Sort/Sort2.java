package Sort;

public class Sort2 {

    public int majorityElement(int[] nums) {

        quickSort(nums);
        // System.out.println(Arrays.toString(nums));
        return nums[nums.length / 2];

    }

    private void bubbleSort(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            boolean flag = true;
            for (int j = 0; j < nums.length - i - 1; j++) {
                if (nums[j] > nums[j + 1]) {
                    int temp = nums[j + 1];
                    nums[j + 1] = nums[j];
                    nums[j] = temp;
                    flag = false;
                }
            }
            if (flag) {
                break;
            }
        }
    }

    private void selectSort(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[minIndex] > nums[j]) {
                    minIndex = j;
                }
            }
            int temp = nums[minIndex];
            nums[minIndex] = nums[i];
            nums[i] = temp;
        }
    }

    private void insertSort(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            int index = i - 1;
            int insertItem = nums[i];
            while (index >= 0 && insertItem < nums[index]) {
                nums[index + 1] = nums[index];
                index--;
            }
            nums[index + 1] = insertItem;
        }
    }

    private void shellSort(int[] nums) {
        int len = nums.length;
        int gap = len / 2;
        while (gap > 0) {
            for (int i = gap; i < len; i++) {
                int preIndex = i - gap;
                int insertItem = nums[i];
                while (preIndex >= 0 && insertItem < nums[preIndex]) {
                    nums[preIndex + gap] = nums[preIndex];
                    preIndex -= gap;
                }
                nums[preIndex + gap] = insertItem;
            }
            gap /= 2;
        }
    }

    private void heapSort(int[] nums) {
        buildHeap(nums);
        for (int i = nums.length - 1; i > 0; i--) {
            swap(nums, 0, i);
            sink(nums, 0, i);
        }
    }

    private void buildHeap(int[] nums) {
        for (int i = Math.max(0, ((int) nums.length / 2) - 1); i >= 0; i--) {
            sink(nums, i, nums.length);
        }
    }

    // max heap sink
    private void sink(int[] arr, int index, int heapLen) {
        int l = 2 * index + 1;
        int r = 2 * index + 2;

        int largest = index;
        if (r < heapLen && arr[r] > arr[largest]) {
            largest = r;
        }
        if (l < heapLen && arr[l] > arr[largest]) {
            largest = l;
        }

        if (largest != index) {
            swap(arr, largest, index);
            sink(arr, largest, heapLen);
        }
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private void mergeSort(int[] nums) {
        if (nums.length > 1) {
            merge(nums, 0, nums.length - 1);
        }
    }

    private void merge(int[] arr, int left, int right) {
        if (left >= right)
            return;
        int mid = (int) (left + right) / 2;
        merge(arr, left, mid);
        merge(arr, mid + 1, right);
        int[] result = new int[right - left + 1];
        int pt = 0;
        int p1 = left;
        int p2 = mid + 1;
        while (p1 <= mid && p2 <= right) {
            result[pt++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= mid) {
            result[pt++] = arr[p1++];
        }
        while (p2 <= right) {
            result[pt++] = arr[p2++];
        }

        for (int i = 0; i < pt; i++) {
            arr[left + i] = result[i];
        }
    }

    private void quickSort(int[] nums) {
        if (nums.length > 1) {
            partition(nums, 0, nums.length - 1);
        }
    }

    private void partition(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int pivot = arr[right];
        int pointer = left;
        // pointer 的左边都是比 pivot 小的元素
        for (int i = left; i < right; i++) {
            if (arr[i] < pivot) {
                int temp = arr[i];
                arr[i] = arr[pointer];
                arr[pointer] = temp;
                pointer++;
            }
        }
        int temp = arr[pointer];
        arr[pointer] = arr[right];
        arr[right] = temp;

        partition(arr, left, pointer - 1);
        partition(arr, pointer + 1, right);
    }
}