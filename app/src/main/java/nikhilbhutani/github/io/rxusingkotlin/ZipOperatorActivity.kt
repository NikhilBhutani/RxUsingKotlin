package nikhilbhutani.github.io.rxusingkotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class ZipOperatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_main)


        sequentialExecutionOfObservablesZip()

        parallelExecutionOfObservablesZip()


    }


    private fun sequentialExecutionOfObservablesZip() {
        val source1 = Flowable.fromCallable {
            for (i in 1..3) {
                Thread.sleep(1000)
                println("Source 1 emitted : " + i + " " +    Thread.currentThread())
            }
            "source 1 Completed"
        }

        val source2 = Flowable.fromCallable {
            for (i in 1..3) {
                Thread.sleep(1000)
                println("Source 2 emitted : " + i + " " + Thread.currentThread())
            }
            "source 2 completed"
        }

        Flowable.zip(source1, source2, BiFunction<String, String, Pair<String, String>>({ t1, t2 -> Pair(t1, t2) }))
            .subscribeOn(Schedulers.io())
            .subscribe({
                println("Combined Result " + Thread.currentThread())
            })

    }


    private fun parallelExecutionOfObservablesZip() {
        val source1 = Flowable.fromCallable {
            for (i in 1..3) {
                Thread.sleep(1000)
                println("Source 1 emitted : " + i + " " +    Thread.currentThread())
            }
            "source 1 Completed"
        }.subscribeOn(Schedulers.newThread())

        val source2 = Flowable.fromCallable {
            for (i in 1..3) {
                Thread.sleep(1000)
                println("Source 2 emitted : " + i + " " + Thread.currentThread())
            }
            "source 2 completed"
        }.subscribeOn(Schedulers.newThread())

        Flowable.zip(source1, source2, BiFunction<String, String, Pair<String, String>>({ t1, t2 -> Pair(t1, t2) }))
            .subscribeOn(Schedulers.io())
            .subscribe({
                println("Combined Result " + Thread.currentThread())
            })
    }
}