/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package machine

fun main() { //initial machine
    val machine = Machine(Ingredients(water = 400, milk = 540, coffee_beans = 120),
        earnings = 550, cups = 9)
    val services = Services(machine)
    Menu(services).execute()

}

class Menu(private val services: Services){
    fun execute(){
        var status = true
        while(status){
            println("\nWrite action (buy, fill, take, remaining, exit):")
            when(readln()){
                "buy"  -> services.buyCoffee()
                "fill" -> services.fillIngredients()
                "take" -> services.takeEarnings()
                "remaining" -> services.checkRemaining()
                "exit"-> status = false
                else -> println("Unknown input. Please try again.")
            }

       }

    }


}
