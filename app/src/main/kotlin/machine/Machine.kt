package machine

import kotlin.system.exitProcess

enum class CoffeeType {
   ESPRESSO, LATTE, CAPPUCCINO
}
enum class ACTION {
    BUY, FILL, TAKE, LOOP
}
data class Ingredients(
    var water: Int,
    var milk : Int,
    var coffee_beans: Int
)

data class Coffee(
   var ingredients: Ingredients,
   var price:Int
)

class Machine(private val inventory: Ingredients,
              var earnings:Int,
              var cups:Int) {
    /**We now design 3 types of coffee with different ingredients: */

    private val espresso: Coffee = Coffee(Ingredients(250, 0, 16),4)
    private val latte: Coffee = Coffee(Ingredients(350, 75, 20),7)
    private val cappuccino: Coffee = Coffee(Ingredients(200, 100, 12),6)


    // a menu action
    fun refill(ingredients: Ingredients, cupsToAdd:Int){
        inventory.water += ingredients.water
        inventory.milk += ingredients.milk
        inventory.coffee_beans += ingredients.coffee_beans
        cups += cupsToAdd

    }
    //a menu action
    fun buyCoffee(coffeeType: CoffeeType){
        /** Check the capacity and buy */
        val hasCapacity = checkCapacity(coffeeType)
        val hasCups = cups > 0
        if(hasCapacity && hasCups) executeBuyTransaction(coffeeType)

    }
    //check if it is possible to give coffee
    private fun checkCapacity(coffeeType: CoffeeType):Boolean {
        return when (coffeeType) {
            CoffeeType.ESPRESSO -> compareInventory(espresso.ingredients)
            CoffeeType.LATTE-> compareInventory(latte.ingredients)
            CoffeeType.CAPPUCCINO -> compareInventory(cappuccino.ingredients)
        }
    }
    //compare inventory
    private fun compareInventory(requirements:Ingredients):Boolean{
        val comparison = mutableListOf(0,0,0)
        if(inventory.water > requirements.water ) comparison[0] = 1
        if(inventory.milk > requirements.milk) comparison[1] = 1
        if(inventory.coffee_beans > requirements.coffee_beans) comparison[2] = 1
       // println("total sum = ${comparison}")
        return comparison.sum() == 3

    }

    //subtract inventory
    private fun subtractInventory(requirements:Ingredients){
        inventory.water -= requirements.water
        inventory.milk -= requirements.milk
        inventory.coffee_beans -= requirements.coffee_beans

    }
    //execute buy coffee if there is enough ingredients
    private fun executeBuyTransaction(coffeeType: CoffeeType){
        when (coffeeType) {
            CoffeeType.ESPRESSO -> {
                subtractInventory(this.espresso.ingredients)
                earnings += espresso.price
                cups--

            }
            CoffeeType.LATTE-> {
                subtractInventory(latte.ingredients)
                earnings += latte.price
                cups--
            }
            CoffeeType.CAPPUCCINO -> {
                subtractInventory(cappuccino.ingredients)
                earnings += cappuccino.price
                cups--
            }
        }

    }

    fun giveStatusReport(){
        println("\nThe coffee machine has:\n" +
                "${inventory.water} ml of water\n" +
                "${inventory.milk} ml of milk\n" +
                "${inventory.coffee_beans} g of coffee beans\n" +
                "$cups disposable cups\n" +
                "\$$earnings of money")
    }

}
//helper functions:
fun checkInputCoffeeType():Int{
    /**helper function to get integer from command line */

    return try{
        readln().toInt()
    }
    catch (e:Exception){
        println("Please input only numbers.")
        exitProcess(0)
    }
}

fun inputAction():ACTION{
    /**helper function to get action from command line */
    return when(readln()){
        "buy" -> ACTION.BUY
        "fill" -> ACTION.FILL
        "take" -> ACTION.TAKE
        else -> ACTION.LOOP
        }
}

class Services(val machine: Machine){
    init{
        machine.giveStatusReport()
    }

    fun fillIngredients(){
        /** this will initialize the machine given the input from customer */
        println("Write how many ml of water the coffee machine has:")
        val waterToAdd= checkInputCoffeeType()
        println("Write how many ml of milk the coffee machine has:")
        val milkToAdd = checkInputCoffeeType()
        println("Write how many grams of coffee beans the coffee machine has:")
        val coffeeBeansToAdd = checkInputCoffeeType()
        println("Write how many disposable cups you want to add: ")
        val cupsToAdd = checkInputCoffeeType()
        machine.refill(Ingredients(waterToAdd,milkToAdd,coffeeBeansToAdd), cupsToAdd)
        //show status
        machine.giveStatusReport()
    }

    fun buyCoffee(){
        /**Buy coffee and update the machine */
        println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:")
        when(checkInputCoffeeType()){
            1 -> machine.buyCoffee(CoffeeType.ESPRESSO)
            2 -> machine.buyCoffee(CoffeeType.LATTE)
            3 -> machine.buyCoffee(CoffeeType.CAPPUCCINO)
        }

        machine.giveStatusReport()
    }

    fun take(){
        println("I gave you ${machine.earnings}")
        machine.earnings = 0
        machine.giveStatusReport()
    }

}



