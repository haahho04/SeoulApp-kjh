package com.kjh.seoulapp.data;

import java.util.List;

public class MemberData
{
    public String name;
    public List<Integer> stampList;

    @Override
    public String toString()
    {
        String result = "name: " + name + " / stampList: ";
        final int size = stampList.size();
        for(int i=1;i<size;i++)
        {
            int stamp = stampList.get(i);
            result += stamp + ", ";
        }

        return result;
    }
}
