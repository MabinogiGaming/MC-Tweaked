#recipes

Tweaks involving crafting table recipes.

## remove

*tweak.recipes.remove([string](/arguments/string/) name)*  
*tweak.recipes.remove([stringList](/arguments/stringlist/) names)*

Disable the specific recipe that matches the provided name
```python
#remove the default crafting recipe for a torch
tweak.recipes.remove("minecraft:torch");
```
<br>

*tweak.recipes.remove([stack](/arguments/stack/) output)*  
*tweak.recipes.remove([stackList](/arguments/stacklist/) outputs)*

Disable all crafting recipes that match the provided output
```python
#remove all crafting recipes for torches
tweak.recipes.remove(<minecraft:torch>);
```
<br>

---
## shaped

*tweak.recipes.shaped([string](/arguments/string/) name, [stack](/arguments/stack/) output, [recipeShaped](/arguments/recipeshaped/) recipe)*

Adds a new shaped crafting recipe
```python
#adds a shaped recipe that turns 1 stone into diamond
tweak.recipes.shaped("cheat_diamond", <minecraft:diamond>, [[<minecraft:stone>]]);
```
<br>

---
## shapeless

*tweak.recipes.shapeless([string](/arguments/string/) name, [stack](/arguments/stack/) output, [recipeShapeless](/arguments/recipeshapeless/) recipe)*

Adds a new shapeless crafting recipe
```python
#adds a shaped recipe that turns 1 stone into diamond
tweak.recipes.shapeless("cheat_diamond", <minecraft:diamond>, [<minecraft:stone>]);
```