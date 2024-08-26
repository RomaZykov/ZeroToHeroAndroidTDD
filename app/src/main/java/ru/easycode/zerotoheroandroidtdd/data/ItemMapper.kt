package ru.easycode.zerotoheroandroidtdd.data

import ru.easycode.zerotoheroandroidtdd.data.cache.ItemCache
import ru.easycode.zerotoheroandroidtdd.presentation.ItemUi

class ItemMapper {

    fun mapItemDbToItem(itemCache: ItemCache) = Item(
        id = itemCache.id,
        text = itemCache.text
    )

    fun mapItemToItemDb(item: Item) = ItemCache(
        id = item.id,
        text = item.text
    )

    fun mapItemToItemUi(item: Item) = ItemUi(
        id = item.id,
        text = item.text
    )
}