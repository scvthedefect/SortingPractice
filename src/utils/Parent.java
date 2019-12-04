package utils;

public class Parent {
    
    /**
     * 交换数组中索引分别为i和j的两个值，若i和j非法会抛出 @ArrayIndexOutOfBoundsException
     * @param nums
     * @param i
     * @param j
     */
    public static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
    
    public static void execute(Class<?> clz) {
        Verify.execute(clz);
    }
    
}
