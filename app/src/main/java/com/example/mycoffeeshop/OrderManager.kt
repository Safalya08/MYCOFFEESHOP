package com.example.mycoffeeshop

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class OrderManager(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("orders", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveOrder(order: Order) {
        val currentList = getOrders().toMutableList()
        currentList.add(order)
        val json = gson.toJson(currentList)
        sharedPreferences.edit().putString("order_list", json).apply()
    }

    fun getOrders(): List<Order> {
        val json = sharedPreferences.getString("order_list", null)
        return if (json != null) {
            val type = object : TypeToken<List<Order>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    // 🆕 New: Cancel Order
    fun cancelOrder(orderName: String) {
        val currentList = getOrders().toMutableList()
        val updatedList = currentList.map { order ->
            if (order.itemName == orderName && order.status != "Cancelled") {
                order.copy(status = "Cancelled")
            } else {
                order
            }
        }

        val json = gson.toJson(updatedList)
        sharedPreferences.edit().putString("order_list", json).apply()
    }


}
