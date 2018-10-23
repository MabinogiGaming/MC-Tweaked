# recipe

A special argument type that allows creation of a crafting recipe.

## Argument

The arrangement of a recipe is important, with the order corresponding to specific slots on the crafting table as so :  
`[[<A>,<B>,<C>],[<D>,<E>,<F>],[<G>,<H>,<I>]]`

Produces the crafting table recipe of :  
A  |  B  |  C  
D  |  E  |  F  
G  |  H  |  I

Where A-I are [ingredients](/arguments/ingredient)

The `[ and ]` seperators are used to represent rows. Ingredients can be left out and the recipe will be automatically sized to create the correct shape.

The best way to illustrate this is examples of vanilla recipes :

```python
#chest (8 wood)
[[<plankWood>, <plankWood>, <plankWood>][<plankWood>, <>, <plankWood>][<plankWood>, <plankWood>, <plankWood>]]

#door (6 wood)
[[<plankWood>, <plankWood>][<plankWood>, <plankWood>][<plankWood>, <plankWood>]]

#stone slab (3 stone)
[[<minecraft:stone>, <minecraft:stone>, minecraft:stone]]

#torch (stick and coal)
[[<minecraft:coal>][<minecraft:stick>]]
```
