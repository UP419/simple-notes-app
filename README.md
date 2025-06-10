1. Install Frontend Dependencies
Navigate to the frontend directory and run: npm install
This will install all the required dependencies into the node_modules directory.

2. Set Up Environment Variables
Create a .env file in the project root. This file should contain the necessary environment variables such as database credentials and frontend address.

3. Run the PostgreSQL Database
Make sure a PostgreSQL database is running. The app expects it to be available on port 5432. You can use Docker for this. For example:
   docker run --name notes-db -e POSTGRES_USER=userName -e POSTGRES_PASSWORD=password -e POSTGRES_DB=database -p 5432:5432 -d postgres

4. Run the Backend
From the root directory, start the Spring Boot backend with: ./mvnw spring-boot:run
Alternatively, you can run the application from IntelliJ by pressing the triangle button next to NotesAppApplication.java.

5. Run the Frontend
In the frontend directory, start the development server with: npm run dev


The app will be available at http://localhost:3000.