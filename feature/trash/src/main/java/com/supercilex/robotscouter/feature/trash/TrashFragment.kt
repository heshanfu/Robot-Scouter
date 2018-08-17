package com.supercilex.robotscouter.feature.trash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.supercilex.robotscouter.core.ui.FragmentBase
import kotlinx.android.synthetic.main.fragment_trash.*

internal class TrashFragment : FragmentBase() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = View.inflate(context, R.layout.fragment_trash, null)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        trashList.adapter = TrashListAdapter(TODO(), savedInstanceState, this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbar)
            checkNotNull(supportActionBar).setDisplayHomeAsUpEnabled(true)
        }
    }

    companion object {
        const val TAG = "TrashFragment"

        fun newInstance() = TrashFragment()
    }
}
