package machine

enum class CoffeeType {
   ESPRESSO, LATTE, CAPPUCCINO
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
    fun buyCoffee(coffeeType: CoffeeType):Int{
        /** Check the capacity and buy */
        val hasCapacity = checkCapacity(coffeeType)
        val hasCups = cups > 0
        if(!hasCups) println("Sorry not enough cups!")
        return if(hasCapacity && hasCups) {
            println("I have enough resources, making you a coffee!")
            executeBuyTransaction(coffeeType)
            0
        } else -1

    }
    //check if it is possible to give coffee
    private fun checkCapacity(coffeeType: CoffeeType):Boolean {
        return when (coffeeType) {
            CoffeeType.ESPRESSO -> compareInventory(espresso.ingredients)
            CoffeeType.LATTE-> compareInventory(latte.ingredients)
            CoffeeType.CAPPUCCINO -> compareInventory(cappuccino.ingredients)
        }
    }
    //Compare the existing inventory with the requirements
    private fun compareInventory(requirements:Ingredients):Boolean{
        val comparison = mutableListOf(0,0,0)
        if(inventory.water > requirements.water ) {
            comparison[0] = 1
        } else println("Sorry not enough water!")
        if(inventory.milk > requirements.milk) comparison[1] = 1
        else println("Sorry not enough milk!")
        if(inventory.coffee_beans > requirements.coffee_beans) comparison[2] = 1
        else println("Sorry not enough coffee beans!")

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
fun checkIntegerInput():Int { /**helper function to get integer from command line */
    return try{
        val input = readln().toInt()
        if(input < 0) -1 else input
    }
    catch (e:Exception){
        -1
    }
}



class Services(private val machine: Machine){

    fun fillIngredients(){
        /** this will initialize the machine given the input from customer */
        var out = -1
        while(out < 0 ){
            println("Write how many ml of water you want to add:")
            out = checkIntegerInput()
            if(out == -1)println("Invalid input. Please try again or enter 0")
        }
        val waterToAdd= out
        out = -1
        while(out < 0){
            println("Write how many ml of milk you want to add:")
            out = checkIntegerInput()
            if(out == -1)println("Invalid input. Please try again or enter 0.")
        }
        val milkToAdd = out
        out = -1
        while(out < 0){
            println("Write how many grams of coffee beans you want to add:")
            out = checkIntegerInput()
            if(out == -1)println("Invalid input. Please try again or enter 0.")
        }
        val coffeeBeansToAdd = out
        out = -1
        while(out < 0){
            println("Write how many disposable cups you want to add: ")
            out = checkIntegerInput()
            if(out == -1)println("Invalid input. Please try again or enter 0.")
        }
        val cupsToAdd = out

        machine.refill(Ingredients(waterToAdd,milkToAdd,coffeeBeansToAdd), cupsToAdd)
    }

    fun buyCoffee(): Int{
        /**Buy coffee and update the machine */
        var rightSelection = false
        var status:Int = -1
        while(!rightSelection) {
            println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:")
            status = when (readln()) {
                "1" -> {
                    rightSelection = true
                    machine.buyCoffee(CoffeeType.ESPRESSO)
                }
                "2" -> {
                    rightSelection = true
                    machine.buyCoffee(CoffeeType.LATTE)
                }
                "3" -> {
                    rightSelection = true
                    machine.buyCoffee(CoffeeType.CAPPUCCINO)
                }
                "back" -> {rightSelection = true
                    -1
                }
                else -> {
                    rightSelection = false
                    println("Unknown selection. Please try again.")
                    -1
                }
            }
        }
        return status

    }

    fun checkRemaining(){
        machine.giveStatusReport()
    }

    fun takeEarnings(){
        println("I gave you ${machine.earnings}")
        machine.earnings = 0
    }

}



