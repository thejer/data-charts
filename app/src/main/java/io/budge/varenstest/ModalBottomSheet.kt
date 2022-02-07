package io.budge.varenstest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.budge.varenstest.databinding.ExchangeCoinsBottomSheetModalBinding

class ModalBottomSheet : BottomSheetDialogFragment() {
    private var _binding: ExchangeCoinsBottomSheetModalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = ExchangeCoinsBottomSheetModalBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        binding.closeIcon.setOnClickListener {
            dismiss()
        }
    }

    private fun setUpRecyclerView() {
        val exchangeCoinsAdapter = ExchangeCoinsAdapter()
        exchangeCoinsAdapter.submitList(listOf(
            ExchangeCoin(0, "btc"),
            ExchangeCoin(1, "eth"),
            ExchangeCoin(2, "xrp"),
            ExchangeCoin(3, "ltc"),
            ExchangeCoin(4, "btc"),
            ExchangeCoin(5, "eth"),
            ExchangeCoin(6, "xrp"),
            ExchangeCoin(7, "ltc"),
            ExchangeCoin(8, "btc"),
            ExchangeCoin(9, "eth"),
            ExchangeCoin(10, "xrp"),
            ExchangeCoin(11, "ltc"),
            ExchangeCoin(12, "btc"),
            ExchangeCoin(13, "eth"),
            ExchangeCoin(14, "xrp"),
            ExchangeCoin(15, "ltc"),
            ExchangeCoin(16, "btc"),
            ExchangeCoin(17, "eth"),
            ExchangeCoin(18, "xrp"),
            ExchangeCoin(19, "ltc"),
        ))
        with(binding.exchangeCoinsRecyclerView) {
            adapter = exchangeCoinsAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}