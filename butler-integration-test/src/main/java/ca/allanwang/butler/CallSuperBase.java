package ca.allanwang.butler;

/**
 * Created by Allan Wang on 2017-05-10.
 */
public class CallSuperBase {

    @CallSuper
    void hello() {
        System.out.println("Hello");
    }
}
