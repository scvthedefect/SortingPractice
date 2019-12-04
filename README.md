# SortingPractice

一个排序算法的练习工具，自动生成测试数据并对算法的正确性进行验证。

手写算法后直接运行即可输出结果，免去组织数据和其他多余代码的麻烦。

## 目标

每隔一段时间进行手写算法练习，以唤醒对算法的记忆和熟练度。

## 如何使用

1. 在`main()`中调用`Verify.execute()`，算法编写完后直接运行`main()`即可在Console中看到输出
2. 对待测算法使用`@Testable`进行标记
3. 可指定测试数据类型`TestDataType`，如指定使用正整数测试的方式：`@Testable(range = TestDataType.POSITIVE)`

## 注意点

1. 推荐在`practice`包下创建新的类，以`Prefix+Date`的方式命名
2. 排序结果必须体现在输入序列`nums`中，即不处理返回值
3. `Parent`仅为提供最便捷的`swap()`（连较长的`Util.swap()`也不愿写），但可以不继承
4. 测试数据类型
    * `NORMAL` 为包含正负
    * `POSITIVE` 为仅含正整数
    * `NEGATIVE` 为仅含负数

## 例子

练习基数排序算法: radixSort()

```java
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

    // 其他排序算法

}
```

输出结果：

```
========== 基本结果 ==========
✔ [bubbleSort, bucketSort, countingSort, heapSort, insertionSort, mergeSort, quickSort, radixSort, selectionSort, shellSort]
✕ []
success: 10; fail: 0; total: 10;
```