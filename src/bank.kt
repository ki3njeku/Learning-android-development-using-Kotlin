fun main(){
    val customer1 = Account()
    customer1.createAccount("James")
    customer1.deposit(1000,"T001")
    customer1.viewBalance()
    customer1.withdraw(600, "T002")
}

class Account{
    lateinit var userName: String
    var balance = 0
    var transactions = mutableListOf<String>("")
    fun createAccount(firstName: String){
        userName = firstName
        println("New account created for $userName")
    }
    fun deposit(amount: Int, code: String){
        if(amount > 0){
            balance += amount
            println("You have deposited $amount. New balance is $balance")
            transactions.add(code)
        }
    }
    fun withdraw(amount: Int, code: String){
        if(amount in 0..balance){
            balance -= amount
            println("You have withdrawn $amount. New balance is $balance")
            transactions.add(code)
        }
    }
    fun viewBalance(){
        println(this.balance)
    }
}