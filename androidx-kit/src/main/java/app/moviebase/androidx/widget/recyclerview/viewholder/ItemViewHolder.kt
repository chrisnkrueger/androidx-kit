package app.moviebase.androidx.widget.recyclerview.viewholder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import app.moviebase.androidx.widget.recyclerview.adapter.ItemAdapter

abstract class ItemViewHolder<T : Any>(
    val itemAdapter: ItemAdapter<T>,
    parent: ViewGroup,
    @LayoutRes resource: Int
) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(resource, parent, false)) {

    var item: T? = null

    protected val context: Context get() = itemView.context
    protected val isLastPosition get() = itemPosition == itemAdapter.getItemCount() - 1
    protected val itemCount get() = itemAdapter.getItemCount()

    private var itemPosition: Int? = null

    init {
        itemAdapter.config.onLongClickListener?.let { listener ->
            itemView.setOnLongClickListener {
                item?.let { listener.click(it) }
                item != null
            }
        }

        itemAdapter.config.onClickListener?.let { listener ->
            itemView.setOnClickListener {
                item?.let { listener.click(it, this) }
            }
        }
    }

    fun bindTo(value: T?, position: Int) {
        val oldValue = item
        if (oldValue != null) unbind(oldValue)
        item = value
        itemPosition = position
        bind(value)
    }

    protected abstract fun bind(value: T?)

    protected open fun unbind(value: T) {
    }
}
