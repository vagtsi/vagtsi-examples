# Java Bridge Methods Example
This is a small example for testing java bridge (synthetic) methods created by the java compile on type erasure
as documented in [Effects of Type Erasure and Bridge Methods](https://docs.oracle.com/javase/tutorial/java/generics/bridgeMethods.html).
In particular this simple example tests and prints out runtime annotations on bridge methods and shows the weird differences
when executed on JVM or within Eclipse IDE.

When executed outside of IDE with Gradle with `./gradlew run` the result printed to console is:  
```
Class de.vagtsi.examples.javagenerics.bridgemethods.NamedEntityStorageService contains 2 'insert' methods:
> insert(de.vagtsi.examples.javagenerics.bridgemethods.NamedEntity) (bridge = false, synthetic = false, @Inject = true)
> insert(de.vagtsi.examples.javagenerics.bridgemethods.Entity) (bridge = true, synthetic = true, @Inject = true)
```

When executed within Eclipse the result is:
```
Class de.vagtsi.examples.javagenerics.bridgemethods.NamedEntityStorageService contains 2 'insert' methods:
> insert(de.vagtsi.examples.javagenerics.bridgemethods.NamedEntity) (bridge = false, synthetic = false, @Inject = true)
> insert(de.vagtsi.examples.javagenerics.bridgemethods.Entity) (bridge = true, synthetic = true, @Inject = false)
```

This shows an interesting difference: Eclipse does not assign the annotation `@Inject` to the bridge method while this is 
the case for the JVM execution.
This is an annoying fact for e.g. Guice AOP logging warnings about `synthetic method` on binding method interceptors
on generic methods.    
