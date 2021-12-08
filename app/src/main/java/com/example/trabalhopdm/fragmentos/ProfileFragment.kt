package com.example.trabalhopdm.fragmentos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.trabalhopdm.R
import com.example.trabalhopdm.atividades.HistoricoActivity
import com.example.trabalhopdm.atividades.LoginActivity
import com.example.trabalhopdm.atividades.MainActivity
import com.example.trabalhopdm.atividades.MainActivity2
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import kotlinx.android.synthetic.main.fragment_profile.view.*

private var mAuth: FirebaseAuth? = null


class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val profileView = inflater.inflate(R.layout.fragment_profile, container, false)

        val user = FirebaseAuth.getInstance().currentUser

        user?.let {
            // Name, email address, and profile photo Url
            val name = it.displayName
            val email = it.email
            //val pht = it.photoUrl

            profileView.txtName.setText(name)
            profileView.txtEmail.setText(email)
            //profileView.imgPerfil.setImageURI(pht)
        }

        profileView.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut();
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }


        profileView.btnPurchHist.setOnClickListener {

            val frag = HistoryFragment()
            activity?.let {
                it.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, frag)
                    .commit()
            }
        }
        return profileView
    }
}
