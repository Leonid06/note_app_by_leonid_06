package com.leonid.leonidsnotesapplication.presentation.notes_feature.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.leonid.leonidsnotesapplication.databinding.FragmentSingleNoteBinding
import com.leonid.leonidsnotesapplication.domain.model.Note
import com.leonid.leonidsnotesapplication.presentation.extensions.showKeyboard
import com.leonid.leonidsnotesapplication.presentation.notes_feature.viewmodels.NoteSharedViewModel
import com.leonid.leonidsnotesapplication.presentation.notes_feature.viewmodels.SingleNoteViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SingleNoteFragment : Fragment(){

    private val vm : SingleNoteViewModel by viewModels()

    private val noteSharedViewModel : NoteSharedViewModel  by activityViewModels()

    private var _binding : FragmentSingleNoteBinding? = null

    private val binding  get()= _binding!!

    private val args by navArgs<SingleNoteFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSingleNoteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedNote = noteSharedViewModel.selectedNote.value!!

        activity?.showKeyboard(binding.etNoteContent)

        binding.etNoteContent.setText(selectedNote.content)

        val backUpContent : String = binding.etNoteContent.text.toString()

        binding.ivFolderChange.setOnClickListener{
            findNavController().navigate(SingleNoteFragmentDirections.actionSingleNoteFragmentToEditFolderDialogFragment())
        }

        binding.ivCancel.setOnClickListener{
            findNavController().navigateUp()
        }

        binding.ivBackup.setOnClickListener{
            binding.etNoteContent.setText(backUpContent)
        }
        binding.addNoteButton.setOnClickListener{

            val content = binding.etNoteContent.text.toString()
            val isStarred = selectedNote.isStarred
            val title : String
            val subtitle : String


            if(content.contains("\n")){
                title = content.split("\n")[0]
                subtitle = content.split("\n")[1]
            }else{
                title = content
                subtitle = ""
            }

            val note = createNote(title, subtitle,  content,isStarred, selectedNote.datetime!!, selectedNote.id, folderId = selectedNote.folderId)

            if(content.isNotEmpty()){
                vm.addNote(note, args.isNew)
                findNavController().navigateUp()
            }
        }

    }

//    private fun showFolderEditMenu() {
//        val menu = PopupMenu(context!!, binding.ivFolderChange)
//        val inflater = menu.menuInflater
//        inflater.inflate(R.menu.sort_menu,  menu.menu)
//        vm.foldersLiveData.value!!.forEach {
//            menu.menu.add(it.title)
//        }
//        menu.setOnMenuItemClickListener(this as PopupMenu.OnMenuItemClickListener)
//        menu.show()
//    }

    private fun createNote(
        title: String,
        subtitle: String,
        content: String,
        isStarred: Boolean,
        date: String,
        id: Int,
        folderId : Int
    ): Note {
        return Note(
            title,
            subtitle,
            content,
            isStarred,
            date,
            id,
            folderId
        )
    }
}
