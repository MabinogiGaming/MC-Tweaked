#jei

Tweaks involving the mod Just Enough Items.

## add
*tweak.jei#add([Stack](/arguments/stack) item);*

Adds an item to JEI, can be useful for items that are hidden by default.
```java
//adds diamond to jei
tweak.jei#add(<minecraft:diamond>);
```
<br>

## hide
*tweak.jei#hide([Stack](/arguments/stack) item);*

Hides an item from jei.
```java
//hides diamond in jei
tweak.jei#hide(<minecraft:diamond>);
```
<br>

---
## remove
*tweak.jei#remove([Stack](/arguments/stack) item);*

Similar to hide, except that it also removes recipes for this item as if calling [tweak.recipes#remove](/actions/recipes/#remove)
```java
//hides diamond in jei and removes all recipes for it
tweak.jei#remove(<minecraft:diamond>);
```