package com.example.mycoffeeshop

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar

class CoffeeListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CoffeeAdapter
    private lateinit var coffeeList: ArrayList<Coffee>
    private lateinit var filteredList: ArrayList<Coffee>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coffee_list)

        // ☕ Toolbar setup
        val toolbar = findViewById<MaterialToolbar>(R.id.topAppBar)
        toolbar.inflateMenu(R.menu.top_app_bar_menu)
        toolbar.overflowIcon?.setTint(ContextCompat.getColor(this, R.color.cream_white))
        toolbar.navigationIcon?.setTint(ContextCompat.getColor(this, R.color.cream_white))

        // ☕ Toolbar menu click handling
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_search -> true
                R.id.action_history -> {
                    Toast.makeText(this, "Opening order history...", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, OrderHistoryActivity::class.java))
                    true
                }
                R.id.action_logout -> {
                    Toast.makeText(this, "Logged out successfully!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        // ☕ RecyclerView setup
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        // ✅ Your full coffee list (coffee_1 to coffee_11)
        coffeeList = arrayListOf(
            Coffee("Espresso", R.drawable.coffee_1, "Strong & bold shot of coffee", "₹120"),
            Coffee("Cappuccino", R.drawable.coffee_2, "Espresso with steamed milk & foam", "₹150"),
            Coffee("Latte", R.drawable.coffee_3, "Smooth mix of milk and espresso", "₹140"),
            Coffee("Americano", R.drawable.coffee_4, "Espresso diluted with hot water", "₹110"),
            Coffee("Mocha", R.drawable.coffee_5, "Chocolate flavored coffee drink", "₹160"),
            Coffee("Macchiato", R.drawable.coffee_6, "Espresso topped with foamed milk", "₹130"),
            Coffee("Flat White", R.drawable.coffee_7, "Rich espresso with velvety milk", "₹145"),
            Coffee("Irish Coffee", R.drawable.coffee_8, "Coffee blended with Irish whiskey", "₹180"),
            Coffee("Iced Latte", R.drawable.coffee_9, "Chilled espresso with milk & ice", "₹155"),
            Coffee("Cold Brew", R.drawable.coffee_10, "Slow-brewed smooth coffee", "₹170"),
            Coffee("Affogato", R.drawable.coffee_11, "Espresso poured over vanilla ice cream", "₹190")
        )

        filteredList = ArrayList(coffeeList)
        adapter = CoffeeAdapter(filteredList)
        recyclerView.adapter = adapter

        // ☕ Setup SearchView and apply cream-white style
        val searchItem = toolbar.menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Search coffee..."

        // Customize SearchView colors
        val searchEditTextId = androidx.appcompat.R.id.search_src_text
        val searchEditText = searchView.findViewById<EditText>(searchEditTextId)
        searchEditText.setTextColor(ContextCompat.getColor(this, R.color.cream_white))
        searchEditText.setHintTextColor(ContextCompat.getColor(this, R.color.cream_white))

        val magId = androidx.appcompat.R.id.search_mag_icon
        val magImage = searchView.findViewById<ImageView>(magId)
        magImage.setColorFilter(ContextCompat.getColor(this, R.color.cream_white))

        val closeId = androidx.appcompat.R.id.search_close_btn
        val closeImage = searchView.findViewById<ImageView>(closeId)
        closeImage.setColorFilter(ContextCompat.getColor(this, R.color.cream_white))

        // 🔍 Filter coffee list dynamically
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                filterCoffeeList(newText)
                return true
            }
        })
    }

    private fun filterCoffeeList(query: String?) {
        val searchText = query?.lowercase()?.trim() ?: ""
        filteredList.clear()

        if (searchText.isEmpty()) {
            filteredList.addAll(coffeeList)
        } else {
            filteredList.addAll(
                coffeeList.filter { it.name.lowercase().contains(searchText) }
            )
        }

        adapter.notifyDataSetChanged()
    }
}
