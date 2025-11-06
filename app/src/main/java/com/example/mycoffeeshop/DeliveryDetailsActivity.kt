package com.example.mycoffeeshop

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DeliveryDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_details)


        val coffeeName = intent.getStringExtra("name") ?: "Coffee"
        val coffeeImage = intent.getIntExtra("image", R.drawable.coffee_1)
        val coffeeTotal = intent.getStringExtra("total") ?: "₹0"


        val orderImage = findViewById<ImageView>(R.id.orderImage)
        val orderName = findViewById<TextView>(R.id.orderName)
        val orderPrice = findViewById<TextView>(R.id.orderPrice)
        val fullNameEditText = findViewById<EditText>(R.id.fullNameEditText)
        val addressEditText = findViewById<EditText>(R.id.addressEditText)
        val phoneEditText = findViewById<EditText>(R.id.phoneEditText)
        val confirmOrderButton = findViewById<Button>(R.id.confirmOrderButton)

        orderImage.setImageResource(coffeeImage)
        orderName.text = coffeeName
        orderPrice.text = "Total: $coffeeTotal"


        confirmOrderButton.setOnClickListener {
            val name = fullNameEditText.text.toString().trim()
            val address = addressEditText.text.toString().trim()
            val phone = phoneEditText.text.toString().trim()

            if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Processing payment...", Toast.LENGTH_SHORT).show()


            confirmOrderButton.postDelayed({
                val intent = Intent(this, PaymentProcessingActivity::class.java)
                intent.putExtra("name", coffeeName)
                intent.putExtra("total", coffeeTotal)
                intent.putExtra("image", coffeeImage)
                startActivity(intent)
                finish()
            }, 2000)


        }
    }
}
