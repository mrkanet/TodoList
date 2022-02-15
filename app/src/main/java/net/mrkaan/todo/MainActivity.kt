package net.mrkaan.todo

import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var items: ArrayList<String>
    lateinit var itemsAdapter: ArrayAdapter<String>
    lateinit var lvItems: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lvItems = findViewById(R.id.lvItems)
        readItems()
        itemsAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1, items
        )
        lvItems.adapter = itemsAdapter

        // Setup remove listener method call

        // Setup remove listener method call
        setupListViewListener()
    }

    // Attaches a long click listener to the listview
    private fun setupListViewListener() {
        lvItems.onItemLongClickListener =
            OnItemLongClickListener { _, _, pos, _ -> // Remove the item within array at position
                items.removeAt(pos)
                // Refresh the adapter
                itemsAdapter.notifyDataSetChanged()
                writeItems()
                // Return true consumes the long click event (marks it handled)
                true
            }
    }

    fun onAddItem(v: View?) {
        val etNewItem = findViewById<EditText>(R.id.etNewItem)
        val itemText = etNewItem.text.toString()
        itemsAdapter.add(itemText)
        etNewItem.setText("")
        writeItems()
    }

    private fun readItems() {
        val filesDir = filesDir
        val todoFile = File(filesDir, "todo.txt")
        items = try {
            ArrayList(FileUtils.readLines(todoFile))
        } catch (e: IOException) {
            ArrayList()
        }
    }

    private fun writeItems() {
        val filesDir = filesDir
        val todoFile = File(filesDir, "todo.txt")
        try {
            FileUtils.writeLines(todoFile, items)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}