/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package machine


fun main() {
    val machine = Machine(Ingredients(1000,1000,100))
    val customerInterface = CustomerInterface(machine)

    //ask now:
    customerInterface.askNumberOfCoffeeCups()


}
