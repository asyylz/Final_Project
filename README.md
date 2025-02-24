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
        <li><a href="#getting-started">Getting Started</a></li>
        <li><a href="#intro-video">Introductory Video</a></li>
        <li><a href="#application">Application</a></li>
        <li><a href="#documentation">Documentation</a></li>
        <li><a href="#contributing">Contributing</a></li>
        <li><a href="#license">License</a></li>
    </ul>
</section>
<section id="features">
    <h2>1-Features</h2>
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
        <li><strong>Singleton (UI Layer)</strong> Ensures that only one instance of a UI manager (or a similar component) exists throughout the application's lifecycle. Helps in managing global UI state efficiently.</li>
        <li><strong>Observer (UI Notifications)</strong> Implements a publisher-subscriber model to notify the UI of state changes dynamically. Ensures decoupling between data sources and UI components, improving responsiveness</li>
    </ul>
</section>
<section id="technologies">
    <h2>2-Technologies</h2>
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
    <h2>3-Development Tools</h2>
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
    <h2>4-Getting Started</h2>
    <p>To get started with the repository, follow these steps:</p>
    <h3>Installation</h3>
    <ol>
        <li>
            <h3>Clone</h3>
        Clone the repository to your local machine using the following command:</li>
        <pre>
<code>git clone git clone https://github.com/asyylz/Final_Project</code>
        </pre>
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
            <h3>Docker Setup for Development & Deployment</h3>
            <p>This section explains how to set up your environment using Docker for both development and deployment (EXE mode). Docker is used to create isolated environments,
                which makes it easy to run the application and its dependencies consistently.</p><br>
            <ol>
                <li>
                    <p>First, ensure that you're in the correct directory, where the docker-compose.yml and related files are located:</p>
                        <pre><code>cd WorksheetGenerator</code></pre>
                </li><br>
                <li>
                    <p>To create the Docker image and start the related containers, run the following command. This will build the Docker image and start the 
                    containers in detached mode (-d):</p>
                  <pre><code>docker-compose up --build -d</code></pre>  
                </li><br>
                <li>
                    <p>Once the containers are up and running, you can verify that the WorksheetGeneratorApp database has been created inside the container.
                    To do this, execute the following command. This will open an interactive shell inside the container.</p>
<pre><code>docker exec -it wgapp-database-container bash</code></pre>
                </li><br>
                <li>
                    <p> Now, you are inside the container. Run the following command to list all databases:</p>
<pre><code>/opt/mssql-tools18/bin/sqlcmd -S localhost -U SA -P "${SQLSERVER_PASSWORD}" -Q "SELECT name FROM sys.databases" -C</code></pre>    
                </li><br>
                <p>You should see a list of databases, and WorksheetGeneratorApp should be present in the list of databases.</p>
                <div>
            <pre>
            <code>
<span>name</span>
<span>-------------------------------</span>
<span>master</span>
<span>tempdb</span>
<span>model</span>
<span>WorksheetGeneratorApp</span>
            </code>
                </pre>
                </div>
<li><p>You exit  from container  by typing container's bash exit:</p>
            <pre><code>exit</code></pre>

</li>
<li>
    <p>To stop container:</p>
      <pre><code>docker stop wgapp-database-container</code></pre>
<p>To start again container:</p>
      <pre><code>docker start wgapp-database-container</code></pre>
</li>
<li>
    <p>Run application with <strong>App.java</strong> file.</p>
</li>
            </ol>
        </li>
    </ol>
</section>
<section id="intro-video">
   <h1>5-Introductory Video</h1>
<p>Link: <a href="https://asiyeyaliz.s3.eu-west-2.amazonaws.com/output.mp4" target="_blank">Watch Video</a></p>
</section>
<section id="application">
    <h1>6-Application</h1>
<p>6.1. Upon launching the application, you will be greeted with the login window.Login Credentials for testing.
After clicking Let's start button user will be directed main window with generation content.
</p>
    <p><strong>username:</strong> testUser<br>
        <strong>password:</strong> passW@rd
    </p>
<div style="display: flex; align-items: center; justify-content: space-around; margin-bottom: 3em">
 <img src="https://wgapp.s3.eu-west-2.amazonaws.com/login.jpeg"
         alt="" height="200"> &nbsp &nbsp &nbsp &nbsp &nbsp
<img src="https://wgapp.s3.eu-west-2.amazonaws.com/landing.jpeg"
         alt="" height="400">
</div><br>
<p>6.2. User can start worksheet generation on generation section on main window by selecting all necessary fields.Once all necessary fields selected generation button will be activated. If user chooses english paper with comprehension (spag, vocabulary,cloze etc) as sub topic passage addition button will appear. User should add a passage and a title for passage. You can clear out all selections by clicking clear selection button and start a new one.
</p>
<div style="display: flex; align-items:center; justify-content: space-around;
margin-bottom: 40px; margin-top: 20px">
 <img src="https://wgapp.s3.eu-west-2.amazonaws.com/main-generator1.jpeg"
         alt="" height="300"> &nbsp &nbsp &nbsp &nbsp &nbsp
<img src="https://wgapp.s3.eu-west-2.amazonaws.com/passage-generator1.jpeg"
         alt="" height="300">
</div><br>
<p>6.3. Once generation is complete, the worksheet section will appear, and the UI will update with the newly generated worksheet. Users can interact with the worksheet in this section, including deleting it, setting a timer, or searching by passage title. However, the search feature is not available for math papers. The 'Show Answers' button will also be activated, but it requires unlocking with a PIN.<br>
User also can download worksheet in PDF format by clicking download button. Exit from program clicking on red exit button.
</p>
<div margin="20px">
 <img src="https://wgapp.s3.eu-west-2.amazonaws.com/worksheet1.jpeg"
         alt="" height="300"> &nbsp &nbsp &nbsp &nbsp &nbsp
<img src="https://wgapp.s3.eu-west-2.amazonaws.com/unlock.jpeg"
         alt="" height="100">
</div><br>
<p>6.4. Users can view a list of worksheets they previously generated in the history section. Here, they can delete a worksheet or open and view it in the worksheet section.<br>In the account section, users can update their password and PIN. By default, all registered users have a PIN set to (0000).
</p>
 <div style="display: flex; align-items: center; justify-content: space-around; margin-top: 40px">
<img src="https://wgapp.s3.eu-west-2.amazonaws.com/history1.jpeg"
         alt="" height="300"> &nbsp &nbsp &nbsp &nbsp &nbsp
 <img src="https://wgapp.s3.eu-west-2.amazonaws.com/account1.jpeg"
         alt="" height="300">
</div><br>
</section>
<section id="documentation">
    <h1>7-Documentation</h1>
    <img src="https://wgapp.s3.eu-west-2.amazonaws.com/ERD.jpg" height="400"/><br><br>
    <img src="https://wgapp.s3.eu-west-2.amazonaws.com/ClassDiagramUI.jpg" />
</section>
<footer>
    <h1>78-Contributing</h1>
    <p>Contributions to the project are welcome! If you find any issues or have suggestions for improvements, please
        feel free to open an issue or submit a pull request.</p>
    <h1>9-License</h1>
    <p>This project is licensed under the MIT License.</p>
</footer>
</body>
