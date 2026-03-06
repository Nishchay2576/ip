# Minion - User Guide

Minion is a **desktop task manager built for speed**. Whether you're tracking deadlines, planning events, or just jotting down things to do, Minion keeps everything organized through a clean, keyboard-first command-line interface.

---

## Table of Contents
* [Quick Start](#quick-start)
* [Features](#features)
    * [Adding a Todo: `todo`](#adding-a-todo--todo)
    * [Adding a Deadline: `deadline`](#adding-a-deadline--deadline)
    * [Adding an Event: `event`](#adding-an-event--event)
    * [Listing all Tasks: `list`](#listing-all-tasks--list)
    * [Marking a Task: `mark`](#marking-a-task--mark)
    * [Unmarking a Task: `unmark`](#unmarking-a-task--unmark)
    * [Deleting a Task: `delete`](#deleting-a-task--delete)
    * [Locating Tasks by Name: `find`](#locating-tasks-by-name--find)
    * [Exiting the Program: `bye`](#exiting-the-program--bye)
    * [Saving the Data](#saving-the-data)
* [FAQ](#faq)
* [Troubleshooting Tips](#troubleshooting-tips)
* [Command Summary](#command-summary)

---

## Quick Start

1. Ensure you have **Java 17** installed in your Computer.
2. Download the latest `minion.jar` from the releases page.
3. Move it to a folder of your choice, this will become Minion's home directory.
4. Open a terminal, navigate to that folder, and run: `java -jar minion.jar` command to run the application.
5. Type any command in the command box and press Enter to execute it.
   Some example commands you can try:
    * `list` : Lists all tasks.
    * `todo eat banana` : Adds a new todo task to the list.
    * `delete 1` : Deletes the 1st task in the current list.
    * `bye` : Exits the app.
6. Refer to the [Features](#features) below for the full command reference.
---

## Features

> **Notes about the command format:**
> * Words in `UPPER_CASE` represent parameters you supply.
    >   e.g. `todo DESCRIPTION` becomes `todo read textbook`.
> * Parameters must follow the order shown in each format.
> * Commands that take no parameters (such as `list` and `bye`) must be entered on their own — adding extra input will result in an unrecognised command error.
    >   e.g. use `bye`, not `bye 123`.
> * Commands are case-insensitive. e.g. `LIST`, `List`, and `list` all work the same way.
> * If you're reading a PDF version of this guide, be careful when copying multi-line commands — spaces around line breaks may be stripped.


### Adding a Todo : `todo`
Adds a standard task without any date or time attached to it.

**Format:** `todo DESCRIPTION`

**Example:**
* `call the dentist`

### Adding a Deadline : `deadline`
Adds a task that needs to be done before a specific date or time.

**Format:** `deadline DESCRIPTION /by DATE_TIME`

> 💡 **Tip:** Minion understands dates and times in `yyyy-MM-dd` or `HH:mm` formats and will automatically reformat them for you!

**Examples:**
* `deadline submit progress report /by 2026-04-01`
* `deadline pay phone bill /by 2026-03-28 23:59`

### Adding an Event : `event`
Adds a task that starts at a specific time and ends at a specific time.

**Format:** `event DESCRIPTION /from START_TIME /to END_TIME`

**Example:**
* `event team standup /from 2026-04-02 09:00 /to 09:30`

### Listing all Tasks : `list`
Shows a complete list of all tasks currently in your task list.

**Format:** `list`

### Marking a Task : `mark`
Marks the specified task in the list as completed.

**Format:** `mark INDEX`
* Marks the task at the specified `INDEX`.
* The index refers to the index number shown in the displayed task list.
* The index **must be a positive integer** 1, 2, 3, …

**Example:**
* `list` followed by `mark 4` marks the 4th task in the list as done.

### Unmarking a Task : `unmark`
Marks the specified completed task in the list as incomplete.

**Format:** `unmark INDEX`
* The index **must be a positive integer** 1, 2, 3, …

**Example:**
* `unmark 1` marks the 1st task in the list as not done yet.

### Deleting a Task : `delete`
Deletes the specified task from the list permanently.

**Format:** `delete INDEX`
* The index **must be a positive integer** 1, 2, 3, …

**Example:**
* `delete 2` removes the 2nd task in the task list.

### Locating Tasks by Name : `find`
Finds all tasks whose descriptions contain the specified keyword.

**Format:** `find KEYWORD`
* The search is case-insensitive. e.g. `book` will match `Book`.
* Only the task description is searched.
* Tasks containing the keyword anywhere in their description will be returned.

**Example:**
* `find report` returns tasks like `write progress report` and `submit final report`.

### Exiting the Program : `bye`
Exits the program and automatically saves your task list to the hard disk.

**Format:** `bye`

### Saving the Data
Minion saves your task list automatically after every change, no manual saving needed. Your data is stored in `data/minion.txt`, located in the same folder as `minion.jar`.

> ⚠️ **Note for advanced users:** You may edit `minion.txt` directly, but any formatting errors will cause Minion to discard the file and start fresh on next launch. Always keep a backup before making direct edits.

---

---

## FAQ

**Q**: How is my data saved?  
**A**: Minion data is saved automatically after every command that changes the list. There is no need to save manually. It is located in `data/minion.txt` in the same folder as your `.jar` file.

**Q**: How do I transfer my data to another computer?  
**A**: Set up Minion on the new machine, then replace its `data/minion.txt` with your existing one.

**Q**: What happens if I accidentally delete a task?  
**A**: Deleted tasks cannot be recovered from within the app. It is recommended to keep periodic backups of your `data/minion.txt` file if this is a concern.

---

## Troubleshooting Tips

1. **Invalid data file format** — If `minion.txt` is edited incorrectly, Minion will start with an empty task list. Always back up the file before editing it by hand.
2. **Dates and times not recognised** — Make sure you're using `yyyy-MM-dd` for dates and `HH:mm` for times. Other formats may produce unexpected results.

---

## Command Summary

| Action | Format | Example |
|--------|--------|---------|
| **Todo** | `todo DESCRIPTION` | `todo call the dentist` |
| **Deadline** | `deadline DESCRIPTION /by DATE_TIME` | `deadline submit progress report /by 2026-04-01` |
| **Event** | `event DESCRIPTION /from START_TIME /to END_TIME` | `event team standup /from 09:00 /to 09:30` |
| **List** | `list` | — |
| **Mark** | `mark INDEX` | `mark 4` |
| **Unmark** | `unmark INDEX` | `unmark 3` |
| **Delete** | `delete INDEX` | `delete 2` |
| **Find** | `find KEYWORD` | `find report` |
| **Bye** | `bye` | — ||
