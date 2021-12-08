package com.example.trabalhopdm.atividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.br.jafapps.bdfirestore.util.DialogProgress
import com.br.jafapps.bdfirestore.util.Util
import com.example.trabalhopdm.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase



class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        val user = auth?.currentUser

        if(user != null){
            //user.email
            //val i = Intent(this, ListaCategoriaActivity::class.java)
            //val i = Intent(this, FirestoreLerDadosActivity::class.java)
            val i = Intent(this, MainActivity2::class.java)
            //val i = Intent(this, ListaProdutosActivity::class.java)
            startActivity(i)
            finish()
        }


        binding.buttonConfirmar.setOnClickListener {

            buttonConfirmar()

        }


    }

    fun buttonConfirmar(){

        val email = binding.editTextEmail.text.toString()
        val senha = binding.editTextSenha.text.toString()

        if(!email.trim().equals("") && !senha.trim().equals("")){


            if(Util.statusInternet(this)){

                login(email,senha)

            }
            else{
                Util.exibirToast(this, "Você precisa estar conectado a internet")
            }

        }
        else{
            Util.exibirToast(this, "Preencha todos os campos")
        }
        val i = Intent(this, MainActivity2::class.java)
        //val i = Intent(this, FirestoreLerDadosActivity::class.java)
        //val i = Intent(this, ListaCategoriaActivity::class.java)
        //val i = Intent(this, ListaProdutosActivity::class.java)
        startActivity(i)
        finish()

        //Util.exibirToast(this, "Login efetuado")

    }

    fun login(email:String, senha:String){

        val dialogProgress = DialogProgress()
        dialogProgress.show(supportFragmentManager, "1")

        auth?.signInWithEmailAndPassword(email,senha)?.addOnCompleteListener(this){task ->

            dialogProgress.dismiss()

            if(task.isSuccessful){
                Util.exibirToast(baseContext, "Login efetuado com sucesso")
                val i = Intent(this, MainActivity2::class.java)
                //val i = Intent(this, FirestoreLerDadosActivity::class.java)
                //val i = Intent(this, ListaCategoriaActivity::class.java)
               // val i = Intent(this, ListaProdutosActivity::class.java)
                startActivity(i)
                finish()
            }
            else{
                //Util.exibirToast(baseContext, "Erro ao realizar login ${task.exception.toString()}")
                val erro = task.exception.toString()
                erroFirebase(erro)
                Log.d("teste", task.exception.toString())
            }
        }



    }

    fun erroFirebase(erro:String){

        if(erro.contains("There is no user corresponding to this identifier")){
            Util.exibirToast(baseContext, "Erro ao realizar login: esse email não está cadastrado")
        }
        else if(erro.contains("The password is invalid")){
            Util.exibirToast(baseContext, "Erro ao realizar login: a senha está invalida")
        }
        else if(erro.contains("The email address is badly")){
            Util.exibirToast(baseContext, "Erro ao realizar login: o email digitado não é valido")
        }

    }
}