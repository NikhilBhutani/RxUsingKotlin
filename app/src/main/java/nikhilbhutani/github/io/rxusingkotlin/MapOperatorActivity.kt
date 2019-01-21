package nikhilbhutani.github.io.rxusingkotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber

class MapOperatorActivity : AppCompatActivity() {

    var compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        getUserObservable()
            .map{it:User->
                var address = Address("")
               var user = User(it.name.toUpperCase(), "","", address)
                return@map user
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<User>{


                override fun onComplete() {
                    Log.i("MapOperator ", "onComplete, All Users are emitted")
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onNext(t: User) {
                    Log.i("MapOperator ", "onNext ${t.name}")
                }

                override fun onError(e: Throwable) {
                    Log.i("MapOperator ", "onError $e")

               }
            })

    }



    fun getUserObservable() : Observable<User> {

        var names : Array<String> = arrayOf("nikhil", "John", "Ankit")


       var users : ArrayList<User> = ArrayList()
        var address = Address("")
        for(name in names){

           val user = User(name, "niks@gmail.com", "M", address)
            users.add(user)
        }

        var observable = Observable.create<User> {

            for(user in users){
                it.onNext(user)
            }

            it.onComplete()

          }

        return observable
    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}