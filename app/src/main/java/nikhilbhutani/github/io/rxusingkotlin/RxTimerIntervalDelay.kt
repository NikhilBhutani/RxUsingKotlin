package nikhilbhutani.github.io.rxusingkotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class RxTimerIntervalDelay : AppCompatActivity() {

    var compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        /**
         *   Whenever we have a use case in which we want to do a particular task after a span of specified time
         *   we can use timer
          */
        compositeDisposable.add(  Observable.timer(2, TimeUnit.SECONDS)
            .flatMap {

                return@flatMap Observable.create<String>{
                    run {
                        it.onNext("Hello Nikhil, From Timer")
                        it.onComplete()
                    }
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.i("Timer ", " $it")
            })


        /**
         *  Delay operator is used when we want to perform a task first and emit to the subscriber with a delay
         */
        compositeDisposable.add(  Observable.create<String>{
            run {
                it.onNext("Hello Nikhil, From Delay")
                it.onComplete()
            }
        }
            .subscribeOn(Schedulers.io())
            .delay(2, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.i(" Delay ", " $it")

            })



        /**
         *   Whenever we have a use case in which we want to do a particular task again & again after a span of specified time
         *   we can use timer
         */

        compositeDisposable.add( Observable.interval(0, 3, TimeUnit.SECONDS )
            .take(4).flatMap {    Observable.create<String>{
            run {
                it.onNext("Hello Nikhil")
                it.onComplete()
            }
        }}
            .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.i(" From Interval ", " $it")

            })

    }


    override fun onDestroy() {
        super.onDestroy()
    compositeDisposable.clear()
    }

}