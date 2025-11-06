package com.example.mycoffeeshop

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.MaterialToolbar

class TopAppBar : AppCompatActivity() {

    private val itemList = listOf(
        "Cappuccino",
        "Espresso",
        "Latte",
        "Cold Coffee",
        "Mocha",
        "Black Coffee"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.top_app_bar)

        val toolbar = findViewById<MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)

        toolbar.title = "☕ Coffee Shop"
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white))
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar_menu, menu)

        // 🎯 Handle SearchView
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as? SearchView

        searchView?.queryHint = "Search your coffee..."

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchItem.collapseActionView()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filtered = itemList.filter { it.contains(newText ?: "", ignoreCase = true) }

                if (newText.isNullOrEmpty()) return true

                if (filtered.isEmpty()) {
                    Toast.makeText(this@TopAppBar, "Item not found", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        this@TopAppBar,
                        "Found: ${filtered.joinToString()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return true
            }
        })

        // Tint icons to cream
        val creamColor = ContextCompat.getColor(this, R.color.cream)
        for (i in 0 until (menu?.size() ?: 0)) {
            val item = menu!!.getItem(i)
            item.icon?.setTint(creamColor)
        }

        return true
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        when (item.itemId) {

            R.id.action_history -> {
                // 🧾 Open OrderHistoryActivity
                val intent = Intent(this, OrderHistoryActivity::class.java)
                startActivity(intent)
            }

            R.id.action_logout -> {
                // 🚪 Navigate to LoginActivity
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
