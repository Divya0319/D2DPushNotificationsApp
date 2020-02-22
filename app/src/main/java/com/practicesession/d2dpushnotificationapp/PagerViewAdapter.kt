package com.practicesession.d2dpushnotificationapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PagerViewAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                ProfileFragment()
            }
            1 -> {
                UsersFragment()
            }
            2 -> {
                NotificationsFragment()
            }
            else -> Fragment()
        }
    }

    override fun getCount(): Int {
        return 3
    }
}