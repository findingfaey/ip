# Arin Task Manager

A personal task management chatbot that helps you keep track of your todos, deadlines, and events through a friendly chat interface.

<p align="center">
  <img src="https://raw.githubusercontent.com/findingfaey/ip/master/docs/Ui.png" alt="Arin UI">
</p>

## Table of Contents
- [Features](#features)
  - [Task Management](#1-task-management)
  - [Finding Tasks](#2-finding-tasks)
  - [Sorting Tasks](#3-sorting-tasks)
  - [Help Command](#4-help-command)
- [Usage Notes](#usage-notes)

## Features

### 1. Task Management

#### 💼 Task Creation

##### Adding Different Task Types

- `todo DESCRIPTION` - Adds a todo task
- `deadline DESCRIPTION /by YYYY-MM-DD HHmm` - Adds a deadline
- `event DESCRIPTION /from YYYY-MM-DD HHmm /to YYYY-MM-DD HHmm` - Adds an event

<p align="center">
  <img src="https://raw.githubusercontent.com/findingfaey/ip/master/src/main/resources/images/add-todo.png" alt="Adding a todo task">
</p>

<p align="center"><em>Adding a todo task</em></p>

<p align="center">
  <img src="https://raw.githubusercontent.com/findingfaey/ip/master/src/main/resources/images/add-deadline.png" alt="Adding a deadline task">
</p>

<p align="center"><em>Adding a deadline task</em></p>

<p align="center">
  <img src="https://raw.githubusercontent.com/findingfaey/ip/master/src/main/resources/images/add-event.png" alt="Adding an event">
</p>

<p align="center"><em>Adding an event</em></p>

<p align="center">
  <img src="https://raw.githubusercontent.com/findingfaey/ip/master/src/main/resources/images/list-tasks.png" alt="List of all tasks">
</p>

<p align="center"><em>List of all tasks</em></p>

#### ✏️ Task Management

##### Managing Tasks

- `list` - Shows all tasks
- `mark INDEX` - Marks task as done (INDEX starts from 1)
- `unmark INDEX` - Marks task as not done
- `delete INDEX` - Deletes a task

**Note**: Task indices start from 1. For example, use `mark 1` to mark the first task as done.

<p align="center">
  <img src="https://raw.githubusercontent.com/findingfaey/ip/master/src/main/resources/images/mark-task.png" alt="Marking tasks as complete">
</p>

<p align="center"><em>Marking tasks as complete</em></p>

<p align="center">
  <img src="https://raw.githubusercontent.com/findingfaey/ip/master/src/main/resources/images/unmark-task.png" alt="Unmarking tasks">
</p>

<p align="center"><em>Unmarking tasks</em></p>

### 2. Finding Tasks

#### 🔍 Search & Filter

##### Search Options
```
find KEYWORD     - Finds tasks containing the keyword
upcoming [days]  - Shows tasks due within specified days (default: 7 days)
```

**Tip**: Use `upcoming` without any number to see tasks due within the next week.

<p align="center">
  <img src="https://raw.githubusercontent.com/findingfaey/ip/master/src/main/resources/images/find-task.png" alt="Finding tasks by keyword">
</p>

<p align="center"><em>Finding tasks by keyword</em></p>

<p align="center">
  <img src="https://raw.githubusercontent.com/findingfaey/ip/master/src/main/resources/images/upcoming-tasks.png" alt="Viewing upcoming tasks">
</p>

<p align="center"><em>Viewing upcoming tasks</em></p>

### 3. Sorting Tasks

#### 🔄 Sorting Options

##### Available Sorting Criteria

- `sort by date` - Sorts tasks chronologically
- `sort by name` - Sorts tasks alphabetically
- `sort by type` - Sorts by task type (ToDo → Deadline → Event)
- `sort by status` - Sorts by completion status

<p align="center">
  <img src="https://raw.githubusercontent.com/findingfaey/ip/master/src/main/resources/images/sort-date.png" alt="Sorting by date">
</p>

<p align="center"><em>Sorting by date</em></p>

<p align="center">
  <img src="https://raw.githubusercontent.com/findingfaey/ip/master/src/main/resources/images/sort-name.png" alt="Sorting by name">
</p>

<p align="center"><em>Sorting by name</em></p>

<p align="center">
  <img src="https://raw.githubusercontent.com/findingfaey/ip/master/src/main/resources/images/sort-type.png" alt="Sorting by type">
</p>

<p align="center"><em>Sorting by type</em></p>

<p align="center">
  <img src="https://raw.githubusercontent.com/findingfaey/ip/master/src/main/resources/images/sort-status.png" alt="Sorting by status">
</p>

<p align="center"><em>Sorting by status</em></p>

### 4. Help Command

#### ❓ Getting Help

##### Help Features

- `help` - Shows all available commands and their usage
- `bye` - Exits the application

<p align="center">
  <img src="https://raw.githubusercontent.com/findingfaey/ip/master/src/main/resources/images/help-command.png" alt="Help command display">
</p>

<p align="center"><em>Comprehensive help message showing all available commands</em></p>

## Usage Notes

### Date and Time Format
- Use `YYYY-MM-DD HHmm` format
  - Example: `2025-02-23 2359`
- For events:
  - Start time must be before end time
  - Use `/from` and `/to` with spaces

### Important Tips
- Task indices start from 1
- Commands are case-sensitive
- Use `help` to see all available commands
- Use `bye` to exit the application

---
