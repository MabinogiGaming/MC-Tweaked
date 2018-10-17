package com.mabinogi.tweaked.script.objects;

import java.util.List;

import com.mabinogi.tweaked.script.objects.iface.IList;

public class ObjStringList implements IList {
	
	public List<String> list;
	
	public ObjStringList(List<String> list)
	{
		this.list = list;
	}

	@Override
	public List<?> getList()
	{
		return list;
	}

}
