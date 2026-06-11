# Task 6 - Topic Management Evidence

## Scope

The Manage pane supports creating, renaming, deleting, and selecting topics through stable selectors.

## Evidence

- Required implementation tags: `create-topic-button`, `rename-topic-button-{id}`, `delete-topic-button-{id}`.
- Instrumented tests cover topic list rendering, create dialog, rename dialog, delete confirmation, validation, and topic selection in `ManagePaneTest`.
- State tests cover topic dialog and mutation behavior in `ArchiveAssistantStateStoreTest`.

## Verification

- Covered by `./gradlew testDebugUnitTest` and `./gradlew compileDebugAndroidTestKotlin` in final verification.
