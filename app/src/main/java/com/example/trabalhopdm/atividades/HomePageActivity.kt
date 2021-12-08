package com.example.trabalhopdm.atividades

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.trabalhopdm.R
import com.example.trabalhopdm.databinding.ActivityHomepageBinding
import com.example.trabalhopdm.fragmentos.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

private var mAuth: FirebaseAuth? = null

class MainActivity2 : AppCompatActivity(), SearchView.OnQueryTextListener,
    SearchView.OnCloseListener{
    lateinit var binding: ActivityHomepageBinding
    var searchView: SearchView? = null
    var toggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = getCurrentUser()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val frag = FrutasFragment()
        supportFragmentManager.beginTransaction().replace(R.id.container, frag).commit()

        binding.bottomNavigationView.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.frutas -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, frag).commit()
                }

                R.id.bebidas -> {
                    val frag = BebidasFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.container, frag).commit()
                }

                R.id.congelados -> {
                    val frag = CongeladosFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.container, frag).commit()
                }

            }
            true

        }
    }

        fun getCurrentUser(): FirebaseUser? {
            val aut = FirebaseAuth.getInstance()
            return aut.currentUser
        }


        fun mostrarPaginaProduto(produto: Map<String, Any>) {
            val i = Intent(this, ProductPageActivity::class.java)
            for (prod in produto) {
                i.putExtra(prod.key, prod.value.toString())
            }
            startActivity(i)
        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.d("yyyyui-onQueryTextC", newText.toString())
        return true
    }

    override fun onClose(): Boolean {
        Log.d("yyyyui-onClose", "onClose")
        return true
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

}


