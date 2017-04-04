package com.linxcool.andbase.retrofit;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by linxcool on 17/4/3.
 */

public abstract class SimpleObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onComplete() {
    }
}
