package com.paulek.core.basic.data;

import com.paulek.core.Core;

public interface Data {

    void load();

    void save(boolean ignoreNotChanged);

    //void validateLoadedData();

}
