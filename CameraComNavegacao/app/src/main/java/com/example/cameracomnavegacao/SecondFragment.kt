package com.example.cameracomnavegacao



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.budiyev.android.codescanner.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private lateinit var codeScanner: CodeScanner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val scannerView = view?.findViewById<CodeScannerView>(R.id.scanner_view)

        codeScanner = CodeScanner(requireContext(), scannerView!!)

        // Parameters (default values)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.CONTINUOUS
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            Thread(Runnable {
                Toast.makeText(requireContext(), "Scan result: ${it.text}", Toast.LENGTH_LONG).show()
            })
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            Thread(Runnable {
                Toast.makeText(requireContext(), "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG).show()
            })
        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
        return this.view
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
}