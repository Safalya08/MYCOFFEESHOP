package com.example.mycoffeeshop

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PaymentProcessingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_processing)

        val coffeeName = intent.getStringExtra("name") ?: "Coffee"
        val coffeeTotal = intent.getStringExtra("total") ?: "₹0"
        val coffeeImage = intent.getIntExtra("image", R.drawable.coffee_1)

        val processingText = findViewById<TextView>(R.id.processingText)
        val loadingImage = findViewById<ImageView>(R.id.loadingImage)

        processingText.text = "Processing Payment..."


        val rotateAnim = AnimationUtils.loadAnimation(this, R.anim.rotate)
        loadingImage.startAnimation(rotateAnim)


        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, OrderSuccessActivity::class.java)
            intent.putExtra("name", coffeeName)
            intent.putExtra("total", coffeeTotal)
            intent.putExtra("image", coffeeImage)
            startActivity(intent)
            finish()
        }, 3000)
    }
}
