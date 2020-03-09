package com.nivelais.supinfo.jporating.presentation.ui.interrogation.answering

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.recyclerview.widget.RecyclerView
import com.hsalf.smilerating.BaseRating
import com.nivelais.supinfo.domain.entities.QuestionEntity
import com.nivelais.supinfo.jporating.R
import com.nivelais.supinfo.jporating.databinding.SmileyQuestionItemBinding
import com.nivelais.supinfo.jporating.databinding.ToTenQuestionItemBinding

class AnsweringQuestionsAdapter(
    private var questions: List<QuestionEntity>,
    private val answerCallback: (Long, Int) -> Unit,
    private val resetCallback: (Long) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (QuestionEntity.Type.fromId(viewType)) {
            QuestionEntity.Type.SMILEY -> SmileyQuestionViewHolder(
                SmileyQuestionItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> ToTenQuestionViewHolder(
                ToTenQuestionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }

    override fun getItemCount(): Int = questions.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AnsweringQuestionViewHolder) {
            holder.bind(
                questions[position],
                position,
                questions.size,
                answerCallback,
                resetCallback
            )
        }
    }

    override fun getItemViewType(position: Int) = questions.elementAt(position).type.id

    interface AnsweringQuestionViewHolder {
        fun bind(
            question: QuestionEntity, position: Int, size: Int,
            answerCallback: (Long, Int) -> Unit,
            resetCallback: (Long) -> Unit
        )
    }

    /**
     * View holder for a seekbar questions
     */
    class ToTenQuestionViewHolder(val binding: ToTenQuestionItemBinding) :
        RecyclerView.ViewHolder(binding.root), AnsweringQuestionViewHolder {

        override fun bind(
            question: QuestionEntity,
            position: Int,
            size: Int,
            answerCallback: (Long, Int) -> Unit,
            resetCallback: (Long) -> Unit
        ) {
            // Bind view element
            binding.textQuestion.text = question.text
            binding.textPlace.text =
                binding.root.context.getString(R.string.lbl_question_item_place, position + 1, size)
            binding.textHintSeekbar.text = binding.root.context.getString(
                R.string.hint_question_item_rating,
                binding.seekRating.progress
            )

            // Reset button
            binding.btnReset.setOnClickListener {
                // Reset seekbar & text
                binding.seekRating.progress = 5
                // Disable button
                binding.btnReset.isEnabled = false
                binding.btnValidate.isEnabled = true
                // Call the reset callback
                resetCallback.invoke(question.id)
            }

            // On rating change
            binding.seekRating.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    // Update the text
                    binding.textHintSeekbar.text = binding.root.context.getString(
                        R.string.hint_question_item_rating,
                        progress
                    )
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })

            // Listen to the validate button
            binding.btnValidate.setOnClickListener {
                // enable reset button
                binding.btnReset.isEnabled = true
                binding.btnValidate.isEnabled = false
                // Call to the reset callback
                answerCallback.invoke(question.id, binding.seekRating.progress)
            }
        }
    }

    /**
     * View holder for a smiley questions
     */
    class SmileyQuestionViewHolder(val binding: SmileyQuestionItemBinding) :
        RecyclerView.ViewHolder(binding.root), AnsweringQuestionViewHolder {

        override fun bind(
            question: QuestionEntity,
            position: Int,
            size: Int,
            answerCallback: (Long, Int) -> Unit,
            resetCallback: (Long) -> Unit
        ) {
            // Bind view element
            binding.textQuestion.text = question.text
            binding.textPlace.text =
                binding.root.context.getString(R.string.lbl_question_item_place, position + 1, size)

            // Setup string ressources on smile
            binding.smileRating.apply {
                setNameForSmile(BaseRating.TERRIBLE, R.string.smile_rating_terrible)
                setNameForSmile(BaseRating.BAD, R.string.smile_rating_bad)
                setNameForSmile(BaseRating.OKAY, R.string.smile_rating_okay)
                setNameForSmile(BaseRating.GOOD, R.string.smile_rating_good)
                setNameForSmile(BaseRating.GREAT, R.string.smile_rating_great)
            }

            // Reset smiley rating button
            binding.btnReset.setOnClickListener {
                // Reset selected smile
                binding.smileRating.selectedSmile = BaseRating.NONE
                // Disable button
                binding.btnReset.isEnabled = false
                // Call to the callback
                resetCallback.invoke(question.id)
            }

            // Bind callback
            binding.smileRating.setOnRatingSelectedListener { level, reselected ->
                // Enable reset button
                binding.btnReset.isEnabled = true

                // Call to the callback
                answerCallback.invoke(question.id, convertSmileRatingToTen(level))
            }
        }

        private fun convertSmileRatingToTen(smileRating: Int) =
            when (smileRating - 1) {
                BaseRating.TERRIBLE -> 0
                BaseRating.BAD -> 2
                BaseRating.OKAY -> 5
                BaseRating.GOOD -> 7
                BaseRating.GREAT -> 10
                else -> 0
            }
    }
}
