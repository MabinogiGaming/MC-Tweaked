#ie.crusher

Tweaks involving the Crusher from Immersive Engineering.

## add
*tweak.ie.crusher.add([stack](/arguments/stack/) output, [stack](/arguments/stack/) input, [integer](/arguments/integer/) time);*  
*tweak.ie.crusher.add([stack](/arguments/stack/) output, [dict](/arguments/dict/) input, [integer](/arguments/integer/) time);*

Adds a new recipe to the crusher.
```python
#adds recipe where stone turns into diamonds, taking 1000 ticks
tweak.ie.crusher.add(<minecraft:diamond>, <minecraft:stone>, 1000);
```
<br>

---
## remove
*tweak.ie.crusher.remove([stack](/arguments/stack/) output);*  
*tweak.ie.crusher.remove([stackList](/arguments/stacklist/) outputs);*  
*tweak.ie.crusher.remove([all](/arguments/all/) all);*

Removes all recipes from the crusher that have the provided output
```python
#removes any recipes to make diamond from the crusher
tweak.ie.crusher.remove(<minecraft:diamond>);

#removes all recipes from the crusher
tweak.ie.crusher.remove(*);
```