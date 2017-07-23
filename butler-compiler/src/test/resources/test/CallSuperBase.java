package test;

import ca.allanwang.butler.CallSuper;

/**
 * Created by Allan Wang on 2017-05-10.
 */
public class CallSuperBase {

    @CallSuper
    public void hello() {
        System.out.println("Hello");
    }
}
