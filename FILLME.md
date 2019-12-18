# Sless

## Extensibility

Briefly explain which files you have to change to introduce a new sless feature, e.g. add a new pseudo-class/-element, add support for namespaces, add at-rules, etc. 
Hint: if this lists all the files in your project you should reevaluate your implementation.

    Adding a new feature
    
## Extra

Write which files, if any, contain extra self-written tests. If you did something extra impressive let us know here as well!

  #### Merge
   
    Assumption: The structure of the sheet being merged into is maintained. This means that if a selector occurs 
    identically twice (writing proper css/sless you wouldn't do that) in the sheet being merged into (rightmost sheet) 
    then the rules with identical selector will not be merged.
    E.g. it will merge sheets into the rightmost sheet. Not the rightmost with itself.
    This will cause problematic behavior as seen in the "occurs twice" test. A solution would be to merge the rightmost sheet wit
    itself first but this is not implemented as writing the same selector twice is bad practice
    - Occurs twice in rightmost
    
    - Extended merge
    
  #### Comment
  
    Assumption: only one comment per rule/declaration
     - Second comment overwrites first


   #### Mixin
   
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