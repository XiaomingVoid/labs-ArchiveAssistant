# Task 5 - Parser Classification Evidence

## Scope

The Home parser accepts text and triggers local-only classification through stable Compose selectors.

## Evidence

- Required implementation tags: `parser-input`, `classify-button`, `recent-topic-list`, `topic-card-{id}`, `settings-trigger`, `manage-button`.
- Instrumented tests cover parser input, classify action, recent topics, and topic card tags in `HomePaneTest`.
- Task 10 tightened the Home manage action to use the required `manage-button` tag and added UI test coverage for it.

## Verification

- Covered by `./gradlew compileDebugAndroidTestKotlin` and attempted `./gradlew connectedDebugAndroidTest` in final verification.
