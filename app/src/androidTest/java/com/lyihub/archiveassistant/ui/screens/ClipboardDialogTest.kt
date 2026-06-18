package com.lyihub.archiveassistant.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class ClipboardDialogTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun clipboardDialog_dragSourceLabel_showsDragTitle() {
        composeRule.setContent {
            ClipboardDialog(
                content = "test content",
                sourceLabel = "拖拽",
                onSummarize = {},
                onManualCreate = {},
                onDismiss = {},
            )
        }
        composeRule.onNodeWithText("检测到拖拽内容").assertIsDisplayed()
    }

    @Test
    fun clipboardDialog_nullSourceLabel_showsClipboardTitle() {
        composeRule.setContent {
            ClipboardDialog(
                content = "test content",
                sourceLabel = null,
                onSummarize = {},
                onManualCreate = {},
                onDismiss = {},
            )
        }
        composeRule.onNodeWithText("检测到剪切板内容").assertIsDisplayed()
    }
}
