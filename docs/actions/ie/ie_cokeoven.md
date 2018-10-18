#ie.cokeoven

Tweaks involving the Coke Oven from Immersive Engineering.

## add
*tweak.ie.cokeoven.add([Stack](/arguments/stack) output, [Stack](/arguments/stack) input, [Integer](/arguments/integer) time, [Stack](/arguments/integer) creosote);*  
*tweak.ie.cokeoven.add([Stack](/arguments/stack) output, [Dict](/arguments/dict) input, [Integer](/arguments/integer) time, [Stack](/arguments/integer) creosote);*

Adds a new recipe to the coke oven.
```python
#adds recipe where stone turns into diamonds and 200mb of creosote, taking 1000 ticks
tweak.ie.cokeoven.add(<minecraft:diamond>, <minecraft:stone>, 1000, 200);
```
<br>

---
## remove
*tweak.ie.cokeoven.remove([Stack](/arguments/stack) output);*  
*tweak.ie.cokeoven.remove([StackList](/arguments/stacklist) outputs);*  
*tweak.ie.cokeoven.remove([All](/arguments/all) all);*

Removes all recipes from the coke oven that have the provided output
```python
#removes the recipe to make charcoal from the coke oven
tweak.ie.cokeoven.remove(<minecraft:coal:1>);

#removes all recipes from the coke oven
tweak.ie.cokeoven.remove(*);
```