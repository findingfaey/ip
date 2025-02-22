# Arin Task Manager

Arin is a personal task management chatbot that helps you keep track of your todos, deadlines, and events through a friendly chat interface.

![Arin UI](Ui.png)

## Features

### 1. Task Management
* **Adding tasks:**
   * `todo DESCRIPTION` - Adds a todo task
   * `deadline DESCRIPTION /by YYYY-MM-DD HHmm` - Adds a deadline
   * `event DESCRIPTION /from YYYY-MM-DD HHmm /to YYYY-MM-DD HHmm` - Adds an event

* **Managing tasks:**
   * `list` - Shows all tasks
   * `mark INDEX` - Marks task as done
   * `unmark INDEX` - Marks task as not done
   * `delete INDEX` - Deletes a task

### 2. Finding Tasks
* `find KEYWORD` - Finds tasks containing the keyword
* `upcoming [days]` - Shows tasks due within specified days

### 3. Sorting Tasks
* `sort by date` - Sorts tasks chronologically
* `sort by name` - Sorts tasks alphabetically
* `sort by type` - Sorts by task type
* `sort by status` - Sorts by completion status

### 4. Other Commands
* `help` - Shows command guide
* `bye` - Exits the application