package com.kjh.seoulapp;

import java.util.Map;

public class MemberData
{
    private String name;
    private Map<String, Integer> stampMap;

    @Override
    public String toString()
    {
        String result = "name: " + name + " / stampMap: ";
        for(String key : stampMap.keySet())
            result += stampMap.get(key) + ", ";

        return result;
    }
}
