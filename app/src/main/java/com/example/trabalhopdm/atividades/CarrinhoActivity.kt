package com.example.trabalhopdm.atividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import com.example.trabalhopdm.R
import com.example.trabalhopdm.databinding.ActivityCarrinhoBinding
import com.example.trabalhopdm.fragmentos.CarrinhoFragment


class CarrinhoActivity : AppCompatActivity(), SearchView.OnQueryTextListener,
    SearchView.OnCloseListener{
    private var total : Float = .0f
    var searchView: SearchView? = null
    lateinit var binding: ActivityCarrinhoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarrinhoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val frag = CarrinhoFragment()
        supportFragmentManager.beginTransaction().replace(R.id.container, frag).commit()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId)
        {
            R.id.user ->
            {
                val i = Intent(this, ProfileActivity::class.java)
                startActivity(i)
            }
            R.id.procura ->
            {
                //val search = item!!.findItem(R.id.procura)
                // searchView = search.actionView as SearchView
                searchView?.queryHint = "Pesquisar"
                searchView?.setOnQueryTextListener(this)
                searchView?.setOnCloseListener(this)
            }
            R.id.carrinho ->
            {
                val i = Intent(this, CarrinhoActivity::class.java)
                startActivity(i)
            }
            R.id.inicio ->
            {
                val i = Intent(this, MainActivity2::class.java)
                startActivity(i)
            }

        }

        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    override fun onClose(): Boolean {
        return true
    }
}