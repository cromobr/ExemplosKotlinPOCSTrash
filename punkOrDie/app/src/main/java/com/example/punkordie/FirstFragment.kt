package com.example.punkordie


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.example.punkordie.databinding.FragmentFirstBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivCarregando.visibility = View.GONE

        binding.buttonFirst.setOnClickListener {
            binding.ivCarregando.visibility = View.VISIBLE
            //binding.ivCarregando.animation.duration = 1000
//            binding.ivCarregando.animate().apply {
//                duration = 1000
//                interpolator = LinearInterpolator()
//                rotationBy(360f)
//            }
//            binding.ivCarregando.animate().start()
//
////            val animateSpinner = binding.ivCarregando.drawable.
////            animateSpinner.start()
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
            binding.ivCarregando.startAnimation(animation)
        }




    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

