package com.example.trabalhopdm.fragmentos

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.trabalhopdm.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_product.view.*
import kotlinx.android.synthetic.main.card_product_history.view.*
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.nio.file.Paths.get
import java.text.NumberFormat


class HistoryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val historyView = inflater.inflate(R.layout.fragment_history, container, false)

        getUserHistory()

        return historyView
    }

    fun getUserHistory() {
        val database = FirebaseDatabase.getInstance()
        val childUid = getCurrentUser()?.uid
        childUid?.let {
            val myRef = database.getReference("users").child(it).child("history")
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val historyPurch = dataSnapshot.value

                    val objectMap : Map<String, Any>
                    objectMap = historyPurch as Map<String, Any>

                    historyContainer?.removeAllViews()
                    for(v in objectMap){
                        updateUI(v)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException())
                }
            })
        }
    }

    fun getCurrentUser(): FirebaseUser? {
        val aut = FirebaseAuth.getInstance()
        return aut.currentUser
    }

    fun updateUI(productMap: Map.Entry<String, Any>){

        val card = layoutInflater.inflate(
                R.layout.card_product_history,
        historyContainer,
        false
        )
        card.txtTitle.text = (productMap.value as HashMap<*,*>).get("name").toString().capitalize()
        card.txtPrice.text = "R$" + (productMap.value as HashMap<*,*>).get("price").toString()
        card.txtDesc.text = (productMap.value as HashMap<*,*>).get("desc").toString().capitalize()
        card.txtQtdComprada.text = (productMap.value as HashMap<*,*>).get("qtd").toString() + " comprados"

        historyContainer.addView(card)

    }
}