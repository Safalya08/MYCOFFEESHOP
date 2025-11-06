package com.example.mycoffeeshop

data class Order(
    val itemName: String,
    val price: String,
    val dateTime: String,
    var status: String = "Confirmed" // 👈 Added field for tracking cancellation
)
