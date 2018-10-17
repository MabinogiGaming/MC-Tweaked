package com.mabinogi.tweaked.script.arguments.iface;

import com.mabinogi.tweaked.script.holders.ActionHolder;

public interface IArgument {

	public String parse(ActionHolder action, String start, String in);

}
