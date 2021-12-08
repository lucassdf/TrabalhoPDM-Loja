package com.example.trabalhopdm.atividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.trabalhopdm.R
import com.example.trabalhopdm.databinding.ActivityMainBinding
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView.setImageResource(R.drawable.mercado)

        binding.buttonCadastrar.setOnClickListener {


            val providers = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build())

            startActivityForResult(
                    AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers).build(),
                    1
            )


        }

        binding.buttonLogin.setOnClickListener {

            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)

        }


    }

    fun getCurrentUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if(requestCode == 1 && resultCode == RESULT_OK){
            Toast.makeText(this, "Usuário cadastrado com sucesso", Toast.LENGTH_LONG).show()
        }
        else{
            finishAffinity()
        }
    }

}