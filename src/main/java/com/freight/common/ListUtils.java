package com.freight.common;

import java.util.*;

public class ListUtils {
    /**
     * list 集合分组
     *
     * @param list    待分组集合
     * @param groupBy 分组Key算法
     * @param <K>     分组Key类型
     * @param <V>     行数据类型
     * @return 分组后的Map集合
     */
    public static <K, V> Map<K, List<V>> groupBy(List<V> list, GroupBy<K, V> groupBy) {
        return groupBy((Collection<V>) list, groupBy);
    }

    /**
     * list 集合分组
     *
     * @param list    待分组集合
     * @param groupBy 分组Key算法
     * @param <K>     分组Key类型
     * @param <V>     行数据类型
     * @return 分组后的Map集合
     */
    public static <K, V> Map<K, List<V>> groupBy(Collection<V> list, GroupBy<K, V> groupBy) {
        Map<K, List<V>> resultMap = new LinkedHashMap<K, List<V>>();

        for (V e : list) {

            K k = groupBy.groupBy(e);
            if (resultMap.containsKey(k)) {
                resultMap.get(k).add(e);
            } else {
                List<V> tmp = new LinkedList<V>();
                tmp.add(e);
                resultMap.put(k, tmp);
            }
        }
        return resultMap;
    }

    /**
     * List分组
     *
     * @param <K> 返回分组Key
     * @param <V> 分组行
     */
    public interface GroupBy<K, V> {
        K groupBy(V row);
    }

    public static List getPageContentByApi(List list, int pageNum, int pageSize) {
        List<String> subList = null;
        //总记录数
        int total = list.size();
        // 开始索引
        int fromIndex = (pageNum - 1) * pageSize;
        // 结束索引
        int toIndex = fromIndex + pageSize;
        // 如果结束索引大于集合的最大索引，那么规定结束索引=集合大小
        if (toIndex > total) {
            toIndex = total;
        }
        if (fromIndex <= total) {
            subList = list.subList(fromIndex, toIndex);
        }
        return subList;
    }

    public static Map<String, Object> getResponseMap(Object data, long total, int pageNum, int pageSize) {
        Map<String, Object> mapTemp = new HashMap<>();
        mapTemp.put("data", data);
        mapTemp.put("total", total);
        mapTemp.put("pageNum", pageNum);
        mapTemp.put("pageSize", pageSize);
        return mapTemp;
    }
}