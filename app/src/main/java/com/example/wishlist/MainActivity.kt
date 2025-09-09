package com.example.wishlist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: WishlistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val itemEntry = findViewById<EditText>(R.id.itemEntry)
        val priceEntry = findViewById<EditText>(R.id.priceEntry)
        val storeEntry = findViewById<EditText>(R.id.storeEntry)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        val recyclerView = findViewById<RecyclerView>(R.id.Wishlistrv)

        adapter = WishlistAdapter(mutableListOf()) { rawUrl ->
            val finalUrl =
                if (rawUrl.startsWith("http://") || rawUrl.startsWith("https://")) rawUrl
                else "https://$rawUrl"

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(finalUrl))
            if (intent.resolveActivity(packageManager) != null) startActivity(intent)
            else Toast.makeText(this, "No app to open the URL", Toast.LENGTH_SHORT).show()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        btnSubmit.setOnClickListener {
            val name = itemEntry.text.toString().trim()
            val priceText = priceEntry.text.toString().trim()
            val url = storeEntry.text.toString().trim()

            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter an item name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val price = priceText.toDoubleOrNull()
            if (price == null) {
                Toast.makeText(this, "Enter a valid price", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (url.isEmpty()) {
                Toast.makeText(this, "Please enter a store URL", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val item = WishlistItem(store = "", name = name, price = price, url = url)
            adapter.addItem(item)

            itemEntry.text.clear()
            priceEntry.text.clear()
            storeEntry.text.clear()
            recyclerView.scrollToPosition(adapter.itemCount - 1)
        }
    }
}
