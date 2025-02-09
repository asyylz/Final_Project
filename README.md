<body>
<header>
    <h1>Worksheet Generator Application</h1>
    <p>The application is designed to assist parents and tutors in preparing
        students for the 11+ GL exam by generating tailored practice worksheets.</p>
</header>
<section id="table-of-contents">
    <h2>Table of Contents</h2>
    <ul>
        <li><a href="#features">Features</a></li>
        <li><a href="#technologies">Technologies</a></li>
        <li><a href="#development-tools">Development Tools</a></li>
        <li><a href="#outcome">Outcome</a></li>
        <li><a href="#contributing">Contributing</a></li>
        <li><a href="#license">License</a></li>
    </ul>
</section>
<section id="features">
    <h2>Features</h2>
    <p>Key features covered in this repository:</p>
    <ul>
        <li><strong>User Authentication:</strong> Secure login and registration system for users, ensuring that only
            authorized users can access the platform.
        </li>
        <li><strong>Main Subject and Sub Subject Selection:</strong> Allows users to choose from a variety of main
            subjects and their corresponding sub-subjects for customized content generation or activities.
        </li>
        <li><strong>Difficulty Level of Sheets:</strong> Users can select the difficulty level of worksheets to match
            their skill and experience, providing a personalized challenge.
        </li>
        <li><strong>Interactive Answer and Score Check:</strong> Provides instant feedback on user answers, with a score
            check display.
        </li>
        <li><strong>Locking Answers with Pin:</strong> Adds a layer of security by allowing users to lock their answers
            with a pin code and set a timer for completing tasks, creating a controlled environment for assessments or
            exercises.
        </li>
        <li><strong>Setting Timer:</strong> Setting a timer for completing tasks, creating a controlled environment for
            assessments or exercises.
        </li>
        <li><strong>Responsive Design:</strong> Ensures a seamless and optimized user experience.</li>
    </ul>
</section>
<section id="technologies">
    <h2>Technologies</h2>
    <ul>
        <li><strong>JavaFX:</strong> A framework for building rich desktop applications using Java. Provides tools for
            creating user interfaces with modern graphics, animations, and more.
        </li>
        <li><strong>OpenAI API (gpt-3.5-turbo):</strong> Used to generate worksheet content dynamically, create tailored
            questions, answers, and explanations based on the selected subjects and difficulty levels.
        </li>
        <li><strong>MS SQL Server:</strong> A relational database management system used for storing and managing
            application data, ensuring efficient data storage and retrieval.
        </li>
    </ul>
</section>
<section id="development-tools">
    <h2>Development Tools</h2>
    <ul>
        <li><strong>IntelliJ IDEA:</strong> A powerful Integrated Development Environment (IDE) for Java and other
            languages, used for efficient coding, debugging, and project management.
        </li>
        <li><strong>Mockito:</strong> A popular testing framework for Java that allows for easy creation of mock objects
            in unit tests, making it simpler to isolate and test specific parts of the application.
        </li>
    </ul>
</section>
<section id="getting-started">
    <h2>Getting Started</h2>
    <p>To get started with the repository, follow these steps:</p>
    <h3>Installation</h3>
    <ol>
        <li>
            <h3>Clone</h3>
        Clone the repository to your local machine using the following command:</li>
        <code>git clone git clone https://github.com/asyylz/Final_Project</code>
        <li>
            <h3>Environment Variables</h3>
        Set up your environment variables: Create a `.env` file at the root level (same level as the `src`
        directory) and add the following variables:
            <ol>
                <li>API_KEY -> Should be created</li>
                <li>API_URL -> https://api.openai.com/v1/chat/completions</li>
                <li>SQLSERVER_URL_DB -> jdbc:sqlserver://localhost;database=WorksheetGeneratorApp</li>
                <li>SQLSERVER_USER -> SA</li>
                <li>SQLSERVER_PASSWORD -> Should be created</li>
            </ol>
        </li>
        <li>
            <h3>Docker Setup</h3>
            <p>For both **development** and **deployment (EXE mode)**, Docker is used to create isolated
                environments.Create a Docker image
                for the application by using the provided docker and docker compose file Here's how to set it up:</p>
            <ol>
                <li>
                    <p>Ensure you are in WorksheetGenerator path</p>
                    <code>cd WorksheetGenerator</code>
                </li>
                <li>
                    <p>Initialize  image creation and related container</p>
                    <code>docker-compose up --build -d</code>
                </li>
                <li>
                    <p>Ensure WorksheetGeneratorApp database  listed:</p>
                    <code>docker exec -it wgapp-database-container bash</code>
                </li>
                <li>
                    <p>Inside container</p>
                    <code>/opt/mssql-tools18/bin/sqlcmd -S localhost -U SA -P "${SQLSERVER_PASSWORD}" -Q "SELECT name FROM sys.databases" -C</code>
                    <p>You should see WorksheetGeneratorApp database in the list</p>
                </li>
            </ol>
        </li>
    </ol>
</section>
<section id="outcome">
    <h1>Outcome</h1>
    <p>To experience the application you can use following email and password:</p><br>
    <p><strong>username:</strong> testUser123<br>
        <strong>password:</strong> passW@rd
    </p>
    <p>Reach outcome site <a href="">here</a></p>
    <img src=""
         alt="">
</section>
<section id="test-coverage">
    <h1>Test Coverage</h1>
    <img src=""
         alt="">
</section>
<footer>
    <h2>Contributing</h2>
    <p>Contributions to the project are welcome! If you find any issues or have suggestions for improvements, please
        feel free to open an issue or submit a pull request.</p>
    <h2>License</h2>
    <p>This project is licensed under the MIT License.</p>
</footer>
</body>