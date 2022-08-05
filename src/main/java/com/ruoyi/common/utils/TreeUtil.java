package com.ruoyi.common.utils;

import com.ruoyi.project.model.domain.ModelCategory;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author dengsd
 * @date 2022/7/29 16:49
 */
public class TreeUtil {
    public static  <T>  List<T> toTree(List<T> data,Integer rootId, Function<T, Integer> parentFun, Function<T, Integer> idFun,Function<T, Integer> orderFn, BiConsumer<T, List<T>> childCs) {
        if(data==null || data.isEmpty()) return data;
        Map<Integer, List<T>> listMap = data.parallelStream()
                .sorted(Comparator.comparing(orderFn))
                .collect(Collectors.groupingBy(parentFun, TreeMap::new,Collectors.toList()));
        listMap.forEach((k,v)-> v.parallelStream().sorted(Comparator.comparing(orderFn)));
        List<T> parentList = listMap.remove(rootId);
        parentList.parallelStream().forEach(f -> {
            List<T> childs = listMap.get( idFun.apply(f));
            if (StringUtils.isNotEmpty(childs)) {
                childCs.accept(f, childs);
            }

        });
        return parentList;
    }

}
