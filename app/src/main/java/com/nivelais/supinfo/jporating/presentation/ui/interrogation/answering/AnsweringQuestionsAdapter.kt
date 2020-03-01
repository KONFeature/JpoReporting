package com.nivelais.supinfo.jporating.presentation.ui.interrogation.answering

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nivelais.supinfo.domain.entities.QuestionEntity
import com.nivelais.supinfo.jporating.R
import com.nivelais.supinfo.jporating.databinding.SatisfactionQuestionItemBinding

class AnsweringQuestionsAdapter(
    private var questions: Set<QuestionEntity>
) : RecyclerView.Adapter<AnsweringQuestionsAdapter.AnsweringQuestionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnsweringQuestionViewHolder =
        AnsweringQuestionViewHolder(
            SatisfactionQuestionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount(): Int = questions.size

    override fun onBindViewHolder(holder: AnsweringQuestionViewHolder, position: Int) =
        holder.bind(questions.elementAt(position), position, questions.size)

    class AnsweringQuestionViewHolder(val binding: SatisfactionQuestionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(question: QuestionEntity, position: Int, size: Int) {
            // Bind view element
            binding.textQuestion.text = question.text
            binding.textPlace.text = binding.root.context.getString(R.string.lbl_question_item_place, position + 1, size)

            // Bind callback

        }
    }
}