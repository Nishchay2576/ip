# Minion - User Guide

Minion is a **desktop app for managing tasks, optimized for use via a Command Line Interface (CLI)**. If you can type fast, Minion can get your task management done faster than traditional GUI apps.

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
2. Download the latest `minion.jar`.
3. Copy the file to the folder you want to use as the home folder for your task list.
4. Open a command terminal, `cd` into the folder, and use the `java -jar minion.jar` command to run the application.
5. Type the command in the command box and press Enter to execute it.
   Some example commands you can try:
    * `list` : Lists all tasks.
    * `todo eat banana` : Adds a new todo task to the list.
    * `delete 1` : Deletes the 1st task in the current list.
    * `bye` : Exits the app.
6. Refer to the [Features](#features) below for details of each command.

---

## Features

> **Notes about the command format:**
> * Words in `UPPER_CASE` are the parameters to be supplied by the user.
    >   e.g. in `todo DESCRIPTION`, `DESCRIPTION` is a parameter which can be used as `todo read textbook`.
> * Parameters must be supplied in the order shown in the format.
> * Extraneous parameters for commands that do not take in parameters (such as `list` and `bye`) will be ignored.
    >   e.g. if the command specifies `bye 123`, it will be interpreted as `bye`.
> * If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines, as space characters surrounding line-breaks may be omitted when copied over to the application.

### Adding a Todo : `todo`
Adds a standard task without any date or time attached to it.

**Format:** `todo DESCRIPTION`

**Example:**
* `todo read textbook`

### Adding a Deadline : `deadline`
Adds a task that needs to be done before a specific date or time.

**Format:** `deadline DESCRIPTION /by DATE_TIME`

> 💡 **Tip:** Minion understands dates and times in `yyyy-MM-dd` or `HH:mm` formats and will automatically reformat them for you!

**Examples:**
* `deadline return library book /by 2026-03-15`
* `deadline submit lab /by 2026-03-20 23:59`

### Adding an Event : `event`
Adds a task that starts at a specific time and ends at a specific time.

**Format:** `event DESCRIPTION /from START_TIME /to END_TIME`

**Example:**
* `event project meeting /from 2026-03-10 14:00 /to 16:00`

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
* `list` followed by `mark 2` marks the 2nd task in the list as done.

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
* `delete 3` removes the 3rd task in the task list.

### Locating Tasks by Name : `find`
Finds all tasks whose descriptions contain the specified keyword.

**Format:** `find KEYWORD`
* The search is case-insensitive. e.g. `book` will match `Book`.
* Only the task description is searched.
* Tasks containing the keyword anywhere in their description will be returned.

**Example:**
* `find book` returns `read book` and `return library book`.

### Exiting the Program : `bye`
Exits the program and automatically saves your task list to the hard disk.

**Format:** `bye`

### Saving the Data
Minion data is saved to the hard disk automatically after any command that changes the task list. There is no need to save manually. Data is stored in `data/minion.txt` in the same folder as your `.jar` file.

> ⚠️ **Caution:** Advanced users may edit the data file directly, but if the file format becomes invalid, Minion will discard all data and start with an empty task list on the next run. It is recommended to keep a backup before editing.

---

## FAQ

**Q**: How is my data saved?  
**A**: Minion data is saved automatically after every command that changes the list. There is no need to save manually. It is located in `data/minion.txt` in the same folder as your `.jar` file.

**Q**: How do I transfer my data to another computer?  
**A**: Install Minion on the other computer and replace the empty `data/minion.txt` file it creates with your existing data file.

**Q**: What happens if I accidentally delete a task?  
**A**: Deleted tasks cannot be recovered from within the app. It is recommended to keep periodic backups of your `data/minion.txt` file if this is a concern.

---

## Troubleshooting Tips

1. **If the `data/minion.txt` file is manually edited with an invalid format**, Minion will start with an empty task list on the next run. Always back up your data file before editing it directly.
2. **Dates and times must follow the `yyyy-MM-dd` or `HH:mm` formats** — other formats may not be recognised and could cause unexpected behaviour.

---

## Command Summary

| Action | Format, Examples |
|--------|------------------|
| **Todo** | `todo DESCRIPTION` <br> e.g., `todo finish homework` |
| **Deadline** | `deadline DESCRIPTION /by DATE_TIME` <br> e.g., `deadline return book /by 2026-03-15` |
| **Event** | `event DESCRIPTION /from START_TIME /to END_TIME` <br> e.g., `event meeting /from 14:00 /to 16:00` |
| **List** | `list` |
| **Mark** | `mark INDEX` <br> e.g., `mark 2` |
| **Unmark** | `unmark INDEX` <br> e.g., `unmark 1` |
| **Delete** | `delete INDEX` <br> e.g., `delete 3` |
| **Find** | `find KEYWORD` <br> e.g., `find book` |
| **Bye** | `bye` |
