package com.example.leonidsnotesapplication.presentation.notes_feature.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.leonidsnotesapplication.databinding.FragmentHomeBinding
import com.example.leonidsnotesapplication.presentation.notes_feature.adapters.NoteCardAdapter
import com.example.leonidsnotesapplication.presentation.notes_feature.util.NotesItemAnimator
import com.example.leonidsnotesapplication.presentation.notes_feature.viewmodels.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    val binding get() = _binding!!

    private val adapter by lazy {
        NoteCardAdapter(this as NoteCardAdapter.NoteTouchListener)
    }

    private val vm : HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        vm.updateNotes()
        binding.vm = vm
        binding.adapter = adapter

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            homeRecyclerView.isNestedScrollingEnabled = false
            homeRecyclerView.layoutManager = LinearLayoutManager(view.context)
            homeRecyclerView.itemAnimator= NotesItemAnimator()
        }
        binding.homeSearchView.isSubmitButtonEnabled  = true
        binding.homeSearchView.setOnQueryTextListener(this as SearchView.OnQueryTextListener)
    }
}