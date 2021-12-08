package com.example.trabalhopdm.atividades

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.trabalhopdm.room.Database
import com.example.trabalhopdm.R
import com.example.trabalhopdm.modelo.Product
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_page.*

class ProductPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_page)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val nome = intent.getStringExtra("nome")
        val desc = intent.getStringExtra("desc")
        val preco = intent.getStringExtra("preco")
        val urlImg = intent.getStringExtra("urlImg")
        val id = intent.getStringExtra("id")
        val categoria = intent.getStringExtra("categoria")

        nomeProduto.text = nome?.capitalize()
        descProduto.text = desc?.capitalize()
        precoProduto.text = "R$" + preco
        Picasso.get().load(urlImg).into(imgProduto)

        progressBarProductPage.visibility = View.GONE

        btnMaisQtd.setOnClickListener{
            var qtd = etQtd.text.toString()
            etQtd.setText(Integer.toString(qtd.toInt() + 1))
        }
        btnMenosQtd.setOnClickListener{
            var qtdm = etQtd.text.toString()
            var qtdFinal = 1
            if(qtdm.toInt() > 1)
                qtdFinal = qtdm.toInt() - 1
            etQtd.setText(Integer.toString(qtdFinal))
        }


        btnBuy.setOnClickListener{
            progressBarProductPage.visibility = View.VISIBLE
            val idprod = id.toString().toInt()
            val qtdprod= etQtd.text.toString().toInt()
            val prod =  Product(
                name = nome.toString(),
                desc = desc.toString(),
                price = preco.toString().toFloat(),
                qtd = etQtd.text.toString().toInt(),
                urlImg = urlImg.toString(),
                idFB = id.toString().toInt()
            )
            //buyItem(idprod, qtdprod, prod)
            Thread{
                insertProduct(idprod, qtdprod, prod)
                finish()
            }.start()
        }
    }

    //Item vai direto pro carrinho
    /*private fun buyItem(id: Int, qtd: Int, prod: Product){
        Thread{
            insertProduct(id, qtd, prod)
            finish()
        }.start()
    }*/

    fun insertProduct(id: Int, qtd: Int, prod: Product){
        val roomdb = Room.databaseBuilder(this, Database::class.java, "AppDB").build()
        val allProducts = roomdb.productDao().getAll()
        val qtdItemsCart = allProducts.size
        var i = 1
        var purch = false
        if(allProducts.isNotEmpty()){
            for (product in allProducts){
                if(product.idFB == id && !purch){
                    roomdb.productDao().insertMore(qtd+product.qtd.toString().toInt(), id)
                    purch = true
                }
                else if(product.idFB != id  && !purch){
                    if(i == qtdItemsCart){
                        roomdb.productDao().insert(prod)
                        purch = true
                    }
                }
                i++
            }
        }else{
            roomdb.productDao().insert(prod)
        }

        Snackbar.make(
            coordinatorLayoutPageProduct,
            "Produto adicionado com sucesso ao carrinho!",
            Snackbar.LENGTH_LONG
        ).show()

        Thread.sleep(700)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}