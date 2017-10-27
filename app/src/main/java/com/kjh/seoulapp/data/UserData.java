package com.kjh.seoulapp.data;

import java.util.ArrayList;
import java.util.List;

public class UserData
{
    public String name;
    public List<Integer> stampList;

    public UserData() {}
	public UserData(String _name)
    {
        name = _name;
        stampList = new ArrayList<>();
		for(int i=0;i<5;i++)
			stampList.add(0);
    }

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
