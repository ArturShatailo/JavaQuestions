<h1>Spring Boot Telegram Bot</h1>
<h2>Java questions</h2>

<p style="color: #ef1ec4">This project is under development</p>
<p>This is an application that allows collect, share and get answers to Java interview questions. This bot is developed to help with interview preparation. 
Telegram Bot application is developed with Spring Boot framework and PostgreSQL database. 
Buttons and commands are presented as Controller endpoints, and logic is implemented in Service classes with JPA Repository injection. </p>

<!--SPACE 20PX --><div style="padding: 10px"></div>

<h3>Contents</h3>
<ul>
    <li><p>Introduction</p></li>
    <li><p>Technologies stack</p></li>
    <li><p>Short classes and repositories structure overview</p></li>
    <li><p>Functionality description</p></li>
    <li><p>Database structure</p></li>
    <li><p>Conclusion</p></li>
</ul>

<!--SPACE 20PX --><div style="padding: 10px"></div>

<h3>Technologies stack</h3>
<ul>
    <li><p>Spring boot</p></li>
    <li><p>PostgreSQL</p></li>
    <li><p>JUnit</p></li>
    <li><p>Telegram Bot API</p></li>
    <li><p>Amazon AWS</p></li>
</ul>

<!--SPACE 20PX --><div style="padding: 10px"></div>

<h3>Short classes and repositories structure overview</h3>
<p>There is a simple for code review structure that divides files according to their purpose
and functionality:</p>
<ol>
    <li><p><span style="color: #d7d100">bot</span> repository where all the classes and interfaces related to the 
Bot entity are placed, including session, messages sender implementation, model and configuration file.</p></li>
    <li><p><span style="color: #d7d100">controller</span> repository contains classes that allows to handle requests from UI as well as web controller class with
endpoints for database administration.</p></li>
    <li><p><span style="color: #d7d100">domain</span> repository contains classes of POJO entities that is related to database development.</p></li>
    <li><p><span style="color: #d7d100">repository</span> contains interfaces that extend JpaRepository interface and may include custom SQL requests to database.</p></li>
    <li><p><span style="color: #d7d100">service</span> repository contains interfaces with methods related to the logic of the application. There are also classes with realisations 
of methods in service interfaces. Methods are related to requests from UI logic and also entities CRUD methods.</p></li>
</ol>

<!--SPACE 20PX --><div style="padding: 10px"></div>

<h3>Functionality description</h3>
<p>The list of commands allowed in this bot:</p>
<ul>
    <li><p><span style="color: #d7d100">/start</span></p>
        <p>Allows begin working with the bot in telegram application</p></li>
    <li><p><span style="color: #d7d100">/reset</span></p>
        <p>Allows return to the main menu and reset all the requests before</p></li>
    <li><p><span style="color: #d7d100">/help</span></p>
        <p>Sends message with the functionality and main commands description</p></li>
</ul>
<p>The list of possible actions that this bot can handle</p>
<ul>
    <li><p><span style="color: #d7d100">Read Java questions and answers</span></p>
        <p>All questions divided into categories and levels. </p></li>
    <li><p><span style="color: #d7d100">Send request for adding new question</span></p>
        <p>Each user can be an author of questions by sending request for verification.</p></li>
    <li><p><span style="color: #d7d100">Pass an interview</span></p>
        <p>User can try to pass an interview and answer several questions from each topic according 
to user's level of knowledge.</p></li>
</ul>

<!--SPACE 20PX --><div style="padding: 10px"></div>

<h3>Database structure</h3>
<div>
    <img src="./java_questions.png" alt="database structure">
</div>

<h3>Conclusion</h3>
<p>This project helps me to fill out my gaps before interviews and make my knowledge base stronger. 
Moreover, I practiced with Spring boot framework and tried to create clear and scalable application. </p>

