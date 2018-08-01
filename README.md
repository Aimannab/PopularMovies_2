# Project Overview

In this second and final stage, you’ll add additional functionality to the app you built in Stage 1. Y#ou will add to the app you built in Stage 1 by building on the detail view for each movie, allowing users to 'favorite' movies.

You’ll add more information to your movie details view:

* You’ll allow users to view and play trailers ( either in the youtube app or a web browser).
* You’ll allow users to read reviews of a selected movie.
* You’ll also allow users to mark a movie as a favorite in the details view by tapping a button(star).
* You'll create a database to store the names and ids of the user's favorite movies (and optionally, the rest of the information needed to display their favorites collection while offline).
* You’ll modify the existing sorting criteria for the main view to include an additional pivot to show their favorites collection.

# What Will I Learn After Stage 2?

You will build a fully featured application that looks and feels natural on the latest Android operating system (Nougat, as of November 2016).

# How Will I Complete this Project?
## Supporting Course Materials

You should have the skills you need to complete this project after completing the Developing Android Apps course.
You are expected to have passed the Popular Movies, Stage 1 project prior to beginning this project.

## Implementation Guide
Just like in Stage 1, we've come up with a set of milestones that will help structure your development and ensure that you reach the final product smoothly. These milestones are listed in the Stage 2 Implementation Guide.
Link: https://docs.google.com/document/d/1ZlN1fUsCSKuInLECcJkslIqvpKlP7jWL2TP9m6UiA6I/pub?embedded=true#h.7sxo8jefdfll

# Rubric

## Required Specifications

* App is written solely in the Java Programming Language.
* App conforms to common standards found in the Android Nanodegree General Project Guidelines.
* App utilizes stable release versions of all libraries, Gradle, and Android Studio.

## User Interface - Layout Specifications

* UI contains an element (e.g., a spinner or settings menu) to toggle the sort order of the movies by: most popular, highest rated.
* Movies are displayed in the main layout via a grid of their corresponding movie poster thumbnails.
* UI contains a screen for displaying the details for a selected movie.
* Movie Details layout contains title, release date, movie poster, vote average, and plot synopsis.
* Movie Details layout contains a section for displaying trailer videos and user reviews.

## User Interface - Function Specifications

* When a user changes the sort criteria (most popular, highest rated, and favorites) the main view gets updated correctly.
* When a movie poster thumbnail is selected, the movie details screen is launched.
* When a trailer is selected, app uses an Intent to launch the trailer.
* In the movies detail screen, a user can tap a button (for example, a star) to mark it as a Favorite. Tap the button on a favorite movie will unfavorite it.

## Network API Implementation

* In a background thread, app queries the /movie/popular or /movie/top_rated API for the sort criteria specified in the settings menu.
* App requests for related videos for a selected movie via the /movie/{id}/videos endpoint in a background thread and displays those details when the user selects a movie.
* App requests for user reviews for a selected movie via the /movie/{id}/reviews endpoint in a background thread and displays those details when the user selects a movie.

# Data Persistence

The titles and IDs of the user’s favorite movies are stored in a native SQLite database and exposed via a ContentProvider
OR
stored using Room.

* Data is updated whenever the user favorites or unfavorites a movie. No other persistence libraries are used.
* When the "favorites" setting option is selected, the main view displays the entire favorites collection based on movie ids stored in the database.

## Android Architecture Components

* If Room is used, database is not re-queried unnecessarily. LiveData is used to observe changes in the database and update the UI accordingly.
* If Room is used, database is not re-queried unnecessarily after rotation. Cached LiveData from ViewModel is used instead.

