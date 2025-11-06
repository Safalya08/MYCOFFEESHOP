package com.example.mycoffeeshop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OrderHistoryAdapter(private val orderList: List<Order>) :
    RecyclerView.Adapter<OrderHistoryAdapter.OrderViewHolder>() {

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val orderText: TextView = view.findViewById(R.id.orderText)
        val orderDate: TextView = view.findViewById(R.id.orderDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]

        holder.orderText.text = "${order.itemName} • ${order.price}"
        holder.orderDate.text = "🕒 ${order.dateTime}"
    }

    override fun getItemCount(): Int = orderList.size
}
