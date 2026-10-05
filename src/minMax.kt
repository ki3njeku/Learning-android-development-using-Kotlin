fun main(){
    val numbers  = arrayOf(2,3,5,7,11)
    println("The first number is ${numbers[0]}")
    findPrimes()
}
fun findMin(numbers: Array<Int>): Int{
    var min: Int = numbers[0]
    for(number in numbers){
        if(number < min){
            min = number
        }
    }
    return min
}

fun findMax(numbers: Array<Int>): Int{
    var max: Int = numbers[0]
    for(number in numbers){
        if(number > max){
            max = number
        }
    }
    return max
}

fun findPrimes(){
    val primes = mutableListOf(2,3)
    val primesToBeFound = 20
    var magic = 6
    while(primes.size < primesToBeFound){
        val lower = magic - 1
        val upper = magic + 1
        var lowerIsPrime: Boolean = true
        var upperIsPrime: Boolean = true
        for(prime in primes) {
            if (lower % prime == 0) {
                lowerIsPrime = false
                break
            }
        }
        if(lowerIsPrime){
            primes.add(lower)
        }
        for(prime in primes){
            if(upper % prime == 0){
                upperIsPrime = false
                break
            }
        }
        if(upperIsPrime){
            primes.add(upper)
        }
        magic += 6
    }
    for(prime in primes){
        println("$prime is prime")
    }
}