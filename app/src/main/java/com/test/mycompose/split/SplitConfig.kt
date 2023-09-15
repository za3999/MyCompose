package com.test.mycompose.split

import android.app.Activity
import com.test.mycompose.*

internal data class SplitInfo(val primaryClass: Class<out Activity>, var secondClassList: List<Class<out Activity>>,
    var placeholderCLass: Class<out Activity>? = null)

internal object SplitConfig {

    internal val SPLIT_LIST = mutableListOf<SplitInfo>().apply {
        add(SplitInfo(primaryClass = MainActivity::class.java, secondClassList = mutableListOf<Class<out Activity>>().apply {
            add(SecondActivity::class.java)
            add(ThirdActivity::class.java)
            add(FourActivity::class.java)
        }, placeholderCLass = PlaceholderActivity::class.java))
    }

    internal val EXPANDED_LIST = mutableListOf<Class<out Activity>>().apply {
        add(ExpandedActivity::class.java)
    }
}