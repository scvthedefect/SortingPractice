package utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Verify {
    
    public static void execute(Class<?> clz) {
        execute(clz, false);
    }
    
    public static void execute(Class<?> clz, boolean fullReport) {
        Method[] ms = clz.getMethods();
        if(ms == null || ms.length == 0) {
            System.out.println("请使用 @Testable 对待测方法进行标记。");
            return;
        }
        
        List<String> successList = new ArrayList<>();
        List<String> failList = new ArrayList<>(); // 排序失败的方法
        List<String> tmpList;
        Map<String, String> failMap = fullReport ? new HashMap<>() : null;
        
        TestDataType dataType;
        int[] raw;
        int[] sorted;
        int len;
        
        // 临时变量
        int i;
        boolean matched;
        int[] nums;
        try {
            Object obj = clz.newInstance();
            for(Method m : ms) {
                // 忽略无 @Testable 声明的方法
                Testable a = m.getAnnotation(Testable.class);
                if(a == null) {
                    continue;
                }
                
                dataType = a.range();
                raw = dataType.getRaw();
                sorted = dataType.getSorted();
                len = dataType.getLen();
                
                nums = Arrays.copyOf(raw, len);
                m.invoke(obj, nums);
                matched = true;
                for(i = 0; i < len; i++) {
                    if(nums[i] != sorted[i]) {
                        matched = false;
                        break;
                    }
                }
                
                tmpList = matched ? successList : failList;
                tmpList.add(m.getName());
                if(!matched && fullReport) {
                    failMap.put(m.getName(), Arrays.toString(nums));
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
            return;
        }
        
        // 输出报告
        Collections.sort(successList);
        Collections.sort(failList);
        System.out.println("========== 基本结果 ==========");
        System.out.println(String.format("✔ %s", successList.toString()));
        System.out.println(String.format("✕ %s", failList.toString()));
        System.out.println(String.format("success: %d; fail: %d; total: %d;", 
                successList.size(), failList.size(), (successList.size() + failList.size())));
        
        if(fullReport) {
            System.out.println("\r\n========== 测试数据 ==========");
            for(TestDataType type : TestDataType.values()) {
                System.out.println(type.name());
                System.out.println(String.format("<待排序> %s", Arrays.toString(type.getRaw())));
                System.out.println(String.format("<排序后> %s", Arrays.toString(type.getSorted())));
            }
            
            if(failMap == null || failMap.isEmpty()) {
                return;
            }
            System.out.println("\r\n========== 有误的结果 ==========");
            for(Map.Entry<String, String> entry : failMap.entrySet()) {
                System.out.println(String.format("<%s>:\r\n%s", entry.getKey(), entry.getValue()));
            }
        }
    }
}
