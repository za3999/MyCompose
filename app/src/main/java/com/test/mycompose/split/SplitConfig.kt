package com.test.mycompose.split

import android.app.Activity
import androidx.annotation.FloatRange
import com.test.mycompose.*

internal data class SplitInfo(val primaryClass: Class<out Activity>, var secondClassList: List<Class<out Activity>>,
    @FloatRange(from = 0.0, to = 1.0, fromInclusive = false, toInclusive = false) var ratio: Float = 0.33f,
    var placeholderCLass: Class<out Activity>? = null)

internal object SplitConfig {

    internal val SPLIT_LIST = mutableListOf<SplitInfo>().apply {
        add(SplitInfo(primaryClass = MainActivity::class.java, secondClassList = mutableListOf<Class<out Activity>>().apply {
            add(SecondActivity::class.java)
            add(ThirdActivity::class.java)
            add(FourActivity::class.java)
        }, placeholderCLass = PlaceholderActivity::class.java))

        add(SplitInfo(primaryClass = ThirdActivity::class.java, secondClassList = mutableListOf<Class<out Activity>>().apply {
            add(FourActivity::class.java)
        }))
    }

    internal val EXPANDED_LIST = mutableListOf<Class<out Activity>>().apply {
        add(ExpandedActivity::class.java)
    }
}