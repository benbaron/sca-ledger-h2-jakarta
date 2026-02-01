# UI Skeleton (JavaFX) — Notes for Codex

## Run
    mvn -q -Pui javafx:run

## Shell features
- Office-like MenuBar: File / Edit / Search / Run / Tools / Help
- ToolBar with common actions
- Left navigation tree (grouped like Outlook)
- Center workspace panel host
- Right inspector pane

## Interaction conventions (target)
- Double-left-click: open the selected object (txn, schedule item, account, fund, etc.)
- Right-click:
  - selects the object
  - offers a context menu
  - populates the inspector pane (details/journal)

## Keyboard accelerators (installed globally)
- Ctrl+S Save
- Ctrl+F Find
- Ctrl+N New
- Ctrl+C / Ctrl+V hooks exist
- Esc closes inspector (returns to default text)

## Panels included (placeholders)
- Ledger (txn table + split editor; split rows start minimal and can be added)
- Outstanding / Schedules
- Budget
- Inventory
- Assets & Depreciation
- Reports
- Chart of Accounts
- Funds
- Settings

## Open design question (needs user decision)
Some Excel pages combine input + output on one page.
For each such page, decide:
- keep combined in one panel
- or split into (Editor panel) + (Summary/Report panel)
