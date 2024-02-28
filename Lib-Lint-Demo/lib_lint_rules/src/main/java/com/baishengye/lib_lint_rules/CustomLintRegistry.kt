package com.baishengye.lib_lint_rules

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import com.baishengye.lib_lint_rules.detctors.AppcompatActivityDetector
import com.baishengye.lib_lint_rules.detctors.DialogFragmentDetector
import com.baishengye.lib_lint_rules.detctors.LayoutNameDetector
import com.baishengye.lib_lint_rules.detctors.LogDetector
import com.baishengye.lib_lint_rules.detctors.MsgObtainDetector
import com.baishengye.lib_lint_rules.detctors.NewThreadDetector
import com.baishengye.lib_lint_rules.detctors.SerialDetector
import com.baishengye.lib_lint_rules.detctors.ViewIdDetector

class CustomLintRegistry : IssueRegistry() {
    override val issues: List<Issue>
        get() = mutableListOf<Issue>().apply {
            println("==== my lint start ====")
            println("api=$api,minApi=$minApi,CurrentApi=$CURRENT_API")

            add(LogDetector.ISSUE)
            add(NewThreadDetector.ISSUE_NEW_THREAD)
            add(MsgObtainDetector.ISSUE)
            add(ViewIdDetector.ISSUE)
            add(LayoutNameDetector.ACTIVITY_LAYOUT_NAME_ISSUE)
            add(LayoutNameDetector.FRAGMENT_LAYOUT_NAME_ISSUE)
            add(SerialDetector.ISSUE)
            add(AppcompatActivityDetector.ISSUE)
            add(DialogFragmentDetector.ISSUE)
        }

    override val api: Int get() = CURRENT_API

    override val minApi: Int get() = 8 // works with Studio 4.1 or later; see com.android.tools.lint.detector.api.Api / ApiKt

    // Requires lint API 30.0+; if you're still building for something
    // older, just remove this property.
    override val vendor: Vendor = Vendor()
}