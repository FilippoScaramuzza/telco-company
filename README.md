# Telecommunication Company Management WebApp Using the Java Persistence API (JPA)

This project was an assignment part of the exam "Data Bases 2" at Politecnico di Milano. This post is made of two parts, in the first one I'll outline the main points of the requirements given by the professors. In the second part I'll show my implementation focusing on the most relevant parts.

Table of Contents

*   [Project Requirements](#project-req)

*   [My Extra Hypothesis](#extra-hyp)

*   [ER and MySQL Diagrams](#diagrams)

*   [Demonstration Videos](#videos)

Project Requirements
--------------------

A telco company offers pre-paid online services to web users. Two client applications using the same database need to be developed.

###  Consumer Application

*   The consumer application has a landing page with login and registration forms.
*   Registration requires a unique username, password, and email.
*   The Home page displays telco service packages, with each package having an ID, name, and one or more services.
*   Services include fixed and mobile phone and internet, each with different configurations and fees.
*   Packages can include optional products with independent monthly fees.
*   The Buy Service page allows users to select packages, validity periods, and optional products and confirms the total price.
*   When the user presses the BUY button, an order is created, billed externally, and scheduled for activation.
*   If billing is rejected three times, an alert is created in a dedicated auditing table.
*   Insolvent users can access the Home page with the list of rejected orders, and try payment again from the confirmation page.

### Employee Application

The employee application allows the authorized employees of the telco company to log in. In the Home page, a form allows the creation of service packages, with all the needed data and the possible optional products associated with them. The same page lets the employee create optional products as well. A Sales Report page allows the employee to inspect the essential data about the sales and about the users over the entire lifespan of the application:

*   Number of total purchases per package.
*   Number of total purchases per package and validity period.
*   Total value of sales per package with and without the optional products.
*   Average number of optional products sold together with each service package.
*   List of insolvent users, suspended orders and alerts.
*   Best seller optional product, i.e. the optional product with the greatest value of sales across all the sold service packages.

My Extra Hypothesis
-------------------

As I learned from the _Distributed Systems_ course, the first thing to make things work is to add hypothesis where something is not specified, or just to make things work. The most important hypothesis have been made with respect to the DB design. It was designed keeping in mind that some small changes in the application domain can occur in the future. In particular if the Employee wants to **add more services** (other than the 4 specified) he can. The same assumption was made for the service options (SMS, minutes, GB) since with this specific design **more service options can be added**. Any limitations may be added to the employee front-end. Other big hypothesis with respect to the application domain:

*   The Employee can also **modify service packages**, other than creating new one.
*   Both the User and the Employee **can not delete an Order**. This is because of the Statistics that the Administration may want to keep track.

ER-Diagram and Exported-MySQL Relation Diagram
----------------------------------------------

Before the start of the development, I designed the DataBase with the following ER-Diagram: ![](https://filipposcaramuzza.dev/wp-content/uploads/2023/04/er_diagram-e1680964063690.png) _The Statistics tables are omitted from the ER Digram since they are computed by triggers acting only as aggregation of data existing in tables shown in the ER diagram._ That resulted in the following MySQL Relation Diagram: ![](https://filipposcaramuzza.dev/wp-content/uploads/2023/03/relation_diagram-1024x723.png) _As for the ER diagram above, the statistics tables have been omitted. Their structure is described in the next pages. Almost everywhere an artificial primary key was used (ID)._

Interaction Diagram
-------------------

The following is the Interaction Diagram I used to design the application: ![](https://filipposcaramuzza.dev/wp-content/uploads/2023/03/Screenshot-2023-04-08-at-15.49.35-1024x467.png)

Video Demonstrations
--------------------
See videos at <a href="https://filipposcaramuzza.dev/index.php/2023/04/08/telecommunication-company-management-webapp-using-the-java-persistence-api-jpa/#videos">filipposcaramuzza.dev (link to the post)</a>
