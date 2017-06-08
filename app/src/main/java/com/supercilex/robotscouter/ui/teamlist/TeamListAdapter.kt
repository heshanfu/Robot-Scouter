package com.supercilex.robotscouter.ui.teamlist

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.View
import com.firebase.ui.database.ChangeEventListener
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.crash.FirebaseCrash
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.supercilex.robotscouter.R
import com.supercilex.robotscouter.data.model.Team
import com.supercilex.robotscouter.ui.CardListHelper
import com.supercilex.robotscouter.util.Constants
import com.supercilex.robotscouter.util.getAdapterItems

class TeamListAdapter(private val fragment: Fragment,
                      private val menuManager: TeamMenuManager,
                      savedInstanceState: Bundle?) :
        FirebaseRecyclerAdapter<Team, TeamViewHolder>(
                Constants.sFirebaseTeams, R.layout.team_list_row_layout, TeamViewHolder::class.java) {
    private val cardListHelper: CardListHelper = CardListHelper(
            this,
            fragment.view!!.findViewById(R.id.list))
    private var noTeamsHint: View? = null

    private var selectedTeamKey: String? = savedInstanceState?.getString(TEAM_KEY)

    fun updateSelection(teamKey: String?) {
        if (TextUtils.isEmpty(teamKey)) {
            if (!TextUtils.isEmpty(selectedTeamKey)) {
                for (i in 0 until itemCount) {
                    if (TextUtils.equals(selectedTeamKey, getItem(i).key)) {
                        notifyItemChanged(i)
                        break
                    }
                }
            }
        } else {
            for (i in 0 until itemCount) {
                if (TextUtils.equals(teamKey, getItem(i).key)) {
                    notifyItemChanged(i)
                    break
                }
            }
        }

        selectedTeamKey = teamKey
    }

    fun onSaveInstanceState(outState: Bundle) = outState.putString(TEAM_KEY, selectedTeamKey)

    public override fun populateViewHolder(teamHolder: TeamViewHolder, team: Team, position: Int) {
        cardListHelper.onBind(teamHolder)
        teamHolder.bind(team,
                fragment,
                menuManager,
                menuManager.selectedTeams.contains(team.helper),
                !menuManager.selectedTeams.isEmpty(),
                TextUtils.equals(selectedTeamKey, team.key))

        showNoTeamsHint()
    }

    override fun onChildChanged(type: ChangeEventListener.EventType,
                                snapshot: DataSnapshot?,
                                index: Int,
                                oldIndex: Int) {
        showNoTeamsHint()

        if (type == ChangeEventListener.EventType.CHANGED) {
            for (oldTeam in menuManager.selectedTeams) {
                val team = getItem(index)
                if (TextUtils.equals(oldTeam.team.key, team.key)) {
                    menuManager.onSelectedTeamChanged(oldTeam, team.helper)
                    break
                }
            }
        } else if (type == ChangeEventListener.EventType.REMOVED && !menuManager.selectedTeams.isEmpty()) {
            val tmpTeams = getAdapterItems(this)
            for (oldTeamHelper in menuManager.selectedTeams) {
                if (!tmpTeams.contains(oldTeamHelper.team)) { // We found the deleted item
                    menuManager.onSelectedTeamRemoved(oldTeamHelper)
                    break
                }
            }
        }
        super.onChildChanged(type, snapshot, index, oldIndex)
    }

    private fun showNoTeamsHint() {
        if (noTeamsHint == null) {
            @Suppress("UNNECESSARY_SAFE_CALL")
            noTeamsHint = fragment?.view?.findViewById(R.id.no_content_hint)
        }

        noTeamsHint?.visibility = if (itemCount == 0) View.VISIBLE else View.GONE
    }

    override fun onCancelled(error: DatabaseError) = FirebaseCrash.report(error.toException())

    companion object {
        private const val TEAM_KEY = "team_key"
    }
}
