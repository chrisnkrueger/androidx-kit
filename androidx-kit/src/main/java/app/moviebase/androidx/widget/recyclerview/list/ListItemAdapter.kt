package app.moviebase.androidx.widget.recyclerview.list

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.moviebase.androidx.widget.recyclerview.glide.GlideAdapterHelper
import app.moviebase.androidx.widget.recyclerview.glide.GlideConfig
import app.moviebase.androidx.widget.recyclerview.glide.GlideItemAdapter
import app.moviebase.androidx.widget.recyclerview.viewholder.ItemViewHolder
import app.moviebase.androidx.widget.recyclerview.viewholder.Recyclable


class ListItemAdapter<T : Any>(
    override val config: ListItemAdapterConfig<T>
) : ListAdapter<T, ItemViewHolder<T>>(config.diffCallback), GlideItemAdapter<T> {

    override val glideConfig: GlideConfig<T> get() = config.glideConfig

    init {
        setHasStableIds(config.onItemId != null)
    }

    override fun getItemId(position: Int): Long {
        val item = getItem(position)
        return config.onItemId?.getItemId(item) ?: RecyclerView.NO_ID
    }

    override fun getItemBy(position: Int): T? = getItem(position)

    override fun onBindViewHolder(holder: ItemViewHolder<T>, position: Int) {
        val value: T? = getItem(position)
        holder.bindTo(value, position)
        GlideAdapterHelper.bindImageView(glideConfig, value, holder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<T> {
        val builder = config.viewHolders[viewType]
            ?: throw NoSuchElementException("factory for view type '$viewType' not available")

        val holder = builder.create(this, parent)
        GlideAdapterHelper.updateImageView(glideConfig, holder)
        return holder
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return config.onViewType.getViewType(item)
    }

    override fun onViewRecycled(holder: ItemViewHolder<T>) {
        if (holder is Recyclable) holder.recycle()
        GlideAdapterHelper.clearImageView(glideConfig, holder)
    }

    override fun onCurrentListChanged(previousList: MutableList<T>, currentList: MutableList<T>) {
        super.onCurrentListChanged(previousList, currentList)
        config.onListChanged?.invoke(currentList.isEmpty())
    }
}
