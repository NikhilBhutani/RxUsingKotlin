package nikhilbhutani.github.io.rxusingkotlin

import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.Disposable
import javax.xml.datatype.DatatypeConstants.SECONDS
import android.R.attr.delay
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import android.arch.lifecycle.Transformations.switchMap
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import java.util.concurrent.TimeUnit
import kotlin.jvm.functions.FunctionN


class SwitchMapOperator : AppCompatActivity() {

    private val TAG = SwitchMapOperator::class.java!!.getSimpleName()

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val integerObservable = Observable.fromArray(arrayOf(1, 2, 3, 4, 5, 6))


        // it always emits 6 as it un-subscribes the before observer
       var oho =  integerObservable
            .subscribeOn(Schedulers.io())
        //    .observeOn(AndroidSchedulers.mainThread())
            .switchMap {Observable.just(it)
            }
            .subscribe {  Log.i("SwitchMapOperator ", "onNext $it.") }


//            .subscribe(object : Observer<Int> {
//
//                override fun onComplete() {
//                    Log.i("SwitchOperator ", "onComplete, All Users with Addresses are emitted")
//                }
//
//                override fun onSubscribe(d: Disposable) {
//                    disposable = d
//                }
//
//                override fun onNext(t: Int) {
//                    Log.i("SwitchMapOperator ", "onNext $t")
//                }
//
//                override fun onError(e: Throwable) {
//                    Log.i("SwitchOperator ", "onError $e")
//
//                }
//            })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable!!.dispose()
    }

}