package com.supercilex.robotscouter.feature.trash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.ObservableSnapshotArray
import com.supercilex.robotscouter.core.ui.SavedStateAdapter
import com.supercilex.robotscouter.core.ui.find
import kotlinx.android.extensions.LayoutContainer

internal class TrashListAdapter(
        items: ObservableSnapshotArray<TrashItem>,
        savedInstanceState: Bundle?,
        fragment: Fragment
) : SavedStateAdapter<TrashItem, TrashViewHolder>(
        FirestoreRecyclerOptions.Builder<TrashItem>()
                .setSnapshotArray(items)
                .setLifecycleOwner(fragment)
                .build(),
        savedInstanceState,
        fragment.find(R.id.trashList)
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TrashViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.trash_item, parent, false)
    )

    override fun onBindViewHolder(holder: TrashViewHolder, position: Int, item: TrashItem) =
            holder.bind(item)
}

internal data class TrashItem(val id: String, val type: Any /* TODO */, val name: String?)

internal class TrashViewHolder(
        override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bind(item: TrashItem) {

    }
}
