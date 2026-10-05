fun main() {
    val morningNotification = 51
    val eveningNotification = 135

    printNotificationSummary(morningNotification)
    printNotificationSummary(eveningNotification)
}


fun printNotificationSummary(numberOfMessages: Int) {
    val message = if(numberOfMessages > 100) "You have 100+ messages" else "You have $numberOfMessages messages"
    println(message)
}
