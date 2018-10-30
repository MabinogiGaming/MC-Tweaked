#jei

Tweaks involving the mod Just Enough Items.

## add
*tweak.jei.add([stack](/arguments/stack/) item);*

Adds an item to JEI, can be useful for items that are hidden by default.
```python
#adds diamond to jei
tweak.jei.add(<minecraft:diamond>);
```
<br>

## hide
*tweak.jei.hide([stack](/arguments/stack/) item);*

Hides an item from jei.
```python
#hides diamond in jei
tweak.jei.hide(<minecraft:diamond>);
```
<br>

---
## remove
*tweak.jei.remove([stack](/arguments/stack/) item);*

Similar to hide, except that it also removes recipes for this item as if calling [tweak.recipes.remove](/actions/recipes/#remove)
```python
#hides diamond in jei and removes all recipes for it
tweak.jei.remove(<minecraft:diamond>);
```