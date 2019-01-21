package nikhilbhutani.github.io.rxusingkotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class FlatMapOperatorActivity : AppCompatActivity() {

    var compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        getUserObservable()
            .flatMap{it:User->
//                var address = Address("")
//                var user = User(it.name.toUpperCase(), "","", address)
                return@flatMap getAddressObservable(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<User> {

                override fun onComplete() {
                    Log.i("FlatMapOperator ", "onComplete, All Users with Addresses are emitted")
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onNext(t: User) {
                    Log.i("FlatMapOperator ", "onNext ${t.name} with Address - ${t.address.addressStr}")
                }

                override fun onError(e: Throwable) {
                    Log.i("FlatMapOperator ", "onError $e")

                }
            })

    }



    fun getUserObservable() : Observable<User> {

        var names : Array<String> = arrayOf("nikhil", "John", "Ankit", "abc", "xyz", "Flana", "Dhimkana")


        var users : ArrayList<User> = ArrayList()
        var address = Address("")
        for(name in names){
            val user = User(name, "niks@gmail.com", "M", address)
            users.add(user)
        }

        var observable = Observable.create<User> {

            for(user in users){
               if(!it.isDisposed) {
                   it.onNext(user)
               }
            }
            if(!it.isDisposed) {
                it.onComplete()
            }
        }

        return observable
    }


    fun getAddressObservable(user: User) : Observable<User>{

       var names : Array<String> = arrayOf("1600 Amphitheatre Parkway, Mountain View, CA 94043",
           "2300 Traverwood Dr. Ann Arbor, MI 48105",
           "500 W 2nd St Suite 2900 Austin, TX 78701",
           "355 Main Street Cambridge, MA 02142")



        var observable = Observable.create<User>{

            var address = Address(names.random().toString())
            if(!it.isDisposed) {
                user.address = address
                it.onNext(user)
                it.onComplete()
            }
        }

        return observable
    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }


}