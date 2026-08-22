# Chatbot UI Test Plan

The expected output blocks contain only the chatbot's output. The test runner separately records the console input used for each session.

## Test: Level 4 task types and status changes

### Aim
Confirm that todo, deadline, and event tasks are stored, displayed, marked, and unmarked correctly.

### Input
```text
todo read book
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
list
mark 2
unmark 2
list
bye
```

### Expected output
```text
     _             _   _                 
    | | ___  _ __ | |_| |__   __ _ _ __  
 _  | |/ _ \| '_ \| __| '_ \ / _` | '_ \ 
| |_| | (_) | | | | |_| | | | (_| | | | |
 \___/ \___/|_| |_|\__|_| |_|\__,_|_| |_|

____________________________________________________________
Hello! I'm Jonathan.
What can I do for you?
____________________________________________________________
____________________________________________________________
Got it! I added this task: 
  [T][ ] read book
Now you have 1 tasks in the list
____________________________________________________________
____________________________________________________________
Got it! I added this task: 
  [D][ ] return book (by: Sunday)
Now you have 2 tasks in the list
____________________________________________________________
____________________________________________________________
Got it! I added this task: 
  [E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 3 tasks in the list
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] read book
2.[D][ ] return book (by: Sunday)
3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
[D][X] return book (by: Sunday)
____________________________________________________________
____________________________________________________________
OK, I've marked this task as not done yet:
[D][ ] return book (by: Sunday)
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] read book
2.[D][ ] return book (by: Sunday)
3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test: Basic task regression

### Aim
Confirm that an ordinary task can still be added, marked, listed, and exited.

### Input
```text
read book
mark 1
list
bye
```

### Expected output
```text
     _             _   _                 
    | | ___  _ __ | |_| |__   __ _ _ __  
 _  | |/ _ \| '_ \| __| '_ \ / _` | '_ \ 
| |_| | (_) | | | | |_| | | | (_| | | | |
 \___/ \___/|_| |_|\__|_| |_|\__,_|_| |_|

____________________________________________________________
Hello! I'm Jonathan.
What can I do for you?
____________________________________________________________
____________________________________________________________
added: read book
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
[X] read book
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[X] read book
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```
