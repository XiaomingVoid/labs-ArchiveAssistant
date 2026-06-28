package com.lyihub.archiveassistant.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.viewinterop.AndroidView
import com.lyihub.archiveassistant.ui.components.XuanPaperBackground

@Composable
fun MemorialDemoOverlay(onDismiss: () -> Unit) {
    val foldView = remember { mutableStateOf<MemorialFoldView?>(null) }
    val dismissStarted = remember { mutableStateOf(false) }
    val completeDismiss = {
        onDismiss()
    }
    val autoDismiss = {
        if (!dismissStarted.value) {
            dismissStarted.value = true
            onDismiss()
        }
    }
    val requestDismiss = {
        if (!dismissStarted.value) {
            dismissStarted.value = true
            foldView.value?.closeWithAnimation(completeDismiss) ?: completeDismiss()
        }
    }

    BackHandler(onBack = requestDismiss)
    MemorialImmersiveSystemUi(onDispose = { foldView.value = null })

    XuanPaperBackground(
        modifier = Modifier
            .fillMaxSize()
            .testTag("memorial-demo-overlay"),
        textureAlpha = 0.2f,
        veilAlpha = 0.72f,
    ) {
        AndroidView(
            factory = { context ->
                MemorialFoldView(context).apply {
                    foldView.value = this
                    setAutoDismissHandler(autoDismiss)
                    setPages(memorialPages)
                }
            },
            update = { view ->
                foldView.value = view
                view.setAutoDismissHandler(autoDismiss)
                view.setPages(memorialPages)
            },
            modifier = Modifier.fillMaxSize(),
        )
    }
}
