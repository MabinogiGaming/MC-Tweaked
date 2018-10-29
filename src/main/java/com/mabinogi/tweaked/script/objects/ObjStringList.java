package com.mabinogi.tweaked.script.objects;

import com.mabinogi.tweaked.api.objects.ITweakedList;

import java.util.List;

public class ObjStringList implements ITweakedList
{
	public List<String> list;
	
	public ObjStringList(List<String> list)
	{
		this.list = list;
	}

	@Override
	public List<String> getList()
	{
		return list;
	}

}
