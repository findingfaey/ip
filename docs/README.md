# Arin Task Manager

Arin is a personal task management chatbot that helps you keep track of your todos, deadlines, and events through a friendly chat interface.

![Arin UI](Ui.png)

## Features

### 1. Task Management

<details>
<summary>💼 Task Creation</summary>

#### Adding Different Task Types
* `todo DESCRIPTION` - Adds a todo task
* `deadline DESCRIPTION /by YYYY-MM-DD HHmm` - Adds a deadline
* `event DESCRIPTION /from YYYY-MM-DD HHmm /to YYYY-MM-DD HHmm` - Adds an event

<table>
  <tr>
    <td><img src="../src/main/resources/images/add-todo.png" width="400" alt="Adding a todo task"><br><em>Adding a todo task</em></td>
    <td><img src="../src/main/resources/images/add-deadline.png" width="400" alt="Adding a deadline task"><br><em>Adding a deadline task</em></td>
  </tr>
  <tr>
    <td><img src="../src/main/resources/images/add-event.png" width="400" alt="Adding an event"><br><em>Adding an event</em></td>
    <td><img src="../src/main/resources/images/list-tasks.png" width="400" alt="Viewing all tasks"><br><em>Viewing all tasks</em></td>
  </tr>
</table>
</details>

<details>
<summary>✏️ Task Management</summary>

#### Managing Tasks
* `list` - Shows all tasks
* `mark INDEX` - Marks task as done (INDEX starts from 1)
* `unmark INDEX` - Marks task as not done
* `delete INDEX` - Deletes a task

<table>
  <tr>
    <td><img src="../src/main/resources/images/mark-task.png" width="400" alt="Marking tasks as complete"><br><em>Marking tasks as complete</em></td>
    <td><img src="../src/main/resources/images/unmark-task.png" width="400" alt="Unmarking tasks"><br><em>Unmarking tasks</em></td>
  </tr>
</table>
</details>

### 2. Finding Tasks

<details>
<summary>🔍 Search & Filter</summary>

#### Search Options
* `find KEYWORD` - Finds tasks containing the keyword
* `upcoming [days]` - Shows tasks due within specified days (default: 7 days)

<table>
  <tr>
    <td><img src="../src/main/resources/images/find-task.png" width="400" alt="Finding tasks by keyword"><br><em>Finding tasks by keyword</em></td>
    <td><img src="../src/main/resources/images/upcoming-tasks.png" width="400" alt="Viewing upcoming tasks"><br><em>Viewing upcoming tasks</em></td>
  </tr>
</table>
</details>

### 3. Sorting Tasks

<details>
<summary>🔄 Sorting Options</summary>

#### Available Sorting Criteria
* `sort by date` - Sorts tasks chronologically
* `sort by name` - Sorts tasks alphabetically
* `sort by type` - Sorts by task type (ToDo → Deadline → Event)
* `sort by status` - Sorts by completion status

<table>
  <tr>
    <td><img src="../src/main/resources/images/sort-date.png" width="400" alt="Sorting by date"><br><em>Sorting by date</em></td>
    <td><img src="../src/main/resources/images/sort-name.png" width="400" alt="Sorting by name"><br><em>Sorting by name</em></td>
  </tr>
  <tr>
    <td><img src="../src/main/resources/images/sort-type.png" width="400" alt="Sorting by type"><br><em>Sorting by type</em></td>
    <td><img src="../src/main/resources/images/sort-status.png" width="400" alt="Sorting by status"><br><em>Sorting by status</em></td>
  </tr>
</table>
</details>

### 4. Help Command

<details>
<summary>❓ Getting Help</summary>

#### Help Features
* `help` - Shows all available commands and their usage
* `bye` - Exits the application

<p align="center">
  <img src="../src/main/resources/images/help-command.png" width="600" alt="Help command display">
  <br>
  <em>Comprehensive help message showing all available commands</em>
</p>
</details>

## Usage Notes

* Task indices start from 1 (e.g., use `mark 1` to mark the first task as done)
* Dates should be in the format YYYY-MM-DD HHmm (e.g., 2025-02-23 2359)
* For events, ensure the end time is after the start time

