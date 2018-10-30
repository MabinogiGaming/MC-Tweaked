# recipeShapeless

A special argument type that allows creation of a shapeless crafting recipe.

A shapeless recipe is one that can have the inputs placed in any order, their positioning doesn't matter.

## Argument

The arrangement of a recipe is important not important, inputs can be placed in any order :  
`[<A>,<B>,<C>,<D>,<E>,<F>,<G>,<H>,<I>]`

Where A-I are [ingredients](/arguments/ingredient/)

The number of ingredients can be anything from 1-9.

The best way to illustrate this is examples of vanilla recipes :

```python
#flint and steel (1 flint, 1 iron)
[<minecraft:flint>, <ingotIron>]
```
