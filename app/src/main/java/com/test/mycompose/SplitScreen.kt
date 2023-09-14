package com.test.mycompose

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.window.embedding.*

fun splitInit(context: Context) {
    val splitPairFilter = SplitPairFilter(ComponentName(context, MainActivity::class.java), ComponentName(context, SecondActivity::class.java), null)
    val splitAttributes: SplitAttributes = SplitAttributes.Builder().setSplitType(SplitAttributes.SplitType.ratio(0.33f))
        .setLayoutDirection(SplitAttributes.LayoutDirection.LEFT_TO_RIGHT).build()
    val splitPairRule =
        SplitPairRule.Builder(setOf(splitPairFilter)).setDefaultSplitAttributes(splitAttributes).setMinWidthDp(840).setMinSmallestWidthDp(600)
            .setMaxAspectRatioInPortrait(EmbeddingAspectRatio.ratio(1.5f)).setFinishPrimaryWithSecondary(SplitRule.FinishBehavior.ALWAYS)
            .setFinishSecondaryWithPrimary(SplitRule.FinishBehavior.ALWAYS).setClearTop(true).build()

    val placeholderActivityFilter = ActivityFilter(componentName = ComponentName(context, MainActivity::class.java), intentAction = null)
    val splitPlaceholderRule = SplitPlaceholderRule.Builder(setOf(placeholderActivityFilter), Intent(context, PlaceholderActivity::class.java))
        .setDefaultSplitAttributes(splitAttributes).setMinWidthDp(840).setMinSmallestWidthDp(600)
        .setMaxAspectRatioInPortrait(EmbeddingAspectRatio.ratio(1.5f)).setFinishPrimaryWithPlaceholder(SplitRule.FinishBehavior.ALWAYS)
        .setSticky(false).build()

    val ruleController = RuleController.getInstance(context)
    ruleController.addRule(splitPairRule)
    ruleController.addRule(splitPlaceholderRule)
}
