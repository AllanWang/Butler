package test;

/**
 * Created by Allan Wang on 2017-05-11.
 */
public class CallSuperOver extends CallSuperBase {

    @Override
    public void hello() {
        super.hello();
        System.out.println("World");
    }
}
