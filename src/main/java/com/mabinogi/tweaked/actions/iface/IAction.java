package com.mabinogi.tweaked.actions.iface;

import com.mabinogi.tweaked.script.holders.ActionHolder;

public interface IAction {
	
	public boolean store(String methodName, ActionHolder action);

}
