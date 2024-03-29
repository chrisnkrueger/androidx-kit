package app.moviebase.androidx.widget.recyclerview.diff

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class ItemDiffCallback<T : Any> : DiffUtil.ItemCallback<T>() {

    /**
     * Called to check whether two objects represent the same item.
     * For example, if your items have unique ids, this method should check their id equality.
     */
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return if (oldItem is Diffable)
            oldItem.isItemTheSame(newItem)
        else
            oldItem == newItem
    }

    /**
     * This information is used to detect if the contents of an item have changed.
     */
    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return if (oldItem is Diffable)
            oldItem.isContentTheSame(newItem)
        else
            oldItem == newItem
    }

    /**
     * When {@link #areItemsTheSame(int, int)} returns {@code true} for two items and
     * {@link #areContentsTheSame(int, int)} returns false for them, DiffUtil
     * calls this method to get a payload about the change.
     */
    override fun getChangePayload(oldItem: T, newItem: T): Any? {
        return if (oldItem is Diffable)
            oldItem.getChangePayload(newItem)
        else
            null
    }
}
