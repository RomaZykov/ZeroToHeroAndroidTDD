package ru.easycode.zerotoheroandroidtdd.presentation

data class ItemUi(val id: Long, val text: String) {
    fun areItemsSame(itemUi: ItemUi) = itemUi.id == id
}