# Proton Mobile Dev Test


## Overview
This exercise is meant as a chance to showcase your coding skills as well as
how you approach refactoring and developing new features in an app.
While how much time you invest in the task is up to you, 8 hours
is what we consider to be a reasonable amount to dedicate to it.

Your task is to take the provided sample app and improve it based on the basic
description in this document, as well as your own expectation of code quality.


## Task
The purpose of this app is to retrieve a basic "tasks" list from a RESTful API and
present that information in master and detail screens.

Some parts of the app are already implemented, even though they were done as an
old POC and will likely need some changes in order to get up to a production standard.

Please feel free to use, refactor or discard any part of the existing codebase
as you see fit, considering that we expect this app to be maintained
and extended in the long term.

## Requirements
- The two screens of the app (master / detail) should follow the structure defined below.
- Tasks data should be downloaded at app launch and stored for offline use.
- Images should only be downloaded on request by the user.
- All data that is encrypted in the API response should be also encrypted when stored locally. Decryption should be performed on the fly when presenting data to the user.

### Design specifications
https://www.figma.com/file/KNDguKE7081VZo8ILRr1bR/Android-Dev-task?node-id=0%3A1

## Security
You are provided with a library `CryptoLib` which must be used to decrypt content from the backend.

For the purpose of this exercise, we'll assume that everything stored in clear text on the device is considered compromised.

### Master Screen
The master screen should have “Proton Test” in the navigation bar.

Below the navigation bar should be a pair of tabs with the options “All tasks” and “Upcoming”.
Below these buttons should be a table view displaying the relevant tasks and in the correct order.
- Upcoming: All upcoming tasks returned by the API ordered by due date.
- All tasks: All tasks ordered by creation date.

The rows should be in the format “Task \<id>: \<decrypted description>” where id and description are
returned by the API.
If the image for a particular task is downloaded (initiated by the user in the detail screen), then
the row should be updated with a thumbnail.

### Detail Screen
The navigation bar title should be in the form “Task \<id>”. Below the navigation bar
you should display the image (once downloaded), and below the image the task data should
be laid out as per design using appropriate units and values.

Display a “Download Image” button aligned to the bottom of the screen, which should disappear upon
successful download.
