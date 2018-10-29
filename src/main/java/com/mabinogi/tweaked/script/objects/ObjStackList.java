package com.mabinogi.tweaked.script.objects;

import com.mabinogi.tweaked.api.objects.ITweakedList;

import java.util.List;

public class ObjStackList implements ITweakedList
{
	public List<ObjStack> list;
	
	public ObjStackList(List<ObjStack> list)
	{
		this.list = list;
	}

	@Override
	public List<ObjStack> getList()
	{
		return list;
	}

}
