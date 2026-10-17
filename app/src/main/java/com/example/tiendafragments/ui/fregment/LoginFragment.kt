package com.example.tiendafragments.ui.fregment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tiendafragments.R
import com.example.tiendafragments.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onAttach(context: Context) {
        super.onAttach(context)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.btnLogin.setOnClickListener {
            val correo = binding.editCorreo.text.toString().trim()
            val pass = binding.editPass.text.toString().trim()
            if (correo.isEmpty() || pass.isEmpty()) {
                Toast.makeText(requireContext(), "Rellena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(correo, pass)
                .addOnSuccessListener {
                    findNavController().navigate(R.id.action_loginFragment2_to_seriesListFragment)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }

        binding.btnRegistro.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment2_to_registerFragment2)
        }
    }

    override fun onDetach() {
        super.onDetach()
    }
}
