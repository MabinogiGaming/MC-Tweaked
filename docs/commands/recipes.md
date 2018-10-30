# recipes

`/tweaked recipes`

Prints a list of all [recipes](/arguments/recipe/) associated to the currently held item to the dump file.

```python
#while holding a torch in the main hand
/tweaked recipes

#in tweaked.dump
/tweaked recipes
	Found 2 recipes :
		shaped = "minecraft:torch", <minecraft:torch:0:4>, [[<minecraft:coal>][<stickWood>]]
		shaped = "minecraft:torch", <minecraft:torch:0:4>, [[<minecraft:coal:1>][<stickWood>]]
```