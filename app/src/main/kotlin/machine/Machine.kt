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
class Machine(val inventory: Ingredients) {
    /** This machine has a coffee formula of :
     * 200 ml water, 50 ml milk and 15 g of coffee beans
     */
    //below is the number of coffees served by this machine
    var coffeeServed: Int = 0
}

class CustomerInterface(val machine: Machine){

    fun askNumberOfCoffeeCups(){
        println("Write how many cups of coffee you will need:")
        var numberOfCups = 0
        try{
            numberOfCups = readln().toInt()
        }
        catch (e:Exception){
            println("Please input only numbers.")
            exitProcess(0)
        }
        val numberOfIngredients = calculateIngredients(numberOfCups)
        println("For $numberOfCups cups of coffee you will need:\n" +
                "${numberOfIngredients.water} ml of water\n" +
                "${numberOfIngredients.milk} ml of milk\n" +
                "${numberOfIngredients.coffee_beans} g of coffee beans")



    }

    private fun calculateIngredients(numberOfCups: Int): Ingredients{
        return Ingredients(water = numberOfCups * WATER,
            milk = numberOfCups * MILK,
            coffee_beans = numberOfCups * COFFEE_BEANS
        )

    }
}
