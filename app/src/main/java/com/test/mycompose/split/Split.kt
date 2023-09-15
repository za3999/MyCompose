package com.test.mycompose.split

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.window.embedding.*

private val second2PrimaryMap = mutableMapOf<Class<out Activity>, Class<out Activity>>()
private val existActivityMaps = mutableMapOf<Class<out Activity>, ArrayList<Activity>>()

fun Context.startActivity(clazz: Class<out Activity>, clearOthers: Boolean = false, options: Bundle? = null) {
    startActivity(Intent(this, clazz), options)
    second2PrimaryMap[clazz]?.let { it ->
        if (clearOthers) {
            existActivityMaps[it]?.let { it ->
                it.forEach {
                    if (it::class.java == clazz) return@forEach
                    it.finish()
                }
            }
        }
    }
}

fun initSplitRules(application: Application) {
    var controller = RuleController.getInstance(application)
    SplitConfig.SPLIT_LIST.forEach {
        val attributes = SplitAttributes.Builder().setSplitType(SplitAttributes.SplitType.ratio(it.ratio))
            .setLayoutDirection(SplitAttributes.LayoutDirection.LEFT_TO_RIGHT).build()
        var secondSet = hashSetOf<SplitPairFilter>().apply {
            it.secondClassList.forEach { second ->
                add(SplitPairFilter(ComponentName(application, it.primaryClass), ComponentName(application, second), null))
                second2PrimaryMap[second] = it.primaryClass
            }
        }
        controller.addRule(SplitPairRule.Builder(secondSet).setDefaultSplitAttributes(attributes).setMinWidthDp(840).setMinSmallestWidthDp(600)
            .setMaxAspectRatioInPortrait(EmbeddingAspectRatio.ratio(1.5f)).setFinishPrimaryWithSecondary(SplitRule.FinishBehavior.NEVER)
            .setFinishSecondaryWithPrimary(SplitRule.FinishBehavior.ALWAYS).setClearTop(true).build())
        it.placeholderCLass?.let { holder ->
            val placeholderActivityFilter = ActivityFilter(componentName = ComponentName(application, it.primaryClass), intentAction = null)
            controller.addRule(SplitPlaceholderRule.Builder(setOf(placeholderActivityFilter), Intent(application, holder))
                .setDefaultSplitAttributes(attributes).setMinWidthDp(840).setMinSmallestWidthDp(600)
                .setMaxAspectRatioInPortrait(EmbeddingAspectRatio.ratio(1.5f)).setFinishPrimaryWithPlaceholder(SplitRule.FinishBehavior.ALWAYS)
                .setSticky(false).build())
        }
    }
    SplitConfig.EXPANDED_LIST.forEach {
        val filter = ActivityFilter(ComponentName(application, it), null)
        controller.addRule(ActivityRule.Builder(setOf(filter)).setAlwaysExpand(true).build())
    }
    application.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {

        override fun onActivityCreated(activity: Activity, p1: Bundle?) {
            second2PrimaryMap[activity::class.java]?.let {
                if (existActivityMaps[it] == null) existActivityMaps[it] = ArrayList()
                existActivityMaps[it]?.add(activity)
            }
        }

        override fun onActivityDestroyed(activity: Activity) {
            second2PrimaryMap[activity::class.java]?.let { existActivityMaps[it]?.remove(activity) }
        }

        override fun onActivityStarted(activity: Activity) {
        }

        override fun onActivityResumed(activity: Activity) {
        }

        override fun onActivityPaused(activity: Activity) {
        }

        override fun onActivityStopped(activity: Activity) {
        }

        override fun onActivitySaveInstanceState(activity: Activity, p1: Bundle) {
        }
    })
}

