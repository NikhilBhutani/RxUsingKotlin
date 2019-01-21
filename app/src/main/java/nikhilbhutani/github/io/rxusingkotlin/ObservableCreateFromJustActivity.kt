package nikhilbhutani.github.io.rxusingkotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Observable
import java.util.*
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable




class ObservableCreateFromJustActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        observableCreateConcept()


        /**
         * This operator creates an Observable from set of items using an Iterable which means we can pass a
         * list or an array of items to the Observable and each item is emitted one at a time. Some of the examples of
         * the operators include fromCallable(), fromFuture(), fromIterable(), fromPublisher(), fromArray().
         */
        observableFromConcept()


        /**
         * Difference between Observable.from() and Observable.just() — For the same input, if you see the above code,
         * Observable.just() emits only once whereas Observable.from()emits n times
         */

        observableJustConcept()




    }


    fun observableCreateConcept(){

        val alphabets = listOf("A", "B", "C", "D")

        /*
         * Observable.create() -> We will need to call the
         * respective methods of the emitter such as onNext()
         * & onComplete() or onError()
         *
         * */
        val observable = Observable.create(object : ObservableOnSubscribe<String> {

            override fun subscribe(emitter: ObservableEmitter<String>) {

                try {

                    /*
                     * The emitter can be used to emit each list item
                     * to the subscriber.
                     *
                     * */
                    for (alphabet in alphabets) {
                        emitter.onNext(alphabet)
                    }
                    /*
                     * Once all the items in the list are emitted,
                     * we can call complete stating that no more items
                     * are to be emitted.
                     *
                     * */
                    emitter.onComplete()

                } catch (e: Exception) {

                    /*
                     * If an error occurs in the process,
                     * we can call error.
                     *
                     * */
                    emitter.onError(e)
                }


            }

        })

        val observer = object : Observer<Any> {
            override fun onSubscribe(d: Disposable) {
                Log.i("onSubscribe", " --- - - - - ")
            }

            override fun onNext(o: Any) {
                Log.i("onNext: $o", "--------")
            }

            override fun onError(e: Throwable) {
                Log.i("onError: " + e.message, " ")
            }

            override fun onComplete() {
                Log.i("onComplete", " ----")
            }
        }

        observable.subscribe(observer)

    }


    fun observableFromConcept(){

       Observable.fromArray("A", "B", "C", "D", "E")
           .subscribe(object : Observer<String> {
               override fun onComplete() {
                   Log.i("onComplete", " ObservableFrom")

               }

               override fun onSubscribe(d: Disposable) {
                   Log.i("onSubscribe", " ObservableFrom")

               }

               override fun onNext(t: String) {
                   Log.i("onNext", " ObservableFrom $t")
               }

               override fun onError(e: Throwable) {
                   Log.i("onError: " + e.message, " ObservableFrom")

               }
           })
    }

    fun observableJustConcept(){

        Observable.just(arrayOf("A", "B", "C", "D", "E"))
            .subscribe(object : Observer<Array<String>> {

                override fun onComplete() {
                    Log.i("onComplete", " ObservableJUST")

                }

                override fun onSubscribe(d: Disposable) {
                    Log.i("onSubscribe", " ObservableJUST")

                }

                override fun onNext(t: Array<String>) {
                    Log.i("onNext", " ObservableJUST ${Arrays.toString(t)}")
                }

                override fun onError(e: Throwable) {
                    Log.i("onError: " + e.message, " ObservableJUST")

                }
            })



    }

}