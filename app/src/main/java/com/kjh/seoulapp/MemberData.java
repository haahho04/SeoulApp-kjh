package com.kjh.seoulapp;

import java.util.Map;

public class MemberData
{
    public String name = new String();
    public Map<String, String> stampMap;

    // test commit
=======
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
>>>>>>> 5508d2cd5a4f6ff75c7d7b632dd6be676e52b93a
}
