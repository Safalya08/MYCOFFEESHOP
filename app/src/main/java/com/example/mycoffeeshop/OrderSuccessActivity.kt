package com.example.mycoffeeshop

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.mycoffeeshop.databinding.ActivityOrderSuccessBinding

class OrderSuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

    
        val name = intent.getStringExtra("name") ?: "Coffee"
        val total = intent.getStringExtra("total") ?: "₹0"
        val image = intent.getIntExtra("image", R.drawable.coffee_1)

        
        binding.successCoffeeImage.setImageResource(image)
        binding.successCoffeeName.text = name
        binding.successCoffeePrice.text = "Total Paid: $total"


        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, CoffeeListActivity::class.java))
            finish()
        }, 3000)
    }
}
