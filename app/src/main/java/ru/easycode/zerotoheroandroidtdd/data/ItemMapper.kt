package ru.easycode.zerotoheroandroidtdd.data

import ru.easycode.zerotoheroandroidtdd.data.cache.ItemCache

class ItemMapper {

    fun mapItemDbToItem(itemCache: ItemCache) = Item(
        id = itemCache.id,
        text = itemCache.text
    )

    fun mapItemToItemDb(item: Item) = ItemCache(
        id = item.id,
        text = item.text
    )
}