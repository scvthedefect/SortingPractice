package utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 待测试的排序算法标记
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Testable {
    
    /**
     * 测试数据的范围，默认为含正负元素
     * @return
     */
    TestDataType range() default TestDataType.NORMAL;

}
