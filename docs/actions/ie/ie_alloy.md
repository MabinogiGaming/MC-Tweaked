#ie.alloy

Tweaks involving the **Alloy Kiln** from Immersive Engineering.

## add
*tweak.ie.alloy.add([stack](/arguments/stack/) output, [stack](/arguments/stack/) inputA, [stack](/arguments/stack/) inputB, [stack](/arguments/integer/) time);*  
*tweak.ie.alloy.add([stack](/arguments/stack/) output, [stack](/arguments/stack/) inputA, [dict](/arguments/dict/) inputB, [stack](/arguments/integer/) time);*  
*tweak.ie.alloy.add([stack](/arguments/stack/) output, [dict](/arguments/dict/) inputA, [dict](/arguments/dict/) inputB, [stack](/arguments/integer/) time);*  

Adds a new recipe to the **Alloy Kiln**.
```python
#adds recipe where stone and iron turns into diamonds, taking 1000 ticks
tweak.ie.alloy.add(<minecraft:diamond>, <minecraft:stone>, <ingotIron>, 1000);
```
<br>

---
## remove
*tweak.ie.alloy.remove([stack](/arguments/stack/) output);*  
*tweak.ie.alloy.remove([stackList](/arguments/stacklist/) outputs);*  
*tweak.ie.alloy.remove([all](/arguments/all/) all);*

Removes all recipes from the **Alloy Kiln** that have the provided output
```python
#removes the recipe to make charcoal from the alloy kiln
tweak.ie.alloy.remove(<minecraft:coal:1>);

#removes all recipes from the alloy kiln
tweak.ie.alloy.remove(*);
```