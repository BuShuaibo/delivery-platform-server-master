package com.neu.deliveryPlatform.annotion;

import com.neu.deliveryPlatform.properties.ExcelColumnTransferType;

import java.lang.annotation.*;

/**
 * @Author asiawu
 * @Date 2023-05-21 22:44
 * @Description:
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelColum {
    ExcelColumnTransferType columnTransferType() default ExcelColumnTransferType.DIRECT;
}
