package com.example.trabalhopdm.fragmentos


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.trabalhopdm.room.Database
import com.example.trabalhopdm.R
import com.example.trabalhopdm.atividades.MainActivity2
import com.example.trabalhopdm.modelo.Product
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_product.view.*
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private var mStorageRef: StorageReference? = null

    companion object {
        const val TAG = "HomeFragment"
    }
    lateinit var storage: FirebaseStorage
    lateinit var progressBarProducts: ProgressBar

    var db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val homeView = inflater.inflate(R.layout.fragment_home, container, false)

        progressBarProducts = homeView.findViewById(R.id.progressBarProducts)
        progressBarProducts.visibility = View.VISIBLE

        getProducts()

        return homeView

    }

    fun filterProductsByName(){
        val busca = etSearch.text.toString().toLowerCase()
        progressBarProducts.visibility = View.VISIBLE
        //Filtrar produtos
        val productsRef = db.collection("produtos")

        productsRef.orderBy("nome").startAt(busca).endAt(busca + "~")
            .get()
            .addOnSuccessListener { documents ->
                productContainer.removeAllViews()
                for (document in documents) {
                    updateUI(document.data)
                }
                progressBarProducts.visibility = View.GONE
            }
            .addOnFailureListener { exception ->
                Snackbar.make(coordinatorLayoutProduct, "Erro ao buscar produtos!", Snackbar.LENGTH_LONG).show()
                Log.w(TAG, "Error while fetching: ", exception)
            }

    }

    //GetProducts from FireStore
    fun getProducts(){
        val productsRef = db.collection("produtos")
        productsRef
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        updateUI(document.data)
                    }
                    progressBarProducts.visibility = View.GONE
                } else {
                    Snackbar.make(coordinatorLayoutProduct, "Erro ao buscar produtos!", Snackbar.LENGTH_LONG).show()
                    Log.w(TAG, "Error while fetching: ", task.exception)
                }

            }
    }

    fun updateUI(productMap: Map<String, Any>){

        val productData = productMap
        val card = layoutInflater?.inflate(
            R.layout.card_product,
            productContainer,
            false
        )
        card.card_title.text = productData["nome"].toString().capitalize()
        card.card_body.text = "R$ " + productData["preco"].toString()

        val imgUrl = productData["urlImg"].toString()
        Picasso.get().load(imgUrl).into(card.card_header)

        card.btnBuy.setOnClickListener(buyItem(productData))
        card.setOnClickListener(clickInCard(productData));
        productContainer.addView(card)

    }

    //Item vai direto pro carrinho
    private fun buyItem(selectedProduct: Map<String, Any>): View.OnClickListener? {
        return View.OnClickListener { v ->
            Thread{
            val produtoSelecionado =
                Product(idFB = selectedProduct["id"].toString().toInt(), name = selectedProduct["nome"].toString(), desc = selectedProduct["desc"].toString(), price = selectedProduct["preco"].toString().toFloat(), qtd = 1, urlImg = selectedProduct["urlImg"].toString())
                insertProduct(produtoSelecionado)
            }.start()
        }
    }

    //Tela de detalhes do item
    private fun clickInCard(selectedProduct: Map<String, Any>): View.OnClickListener? {
        return View.OnClickListener { v ->
            val mainAct = activity as MainActivity2
            mainAct.mostrarPaginaProduto(selectedProduct)
            //activity?.finish()
        }
    }

    fun insertProduct(product: Product){
        activity?.let{
            val roomdb = Room.databaseBuilder(it, Database::class.java, "AppDB").build()
            val allProducts = roomdb.productDao().getAll()
            val qtdTotal = allProducts.size
            var i = 1
            var purch = false
            if(allProducts.isNotEmpty()){
                for(prd in allProducts){
                    //Se o idFb for igual adiciona na qtd
                    if(prd.idFB == product.idFB && !purch){
                        roomdb.productDao().insertMore(1+prd.qtd.toString().toInt(), prd.idFB )
                        purch = true
                    }
                    else if (prd.idFB != product.idFB && !purch){
                        if(i == qtdTotal){
                            roomdb.productDao().insert(product)
                            purch = true
                        }
                    }
                    i++
                }
            }else{
                roomdb.productDao().insert(product)
            }
        Snackbar.make(coordinatorLayoutProduct, "Produto adicionado com sucesso ao carrinho!", Snackbar.LENGTH_LONG).show()
        }
    }
}

