
# The Record Shop

An Android application designed to manage a music record store, providing a smooth user experience for adding, updating, and deleting albums and artists. The app interfaces with the [Record Shop API](https://github.com/jeremiahhaastrup/Record_Shop_API) to handle data storage and retrieval, offering real-time updates for artists and album collections.

## Features
- **Album Management**:
  - Add, update, and delete albums in the record shop.
  - Display album details including cover art, title, release date and artist information using Glide for image loading.
  
- **Artist Management**:
  - Add, update, and delete artist profiles.
  - Display artist details including a profile picture, date of birth, place of birth and biography.

- **UI & UX**:
  - Clean and responsive interface built using MVVM architecture.
  - Integration of Glide for smooth image loading of albums and artist images.
  - User-orientated navigation for managing records and viewing album/artist details.

## Tech Stack
- **Java**: Core language for Android development.
- **MVVM Architecture**: Ensures a clear separation of concerns.
- **Glide**: For efficient image loading and caching.
- **Retrofit**: For handling API requests.
- **Spring Boot (Postgres API)**: The backend API is developed using Spring Boot and hosted on Supabase.
- **Gradle**: For build automation.

## Architecture
The application follows the MVVM architecture pattern, allowing maintainability and scalability by separating the business logic from the UI layer.

## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/jeremiahhaastrup/Record-Shop-Application.git
   ```
2. Open the project in **Android Studio**.
3. Build and run the project on an Android emulator or physical device.

## API Setup
Ensure the [Record Shop API](https://github.com/jeremiahhaastrup/Record_Shop_API) is running for the app to fetch and modify data.

### Steps:
1. Set up the API by following the instructions in its [repository](https://github.com/jeremiahhaastrup/Record_Shop_API).
2. Use the API for managing albums and artists in real-time.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
