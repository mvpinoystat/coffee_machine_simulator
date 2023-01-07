package machine

import kotlin.system.exitProcess

const val WATER = 200
const val MILK = 50
const val COFFEE_BEANS = 15

data class Ingredients(
    var water: Int,
    var milk : Int,
    var coffee_beans: Int
)
class Machine(private val inventory: Ingredients) {
    /** This machine has a coffee formula of :
     * 200 ml water, 50 ml milk and 15 g of coffee beans
     */
    var machineCapacity:Int = currentMachineCapacity()

    //below is the number of coffees served by this machine
    var coffeeServed: Int = 0

    private fun currentMachineCapacity():Int{
       /** get the capacity of the machien in number of cups given ingredients */
        val cupsGivenWater = inventory.water / WATER
        val cupsGivenMilk = inventory.milk / MILK
        val cupsGivenCoffeeBeans = inventory.coffee_beans / COFFEE_BEANS
        //the maximum number of cups is the minimum of the 3 variables above (water, milk , coffee beans)
        var maxCups = 0
        maxCups = if(cupsGivenWater < cupsGivenMilk) cupsGivenWater else cupsGivenMilk
        if(maxCups > cupsGivenCoffeeBeans) maxCups = cupsGivenCoffeeBeans

        return  maxCups
    }
}

fun inputIntegers():Int{
    /**helper function to get integer from command line */
    return try{
        readln().toInt()
    }
    catch (e:Exception){
        println("Please input only numbers.")
        exitProcess(0)
    }
}

class CustomerInterface{
    var machine: Machine? = null

    fun initializeMachine(){
        /** this will initialize the machine given the input from customer */
        println("Write how many ml of water the coffee machine has:")
        val initialWater = inputIntegers()
        println("Write how many ml of milk the coffee machine has:")
        val initialMilk = inputIntegers()
        println("Write how many grams of coffee beans the coffee machine has:")
        val initialCoffeeBeans = inputIntegers()

        //initialize the coffee machine:
        machine = Machine(Ingredients(initialWater,initialMilk,initialCoffeeBeans))
    }

    fun checkCupsRequirements() {
        println("Write how many cups of coffee you will need:")
        val numberOfCups = inputIntegers()

        //check if the machine can accommodate the order:
        machine?.apply {
         when{
             machineCapacity > numberOfCups -> {println("Yes, I can make that amount of coffee " +
                     "(and even ${machineCapacity - numberOfCups } more than that)")}
             machineCapacity < numberOfCups -> {println("No, I can make only $machineCapacity cups of coffee")}
             else -> { println("Yes, I can make that amount of coffee") }
         }
        }


    }

    private fun calculateIngredients(numberOfCups: Int): Ingredients{
        return Ingredients(water = numberOfCups * WATER,
            milk = numberOfCups * MILK,
            coffee_beans = numberOfCups * COFFEE_BEANS
        )

    }
}
