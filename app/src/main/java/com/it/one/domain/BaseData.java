package com.it.one.domain;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 */

public class BaseData implements Serializable {

    /**
     * res : 0
     * data : ["1704","1703","1702","1701","1700","1699","1698","1697","1696","1692"]
     */

    public int res;
    public ArrayList<String> data;

    @Override
    public String toString() {
        return "BaseData{" +
                "res=" + res +
                ", data=" + data +
                '}';
    }

}
