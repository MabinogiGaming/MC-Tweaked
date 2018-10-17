#ie.crusher

Tweaks involving the Crusher from Immersive Engineering.

## add
*tweak.ie.crusher#add([Stack](/arguments/stack) output, [Stack](/arguments/stack) input, [Integer](/arguments/integer) time);*  
*tweak.ie.crusher#add([Stack](/arguments/stack) output, [Dict](/arguments/dict) input, [Integer](/arguments/integer) time);*

Adds a new recipe to the crusher.
```java
//adds recipe where stone turns into diamonds, taking 1000 ticks
tweak.ie.crusher#add(<minecraft:diamond>, <minecraft:stone>, 1000);
```
<br>

---
## remove
*tweak.ie.crusher#remove([Stack](/arguments/stack) output);*  
*tweak.ie.crusher#remove([StackList](/arguments/stacklist) outputs);*  
*tweak.ie.crusher#remove([All](/arguments/all) all);*

Removes all recipes from the crusher that have the provided output
```java
//removes any recipes to make diamond from the crusher
tweak.ie.crusher#remove(<minecraft:diamond>);

//removes all recipes from the crusher
tweak.ie.crusher#remove(*);
```