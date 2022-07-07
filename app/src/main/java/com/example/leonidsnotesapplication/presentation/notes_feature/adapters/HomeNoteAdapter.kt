//package com.example.leonidsnotesapplication.presentation.notes_feature.adapters
//
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.RecyclerView
//import com.example.leonidsnotesapplication.databinding.HomeNoteCardViewBinding
//import com.example.leonidsnotesapplication.databinding.NoteCardViewBinding
//import com.example.leonidsnotesapplication.domain.model.Note
//import com.example.leonidsnotesapplication.domain.model.NoteViewData
//import com.example.leonidsnotesapplication.presentation.notes_feature.callbacks.AdapterCallback
//import com.example.leonidsnotesapplication.presentation.notes_feature.callbacks.OnTouchListener
//import com.example.leonidsnotesapplication.presentation.notes_feature.util.HomeDiffUtil
//
//
//class HomeNoteAdapter(
//    private val onTouchListener : NoteTouchListener
//) :
//    RecyclerView.Adapter<HomeNoteAdapter.ViewHolder>() {
//
//
//    interface NoteTouchListener {
//        fun onNoteSwipedLeft(data : NoteViewData) : Boolean
//        fun onNoteClicked(data  : NoteViewData)
//        fun onDeleteButtonClick(data : NoteViewData)
//        fun onStarCheckBoxClick(data : NoteViewData)
//    }
//
//    private val viewData = ArrayList<NoteViewData>()
//    private val notesListCallback =  AdapterCallback(this as RecyclerView.Adapter<*>)
//
//    class ViewHolder(private val binding: HomeNoteCardViewBinding,
//                     private val onTouchListener: NoteTouchListener
//    ) : RecyclerView.ViewHolder(binding.root) {
//
//
//        init {
//            binding.root.setOnTouchListener(object : OnTouchListener(binding.root.context){
//                override fun onSwipeLeft(): Boolean {
//                    onTouchListener.onNoteSwipedLeft(binding.viewData!!)
//                    return true
//                }
//
//                override fun onClick(): Boolean {
//                    onTouchListener.onNoteClicked(binding.viewData!!)
//                    return true
//                }
//            })
//            binding.ibDelete.setOnClickListener {
//                onTouchListener.onDeleteButtonClick(binding.viewData!!)
//            }
//            binding.cbStar.setOnClickListener {
//                binding.viewData!!.isStarred = binding.cbStar.isChecked
//                onTouchListener.onStarCheckBoxClick(binding.viewData!!)
//            }
//        }
//
//        fun bind(viewData: NoteViewData) {
//            binding.viewData = viewData
//            binding.executePendingBindings()
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val binding = HomeNoteCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//
//        return ViewHolder(binding ,onTouchListener)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(viewData[position])
//    }
//
//
//    fun setData(viewData : ArrayList<NoteViewData>){
//        viewData.reverse()
//
//        val diffUtil = HomeDiffUtil(this.viewData, viewData)
//        val diffResult = DiffUtil.calculateDiff(diffUtil)
//
//        this.viewData.clear()
//        this.viewData.addAll(viewData)
//
//        diffResult.dispatchUpdatesTo(notesListCallback)
//
//        viewData.reverse()
//    }
//
//    override fun getItemCount() =  viewData.size
//
//}