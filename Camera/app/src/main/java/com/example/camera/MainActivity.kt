package com.example.camera



import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.view.View.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd

import androidx.fragment.app.FragmentContainerView
import com.airbnb.lottie.LottieAnimationView
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {

    lateinit var btnScan : Button
    lateinit var txtResultado : TextView
    lateinit var leituraAnimation : LottieAnimationView
    lateinit var fragmentContainerView : FragmentContainerView
    lateinit var lastText : String
    lateinit var barcodeView : DecoratedBarcodeView
    lateinit var codeScanner: CodeScanner
    lateinit var cameraLayout: ConstraintLayout
    lateinit var carrinhoLayout: ConstraintLayout
    lateinit var btnCompra : ImageView
    private val handle = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnScan = findViewById(R.id.btnScan)
        txtResultado = findViewById(R.id.txtResultado)
        cameraLayout = findViewById(R.id.cameraLayout)
        carrinhoLayout = findViewById(R.id.carrinhoLayout)


        //btnCompra = findViewById(R.id.CarrinhoCompras)


        val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)

         codeScanner = CodeScanner(this, scannerView)

        // Parameters (default values)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.CONTINUOUS // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        // Callbacks
        var anterior = ""
        var contagem = 0
        codeScanner.decodeCallback = DecodeCallback {

            runOnUiThread {
                if (anterior != it.text ){
                    btnCompra.visibility = VISIBLE
                    txtResultado.text = txtResultado.text.toString() + " + " + it.text
                    //leituraAnimation.playAnimation()
                    leituraAnimation.resumeAnimation()
                    anterior = it.text
                    contagem = 0
                    btnCompra.visibility = GONE
                } else if (contagem < 10){
                    contagem ++
                } else if (contagem >= 10){
                    anterior = ""
                }
            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast.makeText(this, "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG).show()
            }
        }



        btnScan.setOnClickListener {
            val viewCriada = CriarLinearLayout(cameraLayout, this, 0F, 10F)
            leituraAnimation = findViewById(R.id.leituraAnimacao)
            leituraAnimation.animate()
                .withEndAction{
                   if (carrinhoLayout.width != 52.dpToPx(this)) {
                        val widthAnimator = criarWidthAnimator(carrinhoLayout.width, 52.dpToPx(this), true)
                        animarSacola(widthAnimator, true)
                    }
            }.start()



            leituraAnimation.resumeAnimation()

            handle.postDelayed({
                val widthAnimator = criarWidthAnimator(52.dpToPx(this), 1.dpToPx(this), false)
                animarSacola(widthAnimator, false)
            }, 2000L)


        }
   }

    fun CriarLinearLayout(
        containerPrincipal: ConstraintLayout,
        activityPrincipal: Activity,
        x: Float,
        y: Float
    ): LinearLayout {
        val linearLayout = LinearLayout(this)
        linearLayout.layoutParams = LinearLayout.LayoutParams(52.dpToPx(this), 52.dpToPx(this))
        linearLayout.orientation = LinearLayout.HORIZONTAL
        linearLayout.rotation = -15f
        linearLayout.id = View.generateViewId()
        linearLayout.gravity = Gravity.CENTER
        linearLayout.elevation = 20f
        linearLayout.x = x
        linearLayout.y = y

        containerPrincipal.addView(linearLayout)
        return activityPrincipal.findViewById(linearLayout.id)
    }

    fun Int.dpToPx(context: Context): Int {
        val density = context.resources.displayMetrics.density
        return (this.toFloat() * density).roundToInt()
    }

    fun animarSacola(widthAnimator: ValueAnimator, sacolaEstaAparecendo: Boolean) {
        widthAnimator.addUpdateListener { animation ->
            carrinhoLayout.layoutParams.width = animation!!.animatedValue as Int
            carrinhoLayout.requestLayout()
        }
        if (sacolaEstaAparecendo) {
            carrinhoLayout.visibility = View.VISIBLE
        } else {
            widthAnimator.doOnEnd {
                carrinhoLayout.visibility = View.INVISIBLE
            }
        }
        widthAnimator.start()
    }

    fun criarWidthAnimator(tamanhoInicial: Int, tamanhoFinal: Int, possuiInterpolator: Boolean): ValueAnimator {
        val widthAnimator = ValueAnimator.ofInt(tamanhoInicial, tamanhoFinal)
        if (possuiInterpolator) {
            widthAnimator.interpolator = AnimationUtils.loadInterpolator(this, R.anim.transition_ease_in_out )
        }
        widthAnimator.duration = 100L
        return widthAnimator
    }
}