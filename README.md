#Stonehorse.candy, simpler java

Candy is a collection of java functions intended to simplify code structure, by reducing the need of variables. It introduce tail calls using a trampoline, functions for composition of lazy iterables and functions for reversing functional composition. Candy is supposed to be small, simple and easy to use. It relies on Java 8 and the lambda expression. 

It is early interface may still change

## Installation

Candy can be found in the central maven repo, just add the following to your pom.xml, or similar depending on your build, and maven should download it for you.

```xml
<dependencies>
  <dependency>
    <groupId>com.github.stefanvstein</groupId>
    <artifactId>candy</artifactId>
    <version>0.1</version>
  </dependency>
</dependencies>
```

## Usage

Below are a few example usages of some functions to describe the basics. The functions are grouped in classes.

*[Choices](#choicesjava).java, [Maybe](#maybejava).java, [Iterables](#iterablesjava).java, [Threading and Functions](#threading-and-functionsjava).java & [Trampoline](#trampolinejava).java*

[JavaDoc](https://stefanvstein.github.io/stonehorse.candy/javadocs/index.html)

### [Choices](https://stefanvstein.github.io/stonehorse.candy/stonehorse/candy/Choices.html).java

*Choices contains [ifelse](#ifelse),[when](#when),[unless](#unless),[mapOr](#mapor), [cond](#cond) & [either](#either)*

Choices is a number of expression replacements for the if-statement. It is used to reduce the control flow graph and to make code easier to read by naming different types of conditional expressions

#### ifelse
The `ifelse` function is the expression equivalent of the `if else` statement. It represents a single value and eliminates the need of temporary variables.


```java
// Write:
int question=3;
final String answer=ifelse(question==3, 
                           ()->"YES", 
                           ()->"NO"));
println(answer);

// instead of either:

int question=3;
String answer="Unknown";
if(question==3)
  answer="YES";
else 
  answer="NO";
println(answer);

// where the temporary variable, answer, is needed or:

int question=3;
if(question==3)
  println("YES");
else 
  println("NO");

// where println calls are duplicated.
```
Code becomes less complex with `ifelse` expressions. Nested `ifelse` expressions logically becomes much simpler, as complexity doesn't grow exponentially. Expressions doesn't require temporaries and represent a single value even when nested. `ifelse` will also remove your `{}` from your lambdas.

`ifelse` expressions are equivalent to the ternary operator, which also is expression with a value, but having verb first can be easier to read as expressions grow. E.g.:

```java
int question=3;
final String answer = question==3 ? "YES" : "NO";
println(answer);
```
#### when
Conditional expressions as functions, like `ifelse`, can be categorized to further reveal intent. The function `when`
is like `ifelse` but returns `null` instead of evaluation of an `else` clause.

```java
final String answer = when(question==3, ()->"YES"));
// is same as:
final String answer = question==3 ? "YES" : null;
```

With the `when` function you know you don't need to look further for an `else` clause. The intent is revealed at head, in contrast to the `if` statement where you need to parse ahead to understand structure and occasionally find an `else` clause. This is difficult with nested `if else` statement structures. 

#### doWhen
`if` statements without corresponding `else` clauses usually indicate side effects. Side effects should not represent a value. The purpose of the `void` keyword is to indicate side effects, routines that do not return anything.

`doWhen` acts like when but does not return anything. It reveals that it is used to do side effects when a condition is met, and that your code should not act on the outcome, it's a side effect, unless something exceptional occur.
```java
doWhen(debug, ()->println("DEBUG - we have reached all the way to this point"));
```

#### unless

When the normal path through an if statement is the truth, when the else clause is exceptional, it becomes easier to understand code when you read the then clause before the test. The test and the else clause becomes a guard for exceptional conditions. With the unless function the important path stands out by standing ahead. 

```java
return unless( ()-> "There are " + n + " apples",
               n<0,
               ()-> "Weird!! Less than 0 apples?");
               
// is both easier understand, as the normal case comes first, and less complex than:
if(n<0)
  return "Weird!! Less than 0 apples?"
else 
  return "There are " + n + " apples"
```

#### mapOr

It is common that you have to check if an object exist prior to calling it. Sometimes it is difficult to see the value of the absence of the call due to null.

```java
return mapOr(a, String::toUpperCase, ()->"");

//..is simpler than:

if (null==a)
  return "";
else return a.toUpperCase();
```

Using Optional is another mean to handle null, mapOr is sometimes easier as Optional as it is a single call.


#### cond

The `cond` function is kind of a `switch case` expression. Each case is a pair of Suppliers where the first is a Boolean Supplier and the other is of the cond expressions return type. A last stand alone Supplier is the default, if all other cases has returned FALSE. As each case is a function call it can match on anything. `cond` will short circuit when a match is found

```java
String a= cond(()->1==2, ()->"What?",
               ()->1==1, ()->"Yes",
               ()->2==2, ()->"But 1==1!",
               ()->"catch all");
```

The cond function is helpful in reducing nested if statements

#### either

Either is the pure if else expression. The test is performed on an object using a predicate of the objects type, which result application of either Suppliers.

```java
String s= either(value,
                 this::is3,
                 v->"III",
                 Integer::toBinaryString);

//would be the equivalent of:

String s = null;
if(is3(value))
  s="III";
else
  s=Integer.toBinaryString(value)
```
`either` reduces the need of variables further.

### [Maybe](https://stefanvstein.github.io/stonehorse.candy/stonehorse/candy/Maybe.html).java

*To [Usage](#usage)*

Null checks can easily be eliminated with Optional. Sometimes its preferable to have the verb first, as Optional is a object oriented construct. It's a matter of taste. Verb first programs may be easier to maintain.

```java
Maybe.map(String::toUpperCase, Maybe.just("Value"))
// would yield Maybe.just("VALUE") while
Maybe.map(String::toUpperCase, Maybe.nothing())
//..yield Maybe.nothing().
``` 
Maybe.just and Maybe.nothing creates Optionals while Maybe.map calls map on that Optional.

There is also a Maybe.maybe(T t) function that return Maybe.nothing() if t happen to be null. 

### [Iterables](https://stefanvstein.github.io/stonehorse.candy/stonehorse/candy/Iterables.html).java

*Back to [Usage](#usage)*

*Iterables contains: [map](#map), [filter](#filter), [reduce](#reduce), [take](#take-and-takewhile) & [iterate](#iterate)

Streams in Java are great as they provide the functional construct we are used to in other languages. Since streams are an object oriented construct it is pretty hard to add new functions. Iterables is a set of functions with similar functionality, but without the OO principles. It is used to create transforming lazy and composable Iterables. Iterables are simpler, easier to extend, and have guaranteed ordering. All computation is done with the iterators extracted from these Iterables.

#### map

Returns an Iterable which iterator applies a function to each element 
```java
Iterable<Integer> inced = Iterables.map( e -> e+1, Arrays.asList(1, 2, 3));
println(Iterables.list(inced));
```
would print [2, 3, 4]

The iterator produced, when converting the Iterable to list, is lazy and applies the function as the next value is retrieved. The list function produces a List from the next iterator of a Iterables, unless the Iterable isn't already a list.

#### filter
Filter, as usually, removes elements not matching a predicate

```java
Iterable<Integer> even = Iterables.filter( e -> 0==e%2, Arrays.asList(1, 2, 3, 4));
println(Iterables.list(even));
```
would print [2, 4], as 1 and 2 are odd numbers

The iterators produced by even  are lazy, which is nice since we then can compose the functions without excessive memory allocation.

```java
Iterable<Integer> odd = Iterables.map( v-> v+1, 
                                     Iterables.filter( e -> 0==e%2, 
                                                       Arrays.asList(1, 2, 3, 4)));
println(Iterables.list(odd));
```

would print [3, 5]

With some static imports it becomes more readable

```java
Function<Integer, Integer> inc = (v) -> v+1;
Predicate<Integer> isEven = (e) -> 0==e%2;
void printOdd(){
  Iterable<Integer> odd = map( inc, 
                               filter( isEven, 
                                       asList(1, 2, 3, 4)));
  println(list(odd));
}
```

#### reduce

Reduce or fold left as we may call it in other languages is terminal, like list, and does not necessarily return an Iterable. It combines elements of the next iterator into a single result by repeated application of a combining operation. As a simple example we could sum like:

```java
int sum = reduce((a,v) -> a+v, 0, asList(1,2,3,4))
println(sum);
```
would print 10

This is terminal

#### take and takeWhile

Rather than producing a single value like you do with reduce, you may need the first few elements in iterators. The take functions creates Iterables producing iterators that limit the number of elements produced. 

```java
Iterable<Integer> afew = take(3, asList(1,2,3,4));
println(list(afew));
```
would print [1, 2, 3] while takeWhile takes elements as long as they match a predicate.

```java
Iterable<Integer> afew = takeWhile(e -> e < 3, asList(1,2,3,4));
println(list(afew));
```
would print [1, 2]. 

#### iterate

The iterate function takes a function and a initial value and produces iterators that produces repeated application. That is initial, function(initial), function(function(initial)) and so on.

The infinite rangeFrom function could be created as:

```java
Iterable<Integer> rangeFrom(int initial){
  return iterate(v->v+1, initial);
}
```

Since rangeFrom would continue forever we can limit the iterators produced using take.

```java
println(list(take(4, rangeFrom(1))));
```

would print [1,2,3,4]. rangeFrom would not produce more than 4 numbers, as take won't pull more numbers.

There is more: 

* **first** takes the first element of the next iterator. This is terminal
* **second** takes the second element of the next iterator. This is terminal
* **nth** takes the nth element of the next iterator. This is terminal
* **last** takes the last element of the next iterator
* **rest** is the iterable with all but the first element
* **next** is rest but never empty
* **with** is the iterable with an element prepended
* **withLast** is the iterable with an element appended
* **drop** is the iterablw with a number of elements dropped
* **dropLast** has all elements except the last
* **dropWhile** is the iterable of with all first matching a predicate removed
* **flatMap** is the iterable of concatenations of the result of applying map to every element in iterators of data
* **reductions** is the iterable of producing intermediate values of the reduction of elements by f, starting with accumulator
* **takeNth** is the iterable of every nth element
* **partition** is the iterable of num length iterables 
* **partitionBy** is the iterable of iterables grouped by application
* **splitAt** is the tuple of iterables split at i, where both are lazy
* **repeatedly** is the iterable of continuous supplier retrieval
* **repeat** is the iterable of continuous value
* **range** is the iterable of increasing integers
* **cycle** is the iterable of repeated iterable
* **stream** is a iterable as stream
* **iterable** is a stream of iterable
* **set** is a set of the next iterators values, unless the supplied iterable already is a set. This is terminal
* **forceList** is a new list of iterable. This is terminal
* **forseSet** is a new set of iterable. This is terminal

Again, the iterators produced are lazy and the while functions can safely be composed with other Iterables functions. It is about what to transform rather than how to iterate data. 

### [Threading](https://stefanvstein.github.io/stonehorse.candy/stonehorse/candy/Threading.html) and [Functions](https://stefanvstein.github.io/stonehorse.candy/stonehorse/candy/Functions.html).java

*Back to [Usage](#usage)*

As seen above functional composition can be a little difficult to read. It executes backwards. With the thread function you can write nested composition in serial fashion, or backwards if you will.


```java
println(thread(rangeFrom(1),
               r->take(4, r),
               Iterables::list));

// is same as:

println(list(take(4, rangeFrom(1))));
```
The thread function takes the first value, and applies that to the first function, and applies the result of that to the second function and finally returns the result of the last function.

When we have more than one argument, we can clean things up by resorting to partial application. Functions.java contains functions that improve functions. That is e.g. the partial application of converting a BiFunction to a Function by applying the first argument prior to executing the function.

```java
println(thread(rangeFrom(1),
               function(Iterables::take, 4),
               Iterables::list));
```

Since the if statements replacements found in Choices.java are expressions, they also fit well within the thread function. 

### [Trampoline](https://stefanvstein.github.io/stonehorse.candy/stonehorse/candy/Trampoline.html).java

*Back to [Usage](#usage)*

Most functions in this code collection is of functional style. Functional programming languages usually utilize tail calls to implement iteration. It is simple and allow for programming without side effects. Variables are turned into final argument values. Java does not support tail calls well since the compiler can't optimize away excessive stack usage, among other things. Compilers can implement tail call optimization using a trampoline, and the very same constructs can be written by hand in a library.

A function like:

```java
static String foo(int a) {
  return 
    ifelse(a > 200000,
           () -> "Foo"
           () -> foo(a + 1));
}
```

...that eats stack, can be rewritten like:

```java
static Supplier<Continuation<String>> foo(int a) {
  return () -> 
     ifelse(a > 200000,
            () -> done("Foo"),
            () -> recur(foo( a + 1)));
}
```
... can be used by the trampoline without eating stack:

```java
trampoline(foo(0));
```

The functions done and recur creates data telling the trampoline to either stop calling foo or to continue with the data passed to recur, a Supplier.

This technique does allow for mutual recursion, like:

```java
static Supplier<Continuation<Boolean>> isEven(int a) {
  return () -> 
    ifelse(a == 0,
           () -> done(true),
           () -> recur(isOdd(a - 1)));
}

static Supplier<Continuation<Boolean>> isOdd(int a) {
  return () -> 
    ifelse(a == 0,
           () -> done(false)
           () -> recur(isEven(a - 1)));
}

trampoline(isEven(200000));
```

A recursive function returns a Supplier of a Continuation. The Continuation either contains a final value or another function returning a Continuation. The trampoline will call the repeatedly returned continuation function as long as they returned. When none is returned the eventual final value is returned. The functions done and recur produce the appropriate Continuations, with final value or continuation.

Three other functions lazy, seq, and stop are used similarly to produce lazy recursion through a the Iterator interface. Using these, the lazy iterators in Iterables.java becomes pretty easy to implement. 

The map function can be implemented as:

```java
public static <A, V> Iterable<V> map(final Function<? super A, V> f, 
                                     final Iterable<A> data) {
  requireNonNull(f);
  return when(nonNull(data), () ->
     () -> either(data.iterator(),
                  Iterator::hasNext,
                  i -> lazy(mapI(f, i)).iterator(),
                  i -> emptyIterator()));
}

private static <A, V> Supplier<Continuation<V>> mapI(final Function<? super A, ? extends V> f, 
                                                     final Iterator<A> elements) {
  return () -> either(f.apply(elements.next()),
                      v -> elements.hasNext(),
                      v -> seq(mapI(f, elements), v),
                      Trampoline::done);
}
```

The seq function is like recur but adds an additional value along with the continuation. The lazy function creates a Iterator of the value passed along with the continuation. The iterator returned by lazy will continue to call incoming continuations and return values when present, until an eventual final value is present. There is also a stop function that stops the iterator without returning a final value.


New lazy iterables can easily be constructed by composing the dozen of functions already existing. Perhaps like:
```java
<T> Iterable<T> interleave(Supplier<? extends T> t, 
                           Iterable<? extends T> d){
  return with(first(d), 
              flatMap(v-> asList(t.get(), v),
                      Iterables.next(d)));
}
```
With recursion its also easy to implement these from scratch.

The important aspect of trampoline is that functional algorithms with tail recursion can be implemented in Java.

 <div align="right">
 /Stefan von Stein
</div> 
