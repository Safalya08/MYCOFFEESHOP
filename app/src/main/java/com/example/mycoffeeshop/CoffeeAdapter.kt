package com.example.mycoffeeshop

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CoffeeAdapter(private val coffeeList: List<Coffee>) :
    RecyclerView.Adapter<CoffeeAdapter.CoffeeViewHolder>() {

    class CoffeeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val coffeeImage: ImageView = view.findViewById(R.id.coffeeImage)
        val coffeeName: TextView = view.findViewById(R.id.coffeeName)
        val coffeeDescription: TextView = view.findViewById(R.id.coffeeDescription)
        val orderButton: Button = view.findViewById(R.id.btnOrder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_coffee, parent, false)
        return CoffeeViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoffeeViewHolder, position: Int) {
        val coffee = coffeeList[position]
        val context = holder.itemView.context

        holder.coffeeImage.setImageResource(coffee.image)
        holder.coffeeName.text = coffee.name
        holder.coffeeDescription.text = coffee.description

        // ✅ Button click to open OrderDetailActivity
        holder.orderButton.setOnClickListener {
            val intent = Intent(context, OrderDetailActivity::class.java)
            intent.putExtra("name", coffee.name)
            intent.putExtra("description", coffee.description)
            intent.putExtra("image", coffee.image)
            intent.putExtra("price", coffee.price)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = coffeeList.size
}
