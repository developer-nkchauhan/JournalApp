# Journal App

[![Language: Kotlin](https://img.shields.io/badge/Language-Kotlin-blue.svg)](https://kotlinlang.org/)

## Overview

This project is a Journal Application that allows users to sign up and sign in using Firebase Authentication. Users can view a list of journals along with their descriptions, and they have the ability to add new journals, including uploading images. All data is securely stored in Firebase Firestore and Firebase Storage.

## Features

- **User Authentication**: 
  - Sign up and sign in using Firebase Authentication.
  
- **Journal Management**:
  - Display a list of journals tailored to each user.
  - Each journal includes a description.
  
- **Image Upload**:
  - Users can upload image associated with their journals.
  - Images are stored in Firebase Storage for easy retrieval.

## Components Used

This project utilizes various components to create a robust and user-friendly journal application:

- **RecyclerView**: A powerful component for displaying large sets of data in a scrollable list, efficiently reusing item views to improve performance.

- **Navigation Component**: Manages fragment transactions and navigation within the app, simplifying the implementation of navigation patterns.

- **Fragment**: Represents a portion of the user interface in an activity, allowing for better organization and reuse across different screens.

- **Single Activity Architecture**: Uses a single activity to host multiple fragments, promoting modularization of the codebase.

- **Firebase Authentication**: Provides secure user authentication, allowing users to sign up and log in using email and password.

- **Firebase Firestore**: A NoSQL cloud database for real-time data synchronization, used to store and retrieve journal entries.

- **Firebase Storage**: A cloud storage solution that allows users to upload images associated with their journal entries securely.

- **XML**: Used for designing user interface layouts in Android, defining the structure and appearance of UI components.

- **Glide**: An image loading library that simplifies loading images into ImageViews while handling caching and memory management efficiently.

- **Options Menu**: Provides a menu for users to manage their session such as logout.

## How to Use This Project

### Create and Setup Your Own Firebase Project
1. **Clone This Project**:
   - Clone the repository using the following command or by downloading the zip file.

     ```bash
     git clone https://github.com/yourusername/journal-app.git
     ```
2. **Create a Firebase Project**:
   - Go to the [Firebase Console](https://console.firebase.google.com/).
   - Click on "Add project" and follow the prompts to enter your project name, select your country/region, and accept the terms of service. Click "Create project" when done.

3. **Enable Services**:
   - After creating your project, navigate to the "Build" section in the left sidebar.
   - Enable the following services:
     - **Authentication**: Set up sign-in methods (e.g., Email/Password).
     - **Firestore Database**: Click on "Create database" and choose "Start in test mode" for development purposes.
     - **Storage**: Click on "Get Started" to enable Cloud Storage for file uploads.
  - Also do not forget to download the `google-services.json` file and place into the root directory of your app module (usually located at `app/` in Android Studio).

4. **Create Firestore Collection**:
   - In Firestore, create a collection reference named "Journal". This will be used to store journal entries for each user.

## Google Services Configuration

To properly configure your project, please ensure you add your own `google-services.json` file. This file is essential for integrating Firebase services into your application.

### Steps to Add `google-services.json`

1. **Create a Firebase Project**:
   - Go to the [Firebase Console](https://console.firebase.google.com/).
   - Click on "Add project" and follow the prompts to create a new project.

2. **Register Your App**:
   - In the Firebase console, select your project.
   - Click on the gear icon (⚙️) next to "Project Overview" and select "Project settings."
   - In the "Your apps" section, click on "Add app" and choose Android.
   - Enter your app's package name (make sure it matches the `applicationId` in your `build.gradle` file).

3. **Download `google-services.json`**:
   - After registering your app, you will see an option to download the `google-services.json` file.
   - Click on "Download google-services.json" to obtain the configuration file.

4. **Add the File to Your Project**:
   - Move the downloaded `google-services.json` file into the root directory of your app module (usually located at `app/` in Android Studio).

5. **Add Google Services Plugin and dependency**:
   - Open your project-level `build.gradle` file and add 'com.google.gms:google-services' dependency:

6. **Sync Your Project**:
   - After making these changes, sync your project with Gradle files to ensure everything is set up correctly.

7. **Verify Configuration**:
   - Run your app to verify that Firebase services are properly configured and working as expected.
