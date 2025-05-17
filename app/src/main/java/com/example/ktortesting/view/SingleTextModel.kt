package com.example.ktortesting.view

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.AutoModel
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.ktortesting.R


@EpoxyModelClass(layout = R.layout.item_school)
abstract class SingleTextModel: EpoxyModelWithHolder<SingleTextModel.TextHolder>() {

    @EpoxyAttribute
    var schoolName: String = ""

    override fun bind(holder: TextHolder) {
        super.bind(holder)
        holder.textView.text = schoolName
    }

    inner class TextHolder: EpoxyHolder() {
        lateinit var textView: TextView

        override fun bindView(itemView: View) {
            textView = itemView.findViewById(R.id.schoolNameTextView)
        }
    }
}