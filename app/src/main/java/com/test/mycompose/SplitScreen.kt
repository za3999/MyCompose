package com.test.mycompose

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.window.embedding.*

fun splitInit(context: Context) {
    val splitAttributes = SplitAttributes.Builder().setSplitType(SplitAttributes.SplitType.ratio(0.33f))
        .setLayoutDirection(SplitAttributes.LayoutDirection.LEFT_TO_RIGHT).build()
    RuleController.getInstance(context).apply {
        addRule(getSplitPairRule(context, splitAttributes))
        addRule(getSplitPlaceholderRule(context, splitAttributes))
        addRule(getExpandedFilters(context))
    }
}

private fun getSplitPairRule(context: Context, attributes: SplitAttributes): SplitPairRule {
    val mainSplitFilter = SplitPairFilter(ComponentName(context, MainActivity::class.java), ComponentName(context, SecondActivity::class.java), null)
    val main2SplitFilter = SplitPairFilter(ComponentName(context, MainActivity::class.java), ComponentName(context, ThirdActivity::class.java), null)
    return SplitPairRule.Builder(setOf(mainSplitFilter, main2SplitFilter)).setDefaultSplitAttributes(attributes).setMinWidthDp(840)
        .setMinSmallestWidthDp(600).setMaxAspectRatioInPortrait(EmbeddingAspectRatio.ratio(1.5f))
        .setFinishPrimaryWithSecondary(SplitRule.FinishBehavior.NEVER).setFinishSecondaryWithPrimary(SplitRule.FinishBehavior.ALWAYS)
        .setClearTop(true).build()
}

private fun getSplitPlaceholderRule(context: Context, attributes: SplitAttributes): SplitPlaceholderRule {
    val placeholderActivityFilter = ActivityFilter(componentName = ComponentName(context, MainActivity::class.java), intentAction = null)
    return SplitPlaceholderRule.Builder(setOf(placeholderActivityFilter), Intent(context, PlaceholderActivity::class.java))
        .setDefaultSplitAttributes(attributes).setMinWidthDp(840).setMinSmallestWidthDp(600)
        .setMaxAspectRatioInPortrait(EmbeddingAspectRatio.ratio(1.5f)).setFinishPrimaryWithPlaceholder(SplitRule.FinishBehavior.ALWAYS)
        .setSticky(false).build()
}

private fun getExpandedFilters(context: Context): ActivityRule {
    val expandedActivityFilter = ActivityFilter(ComponentName(context, ExpandedActivity::class.java), null)
    return ActivityRule.Builder(setOf(expandedActivityFilter)).setAlwaysExpand(true).build()
}
