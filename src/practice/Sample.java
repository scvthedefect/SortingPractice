package practice;

import java.util.ArrayList;
import java.util.List;

import utils.Parent;
import utils.TestDataType;
import utils.Testable;

public class Sample extends Parent {

    public static void main(String[] args) {
        execute(Sample.class);
        // 等价于
//        Verify.execute(Sample.class);
        // 或
//        Verify.execute(Sample.class, true);
    }
    
    @Testable(range = TestDataType.POSITIVE)
    public void radixSort(int[] nums) {
        if(nums == null || nums.length <= 1) {
            return;
        }
        int max = nums[0], digit = 1, i = 1;
        for(; i < nums.length; i++) {
            if(nums[i] > max) {
                max = nums[i];
            }
        }
        while(max >= 10) {
            digit++;
            max /= 10;
        }
        int[] count = new int[10];
        int[] tmp = new int[nums.length];
        int radix = 1;
        int k;
        for(int j = 0; j < digit; j++) {
            for(i = 0; i < count.length; i++) {
                count[i] = 0;
            }
            for(i = 0; i < nums.length; i++) {
                k = (nums[i] / radix) % 10;
                count[k]++;
            }
            for(i = 1; i < count.length; i++) {
                count[i] += count[i - 1];
            }
            for(i = nums.length - 1; i>= 0; i--) {
                k = (nums[i] / radix) % 10;
                tmp[--count[k]] = nums[i];
            }
            for(i = 0; i < nums.length; i++) {
                nums[i] = tmp[i];
            }
            radix *= 10;
        }
    }
    
    @Testable
    public void bucketSort(int[] nums) {
        if(nums == null || nums.length <= 1) {
            return;
        }
        int max = nums[0], min = max;
        for(int n : nums) {
            if(n > max) {
                max = n;
            }
            if(n < min) {
                min = n;
            }
        }
        int bucketNum = (max - min) / 10;
        List<List<Integer>> bucketList = new ArrayList<>(bucketNum);
        int i = 0, index;
        for(; i < bucketNum; i++) {
            bucketList.add(new ArrayList<>());
        }
        for(i = 0; i < nums.length; i++) {
            index = indexFor(nums[i], min, bucketNum);
            bucketList.get(index).add(nums[i]);
        }
        List<Integer> bucket;
        index = 0;
        int j;
        int[] tmp;
        for(i = 0; i < bucketNum; i++) {
            bucket = bucketList.get(i);
            if(bucket.isEmpty()) continue;
            
            tmp = countingSort(bucket);
            for(j = 0; j < tmp.length; j++) {
                nums[index++] = tmp[j];
            }
        }
    }
    
    private int[] countingSort(List<Integer> list) {
        int max = list.get(0), min = max;
        for(int n : list) {
            if(n > max) {
                max = n;
            }
            if(n < min) {
                min = n;
            }
        }
        int[] count = new int[max - min + 1];
        for(int n : list) {
            count[n - min]++;
        }
        int i;
        for(i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }
        int[] res = new int[list.size()];
        int num;
        for(i = list.size() - 1; i >= 0; i--) {
            num = list.get(i);
            res[--count[num - min]] = num;
        }
        return res;
    }
    
    private int indexFor(int num, int min, int bucketNum) {
        return (num - min) / bucketNum;
    }
    
    @Testable
    public void countingSort(int[] nums) {
        if(nums == null || nums.length <= 1) {
            return;
        }
        int max = nums[0], min = max, i = 1;
        for(; i < nums.length; i++) {
            if(nums[i] > max) {
                max = nums[i];
            }
            if(nums[i] < min) {
                min = nums[i];
            }
        }
        int[] count = new int[max - min + 1];
        for(i = 0; i < nums.length; i++) {
            count[nums[i] - min]++;
        }
        for(i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }
        int[] tmp = new int[nums.length];
        for(i = nums.length - 1; i >= 0; i--) {
            tmp[--count[nums[i] - min]] = nums[i];
        }
        for(i = 0; i < nums.length; i++) {
            nums[i] = tmp[i];
        }
    }
    
    @Testable
    public void heapSort(int[] nums) {
        if(nums == null || nums.length <= 1) {
            return;
        }
        int last = nums.length - 1;
        int begin = (last - 1) / 2;
        for(int i = begin; i >= 0; i--) {
            maxHeapify(nums, i, last);
        }
        for(int i = last; i > 0; i--) {
            swap(nums, 0, i);
            maxHeapify(nums, 0, i - 1);
        }
    }
    
