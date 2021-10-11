package com.cuncis.sampletourguide.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cuncis.sampletourguide.R
import com.cuncis.sampletourguide.databinding.FragmentHomeBinding
import com.cuncis.sampletourguide.utils.Tooltips
import com.skydoves.balloon.Balloon
import me.toptas.fancyshowcase.FancyShowCaseQueue
import me.toptas.fancyshowcase.FancyShowCaseView
import me.toptas.fancyshowcase.FocusShape
import me.toptas.fancyshowcase.listener.OnCompleteListener
import me.toptas.fancyshowcase.listener.OnViewInflateListener

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private lateinit var tooltips: Balloon

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.tvTitle
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        return root
    }

    override fun onViewCreated(view1: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view1, savedInstanceState)
        val fancyShowCaseView1 = FancyShowCaseView.Builder(requireActivity())
            .title("First Queue Item")
            .focusOn(binding.tvTitle)
            .focusShape(FocusShape.ROUNDED_RECTANGLE)
            .customView(R.layout.layout_simple_tooltip, object : OnViewInflateListener {
                override fun onViewInflated(view: View) {
                    view.findViewById<Button>(R.id.btn_yes).setOnClickListener {
                        Toast.makeText(requireContext(), "Popup", Toast.LENGTH_SHORT).show()

                    }
                }
            })
            .showOnce("text1")
            .closeOnTouch(true)
            .build()

        val fancyShowCaseView2 = FancyShowCaseView.Builder(requireActivity())
            .title("Second Queue Item")
            .focusOn(binding.tvDesc)
            .focusShape(FocusShape.ROUNDED_RECTANGLE)
            .customView(R.layout.layout_simple_tooltip, object : OnViewInflateListener {
                override fun onViewInflated(view: View) {
                    view.findViewById<Button>(R.id.btn_yes).setOnClickListener {
                        Toast.makeText(requireContext(), "Popup", Toast.LENGTH_SHORT).show()
                    }
                }
            })
            .showOnce("text2")
            .closeOnTouch(true)
            .build()

        val fancyShowCaseView3 = FancyShowCaseView.Builder(requireActivity())
            .title("Third Queue Item")
            .focusOn(binding.btnPush)
            .focusShape(FocusShape.ROUNDED_RECTANGLE)
            .customView(R.layout.layout_simple_tooltip, object : OnViewInflateListener {
                override fun onViewInflated(view: View) {
                    view.findViewById<Button>(R.id.btn_yes).setOnClickListener {
                        Toast.makeText(requireContext(), "Popup", Toast.LENGTH_SHORT).show()
                    }
                }
            })
            .showOnce("text3")
            .closeOnTouch(true)
            .build()

        val queue = FancyShowCaseQueue()
            .add(fancyShowCaseView1)
            .add(fancyShowCaseView2)
            .add(fancyShowCaseView3)
        queue.show()
        queue.completeListener = object : OnCompleteListener {
            override fun onComplete() {
                Toast.makeText(requireContext(), "Finished", Toast.LENGTH_SHORT).show()
            }
        }

        onClickListener()
        setupTooltips()
        tooltipsListener()
    }

    private fun onClickListener() {
        binding.btnPush.setOnClickListener {
            tooltips.showAlignTop(it)
        }
    }

    private fun setupTooltips() {
        tooltips = Tooltips.setupBalloon(
            requireContext(),
            viewLifecycleOwner,
            R.layout.layout_onboarding_tooltip,
            140
        )
    }

    private fun tooltipsListener() {
        val view = tooltips.getContentView()
        val btnYes = view.findViewById<Button>(R.id.btn_yes)
        btnYes.setOnClickListener {
            Toast.makeText(requireContext(), "Yes Clicked", Toast.LENGTH_SHORT).show()
            tooltips.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        tooltips.dismiss()
    }
}