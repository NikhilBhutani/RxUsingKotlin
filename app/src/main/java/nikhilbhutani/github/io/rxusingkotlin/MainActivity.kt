//package nikhilbhutani.github.io.rxusingkotlin
//
//import android.support.v7.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Log
//import io.reactivex.Observable
//import io.reactivex.schedulers.Schedulers
//import io.reactivex.internal.disposables.DisposableHelper.isDisposed
//import io.reactivex.ObservableEmitter
//import io.reactivex.ObservableOnSubscribe
//import io.reactivex.Observer
//import io.reactivex.disposables.Disposable
//import io.reactivex.android.schedulers.AndroidSchedulers
//
//
//
//class MainActivity : AppCompatActivity() {
//
//    private var disposable: Disposable? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        getUsersObservable()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .map(object : Function<User, User>() {
//                @Throws(Exception::class)
//                fun apply(user: User): User {
//                    // modifying user object by adding email address
//                    // turning user name to uppercase
//                    user.email = String.format("%s@rxjava.wtf", user.name)
//                    user.name = user.name.toUpperCase()
//                    return user
//                }
//            })
//            .subscribe(object : Observer<User> {
//
//
//
//                fun onSubscribe(d: Disposable) {
//                    disposable = d
//                }
//
//                fun onNext(user: User) {
////                    Log.e(
////                        FragmentActivity.TAG,
////                        "onNext: " + user.name + ", " + user.gender + ", " + user.getAddress().getAddress()
////                    )
//                }
//
//                fun onError(e: Throwable) {
//            //        Log.e(FragmentActivity.TAG, "onError: " + e.message)
//                }
//
//                fun onComplete() {
//              //      Log.e(FragmentActivity.TAG, "All users emitted!")
//                }
//            })
//    }
//
//    /**
//     * Assume this method is making a network call and fetching Users
//     * an Observable that emits list of users
//     * each User has name and email, but missing email id
//     */
//    private fun getUsersObservable(): Observable<User> {
//        val names = arrayOf("mark", "john", "trump", "obama")
//
//        val users = ArrayList()
//        for (name in names) {
//            val user = User(name, "email", "gender")
//            user.name = name
//            user.gender = "male"
//
//            users.add(user)
//        }
//        return Observable
//            .create(ObservableOnSubscribe<User> { emitter ->
//                for (user in users) {
//                    if (!emitter.isDisposed) {
//                        emitter.onNext(user)
//                    }
//                }
//
//                if (!emitter.isDisposed) {
//                    emitter.onComplete()
//                }
//            }).subscribeOn(Schedulers.io())
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        disposable!!.dispose()
//    }
//}
