# Sless

## Extensibility

Briefly explain which files you have to change to introduce a new sless feature, e.g. add a new pseudo-class/-element, add support for namespaces, add at-rules, etc. 
Hint: if this lists all the files in your project you should reevaluate your implementation.

    Adding a new feature would need to change 
    - the Base trait if a new AST case class is needed (ex: new pseudo-class)
    - if added an AST class, update compiler trait to include the new case
    - defining a *DSL trait as interface in sless.dsl
    - adding an extra trait in sless.ast and implementing functionality there
    
## Extra

Write which files, if any, contain extra self-written tests. If you did something extra impressive let us know here as well!

  #### MergeTestExtra
   
    - Occurs twice in rightmost (assumption explained in comment)
    - Extended merge
    
  #### CommentTestExtra
  
    - Second comment overwrites first


   #### MixinTestExtra
   
    Showing mixin skills
    - Simple color mixin
    - Nested margin
    - Height and double width

## Better Values

Explain your implementation briefly, if you chose to implement this extension.

    /

## Improving original features

If you chose to extend some of the original features as an extensions, properly document what you did here.

    /

## Custom Extension

If you came up with your own extension, properly document it here. Explain its
intended behavior and showcase by example.

    /

## Feedback on the Project 

After working on this project for such a long time, you're allowed to vent in this
section. Tell us what you learned or what you would've liked to learn instead,
etc. This does not count toward your grades at all (neither positive nor negative).

    Scala > Java