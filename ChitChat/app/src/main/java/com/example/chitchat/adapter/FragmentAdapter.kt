package com.example.chitchat.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.chitchat.ui.FirstFragment
import com.example.chitchat.ui.FragmentSearch
import com.example.chitchat.ui.SecondFragment

class FragmentAdapter(fragmentManager:FragmentManager):FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        when(position){
            0->return FragmentSearch()
            2->return SecondFragment()
            1->return FragmentSearch()

        }
        return FirstFragment()
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0->return "Chats"
            2->return "Settings"
            1->return "Search"
        }
        return ""
    }
}