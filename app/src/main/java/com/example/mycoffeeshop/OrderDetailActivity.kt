package com.example.mycoffeeshop

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mycoffeeshop.databinding.ActivityOrderDetailBinding
import java.text.SimpleDateFormat
import java.util.*

class OrderDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderDetailBinding
    private var quantity = 1
    private var basePrice = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val coffeeName = intent.getStringExtra("name") ?: "Coffee"
        val coffeeDescription = intent.getStringExtra("description") ?: "Delicious coffee"
        val coffeeImage = intent.getIntExtra("image", R.drawable.ic_launcher_foreground)
        val coffeePrice = intent.getStringExtra("price") ?: "₹120"

        basePrice = coffeePrice.replace("₹", "").toIntOrNull() ?: 120

        binding.coffeeImage.setImageResource(coffeeImage)
        binding.coffeeName.text = coffeeName
        binding.coffeeDescription.text = coffeeDescription
        binding.coffeePrice.text = "Price: ₹$basePrice"
        binding.quantityText.text = quantity.toString()
        updateTotal()

        binding.btnAdd.setOnClickListener {
            quantity++
            binding.quantityText.text = quantity.toString()
            updateTotal()
        }

        binding.btnMinus.setOnClickListener {
            if (quantity > 1) quantity--
            binding.quantityText.text = quantity.toString()
            updateTotal()
        }

        binding.btnProceed.setOnClickListener {
            val total = "₹${basePrice * quantity}"

            val dateFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
            val formattedDate = dateFormat.format(Date())

            val order = Order(coffeeName, total, formattedDate)
            OrderManager(this).saveOrder(order)

            Toast.makeText(this, "Order saved: $formattedDate", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, DeliveryDetailsActivity::class.java)
            intent.putExtra("name", coffeeName)
            intent.putExtra("image", coffeeImage)
            intent.putExtra("total", total)
            startActivity(intent)
        }

 
        binding.btnCancelOrder.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Cancel Order")
                .setMessage("Are you sure you want to cancel this order?")
                .setPositiveButton("Yes") { _, _ ->
                    cancelOrder()
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

    private fun updateTotal() {
        val total = basePrice * quantity
        binding.totalPrice.text = "Total: ₹$total"
    }

    private fun cancelOrder() {
        val coffeeName = binding.coffeeName.text.toString()
        OrderManager(this).cancelOrder(coffeeName)

        Toast.makeText(this, "Order cancelled successfully", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, OrderHistoryActivity::class.java)
        startActivity(intent)
        finish()
    }

}

