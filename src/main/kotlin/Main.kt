import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.channels.*

fun main(args: Array<String>) = runBlocking<Unit> {
    var cur = numbersFrom(2)
    repeat(100) {
        val prime = cur.receive()
        println(prime)
        cur = filter(cur, prime)
    }
    coroutineContext.cancelChildren() // cancel all children to let main finish
}

fun CoroutineScope.numbersFrom(start: Int) = produce {
    for (x in start..Int.MAX_VALUE) send(x)
}

fun CoroutineScope.filter(numbers: ReceiveChannel<Int>, prime: Int) = produce {
    for (x in numbers) if (x % prime != 0) send(x)
}
