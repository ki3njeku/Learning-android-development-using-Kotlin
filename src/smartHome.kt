import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.random.Random

private val thermostat = Thermostat("D001", "Central thermostat", 20, 0.05)
private val fan = Fan("D002", "Central fan", 50, 0.2, 5, 1000.0)
private val ac = ACUnit("D003", "Central AC unit", 150, 0.1, 30, 500.0)
private val ducted = DuctedAC("D004", "Central ducted AC", 500, 0.05, 50, 200.0)
private val radiator = Radiator("D005", "Portable radiator", 3000, 0.1, 30, 200.0)
private val centralHeating = CentralHeating("D006", "Central heating system", 5000, 0.05, 70, 100.0)
private var powerUsed: Double = 0.0
private var ambientTemp: Double = 30.0

fun main() {
    thermostat.regulateTemp(ambientTemp, 26.3)
}

fun plusOrMinus(constant:Double, value: Double): Double{
    val amountToChange = (Random.nextDouble(-constant,constant) *1000.0).roundToInt() / 1000.0
    return value + amountToChange
}

abstract class Device(var deviceId: String, var deviceName: String, var powerConsumption: Int) {
    open fun identifyDevice(){
        println("My device id is: $deviceId")
    }
    fun calculatePowerUsage(timeUsed: Double){
        powerUsed += (powerConsumption * (timeUsed / 1000.0))
    }
}

class Thermostat(deviceId: String, deviceName: String, powerConsumption: Int, var accuracy: Double): Device(deviceId, deviceName, powerConsumption){
    override fun identifyDevice(){
        super.identifyDevice()
        println("The accuracy of this thermostat is: $accuracy")
    }
    fun regulateTemp(currentTemp: Double, requiredTemperature: Double){
        val delta = requiredTemperature - currentTemp
        val allowedError = accuracy * currentTemp
        if(abs(delta) > allowedError){
            if(delta < -30){
                ducted.changeTemperature(abs(delta))
            }else if( delta < -5){
                ac.changeTemperature(abs(delta))
            }else if(delta < 0){
                fan.changeTemperature(delta)
            }else if(delta < 30){
                radiator.changeTemperature(delta)
            }else{
                centralHeating.changeTemperature(delta)
            }
        }else {
            println("Temperature is within acceptable range")
        }
    }
}

abstract class TempControlDevice(deviceId: String, deviceName: String, powerConsumption: Int, var accuracy: Double, var maxDelta: Int, var rate: Double): Device(deviceId, deviceName, powerConsumption){
    override fun identifyDevice() {
        super.identifyDevice()
        println("The accuracy of this $deviceName is: $accuracy")
        println("This $deviceName can reduce the temperature by a maximum of $maxDelta degrees")
    }
    fun changeTemperature(amount: Double){
        if(abs(amount) < maxDelta) {
            val error = accuracy * amount
            val amountCorrect = plusOrMinus(error, amount)
            val delay = rate * amountCorrect
            Thread.sleep(delay.toLong())
            println("The $deviceName was on for $delay milliseconds and changed the temperature by $amountCorrect")
            calculatePowerUsage(delay)
            ambientTemp += amountCorrect
        }else {
            println("The delta is higher than this $deviceName is rated for")
        }
    }
}

class Fan (deviceId: String, deviceName: String, powerConsumption: Int, accuracy: Double, maxDelta: Int, rate: Double): TempControlDevice(deviceId, deviceName, powerConsumption, accuracy, maxDelta, rate)
class ACUnit (deviceId: String, deviceName: String, powerConsumption: Int, accuracy: Double, maxDelta: Int, rate: Double): TempControlDevice(deviceId, deviceName, powerConsumption, accuracy, maxDelta, rate)
class DuctedAC (deviceId: String, deviceName: String, powerConsumption: Int, accuracy: Double, maxDelta: Int, rate: Double): TempControlDevice(deviceId, deviceName, powerConsumption, accuracy, maxDelta, rate)
class Radiator(deviceId: String, deviceName: String, powerConsumption: Int, accuracy: Double, maxDelta: Int, rate: Double): TempControlDevice(deviceId, deviceName, powerConsumption, accuracy, maxDelta, rate)
class CentralHeating(deviceId: String, deviceName: String, powerConsumption: Int, accuracy: Double, maxDelta: Int, rate: Double): TempControlDevice(deviceId, deviceName, powerConsumption, accuracy, maxDelta, rate)