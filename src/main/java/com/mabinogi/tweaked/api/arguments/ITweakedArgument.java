package com.mabinogi.tweaked.api.arguments;

import com.mabinogi.tweaked.script.holders.ActionHolder;

public interface ITweakedArgument
{
	String parse(ActionHolder action, String start, String in);
}
