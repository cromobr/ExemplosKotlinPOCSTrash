package com.example.cameracomnavegacao

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.cameracomnavegacao.databinding.FragmentFirstBinding
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.example.cameracomnavegacao.databinding.FragmentSecondBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private lateinit var codeScanner: CodeScanner

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //val scannerView = view.findViewById<CodeScannerView>(R.id.scanner_view)
        val activity = requireActivity()
      //  codeScanner = CodeScanner(activity, scannerView!!)
//        codeScanner.decodeCallback = DecodeCallback {
//            activity.runOnUiThread {
//                Toast.makeText(activity, it.text, Toast.LENGTH_LONG).show()
//            }
//        }
        binding.buttonFirst.setOnClickListener {
         FragmentSecondBinding.inflate(layoutInflater)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}