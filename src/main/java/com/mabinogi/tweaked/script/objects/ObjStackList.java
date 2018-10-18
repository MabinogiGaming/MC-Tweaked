package com.mabinogi.tweaked.script.objects;

import java.util.List;

import com.mabinogi.tweaked.api.objects.IList;

public class ObjStackList implements IList {
	
	public List<ObjStack> list;
	
	public ObjStackList(List<ObjStack> list)
	{
		this.list = list;
	}

	@Override
	public List<?> getList()
	{
		return list;
	}

}
