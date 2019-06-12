package nikhilbhutani.github.io.rxusingkotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class TestActivity : AppCompatActivity() {

    private lateinit var disposable : Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


       disposable = Observable.just("Hi", " Nikhil, ").delay(10000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<String>(){

                override fun onComplete() {
                    Log.i("onComplete ", "")
                }

                override fun onNext(t: String) {
                   Log.i("onNext ", " $t")
                }

                override fun onError(e: Throwable) {
                    Log.i(" ", " $e")
                }

            })

    }


    override fun onDestroy() {
        super.onDestroy()
       disposable.dispose()
    }

}