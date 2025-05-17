package com.example.ktortesting.view

import com.airbnb.epoxy.EpoxyController
import com.example.ktortesting.struct.School

class SingleTextController : EpoxyController() {

    var schoolNameItems : List<School> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        schoolNameItems.forEach { school ->
            SingleTextModel_().schoolName(school.name)
                .id(school.id)
                .addTo(this)
        }
    }
}