package com.neu.deliveryPlatform.util;

import com.neu.deliveryPlatform.annotion.ExcelColum;
import com.neu.deliveryPlatform.config.exception.BizException;
import com.neu.deliveryPlatform.entity.Commodity;
import com.neu.deliveryPlatform.mapper.CommodityMapper;
import com.neu.deliveryPlatform.properties.ErrorCode;
import com.neu.deliveryPlatform.properties.ExcelColumnProperties;
import com.neu.deliveryPlatform.properties.ExcelColumnTransferType;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @Author asiawu
 * @Date 2023-04-17 17:04
 * @Description:
 */
public class ObjectUtils {
    /**
     * 把不为空的属性拷贝给target
     */
    public static void copyNonNullProperties(Object source, Object target) throws IllegalAccessException {
        for (Field sourceField : source.getClass().getDeclaredFields()) {
            sourceField.setAccessible(true);
            Object sourceValue = sourceField.get(source);
            if (sourceValue != null) {
                Field targetField = getField(target.getClass(), sourceField.getName());
                if (targetField != null) {
                    targetField.setAccessible(true);
                    targetField.set(target, sourceValue);
                }
            }
        }
    }

    /**
     * target中对应的属性为空时才拷贝该属性
     */
    public static void copyToNullProperties(Object source, Object target) throws IllegalAccessException {
        for (Field sourceField : source.getClass().getDeclaredFields()) {
            sourceField.setAccessible(true);
            Object sourceValue = sourceField.get(source);
            if (sourceValue != null) {
                Field targetField = getField(target.getClass(), sourceField.getName());
                if (targetField != null) {
                    targetField.setAccessible(true);
                    if (targetField.get(target) == null) {
                        targetField.set(target, sourceValue);
                    }
                }
            }
        }
    }

    /**
     * 根据配置文件中字段的对应关系，把一行excel数据转化为一个commodity对象
     */
    public static Commodity transferExcelLineToCommodity(Map<String, Object> line, ExcelColumnProperties excelColumnProperties, CommodityMapper commodityMapper) {
        Commodity commodity = new Commodity();
        commodity.setIsAvailable(1);
        Field[] fields = Commodity.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(ExcelColum.class)) {
                ExcelColumnTransferType excelColumnTransferType = field.getDeclaredAnnotation(ExcelColum.class).columnTransferType();
                try {
                    Field declaredField = ExcelColumnProperties.class.getDeclaredField(field.getName());
                    declaredField.setAccessible(true);
                    String columnName = (String) declaredField.get(excelColumnProperties);
                    String value = (String) line.get(columnName);
                    switch (excelColumnTransferType) {
                        case SUBSTRING:
                            value = value.substring(value.indexOf('=') + 1);
                            break;
                        case QUERY_FROM_DB:
                            value = String.valueOf(commodityMapper.getCategoryIdByName(value));
                            if ("null".equals(value)) {
                                value = String.valueOf(commodityMapper.getCategoryIdByName("其他"));
                            }
                            break;
                        default:
                    }
                    Class<?> fieldType = field.getType();
                    if (fieldType.equals(Long.class)) {
                        field.set(commodity, Long.parseLong(value));
                    } else if (fieldType.equals(Double.class)) {
                        field.set(commodity, Double.parseDouble(value));
                    } else if (fieldType.equals(Integer.class)) {
                        field.set(commodity, Integer.parseInt(value));
                    } else {
                        field.set(commodity, value);
                    }
                } catch (NoSuchFieldException e) {
                    throw new BizException(ErrorCode.FILE_ANALYZE_ERROR);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return commodity;
    }

    private static Field getField(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }
}
