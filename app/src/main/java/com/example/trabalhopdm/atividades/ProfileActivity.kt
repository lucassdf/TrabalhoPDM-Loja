package com.example.trabalhopdm.atividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import com.br.jafapps.bdfirestore.util.Util
import com.example.trabalhopdm.R
import com.example.trabalhopdm.databinding.ActivityProfileBinding
import com.example.trabalhopdm.fragmentos.ProfileFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity(), SearchView.OnQueryTextListener,
    SearchView.OnCloseListener {
    lateinit var binding: ActivityProfileBinding
    var searchView: SearchView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        val usuario = Firebase.auth.currentUser?.email.toString()
        binding.editTextUsuario.setText(usuario)

        binding.buttonLogoff.setOnClickListener {
            Util.exibirToast(this, "Logout efetuado com sucesso")
            Firebase.auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }*/

        val frag = ProfileFragment()
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
                val i = Intent(this, com.example.trabalhopdm.atividades.ProfileActivity::class.java)
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