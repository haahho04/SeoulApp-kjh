package com.kjh.seoulapp;

import java.util.Map;

/**
 * Created by hojong on 2017-10-08.
 */

public class MemberData
{
    public String name;
    public Map<String, Integer> stampMap;

    @Override
    public String toString()
    {
        String result = "name: " + name + " / stampMap: ";
        for(String key : stampMap.keySet())
            result += stampMap.get(key) + ", ";

        return result;
    }
}
