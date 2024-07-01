package com.kinnarastudio.commons.mekarisign.builder;

import com.kinnarastudio.commons.mekarisign.exception.BuildingException;

public interface Builder<T> {
    T build()  throws BuildingException;
}