    private void maxHeapify(int[] nums, int i, int last) {
        int left, right, max;
        boolean next;
        do {
            next = false;
            left = 2 * i + 1;
            if(left > last) return;
            
            right = left + 1;
            max = left;
            if(right <= last && nums[right] > nums[left]) max = right;
            if(nums[i] < nums[max]) {
                swap(nums, i, max);
                i = max;
                next = true;
            }
        } while(next);
    }
    
    @Testable
    public void shellSort(int[] nums) {
        if(nums == null || nums.length <= 1) {
            return;
        }
        int incr = nums.length / 2;
        int i, j, k;
        while(incr >= 1) {
            for(i = 0; i < nums.length; i++) {
                k = nums[i];
                j = i - incr;
                while(j >= 0 && k < nums[j]) {
                    nums[j + incr] = nums[j];
                    j -= incr;
                }
                nums[j + incr] = k;
            }
            incr /= 2;
        }
    }
    
    @Testable
    public void insertionSort(int[] nums) {
        if(nums == null || nums.length <= 1) {
            return;
        }
        int j, k;
        for(int i = 1; i < nums.length; i++) {
            k = nums[i];
            j = i - 1;
            while(j >= 0 && k < nums[j]) {
                nums[j + 1] = nums[j];
                j--;
            }
            nums[j + 1] = k;
        }
    }
    
    @Testable
    public void mergeSort(int[] nums) {
        if(nums == null || nums.length <= 1) {
            return;
        }
        int[] tmp = new int[nums.length];
        mSort(nums, 0, nums.length - 1, tmp);
    }
    
    private void mSort(int[] nums, int left, int right, int[] tmp) {
        if(nums.length <= 1 || left >= right) {
            return;
        }
        
        int mid = left + (right - left) / 2;
        mSort(nums, left, mid, tmp);
        mSort(nums, mid + 1, right, tmp);
        merge(nums, left, mid, right, tmp);
    }
    
    private void merge(int[] nums, int left, int mid, int right, int[] tmp) {
        int i = left, j = mid + 1, t = 0;
        while(i <= mid && j <= right) {
            if(nums[i] < nums[j]) {
                tmp[t++] = nums[i++];
            } else {
                tmp[t++] = nums[j++];
            }
        }
        while(i <= mid) {
            tmp[t++] = nums[i++];
        }
        while(j <= right) {
            tmp[t++] = nums[j++];
        }
        t = 0;
        while(left <= right) {
            nums[left++] = tmp[t++];
        }
    }
    
    @Testable
    public void quickSort(int[] nums) {
        if(nums == null || nums.length <= 1) {
            return;
        }
        qSort(nums, 0, nums.length - 1);
    }
    
    private void qSort(int[] nums, int left, int right) {
        if(nums.length <= 1 || left >= right) {
            return;
        }
        
        int pivot = nums[left + (right - left) / 2];
        int i = left, j = right;
        while(i <= j) {
            while(nums[i] < pivot) {
                i++;
            }
            while(nums[j] > pivot) {
                j--;
            }
            if(i < j) {
                swap(nums, i, j);
                i++;
                j--;
            } else if(i == j) {
                i++;
            }
        }
        qSort(nums, left, j);
        qSort(nums, i, right);
    }
    
    @Testable
    public void bubbleSort(int[] nums) {
        if(nums == null || nums.length <= 1) {
            return;
        }
        int len = nums.length;
        int i, j;
        boolean changed;
        do {
            len--;
            changed = false;
            for(i = 0; i < len; i++) {
                j = i + 1;
                if(nums[i] > nums[j]) {
                    swap(nums, i, j);
                    changed = true;
                }
            }
        } while(changed);
    }
    
    @Testable
    public void selectionSort(int[] nums) {
        if(nums == null || nums.length <= 1) {
            return;
        }
        
        int min, j;
        for(int i = 0; i < nums.length; i++) {
            min = i;
            for(j = i + 1; j < nums.length; j++) {
                if(nums[j] < nums[min]) {
                    min = j;
                }
            }
            if(i != min) {
                swap(nums, i, min);
            }
        }
    }
    
}
