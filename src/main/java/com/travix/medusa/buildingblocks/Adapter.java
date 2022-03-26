package com.travix.medusa.buildingblocks;

import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

public interface Adapter<S, T> {

    T adapt(S source);

    default List<T> adapt(List<S> source) {
        if (CollectionUtils.isEmpty(source)) {
            return Collections.emptyList();
        }

        return source.stream().map(this::adapt).toList();
    }

    record AdapterResult<T>() {

    }

}