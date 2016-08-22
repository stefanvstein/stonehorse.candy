# Stonehorse.candy, simpler java

Candy is a collection of java functions intended to simplify code structure. It introduce tail calls using a trampoline and functions for reversing functional composition. There is a Iterable library for composition of lazy iterators. It is supposed to be simple, rather than easy, to use. It relies on Java 8 and the lambda expresion. 

It is very early, and interface may still change

## Installation

You can either copy src to you project, or build with maven. It is not published to any public repo yet.

## Usage

Below are a few example usages of some functions to describe the basics. The functions are grouped in classes.

Choices.java, [Maybe.java](#Maybe.java), [Iterables.java](#Iterables.java) [Threading.java and Functions.java](#Threading.java) & [Trampoline.java](#Trampoline.java)

### Choices.java

Choices is a number of expression replacements for the if-statement. It is used to reduce the control flow graph and to make code easier to read by naming different types of conditional expressions

#### ifelse

The most basic one is ifelse. A if else statement like:

```java
int question =3;
String answer ="Unknown";
if(question==3)
  answer="YES";
else 
  answer="NO";
println(answer);
```

can be written as

```java
int question=3;
println(ifelse(question==3, 
             ()->"YES", 
             ()->"NO"));
```

The ifelse function is an expression, so only one print statement is needed while the temporary answer variable isn't necesary, so the code becomes less complex. Premature evaluation is avoided by the answers being Suppliers. It's possible to write this even shorter using the ternary operator, but having the verb first can be simpler as expressions grow. 

```java
int question=3;
println(question==3 ? "YES" : "NO");
```

A growing expression is less complex than a growing set of statements. The initial example is not too complex, but real methods rarely consists of only single conditionals. Nested if statements is painful.

#### when
Is like ifelse but returns null instead of evaluation of the else clause when the test is false.

```java
println(question==3 ? "YES" : null);
```

becomes 

```java
println(when(question==3, ()->"YES"));
```

or using whenNot when false is of interest:

```java
println(whenNot(question==4, ()->"NO"));
```

#### mapOr

Return function application of something, except when something happen to be null and the value instead is pulled from a Supplier.

```java
if (null==a)
  return "";
else return a.toUpperCase();
```
..can instead be written as

```java
return mapOr(a, String::toUpperCase, ()->"");
```

Using Optional is another mean to handle null, mapOr is sometimes easier as Optional is an object oriented construct.

#### unless

Sometimes it easier to understand a program if you read the then caluse before the test statement. That is when the normal path is the truth.

```java

if(n<0)
  return useBackup();
else 
  return doSomethingInteresting();
```

can instead be written

```java
return unless(()->doSomethingInteresting(), 
              n<0, 
              ()->useBackup());
```
as it's more important to understand that this program is about to do something interesting, while the backup plan is exceptional.

#### doWhen

Sometimes it's more clear if side effects don't return result. It may be better to take care of failing effects as early as possible. The doWhen function is void but still act as a conditional. It behaves as when, but do indicates that its clearly not a function.

```java
doWhen(debug, ()->println("DEBUG - we have reached all the way to this point"));
```

#### cond

The cond function is kind of a switch case expression. Each case is a pair of Suppliers where the  first is a Boolean Supplier and the other is of the cond expressions return type. A last stand alone Supplier is the default, if all other cases has returned FALSE. As each case is a function call it can match on anything. It will short circuit when a match is found

```java
String a= cond(()->1==2, ()->"What?",
               ()->1==1, ()->"Yes",
               ()->2==2, ()->"But 1==1!",
               ()->"catch all");
```

The cond function is helpful in reducing nested if statements

### Maybe.java

Null checks can easily be eliminated with Optional, which is a object oriented construct. Sometimes its preferable to have the verb first. Its a matter of taste. Verb first programs may be easier to maintain.

```java
Maybe.map(String::toUpperCase, Maybe.just("Value"))
```

would yield Maybe.just("VALUE") while

```java
Maybe.map(String::toUpperCase, Maybe.nothing())
```

yield Maybe.nothing(). Maybe.just and Maybe.nothing creates Optionals while Maybe.map calls map on that Optional.

There is also a Maybe.maybe(T t) funktion that return Maybe.nothing() if t happen to be null. 

### Iterables.java

Streams in Java are great as they provide the functional construct we are used to in other languages. Since streams are an object oriented construct it is pretty hard to add new functions. Iterables is a set of functions with similar functionality, but without the OO principles. It is used to create transforming Iterables that are lazy and can be composed.

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

Reduce or fold left as we may call it in other langauges is terminal, like list, and does not necesarily return an Iterable. It combines elements of the next iterator into a single result by repeated application of a combining operation. As a simple example we could sum like:

```java
int sum = reduce((a,v) -> a+v, 0, asList(1,2,3,4))
println(sum);
```
would print 10

This is terminal

#### take and takeWhile

Rather that producing a single value like you do with reduce, you may need the first few elements in iterators. The take functions creates Iterables producing iterators that limit the number of elements produced. 

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

The iterate function takes a function and a initial value and produces iterators that produces reapeated application. That is initial, function(initial), function(function(initial)) and so on.

The infinite range function could be created as:

```java
Iterable<Integer> range(int initial){
  return iterate(v->v+1, initial);
}
```

Since range would continue forever we can limit the iterators produced using take.

```java
println(list(take(4, range(1))));
```

would print [1,2,3,4]. range would not produce more than 4 numbers, as take won't pull more.

And there is more: 

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
* **repeatedly** is the iterable of continous supplier retrieval
* **repeat** is the iterable of continous value
* **range** is the iterable of increasing integers
* **cycle** is the iterable of repeated iterable
* **stream** is a iterable as stream
* **iterable** is a stream of iterable
* **set** is a set of the next iterators values, unless the supplied iterable already is a set. This is terminal
* **forceList** is a new list of iterable. This is terminal
* **forseSet** is a new set of iterable. This is terminal

Again, the iterators produced are lazy and the while functions can safely be composed with other Iterables functions. It is about what to transform rather than how to iterate data. 

### Threading.java and Functions.java

As seen above functional composition can be a little difficult to read. It executes backwards. With the thread function you can write nested composition in serial fasion, or backwards if you will.

```java
println(list(take(4, range(1))));
```

can instead be written

```java
println(thread(range(1),
               r->take(4, r),
               s->list(s)));
```

The thread function takes the first value, and applies that to the first function, and applies the result of that to the second function and finally returns the result of the last function.

Method references cleans things up

```java
println(thread(range(1),
               r->take(4, r),
               Iterables::list));
```

When we have more than one argument, we can clean things up by resorting to partial application. Functions.java contains functions that improve functions. That is e.g. the partial application of converting a BiFunction to a Function by applying the first argument prior to executing the function.

```java
println(thread(range(1),
               partial(Iterables::take, 4),
               Iterables::list));
```

There are overloads of thread with arity on functions to call. There is also threadMaybe that works the same but aborts with null when ever a null is to be passed between the functions.

Since the if statements replacements found in Choices.java are expressions, they also fit well within the thread function. 

### Trampoline.java

Most functions in this code collection is of functional style. Functional programming languages usually utilize tail calls to implement iteration. It is simple and allow for programming without side effects. Java does not support tail calls well since the compiler can't optimize away excessive stack usage, among other things. Compilers can implement tail call optimization using a trampoline, and the very same constructs can be written by hand in a library.

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
static Supplier<RecursiveVal<String>> foo(int a) {
  return () -> 
     ifelse(a > 200000,
            () -> done("Foo"),
            () -> recur(foo( a + 1)));
}
```

with a bit more complex signature, and explicit intent on recursion
... can be used by the trampoline without eating stack:

```java
trampoline(foo(0));
```

The functions done and recur creates data telling the trampoline to either stop calling foo or to continue with the data passed to recur, a Supplier.

This technique does allow for mutual recursion, like:

```java
static Supplier<RecursiveVal<Boolean>> isEven(int a) {
  return () -> 
    a == 0
    ? done(true)
    : recur(isOdd(a - 1));
}

static Supplier<RecursiveVal<Boolean>> isOdd(int a) {
  return () -> 
    a == 0
    ? done(false)
    : recur(isEven(a - 1));
}

trampoline(isEven(200000));
```

A recursive function returns a Supplier of a RecursiveVal. The RecursiveVal either contains a final value or a continuation. The continuation may be another function that returns a RecursiveVal. The trampoline will call the repeatedly returned continuations as long as they returned. When none is returned the eventual final value is returned. The functions done and recur produce the apropriate RecursiveVals, with final value or continuation.

Three other functions lazy, seq, and stop are used similarily to produce lazy recursion through a the Iterator interface. Using these, the lazy iterators in Iterables.java becomes pretty easy to implement. 

The map function is implemented as:

```java
public static <A, V> Iterable<V> map( Function<? super A, V> f, 
                                      Iterable<A> data) {
  if (isNull(data) || isNull(f))
    return null;
  return () -> {
    Iterator<A> i = data.iterator();
    if (!i.hasNext())
      return emptyIterator();
    return lazy(mapI(f, i)).iterator();
  };
}

private static <A, V> 
Supplier<RecursiveVal<V>> mapI(Function<? super A, ? extends V> f, 
                               Iterator<A> elements) {
  return () -> {
    V v = f.apply(elements.next());
    if (elements.hasNext())
      return seq(mapI(f, elements), v);
    return done(v);
  };
}
```

The seq function is like recur but adds an aditional value along with the continuation. The lazy function creates a Iterator of the value passes along with the continuation. The iterator returned by lazy will continue to call incomming continuations and return values when present, until an eventual final value is present. There is also a stop function that stops the iterator without returning a final value. 

New lazy iterables can easily be constructed by composing the dozen of functions already existing. With recursion its also easy to implement these from scratch.

 <div align="right">
 /Stefan von Stein
</div> 
