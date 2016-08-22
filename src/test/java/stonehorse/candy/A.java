package stonehorse.candy;

public class A{
    public static A a(){
        return new A();
    }
    public static class B extends A{
        public static B b(){
            return new B();
        }
    }
}
