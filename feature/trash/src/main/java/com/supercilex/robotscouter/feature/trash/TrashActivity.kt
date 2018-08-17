package com.supercilex.robotscouter.feature.trash

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.transaction
import com.supercilex.robotscouter.Bridge
import com.supercilex.robotscouter.TrashActivityCompanion
import com.supercilex.robotscouter.core.RobotScouter
import com.supercilex.robotscouter.core.ui.ActivityBase
import org.jetbrains.anko.intentFor
import com.supercilex.robotscouter.R as RC

@Bridge
internal class TrashActivity : ActivityBase() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(RC.style.RobotScouter_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trash)
        if (savedInstanceState == null) {
            supportFragmentManager.transaction {
                add(R.id.trash, TrashFragment.newInstance(), TrashFragment.TAG)
            }
        }
    }

    override fun onShortcut(keyCode: Int, event: KeyEvent): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_DEL, KeyEvent.KEYCODE_FORWARD_DEL -> TODO()
            else -> return false
        }
        return true
    }

    companion object : TrashActivityCompanion {
        override fun createIntent(): Intent = RobotScouter.intentFor<TrashActivity>()
    }
}
